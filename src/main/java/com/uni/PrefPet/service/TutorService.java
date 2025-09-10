package com.uni.PrefPet.service;
import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.repository.EntidadeRepository;
import com.uni.PrefPet.repository.TutorRepository;
import com.uni.PrefPet.repository.VeterinarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    public List<Tutor> findAll() {
        return tutorRepository.findAll();
    }

    public Tutor save(Tutor tutor) {

        if (tutorRepository.existsByCpf(tutor.getCpf())) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF.");
        }

        if (tutorRepository.existsByTelefone(tutor.getTelefone())) {
            throw new IllegalArgumentException("Já existe um usuário com este telefone.");
        }

        if (tutorRepository.existsByEmail(tutor.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário com este email.");
        }

        return tutorRepository.save(tutor);
    }



    public Tutor findById(Long id) {
        return tutorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Entidade findByCnpj(String cnpj){

        return tutorRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o cnpj informado"));
    }

    public Tutor update(Long id, Tutor tutorAtualizado) {
        Tutor existente = tutorRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Usuário com id " + id + " não encontrado."));

        if (tutorRepository.existsByCpf(tutorAtualizado.getCpf())) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF.");
        }

        if (tutorRepository.existsByTelefone(tutorAtualizado.getTelefone())) {
            throw new IllegalArgumentException("Já existe um usuário com este telefone.");
        }

        if (tutorRepository.existsByCnpj(tutorAtualizado.getCnpj())) {
            throw new IllegalArgumentException("Já existe um usuário com este cnpj.");
        }


        existente.setEstado(tutorAtualizado.getEstado());
        existente.setCidade(tutorAtualizado.getCidade());
        existente.setCep(tutorAtualizado.getCep());
        existente.setCnpj(tutorAtualizado.getCnpj());
        existente.setNome(tutorAtualizado.getNome());
        existente.setCpf(tutorAtualizado.getCpf());
        existente.setTelefone(tutorAtualizado.getTelefone());
        existente.setAnimais(tutorAtualizado.getAnimais());
        return tutorRepository.save(existente);
    }

    public String delete(Long id) {
        if (!tutorRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário com id " + id + " não encontrado.");
        }
        tutorRepository.deleteById(id);
        return "Usuário com id " + id + " foi excluído com sucesso.";
    }


    //serviços especificos:

    public boolean existsByCPF(String cpf) {
        return tutorRepository.existsByCpf(cpf);
    }

    public boolean existsByEmail(String email) {
        return tutorRepository.existsByEmail(email);
    }

    public boolean existsByTelefone(String telefone){
        return tutorRepository.existsByTelefone(telefone);
    }

    public List<Tutor> findByNome(String nome) {
        return tutorRepository.findByNomeContainingIgnoreCase(nome)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o nome informado"));
    }

    public List<Tutor> findByCPF(String cpf) {
        return tutorRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o CPF informado"));
    }

    public List<Tutor> findByTelefone(String telefone) {
        return tutorRepository.findByTelefone(telefone)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o telefone informado"));
    }

    public List<Tutor> findByEmail(String email) {
        return tutorRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o email informado"));
    }

    //fim dos serviços especificos

}
