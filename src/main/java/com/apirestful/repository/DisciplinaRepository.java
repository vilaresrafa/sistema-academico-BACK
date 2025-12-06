package com.apirestful.repository;

import com.apirestful.model.Disciplina;
import com.apirestful.model.Disciplina;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    @Query("select d from Disciplina d order by d.id")
    List<Disciplina> recuperarDisciplinas();

    @Query("SELECT d FROM Disciplina d WHERE LOWER(d.nome) LIKE LOWER(CONCAT('%', :nome, '%')) ORDER BY d.id")
    List<Disciplina> buscarPorNome(@Param("nome") String nome);


}
