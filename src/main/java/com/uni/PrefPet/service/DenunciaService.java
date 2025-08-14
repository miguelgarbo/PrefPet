package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Denuncia;
import com.uni.PrefPet.model.Usuario;
import com.uni.PrefPet.repository.DenunciaRepository;
import com.uni.PrefPet.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DenunciaService {


    @Autowired
    private DenunciaRepository denunciaRepository;
    private UsuarioRepository usuarioRepository;

    ///crud basico

    public Denuncia save(Denuncia denuncia){

        if(denuncia.getUsuario()==null){
            Usuario usuarioAnonimo = new Usuario();
            usuarioAnonimo.setNome("Usuário Anônimo");
            usuarioAnonimo.setCPF(null);
            usuarioAnonimo = usuarioRepository.save(usuarioAnonimo);
            denuncia.setUsuario(usuarioAnonimo);
        }else {
            // Se houver usuário, verifica se já existe um CPF igual
            String cpf = denuncia.getUsuario().getCPF();
            if (cpf != null && usuarioRepository.findByCPF(cpf).isPresent()) {
                throw new IllegalArgumentException("Já existe um usuário com este CPF.");
            }
        }

        return denunciaRepository.save(denuncia);
    }

    public Denuncia findById(Long id){
        return denunciaRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Denuncia Não Encontradaa")
        );
    }

    public List<Denuncia> findAll(){
        return denunciaRepository.findAll();
    }

    public String delete(Long id){

        if(!existById(id)){
        }
        denunciaRepository.deleteById(id);

        return "denuncia  Deletada com Sucesso";
    }

    public boolean existById(Long id) {
        if (!denunciaRepository.existsById(id)) {
            throw new EntityNotFoundException("denuncia  Não Encontrada");
        }
        return true;
    }


    public Denuncia update(Long id, Denuncia denunciaAtualizada) {

        Denuncia denunciaSelecionada = denunciaRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("denuncia  Não Encontrada")
        );



        if (denunciaAtualizada.getUsuario() != null) {
            denunciaSelecionada.setUsuario(denunciaAtualizada.getUsuario());
        }

        if (denunciaAtualizada.getEspecie() != null) {
            denunciaSelecionada.setEspecie(denunciaAtualizada.getEspecie());
        }

        if (denunciaAtualizada.getTipo() != null) {
            denunciaSelecionada.setTipo(denunciaAtualizada.getTipo());
        }

        if (denunciaAtualizada.getDescricao() != null) {
            denunciaSelecionada.setDescricao(denunciaAtualizada.getDescricao());
        }

        if (denunciaAtualizada.getStatus() != null) {
            denunciaSelecionada.setStatus(denunciaAtualizada.getStatus());
        }

        if (denunciaAtualizada.getContatosNotificados() != null) {
            denunciaSelecionada.setContatosNotificados(denunciaAtualizada.getContatosNotificados());
        }
        if (denunciaAtualizada.getLocalizacao() != null) {
            denunciaSelecionada.setLocalizacao(denunciaAtualizada.getLocalizacao());
        }
        if (denunciaAtualizada.getDataCriacao() != null) {
            denunciaSelecionada.setDataCriacao(denunciaAtualizada.getDataCriacao());
        }



        return denunciaRepository.save(denunciaSelecionada);    }

    ///fim crud basico

    //serviços especificos:




    //
    
}
