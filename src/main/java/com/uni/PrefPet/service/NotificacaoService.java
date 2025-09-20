package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.AplicacaoVacina;
import com.uni.PrefPet.model.Notificacao;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.repository.AnimalRepository;
import com.uni.PrefPet.repository.NotificacaoRepository;
import com.uni.PrefPet.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificacaoService {
    
    @Autowired
    private NotificacaoRepository notificacaoRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private AnimalRepository animalRepository;

    public List<Notificacao> findAll() {
        return notificacaoRepository.findAll();
    }

    public Notificacao save(Notificacao publicacao) {
        return notificacaoRepository.save(publicacao);
    }

    public Notificacao findById(Long id) {
        return notificacaoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Notificacao update(Long id, Notificacao notificacaoAtualizada) {
        Notificacao notificacaoSelecionada = notificacaoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Notificacao com id " + id + " não encontrado."));
        
        if (notificacaoAtualizada.getTexto() != null) {
            notificacaoSelecionada.setTexto(notificacaoAtualizada.getTexto());
        }
        return notificacaoRepository.save(notificacaoSelecionada);
    }

    public String delete(Long id) {
        if (!notificacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Publicaco com id " + id + " não encontrado.");
        }
        notificacaoRepository.deleteById(id);
        return "Notificacao com id " + id + " foi excluído com sucesso.";
    }

    public List<Notificacao> findByTutorId(Long id){
        return notificacaoRepository.findByTutorId(id);
    }


    public Notificacao gerarNotificacaoDataValidadeVacina(AplicacaoVacina aplicacaoVacina){

        Tutor tutor = aplicacaoVacina.getAnimal().getTutor();

        long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), aplicacaoVacina.getDataValidade());
        String mensagem = null;
        Integer nivel = null;

        if (diasRestantes > 0 && diasRestantes <= 7) {
            mensagem = "A vacina " + aplicacaoVacina.getVacina().getNome() +
                    " do seu animal " + aplicacaoVacina.getAnimal().getNome() +
                    " vence em " + diasRestantes + " dia(s). Agende a renovação.";
            nivel = 1;
        }

        if (LocalDate.now().isEqual(aplicacaoVacina.getDataValidade())) {
            mensagem = "A vacina " + aplicacaoVacina.getVacina().getNome() +
                    " do seu animal " + aplicacaoVacina.getAnimal().getNome() +
                    " vence hoje! Não esqueça de renovar.";
            nivel = 2;

        }


        if(LocalDate.now().isAfter(aplicacaoVacina.getDataValidade())){

            mensagem="A vacina " + aplicacaoVacina.getVacina().getNome() +
                    " do seu animal " + aplicacaoVacina.getAnimal().getNome() +
                    " venceu em " + aplicacaoVacina.getDataValidade() +
                    ". Procure um veterinário para a renovação.";
            nivel = 3;
        }
        if (mensagem != null) {
            Notificacao notificacao = new Notificacao();
            notificacao.setTutor(tutor);
            notificacao.setTexto(mensagem);
            notificacao.setNivel(nivel);
            return notificacao;
        }

        return null;
}

    public Notificacao gerarConvite(Long tutorDestinatario_id,Long tutor_id, Long animal_id){

        Tutor tutorDestinario = tutorRepository.findById(tutorDestinatario_id).get();
        Tutor tutor = tutorRepository.findById(tutor_id).get();
        Animal animal = animalRepository.findById(animal_id).get();

        Notificacao convite = new Notificacao();
        convite.setTutor(tutorDestinario);
        convite.setTexto("Você foi convidado(a) para se tornar o tutor do animal "
                + animal.getNome()
                + ", atualmente sob tutela de "
                + tutor.getNome()
                + ".\n"
                + "Deseja aceitar a transferência de tutela?\n");

        convite.setNivel(1);
        convite.setAceito(false);
        notificacaoRepository.save(convite);

        return convite;
    }
}