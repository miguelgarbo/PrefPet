package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Notificacao;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.repository.NotificacaoRepository;
import com.uni.PrefPet.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class NotificacaoServiceTest {

    @Autowired
    NotificacaoService notificacaoService;

    @Autowired
    NotificacaoRepository notificacaoRepository;

    @Autowired
    TutorRepository tutorRepository;

    Tutor tutorDestinatario = new Tutor();

    Tutor tutorRemetente =new Tutor();

    Notificacao notificacaoPadrao = new Notificacao();

    Notificacao convite = new Notificacao();

    Notificacao outraNotificacao = new Notificacao();

    @BeforeEach
    void setUp(){

        notificacaoRepository.deleteAll();

        tutorDestinatario.setNome("Miguel");
        tutorDestinatario.setEmail("miguel@example.com");
        tutorDestinatario.setCpf("913.776.830-10");
        tutorDestinatario.setCep("65040-717");
        tutorDestinatario.setSenha("senha123");
        tutorDestinatario.setEstado("MA");
        tutorDestinatario.setCidade("São Luís");

        tutorRemetente.setNome("Ana");
        tutorRemetente.setEmail("ana@example.com");
        tutorRemetente.setCpf("635.018.720-20");
        tutorRemetente.setCep("57072-362");
        tutorRemetente.setSenha("senha123");
        tutorRemetente.setEstado("AL");
        tutorRemetente.setCidade("Maceió");

        tutorRepository.save(tutorDestinatario);
        tutorRepository.save(tutorRemetente);

        notificacaoPadrao.setTexto("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");
        notificacaoPadrao.setNivel(1);
        notificacaoPadrao.setTutorDestinatario(tutorDestinatario);

        convite.setTexto("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Aceita O Convite?");
        convite.setAceito(false);
        convite.setNivel(1);
        convite.setTutorDestinatario(tutorDestinatario);
        convite.setTutorRemetente(tutorRemetente);

        notificacaoRepository.save(notificacaoPadrao);
        notificacaoRepository.save(convite);
    }

    @Test
    @DisplayName("deve registrar uma notificação padrao do sistema")
    void test_create_notificacao(){
        Notificacao notificacao = new Notificacao();

        notificacao.setTexto("Teste Not");
        notificacao.setTutorDestinatario(tutorDestinatario);
        notificacao.setNivel(1);

        var resposta = notificacaoService.save(notificacao);

        Assertions.assertNotNull(resposta.getId());
        Assertions.assertEquals(resposta, notificacao);
    }





}