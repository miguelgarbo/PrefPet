package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.model.Usuarios.Usuario;
import com.uni.PrefPet.model.Usuarios.Veterinario;
import com.uni.PrefPet.model.dtos.EntidadeDTO;
import com.uni.PrefPet.model.dtos.TutorDTO;
import com.uni.PrefPet.model.dtos.VeterinarioDTO;
import com.uni.PrefPet.service.EntidadeService;
import com.uni.PrefPet.service.TutorService;
import com.uni.PrefPet.service.UsuarioService;
import com.uni.PrefPet.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    TutorService tutorService;

    @Autowired
    EntidadeService entidadeService;

    @Autowired
    VeterinarioService veterinarioService;

    @GetMapping("/root")
    public ResponseEntity<String> default_response(){
        var string = "The Prepfpet Api Is Running";
        return new ResponseEntity<>(string, HttpStatus.OK);
    }

    @GetMapping("/tutor/keycloak/{sub}")
    public Tutor findTutorByKeycloakId(@PathVariable String sub) {
        Tutor tutor = usuarioService.findByTutorKeycloakId(sub);
        return tutor;
    }

    @GetMapping("/veterinario/keycloak/{sub}")
    public Veterinario findVeterinarioByKeycloakId(@PathVariable String sub) {
        Veterinario veterinario = usuarioService.findByVeterinarioKeycloakId(sub);
        return veterinario;
    }

    @GetMapping("/entidade/keycloak/{sub}")
    public Entidade findEntidadeByKeycloakId(@PathVariable String sub) {
        Entidade entidade = usuarioService.findByEntidadeKeycloakId(sub);
        return entidade;
    }

    @PostMapping("/register/tutor")
    public ResponseEntity<Tutor> save(@RequestBody TutorDTO tutor){
        var resposta = tutorService.save(tutor);
        return new ResponseEntity<>(resposta, HttpStatus.CREATED);
    }

    @PostMapping("/register/entidade")
    public ResponseEntity<Entidade> save(@RequestBody EntidadeDTO entidade){
        var resposta = entidadeService.save(entidade);
        return new ResponseEntity<>(resposta, HttpStatus.CREATED);
    }

    @PostMapping("/register/veterinario")
    public ResponseEntity<Veterinario> save(@RequestBody VeterinarioDTO veterinario){
        var resposta = veterinarioService.save(veterinario);
        return new ResponseEntity<>(resposta, HttpStatus.CREATED);
    }



    @GetMapping("/findAll")
    public ResponseEntity<List<Usuario>> findAll(){
        var resposta = usuarioService.findAll();
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id){
        var resposta = usuarioService.findById(id);
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

}
