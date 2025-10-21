package com.uni.PrefPet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.AplicacaoVacina;
import com.uni.PrefPet.model.Notificacao;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.model.Usuarios.Veterinario;
import com.uni.PrefPet.model.Vacina;
import com.uni.PrefPet.service.AplicacaoVacinaService;
import com.uni.PrefPet.service.NotificacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Executable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificacaoController.class)
public class NotificacaoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    NotificacaoService notificacaoService;

    @MockitoBean
    AplicacaoVacinaService aplicacaoVacinaService;

    Notificacao notificacao = new Notificacao();

    Notificacao convite = new Notificacao();

    AplicacaoVacina aplicacaoVacina = new AplicacaoVacina();

    Animal animal = new Animal();

    Tutor tutor = new Tutor();

    Tutor tutor2 = new Tutor();

    Vacina vacina = new Vacina();

    Veterinario veterinario = new Veterinario();

    List<Notificacao> notificacoes = new ArrayList<>();

    @Autowired
    private ObjectMapper objectMapper;

    String notificacaoJson;

    @BeforeEach
    void setUp() throws Exception{

        veterinario.setId(1L);
        veterinario.setNome("Ana");
        veterinario.setTelefone("+5545999998878");
        veterinario.setCep("85851-000");
        veterinario.setCpf("434.484.890-00");
        veterinario.setCidade("Foz do Iguaçu");
        veterinario.setEstado("PR");
        veterinario.setSenha("senha123");
        veterinario.setEmail("anavet@example.com");
        veterinario.setCRMV("1234");

        animal.setId(1L);
        animal.setNome("Rex");
        animal.setEspecie("Canina");
        animal.setCor("Marrom");
        animal.setSexo("Macho");
        animal.setCastrado(true);
        animal.setMicrochip(true);
        animal.setNumeroMicrochip("1234567890");
        animal.setDataNascimento(LocalDate.of(2020, 5, 10));
        animal.setNaturalidade("Foz do Iguaçu");

        tutor.setId(1L);
        tutor.setNome("Miguel Garbo");
        tutor.setTelefone("+5545999998888");
        tutor.setCep("85851-000");
        tutor.setCpf("123.456.789-09");
        tutor.setCidade("Foz do Iguaçu");
        tutor.setEstado("PR");
        tutor.setSenha("senha123");
        tutor.setEmail("miguel.garbo@example.com");

        tutor2.setId(2L);
        tutor2.setNome("Ana");
        tutor2.setTelefone("+5545999998128");
        tutor2.setCep("85851-000");
        tutor2.setCpf("620.163.630-70");
        tutor2.setCidade("Foz do Iguaçu");
        tutor2.setEstado("PR");
        tutor2.setSenha("senha123");
        tutor2.setEmail("ana@example.com");

        vacina.setId(1L);
        vacina.setNome("v10");

        aplicacaoVacina.setVacina(vacina);
        aplicacaoVacina.setAnimal(animal);
        aplicacaoVacina.setLote("1234");
        aplicacaoVacina.setVeterinario(veterinario);
        aplicacaoVacina.setNumeroDose(1);
        aplicacaoVacina.setDataAplicacao(LocalDate.of(2024, 10,16));

        notificacao.setTexto("Lorem");
        notificacao.setNivel(1);
        notificacao.setTutorDestinatario(tutor);

        convite.setTexto("Aceita o Convite?");
        convite.setAceito(false);
        convite.setNivel(1);
        convite.setTutorDestinatario(tutor);
        convite.setTutorRemetente(tutor2);
        convite.setAnimal(animal);

        notificacoes.add(notificacao);
        notificacoes.add(convite);
        notificacaoJson = objectMapper.writeValueAsString(notificacao);
    }

    @Test
    void save() throws Exception{

        Mockito.when(notificacaoService.save(any())).thenReturn(notificacao);

        mockMvc.perform(post("/notificacoes")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(notificacao)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(notificacao)));

    }

    @Test
    void findById() throws  Exception{

        Mockito.when(notificacaoService.findById(any())).thenReturn(notificacao);

        mockMvc.perform(get("/notificacoes/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(notificacao)));
    }

    @Test
    void delete_test() throws Exception {
        Mockito.when(notificacaoService.delete(anyLong()))
                .thenReturn("Notificacao com id 1 foi excluído com sucesso.");

        mockMvc.perform(delete("/notificacoes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Notificacao com id 1 foi excluído com sucesso."));
    }

    @Test
    void update() throws Exception{

        Notificacao notificacao1 = new Notificacao();
        notificacao1.setAceito(true);
        notificacao1.setTexto("Teste");
        notificacao1.setNivel(2);

        notificacao.setAceito(notificacao1.getAceito());
        notificacao.setNivel(notificacao1.getNivel());
        notificacao.setTexto(notificacao1.getTexto());

        Mockito.when(notificacaoService.update(anyLong(), any())).thenReturn(notificacao);

        mockMvc.perform(put("/notificacoes/1")
                        .content(objectMapper.writeValueAsString(notificacao))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(notificacao)));
    }

    @Test
    void findAll() throws Exception{

        Mockito.when(notificacaoService.findAll()).thenReturn(notificacoes);


        String jsonEsperado = objectMapper.writeValueAsString(notificacoes);

        mockMvc.perform(get("/notificacoes/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonEsperado));
    }

    @Test
    void findByTutorDestinatarioId()  throws  Exception{

        Mockito.when(notificacaoService.findByTutorDestinatario(anyLong())).thenReturn(notificacoes);

        String jsonEsperado = objectMapper.writeValueAsString(notificacoes);

        mockMvc.perform(get("/notificacoes/findByTutorId")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonEsperado));
    }

    @Test
    void gerarNotificacoes() throws Exception {

        AplicacaoVacina aplicacao = new AplicacaoVacina();
        List<AplicacaoVacina> aplicacoes = List.of(aplicacao);

        Mockito.when(aplicacaoVacinaService.findAll()).thenReturn(aplicacoes);

        Mockito.when(notificacaoService.gerarNotificacaoDataValidadeVacina(any()))
                .thenReturn(notificacao);

        Mockito.when(notificacaoService.save(any())).thenReturn(notificacao);

        mockMvc.perform(get("/notificacoes/gerar"))
                .andExpect(status().isOk())
                .andExpect(content().string("Notificações geradas com sucesso!"));

        Mockito.verify(aplicacaoVacinaService, Mockito.times(1)).findAll();
        Mockito.verify(notificacaoService, Mockito.atLeastOnce()).gerarNotificacaoDataValidadeVacina(any());
        Mockito.verify(notificacaoService, Mockito.atLeastOnce()).save(any());
    }


    @Test
    void gerarConvite() throws Exception{

        Notificacao notificacao1 = new Notificacao();

        notificacao1.setTexto("Teste");
        notificacao1.setAnimal(animal);
        notificacao1.setTutorDestinatario(tutor);
        notificacao1.setTutorRemetente(tutor2);
        notificacao1.setAceito(false);
        notificacao1.setNivel(2);

        Mockito.when(notificacaoService.gerarConvite(any(), any(), any()))
                .thenReturn(notificacao1);

        var mensagem = objectMapper.writeValueAsString(notificacao1);

        mockMvc.perform(post("/notificacoes/gerarConvite")
                        .param("tutorDestinatario_id", "1")
                        .param("tutorRemetente_id", "2")
                        .param("animal_id", "1"))
                .andExpect(status().isCreated())
                .andExpect(content().json(mensagem));
    }

    @Test
    void conviteAceito() throws Exception{
        Mockito.doNothing().when(notificacaoService).conviteAceito(1L);

        mockMvc.perform(post("/notificacoes/conviteAceito/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Convite aceito e tutor atualizado com sucesso!"));
    }
}
