package com.apirestful;

import com.apirestful.model.*;
import com.apirestful.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class ApirestfulApplication implements CommandLineRunner {

    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final InscricaoRepository inscricaoRepository;
    private final DisciplinaRepository disciplinaRepository;

    public static void main(String[] args) {
        SpringApplication.run(ApirestfulApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Professores
        Professor p1 = new Professor("Carlos Silva", "carlos@universidade.com");
        Professor p2 = new Professor("Mariana Souza", "mariana@universidade.com");
        Professor p3 = new Professor("Fernando Almeida", "fernando@universidade.com");
        Professor p4 = new Professor("Juliana Torres", "juliana@universidade.com");
        professorRepository.save(p1);
        professorRepository.save(p2);
        professorRepository.save(p3);
        professorRepository.save(p4);

        // Disciplinas
        Disciplina d1 = new Disciplina("Desenvolvimento Web", 60);
        Disciplina d2 = new Disciplina("Banco de Dados", 30);
        Disciplina d3 = new Disciplina("Estruturas de Dados", 45);
        Disciplina d4 = new Disciplina("Redes de Computadores", 40);
        disciplinaRepository.save(d1);
        disciplinaRepository.save(d2);
        disciplinaRepository.save(d3);
        disciplinaRepository.save(d4);

        // Turmas
        Turma t1 = new Turma(2025, "A001", "a001", "1º semestre", p1, d2);
        Turma t2 = new Turma(2025, "A002", "a002", "2º semestre", p2, d1);
        Turma t3 = new Turma(2025, "B001", "b001", "1º semestre", p3, d3);
        Turma t4 = new Turma(2025, "B002", "b002", "2º semestre", p4, d4);
        turmaRepository.save(t1);
        turmaRepository.save(t2);
        turmaRepository.save(t3);
        turmaRepository.save(t4);

        // Alunos - Turma A001 (Banco de Dados)
        Aluno a1 = new Aluno("João", "joao", "joao@email.com", t1);
        Aluno a2 = new Aluno("Beatriz", "beatriz", "beatriz@email.com", t1);
        Aluno a3 = new Aluno("Lucas", "lucas", "lucas@email.com", t1);
        Aluno a4 = new Aluno("Fernanda", "fernanda", "fernanda@email.com", t1);
        alunoRepository.save(a1);
        alunoRepository.save(a2);
        alunoRepository.save(a3);
        alunoRepository.save(a4);

        // Alunos - Turma A002 (Desenvolvimento Web)
        Aluno a5 = new Aluno("Ana", "ana", "ana@email.com", t2);
        Aluno a6 = new Aluno("Rafael", "rafael", "rafael@email.com", t2);
        Aluno a7 = new Aluno("Clara", "clara", "clara@email.com", t2);
        Aluno a8 = new Aluno("Pedro", "pedro", "pedro@email.com", t2);
        alunoRepository.save(a5);
        alunoRepository.save(a6);
        alunoRepository.save(a7);
        alunoRepository.save(a8);

        // Alunos - Turma B001 (Estruturas de Dados)
        Aluno a9 = new Aluno("Gustavo", "gustavo", "gustavo@email.com", t3);
        Aluno a10 = new Aluno("Larissa", "larissa", "larissa@email.com", t3);
        Aluno a11 = new Aluno("Tiago", "tiago", "tiago@email.com", t3);
        Aluno a12 = new Aluno("Camila", "camila", "camila@email.com", t3);
        alunoRepository.save(a9);
        alunoRepository.save(a10);
        alunoRepository.save(a11);
        alunoRepository.save(a12);

        // Alunos - Turma B002 (Redes de Computadores)
        Aluno a13 = new Aluno("Marcos", "marcos", "marcos@email.com", t4);
        Aluno a14 = new Aluno("Patrícia", "patricia", "patricia@email.com", t4);
        Aluno a15 = new Aluno("Rodrigo", "rodrigo", "rodrigo@email.com", t4);
        Aluno a16 = new Aluno("Isabela", "isabela", "isabela@email.com", t4);
        alunoRepository.save(a13);
        alunoRepository.save(a14);
        alunoRepository.save(a15);
        alunoRepository.save(a16);

        // Inscrições
        Inscricao i1 = new Inscricao(LocalDateTime.now(), a1, t1);
        Inscricao i2 = new Inscricao(LocalDateTime.now(), a2, t2);
        inscricaoRepository.save(i1);
        inscricaoRepository.save(i2);
    }
}
