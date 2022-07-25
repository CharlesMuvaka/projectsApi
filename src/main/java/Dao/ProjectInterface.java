package Dao;

import models.Project;

import java.util.List;

public interface ProjectInterface {

    //CREATE
    void addProject();

    //READ
    Project getProjectById(int id);
    List<Project> getProjectByType(String type);

    //Update
    void updateProject(int id, Project project);

    //Delete
    void deleteProject(int id);
}
