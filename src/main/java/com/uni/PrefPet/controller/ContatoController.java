package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Contato;
import com.uni.PrefPet.service.ContatoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @PostMapping("/save")
    public ResponseEntity<Contato> save(@RequestBody @Valid Contato contato) {
        try {
            var result = contatoService.save(contato);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Contato> findById(@PathVariable Long id) {
        try {
            var result = contatoService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Contato>> findAll() {
        try {
            var result = contatoService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Contato> update(@PathVariable Long id, @RequestBody Contato contato) {
        try {
            var updated = contatoService.update(id, contato);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            contatoService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/existsByEmail/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        try {
            boolean exists = contatoService.existsByEmail(email);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<List<Contato>> findByEmail(@PathVariable String email) {
        try {
            List<Contato> contatos = contatoService.findByEmail(email);
            return new ResponseEntity<>(contatos, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




    @GetMapping("/findByNomeOrgao/{nomeOrgao}")
    public ResponseEntity<List<Contato>> findByNomeOrgaoContainingIgnoreCase(@PathVariable String nomeOrgao) {
        try {
            var result = contatoService.findByNomeOrgaoContainingIgnoreCase(nomeOrgao);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByTelefone/{telefone}")
    public ResponseEntity<List<Contato>> findByTelefone(@PathVariable String telefone) {
        try {
            var result = contatoService.findByTelefone(telefone);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
