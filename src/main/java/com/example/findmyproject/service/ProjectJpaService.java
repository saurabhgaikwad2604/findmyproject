package com.example.findmyproject.service;

import com.example.findmyproject.model.*;
import com.example.findmyproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class ProjectJpaService implements ProjectRepository {
    @Autowired
    private ProjectJpaRepository projectJpaRepository;

    @Autowired
    private ResearcherJpaRepository researcherJpaRepository;

    @Override
    public ArrayList<Project> getProjects() {
        List<Project> projectList = projectJpaRepository.findAll();
        ArrayList<Project> projects = new ArrayList<>(projectList);
        return projects;
    }

    @Override
    public Project getProject(int id) {
        try {
            Project project = projectJpaRepository.findById(id).get();
            return project;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Project addProject(Project project) {
        try {
            List<Integer> researcherIds = new ArrayList<>();
            for (Researcher researcher : project.getResearchers()) {
                researcherIds.add(researcher.getId());
            }
            List<Researcher> researchers = researcherJpaRepository.findAllById(researcherIds);
            if (researcherIds.size() != researchers.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            project.setResearchers(researchers);

            for (Researcher researcher : researchers) {
                researcher.getProjects().add(project);
            }
            Project savedProject = projectJpaRepository.save(project);
            researcherJpaRepository.saveAll(researchers);
            return savedProject;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Project updateProject(int id, Project project) {
        try {
            Project newProject = projectJpaRepository.findById(id).get();
            if (project.getName() != null) {
                newProject.setName(project.getName());
            }
            if (project.getBudget() != 0) {
                newProject.setBudget(project.getBudget());
            }
            if (project.getResearchers() != null) {
                List<Researcher> researchers = newProject.getResearchers();
                for (Researcher researcher : researchers) {
                    researcher.getProjects().remove(newProject);
                }
                researcherJpaRepository.saveAll(researchers);

                List<Integer> newResearcherIds = new ArrayList<>();
                for (Researcher researcher : project.getResearchers()) {
                    newResearcherIds.add(researcher.getId());
                }
                List<Researcher> newResearchers = researcherJpaRepository.findAllById(newResearcherIds);
                if (newResearcherIds.size() != newResearchers.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                for (Researcher researcher : newResearchers) {
                    researcher.getProjects().add(newProject);
                }
                researcherJpaRepository.saveAll(newResearchers);
                newProject.setResearchers(newResearchers);
            }
            return projectJpaRepository.save(newProject);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteProject(int id) {
        try {
            Project project = projectJpaRepository.findById(id).get();

            List<Researcher> researchers = project.getResearchers();
            for (Researcher researcher : researchers) {
                researcher.getProjects().remove(project);
            }
            researcherJpaRepository.saveAll(researchers);

            projectJpaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Researcher> getProjectResearcher(int id) {
        Project project = projectJpaRepository.findById(id).get();
        List<Researcher> researchers = project.getResearchers();
        return researchers;
    }
}