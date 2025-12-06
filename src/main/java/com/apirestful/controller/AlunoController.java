package com.apirestful.controller;

import com.apirestful.model.Aluno;
import com.apirestful.model.AlunoDTO;
import com.apirestful.model.ResultadoPaginado;
import com.apirestful.service.AlunoService;
import com.apirestful.validator.AlunoValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("alunos")
public class AlunoController {

    private final AlunoService alunoService;
    private final AlunoValidator alunoValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(alunoValidator);
    }

    // listar — requer pelo menos ROLE_USER (ou ADMIN)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public List<Aluno> recuperarAlunos() {
        return alunoService.recuperarAlunos();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public Aluno getById(@PathVariable Long id) {
        return alunoService.getById(id);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("dto/{idAluno}")
    public ResponseEntity<?> recuperarAlunoPorId(
            @PathVariable("idAluno") Long id ) {
        Aluno aluno = alunoService.recuperarAlunoPorId(id);
        AlunoDTO alunoDTO = new AlunoDTO(aluno.getId(), aluno.getNome());
        return ResponseEntity.ok(alunoDTO);
    }

    // criar — apenas ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Aluno aluno, BindingResult br) {
        if (br.hasErrors()) {
            return ResponseEntity.badRequest().body(br.getAllErrors());
        }
        Aluno saved = alunoService.create(aluno);
        return ResponseEntity.status(201).body(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Aluno update(@PathVariable Long id, @RequestBody Aluno aluno) {
        return alunoService.update(id, aluno);
    }

    // deletar — apenas ADMIN (frontend pode exibir botão para todos; backend vai retornar 403 para USER)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        alunoService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
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

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("turmas/{slugTurma}")
    public List<Aluno> recuperarAlunosPorSlugDaTurma(@PathVariable("slugTurma") String slugTurma) {
        return alunoService.recuperarAlunosPorSlugDaTurma(slugTurma);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/nao-inscritos")
    public List<Aluno> recuperarAlunosNaoInscritos(
            @RequestParam Long turmaId,
            @RequestParam(required = false) String nome
    ) {
        return alunoService.recuperarAlunosNaoInscritos(turmaId, nome);
    }
}
