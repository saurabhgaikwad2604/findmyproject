package com.example.findmyproject.repository;

import com.example.findmyproject.model.*;
import java.util.*;

public interface ProjectRepository {
    ArrayList<Project> getProjects();

    Project getProject(int id);

    Project addProject(Project project);

    Project updateProject(int id, Project project);

    void deleteProject(int id);

    List<Researcher> getProjectResearcher(int id);
}