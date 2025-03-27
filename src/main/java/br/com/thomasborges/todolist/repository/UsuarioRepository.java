package br.com.thomasborges.todolist.repository;

import br.com.thomasborges.todolist.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
