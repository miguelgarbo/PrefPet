package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Enum.Role;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.model.Usuarios.Usuario;
import com.uni.PrefPet.repository.auth.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public UsuarioService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }



    public Usuario findById(Long id){
        return  usuarioRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Usuario Nao Encontrado"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public String deleteUserGlobal(Long id, Long idCurrentUser) {

        if(id == null || idCurrentUser == null){
            throw new RuntimeException("IDs inválidos");
        }

        Usuario usuarioParaDeletar = findById(id);
        Usuario usuarioLogado = findById(idCurrentUser);

            var role = usuarioLogado.getRole();

        if (role == Role.ADMIN) {
            usuarioRepository.deleteById(id);
            return "Usuário com id " + id + " foi excluído";
        }

        if (!idCurrentUser.equals(id)) {
            throw new RuntimeException("Você não tem permissão para apagar outro usuário");
        }

        usuarioRepository.deleteById(id);
        return "Usuário com id " + id + " foi excluído com sucesso.";    }


    public String gerarHashSenha(String senha){
        var hashSenha = passwordEncoder.encode(senha);
        return hashSenha;
    }

    public Usuario preValidacaoUsuarioSave(Usuario usuario) {

        var emailExiste = usuarioRepository.existsByEmail(usuario.getEmail());

        if (emailExiste){
            throw new IllegalArgumentException("Esse Email Já Possui uma Conta no Sistema, Tente o Login");
        }

        var cpfExiste = usuarioRepository.existsByCpf(usuario.getCpf());

        if (cpfExiste){
            throw new IllegalArgumentException("Esse CPF Já Possui uma Conta no Sistema, Tente o Login");
        }

        var telefoneExiste = usuarioRepository.existsByTelefone(usuario.getTelefone());

        if (telefoneExiste){
            throw new IllegalArgumentException("Esse Telefone Já Possui uma Conta no Sistema, Tente o Login");
        }

        var cnpjExiste = usuarioRepository.existsByCnpj(usuario.getCnpj());

        if (cnpjExiste) {
            throw new IllegalArgumentException("Esse CNPJ Já Possui uma Conta no Sistema, Tente o Login");
        }

        usuario.setSenha(gerarHashSenha(usuario.getSenha()));

        return usuario;
    }

    public Usuario preValidacaoUsuarioUpdate(Long id, Usuario usuario) {

        var usuarioParaAtualizar = findById(id);

        var emailExiste = usuarioRepository.existsByEmailAndIdNot(usuario.getEmail(), id);

        if (emailExiste){
            throw new IllegalArgumentException("Esse Email Já Possui uma Conta no Sistema");
        }

        var cpfExiste = usuarioRepository.existsByCpfAndIdNot(usuario.getCpf(), id);

        if (cpfExiste){
            throw new IllegalArgumentException("Esse CPF Já Possui uma Conta no Sistema");
        }

        var telefoneExiste = usuarioRepository.existsByTelefoneAndIdNot(usuario.getTelefone(), id);

        if (telefoneExiste){
            throw new IllegalArgumentException("Esse Telefone Já Possui uma Conta no Sistema");
        }

        if (usuario.getCnpj() != null) {
            boolean cnpjExiste = usuarioRepository.existsByCnpjAndIdNot(usuario.getCnpj(), id);

            if (cnpjExiste) {
                throw new IllegalArgumentException("Esse CNPJ já possui uma conta no sistema");
            }
        }


        //update part
        usuarioParaAtualizar.setEstado(usuario.getEstado());
        usuarioParaAtualizar.setCidade(usuario.getCidade());
        usuarioParaAtualizar.setCep(usuario.getCep());
        usuarioParaAtualizar.setCnpj(usuario.getCnpj());
        usuarioParaAtualizar.setNome(usuario.getNome());
        usuarioParaAtualizar.setCpf(usuario.getCpf());
        usuarioParaAtualizar.setTelefone(usuario.getTelefone());
        usuarioParaAtualizar.setImagemUrlPerfil(usuario.getImagemUrlPerfil());
        usuarioParaAtualizar.setEmail(usuario.getEmail());
        usuarioParaAtualizar.setSenha(usuario.getSenha());

        return usuarioParaAtualizar;
    }

}
