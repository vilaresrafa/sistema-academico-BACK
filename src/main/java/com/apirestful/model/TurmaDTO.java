package com.apirestful.model;

import java.util.List;

public record TurmaDTO(Long id, int ano, String periodo, Disciplina nome, Professor professor) {
}
