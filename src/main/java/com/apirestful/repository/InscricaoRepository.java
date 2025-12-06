package com.apirestful.repository;

import com.apirestful.model.Inscricao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    List<Inscricao> findByTurmaIdOrderByIdDesc(Long turmaId);

    @Transactional
    void deleteByAlunoId(Long alunoId);
}
