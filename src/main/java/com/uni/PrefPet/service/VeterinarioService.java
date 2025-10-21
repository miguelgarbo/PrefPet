package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.model.Usuarios.Veterinario;
import com.uni.PrefPet.model.Usuarios.Veterinario;
import com.uni.PrefPet.model.Usuarios.Veterinario;
import com.uni.PrefPet.repository.VeterinarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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

    public List<Veterinario> findAll() {
        return veterinarioRepository.findAll();
    }

    public Veterinario update(Long id, Veterinario veterinarioAtualizado) {
        Veterinario existente = veterinarioRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Usuário com id " + id + " não encontrado."));

        if (veterinarioRepository.existsByCpf(veterinarioAtualizado.getCpf())) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF.");
        }

        if (veterinarioRepository.existsByTelefone(veterinarioAtualizado.getTelefone())) {
            throw new IllegalArgumentException("Já existe um usuário com este telefone.");
        }

        if (veterinarioRepository.existsByCnpj(veterinarioAtualizado.getCnpj())) {
            throw new IllegalArgumentException("Já existe um usuário com este cnpj.");
        }

        existente.setEstado(veterinarioAtualizado.getEstado());
        existente.setCidade(veterinarioAtualizado.getCidade());
        existente.setCep(veterinarioAtualizado.getCep());
        existente.setCnpj(veterinarioAtualizado.getCnpj());
        existente.setNome(veterinarioAtualizado.getNome());
        existente.setCpf(veterinarioAtualizado.getCpf());
        existente.setTelefone(veterinarioAtualizado.getTelefone());
        existente.setCRMV(veterinarioAtualizado.getCRMV());
        return veterinarioRepository.save(existente);
    }
    
    
    public Veterinario findById(Long id){
        return veterinarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o id informado"));
    }

    public String delete(Long id) {
        if (!veterinarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário com id " + id + " não encontrado.");
        }
        veterinarioRepository.deleteById(id);
        return "Veterinario com id " + id + " foi excluído com sucesso.";
    }



    //serviços especificos:

    public Veterinario findByCRMV(String crmv){
        return veterinarioRepository.findByCRMV(crmv)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o crmv informado"));
    }


    public List<Veterinario> findByNome(String nome) {
        return veterinarioRepository.findByNomeContainingIgnoreCase(nome)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o nome informado"));
    }

    public Veterinario findByCPF(String cpf) {
        return veterinarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o CPF informado"));
    }


    public Veterinario findByEmail(String email) {
        return veterinarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o email informado"));
    }
    
    
}

