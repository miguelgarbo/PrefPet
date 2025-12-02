package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Publicacao;
import com.uni.PrefPet.service.PublicacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/publicacao")
@CrossOrigin("*")
public class PublicacaoController {
    
    @Autowired
    private PublicacaoService publicacaoService;

    @PreAuthorize("hasAnyAuthority('ENTIDADE')")
    @PostMapping
    public ResponseEntity<Publicacao> save(@RequestBody @Valid Publicacao publicacao) {
            var result = publicacaoService.save(publicacao);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('TUTOR','ADMIN', 'ENTIDADE')")
    @GetMapping("/{id}")
    public ResponseEntity<Publicacao> findById(@PathVariable Long id) {
            var result = publicacaoService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ENTIDADE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        
            publicacaoService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ENTIDADE')")
    @PutMapping("{id}")
    public ResponseEntity<Publicacao> update(@PathVariable Long id, @RequestBody Publicacao publicacao) {
        
            var updatedPublicacao = publicacaoService.update(id, publicacao);
            return new ResponseEntity<>(updatedPublicacao, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyAuthority('TUTOR','ADMIN', 'ENTIDADE')")
    @GetMapping("/findAll")
    public ResponseEntity<List<Publicacao>> findAll() {
        
            var result = publicacaoService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);

    }


    @PreAuthorize("hasAnyAuthority('TUTOR','ADMIN', 'ENTIDADE')")
    @GetMapping("/byEntidadeNome")
    public ResponseEntity<List<Publicacao>> findByEntidadeNome(@RequestParam String nomeEntidade) {
            var result = publicacaoService.findByEntidadeNome(nomeEntidade);
            return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TUTOR','ADMIN', 'ENTIDADE')")
    @GetMapping("/byTipoPublicacao")
    public ResponseEntity<List<Publicacao>> findByTipoPublicacao(@RequestParam String tipoPublicacao) {
            var result = publicacaoService.findByTipoPublicacao(tipoPublicacao);
            return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TUTOR','ADMIN', 'ENTIDADE')")
    @GetMapping("/byDescricao")
    public ResponseEntity<List<Publicacao>> findByDescricao(@RequestParam String descricao) {
        
            var result = publicacaoService.findByDescricao(descricao);
            return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
