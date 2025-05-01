package br.com.thomasborges.todolist.controller;

import br.com.thomasborges.todolist.dto.TarefaRequest;
import br.com.thomasborges.todolist.dto.TarefaResponse;
import br.com.thomasborges.todolist.model.Tarefa;
import br.com.thomasborges.todolist.repository.ListaRepository;
import br.com.thomasborges.todolist.repository.TarefaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@RestController
@RequestMapping("/usuarios/{usuarioId}/listas/{listaId}/tarefas")
public class TarefaController {

    private final TarefaRepository repository;
    private final ListaRepository listaRepository;

    public TarefaController(TarefaRepository repository, ListaRepository listaRepository) {
        this.repository = repository;
        this.listaRepository = listaRepository;
    }

    private static final Function<Tarefa, TarefaResponse> toResponse = (tarefa) -> new TarefaResponse(tarefa.getId(), tarefa.getNome(), tarefa.getDescricao(), tarefa.getStatus());

    @GetMapping
    public List<TarefaResponse> listar(
            @PathVariable Long usuarioId,
            @PathVariable Long listaId,
            @RequestParam(required = false, defaultValue = "TODO") Set<Tarefa.Status> status) {
        return repository.findAllByListaIdAndLista_UsuarioIdAndStatusIn(listaId, usuarioId, status)
                .stream().map(toResponse).toList();
    }

    @GetMapping("/{tarefaId}")
    public ResponseEntity<TarefaResponse> buscar(
            @PathVariable Long usuarioId,
            @PathVariable Long listaId,
            @PathVariable Long tarefaId) {

        return repository.findByListaIdAndLista_UsuarioIdAndId(listaId, usuarioId, tarefaId)
                .map(toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TarefaResponse> criar(
            @PathVariable Long usuarioId,
            @PathVariable Long listaId,
            @RequestBody @Valid TarefaRequest request) {

        // SUGESTAO DEEP
        // Verifica se a lista pertence ao usuário
        if (!listaRepository.existsByIdAndUsuarioId(listaId, usuarioId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista não encontrada para este usuário");
        }

        var tarefa = new Tarefa(request.nome(), request.descricao());
        tarefa.setListaId(listaId);
        Tarefa tarefaSalva = repository.save(tarefa);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tarefaSalva.getId())
                .toUri();

        var response = toResponse.apply(tarefa);
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{tarefaId}/")
    public ResponseEntity<TarefaResponse> atualizar(
            @PathVariable Long usuarioId,
            @PathVariable Long listaId,
            @PathVariable Long tarefaId,
            @RequestBody @Valid TarefaRequest tarefaAtualizada) {

        return repository.findByListaIdAndLista_UsuarioIdAndId(listaId, usuarioId, tarefaId)
                .map(tarefaExistente -> {
                    tarefaExistente.setNome(tarefaAtualizada.nome());
                    tarefaExistente.setDescricao(tarefaAtualizada.descricao());
                    return repository.save(tarefaExistente);
                })
                .map(toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{tarefaId}/concluir")
    public ResponseEntity<TarefaResponse> concluir(
            @PathVariable Long usuarioId,
            @PathVariable Long listaId,
            @PathVariable Long tarefaId) {
        return repository.findByListaIdAndLista_UsuarioIdAndId(listaId, usuarioId, tarefaId)
                .map(tarefaExistente -> {
                    tarefaExistente.concluir();
                    return repository.save(tarefaExistente);
                })
                .map(toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{tarefaId}/desfazer")
    public ResponseEntity<TarefaResponse> desfazer(
            @PathVariable Long usuarioId,
            @PathVariable Long listaId,
            @PathVariable Long tarefaId) {
        return repository.findByListaIdAndLista_UsuarioIdAndId(listaId, usuarioId, tarefaId)
                .map(tarefaExistente -> {
                    tarefaExistente.desfazer();
                    return repository.save(tarefaExistente);
                })
                .map(toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{tarefaId}")
    public ResponseEntity<?> deletar(
            @PathVariable Long usuarioId,
            @PathVariable Long listaId,
            @PathVariable Long tarefaId) {
        repository.deleteByIdAndListaIdAndLista_UsuarioId(tarefaId, listaId, usuarioId);
        return ResponseEntity.noContent().build();
    }

}
