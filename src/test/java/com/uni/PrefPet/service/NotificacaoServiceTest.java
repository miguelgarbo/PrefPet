package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.AplicacaoVacina;
import com.uni.PrefPet.model.Notificacao;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.model.Usuarios.Veterinario;
import com.uni.PrefPet.model.Vacina;
import com.uni.PrefPet.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class NotificacaoServiceTest {

    @Autowired
    NotificacaoService notificacaoService;

    @Autowired
    NotificacaoRepository notificacaoRepository;

    @Autowired
    TutorRepository tutorRepository;

    @Autowired
    AplicacaoVacinaService aplicacaoVacinaService;

    @Autowired
    VacinaService vacinaService;

    @Autowired
    AnimalService animalService;

    @Autowired
    VeterinarioService veterinarioService;

    @Autowired
    AplicacaoVacinaRepository aplicacaoVacinaRepository;

    @Autowired
    VeterinarioRepository veterinarioRepository;

    @Autowired
    AnimalRepository animalRepository;

    @Autowired
    VacinaRepository vacinaRepository;


    List<Notificacao> notificacoes = new ArrayList<>();

    Tutor tutorDestinatario = new Tutor();

    Tutor tutorRemetente =new Tutor();

    Notificacao notificacaoPadrao = new Notificacao();

    Notificacao convite = new Notificacao();

    Notificacao outraNotificacao = new Notificacao();

    AplicacaoVacina aplicacaoVacina = new AplicacaoVacina();

    AplicacaoVacina aplicacaoVacina2 = new AplicacaoVacina();

    AplicacaoVacina aplicacaoVacina3 = new AplicacaoVacina();

    Animal animal = new Animal();



    @BeforeEach
    void setUp(){
        aplicacaoVacinaRepository.deleteAll();
        animalRepository.deleteAll();
        vacinaRepository.deleteAll();
        veterinarioRepository.deleteAll();
        tutorRepository.deleteAll();
        notificacaoRepository.deleteAll();

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

        outraNotificacao.setTexto("Texto Updated");
        outraNotificacao.setNivel(2);
        outraNotificacao.setTutorDestinatario(tutorDestinatario);


        convite.setTexto("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Aceita O Convite?");
        convite.setAceito(false);
        convite.setNivel(1);
        convite.setTutorDestinatario(tutorDestinatario);
        convite.setTutorRemetente(tutorRemetente);
        convite.setAnimal(animal);



        notificacoes.add(notificacaoPadrao);
        notificacoes.add(convite);
        notificacaoRepository.save(notificacaoPadrao);
        notificacaoRepository.save(convite);

        Vacina vacina = new Vacina();
        vacina.setNome("v10");

        vacinaService.save(vacina);

        animal.setNome("Rex");
        animal.setEspecie("Canina");
        animal.setCor("Marrom");
        animal.setSexo("Macho");
        animal.setCastrado(true);
        animal.setMicrochip(true);
        animal.setNumeroMicrochip("1234567890");
        animal.setDataNascimento(LocalDate.of(2020, 5, 10));
        animal.setNaturalidade("Foz do Iguaçu");

        animalService.save(animal);

        Tutor tutor = new Tutor();
        tutor.setNome("Miguel Garbo");
        tutor.setTelefone("+5545999998888");
        tutor.setCep("85851-000");
        tutor.setCpf("123.456.789-09"); // CPF válido só pra exemplo de teste
        tutor.setCidade("Foz do Iguaçu");
        tutor.setEstado("PR");
        tutor.setSenha("senha123");
        tutor.setEmail("miguel.garbo@example.com");

        tutorRepository.save(tutor);

        Veterinario veterinario = new Veterinario();
        veterinario.setNome("Ana");
        veterinario.setTelefone("+5545999998878");
        veterinario.setCep("85851-000");
        veterinario.setCpf("434.484.890-00"); // CPF válido só pra exemplo de teste
        veterinario.setCidade("Foz do Iguaçu");
        veterinario.setEstado("PR");
        veterinario.setSenha("senha123");
        veterinario.setEmail("ana.garbo@example.com");
        veterinario.setCRMV("1234");

        veterinarioService.save(veterinario);

        aplicacaoVacina.setVacina(vacina);
        aplicacaoVacina.setAnimal(animal);
        aplicacaoVacina.setLote("1234");
        aplicacaoVacina.setVeterinario(veterinario);
        aplicacaoVacina.setNumeroDose(1);
        aplicacaoVacina.setDataAplicacao(LocalDate.of(2024, 10,16));

        aplicacaoVacina2.setVacina(vacina);
        aplicacaoVacina2.setAnimal(animal);
        aplicacaoVacina2.setLote("1234");
        aplicacaoVacina2.setVeterinario(veterinario);
        aplicacaoVacina2.setNumeroDose(1);
        aplicacaoVacina2.setDataAplicacao(LocalDate.of(2024, 10,21));


        aplicacaoVacina3.setVacina(vacina);
        aplicacaoVacina3.setAnimal(animal);
        aplicacaoVacina3.setLote("1234");
        aplicacaoVacina3.setVeterinario(veterinario);
        aplicacaoVacina3.setNumeroDose(1);
        aplicacaoVacina3.setDataAplicacao(LocalDate.of(2024, 10,13));

        aplicacaoVacinaService.save(aplicacaoVacina3,12);
        aplicacaoVacinaService.save(aplicacaoVacina2,12);
        aplicacaoVacinaService.save(aplicacaoVacina,12);

    }

    @Test
    @DisplayName("deve registrar uma notificação padrao do sistema")
    void save(){
        Notificacao notificacao = new Notificacao();

        notificacao.setTexto("Teste Not");
        notificacao.setTutorDestinatario(tutorDestinatario);
        notificacao.setNivel(1);

        var resposta = notificacaoService.save(notificacao);

        Assertions.assertNotNull(resposta.getId());
        Assertions.assertEquals(resposta, notificacao);
    }


    @Test
    @DisplayName("Deve retornar todas as nots do banco")
    void findAll() {

        var resposta = notificacaoService.findAll();

        Assertions.assertEquals(notificacoes.get(0).getTexto(),resposta.get(0).getTexto());
    }



    @Test
    void findById() {

        var resposta = notificacaoService.findById(1L);
        System.out.println(notificacaoPadrao.getTexto() + " -- " +resposta.getTexto());

        Assertions.assertEquals(notificacaoPadrao.getTexto(), resposta.getTexto());

    }

    @Test
    void update() {
        var resposta = notificacaoService.update(1L, outraNotificacao);
        Assertions.assertEquals(resposta.getTexto(), "Texto Updated");
    }

    @Test
    void updateError() {

        var exception =  Assertions.assertThrows(EntityNotFoundException.class, ()->{
            notificacaoService.update(10L, outraNotificacao);
        });

        Assertions.assertEquals("Notificacao com id " + 10 + " não encontrado.", exception.getMessage());
    }

    @Test
    void delete() {
        var mensagem = notificacaoService.delete(1L);
        Assertions.assertEquals(mensagem, "Notificacao com id 1 foi excluído com sucesso.");
    }

    @Test
    void deleteError() {

       var exception =  Assertions.assertThrows(EntityNotFoundException.class, ()->{
            notificacaoService.delete(10L);
        });

        Assertions.assertEquals("Notificacao com id " + 10 + " não encontrado.", exception.getMessage());
    }

    @Test
    void findByTutorId() {
        var resposta = notificacaoService.findByTutorDestinatario(1L);
        Assertions.assertEquals(resposta.get(0).getId(), notificacoes.get(0).getId());
    }


    @Test
    void gerarNotificacaoDataValidadeVacina() {

        var resposta = notificacaoService.gerarNotificacaoDataValidadeVacina(aplicacaoVacina);

            Notificacao notificacaoCriada = new Notificacao();
            notificacaoCriada.setTexto("A vacina " + aplicacaoVacina.getVacina().getNome() + " do seu animal " + aplicacaoVacina.getAnimal().getNome() + " vence hoje! Não esqueça de renovar.");
            notificacaoCriada.setNivel(2);
            notificacaoCriada.setTutorDestinatario(aplicacaoVacina.getAnimal().getTutor());

        Assertions.assertEquals(resposta.getNivel(),notificacaoCriada.getNivel());
        Assertions.assertEquals(resposta.getTexto(),notificacaoCriada.getTexto());

    }

    @Test
    void gerarNotificacaoDataValidadeVacina2() {

        var resposta = notificacaoService.gerarNotificacaoDataValidadeVacina(aplicacaoVacina2);

        Notificacao notificacaoCriada = new Notificacao();
        notificacaoCriada.setTexto("A vacina " + aplicacaoVacina2.getVacina().getNome() +
                " do seu animal " + aplicacaoVacina2.getAnimal().getNome() +
                " vence em " + 5 + " dia(s). Agende a renovação.");

        notificacaoCriada.setNivel(1);
        notificacaoCriada.setTutorDestinatario(aplicacaoVacina2.getAnimal().getTutor());

        Assertions.assertEquals(resposta.getNivel(),notificacaoCriada.getNivel());
        Assertions.assertEquals(resposta.getTexto(),notificacaoCriada.getTexto());

    }

    @Test
    void gerarNotificacaoDataValidadeVacina3() {

        var resposta = notificacaoService.gerarNotificacaoDataValidadeVacina(aplicacaoVacina3);

        Notificacao notificacaoCriada = new Notificacao();
        notificacaoCriada.setTexto("A vacina " + aplicacaoVacina3.getVacina().getNome() +
                " do seu animal " + aplicacaoVacina3.getAnimal().getNome() +
                " venceu em " + aplicacaoVacina3.getDataValidade() +
                ". Procure um veterinário para a renovação.");

        notificacaoCriada.setNivel(3);
        notificacaoCriada.setTutorDestinatario(aplicacaoVacina.getAnimal().getTutor());

        Assertions.assertEquals(resposta.getNivel(),notificacaoCriada.getNivel());
        Assertions.assertEquals(resposta.getTexto(),notificacaoCriada.getTexto());

    }

    @Test
    void gerarConvite() {

        var resposta = notificacaoService.gerarConvite(tutorDestinatario.getId(), tutorRemetente.getId(), animal.getId());

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

        Assertions.assertEquals(resposta.getTexto(), convite.getTexto());
    }

    @Test
    void gerarConviteError(){

        Assertions.assertThrows(IllegalArgumentException.class, ()->{

            notificacaoService.gerarConvite(tutorDestinatario.getId(),tutorDestinatario.getId(),animal.getId());
        });

    }

    @Test
    void conviteAceito() {

        notificacaoService.conviteAceito(convite.getId());

        Assertions.assertTrue(convite.getAceito());
    }

    @Test
    void conviteAceitoError() {

        Assertions.assertThrows(RuntimeException.class, ()->{
            notificacaoService.conviteAceito(20L);
        });
    }

    @Test
    void gerarNotificacaoConviteAceito() {

        notificacaoRepository.deleteAll();

        notificacaoService.gerarNotificacaoConviteAceito(tutorDestinatario, tutorRemetente, animal);

        var notsDestinatario = notificacaoService.findByTutorDestinatario(tutorDestinatario.getId());
        var notsRemetente = notificacaoService.findByTutorDestinatario(tutorRemetente.getId());

        Assertions.assertEquals("Você agora é oficialmente o tutor do animal "
                + animal.getNome() + "!", notsDestinatario.get(0).getTexto());

        Assertions.assertEquals(4, notsDestinatario.get(0).getNivel());
        Assertions.assertTrue(notsDestinatario.get(0).getAceito());

        Assertions.assertEquals("A transferência do animal " + animal.getNome()
                + " para '" + tutorDestinatario.getNome() + "' foi concluída com sucesso!"
                , notsRemetente.get(0).getTexto());

        Assertions.assertEquals(4, notsRemetente.get(0).getNivel());
        Assertions.assertTrue(notsRemetente.get(0).getAceito());
    }

}