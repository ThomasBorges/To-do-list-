package br.com.thomasborges.todolist.controller;

import br.com.thomasborges.todolist.model.Lista;
import br.com.thomasborges.todolist.model.Usuario;
import br.com.thomasborges.todolist.repository.ListaRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/listas")
public class ListaController {

    private final ListaRepository repository;

    public ListaController(ListaRepository repository) {
        this.repository = repository;
    }


    // GET /usuarios/{usuarioId}/listas
    // GET http://localhost:8080/usuarios/1/listas
    @GetMapping
    public List<Lista> listar(@PathVariable Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    // GET /usuarios/{usuarioId}/listas/{listaId}
    // GET http://localhost:8080/usuarios/{usuarioId}/listas/{listaId}
    @GetMapping("/{id}")
    public ResponseEntity<Lista> getLista(
            @PathVariable Long usuarioId,
            @PathVariable Long listaId) {
        var resultado = repository.findByUsuarioIdAndId(usuarioId, listaId);
        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var lista = resultado.get();
        return ResponseEntity.ok(lista);
    }


    // POST /usuarios/{usuarioId}/listas
    // POST http://localhost:8080/usuarios/1/listas
    @PostMapping
    public ResponseEntity<Lista> criar(@PathVariable Long usuarioId,
                                       @RequestBody @Valid Lista lista) {
        lista.setUsuarioId(usuarioId);
        lista = repository.save(lista);
        return ResponseEntity.ok(lista);
    }

    // PUT /usuarios/{usuarioId}/listas/{listaId}
    // PUT http://localhost:8080/usuarios/1/listas/1
    @PutMapping("/{listaId}")
    public ResponseEntity<Lista> atualizar(
            @PathVariable Long usuarioId,
            @PathVariable Long listaId,
            @RequestBody @Valid Lista lista) {

        var resultado = repository.findByUsuarioIdAndId(usuarioId, listaId);
        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var listaEncontrada = resultado.get();
        listaEncontrada.setNome(lista.getNome());
        listaEncontrada.setDescricao(lista.getDescricao());
        listaEncontrada = repository.save(listaEncontrada);
        return ResponseEntity.ok(listaEncontrada);
    }

    @DeleteMapping("/{listaId}")
    public ResponseEntity<?> deletar(
            @PathVariable Long usuarioId,
            @PathVariable Long listaId) {
        repository.deleteByUsuarioIdAndId(usuarioId, listaId);
        return ResponseEntity.noContent().build();
    }

}
