package com.uni.PrefPet.controller;


import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tutores")
@CrossOrigin("*")
public class TutorController {

    @Autowired
    private TutorService tutorService;


    @PostMapping
    public ResponseEntity<Tutor> save(@RequestBody @Valid Tutor tutor) {

            var result = tutorService.save(tutor);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Tutor> findById(@PathVariable Long id) {
            var result = tutorService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
            tutorService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @PutMapping("/{id}")
    public ResponseEntity<Tutor> update(@PathVariable Long id, @RequestBody Tutor tutor) {
            var updatedTutor = tutorService.update(id, tutor);
            return new ResponseEntity<>(updatedTutor, HttpStatus.OK);
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<Tutor>> findAll() {
            var result = tutorService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/existsByCPF")
    public ResponseEntity<Boolean> existsByCPF(@RequestParam String cpf) {
        try {
            boolean exists = tutorService.existsByCPF(cpf);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/existsByEmail")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {

            boolean exists = tutorService.existsByEmail(email);
            return new ResponseEntity<>(exists, HttpStatus.OK);

    }

    @GetMapping("/findByNome")
    public ResponseEntity<List<Tutor>> findByNome(@RequestParam String nome) {
            List<Tutor> tutor = tutorService.findByNome(nome);
            return new ResponseEntity<>(tutor, HttpStatus.OK);

    }

    @GetMapping("/findByCPF")
    public ResponseEntity<List<Tutor>> findByCPF(@RequestParam String cpf) {
            List<Tutor> tutor = tutorService.findByCPF(cpf);
            return new ResponseEntity<>(tutor, HttpStatus.OK);
    }

    @GetMapping("/findByTelefone")
    public ResponseEntity<List<Tutor>> findByTelefone(@RequestParam String telefone) {
        List<Tutor> tutor = tutorService.findByTelefone(telefone);
        return new ResponseEntity<>(tutor, HttpStatus.OK);
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<List<Tutor>> findByEmail(@RequestParam String email) {
            var tutors = tutorService.findByEmail(email);
            return new ResponseEntity<>(tutors, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<Boolean> login(@RequestParam String email, @RequestParam String senha) {
            var result = tutorService.login(email, senha);
            return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }
}

