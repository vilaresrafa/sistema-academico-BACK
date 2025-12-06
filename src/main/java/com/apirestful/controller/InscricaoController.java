package com.apirestful.controller;

import com.apirestful.model.Inscricao;
import com.apirestful.service.InscricaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {
    private final InscricaoService service;

    public InscricaoController(InscricaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Inscricao> listarPorTurma(@RequestParam Long turmaId) {
        return service.listarPorTurma(turmaId);
    }


    @PostMapping
    public Inscricao create(@RequestBody Inscricao inscricao) {
        return service.create(inscricao);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
