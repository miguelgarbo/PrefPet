package com.uni.PrefPet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uni.PrefPet.model.Contato;
import com.uni.PrefPet.model.Emergencia;
import com.uni.PrefPet.service.ContatoService;
import com.uni.PrefPet.service.EmergenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(EmergenciaController.class)
public class EmergenciaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    EmergenciaService emergenciaService;

    @MockitoBean
    ContatoService contatoService;

    @Autowired
    ObjectMapper objectMapper;

    Emergencia emergencia = new Emergencia();

    List<Emergencia> emergencias = new ArrayList<>();

    Contato contato  = new Contato();

    List<Contato> contatos = new ArrayList<>();

    String jsonEmergencia;

    @BeforeEach
    void setUp() throws Exception {
        contato.setAtivo(true);
        contato.setTelefone("45988366777");
        contato.setEmail("unila@example.com");
        contato.setNomeOrgao("Unila");
        contato.setId(1L);

        emergencia.setNome("MAUS TRATOS");
        emergencia.setId(1L);
        emergencia.addContato(contato);

        jsonEmergencia = objectMapper.writeValueAsString(emergencia);

        emergencias.add(emergencia);
    }

    @Test
    @DisplayName("Teste Unitário: salvar uma emergência")
    void save() throws Exception {
        Mockito.when(emergenciaService.save(any())).thenReturn(emergencia);

        mockMvc.perform(post("/emergencia")
                        .contentType("application/json")
                        .content(jsonEmergencia))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonEmergencia));

        Mockito.verify(emergenciaService, Mockito.times(1)).save(any(Emergencia.class));
    }

    @Test
    @DisplayName("Teste Unitário: buscar emergência por ID")
    void findById() throws Exception{
        Mockito.when(emergenciaService.findById(1L)).thenReturn(emergencia);

        mockMvc.perform(get("/emergencia/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonEmergencia));
    }

    @Test
    @DisplayName("Teste Unitário: deletar emergência por ID")
    void delete_test() throws Exception{
        Mockito.when(emergenciaService.delete(1L)).thenReturn("Emergencia Deletada");

        mockMvc.perform(delete("/emergencia/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Emergencia Deletada"));
    }

    @Test
    @DisplayName("Teste Unitário: atualizar uma emergência")
    void update() throws Exception {
        Emergencia emergencia1 = new Emergencia();
        emergencia1.setNome("TESTE");

        emergencia.setNome(emergencia1.getNome());

        String jsonEmegenciaUpdate = objectMapper.writeValueAsString(emergencia1);
        String jsonEsperado = objectMapper.writeValueAsString(emergencia);

        Mockito.when(emergenciaService.update(1L, emergencia1)).thenReturn(emergencia);

        mockMvc.perform(put("/emergencia/1")
                        .contentType("application/json")
                        .content(jsonEmegenciaUpdate))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonEsperado))
                .andExpect(jsonPath("$.nome").value("TESTE"));
    }

    @Test
    @DisplayName("Teste Unitário: listar todas as emergências")
    void findAll() throws Exception {
        String jsonEsperado = objectMapper.writeValueAsString(emergencias);

        Mockito.when(emergenciaService.findAll()).thenReturn(emergencias);

        mockMvc.perform(get("/emergencia/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonEsperado));
    }
}

