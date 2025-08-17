package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Especie;
import com.uni.PrefPet.service.EspecieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especies")
public class EspecieController {
    
    @Autowired
    private EspecieService especieService;
    
    @PostMapping("/save")
    public ResponseEntity<Especie> save(@RequestBody Especie especie
    ) {
        try {
            var result = especieService.save(especie);
            return new ResponseEntity<>(result,
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null,
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Especie> findById(@PathVariable Long id){
        try {
            var result = especieService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        try {
            especieService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Especie> update (@Valid @PathVariable Long id, @RequestBody Especie especie){
        try {
            var updatedUsuario = especieService.update(id, especie);
            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<Especie>> findAll() {
        try {
            var result = especieService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    
    
}
