package com.apirestful.service;

import com.apirestful.model.Inscricao;
import com.apirestful.repository.InscricaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscricaoService {
    private final InscricaoRepository repository;

    public InscricaoService(InscricaoRepository repository) {
        this.repository = repository;
    }

    public List<Inscricao> listarPorTurma(Long turmaId) {
        return repository.findByTurmaIdOrderByIdDesc(turmaId);
    }


    public Inscricao create(Inscricao inscricao) {
        return repository.save(inscricao);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
