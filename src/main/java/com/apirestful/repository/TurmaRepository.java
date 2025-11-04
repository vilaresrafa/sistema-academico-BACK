package com.apirestful.repository;

import com.apirestful.model.Turma;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from Turma t where t.id = :id")
    Optional<Turma> recuperarTurmaPorIdComLock(@Param("id") Long id);

    @Query("select t from Turma t order by t.id")
    List<Turma> recuperarTurmas();

    @Query("select t from Turma t where t.id = :id")
    Optional<Turma> recuperarTurmaPorId(@Param("id") Long id);

    @Query (
            value = "select t from Turma t order by t.id",
            countQuery = "select count(t) from Turma t"
    )
    Page<Turma> recuperarProdutosComPaginacao(Pageable pageable);

    @Query("SELECT t FROM Turma t WHERE LOWER(t.nome) LIKE LOWER(CONCAT('%', :nome, '%')) ORDER BY t.id")
    List<Turma> buscarPorNome(@Param("nome") String nome);


}
