import com.google.gson.Gson;
import data.ProjectDao;
import exceptions.ApiException;
import models.Project;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
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

        //get the index page
        get("/", (req, res)-> new ModelAndView(new HashMap<>(), "index.hbs"),new HandlebarsTemplateEngine());

        //get all template projects
        get("/allProjects", (req,res)->{
            Map<String, Object> templateData = new HashMap<>();
            List<Project> allProjects = dao.getAllProjects();
            templateData.put("projects", allProjects);
            return new ModelAndView(templateData, "projects.hbs");
        },new HandlebarsTemplateEngine());

        //get individual template project
        get("/singleProject/:id", (req,res)->{
            Map<String, Object> templateData = new HashMap<>();
            int id = Integer.parseInt(req.params(":id"));
            Project project = dao.getProjectById(id);
            templateData.put("projects", project);
            return new ModelAndView(templateData, "project_details.hbs");
        },new HandlebarsTemplateEngine());

        //get android projects
        get("/singleProject/:id", (req,res)->{
            Map<String, Object> templateData = new HashMap<>();
            String type = "Android-Application";
            List<Project> androidProjects = dao.getProjectByType(type);
            templateData.put("projects", androidProjects );
            return new ModelAndView(templateData, "android.hbs");
        },new HandlebarsTemplateEngine());

        //get web application projects
        get("/singleProject/:id", (req,res)->{
            Map<String, Object> templateData = new HashMap<>();
            String type = "Web-Application";
            List<Project> androidProjects = dao.getProjectByType(type);
            templateData.put("projects", androidProjects );
            return new ModelAndView(templateData, "android.hbs");
        },new HandlebarsTemplateEngine());

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
                res.type("application/json");
                return gson.toJson(project);
            }

        });

        //get all projects
        get("/projects", "application/json", (req,res)->{
            if(dao.getAllProjects() != null){
                res.type("application/json");
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
               res.type("application/json");
               return  gson.toJson(project);
           }
        });

        //get a project based on its type
        get("/projects/:type", "application/json", (req, res)->{
            String type = req.params(":type");
            if(dao.getProjectByType(type) == null){
                return  new ApiException(401, String.format("Projects of type %s arent available", type));
            }else{
                res.type("application/json");
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
               res.type("application/json");
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
                res.type("application/json");
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


    }
}
