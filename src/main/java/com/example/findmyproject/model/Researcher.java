package com.example.findmyproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Data
@Entity
@Table(name = "researcher")
public class Researcher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String specialization;
    @ManyToMany
    @JoinTable(name = "researcher_project", joinColumns = @JoinColumn(name = "researcherid"), inverseJoinColumns = @JoinColumn(name = "projectid"))
    @JsonIgnoreProperties("researchers")
    private List<Project> projects = new ArrayList<>();
}