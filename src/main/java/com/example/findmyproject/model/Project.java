package com.example.findmyproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import jakarta.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double budget;
    @ManyToMany(mappedBy = "projects")
    @JsonIgnoreProperties("projects")
    private List<Researcher> researchers = new ArrayList<>();
}