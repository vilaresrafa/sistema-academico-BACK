package com.apirestful.controller;

import com.apirestful.model.Disciplina;
import com.apirestful.service.DisciplinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    @GetMapping
    public List<Disciplina> recuperarDisciplinas(
            @RequestParam(required = false) String nome) {
        return disciplinaService.recuperarDisciplinasPorNome(nome);
    }

    @PostMapping
    public Disciplina create(@RequestBody Disciplina disciplina) {
        return disciplinaService.create(disciplina);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        disciplinaService.delete(id);
    }
}
