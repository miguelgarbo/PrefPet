package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.model.Usuarios.Usuario;
import com.uni.PrefPet.model.Usuarios.Veterinario;
import com.uni.PrefPet.service.EntidadeService;
import com.uni.PrefPet.service.TutorService;
import com.uni.PrefPet.service.UsuarioService;
import com.uni.PrefPet.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    TutorService tutorService;

    @Autowired
    EntidadeService entidadeService;

    @Autowired
    VeterinarioService veterinarioService;


    @PostMapping("/register/tutor")
    public ResponseEntity<Tutor> save(@RequestBody Tutor tutor){
        var resposta = tutorService.save(tutor);
        return new ResponseEntity<>(resposta, HttpStatus.CREATED);
    }

    @PostMapping("/register/entidade")
    public ResponseEntity<Entidade> save(@RequestBody Entidade entidade){
        var resposta = entidadeService.save(entidade);
        return new ResponseEntity<>(resposta, HttpStatus.CREATED);
    }

    @PostMapping("/register/veterinario")
    public ResponseEntity<Veterinario> save(@RequestBody Veterinario veterinario){
        var resposta = veterinarioService.save(veterinario);
        return new ResponseEntity<>(resposta, HttpStatus.CREATED);
    }



}
