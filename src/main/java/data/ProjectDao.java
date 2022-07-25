package data;

import Dao.DB;
import Dao.ProjectInterface;
import models.Project;
import org.sql2o.Connection;

import java.util.List;

public class ProjectDao implements ProjectInterface {

    @Override
    public void addProject(Project project) {
       String query = "INSERT INTO projects(name, type, url, link) VALUES(:name, :type, :url, :link)";
       try(Connection conn = DB.sql2o.open()){
           int id = (int) conn.createQuery(query, true)
                   .addParameter("name", project.getName() )
                   .addParameter("type", project.getType() )
                   .addParameter("url", project.getUrl() )
                   .addParameter("link", project.getLink() )
                   .executeUpdate()
                   .getKey();
           project.setId(id);
       }
    }

    @Override
    public Project getProjectById(int id) {
        String query = "SELECT * FROM my_projects WHERE id = :id";
        try(Connection conn = DB.sql2o.open()){
            return conn.createQuery(query)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Project.class);
        }
    }

    @Override
    public List<Project> getProjectByType(String type) {
        String query = "SELECT * FROM my_projects WHERE type = :type";
        try(Connection conn = DB.sql2o.open()){
            return conn.createQuery(query)
                    .addParameter("type", type)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Project.class);
        }

    }

    @Override
    public List<Project> getAllProjects() {
        String query = "SELECT * FROM my_projects";
        try(Connection conn = DB.sql2o.open()){
            return conn.createQuery(query)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Project.class);
        }
    }

    @Override
    public void updateProject(int id, Project project) {

    }

    @Override
    public void deleteProject(int id) {

    }
}
