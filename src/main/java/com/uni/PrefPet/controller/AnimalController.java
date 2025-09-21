package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.service.AnimalService;
import com.uni.PrefPet.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/animais")
@CrossOrigin("*")
public class AnimalController {

    @Autowired
    private AnimalService animalService;
    private TutorService tutorService;

    @PostMapping("/save")
    public ResponseEntity<Animal> save(@RequestBody @Valid Animal animal
    ) {

            var result = animalService.save(animal);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

        @GetMapping("/findById/{id}")
        public ResponseEntity<Animal> findById(@PathVariable Long id){

                var result = animalService.findById(id);
                return new ResponseEntity<>(result, HttpStatus.OK);

        }
        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> delete (@PathVariable Long id){
                animalService.delete(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        @PutMapping("/update/{id}")
        public ResponseEntity<Animal> update (@Valid @PathVariable Long id, @RequestBody Animal animal){
                var updatedUsuario = animalService.update(id, animal);
                return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        }
        @GetMapping("/findAll")
        public ResponseEntity<List<Animal>> findAll() {

                var result = animalService.findAll();
                return new ResponseEntity<>(result, HttpStatus.OK);

        }


        @GetMapping("/findByNome")
        public ResponseEntity<Animal> findByNome(@RequestParam String nome) {

                var result = animalService.findByNome(nome);
                return new ResponseEntity<>(result, HttpStatus.OK);

        }

        @GetMapping("/findByEspecie")
        public ResponseEntity<List<Animal>> findByEspecie(@RequestParam String especie) {

                var result = animalService.findByEspecieNome(especie);
                return new ResponseEntity<>(result, HttpStatus.OK);

        }

        @GetMapping("/findByCor")
        public ResponseEntity<List<Animal>> findByCor(@RequestParam String cor) {

                var result = animalService.findByCor(cor);
                return new ResponseEntity<>(result, HttpStatus.OK);

        }

        @GetMapping("/findBySexo")
        public ResponseEntity<List<Animal>> findBySexo(@RequestParam String sexo) {

                var result = animalService.findBySexo(sexo);
                return new ResponseEntity<>(result, HttpStatus.OK);

        }

        @GetMapping("/findByCastrado")
        public ResponseEntity<List<Animal>> findByCastrado(@RequestParam Boolean castrado) {

                var result = animalService.findByCastrado(castrado);
                return new ResponseEntity<>(result, HttpStatus.OK);

        }

        @GetMapping("/findByMicrochip")
        public ResponseEntity<Animal> findByNumeroMicrochip(@RequestParam String numeroMicrochip) {

                var result = animalService.findByMicrochip(numeroMicrochip);
                return new ResponseEntity<>(result, HttpStatus.OK);

        }


        @GetMapping("/findByTutor")
        public ResponseEntity<List<Animal>> findByUsuario(@RequestParam Long tutorId) {

                Tutor tutorInformado = tutorService.findById(tutorId);
                var result = animalService.findByTutor(tutorInformado);
                return new ResponseEntity<>(result, HttpStatus.OK);

        }

        @GetMapping("/findByDataNascimento")
        public ResponseEntity<List<Animal>> findByDataNascimento(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataNascimento) {

                var result = animalService.findByDataNascimento(dataNascimento);
                return new ResponseEntity<>(result, HttpStatus.OK);

        }

        @GetMapping("/findByRegistroGeral")
        public ResponseEntity<List<Animal>> findByRegistroGeral(@RequestParam String registroGeral) {

                var result = animalService.findByRegistroGeral(registroGeral);
                return new ResponseEntity<>(result, HttpStatus.OK);

        }

        @GetMapping("/findAnimaisIdadeAcima")
        public ResponseEntity<List<Animal>> findAnimaisIdadeAcima(@RequestParam int idade) {

                var result = animalService.findAnimaisIdadeAcima(idade);
                return new ResponseEntity<>(result, HttpStatus.OK);

        }

        @GetMapping("/findAnimaisEntreIdade")
        public ResponseEntity<List<Animal>> findAnimaisEntreIdade(@RequestParam int idadeMin, @RequestParam int idadeMax) {

                var result = animalService.findAnimaisEntreIdade(idadeMax, idadeMin);
                return new ResponseEntity<>(result, HttpStatus.OK);

        }

}

