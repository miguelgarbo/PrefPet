package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Usuarios.Veterinario;
import com.uni.PrefPet.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeterinarioService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    public Veterinario save(Veterinario veterinario) {

        if (veterinarioRepository.existsByCRMV(veterinario.getCRMV())) {
            throw new IllegalArgumentException("Já existe um usuário com este CRMV.");
        }

        return veterinarioRepository.save(veterinario);
    }
}

