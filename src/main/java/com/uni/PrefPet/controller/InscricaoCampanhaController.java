package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.InscricaoCampanha;
import com.uni.PrefPet.service.InscricaoCampanhaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscricoesCampanha")

public class InscricaoCampanhaController {
    private InscricaoCampanhaService inscricaoCampanhaService;


    @PostMapping("/save")
    public ResponseEntity<InscricaoCampanha> save(@RequestBody InscricaoCampanha inscricaoCampanha){
        return ResponseEntity.ok(inscricaoCampanhaService.save(inscricaoCampanha));
    }

    @GetMapping("/findById/{id}")
    public  ResponseEntity<InscricaoCampanha> save(@PathVariable Long id){
        return ResponseEntity.ok(inscricaoCampanhaService.findById(id));
    }

    @GetMapping("/listAll")
        public ResponseEntity<List<InscricaoCampanha>> listAll(){
            return ResponseEntity.ok(inscricaoCampanhaService.findAll());
        }

    @PutMapping("/update/{id}")
        public ResponseEntity<InscricaoCampanha> update(@PathVariable Long id, InscricaoCampanha inscricaoCampanha){
            return  ResponseEntity.ok(inscricaoCampanhaService.update(id, inscricaoCampanha));
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(inscricaoCampanhaService.delete(id));
    }


}

