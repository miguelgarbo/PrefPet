package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.AplicacaoVacina;
import com.uni.PrefPet.service.AplicacaoVacinaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/aplicacao")
@CrossOrigin("*")
public class AplicacaoVacinaController {

    @Autowired
    private AplicacaoVacinaService aplicacaoVacinaService;


    //  SAVE  //
    @PreAuthorize("hasAnyAuthority('VETERINARIO','ADMIN')")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<AplicacaoVacina> save(
            @RequestBody AplicacaoVacina aplicacaoVacina,
            @RequestParam int meses
    ) {
        // Nenhuma funcionalidade removida — apenas passa ao service
        var result = aplicacaoVacinaService.save(aplicacaoVacina, meses);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    //  FIND BY ID  //
    @PreAuthorize("hasAnyAuthority('VETERINARIO', 'TUTOR','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AplicacaoVacina> findById(@PathVariable Long id) {
        var result = aplicacaoVacinaService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    //  FIND ALL  //
    @PreAuthorize("hasAnyAuthority('VETERINARIO','ADMIN')")
    @GetMapping("/findAll")
    public ResponseEntity<List<AplicacaoVacina>> findAll() {

        var result = aplicacaoVacinaService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    //  UPDATE  //
    @PreAuthorize("hasAnyAuthority('VETERINARIO','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AplicacaoVacina> update(
            @PathVariable Long id,
            @RequestBody AplicacaoVacina vacina
    ) {
        // NÃO removi nada — apenas delego ao service
        var updated = aplicacaoVacinaService.update(id, vacina);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }


    //  DELETE  //
    @PreAuthorize("hasAnyAuthority('VETERINARIO','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        aplicacaoVacinaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PreAuthorize("hasAnyAuthority('VETERINARIO','ADMIN','TUTOR')")
    @GetMapping("/findByAnimalId/{animal_id}")
    public ResponseEntity<List<AplicacaoVacina>> findByAnimalId(@PathVariable Long animal_id) {
        var result = aplicacaoVacinaService.findByAnimal(animal_id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


//    @GetMapping("/by-lote")
//    public ResponseEntity<AplicacaoVacina> findByLote(@RequestParam String lote) {
//
//        AplicacaoVacina result = aplicacaoVacinaService.findByLote(lote);
//        return ResponseEntity.ok(result);
//
//    }
    @PreAuthorize("hasAnyAuthority('VETERINARIO','ADMIN')")
    @GetMapping("/findByVeterinarioId/{veterinario_id}")
    public ResponseEntity<List<AplicacaoVacina>> findByVetId(@PathVariable Long veterinario_id) {
        var result = aplicacaoVacinaService.findByVeterinarioId(veterinario_id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('VETERINARIO','ADMIN','TUTOR')")
    @GetMapping("/validade-before")
    public ResponseEntity<List<AplicacaoVacina>> findByValidadeBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
        List<AplicacaoVacina> result = aplicacaoVacinaService.findByValidadeBefore(data);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyAuthority('VETERINARIO','ADMIN','TUTOR')")
    @GetMapping("/validade-after")
    public ResponseEntity<List<AplicacaoVacina>> findByValidadeAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
        List<AplicacaoVacina> result = aplicacaoVacinaService.findByValidadeAfter(data);
        return ResponseEntity.ok(result);

    }



    @PreAuthorize("hasAnyAuthority('VETERINARIO','ADMIN','TUTOR')")
    @GetMapping("/aplicacaoData")
    public ResponseEntity<List<AplicacaoVacina>> findByDataAplicacao(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
        List<AplicacaoVacina> result = aplicacaoVacinaService.findByDataAplicacao(data);
        return ResponseEntity.ok(result);

    }

    @PreAuthorize("hasAnyAuthority('VETERINARIO','ADMIN','TUTOR')")
    @GetMapping("/aplicacao-after")
    public ResponseEntity<List<AplicacaoVacina>> findByDataAplicacaoAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
        List<AplicacaoVacina> result = aplicacaoVacinaService.findByDataAplicacaoAfter(data);
        return ResponseEntity.ok(result);

    }

}

