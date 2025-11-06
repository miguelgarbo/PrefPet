package com.uni.PrefPet.controller;

import com.uni.PrefPet.service.LoginService;
import com.uni.PrefPet.model.dtos.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<String> logar(@RequestBody Login login){

        var token = loginService.logar(login);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
