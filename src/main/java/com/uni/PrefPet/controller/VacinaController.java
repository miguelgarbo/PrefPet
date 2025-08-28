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
public class VacinaController {

    @Autowired
    private VacinaService vacinaService;

    @PostMapping("/save")
    public ResponseEntity<Vacina> save(@RequestBody @Valid Vacina vacina, @RequestParam int meses) {
        try {
            var result = vacinaService.save(vacina, meses);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Vacina> findById(@PathVariable Long id) {
        try {
            var result = vacinaService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Vacina>> findAll() {
        try {
            var result = vacinaService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Vacina> update(@PathVariable Long id, @RequestBody Vacina vacina) {
        try {
            var updated = vacinaService.update(id, vacina);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            vacinaService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-nome")
    public ResponseEntity<List<Vacina>> findByNome(@RequestParam String nome) {
        try {
            List<Vacina> result = vacinaService.findByNome(nome);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/by-lote")
    public ResponseEntity<Vacina> findByLote(@RequestParam String lote) {
        try {
            Vacina result = vacinaService.findByLote(lote);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/exists-lote")
    public ResponseEntity<Boolean> existsByLote(@RequestParam String lote) {
        boolean exists = vacinaService.existsByLote(lote);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/validade-before")
    public ResponseEntity<List<Vacina>> findByValidadeBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            List<Vacina> result = vacinaService.findByValidadeBefore(data);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/validade-after")
    public ResponseEntity<List<Vacina>> findByValidadeAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            List<Vacina> result = vacinaService.findByValidadeAfter(data);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/aplicacao")
    public ResponseEntity<List<Vacina>> findByDataAplicacao(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            List<Vacina> result = vacinaService.findByDataAplicacao(data);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/aplicacao-after")
    public ResponseEntity<List<Vacina>> findByDataAplicacaoAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            List<Vacina> result = vacinaService.findByDataAplicacaoAfter(data);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/animal/{id}")
    public ResponseEntity<List<Vacina>> findByAnimalId(@PathVariable Long id) {
        try {
            List<Vacina> result = vacinaService.findByAnimalId(id);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
