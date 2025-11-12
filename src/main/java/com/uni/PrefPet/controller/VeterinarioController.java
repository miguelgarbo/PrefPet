package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Usuarios.Veterinario;
import com.uni.PrefPet.service.VeterinarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veterinarios")
@CrossOrigin("*")
public class VeterinarioController {

    @Autowired
    private VeterinarioService veterinarioService;

    @PostMapping
    public ResponseEntity<Veterinario> save(@RequestBody @Valid Veterinario veterinario) {
            var result = veterinarioService.save(veterinario);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Veterinario> findById(@PathVariable Long id) {

            var result = veterinarioService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
            var mensagem = veterinarioService.delete(id);
            return new ResponseEntity<>(mensagem,HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veterinario> update(@PathVariable Long id, @RequestBody Veterinario veterinario) {
            var updatedVeterinario = veterinarioService.update(id, veterinario);
            return new ResponseEntity<>(updatedVeterinario, HttpStatus.OK);

    }
    @GetMapping("/findAll")
    public ResponseEntity<List<Veterinario>> findAll() {
            var result = veterinarioService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);

    }


    @GetMapping("/findByNome")
    public ResponseEntity<List<Veterinario>> findByNome(@RequestParam String nome) {
            List<Veterinario> veterinario = veterinarioService.findByNome(nome);
            return new ResponseEntity<>(veterinario, HttpStatus.OK);

    }

    @GetMapping("/findByCPF")
    public ResponseEntity<Veterinario> findByCPF(@RequestParam String cpf) {
            var veterinario = veterinarioService.findByCPF(cpf);
            return new ResponseEntity<>(veterinario, HttpStatus.OK);

    }

    @GetMapping("/findByEmail")
    public ResponseEntity<Veterinario> findByEmail(@RequestParam String email) {
            var veterinario = veterinarioService.findByEmail(email);
            return new ResponseEntity<>(veterinario, HttpStatus.OK);

    }

}
