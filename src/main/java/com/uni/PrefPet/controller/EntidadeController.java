package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Enum.TipoEntidade;
import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.service.EntidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entidades")
@CrossOrigin("*")
public class EntidadeController {

    @Autowired
    private EntidadeService entidadeService;

    @PostMapping("/save")
    public ResponseEntity<Entidade> save(@RequestBody @Valid Entidade entidade) {
        try {
            var result = entidadeService.save(entidade);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null,
                    HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<Entidade> findById(@PathVariable Long id) {
        try {
            var result = entidadeService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            entidadeService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Entidade> update(@PathVariable Long id, @RequestBody Entidade entidade) {
        try {
            var updatedEntidade = entidadeService.update(id, entidade);
            return new ResponseEntity<>(updatedEntidade, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<Entidade>> findAll() {
        try {
            var result = entidadeService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/existsByCPF")
    public ResponseEntity<Boolean> existsByCPF(@RequestParam String cpf) {
        try {
            boolean exists = entidadeService.existsByCPF(cpf);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/existsByEmail")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        try {
            boolean exists = entidadeService.existsByEmail(email);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByNome")
    public ResponseEntity<List<Entidade>> findByNome(@RequestParam String nome) {
        try {
            List<Entidade> entidade = entidadeService.findByNome(nome);
            return new ResponseEntity<>(entidade, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByCPF")
    public ResponseEntity<List<Entidade>> findByCPF(@RequestParam String cpf) {
        try {
            List<Entidade> entidade = entidadeService.findByCPF(cpf);
            return new ResponseEntity<>(entidade, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByTelefone")
    public ResponseEntity<List<Entidade>> findByTelefone(@RequestParam String telefone) {
        try {
            List<Entidade> entidade = entidadeService.findByTelefone(telefone);
            return new ResponseEntity<>(entidade, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<List<Entidade>> findByEmail(@RequestParam String email) {
        try {
            var entidades = entidadeService.findByEmail(email);
            return new ResponseEntity<>(entidades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByCnpj")
    public ResponseEntity<Entidade> findByCnpj(@RequestParam String cnpj) {
        try {
            var entidade = entidadeService.findByCnpj(cnpj);
            return new ResponseEntity<>(entidade, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/findByTipoEntidade")
    public ResponseEntity<List<Entidade>> findByTipoEntidade(@RequestParam TipoEntidade tipoEntidade) {
        try {
            var entidades = entidadeService.findByTipoEntidade(tipoEntidade);
            return new ResponseEntity<>(entidades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }



}
