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
        return null;
    }

    @Override
    public List<Project> getProjectByType(String type) {
        return null;
    }

    @Override
    public void updateProject(int id, Project project) {

    }

    @Override
    public void deleteProject(int id) {

    }
}
