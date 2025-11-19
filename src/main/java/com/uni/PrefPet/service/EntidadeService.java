package com.uni.PrefPet.service;


import com.uni.PrefPet.model.Enum.TipoEntidade;
import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.model.Usuarios.Veterinario;
import com.uni.PrefPet.repository.EntidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.util.List;

@Service
public class EntidadeService {

    @Autowired
    private EntidadeRepository entidadeRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Entidade save(Entidade entidade) {

        usuarioService.preValidacaoUsuarioSave(entidade);

        return entidadeRepository.save(entidade);
    }

    public Entidade update(Long id, Entidade entidadeAtualizado) {

        Entidade entidadeValidada = (Entidade) usuarioService.preValidacaoUsuarioUpdate(id, entidadeAtualizado);
        System.out.println("é pra ter o id: "+ entidadeValidada.getId());

        entidadeValidada.setTipoEntidade(entidadeAtualizado.getTipoEntidade());
        entidadeValidada.setPublicacoes(entidadeAtualizado.getPublicacoes());

        return entidadeRepository.save(entidadeValidada);
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


