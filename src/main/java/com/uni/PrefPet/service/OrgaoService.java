package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.model.Usuarios.Orgao;
import com.uni.PrefPet.repository.OrgaoRepository;
import com.uni.PrefPet.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgaoService extends UsuarioService{

    @Autowired
    private OrgaoRepository orgaoRepository;

        @Override
        public Orgao save(Usuario usuario) {
            if (!(usuario instanceof Orgao)) {
                throw new IllegalArgumentException("O usuário informado não é um Órgão.");
            }

            Orgao orgao = (Orgao) usuario;

            if (orgaoRepository.existsByCnpj(orgao.getCnpj())) {
                throw new IllegalArgumentException("Já existe um órgão com este CNPJ.");
            }

            //pega as validacoes do usuario service
            super.save(orgao);
            return orgaoRepository.save(orgao);
        }

    @Override
    public Orgao update(Long id, Usuario usuarioAtualizado) {
        if (!(usuarioAtualizado instanceof Orgao)) {
            throw new IllegalArgumentException("O usuário informado não é um Órgão.");
        }

        Orgao orgaoAtualizado = (Orgao) usuarioAtualizado;

        Orgao existente = orgaoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Órgão com id " + id + " não encontrado."));

        super.update(id, orgaoAtualizado);

        existente.setCnpj(orgaoAtualizado.getCnpj());
        existente.setTipoOrgao(orgaoAtualizado.getTipoOrgao());

        return orgaoRepository.save(existente);
    }



    }


