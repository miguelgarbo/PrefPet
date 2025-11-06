package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Usuarios.Usuario;
import com.uni.PrefPet.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Long id){
        return  usuarioRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Usuario Nao Encontrado"));
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }


}
