package com.uni.PrefPet.controller;
import com.uni.PrefPet.model.Contato;
import com.uni.PrefPet.model.Emergencia;
import com.uni.PrefPet.service.ContatoService;
import com.uni.PrefPet.service.EmergenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/emergencia")
@CrossOrigin("*")

public class EmergenciaController {

    @Autowired
    private EmergenciaService emergenciaService;
    @Autowired
    private ContatoService contatoService;

    @PostMapping("/save")
    public ResponseEntity<Emergencia> save(@RequestBody @Valid Emergencia emergencia) {
        var result = emergenciaService.save(emergencia);
        return new ResponseEntity<>(result, HttpStatus.CREATED);}


    @GetMapping("/findById/{id}")
    public ResponseEntity<Emergencia> findById(@PathVariable Long id) {
        try {
            var result = emergenciaService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            emergenciaService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Emergencia> update(@PathVariable Long id, @RequestBody Emergencia emergencia) {
        try {
            var updatedEmergencia = emergenciaService.update(id, emergencia);
            return new ResponseEntity<>(updatedEmergencia, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<Emergencia>> findAll() {
        try {
            var result = emergenciaService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
