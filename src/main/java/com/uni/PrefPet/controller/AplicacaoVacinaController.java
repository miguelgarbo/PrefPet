package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.AplicacaoVacina;
import com.uni.PrefPet.service.AplicacaoVacinaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/aplicacao")
@CrossOrigin("*")
public class AplicacaoVacinaController {

    @Autowired
    private AplicacaoVacinaService aplicacaoVacinaService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<AplicacaoVacina> save(@RequestBody AplicacaoVacina aplicacaoVacina, @RequestParam int meses) {
        var result = aplicacaoVacinaService.save(aplicacaoVacina, meses);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AplicacaoVacina> findById(@PathVariable Long id) {
        var result = aplicacaoVacinaService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<AplicacaoVacina>> findAll() {

        var result = aplicacaoVacinaService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<AplicacaoVacina> update(@PathVariable Long id, @RequestBody AplicacaoVacina vacina) {

        var updated = aplicacaoVacinaService.update(id, vacina);
        return new ResponseEntity<>(updated, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        aplicacaoVacinaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/findByAnimalId/{animal_id}")
    public ResponseEntity<List<AplicacaoVacina>> findByAnimalId(@PathVariable Long animal_id) {
        var result = aplicacaoVacinaService.findByAnimal(animal_id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/by-lote")
    public ResponseEntity<AplicacaoVacina> findByLote(@RequestParam String lote) {

        AplicacaoVacina result = aplicacaoVacinaService.findByLote(lote);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/validade-before")
    public ResponseEntity<List<AplicacaoVacina>> findByValidadeBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<AplicacaoVacina> result = aplicacaoVacinaService.findByValidadeBefore(data);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/validade-after")
    public ResponseEntity<List<AplicacaoVacina>> findByValidadeAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<AplicacaoVacina> result = aplicacaoVacinaService.findByValidadeAfter(data);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/aplicacaoData")
    public ResponseEntity<List<AplicacaoVacina>> findByDataAplicacao(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<AplicacaoVacina> result = aplicacaoVacinaService.findByDataAplicacao(data);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/aplicacao-after")
    public ResponseEntity<List<AplicacaoVacina>> findByDataAplicacaoAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<AplicacaoVacina> result = aplicacaoVacinaService.findByDataAplicacaoAfter(data);
        return ResponseEntity.ok(result);

    }
}

