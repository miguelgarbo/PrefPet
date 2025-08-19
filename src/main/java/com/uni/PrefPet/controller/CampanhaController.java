package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Campanha;
import com.uni.PrefPet.service.CampanhaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/campanhas")
public class CampanhaController {

    @Autowired
    private CampanhaService campanhaService;

    @PostMapping("/save")
    public ResponseEntity<Campanha> save(@Valid @RequestBody Campanha campanha) {
        try {
            var novaCampanha = campanhaService.save(campanha);
            return new ResponseEntity<>(novaCampanha, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Campanha> findById(@PathVariable Long id) {
        try {
            var result = campanhaService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Campanha>> listAll() {
        try {
            var result = campanhaService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Campanha> update(@PathVariable Long id, @Valid @RequestBody Campanha campanha) {
        try {
            var updatedCampanha = campanhaService.update(id, campanha);
            return new ResponseEntity<>(updatedCampanha, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            campanhaService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/existsByTitulo/{titulo}")
    public ResponseEntity<Boolean> existsByTitulo(@PathVariable String titulo) {
        try {
            boolean exists = campanhaService.existsByTitulo(titulo);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByDataCriacao/{data}")
    public ResponseEntity<List<Campanha>> findByDataCriacao(@PathVariable LocalDate data) {
        try {
            var result = campanhaService.findByDataCriacao(data);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByTituloContainingIgnoreCase/{titulo}")
    public ResponseEntity<List<Campanha>> findByTituloContainingIgnoreCase(@PathVariable String titulo) {
        try {
            var result = campanhaService.findByTituloContainingIgnoreCase(titulo);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findCampanhaByDataCriacaoAfter/{data}")
    public ResponseEntity<List<Campanha>> findCampanhaByDataCriacaoAfter(@PathVariable LocalDate data){
        try{
            var result = campanhaService.findCampanhaByDataCriacaoAfter(data);

            if (result == null || result.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findCampanhaByDataCriacaoBefore/{data}")
    public ResponseEntity<List<Campanha>> findCampanhaByDataCriacaoBefore(@PathVariable LocalDate data){
        try{
            var result = campanhaService.findCampanhaByDataCriacaoBefore(data);

            if (result == null || result.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findCampanhaByDataCriacaoBeetwen/{comeco}/{fim}")
    public ResponseEntity<List<Campanha>> findCampanhaByDataCriacaoBefore
            (@PathVariable LocalDate comeco, @PathVariable LocalDate fim){
        try{
            var result = campanhaService.findCampanhaByDataCriacaoBeetwen(comeco, fim);

            if (result == null || result.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
}
