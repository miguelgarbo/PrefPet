package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Vacina;
import com.uni.PrefPet.service.VacinaService;
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

    @GetMapping("/findByNome")
    public ResponseEntity<List<Vacina>> findByNome(@RequestParam String nome) {
        try {
            List<Vacina> result = vacinaService.findByNome(nome);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByLote")
    public ResponseEntity<Vacina> findByLote(@RequestParam String lote) {
        try {
            Vacina result = vacinaService.findByLote(lote);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/existsByLote")
    public ResponseEntity<Boolean> existsByLote(@RequestParam String lote) {
        try {
            boolean exists = vacinaService.existsByLote(lote);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByValidadeBefore")
    public ResponseEntity<List<Vacina>> findByValidadeBefore(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            List<Vacina> result = vacinaService.findByValidadeBefore(data);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByValidadeAfter")
    public ResponseEntity<List<Vacina>> findByValidadeAfter(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        try {
            List<Vacina> result = vacinaService.findByValidadeAfter(data);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
