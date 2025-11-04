package com.apirestful.service;

import com.apirestful.exception.EntidadeNaoEncontradaException;
import com.apirestful.model.Aluno;
import com.apirestful.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    //public AlunoService(AlunoRepository repository) {
    //    this.repository = repository;
    //}

    public List<Aluno> recuperarAlunos() {
        return alunoRepository.recuperarAlunos();
    }

    public Aluno getById(Long id) {
        return alunoRepository.findById(id).orElseThrow();
    }

    public Aluno create(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public Aluno update(Long id, Aluno aluno) {
        aluno.setId(id);
        return alunoRepository.save(aluno);
    }

    public void delete(Long id) {
        alunoRepository.deleteById(id);
    }

    public Aluno recuperarAlunoPorId(Long id) {
        return alunoRepository.recuperarAlunoPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Aluno com id = " + id + " não encontrado."));
    }

    public Page<Aluno> recuperarAlunosComPaginacao(Pageable pageable, String nome) {
        return alunoRepository.recuperarAlunosComPaginacao(pageable, "%" + nome + "%");
    }

    public List<Aluno> recuperarAlunosPorSlugDaTurma(String slugTurma) {
        return alunoRepository.recuperarAlunosPorSlugDaTurma(slugTurma);
    }

}
