package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Denuncia;
import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.service.DenunciaService;
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

    @PostMapping("/save")
    public ResponseEntity<Denuncia> save(@RequestBody Denuncia denuncia) {
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
        return ResponseEntity.ok(denunciaService.findAll());
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

    // 🔎 Filtros extras
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Denuncia>> findByTipo(@PathVariable Denuncia.TipoDenuncia tipo) {
        return ResponseEntity.ok(denunciaService.findByTipo(tipo));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Denuncia>> findByStatus(@PathVariable Denuncia.StatusDenuncia status) {
        return ResponseEntity.ok(denunciaService.findByStatus(status));
    }

    @PostMapping("/usuario")
    public ResponseEntity<List<Denuncia>> findByUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(denunciaService.findByUsuario(usuario));
    }

    @GetMapping("/anonimas")
    public ResponseEntity<List<Denuncia>> findAnonimas() {
        return ResponseEntity.ok(denunciaService.findAnonimas());
    }

    @GetMapping("/naoAnonimas")
    public ResponseEntity<List<Denuncia>> findNaoAnonimas() {
        return ResponseEntity.ok(denunciaService.findNaoAnonimas());
    }

    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<Denuncia>> findByEspecie(@PathVariable String especie) {
        return ResponseEntity.ok(denunciaService.findByEspecie(especie));
    }
}
