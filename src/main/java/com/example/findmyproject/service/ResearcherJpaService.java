package com.example.findmyproject.service;

import com.example.findmyproject.model.*;
import com.example.findmyproject.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class ResearcherJpaService implements ResearcherRepository {
    @Autowired
    private ResearcherJpaRepository researcherJpaRepository;

    @Autowired
    private ProjectJpaRepository projectJpaRepository;

    @Override
    public ArrayList<Researcher> getResearchers() {
        List<Researcher> researcherList = researcherJpaRepository.findAll();
        ArrayList<Researcher> researchers = new ArrayList<>(researcherList);
        return researchers;
    }

    @Override
    public Researcher getResearcher(int id) {
        try {
            Researcher researcher = researcherJpaRepository.findById(id).get();
            return researcher;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Researcher addResearcher(Researcher researcher) {
        try {
            List<Integer> projectIds = new ArrayList<>();
            for (Project project : researcher.getProjects()) {
                projectIds.add(project.getId());
            }
            List<Project> projects = projectJpaRepository.findAllById(projectIds);
            if (projectIds.size() != projects.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            researcher.setProjects(projects);

            for (Project project : projects) {
                project.getResearchers().add(researcher);
            }
            Researcher savedResearcher = researcherJpaRepository.save(researcher);
            projectJpaRepository.saveAll(projects);
            return savedResearcher;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Researcher updateResearcher(int id, Researcher researcher) {
        try {
            Researcher newResearcher = researcherJpaRepository.findById(id).get();
            if (researcher.getName() != null) {
                newResearcher.setName(researcher.getName());
            }
            if (researcher.getSpecialization() != null) {
                newResearcher.setSpecialization(researcher.getSpecialization());
            }
            if (researcher.getProjects() != null) {
                List<Project> projects = researcher.getProjects();
                for (Project project : projects) {
                    project.getResearchers().remove(newResearcher);
                }
                projectJpaRepository.saveAll(projects);

                List<Integer> newProjectIds = new ArrayList<>();
                for (Project project : researcher.getProjects()) {
                    newProjectIds.add(project.getId());
                }
                List<Project> newProjects = projectJpaRepository.findAllById(newProjectIds);
                if (newProjectIds.size() != newProjects.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                for (Project project : newProjects) {
                    project.getResearchers().add(newResearcher);
                }
                projectJpaRepository.saveAll(newProjects);
                newResearcher.setProjects(newProjects);
            }
            return researcherJpaRepository.save(newResearcher);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteResearcher(int id) {
        try {
            Researcher researcher = researcherJpaRepository.findById(id).get();

            List<Project> projects = researcher.getProjects();
            for (Project project : projects) {
                project.getResearchers().remove(researcher);
            }
            projectJpaRepository.saveAll(projects);

            researcherJpaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Project> getResearchProject(int id) {
        Researcher researcher = researcherJpaRepository.findById(id).get();
        List<Project> projects = researcher.getProjects();
        return projects;
    }
}