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


    //simula chamadas http
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
    @DisplayName("Esse Teste Deve Retornar Ok e Cadastrar o Tutor de acordo com seus validations")
    void testValidationsSaveTutorOK() throws Exception {
        //give
        Mockito.when(tutorService.save(tutor)).thenReturn(tutor);

        mockMvc.perform(post("/tutores")
                        .contentType("application/json")
                        .content(tutorJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(tutorJson));
    }

    @Test
    @DisplayName("Esse Teste Deve Retornar uma exceção por integridade de dados pois ja existe tal cpf no banco")
    void testValidationsSaveTutorError() throws Exception {
        //give

        Mockito.when(tutorService.save(tutor)).thenThrow(new IllegalArgumentException("Já existe um usuário com este CPF."));

        mockMvc.perform(post("http://localhost:8080/tutores")
                        .contentType("application/json")
                        .content(tutorJson))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("Já existe um usuário com este CPF.")));
    }


    @Test
    @DisplayName("Esse TEste Deve Retornar um tutor pelo id")
    void testFindByIdValidOk() throws Exception {

        Mockito.when(tutorService.findById(any())).thenReturn(tutor);

        //Where E O Then
        mockMvc.perform(get("http://localhost:8080/tutores/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(tutorJson));

    }

    @Test
    @DisplayName("Esse TEste Deve Retornar um tutor pelo id")
    void testFindByIdValidError() throws Exception {

        Mockito.when(tutorService.findById(any())).thenThrow(new EntityNotFoundException("Nenhum Tutor Com esse Id"));

        //Where E O Then
        mockMvc.perform(get("/tutores/1"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("Nenhum Tutor Com esse Id")));
    }

    @Test
    @DisplayName("Esse teste deve retornar uma lista com os tutores cadastrados no banco")
    void testFindAllOk() throws Exception {

        Mockito.when(tutorService.findAll()).thenReturn(tutores);

        var respostaLista = mockMvc.perform(get("/tutores/findAll"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<Tutor> listaRetornada = objectMapper.readValue(respostaLista, new TypeReference<List<Tutor>>() {
        });
        Assertions.assertEquals(listaRetornada.size(), tutores.size());
        Assertions.assertEquals(listaRetornada.get(0).getNome(), tutores.get(0).getNome());
    }

    @Test
    @DisplayName("Esse teste deve retornar uma lista com os tutores cadastrados no banco")
    void testFindAllEmptyList() throws Exception {

        Mockito.when(tutorService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/tutores/findAll"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Esse Teste vai deletar pelo id")
    void testDeleteTutorOk() throws Exception {

        Mockito.when(tutorService.delete(tutor.getId()))
                .thenReturn("Usuário com id " + tutor.getId() + " foi excluído com sucesso.");

        mockMvc.perform(delete("/tutores/1"))
                .andExpect(status().isNoContent());
    }

    //Erro
//    @Test
//    @DisplayName("Esse Teste vai retornar uma exceção da service e na controller no content")
//    void testDeleteTutorError() throws Exception {
//
//        Mockito.when(tutorService.delete(tutor.getId()))
//                .thenThrow(new EntityNotFoundException("Usuário com id " + tutor.getId() + " não encontrado."));
//
//        mockMvc.perform(delete("/tutores/1"))
//                .andExpect(status().isNoContent());
//    }

    @Test
    @DisplayName("esse teste vai atualizar um tutor")
    void testUpdateTutor() throws Exception{

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
    @DisplayName("esse teste vai lançar uma exceção para manter integridade de dados")
    void testUpdateTutorErrorNotId() throws Exception{
        Mockito.when(tutorService.update(eq(3L), any(Tutor.class)))
                .thenThrow(new EntityNotFoundException("Usuário com id " + 3 + " não encontrado."));

        mockMvc.perform(put("/tutores/3")
                        .contentType("application/json")
                        .content(outroTutorJson))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Usuário com id 3 não encontrado."))
                .andDo(print());

        Mockito.verify(tutorService, Mockito.times(1)).update(any(), any());

    }

    @Test
    @DisplayName("esse teste vai lançar uma exceção para manter integridade de dados")
    void testUpdateTutorErrorCpfIntegrity() throws Exception{
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
    @DisplayName("esse teste vai lançar uma exceção para manter integridade de dados")
    void testUpdateTutorErrorTelefoneIntegrity() throws Exception{
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
    @DisplayName("deve retornar um tutor pelo seu nome")
    void testFindByNomeOk() throws Exception{

        Mockito.when(tutorService.findByNome("Miguel")).thenReturn(tutor);

        mockMvc.perform(get("/tutores/findByNome")
                .param("nome", "Miguel"))
                .andExpect(status().isOk())
                .andExpect(content().json(tutorJson)
                );
    }


    @Test
    @DisplayName("deve retornar um erro ao buscar tutor pelo nome")
    void testFindByNomeError() throws Exception{
        Mockito.when(tutorService.findByNome("Miguel")).thenThrow(new EntityNotFoundException("Nenhum usuário encontrado com o nome informado"));

        mockMvc.perform(get("/tutores/findByNome")
                        .param("nome", "Miguel"))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Nenhum usuário encontrado com o nome informado")
                );
    }

    @Test
    @DisplayName("deve retornar um tutor pelo seu cpf")
    void testFindByCpfOk() throws Exception{

        Mockito.when(tutorService.findByCPF("466.027.230-30")).thenReturn(tutor);

        mockMvc.perform(get("/tutores/findByCPF")
                        .param("cpf", "466.027.230-30"))
                .andExpect(status().isOk())
                .andExpect(content().json(tutorJson)
                );
    }

    @Test
    @DisplayName("deve retornar um erro ao buscar tutor pelo cpf")
    void testFindByCpfError() throws Exception{

        Mockito.when(tutorService.findByCPF("466.027.230-30")).thenThrow(new EntityNotFoundException("Nenhum usuário encontrado com o CPF informado"));

        mockMvc.perform(get("/tutores/findByCPF")
                        .param("cpf", "466.027.230-30"))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Nenhum usuário encontrado com o CPF informado")
                );
    }



    @Test
    @DisplayName("deve retornar um tutor pelo seu email")
    void testFindByEmailOk() throws Exception{

        Mockito.when(tutorService.findByEmail(tutor.getEmail())).thenReturn(tutor);

        mockMvc.perform(get("/tutores/findByEmail")
                        .param("email", tutor.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().json(tutorJson)
                );
    }

    @Test
    @DisplayName("deve retornar um erro ao buscar tutor pelo email")
    void testFindByEmailError() throws Exception{

        Mockito.when(tutorService.findByEmail("miguel@example.com")).thenThrow(new EntityNotFoundException("Nenhum usuário encontrado com o email informado"));

        mockMvc.perform(get("/tutores/findByEmail")
                        .param("email", "miguel@example.com"))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Nenhum usuário encontrado com o email informado")
                );
    }

    @Test
    @DisplayName("esse test deve liberar um login")
    void testLoginOk() throws Exception{

        Mockito.when(tutorService.login(tutor.getEmail(), tutor.getSenha())).thenReturn(true);

        mockMvc.perform(get("/tutores/login")
                        .param("senha", tutor.getSenha())
                        .param("email", tutor.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("esse test deve lançar uma exceção ao realizar um login")
    void testLoginError() throws Exception{

        Mockito.when(tutorService.login("email@example.com", "senha123")).thenReturn(false);

        mockMvc.perform(get("/tutores/login")
                        .param("senha", "email@example.com")
                        .param("email", "senha123"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("false"));
    }

    @Test
    @DisplayName("esse test deve retornar o usuario atual ")
    void testGetCurrentUser() throws Exception{

        Mockito.when(tutorService.getCurrentUser()).thenReturn(tutor);

        mockMvc.perform(get("/tutores/current-user"))
                .andExpect(status().isOk())
                .andExpect(content().json(tutorJson));
    }

    @Test
    @DisplayName("esse test deve lançar uma exceção ao buscar um usuario atual ")
    void testGetCurrentUserError() throws Exception{

        Mockito.when(tutorService.getCurrentUser()).thenReturn(null);

        mockMvc.perform(get("/tutores/current-user"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @DisplayName("esse test deve realizar o logout do usuario atual ")
    void testLogout() throws Exception{

        Mockito.doNothing().when(tutorService).logout();

        mockMvc.perform(post("/tutores/logout"))
                .andExpect(status().isOk())
                .andExpect(content().string("Logout realizado com sucesso!"));
    }







}


