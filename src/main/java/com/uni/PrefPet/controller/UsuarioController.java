package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Usuarios.UsuarioComum;
import com.uni.PrefPet.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/save")
    public ResponseEntity<UsuarioComum> save(@RequestBody @Valid UsuarioComum usuario) {
        try {
            var result = usuarioService.save(usuario);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null,
                    HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<UsuarioComum> findById(@PathVariable Long id) {
        try {
            var result = usuarioService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            usuarioService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioComum> update(@PathVariable Long id, @RequestBody UsuarioComum usuario) {
        try {
            var updatedUsuario = usuarioService.update(id, usuario);
            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<UsuarioComum>> findAll() {
        try {
            var result = usuarioService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/existsByCPF")
    public ResponseEntity<Boolean> existsByCPF(@RequestParam String cpf) {
        try {
            boolean exists = usuarioService.existsByCPF(cpf);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/existsByEmail")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        try {
            boolean exists = usuarioService.existsByEmail(email);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByNome")
    public ResponseEntity<List<UsuarioComum>> findByNome(@RequestParam String nome) {
        try {
            List<UsuarioComum> usuario = usuarioService.findByNome(nome);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByCPF")
    public ResponseEntity<List<UsuarioComum>> findByCPF(@RequestParam String cpf) {
        try {
            List<UsuarioComum> usuario = usuarioService.findByCPF(cpf);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByTelefone")
    public ResponseEntity<List<UsuarioComum>> findByTelefone(@RequestParam String telefone) {
        try {
            List<UsuarioComum> usuario = usuarioService.findByTelefone(telefone);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<List<UsuarioComum>> findByEmail(@RequestParam String email) {
        try {
            List<UsuarioComum> usuario = usuarioService.findByEmail(email);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}

