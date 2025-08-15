package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Campanha;
import com.uni.PrefPet.service.CampanhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campanhas")
public class CampanhaController {

    @Autowired
    private CampanhaService campanhaService;

    @PostMapping("/save")
    public ResponseEntity<Campanha> save(@RequestBody Campanha campanha) {
        Campanha novaCampanha = campanhaService.save(campanha);
        return ResponseEntity.ok(novaCampanha);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Campanha> findById(@PathVariable Long id) {
        return ResponseEntity.ok(campanhaService.findById(id));
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Campanha>> listAll() {
        return ResponseEntity.ok(campanhaService.findAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Campanha> update(@PathVariable Long id, @RequestBody Campanha campanha) {
        return ResponseEntity.ok(campanhaService.update(id, campanha));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(campanhaService.delete(id));
    }
}
