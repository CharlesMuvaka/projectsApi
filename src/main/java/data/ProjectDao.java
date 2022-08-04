package data;

import Dao.DB;
import Dao.ProjectInterface;
import models.Project;
import org.sql2o.Connection;

import java.util.List;

public class ProjectDao implements ProjectInterface {

    @Override
    public void addProject(Project project) {
       String query = "INSERT INTO projects(name, type, status, language_used, url1, url2, url3, created_at, link) VALUES(:name, :type, :status, :language_used, :url1, :url2, :url3, :created_at, :link)";
       try(Connection conn = DB.sql2o.open()){
           int id = (int) conn.createQuery(query, true)
                   .addParameter("name", project.getName() )
                   .addParameter("type", project.getType() )
                   .addParameter("status", project.getStatus() )
                   .addParameter("language_used", project.getLanguage_used() )
                   .addParameter("url1", project.getUrl1() )
                   .addParameter("url2", project.getUrl2() )
                   .addParameter("url3", project.getUrl3() )
                   .addParameter("created_at", project.getCreated_at() )
                   .addParameter("link", project.getLink() )
                   .executeUpdate()
                   .getKey();
           project.setId(id);
       }
    }

    @Override
    public Project getProjectById(int id) {
        String query = "SELECT * FROM projects WHERE id = :id";
        try(Connection conn = DB.sql2o.open()){
            return conn.createQuery(query)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Project.class);
        }
    }

    @Override
    public List<Project> getProjectByType(String type) {
        String query = "SELECT * FROM projects WHERE type = :type";
        try(Connection conn = DB.sql2o.open()){
            return conn.createQuery(query)
                    .addParameter("type", type)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Project.class);
        }

    }

    @Override
    public List<Project> getAllProjects() {
        String query = "SELECT * FROM projects";
        try(Connection conn = DB.sql2o.open()){
            return conn.createQuery(query)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Project.class);
        }
    }

    @Override
    public void updateProject(int id, Project project) {
        String query  = "UPDATE projects SET name = :name , type = :type, status = :status, language_used = :language_used, url1 = :url1, url2 = :url2, url3 = :url3, created_at = :created_at, link = :link WHERE id = :id";
        try(Connection conn = DB.sql2o.open()){
            conn.createQuery(query)
                    .addParameter("name", project.getName())
                    .addParameter("type", project.getType())
                    .addParameter("status", project.getStatus())
                    .addParameter("language_used", project.getLanguage_used())
                    .addParameter("url1", project.getUrl1())
                    .addParameter("url2", project.getUrl2())
                    .addParameter("url3", project.getUrl3())
                    .addParameter("created_at", project.getCreated_at())
                    .addParameter("link", project.getLink())
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteProject(int id) {
        String query = "DELETE FROM projects WHERE id = :id";
        try(Connection conn = DB.sql2o.open() ){
            conn.createQuery(query)
                    .addParameter("id", id)
                    .executeUpdate();
        }

    }
}
