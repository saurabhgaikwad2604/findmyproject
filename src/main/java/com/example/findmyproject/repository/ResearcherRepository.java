package com.example.findmyproject.repository;

import com.example.findmyproject.model.*;
import java.util.*;

public interface ResearcherRepository {
    ArrayList<Researcher> getResearchers();

    Researcher getResearcher(int id);

    Researcher addResearcher(Researcher researcher);

    Researcher updateResearcher(int id, Researcher researcher);

    void deleteResearcher(int id);

    List<Project> getResearchProject(int id);
}