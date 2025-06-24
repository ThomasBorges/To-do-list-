package br.com.thomasborges.todolist.controller;

import br.com.thomasborges.todolist.dto.UsuarioRequest;
import br.com.thomasborges.todolist.dto.UsuarioResponse;
import br.com.thomasborges.todolist.model.Senha;
import br.com.thomasborges.todolist.model.Usuario;
import br.com.thomasborges.todolist.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    private static final Function<Usuario, UsuarioResponse> toResponse = (usuario) -> new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());

    // GET /usuarios
    // GET http://localhost:8080/usuarios
    @GetMapping
    public List<UsuarioResponse> getUsuarios() {
        return repository.findAll().stream().map(toResponse).toList();
    }

    // GET /usuarios/{id}
    // GET http://localhost:8080/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getUsuario(@PathVariable Long id) {
        var resultado = repository.findById(id);
        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var usuario = resultado.get();
        return ResponseEntity.ok(toResponse.apply(usuario));
    }

    // POST /usuarios
    // POST http://localhost:8080/usuarios
    @PostMapping
    public ResponseEntity<UsuarioResponse> criarUsuario(@RequestBody @Valid UsuarioRequest request) {
        var random = new Random(4);
        var salt  = BCrypt.gensalt(random.nextInt(31));
        var hash = BCrypt.hashpw(request.senha(), salt);
        var senha = new Senha(salt, hash);
        var novoUsuario = new Usuario(request.nome(), request.email(), senha);
        novoUsuario = repository.save(novoUsuario);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoUsuario.getId())
                .toUri();

        return ResponseEntity.created(location).body(toResponse.apply(novoUsuario));
    }

    // PUT /usuarios/{id}
    // PUT http://localhost:8080/usaurios/1234
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioRequest request) {

        var resultado = repository.findById(id);
        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var usuarioEncontrado = resultado.get();
        usuarioEncontrado.setEmail(request.email());
        usuarioEncontrado.setNome(request.nome());

        var senha = usuarioEncontrado.getSenha();
        var hash = BCrypt.hashpw(request.senha(), senha.getSalt());
        senha.setHashPassword(hash);

        usuarioEncontrado = repository.save(usuarioEncontrado);
        return ResponseEntity.ok(toResponse.apply(usuarioEncontrado));
    }


    // DELETE /usuarios/{id}
    // DELETE http://localhost:8080/usaurios/1234
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
