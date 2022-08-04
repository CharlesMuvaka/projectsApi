import com.google.gson.Gson;
import data.ProjectDao;
import exceptions.ApiException;
import models.Project;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        Gson gson = new Gson();
        ProjectDao dao = new ProjectDao();

        //add a project
        post("/project", "application/json",(req, res)->{
            Project project = gson.fromJson(req.body(), Project.class);

            if(project.getName() == null){
                return new ApiException(400, "Please enter the name of the project");
            }else if(project.getType() == null){
                return new ApiException(401, "Please enter the type of the project");
            }else if(project.getUrl1() == null){
                return new ApiException(402, "Please enter the image url of the project");

            }else if(project.getLink() == null){
                return new ApiException(400, "Please enter the link to the project");
            }else{
                dao.addProject(project);
                res.status(201);
                return gson.toJson(project);
            }

        });

        //get all projects
        get("/projects", "application/json", (req,res)->{
            if(dao.getAllProjects() != null){
                return gson.toJson(dao.getAllProjects());
            }else {
                return new ApiException(402, "There are no available projects");
            }
        });

        //get a project by id
        get("/project/:id", "application/json", (req, res)->{
           int id = Integer.parseInt(req.params(":id"));
           Project project = dao.getProjectById(id);

           if (project == null){
               return  new ApiException(403, String.format("The project with the given id %s doesn't exist", id));
           }else{
               return  gson.toJson(project);
           }
        });

        //get a project based on its type
        get("/projects/:type", "application/json", (req, res)->{
            String type = req.params(":type");
            if(dao.getProjectByType(type) == null){
                return  new ApiException(401, String.format("Projects of type %s arent available", type));
            }else{
                return gson.toJson(dao.getProjectByType(type));
            }
        });

        //update a project
        patch("/updateProject/:id", "application/json", (req, res)->{
           int id = Integer.parseInt(req.params(":id"));
           Project project = gson.fromJson(req.body(), Project.class);

           if(dao.getProjectById(id) == null){
               return  new ApiException(402, String.format("Project with id %s doesn't exist thus can't be updated ", id));
           }else{
               project.setId(id);
               dao.updateProject(id, project);
               return gson.toJson(project);
           }
        });

        //delete a project based on its id
        delete("/deleteProject/:id", "application/json", (req, res)->{
            int id = Integer.parseInt(req.params(":id"));
            if(dao.getProjectById(id) == null){
                return new ApiException(403, String.format("Project with the given id %s doesn't exist therefore cant be deleted", id));
            }else{
                dao.deleteProject(id);
                return gson.toJson(dao.getAllProjects());
            }
        });


        exception(ApiException.class, (exc, req, res) -> {
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", exc.getStatusCode());
            jsonMap.put("errorMessage", exc.getMessage());
            res.type("application/json"); //after does not run in case of an exception.
            res.status(exc.getStatusCode()); //set the status
            res.body(gson.toJson(jsonMap));  //set the output.
        });

        after((req,res)-> res.type("application/json"));

    }
}
