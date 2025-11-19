package com.uni.PrefPet.service;
import com.uni.PrefPet.exception.DeniedAcessException;
import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private UsuarioService usuarioService;

    public List<Tutor> findAll() {
        return tutorRepository.findAll();
    }

    public Tutor save(Tutor tutor) {

        usuarioService.preValidacaoUsuarioSave(tutor);

        return tutorRepository.save(tutor);
    }


    public Tutor findById(Long id) {
        return tutorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Nenhum Tutor Com esse Id"));
    }


    public Tutor update(Long id, Tutor tutorAtualizado) {

        Tutor tutorValidado = (Tutor) usuarioService.preValidacaoUsuarioUpdate(id, tutorAtualizado);
        System.out.println("é pra ter o id aqui :" + tutorValidado.getId());

        tutorValidado.setAnimais(tutorAtualizado.getAnimais());
        tutorValidado.setNotificacoesRecebidas(tutorAtualizado.getNotificacoesRecebidas());
        tutorValidado.setNotificacoesEnviadas(tutorAtualizado.getNotificacoesEnviadas());

        return tutorRepository.save(tutorValidado);
    }



    public String delete(Long id) {
        if (!tutorRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário com id " + id + " não encontrado.");
        }
        tutorRepository.deleteById(id);
        return "Usuário com id " + id + " foi excluído com sucesso.";
    }


    //serviços especificos:


    public Tutor findByNome(String nome) {
        return tutorRepository.findByNomeContainingIgnoreCase(nome)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o nome informado"));
    }

    public Tutor findByCPF(String cpf) {
        return tutorRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o CPF informado"));
    }


    public Tutor findByEmail(String email) {
        return tutorRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum usuário encontrado com o email informado"));
    }

    //fim dos serviços especificos

}
