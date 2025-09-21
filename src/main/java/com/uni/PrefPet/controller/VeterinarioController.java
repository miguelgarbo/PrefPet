package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Usuarios.Veterinario;
import com.uni.PrefPet.service.VeterinarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veterinarios")
@CrossOrigin("*")
public class VeterinarioController {

    @Autowired
    private VeterinarioService veterinarioService;

    @PostMapping("/save")
    public ResponseEntity<Veterinario> save(@RequestBody @Valid Veterinario veterinario) {
            var result = veterinarioService.save(veterinario);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<Veterinario> findById(@PathVariable Long id) {

            var result = veterinarioService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
            veterinarioService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Veterinario> update(@PathVariable Long id, @RequestBody Veterinario veterinario) {
            var updatedVeterinario = veterinarioService.update(id, veterinario);
            return new ResponseEntity<>(updatedVeterinario, HttpStatus.OK);

    }
    @GetMapping("/findAll")
    public ResponseEntity<List<Veterinario>> findAll() {
            var result = veterinarioService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/existsByCPF")
    public ResponseEntity<Boolean> existsByCPF(@RequestParam String cpf) {
            boolean exists = veterinarioService.existsByCPF(cpf);
            return new ResponseEntity<>(exists, HttpStatus.OK);

    }

    @GetMapping("/existsByEmail")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
            boolean exists = veterinarioService.existsByEmail(email);
            return new ResponseEntity<>(exists, HttpStatus.OK);

    }

    @GetMapping("/findByNome")
    public ResponseEntity<List<Veterinario>> findByNome(@RequestParam String nome) {
            List<Veterinario> veterinario = veterinarioService.findByNome(nome);
            return new ResponseEntity<>(veterinario, HttpStatus.OK);

    }

    @GetMapping("/findByCPF")
    public ResponseEntity<List<Veterinario>> findByCPF(@RequestParam String cpf) {
            List<Veterinario> veterinario = veterinarioService.findByCPF(cpf);
            return new ResponseEntity<>(veterinario, HttpStatus.OK);

    }

    @GetMapping("/findByTelefone")
    public ResponseEntity<List<Veterinario>> findByTelefone(@RequestParam String telefone) {
            List<Veterinario> veterinario = veterinarioService.findByTelefone(telefone);
            return new ResponseEntity<>(veterinario, HttpStatus.OK);

    }

    @GetMapping("/findByEmail")
    public ResponseEntity<List<Veterinario>> findByEmail(@RequestParam String email) {
            var veterinarios = veterinarioService.findByEmail(email);
            return new ResponseEntity<>(veterinarios, HttpStatus.OK);

    }

    @GetMapping("/findByCnpj")
    public ResponseEntity<Veterinario> findByCnpj(@RequestParam String cnpj) {
        var veterinario = veterinarioService.findByCnpj(cnpj);
        return new ResponseEntity<>(veterinario, HttpStatus.OK);
    }
}
