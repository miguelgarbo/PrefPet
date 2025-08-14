package com.uni.PrefPet.service;
import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listAll() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        if (usuarioRepository.findByNome(usuario.getNome()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com este nome.");
        }

        if (usuarioRepository.findByCPF(usuario.getCPF()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF.");
        }

        if (usuarioRepository.findByTelefone(usuario.getTelefone()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com este telefone.");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Usuario update(Long id, Usuario usuarioAtualizado) {
        Usuario existente = usuarioRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Usuário com id " + id + " não encontrado."));

        if (!existente.getNome().equalsIgnoreCase(usuarioAtualizado.getNome()) &&
                usuarioRepository.findByNome(usuarioAtualizado.getNome()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com este nome.");
        }

        if (!existente.getCPF().equals(usuarioAtualizado.getCPF()) &&
                usuarioRepository.findByCPF(usuarioAtualizado.getCPF()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF.");
        }

        if (!existente.getTelefone().equals(usuarioAtualizado.getTelefone()) &&
                usuarioRepository.findByTelefone(usuarioAtualizado.getTelefone()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com este telefone.");
        }

        existente.setNome(usuarioAtualizado.getNome());
        existente.setCPF(usuarioAtualizado.getCPF());
        existente.setTelefone(usuarioAtualizado.getTelefone());
        existente.setAnimais(usuarioAtualizado.getAnimais());
        return usuarioRepository.save(existente);
    }

    public String delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário com id " + id + " não encontrado.");
        }
        usuarioRepository.deleteById(id);
        return "Usuário com id " + id + " foi excluído com sucesso.";
    }
}
