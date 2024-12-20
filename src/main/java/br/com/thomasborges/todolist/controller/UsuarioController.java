package br.com.thomasborges.todolist.controller;

import br.com.thomasborges.todolist.model.Usuario;
import br.com.thomasborges.todolist.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }


    // GET /usuarios
    // GET http://localhost:8080/usuarios
    @GetMapping
    public ResponseEntity<Set<Usuario>> getUsuarios() {
        return ResponseEntity.ok(repository.getUsuarios());
    }

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody @Valid Usuario usuario) {
        repository.adicionarUsuario(usuario);
        return ResponseEntity.ok().build();
    }

}
