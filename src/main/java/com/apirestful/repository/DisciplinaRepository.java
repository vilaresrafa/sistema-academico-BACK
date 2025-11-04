package com.apirestful.repository;

import com.apirestful.model.Disciplina;
import com.apirestful.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
}
