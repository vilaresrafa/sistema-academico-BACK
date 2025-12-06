package com.apirestful.service;

import com.apirestful.exception.EntidadeNaoEncontradaException;
import com.apirestful.model.Aluno;
import com.apirestful.model.Turma;
import com.apirestful.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TurmaService {
    private final TurmaRepository turmaRepository;

    public List<Turma> recuperarTurmas() {
        return turmaRepository.recuperarTurmas();
    }

    public Turma create(Turma turma) {
        return turmaRepository.save(turma);
    }

    public void delete(Long id) {
        turmaRepository.deleteById(id);
    }

    public Turma getById(Long id) {
        return turmaRepository.findById(id).orElseThrow();
    }

    public Turma recuperarTurmaPorId(Long id) {
        return turmaRepository.recuperarTurmaPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Turma com id = " + id + " não encontrada."));
    }

    public List<Turma> recuperarTurmasPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return turmaRepository.recuperarTurmas();
        }
        return turmaRepository.buscarPorNome(nome);
    }

    public List<Turma> recuperarTurmas(String nome, Long disciplinaId) {

        // nome + disciplinaId
        if (disciplinaId != null && nome != null && !nome.isBlank()) {
            return turmaRepository.buscarPorNomeEDisciplina(nome, disciplinaId);
        }

        // apenas disciplinaId
        if (disciplinaId != null) {
            return turmaRepository.buscarPorDisciplina(disciplinaId);
        }

        // apenas nome
        if (nome != null && !nome.isBlank()) {
            return turmaRepository.buscarPorNome(nome);
        }

        // nenhum filtro
        return turmaRepository.recuperarTurmas();
    }

}
