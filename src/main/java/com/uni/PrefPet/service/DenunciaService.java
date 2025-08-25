package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Denuncia;
import com.uni.PrefPet.model.Enum.TipoDenuncia;
import com.uni.PrefPet.model.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DenunciaService {

//    @Autowired
//    private DenunciaRepository denunciaRepository;
//    private UsuarioService usuarioService;
//
//    /// CRUD básico
//    public Denuncia save(Denuncia denuncia) {
//        if(denuncia.getUsuario()==null){
//            denuncia.setAnonima(true);
//        }
//
//        if(denuncia.isAnonima()){
//            denuncia.setUsuario(null);
//        }
//
//        denuncia.setDataCriacao(LocalDateTime.now());
//        return denunciaRepository.save(denuncia);
//    }
//
//    public Denuncia findById(Long id) {
//        return denunciaRepository.findById(id).orElseThrow(() ->
//                new EntityNotFoundException("Denúncia não encontrada")
//        );
//    }
//
//    public List<Denuncia> findAll() {
//        return denunciaRepository.findAll();
//    }
//
//    public String delete(Long id) {
//        if (!existById(id)) {
//            throw new EntityNotFoundException("Denúncia não encontrada");
//        }
//        denunciaRepository.deleteById(id);
//        return "Denúncia deletada com sucesso";
//    }
//
//    public boolean existById(Long id) {
//        if (!denunciaRepository.existsById(id)) {
//            throw new EntityNotFoundException("Denúncia não encontrada");
//        }
//        return true;
//    }
//
//    public Denuncia update(Long id, Denuncia denunciaAtualizada) {
//        Denuncia denunciaSelecionada = denunciaRepository.findById(id).orElseThrow(() ->
//                new EntityNotFoundException("Denúncia não encontrada")
//        );
//
//        if (denunciaAtualizada.getUsuario() != null) {
//            denunciaSelecionada.setUsuario(denunciaAtualizada.getUsuario());
//        }
//        if (denunciaAtualizada.getEspecie() != null) {
//            denunciaSelecionada.setEspecie(denunciaAtualizada.getEspecie());
//        }
//        if (denunciaAtualizada.getTipo() != null) {
//            denunciaSelecionada.setTipo(denunciaAtualizada.getTipo());
//        }
//        if (denunciaAtualizada.getDescricao() != null) {
//            denunciaSelecionada.setDescricao(denunciaAtualizada.getDescricao());
//        }
//        if (denunciaAtualizada.getStatus() != null) {
//            denunciaSelecionada.setStatus(denunciaAtualizada.getStatus());
//        }
//        if (denunciaAtualizada.getContatosNotificados() != null) {
//            denunciaSelecionada.setContatosNotificados(denunciaAtualizada.getContatosNotificados());
//        }
//        if (denunciaAtualizada.getLocalizacao() != null) {
//            denunciaSelecionada.setLocalizacao(denunciaAtualizada.getLocalizacao());
//        }
//        if (denunciaAtualizada.getDataCriacao() != null) {
//            denunciaSelecionada.setDataCriacao(denunciaAtualizada.getDataCriacao());
//        }
//
//        return denunciaRepository.save(denunciaSelecionada);
//    }
//
//    /// Filtros extras
//    public List<Denuncia> findByTipo(TipoDenuncia tipo) {
//        return denunciaRepository.findByTipo(tipo);
//    }
//
//    public List<Denuncia> findByStatus(StatusDenuncia status) {
//        return denunciaRepository.findByStatus(status);
//    }
//
//    public List<Denuncia> findByUsuario(Usuario usuario) {
//        return denunciaRepository.findByUsuario(usuario);
//    }
//
//    public List<Denuncia> findAnonimas() {
//        return denunciaRepository.findByAnonimaTrue();
//    }
//
//    public List<Denuncia> findNaoAnonimas() {
//        return denunciaRepository.findByAnonimaFalse();
//    }
//
//    public List<Denuncia> findByEspecieNome(String especie) {
//        return denunciaRepository.findByEspecieNomeContainingIgnoreCase(especie);
//    }



}
