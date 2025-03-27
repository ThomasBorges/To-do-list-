package br.com.thomasborges.todolist.repository;

import br.com.thomasborges.todolist.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {


    /**
     * SELECT t.*
     * FROM tarefa t
     * JOIN lista l on t.lista_id = l.id
     * WHERE t.lista_id =: listaId and l.usuario_id = :usuarioId
     */
    List<Tarefa> findAllByListaIdAndLista_UsuarioId(Long listaId, Long usuarioId);

    /**
     * SELECT t.*
     * FROM tarefa t
     * JOIN lista l on t.lista_id = l.id
     * WHERE t.lista_id =: listaId and l.usuario_id = :usuarioId and t.id =:tarefaId
     */
    Optional<Tarefa> findByListaIdAndLista_UsuarioIdAndId(Long listaId, Long usuarioId, Long tarefaId);
}
