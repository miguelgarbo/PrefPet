package com.uni.PrefPet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uni.PrefPet.model.AplicacaoVacina;
import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.Vacina;
import com.uni.PrefPet.service.AplicacaoVacinaService;
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
    @DisplayName("Deve salvar aplicação de vacina com sucesso (POST /aplicacao)")
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
    @DisplayName("Deve retornar aplicação existente (GET /aplicacao/{id})")
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
    @DisplayName("Deve listar todas as aplicações (GET /aplicacao/findAll)")
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
    @DisplayName("Deve atualizar aplicação de vacina (PUT /aplicacao/{id})")
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
    @DisplayName("Deve deletar aplicação de vacina (DELETE /aplicacao/{id})")
    void deletarAplicacaoVacina() throws Exception {
        mockMvc.perform(delete("/aplicacao/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(aplicacaoVacinaService).delete(anyLong());
    }
}
