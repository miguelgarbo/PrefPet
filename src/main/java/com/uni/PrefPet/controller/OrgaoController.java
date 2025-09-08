package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.model.Usuarios.Orgao;
import com.uni.PrefPet.service.OrgaoService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orgaos")
@CrossOrigin("*")
public class OrgaoController {

    @Autowired
    private OrgaoService orgaoService;

    @GetMapping("/findById/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id){

        var resultado = orgaoService.findById(id);
        return new ResponseEntity<>(resultado, HttpStatus.OK);

    }

}
