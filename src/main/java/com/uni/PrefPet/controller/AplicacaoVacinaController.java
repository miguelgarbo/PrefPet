package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.AplicacaoVacina;
import com.uni.PrefPet.service.AplicacaoVacinaService;
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
@RequestMapping("/aplicacao-vacinas")
@CrossOrigin("*")
public class AplicacaoVacinaController {

    @Autowired
    private AplicacaoVacinaService aplicacaoVacinaService;

    @PostMapping
    public ResponseEntity<AplicacaoVacina> save(@RequestBody @Valid AplicacaoVacina vacina, @RequestParam int meses) {
        try {
            var result = aplicacaoVacinaService.save(vacina, meses);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AplicacaoVacina> findById(@PathVariable Long id) {
        try {
            var result = aplicacaoVacinaService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<AplicacaoVacina>> findAll() {
        try {
            var result = aplicacaoVacinaService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AplicacaoVacina> update(@PathVariable Long id, @RequestBody AplicacaoVacina vacina) {
        try {
            var updated = aplicacaoVacinaService.update(id, vacina);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            aplicacaoVacinaService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/by-lote")
    public ResponseEntity<AplicacaoVacina> findByLote(@RequestParam String lote) {
        try {
            AplicacaoVacina result = aplicacaoVacinaService.findByLote(lote);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/exists-lote")
    public ResponseEntity<Boolean> existsByLote(@RequestParam String lote) {
        boolean exists = aplicacaoVacinaService.existsByLote(lote);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/validade-before")
    public ResponseEntity<List<AplicacaoVacina>> findByValidadeBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            List<AplicacaoVacina> result = aplicacaoVacinaService.findByValidadeBefore(data);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/validade-after")
    public ResponseEntity<List<AplicacaoVacina>> findByValidadeAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            List<AplicacaoVacina> result = aplicacaoVacinaService.findByValidadeAfter(data);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/aplicacaoData")
    public ResponseEntity<List<AplicacaoVacina>> findByDataAplicacao(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            List<AplicacaoVacina> result = aplicacaoVacinaService.findByDataAplicacao(data);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/aplicacao-after")
    public ResponseEntity<List<AplicacaoVacina>> findByDataAplicacaoAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            List<AplicacaoVacina> result = aplicacaoVacinaService.findByDataAplicacaoAfter(data);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    
    
}
