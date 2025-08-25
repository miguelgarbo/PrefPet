package com.uni.PrefPet.service;
import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.model.Usuarios.UsuarioComum;
import com.uni.PrefPet.repository.UsuarioComumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioComumRepository usuarioComumRepository;

    public List<UsuarioComum> findAll() {
        return usuarioComumRepository.findAll();
    }

    public UsuarioComum save(UsuarioComum usuarioComum) {

        if (usuarioComumRepository.existsByCpf(usuarioComum.getCpf())) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF.");
        }

        if (usuarioComumRepository.existsByTelefone(usuarioComum.getTelefone())) {
            throw new IllegalArgumentException("Já existe um usuário com este telefone.");
        }

        if (usuarioComumRepository.existsByEmail(usuarioComum.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário com este email.");
        }

        return usuarioComumRepository.save(usuarioComum);
    }

    public UsuarioComum findById(Long id) {
        return usuarioComumRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public UsuarioComum update(Long id, UsuarioComum usuarioAtualizado) {
        UsuarioComum existente = usuarioComumRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Usuário com id " + id + " não encontrado."));

        if (!existente.getCpf().equals(usuarioAtualizado.getCpf()) &&
                usuarioComumRepository.findByCpf(usuarioAtualizado.getCpf()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF.");
        }

        if (!existente.getTelefone().equals(usuarioAtualizado.getTelefone()) &&
                usuarioComumRepository.findByTelefone(usuarioAtualizado.getTelefone()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com este telefone.");
        }

        existente.setNome(usuarioAtualizado.getNome());
        existente.setCpf(usuarioAtualizado.getCpf());
        existente.setTelefone(usuarioAtualizado.getTelefone());
        existente.setAnimais(usuarioAtualizado.getAnimais());
        return usuarioComumRepository.save(existente);
    }

    public String delete(Long id) {
        if (!usuarioComumRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário com id " + id + " não encontrado.");
        }
        usuarioComumRepository.deleteById(id);
        return "Usuário com id " + id + " foi excluído com sucesso.";
    }


    //serviços especificos:

    public boolean existsByCPF(String cpf) {
        return usuarioComumRepository.existsByCpf(cpf);
    }

    public boolean existsByEmail(String email) {
        return usuarioComumRepository.existsByEmail(email);
    }

    public boolean existsByTelefone(String telefone){
        return usuarioComumRepository.existsByTelefone(telefone);
    }

    public List<UsuarioComum> findByNome(String nome) {
        return usuarioComumRepository.findByNomeContainingIgnoreCase(nome)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o nome informado"));
    }

    public List<UsuarioComum> findByCPF(String cpf) {
        return usuarioComumRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o CPF informado"));
    }

    public List<UsuarioComum> findByTelefone(String telefone) {
        return usuarioComumRepository.findByTelefone(telefone)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o telefone informado"));
    }

    public List<UsuarioComum> findByEmail(String email) {
        return usuarioComumRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o email informado"));
    }

    //fim dos serviços especificos

}
