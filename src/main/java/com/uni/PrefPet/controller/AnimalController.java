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
        try {
            var result = animalService.save(animal);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
        @GetMapping("/findAll")
        public ResponseEntity<List<Animal>> findAll() {
            try {
                var result = animalService.findAll();
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }


        @GetMapping("/findByNome")
        public ResponseEntity<List<Animal>> findByNome(@RequestParam String nome) {
            try {
                var result = animalService.findByNome(nome);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping("/findByEspecie")
        public ResponseEntity<List<Animal>> findByEspecie(@RequestParam String especie) {
            try {
                var result = animalService.findByEspecieNome(especie);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping("/findByCor")
        public ResponseEntity<List<Animal>> findByCor(@RequestParam String cor) {
            try {
                var result = animalService.findByCor(cor);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping("/findBySexo")
        public ResponseEntity<List<Animal>> findBySexo(@RequestParam String sexo) {
            try {
                var result = animalService.findBySexo(sexo);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping("/findByCastrado")
        public ResponseEntity<List<Animal>> findByCastrado(@RequestParam Boolean castrado) {
            try {
                var result = animalService.findByCastrado(castrado);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping("/findByMicrochip")
        public ResponseEntity<Animal> findByNumeroMicrochip(@RequestParam String numeroMicrochip) {
            try {
                var result = animalService.findByMicrochip(numeroMicrochip);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }


        @GetMapping("/findByTutor")
        public ResponseEntity<List<Animal>> findByUsuario(@RequestParam Long tutorId) {
            try {

                Tutor tutorInformado = tutorService.findById(tutorId);
                var result = animalService.findByTutor(tutorInformado);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping("/findByDataNascimento")
        public ResponseEntity<List<Animal>> findByDataNascimento(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataNascimento) {
            try {
                var result = animalService.findByDataNascimento(dataNascimento);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping("/findByRegistroGeral")
        public ResponseEntity<List<Animal>> findByRegistroGeral(@RequestParam String registroGeral) {
            try {
                var result = animalService.findByRegistroGeral(registroGeral);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping("/findAnimaisIdadeAcima")
        public ResponseEntity<List<Animal>> findAnimaisIdadeAcima(@RequestParam int idade) {
            try {
                var result = animalService.findAnimaisIdadeAcima(idade);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping("/findAnimaisEntreIdade")
        public ResponseEntity<List<Animal>> findAnimaisEntreIdade(@RequestParam int idadeMin, @RequestParam int idadeMax) {
            try {
                var result = animalService.findAnimaisEntreIdade(idadeMax, idadeMin);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }

}

