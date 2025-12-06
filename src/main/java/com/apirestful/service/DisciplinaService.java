package com.apirestful.service;

import com.apirestful.exception.EntidadeNaoEncontradaException;
import com.apirestful.model.Disciplina;
import com.apirestful.repository.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DisciplinaService {
    private final DisciplinaRepository disciplinaRepository;

    public List<Disciplina> recuperarDisciplinas() {
        return disciplinaRepository.recuperarDisciplinas();
    }

    public Disciplina create(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public void delete(Long id) {
        disciplinaRepository.deleteById(id);
    }

    public List<Disciplina> recuperarDisciplinasPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return disciplinaRepository.recuperarDisciplinas();
        }
        return disciplinaRepository.buscarPorNome(nome);
    }

}
