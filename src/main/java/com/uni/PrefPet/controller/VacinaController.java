package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Vacina;
import com.uni.PrefPet.service.VacinaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vacinas")
@CrossOrigin("*")
public class VacinaController {

    @Autowired
    private VacinaService vacinaService;

    @PostMapping("/save")
    public ResponseEntity<Vacina> save(@RequestBody @Valid Vacina vacina) {

            var result = vacinaService.save(vacina);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Vacina> findById(@PathVariable Long id) {

            var result = vacinaService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Vacina>> findAll() {

            var result = vacinaService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Vacina> update(@PathVariable Long id, @RequestBody Vacina vacina) {

            var updated = vacinaService.update(id, vacina);
            return new ResponseEntity<>(updated, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

            vacinaService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/by-nome")
    public ResponseEntity<List<Vacina>> findByNome(@RequestParam String nome) {

            List<Vacina> result = vacinaService.findByNome(nome);
            return ResponseEntity.ok(result);

    }

}
