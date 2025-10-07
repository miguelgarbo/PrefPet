package com.uni.PrefPet.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

//Definindo que quem vai gerenciar essa classe de tests será o mockito
@ExtendWith(MockitoExtension.class)
public class TutorServiceTest {

    @InjectMocks
    TutorService tutorService;

    @Mock
    TutorRepository tutorRepository;

    @Mock
    AnimalService animalService;

    ObjectMapper objectMapper = new ObjectMapper();

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
    @DisplayName("teste de cadastro valido")
    void testTutorSaveOk(){

        Mockito.when(tutorRepository.save(tutor)).thenReturn(tutor);

        var resposta = tutorService.save(tutor);

        assertEquals(tutor, resposta);
        Mockito.verify(tutorRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("teste de cadastro invalido, email ja existe")
    void testTutorSaveErrorEmailDuplicate(){

        Mockito.when(tutorRepository.save(tutor)).thenThrow(new IllegalArgumentException("Já existe um usuário com este email"));

        var exception = assertThrows(IllegalArgumentException.class, ()->{

            tutorService.save(tutor);

        });

        assertEquals(exception.getMessage(),"Já existe um usuário com este email");
        Mockito.verify(tutorRepository, Mockito.times(1)).save(any());
    }




}
