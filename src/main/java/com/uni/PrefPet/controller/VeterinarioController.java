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
        try {
            var result = veterinarioService.save(veterinario);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null,
                    HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<Veterinario> findById(@PathVariable Long id) {
        try {
            var result = veterinarioService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            veterinarioService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Veterinario> update(@PathVariable Long id, @RequestBody Veterinario veterinario) {
        try {
            var updatedVeterinario = veterinarioService.update(id, veterinario);
            return new ResponseEntity<>(updatedVeterinario, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<Veterinario>> findAll() {
        try {
            var result = veterinarioService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/existsByCPF")
    public ResponseEntity<Boolean> existsByCPF(@RequestParam String cpf) {
        try {
            boolean exists = veterinarioService.existsByCPF(cpf);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/existsByEmail")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        try {
            boolean exists = veterinarioService.existsByEmail(email);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByNome")
    public ResponseEntity<List<Veterinario>> findByNome(@RequestParam String nome) {
        try {
            List<Veterinario> veterinario = veterinarioService.findByNome(nome);
            return new ResponseEntity<>(veterinario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByCPF")
    public ResponseEntity<List<Veterinario>> findByCPF(@RequestParam String cpf) {
        try {
            List<Veterinario> veterinario = veterinarioService.findByCPF(cpf);
            return new ResponseEntity<>(veterinario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByTelefone")
    public ResponseEntity<List<Veterinario>> findByTelefone(@RequestParam String telefone) {
        try {
            List<Veterinario> veterinario = veterinarioService.findByTelefone(telefone);
            return new ResponseEntity<>(veterinario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<List<Veterinario>> findByEmail(@RequestParam String email) {
        try {
            var veterinarios = veterinarioService.findByEmail(email);
            return new ResponseEntity<>(veterinarios, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByCnpj")
    public ResponseEntity<Veterinario> findByCnpj(@RequestParam String cnpj) {
        try {
            var veterinario = veterinarioService.findByCnpj(cnpj);
            return new ResponseEntity<>(veterinario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
