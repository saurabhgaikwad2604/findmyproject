package com.example.findmyproject.controller;

import com.example.findmyproject.model.*;
import com.example.findmyproject.service.ResearcherJpaService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class ResearcherController {
	@Autowired
	public ResearcherJpaService researcherService;

	@GetMapping("/researchers")
	public ArrayList<Researcher> getResearchers() {
		return researcherService.getResearchers();
	}

	@GetMapping("/researchers/{researcherId}")
	public Researcher getResearcher(@PathVariable("researcherId") int id) {
		return researcherService.getResearcher(id);
	}

	@PostMapping("/researchers")
	public Researcher addResearcher(@RequestBody Researcher researcher) {
		return researcherService.addResearcher(researcher);
	}

	@PutMapping("/researchers/{researcherId}")
	public Researcher updateResearcher(@PathVariable("researcherId") int id, @RequestBody Researcher researcher) {
		return researcherService.updateResearcher(id, researcher);
	}

	@DeleteMapping("/researchers/{researcherId}")
	public void deleteResearcher(@PathVariable("researcherId") int id) {
		researcherService.deleteResearcher(id);
	}

	@GetMapping("/researchers/{researcherId}/projects")
	public List<Project> getResearchProject(@PathVariable("researcherId") int id) {
		return researcherService.getResearchProject(id);
	}

}