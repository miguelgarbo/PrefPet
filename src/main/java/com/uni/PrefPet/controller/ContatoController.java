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
@CrossOrigin("*")

public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @PostMapping("/save")
    public ResponseEntity<Contato> save(@RequestBody @Valid Contato contato) {
       
            var result = contatoService.save(contato);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
       
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Contato> findById(@PathVariable Long id) {
            var result = contatoService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
      
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Contato>> findAll() {
        
            var result = contatoService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
       
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Contato> update(@PathVariable Long id, @RequestBody Contato contato) {
            var updated = contatoService.update(id, contato);
            return new ResponseEntity<>(updated, HttpStatus.OK);
       
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
            contatoService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       
    }

    @GetMapping("/existsByEmail/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        
            boolean exists = contatoService.existsByEmail(email);
            return new ResponseEntity<>(exists, HttpStatus.OK);
       
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<List<Contato>> findByEmail(@PathVariable String email) {
        
            List<Contato> contatos = contatoService.findByEmail(email);
            return new ResponseEntity<>(contatos, HttpStatus.OK);

    }




    @GetMapping("/findByNomeOrgao/{nomeOrgao}")
    public ResponseEntity<List<Contato>> findByNomeOrgaoContainingIgnoreCase(@PathVariable String nomeOrgao) {
        

            var result = contatoService.findByNomeOrgaoContainingIgnoreCase(nomeOrgao);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/findByTelefone/{telefone}")
    public ResponseEntity<List<Contato>> findByTelefone(@PathVariable String telefone) {
        
            var result = contatoService.findByTelefone(telefone);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
