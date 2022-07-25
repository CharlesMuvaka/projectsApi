package Dao;

import models.Project;

import java.util.List;

public interface ProjectInterface {

    //CREATE
    void addProject(Project project);

    //READ
    Project getProjectById(int id);
    List<Project> getProjectByType(String type);
    List<Project> getAllProjects();

    //Update
    void updateProject(int id, Project project);

    //Delete
    void deleteProject(int id);
}
