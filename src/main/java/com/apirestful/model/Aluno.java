package com.apirestful.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String slug;
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    private Turma turma;

    public Aluno(String nome, String slug, String email, Turma turma) {
        this.nome = nome;
        this.slug = slug;
        this.email = email;
        this.turma = turma;
    }

    public Aluno() {

    }
}
