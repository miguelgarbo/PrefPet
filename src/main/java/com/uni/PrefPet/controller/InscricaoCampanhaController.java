package com.uni.PrefPet.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscricoesCampanha")

public class InscricaoCampanhaController {

    @Autowired
    private InscricaoCampanhaService inscricaoCampanhaService;

    @PostMapping("/save")
    public ResponseEntity<InscricaoCampanha> save(@Valid @RequestBody InscricaoCampanha inscricaoCampanha) {
        try {
            var result = inscricaoCampanhaService.save(inscricaoCampanha);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<InscricaoCampanha> findById(@PathVariable Long id) {
        try {
            var result = inscricaoCampanhaService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<InscricaoCampanha>> findAll() {
        try {
            var result = inscricaoCampanhaService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<InscricaoCampanha> update(@PathVariable Long id,
                                                    @Valid @RequestBody InscricaoCampanha inscricaoCampanha) {
        try {
            var updated = inscricaoCampanhaService.update(id, inscricaoCampanha);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            inscricaoCampanhaService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByCampanhaTitulo/{titulo}")
    public ResponseEntity<List<InscricaoCampanha>> findByCampanhaTitulo(@PathVariable String titulo){
        try {
            var result = inscricaoCampanhaService.findByCampanhaTitulo(titulo);

            if (result == null || result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByAnimalNome/{nome}")
    public ResponseEntity<List<InscricaoCampanha>> findByAnimalNome(@PathVariable String nome){
        try {
            var result = inscricaoCampanhaService.findByAnimalNome(nome);

            if (result == null || result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByUsuarioNome/{nome}")
    public ResponseEntity<List<InscricaoCampanha>> findByUsuarioNome(@PathVariable String nome){
        try {
            var result = inscricaoCampanhaService.findByUsuarioNome(nome);

            if (result == null || result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByStatus/{status}")
    public ResponseEntity<List<InscricaoCampanha>> findByStatus(@PathVariable StatusInscricao status) {
        try{
            var result = inscricaoCampanhaService.findByStatus(status);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}

