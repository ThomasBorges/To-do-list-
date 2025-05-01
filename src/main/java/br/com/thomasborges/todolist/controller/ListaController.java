package br.com.thomasborges.todolist.controller;

import br.com.thomasborges.todolist.dto.ListaRequest;
import br.com.thomasborges.todolist.dto.ListaResponse;
import br.com.thomasborges.todolist.model.Lista;
import br.com.thomasborges.todolist.model.Usuario;
import br.com.thomasborges.todolist.repository.ListaRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/usuarios/{usuarioId}/listas")
public class ListaController {

    private final ListaRepository repository;

    public ListaController(ListaRepository repository) {
        this.repository = repository;
    }


    public static final Function<Lista,ListaResponse> toResponse = (lista) -> new ListaResponse(lista.getId(), lista.getNome(), lista.getDescricao());



    // GET /usuarios/{usuarioId}/listas
    // GET http://localhost:8080/usuarios/1/listas
    @GetMapping
    public List<ListaResponse> listar(@PathVariable Long usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream().map(toResponse).toList();
    }

    // GET /usuarios/{usuarioId}/listas/{listaId}
    // GET http://localhost:8080/usuarios/{usuarioId}/listas/{listaId}
    @GetMapping("/{listaId}")
    public ResponseEntity<ListaResponse> getLista(
            @PathVariable Long usuarioId,
            @PathVariable Long listaId) {
        return repository.findByUsuarioIdAndId(usuarioId, listaId)
                .map(toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // POST /usuarios/{usuarioId}/listas
    // POST http://localhost:8080/usuarios/1/listas
    @PostMapping
    public ResponseEntity<ListaResponse> criar(
            @PathVariable Long usuarioId,
            @RequestBody @Valid ListaRequest request) {

        var lista = new Lista(request.nome(), request.descricao());
        lista.setUsuarioId(usuarioId);
        lista = repository.save(lista);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(lista.getId())
                .toUri();

        var response = toResponse.apply(lista);
        return ResponseEntity.created(location).body(response);
    }

    // PUT /usuarios/{usuarioId}/listas/{listaId}
    // PUT http://localhost:8080/usuarios/1/listas/1
    @PutMapping("/{listaId}")
    public ResponseEntity<ListaResponse> atualizar(
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
        var response = toResponse.apply(listaEncontrada);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{listaId}")
    public ResponseEntity<?> deletar(
            @PathVariable Long usuarioId,
            @PathVariable Long listaId) {
        repository.deleteByUsuarioIdAndId(usuarioId, listaId);
        return ResponseEntity.noContent().build();
    }

}
