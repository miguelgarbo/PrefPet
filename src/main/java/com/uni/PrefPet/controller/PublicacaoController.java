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

    @PostMapping("/save")
    public ResponseEntity<Publicacao> save(@RequestBody @Valid Publicacao publicacao) {
        try {
            var result = publicacaoService.save(publicacao);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null,
                    HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<Publicacao> findById(@PathVariable Long id) {
        try {
            var result = publicacaoService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            publicacaoService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Publicacao> update(@PathVariable Long id, @RequestBody Publicacao publicacao) {
        try {
            var updatedPublicacao = publicacaoService.update(id, publicacao);
            return new ResponseEntity<>(updatedPublicacao, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<Publicacao>> findAll() {
        try {
            var result = publicacaoService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByEntidade")
    public ResponseEntity<List<Publicacao>> findByUsuario(@RequestParam Entidade entidade) {
        try {
            var result = publicacaoService.findByEntidade(entidade);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }}

    @GetMapping("/findByEntidadeNome")
    public ResponseEntity<List<Publicacao>> findByEntidadeNome(@RequestParam String nomeEntidade) {
        try {
            var result = publicacaoService.findByEntidadeNome(nomeEntidade);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByDataCriacao")
    public ResponseEntity<List<Publicacao>> findByDataCriacao(@RequestParam LocalDate data) {
        try {
            var result = publicacaoService.findByDataCriacao(data);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByTipoPublicacao")
    public ResponseEntity<List<Publicacao>> findByTipoPublicacao(@RequestParam String tipoPublicacao) {
        try {
            var result = publicacaoService.findByTipoPublicacao(tipoPublicacao);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findByDescricao")
    public ResponseEntity<List<Publicacao>> findByDescricao(@RequestParam String descricao) {
        try {
            var result = publicacaoService.findByDescricao(descricao);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }







}
