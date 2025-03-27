package br.com.thomasborges.todolist.controller;

import br.com.thomasborges.todolist.model.Tarefa;
import br.com.thomasborges.todolist.repository.TarefaRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/listas/{listaId}/tarefas")
public class TarefaController {

    private final TarefaRepository repository;

    public TarefaController(TarefaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Tarefa> listar(
                            @PathVariable Long usuarioId,
                            @PathVariable Long listaId) {
        return repository.findAllByListaIdAndLista_UsuarioId(listaId, usuarioId);
    }

    @GetMapping("/{tarefaId}")
    public ResponseEntity<Tarefa> buscar(
            @PathVariable Long usuarioId,
            @PathVariable Long listaId,
            @PathVariable Long tarefaId) {

        return repository.findByListaIdAndLista_UsuarioIdAndId(listaId, usuarioId, tarefaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/{tarefaId}")
    public ResponseEntity<Tarefa> criar (
            @PathVariable Long usuarioId,
            @PathVariable Long listaId,
            @RequestBody @Valid Tarefa tarefa) {

        // SUGESTAO DEEP
        // Verifica se a lista pertence ao usuário
        //        if (!repository.existsByListaIdAndLista_UsuarioId(listaId, usuarioId)) {
        //            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista não encontrada para este usuário");
        //        }
        //
        //        tarefa.setListaId(listaId);
        //        Tarefa tarefaSalva = repository.save(tarefa);
        //
        //        URI location = ServletUriComponentsBuilder
        //                .fromCurrentRequest()
        //                .path("/{id}")
        //                .buildAndExpand(tarefaSalva.getId())
        //                .toUri();
        //
        //        return ResponseEntity.created(location).body(tarefaSalva);
        //    }

        tarefa.setListaId(listaId);
        tarefa = repository.save(tarefa);
        return ResponseEntity.ok(tarefa);
    }

    @PutMapping
    public ResponseEntity<Tarefa> atualizar(
            @PathVariable Long usuarioId,
            @PathVariable Long listaId,
            @PathVariable Long tarefaId,
            @RequestBody @Valid Tarefa tarefaAtualizada) {

        return repository.findByListaIdAndLista_UsuarioIdAndId(listaId, usuarioId, tarefaId)
                .map(tarefaExistente -> {
                    tarefaExistente.setNome(tarefaAtualizada.getNome());
                    tarefaExistente.setDescricao(tarefaAtualizada.getDescricao());
                    return ResponseEntity.ok(repository.save(tarefaExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{tarefaId}")
    public ResponseEntity<?> deletar(
            @PathVariable Long usuarioId,
            @PathVariable Long listaId,
            @PathVariable Long tarefaId) {
        repository.deleteById(tarefaId);
        return ResponseEntity.noContent().build();
    }

}
