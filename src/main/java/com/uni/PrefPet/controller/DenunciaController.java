package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Denuncia;
import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.service.DenunciaService;
import com.uni.PrefPet.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/denuncias")
public class DenunciaController {

    @Autowired
    private DenunciaService denunciaService;
    private UsuarioService usuarioService;

    @PostMapping("/save")
    public ResponseEntity<Denuncia> save(@RequestBody @Valid Denuncia denuncia) {
        try {
            var result = denunciaService.save(denuncia);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Denuncia> findById(@PathVariable Long id) {
        try {
            var result = denunciaService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Denuncia>> listAll() {
        try {
            var result = denunciaService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Denuncia> update(@PathVariable Long id, @RequestBody Denuncia denuncia) {
        try {
            var updated = denunciaService.update(id, denuncia);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            denunciaService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Denuncia>> getByTipo(@PathVariable Denuncia.TipoDenuncia tipo) {
        try {
            var result = denunciaService.findByTipo(tipo);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Denuncia>> getByStatus(@PathVariable Denuncia.StatusDenuncia status) {
        try {
            var result = denunciaService.findByStatus(status);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Denuncia>> getByUsuario(@PathVariable Long usuarioId) {
        try {
            Usuario usuario = usuarioService.findById(usuarioId);
            var result = denunciaService.findByUsuario(usuario);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/anonimas")
    public ResponseEntity<List<Denuncia>> getAnonimas() {
        try {
            var result = denunciaService.findByAnonimaTrue();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/nao-anonimas")
    public ResponseEntity<List<Denuncia>> getNaoAnonimas() {
        try {
            var result = denunciaService.findByAnonimaFalse();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/especie")
    public ResponseEntity<List<Denuncia>> getByEspecie(@RequestParam String especie) {
        try {
            var result = denunciaService.findByEspecieContainingIgnoreCase(especie);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
