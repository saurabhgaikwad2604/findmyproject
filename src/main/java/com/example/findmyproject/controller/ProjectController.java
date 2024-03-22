package com.example.findmyproject.controller;

import com.example.findmyproject.model.*;
import com.example.findmyproject.service.ProjectJpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class ProjectController {
	@Autowired
	public ProjectJpaService projectService;

	@GetMapping("/researchers/projects")
	public ArrayList<Project> getProjects() {
		return projectService.getProjects();
	}

	@GetMapping("/researchers/projects/{projectId}")
	public Project getProject(@PathVariable("projectId") int id) {
		return projectService.getProject(id);
	}

	@PostMapping("/researchers/projects")
	public Project addProject(@RequestBody Project project) {
		return projectService.addProject(project);
	}

	@PutMapping("/researchers/projects/{projectId}")
	public Project updateProject(@PathVariable("projectId") int id, @RequestBody Project project) {
		return projectService.updateProject(id, project);
	}

	@DeleteMapping("/researchers/projects/{projectId}")
	public void deleteProject(@PathVariable("projectId") int id) {
		projectService.deleteProject(id);
	}

	@GetMapping("/projects/{projectId}/researchers")
	public List<Researcher> getProjectResearcher(@PathVariable("projectId") int id) {
		return projectService.getProjectResearcher(id);
	}
}