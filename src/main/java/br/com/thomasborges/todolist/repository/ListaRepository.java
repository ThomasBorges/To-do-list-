package br.com.thomasborges.todolist.repository;

import br.com.thomasborges.todolist.model.Lista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListaRepository extends JpaRepository<Lista, Long> {

    /**
     * Select * from lista where usuario_id = :usuarioId
     */
    List<Lista> findByUsuarioId(Long usuarioId);


    /**
     * Select * from lista where usuario_id = :usuarioId and id =:listaId
     */
    Optional<Lista> findByUsuarioIdAndId(Long usuarioId, Long listaId);

    /**
     * DELETE FROM lista where usuario_id = :usuarioId and id =:listaId
     */
    void deleteByUsuarioIdAndId(Long usuarioId, Long listaId);

    boolean existsByIdAndUsuarioId(Long listaId, Long usuarioId);

}
