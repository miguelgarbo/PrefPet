package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.service.AnimalService;
import com.uni.PrefPet.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @PostMapping("/save")
    public ResponseEntity<Animal> save(
            @Valid @RequestBody Animal animal
    ) {
        try {
            var result = animalService.save(animal);
            return new ResponseEntity<>(result,
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null,
                    HttpStatus.BAD_GATEWAY);
        }
    }

        @GetMapping("/findById/{id}")
        public ResponseEntity<Animal> findById(@PathVariable Long id){
            try {
                var result = animalService.findById(id);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> delete (@PathVariable Long id){
            try {
                animalService.delete(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        @PutMapping("/update/{id}")
        public ResponseEntity<Animal> update (@Valid @PathVariable Long id, @RequestBody Animal animal){
            try {
                var updatedUsuario = animalService.update(id, animal);
                return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
        @GetMapping("/listAll")
        public ResponseEntity<List<Animal>> listAll() {
            try {
                var result = animalService.listAll();
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
    }

