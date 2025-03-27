package br.com.thomasborges.todolist.controller;

import br.com.thomasborges.todolist.model.Tarefa;
import br.com.thomasborges.todolist.repository.TarefaRepository;
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

        var resultado = repository.findByListaIdAndLista_UsuarioIdAndId(listaId, usuarioId, tarefaId);
        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var tarefa = resultado.get();
        return ResponseEntity.ok(tarefa);
    }


}
