package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Usuarios.Tutor;
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

    @Autowired
    private UsuarioService usuarioService;

    public Veterinario save(Veterinario veterinario) {

        usuarioService.preValidacaoUsuarioSave(veterinario);

        if (veterinarioRepository.existsByCRMV(veterinario.getCRMV())) {
            throw new IllegalArgumentException("Já existe um Veterinário com este CRMV no Sistema");
        }

        return veterinarioRepository.save(veterinario);
    }

    public List<Veterinario> findAll() {
        return veterinarioRepository.findAll();
    }

    public Veterinario update(Long id, Veterinario vetAtualizado) {

        Veterinario vetValidado = (Veterinario) usuarioService.preValidacaoUsuarioUpdate(id, vetAtualizado);
        System.out.println("é pra ter o id: "+ vetValidado.getId());

        var crmvExiste = veterinarioRepository.existsByCRMVAndIdNot(vetAtualizado.getCRMV(), id);

        if (crmvExiste){
            throw new IllegalArgumentException("Esse CRMV Já Possui uma Conta no Sistema");
        }
        vetValidado.setCRMV(vetAtualizado.getCRMV());
        vetValidado.setAplicacoes(vetAtualizado.getAplicacoes());

        return veterinarioRepository.save(vetValidado);
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

