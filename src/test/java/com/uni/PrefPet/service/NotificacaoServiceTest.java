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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

//anotacoes para ele quebrar o banco toda vez
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

    AplicacaoVacina aplicacaoVacinaNull = new AplicacaoVacina();

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
        tutor.setCpf("123.456.789-09");
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
        aplicacaoVacina.setDataAplicacao(LocalDate.now());
        aplicacaoVacina.setDataValidade(LocalDate.now());

        aplicacaoVacina2.setVacina(vacina);
        aplicacaoVacina2.setAnimal(animal);
        aplicacaoVacina2.setLote("1234");
        aplicacaoVacina2.setVeterinario(veterinario);
        aplicacaoVacina2.setNumeroDose(1);
        aplicacaoVacina2.setDataAplicacao(LocalDate.now());
        aplicacaoVacina2.setDataValidade(LocalDate.now().plusDays(3));

        aplicacaoVacina3.setVacina(vacina);
        aplicacaoVacina3.setAnimal(animal);
        aplicacaoVacina3.setLote("1234");
        aplicacaoVacina3.setVeterinario(veterinario);
        aplicacaoVacina3.setNumeroDose(1);
        aplicacaoVacina3.setDataAplicacao(LocalDate.now());
        aplicacaoVacina3.setDataValidade(LocalDate.now().minusDays(5));



        aplicacaoVacinaNull.setDataAplicacao(LocalDate.now());
        aplicacaoVacinaNull.setDataValidade(LocalDate.now().plusYears(100));
        aplicacaoVacinaNull.setVacina(vacina);
        aplicacaoVacinaNull.setVeterinario(veterinario);
        aplicacaoVacinaNull.setAnimal(animal);
        aplicacaoVacinaNull.setNumeroDose(4);

    }

    @Test
    @DisplayName("Teste integração - deve registrar uma notificação padrao do sistema")
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
    @DisplayName("Teste integração - Deve retornar todas as nots do banco")
    void findAll() {

        var resposta = notificacaoService.findAll();

        Assertions.assertEquals(notificacoes.get(0).getTexto(),resposta.get(0).getTexto());
    }



    @Test
    @DisplayName("Teste integração - deve retornar uma notificacao por id")
    void findById() {

        var resposta = notificacaoService.findById(1L);

        Assertions.assertEquals(notificacaoPadrao.getTexto(), resposta.getTexto());
    }

    @Test
    @DisplayName("Teste integração - deve lançar exceção ao buscar por id a notificacao")
    void findByTutorId() {
        var resposta = notificacaoService.findByTutorDestinatario(1L);
        Assertions.assertEquals(resposta.get(0).getId(), notificacoes.get(0).getId());
    }

    @Test
    @DisplayName("Teste integração - deve retornar atualizar uma notificacao ")
    void update() {
        var resposta = notificacaoService.update(1L, outraNotificacao);
        Assertions.assertEquals(resposta.getTexto(), "Texto Updated");
    }

    @Test
    @DisplayName("Teste integração - deve lançar exceção ao atualizar a notificacao")
    void updateError() {

        var exception =  Assertions.assertThrows(EntityNotFoundException.class, ()->{
            notificacaoService.update(10L, outraNotificacao);
        });

        Assertions.assertEquals("Notificacao com id " + 10 + " não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste integração - deve deletar uma notificacao")
    void delete() {
        var mensagem = notificacaoService.delete(1L);
        Assertions.assertEquals(mensagem, "Notificacao com id 1 foi excluído com sucesso.");
    }

    @Test
    @DisplayName("Teste integração - deve lançar exceção ao deletar a notificacao inexistente")
    void deleteError() {

       var exception =  Assertions.assertThrows(EntityNotFoundException.class, ()->{
            notificacaoService.delete(10L);
        });

        Assertions.assertEquals("Notificacao com id " + 10 + " não encontrado.", exception.getMessage());
    }




    @Test
    @DisplayName("Teste integração - gerar uma notifcacao se a data estiver na condição correta")
    void gerarNotificacaoDataValidadeVacina() {
        var resposta = notificacaoService.gerarNotificacaoDataValidadeVacina(aplicacaoVacina);

        System.out.println(resposta);

            Notificacao notificacaoCriada = new Notificacao();
            notificacaoCriada.setTexto("A vacina " + aplicacaoVacina.getVacina().getNome() + " do seu animal " + aplicacaoVacina.getAnimal().getNome() + " vence hoje! Não esqueça de renovar.");
            notificacaoCriada.setNivel(2);
            notificacaoCriada.setTutorDestinatario(aplicacaoVacina.getAnimal().getTutor());

        Assertions.assertEquals(resposta.getNivel(),notificacaoCriada.getNivel());
        Assertions.assertEquals(resposta.getTexto(),notificacaoCriada.getTexto());

    }

    @Test
    @DisplayName("Teste integração - gerar uma notifcacao se a data estiver na condição correta 2")
    void gerarNotificacaoDataValidadeVacina2() {

        var resposta = notificacaoService.gerarNotificacaoDataValidadeVacina(aplicacaoVacina2);

        Notificacao notificacaoCriada = new Notificacao();
        notificacaoCriada.setTexto("A vacina " + aplicacaoVacina2.getVacina().getNome() +
                " do seu animal " + aplicacaoVacina2.getAnimal().getNome() +
                " vence em " + 3 + " dia(s). Agende a renovação.");

        notificacaoCriada.setNivel(1);
        notificacaoCriada.setTutorDestinatario(aplicacaoVacina2.getAnimal().getTutor());

        Assertions.assertEquals(resposta.getNivel(),notificacaoCriada.getNivel());
        Assertions.assertEquals(resposta.getTexto(),notificacaoCriada.getTexto());

    }

    @Test
    @DisplayName("Teste integração - gerar uma notifcacao se a data estiver na condição correta 3")
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
    @DisplayName("Teste integração - retornar null se a mensagem nao tiver conteudo")
    void gerarNotificacaoDataValidadeVacina4() {

        var resposta = notificacaoService.gerarNotificacaoDataValidadeVacina(aplicacaoVacinaNull);

        Assertions.assertNull(resposta);

    }

    @Test
    @DisplayName("Teste integração - gerar um convite de tranferencia de tutor")
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
    @DisplayName("Teste integração - lançar uma excessão ao gerar um convite de tranferencia de tutor")
    void gerarConviteError(){

        Assertions.assertThrows(IllegalArgumentException.class, ()->{

            notificacaoService.gerarConvite(tutorDestinatario.getId(),tutorDestinatario.getId(),animal.getId());
        });

    }

    @Test
    @DisplayName("Teste integração - deve aceitar um convite de tranferencia de tutor")
    void conviteAceito() {

        notificacaoService.conviteAceito(convite.getId());

        Assertions.assertTrue(convite.getAceito());
    }

    @Test
    @DisplayName("Teste integração - gerar uma exceção ao aceitar convite de tranferencia de tutor")
    void conviteAceitoError() {

        Assertions.assertThrows(RuntimeException.class, ()->{
            notificacaoService.conviteAceito(20L);
        });
    }

    @Test
    @DisplayName("Teste integração - gerar um nots de confirmação de tranferencia de tutor")
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