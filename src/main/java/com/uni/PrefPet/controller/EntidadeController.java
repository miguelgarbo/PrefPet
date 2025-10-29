package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Enum.TipoEntidade;
import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.service.EntidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entidades")
@CrossOrigin("*")
public class EntidadeController {

    @Autowired
    private EntidadeService entidadeService;

    @PostMapping
    public ResponseEntity<Entidade> save(@RequestBody @Valid Entidade entidade) {
        
            var result = entidadeService.save(entidade);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Entidade> findById(@PathVariable Long id) {
        
            var result = entidadeService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
            entidadeService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Entidade> update(@PathVariable Long id, @RequestBody Entidade entidade) {
        
            var updatedEntidade = entidadeService.update(id, entidade);
            return new ResponseEntity<>(updatedEntidade, HttpStatus.OK);

    }
    @GetMapping("/findAll")
    public ResponseEntity<List<Entidade>> findAll() {
        
            var result = entidadeService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);

    }


    @GetMapping("/findByNome")
    public ResponseEntity<List<Entidade>> findByNome(@RequestParam String nome) {
        
            List<Entidade> entidade = entidadeService.findByNome(nome);
            return new ResponseEntity<>(entidade, HttpStatus.OK);

    }

    @GetMapping("/findByCPF")
    public ResponseEntity<Entidade> findByCPF(@RequestParam String cpf) {
        
            Entidade entidade = entidadeService.findByCPF(cpf);
            return new ResponseEntity<>(entidade, HttpStatus.OK);

    }

    @GetMapping("/findByTelefone")
    public ResponseEntity<List<Entidade>> findByTelefone(@RequestParam String telefone) {
        
            List<Entidade> entidade = entidadeService.findByTelefone(telefone);
            return new ResponseEntity<>(entidade, HttpStatus.OK);

    }

    @GetMapping("/findByEmail")
    public ResponseEntity<Entidade> findByEmail(@RequestParam String email) {
        
            var entidades = entidadeService.findByEmail(email);
            return new ResponseEntity<>(entidades, HttpStatus.OK);

    }

    @GetMapping("/findByCnpj")
    public ResponseEntity<Entidade> findByCnpj(@RequestParam String cnpj) {
        
            var entidade = entidadeService.findByCnpj(cnpj);
            return new ResponseEntity<>(entidade, HttpStatus.OK);

    }



    @GetMapping("/findByTipoEntidade")
    public ResponseEntity<List<Entidade>> findByTipoEntidade(@RequestParam TipoEntidade tipoEntidade) {
        
            var entidades = entidadeService.findByTipoEntidade(tipoEntidade);
            return new ResponseEntity<>(entidades, HttpStatus.OK);

    }



}
