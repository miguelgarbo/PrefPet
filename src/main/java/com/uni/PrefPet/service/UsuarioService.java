package com.uni.PrefPet.service;
import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.model.Usuarios.Orgao;
import com.uni.PrefPet.model.Usuarios.Veterinario;
import com.uni.PrefPet.repository.OrgaoRepository;
import com.uni.PrefPet.repository.UsuarioRepository;
import com.uni.PrefPet.repository.VeterinarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private OrgaoRepository orgaoRepository;
    private VeterinarioRepository veterinarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {

        validarAtributosPadroes(usuario);
        
        if(usuario instanceof Orgao orgao){
            validarOrgao(orgao);
        }

        if(usuario instanceof Veterinario){



        }


        
        



        return usuarioRepository.save(usuario);
    }


    public void validarVeterinario(Veterinario veterinario){

        if (veterinarioRepository.existsByCRMV(veterinario.getCRMV())) {
            throw new IllegalArgumentException("Já existe um usuário com este CRMV.");
        }

        if(veterinario.getCRMV() ==null && veterinario.getCRMV().isBlank()){
            throw new IllegalArgumentException("CRMV NAO PODE SER NULO");

        }

    }


    public void validarAtributosPadroes(Usuario usuario){
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF.");
        }

        if (usuarioRepository.existsByTelefone(usuario.getTelefone())) {
            throw new IllegalArgumentException("Já existe um usuário com este telefone.");
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário com este email.");
        }
    }

    public void validarOrgao(Orgao orgao){

        if(orgao.getCnpj() == null && orgao.getCnpj().isBlank()){
            throw new IllegalArgumentException("CNPJ Não pode ser nulo");
        }
        if(orgao.getTipoOrgao() == null ){
            throw new IllegalArgumentException("Tipo do Orgao Não pode ser nulo");
        }
        if(orgaoRepository.existsByCnpj(orgao.getCnpj())){
            throw new IllegalArgumentException("já Existe um Usuario com esse CNPJ");
        }
    }


    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Usuario update(Long id, Usuario usuarioAtualizado) {
        Usuario existente = usuarioRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Usuário com id " + id + " não encontrado."));

        if (!existente.getCpf().equals(usuarioAtualizado.getCpf()) &&
                usuarioRepository.findByCpf(usuarioAtualizado.getCpf()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com este CPF.");
        }

        if (!existente.getTelefone().equals(usuarioAtualizado.getTelefone()) &&
                usuarioRepository.findByTelefone(usuarioAtualizado.getTelefone()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com este telefone.");
        }

        existente.setNome(usuarioAtualizado.getNome());
        existente.setCpf(usuarioAtualizado.getCpf());
        existente.setTelefone(usuarioAtualizado.getTelefone());
//        existente.setAnimais(usuarioAtualizado.getAnimais());
        return usuarioRepository.save(existente);
    }

    public String delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário com id " + id + " não encontrado.");
        }
        usuarioRepository.deleteById(id);
        return "Usuário com id " + id + " foi excluído com sucesso.";
    }


    //serviços especificos:

    public boolean existsByCPF(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public boolean existsByTelefone(String telefone){
        return usuarioRepository.existsByTelefone(telefone);
    }

    public List<Usuario> findByNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o nome informado"));
    }

    public List<Usuario> findByCPF(String cpf) {
        return usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o CPF informado"));
    }

    public List<Usuario> findByTelefone(String telefone) {
        return usuarioRepository.findByTelefone(telefone)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o telefone informado"));
    }

    public List<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o email informado"));
    }

    //fim dos serviços especificos

}
