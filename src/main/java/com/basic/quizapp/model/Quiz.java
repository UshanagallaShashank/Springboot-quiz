package com.basic.quizapp.model;
import com.basic.quizapp.model.Questions;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;

    @ManyToMany
    private List<Questions> questions;
}
