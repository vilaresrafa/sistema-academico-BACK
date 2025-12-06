package com.apirestful.controller;

import com.apirestful.model.Aluno;
import com.apirestful.model.AlunoDTO;
import com.apirestful.model.ResultadoPaginado;
import com.apirestful.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("alunos")
public class AlunoController {

    private final AlunoService alunoService;

    // public AlunoController(AlunoService service) {
    //    this.service = service;
    // }

    @GetMapping
    public List<Aluno> recuperarAlunos() {
        return alunoService.recuperarAlunos();
    }

    @GetMapping("/{id}")
    public Aluno getById(@PathVariable Long id) {
        return alunoService.getById(id);
    }

    @GetMapping("{idAluno}")
    public ResponseEntity<?> recuperarAlunoPorId(
            @PathVariable("idAluno") Long id ) {
            Aluno aluno = alunoService.recuperarAlunoPorId(id);
            AlunoDTO alunoDTO = new AlunoDTO(aluno.getId(), aluno.getNome());
            System.out.println(alunoDTO.id());
            return ResponseEntity.ok(alunoDTO);
    }

    @PostMapping
    public Aluno create(@RequestBody Aluno aluno) {
        return alunoService.create(aluno);
    }

    @PutMapping("/{id}")
    public Aluno update(@PathVariable Long id, @RequestBody Aluno aluno) {
        return alunoService.update(id, aluno);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        alunoService.delete(id);
    }

    @GetMapping("paginacao")
    public ResultadoPaginado<Aluno> recuperarAlunosComPaginacao(
            @RequestParam(name = "pagina", defaultValue = "0") int pagina,
            @RequestParam(name = "tamanho", defaultValue = "5") int tamanho,
            @RequestParam(name = "nome", defaultValue = "") String nome
    ) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<Aluno> page = alunoService.recuperarAlunosComPaginacao(pageable, nome);
        return new ResultadoPaginado<Aluno>(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getContent());
    }

    // http://localhost:8080/alunos/turmas/A320
    @GetMapping("turmas/{slugTurma}")
    public List<Aluno> recuperarAlunosPorSlugDaTurma(@PathVariable("slugTurma") String slugTurma) {
        return alunoService.recuperarAlunosPorSlugDaTurma(slugTurma);
    }

    @GetMapping("/nao-inscritos")
    public List<Aluno> recuperarAlunosNaoInscritos(
            @RequestParam Long turmaId,
            @RequestParam(required = false) String nome
    ) {
        return alunoService.recuperarAlunosNaoInscritos(turmaId, nome);
    }
}
