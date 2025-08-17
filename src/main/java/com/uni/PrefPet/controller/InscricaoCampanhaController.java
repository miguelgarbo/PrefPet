package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.InscricaoCampanha;
import com.uni.PrefPet.service.InscricaoCampanhaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscricoesCampanha")

public class InscricaoCampanhaController {
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

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            inscricaoCampanhaService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}

