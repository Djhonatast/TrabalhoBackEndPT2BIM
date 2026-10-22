package org.example.apicursos.controller;

import org.example.apicursos.model.Curso;
import org.example.apicursos.repository.CursoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoRepository repository;

    public CursoController(CursoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Curso> cadastrarCurso(@RequestBody Curso curso) {
        curso.setId(null); // garante que é um INSERT e que o banco gera o ID
        return ResponseEntity.ok(repository.save(curso));
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listarCursos(
            @RequestParam(required = false) String tipoCurso,
            @RequestParam(required = false) String modalidade,
            @RequestParam(required = false) String periodo) {

        return ResponseEntity.ok(repository.filtrar(tipoCurso, modalidade, periodo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizarCurso(@PathVariable Integer id,
                                                @RequestBody Curso cursoAtualizado) {
        return repository.findById(id)
                .map(curso -> {
                    curso.setNome(cursoAtualizado.getNome());
                    curso.setDescricao(cursoAtualizado.getDescricao());
                    curso.setCargaHoraria(cursoAtualizado.getCargaHoraria());
                    curso.setTipoCurso(cursoAtualizado.getTipoCurso());
                    curso.setModalidade(cursoAtualizado.getModalidade());
                    curso.setDuracao(cursoAtualizado.getDuracao());
                    curso.setPeriodo(cursoAtualizado.getPeriodo());
                    return ResponseEntity.ok(repository.save(curso));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCurso(@PathVariable Integer id) {
        return repository.findById(id)
                .map(curso -> {
                    repository.delete(curso);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}