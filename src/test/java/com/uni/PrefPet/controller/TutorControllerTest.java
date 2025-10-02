package com.uni.PrefPet.controller;


import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.service.TutorService;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;


@WebMvcTest(TutorController.class)
public class TutorControllerTest {


    //simula chamadas http
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    TutorService tutorService;


    // Teste De Post Aguardar exemplo do prof
    @Test
    @DisplayName("Esse Teste Deve Retornar Ok e Cadastrar o Tutor de acordo com seus validations")
    void testValidationsSaveTutorStatusOk() throws Exception{

        //given
        Tutor tutor = new Tutor();
        tutor.setNome("Teste");
        tutor.setSenha("senha123");
        tutor.setEmail("test@example.com");
        tutor.setCep("85852710");
        tutor.setCpf("466.027.230-30");
        tutor.setCidade("Foz Do Iguaçu");
        tutor.setEstado("Paraná");
        tutor.setTelefone("45988366777");

        Mockito.when(tutorService.save(tutor)).thenReturn(tutor);

        mockMvc.perform(post("http://localhost:8080/tutores")).andDo(print()).andExpect(status().isOk());

    }


    @Test
    @DisplayName("Esse TEste Deve Retornar um tutor pelo id")
    void testFindByIdValidOk() throws Exception {

        //given

        Tutor tutor =new Tutor();

        Mockito.when(tutorService.findById(any())).thenReturn(tutor);

        //Where E O Then
        mockMvc.perform(get("http://localhost:8080/tutores/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
