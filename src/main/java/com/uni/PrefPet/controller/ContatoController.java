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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<Contato> save(@RequestBody @Valid Contato contato) {
       
            var result = contatoService.save(contato);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
       
    }

    // acesso permit all no security config
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Contato> update(@PathVariable Long id, @RequestBody Contato contato) {
            var updated = contatoService.update(id, contato);
            return new ResponseEntity<>(updated, HttpStatus.OK);
       
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
            contatoService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','TUTOR','VETERINARIO', 'ENTIDADE')")
    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<List<Contato>> findByEmail(@PathVariable String email) {
        
            List<Contato> contatos = contatoService.findByEmail(email);
            return new ResponseEntity<>(contatos, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TUTOR','VETERINARIO', 'ENTIDADE')")
    @GetMapping("/findByNomeOrgao/{nomeOrgao}")
    public ResponseEntity<List<Contato>> findByNomeOrgaoContainingIgnoreCase(@PathVariable String nomeOrgao) {
        

            var result = contatoService.findByNomeOrgaoContainingIgnoreCase(nomeOrgao);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }
    
    @PreAuthorize("hasAnyAuthority('ADMIN','TUTOR','VETERINARIO', 'ENTIDADE')")
    @GetMapping("/findByTelefone/{telefone}")
    public ResponseEntity<List<Contato>> findByTelefone(@PathVariable String telefone) {
        
            var result = contatoService.findByTelefone(telefone);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
