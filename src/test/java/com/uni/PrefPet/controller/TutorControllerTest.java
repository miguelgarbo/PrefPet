package com.uni.PrefPet.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.service.TutorService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TutorController.class)
public class TutorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    TutorService tutorService;

    @Autowired
    ObjectMapper objectMapper;

    Tutor tutor = new Tutor();
    Tutor outroTutor = new Tutor();
    String tutorJson;
    String outroTutorJson;
    List<Tutor> tutores = new ArrayList<>();

    @BeforeEach
    void setUp() throws Exception {
        tutor.setId(1L);
        tutor.setNome("Miguel");
        tutor.setSenha("senha123");
        tutor.setEmail("miguel@example.com");
        tutor.setCep("02919-020");
        tutor.setCpf("466.027.230-30");
        tutor.setCidade("São Paulo");
        tutor.setEstado("São Paulo");
        tutor.setTelefone("45988366777");

        outroTutor.setId(2L);
        outroTutor.setNome("Ana");
        outroTutor.setSenha("senha123");
        outroTutor.setEmail("ana@example.com");
        outroTutor.setCep("29450-970");
        outroTutor.setCpf("186.358.770-55");
        outroTutor.setCidade("Foz Do Iguaçu");
        outroTutor.setEstado("Paraná");
        outroTutor.setTelefone("45988366777");

        tutorJson = objectMapper.writeValueAsString(tutor);
        outroTutorJson = objectMapper.writeValueAsString(outroTutor);

        tutores.add(tutor);
        tutores.add(outroTutor);
    }

    @Test
    @DisplayName("Teste Unitário: salvar tutor com sucesso")
    void testValidationsSaveTutorOK() throws Exception {
        Mockito.when(tutorService.save(tutor)).thenReturn(tutor);

        mockMvc.perform(post("/tutores")
                        .contentType("application/json")
                        .content(tutorJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(tutorJson));
    }

    @Test
    @DisplayName("Teste Unitário: falha ao salvar tutor por CPF duplicado")
    void testValidationsSaveTutorError() throws Exception {
        Mockito.when(tutorService.save(tutor))
                .thenThrow(new IllegalArgumentException("Já existe um usuário com este CPF."));

        mockMvc.perform(post("/tutores")
                        .contentType("application/json")
                        .content(tutorJson))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("Já existe um usuário com este CPF.")));
    }

    @Test
    @DisplayName("Teste Unitário: buscar tutor por ID com sucesso")
    void testFindByIdValidOk() throws Exception {
        Mockito.when(tutorService.findById(any())).thenReturn(tutor);

        mockMvc.perform(get("/tutores/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(tutorJson));
    }

    @Test
    @DisplayName("Teste Unitário: buscar tutor por ID que não existe")
    void testFindByIdValidError() throws Exception {
        Mockito.when(tutorService.findById(any()))
                .thenThrow(new EntityNotFoundException("Nenhum Tutor Com esse Id"));

        mockMvc.perform(get("/tutores/1"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("Nenhum Tutor Com esse Id")));
    }

    @Test
    @DisplayName("Teste Unitário: listar todos os tutores cadastrados")
    void testFindAllOk() throws Exception {
        Mockito.when(tutorService.findAll()).thenReturn(tutores);

        var respostaLista = mockMvc.perform(get("/tutores/findAll"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<Tutor> listaRetornada = objectMapper.readValue(respostaLista, new TypeReference<List<Tutor>>() {});
        Assertions.assertEquals(listaRetornada.size(), tutores.size());
        Assertions.assertEquals(listaRetornada.get(0).getNome(), tutores.get(0).getNome());
    }

    @Test
    @DisplayName("Teste Unitário: listar tutores quando não há registros")
    void testFindAllEmptyList() throws Exception {
        Mockito.when(tutorService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/tutores/findAll"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Teste Unitário: deletar tutor com sucesso")
    void testDeleteTutorOk() throws Exception {
        Mockito.when(tutorService.delete(tutor.getId()))
                .thenReturn("Usuário com id " + tutor.getId() + " foi excluído com sucesso.");

        mockMvc.perform(delete("/tutores/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Teste Unitário: atualizar tutor com sucesso")
    void testUpdateTutor() throws Exception {
        Mockito.when(tutorService.update(eq(1L), any(Tutor.class))).thenReturn(tutor);

        mockMvc.perform(put("/tutores/1")
                        .contentType("application/json")
                        .content(outroTutorJson))
                .andExpect(status().isOk())
                .andExpect(content().json(tutorJson))
                .andDo(print());

        Mockito.verify(tutorService, Mockito.times(1)).update(any(), any());
    }

    @Test
    @DisplayName("Teste Unitário: atualizar tutor que não existe")
    void testUpdateTutorErrorNotId() throws Exception {
        Mockito.when(tutorService.update(eq(3L), any(Tutor.class)))
                .thenThrow(new EntityNotFoundException("Usuário com id 3 não encontrado."));

        mockMvc.perform(put("/tutores/3")
                        .contentType("application/json")
                        .content(outroTutorJson))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Usuário com id 3 não encontrado."))
                .andDo(print());

        Mockito.verify(tutorService, Mockito.times(1)).update(any(), any());
    }

    @Test
    @DisplayName("Teste Unitário: falha ao atualizar tutor por CPF duplicado")
    void testUpdateTutorErrorCpfIntegrity() throws Exception {
        Mockito.when(tutorService.update(eq(1L), any(Tutor.class)))
                .thenThrow(new IllegalArgumentException("Já existe um usuário com este CPF."));

        mockMvc.perform(put("/tutores/1")
                        .contentType("application/json")
                        .content(outroTutorJson))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Já existe um usuário com este CPF."))
                .andDo(print());

        Mockito.verify(tutorService, Mockito.times(1)).update(any(), any());
    }

    @Test
    @DisplayName("Teste Unitário: falha ao atualizar tutor por telefone duplicado")
    void testUpdateTutorErrorTelefoneIntegrity() throws Exception {
        Mockito.when(tutorService.update(eq(1L), any(Tutor.class)))
                .thenThrow(new IllegalArgumentException("Já existe um usuário com este telefone."));

        mockMvc.perform(put("/tutores/1")
                        .contentType("application/json")
                        .content(outroTutorJson))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Já existe um usuário com este telefone."))
                .andDo(print());

        Mockito.verify(tutorService, Mockito.times(1)).update(any(), any());
    }

    @Test
    @DisplayName("Teste Unitário: buscar tutor por nome com sucesso")
    void testFindByNomeOk() throws Exception {
        Mockito.when(tutorService.findByNome("Miguel")).thenReturn(tutor);

        mockMvc.perform(get("/tutores/findByNome")
                        .param("nome", "Miguel"))
                .andExpect(status().isOk())
                .andExpect(content().json(tutorJson));
    }

    @Test
    @DisplayName("Teste Unitário: falha ao buscar tutor por nome inexistente")
    void testFindByNomeError() throws Exception {
        Mockito.when(tutorService.findByNome("Miguel"))
                .thenThrow(new EntityNotFoundException("Nenhum usuário encontrado com o nome informado"));

        mockMvc.perform(get("/tutores/findByNome")
                        .param("nome", "Miguel"))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Nenhum usuário encontrado com o nome informado"));
    }

    @Test
    @DisplayName("Teste Unitário: buscar tutor por CPF com sucesso")
    void testFindByCpfOk() throws Exception {
        Mockito.when(tutorService.findByCPF("466.027.230-30")).thenReturn(tutor);

        mockMvc.perform(get("/tutores/findByCPF")
                        .param("cpf", "466.027.230-30"))
                .andExpect(status().isOk())
                .andExpect(content().json(tutorJson));
    }

    @Test
    @DisplayName("Teste Unitário: falha ao buscar tutor por CPF inexistente")
    void testFindByCpfError() throws Exception {
        Mockito.when(tutorService.findByCPF("466.027.230-30"))
                .thenThrow(new EntityNotFoundException("Nenhum usuário encontrado com o CPF informado"));

        mockMvc.perform(get("/tutores/findByCPF")
                        .param("cpf", "466.027.230-30"))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Nenhum usuário encontrado com o CPF informado"));
    }

    @Test
    @DisplayName("Teste Unitário: buscar tutor por email com sucesso")
    void testFindByEmailOk() throws Exception {
        Mockito.when(tutorService.findByEmail(tutor.getEmail())).thenReturn(tutor);

        mockMvc.perform(get("/tutores/findByEmail")
                        .param("email", tutor.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().json(tutorJson));
    }

    @Test
    @DisplayName("Teste Unitário: falha ao buscar tutor por email inexistente")
    void testFindByEmailError() throws Exception {
        Mockito.when(tutorService.findByEmail("miguel@example.com"))
                .thenThrow(new EntityNotFoundException("Nenhum usuário encontrado com o email informado"));

        mockMvc.perform(get("/tutores/findByEmail")
                        .param("email", "miguel@example.com"))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Nenhum usuário encontrado com o email informado"));
    }

    @Test
    @DisplayName("Teste Unitário: login de tutor com sucesso")
    void testLoginOk() throws Exception {
        Mockito.when(tutorService.login(tutor.getEmail(), tutor.getSenha())).thenReturn(true);

        mockMvc.perform(get("/tutores/login")
                        .param("senha", tutor.getSenha())
                        .param("email", tutor.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("Teste Unitário: falha ao realizar login")
    void testLoginError() throws Exception {
        Mockito.when(tutorService.login("email@example.com", "senha123")).thenReturn(false);

        mockMvc.perform(get("/tutores/login")
                        .param("senha", "email@example.com")
                        .param("email", "senha123"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("false"));
    }

    @Test
    @DisplayName("Teste Unitário: obter usuário atual com sucesso")
    void testGetCurrentUser() throws Exception {
        Mockito.when(tutorService.getCurrentUser()).thenReturn(tutor);

        mockMvc.perform(get("/tutores/current-user"))
                .andExpect(status().isOk())
                .andExpect(content().json(tutorJson));
    }

    @Test
    @DisplayName("Teste Unitário: falha ao obter usuário atual")
    void testGetCurrentUserError() throws Exception {
        Mockito.when(tutorService.getCurrentUser()).thenReturn(null);

        mockMvc.perform(get("/tutores/current-user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Teste Unitário: logout do usuário atual")
    void testLogout() throws Exception {
        Mockito.doNothing().when(tutorService).logout();

        mockMvc.perform(post("/tutores/logout"))
                .andExpect(status().isOk())
                .andExpect(content().string("Logout realizado com sucesso!"));
    }

}


