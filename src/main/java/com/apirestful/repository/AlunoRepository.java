package com.apirestful.repository;

import com.apirestful.model.Aluno;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByNome(String nome);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Aluno p where p.id = :id")
    Optional<Aluno> recuperarAlunoPorIdComLock(@Param("id") Long id);

    @Query("select a from Aluno a order by a.id")
    List<Aluno> recuperarAlunos();

    //@Query("select a from Aluno a left outer join fetch p.categoria where a.id = :id")
    //Optional<Aluno> recuperarAlunoPorIdComCategoria(@Param("id") Long id);

    @Query("select a from Aluno a where a.id = :id")
    Optional<Aluno> recuperarAlunoPorId(@Param("id") Long id);

    @Query (
            value = "select a from Aluno a " +
                    "left outer join fetch a.turma " +
                    "where a.nome like :nome " +
                    "order by a.id",
            countQuery = "select count(a) from Aluno a " +
                    "where a.nome like :nome"
    )
    Page<Aluno> recuperarAlunosComPaginacao(Pageable pageable, @Param("nome") String nome);

    @Query("select a from Aluno a left outer join fetch a.turma t where t.slug = :slugTurma")
    List<Aluno> recuperarAlunosPorSlugDaTurma(@Param("slugTurma") String slugTurma);
}
