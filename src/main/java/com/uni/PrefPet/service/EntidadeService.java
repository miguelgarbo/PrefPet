package com.uni.PrefPet.service;


import com.uni.PrefPet.model.Enum.TipoEntidade;
import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.repository.EntidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntidadeService {

    @Autowired
    private EntidadeRepository entidadeRepository;

    public Entidade save(Entidade entidade) {

        if (entidadeRepository.existsByCnpj(entidade.getCnpj())) {
            throw new IllegalArgumentException("Já existe um órgão com este CNPJ.");
        }
        if (entidadeRepository.existsByCpf(entidade.getCpf())) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF.");
        }

        if (entidadeRepository.existsByTelefone(entidade.getTelefone())) {
            throw new IllegalArgumentException("Já existe um usuário com este telefone.");
        }

        if (entidadeRepository.existsByEmail(entidade.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário com este email.");
        }


        return entidadeRepository.save(entidade);
    }

    public Entidade update(Long id, Entidade entidadeAtualizado) {

        Entidade existente = entidadeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Entidade com id " + id + " não encontrado."));


        if (entidadeRepository.existsByCpf(entidadeAtualizado.getCpf())) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF.");
        }

        if (entidadeRepository.existsByTelefone(entidadeAtualizado.getTelefone())) {
            throw new IllegalArgumentException("Já existe um usuário com este telefone.");
        }

        if (entidadeRepository.existsByCnpj(entidadeAtualizado.getCnpj())) {
            throw new IllegalArgumentException("Já existe um usuário com este cnpj.");
        }

        existente.setEstado(entidadeAtualizado.getEstado());
        existente.setCidade(entidadeAtualizado.getCidade());
        existente.setCep(entidadeAtualizado.getCep());
        existente.setNome(entidadeAtualizado.getNome());
        existente.setCpf(entidadeAtualizado.getCpf());
        existente.setTelefone(entidadeAtualizado.getTelefone());
        existente.setCnpj(entidadeAtualizado.getCnpj());
        existente.setTipoEntidade(entidadeAtualizado.getTipoEntidade());

        return entidadeRepository.save(existente);
    }

    public Entidade findById(Long id) {
        return entidadeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public String delete(Long id) {
        if (!entidadeRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário com id " + id + " não encontrado.");
        }
        entidadeRepository.deleteById(id);
        return "Usuário com id " + id + " foi excluído com sucesso.";
    }

    public List<Entidade> findAll(){
        return entidadeRepository.findAll();
    }


    //serviços especificos:

    public List<Entidade> findByTipoEntidade(TipoEntidade tipoEntidade){

        return entidadeRepository.findByTipoEntidade(tipoEntidade)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o nome informado"));
    }

    public Entidade findByCnpj(String cnpj){

        return entidadeRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o cnpj informado"));
    }

    public boolean existsByCPF(String cpf) {
        return entidadeRepository.existsByCpf(cpf);
    }

    public boolean existsByEmail(String email) {
        return entidadeRepository.existsByEmail(email);
    }

    public boolean existsByTelefone(String telefone) {
        return entidadeRepository.existsByTelefone(telefone);
    }

    public List<Entidade> findByNome(String nome) {
        return entidadeRepository.findByNomeContainingIgnoreCase(nome)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o nome informado"));
    }

    public Entidade findByCPF(String cpf) {
        return entidadeRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o CPF informado"));
    }

    public List<Entidade> findByTelefone(String telefone) {
        return entidadeRepository.findByTelefone(telefone)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o telefone informado"));
    }

    public Entidade findByEmail(String email) {
        return entidadeRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o email informado"));
    }

    }


