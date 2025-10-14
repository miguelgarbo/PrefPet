package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Publicacao;
import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.service.PublicacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/publicacao")
@CrossOrigin("*")
public class PublicacaoController {
    
    @Autowired
    private PublicacaoService publicacaoService;

    @PostMapping
    public ResponseEntity<Publicacao> save(@RequestBody @Valid Publicacao publicacao) {
        
            var result = publicacaoService.save(publicacao);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Publicacao> findById(@PathVariable Long id) {
        
            var result = publicacaoService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
            publicacaoService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @PutMapping("{id}")
    public ResponseEntity<Publicacao> update(@PathVariable Long id, @RequestBody Publicacao publicacao) {
        
            var updatedPublicacao = publicacaoService.update(id, publicacao);
            return new ResponseEntity<>(updatedPublicacao, HttpStatus.OK);

    }
    @GetMapping("/findAll")
    public ResponseEntity<List<Publicacao>> findAll() {
        
            var result = publicacaoService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/byEntidade")
    public ResponseEntity<List<Publicacao>> findByEntidade(@RequestParam Entidade entidade) {
        
            var result = publicacaoService.findByEntidade(entidade);
            return new ResponseEntity<>(result, HttpStatus.OK);
       }

    @GetMapping("/byEntidadeNome")
    public ResponseEntity<List<Publicacao>> findByEntidadeNome(@RequestParam String nomeEntidade) {
        
            var result = publicacaoService.findByEntidadeNome(nomeEntidade);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }



    @GetMapping("/byTipoPublicacao")
    public ResponseEntity<List<Publicacao>> findByTipoPublicacao(@RequestParam String tipoPublicacao) {
        
            var result = publicacaoService.findByTipoPublicacao(tipoPublicacao);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @GetMapping("/byDescricao")
    public ResponseEntity<List<Publicacao>> findByDescricao(@RequestParam String descricao) {
        
            var result = publicacaoService.findByDescricao(descricao);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }


}
