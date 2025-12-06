package com.apirestful.controller;

import com.apirestful.model.*;
import com.apirestful.service.TurmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("turmas")
public class TurmaController {

    private final TurmaService turmaService;

    @GetMapping
    public List<Turma> recuperarTurmas(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long disciplinaId
    ) {
        return turmaService.recuperarTurmas(nome, disciplinaId);
    }
    // @GetMapping("/{id}")
    // public Turma getById(@PathVariable Long id) {
    //    return turmaService.getById(id);
    // }

    @GetMapping("{idTurma}")
    public ResponseEntity<?> recuperarTurmaPorId(
            @PathVariable("idTurma") Long id ) {
        Turma turma = turmaService.recuperarTurmaPorId(id);
        TurmaDTO turmaDTO = new TurmaDTO(turma.getId(), turma.getAno(), turma.getPeriodo(), turma.getDisciplina(), turma.getProfessor()) ;
        System.out.println(turmaDTO.id());
        return ResponseEntity.ok(turmaDTO);
    }

    @PostMapping
    public Turma create(@RequestBody Turma turma) {
        return turmaService.create(turma);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        turmaService.delete(id);
    }
}
