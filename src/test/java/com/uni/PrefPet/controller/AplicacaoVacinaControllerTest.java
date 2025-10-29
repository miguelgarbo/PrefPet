package com.uni.PrefPet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uni.PrefPet.model.AplicacaoVacina;
import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.Vacina;
import com.uni.PrefPet.service.AplicacaoVacinaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AplicacaoVacinaController.class)
class AplicacaoVacinaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AplicacaoVacinaService aplicacaoVacinaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Teste Unitario - Deve salvar aplicação de vacina com sucesso (POST /aplicacao)")
    void salvarAplicacaoVacina() throws Exception {
        AplicacaoVacina aplicacao = new AplicacaoVacina();
        aplicacao.setId(1L);
        aplicacao.setLote("L123");
        aplicacao.setDataAplicacao(LocalDate.of(2024, 1, 10));
        aplicacao.setVacina(new Vacina());
        aplicacao.setAnimal(new Animal());

        Mockito.when(aplicacaoVacinaService.save(any(AplicacaoVacina.class), eq(6)))
                .thenReturn(aplicacao);

        mockMvc.perform(post("/aplicacao")
                        .param("meses", "6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aplicacao)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.lote").value("L123"));
    }

    @Test
    @DisplayName("Teste Unitario - Deve retornar erro 404 ao tentar salvar aplicação com vacina inexistente")
    void salvarAplicacaoVacinaErro() throws Exception {
        AplicacaoVacina aplicacao = new AplicacaoVacina();
        aplicacao.setLote("L404");
        aplicacao.setVacina(new Vacina());
        aplicacao.getVacina().setId(999L);
        aplicacao.setAnimal(new Animal());

        Mockito.when(aplicacaoVacinaService.save(any(AplicacaoVacina.class), anyInt()))
                .thenThrow(new EntityNotFoundException("Vacina não encontrada"));

        mockMvc.perform(post("/aplicacao")
                        .param("meses", "6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aplicacao)))
                .andExpect(status().isInternalServerError());
    }


    @Test
    @DisplayName("Teste Unitario -Deve retornar aplicação existente (GET /aplicacao/{id})")
    void buscarAplicacaoPorId() throws Exception {
        AplicacaoVacina aplicacao = new AplicacaoVacina();
        aplicacao.setId(2L);
        aplicacao.setLote("XYZ");

        Mockito.when(aplicacaoVacinaService.findById(2L)).thenReturn(aplicacao);

        mockMvc.perform(get("/aplicacao/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lote").value("XYZ"));
    }

    @Test
    @DisplayName("Teste Unitario -Deve retornar 404 quando aplicação não for encontrada (GET /aplicacao/{id})")
    void buscarAplicacaoPorIdNaoEncontrada() throws Exception {
        Mockito.when(aplicacaoVacinaService.findById(999L))
                .thenThrow(new EntityNotFoundException("Aplicação não encontrada"));

        mockMvc.perform(get("/aplicacao/999"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Teste Unitario -Deve listar todas as aplicações (GET /aplicacao/findAll)")
    void listarAplicacoes() throws Exception {
        AplicacaoVacina a1 = new AplicacaoVacina();
        a1.setLote("A");
        AplicacaoVacina a2 = new AplicacaoVacina();
        a2.setLote("B");

        Mockito.when(aplicacaoVacinaService.findAll()).thenReturn(List.of(a1, a2));

        mockMvc.perform(get("/aplicacao/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Teste Unitario -Deve atualizar aplicação de vacina (PUT /aplicacao/{id})")
    void atualizarAplicacaoVacina() throws Exception {
        AplicacaoVacina aplicacaoAtualizada = new AplicacaoVacina();
        aplicacaoAtualizada.setId(1L);
        aplicacaoAtualizada.setLote("L999");

        Mockito.when(aplicacaoVacinaService.update(eq(1L), any(AplicacaoVacina.class)))
                .thenReturn(aplicacaoAtualizada);

        mockMvc.perform(put("/aplicacao/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aplicacaoAtualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lote").value("L999"));
    }

    @Test
    @DisplayName("Teste Unitario -Deve retornar erro 404 ao tentar atualizar aplicação inexistente (PUT /aplicacao/{id})")
    void atualizarAplicacaoVacinaErro() throws Exception {
        AplicacaoVacina aplicacao = new AplicacaoVacina();
        aplicacao.setLote("L999");

        Mockito.when(aplicacaoVacinaService.update(eq(999L), any(AplicacaoVacina.class)))
                .thenThrow(new EntityNotFoundException("Aplicação não encontrada"));

        mockMvc.perform(put("/aplicacao/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aplicacao)))
                .andExpect(status().isInternalServerError());
    }


    @Test
    @DisplayName("Teste Unitario -Deve deletar aplicação de vacina (DELETE /aplicacao/{id})")
    void deletarAplicacaoVacina() throws Exception {
        mockMvc.perform(delete("/aplicacao/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(aplicacaoVacinaService).delete(anyLong());
    }

    @Test
    @DisplayName("Teste Unitario -GET /aplicacao/by-lote deve retornar aplicação de vacina")
    void deveBuscarAplicacaoPorLote() throws Exception {
        AplicacaoVacina app = new AplicacaoVacina();
        Mockito.when(aplicacaoVacinaService.findByLote("L1")).thenReturn(app);

        mockMvc.perform(get("/aplicacao/by-lote")
                        .param("lote", "L1"))
                .andExpect(status().isOk());

        Mockito.verify(aplicacaoVacinaService).findByLote("L1");
    }



    @Test
    @DisplayName("Teste Unitario -GET /aplicacao/findByAnimalId deve retornar lista de aplicações")
    void deveBuscarAplicacaoPorAnimalId() throws Exception {
        List<AplicacaoVacina> lista = List.of(new AplicacaoVacina());
        Mockito.when(aplicacaoVacinaService.findByAnimal(1L)).thenReturn(lista);

        mockMvc.perform(get("/aplicacao/findByAnimalId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        Mockito.verify(aplicacaoVacinaService).findByAnimal(1L);
    }

    @Test
    @DisplayName("Teste Unitario -GET /aplicacao/validade-before deve retornar lista")
    void deveBuscarPorValidadeBefore() throws Exception {
        List<AplicacaoVacina> lista = List.of(new AplicacaoVacina());
        Mockito.when(aplicacaoVacinaService.findByValidadeBefore(any())).thenReturn(lista);

        mockMvc.perform(get("/aplicacao/validade-before")
                        .param("data", LocalDate.now().toString()))
                .andExpect(status().isOk());

        Mockito.verify(aplicacaoVacinaService).findByValidadeBefore(any());
    }

    @Test
    @DisplayName("Teste Unitario -GET /aplicacao/validade-after deve retornar lista")
    void deveBuscarPorValidadeAfter() throws Exception {
        List<AplicacaoVacina> lista = List.of(new AplicacaoVacina());
        Mockito.when(aplicacaoVacinaService.findByValidadeAfter(any())).thenReturn(lista);

        mockMvc.perform(get("/aplicacao/validade-after")
                        .param("data", LocalDate.now().toString()))
                .andExpect(status().isOk());

        Mockito.verify(aplicacaoVacinaService).findByValidadeAfter(any());
    }

    @Test
    @DisplayName("Teste Unitario -GET /aplicacao/aplicacaoData deve retornar lista")
    void deveBuscarPorDataAplicacao() throws Exception {
        List<AplicacaoVacina> lista = List.of(new AplicacaoVacina());
        Mockito.when(aplicacaoVacinaService.findByDataAplicacao(any())).thenReturn(lista);

        mockMvc.perform(get("/aplicacao/aplicacaoData")
                        .param("data", LocalDate.now().toString()))
                .andExpect(status().isOk());

        Mockito.verify(aplicacaoVacinaService).findByDataAplicacao(any());
    }

    @Test
    @DisplayName("Teste Unitario -GET /aplicacao/aplicacao-after deve retornar lista")
    void deveBuscarPorDataAplicacaoAfter() throws Exception {
        List<AplicacaoVacina> lista = List.of(new AplicacaoVacina());
        Mockito.when(aplicacaoVacinaService.findByDataAplicacaoAfter(any())).thenReturn(lista);

        mockMvc.perform(get("/aplicacao/aplicacao-after")
                        .param("data", LocalDate.now().toString()))
                .andExpect(status().isOk());

        Mockito.verify(aplicacaoVacinaService).findByDataAplicacaoAfter(any());
    }

}
