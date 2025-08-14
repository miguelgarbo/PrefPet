package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Tutor;
import com.uni.PrefPet.service.TutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @PostMapping("/save")
    public ResponseEntity<Tutor> save(
            @RequestBody Tutor livro) {
        try {
            var result = tutorService.save(livro);
            return new ResponseEntity<>(result,
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null,
                    HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<Tutor> findById(@PathVariable Long id) {
        try {
            var result = tutorService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            tutorService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Tutor> update(@PathVariable Long id, @RequestBody Tutor tutor) {
        try {
            var updatedTutor = tutorService.update(id, tutor);
            return new ResponseEntity<>(updatedTutor, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/listAll")
    public ResponseEntity<List<Tutor>> listAll() {
        try {
            var result = tutorService.listAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}

