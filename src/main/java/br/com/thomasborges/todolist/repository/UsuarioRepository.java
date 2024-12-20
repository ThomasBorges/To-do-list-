package br.com.thomasborges.todolist.repository;

import br.com.thomasborges.todolist.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class UsuarioRepository {

    private final Set<Usuario> usuarios = new HashSet<>();

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void adicionarUsuario(Usuario usuario) {
        if (usuarios.contains(usuario)) {
            throw new IllegalArgumentException("Usuário já cadastrado");
        }
        usuarios.add(usuario);
    }

}
