package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.AplicacaoVacina;
import com.uni.PrefPet.model.Notificacao;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.repository.NotificacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificacaoService {
    
    @Autowired
     NotificacaoRepository notificacaoRepository;
    @Autowired
     TutorService tutorService;
    @Autowired
     AnimalService animalService;

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
        return notificacaoRepository.findByTutorDestinatarioId(id);
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
            notificacao.setTutorDestinatario(tutor);
            notificacao.setTexto(mensagem);
            notificacao.setNivel(nivel);
            return notificacao;
        }

        return null;
}

    public Notificacao gerarConvite(Long tutorDestinatario_id, Long tutorRemetente_id, Long animal_id) {

        Tutor tutorDestinatario = tutorService.findById(tutorDestinatario_id);
        Tutor tutorRemetente = tutorService.findById(tutorRemetente_id);
        Animal animal = animalService.findById(animal_id);

        Notificacao convite = new Notificacao();
        convite.setTutorDestinatario(tutorDestinatario);
        convite.setTutorRemetente(tutorRemetente);
        convite.setAnimal(animal);

        convite.setTexto("Você foi convidado(a) para se tornar o tutor do animal "
                + animal.getNome()
                + ", atualmente sob tutela de "
                + tutorRemetente.getNome()
                + ".\nDeseja aceitar a transferência de tutela?");
        convite.setNivel(1);
        convite.setAceito(false);

        return notificacaoRepository.save(convite);
    }


    public void conviteAceito(Long notificacaoId) {
        Notificacao notificacao = notificacaoRepository.findById(notificacaoId)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));

        Tutor destinatario = notificacao.getTutorDestinatario();
        Tutor remetente = notificacao.getTutorRemetente();
        Animal animal = notificacao.getAnimal();

        animal.setTutor(destinatario);
        animalService.save(animal);

        notificacao.setAceito(true);
        notificacaoRepository.save(notificacao);

        gerarNotificacaoConviteAceito(destinatario, remetente, animal);
    }


    public void gerarNotificacaoConviteAceito(Tutor destinatario, Tutor remetente, Animal animal) {

        Notificacao notificacaoNovoTutor = new Notificacao();
        notificacaoNovoTutor.setTutorDestinatario(destinatario);
        notificacaoNovoTutor.setTexto("Você agora é oficialmente o tutor do animal " + animal.getNome() + "!");
        notificacaoNovoTutor.setNivel(4);
        notificacaoNovoTutor.setAceito(true);

        Notificacao notificacaoAntigoTutor = new Notificacao();
        notificacaoAntigoTutor.setTutorDestinatario(remetente);
        notificacaoAntigoTutor.setTexto("A transferência do animal " + animal.getNome()
                + " para '" + destinatario.getNome() + "' foi concluída com sucesso!");
        notificacaoAntigoTutor.setNivel(4);
        notificacaoAntigoTutor.setAceito(true);

        notificacaoRepository.save(notificacaoNovoTutor);
        notificacaoRepository.save(notificacaoAntigoTutor);
    }

}