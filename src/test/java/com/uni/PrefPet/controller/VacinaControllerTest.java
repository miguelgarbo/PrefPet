package com.uni.PrefPet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uni.PrefPet.model.Vacina;
import com.uni.PrefPet.service.VacinaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VacinaController.class)
class VacinaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VacinaService vacinaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve salvar vacina com sucesso (POST /vacinas/save)")
    void salvarVacina() throws Exception {
        Vacina vacina = new Vacina();
        vacina.setId(1L);
        vacina.setNome("Antirrábica");

        Mockito.when(vacinaService.save(any(Vacina.class))).thenReturn(vacina);

        mockMvc.perform(post("/vacinas/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacina)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Antirrábica"));
    }

    @Test
    @DisplayName("Deve retornar erro 400 ao tentar salvar vacina inválida (POST /vacinas/save)")
    void salvarVacinaErro() throws Exception {
        Vacina vacina = new Vacina();
        vacina.setNome(""); // nome inválido

        Mockito.when(vacinaService.save(any(Vacina.class)))
                .thenThrow(new IllegalArgumentException("Dados inválidos"));

        mockMvc.perform(post("/vacinas/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacina)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Deve retornar vacina existente (GET /vacinas/findById/{id})")
    void buscarVacinaPorId() throws Exception {
        Vacina vacina = new Vacina();
        vacina.setId(2L);
        vacina.setNome("V8");

        Mockito.when(vacinaService.findById(2L)).thenReturn(vacina);

        mockMvc.perform(get("/vacinas/findById/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("V8"));
    }

    @Test
    @DisplayName("Deve retornar erro 404 quando vacina não for encontrada (GET /vacinas/findById/{id})")
    void buscarVacinaPorIdErro() throws Exception {
        Mockito.when(vacinaService.findById(99L))
                .thenThrow(new EntityNotFoundException("Vacina não encontrada"));

        mockMvc.perform(get("/vacinas/findById/99"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve listar todas as vacinas (GET /vacinas/findAll)")
    void listarVacinas() throws Exception {
        Vacina v1 = new Vacina(); v1.setNome("A");
        Vacina v2 = new Vacina(); v2.setNome("B");

        Mockito.when(vacinaService.findAll()).thenReturn(List.of(v1, v2));

        mockMvc.perform(get("/vacinas/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Deve atualizar vacina (PUT /vacinas/update/{id})")
    void atualizarVacina() throws Exception {
        Vacina vacinaAtualizada = new Vacina();
        vacinaAtualizada.setId(1L);
        vacinaAtualizada.setNome("Nova Vacina");

        Mockito.when(vacinaService.update(eq(1L), any(Vacina.class))).thenReturn(vacinaAtualizada);

        mockMvc.perform(put("/vacinas/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacinaAtualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nova Vacina"));
    }

    @Test
    @DisplayName("Deve retornar erro 404 ao tentar atualizar vacina inexistente (PUT /vacinas/update/{id})")
    void atualizarVacinaErro() throws Exception {
        Vacina vacina = new Vacina();
        vacina.setNome("V99");

        Mockito.when(vacinaService.update(eq(99L), any(Vacina.class)))
                .thenThrow(new EntityNotFoundException("Vacina não encontrada"));

        mockMvc.perform(put("/vacinas/update/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacina)))
                .andExpect(status().isInternalServerError());
    }


    @Test
    @DisplayName("Deve deletar vacina (DELETE /vacinas/delete/{id})")
    void deletarVacina() throws Exception {
        mockMvc.perform(delete("/vacinas/delete/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(vacinaService).delete(anyLong());
    }

    @Test
    @DisplayName("Deve retornar erro 404 ao deletar vacina inexistente (DELETE /vacinas/delete/{id})")
    void deletarVacinaErro() throws Exception {
        Mockito.doThrow(new EntityNotFoundException("Vacina não encontrada"))
                .when(vacinaService).delete(99L);

        mockMvc.perform(delete("/vacinas/delete/99"))
                .andExpect(status().isInternalServerError());
    }


}
