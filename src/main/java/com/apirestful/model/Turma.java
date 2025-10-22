package com.apirestful.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private int ano;
    private String periodo;
    private String slug;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @JsonIgnore
    @OneToMany(mappedBy = "turma")
    private List<Aluno> alunos;

    public Turma() {}

    public Turma(int ano, String nome, String slug,String periodo, Professor professor, Disciplina disciplina) {
        this.nome = nome;
        this.ano = ano;
        this.slug = slug;
        this.periodo = periodo;
        this.professor = professor;
        this.disciplina = disciplina;
        this.alunos = new ArrayList<>();
    }
}
