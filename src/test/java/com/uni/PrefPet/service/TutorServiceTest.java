package com.uni.PrefPet.service;


import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Definindo que quem vai gerenciar essa classe de tests será o mockito
@ExtendWith(MockitoExtension.class)
public class TutorServiceTest {

    @InjectMocks
    TutorService tutorService;

    @Mock
    TutorRepository tutorRepository;

    @Mock
    AnimalService animalService;

    @Test
    @DisplayName("Validar Cadastro de Tutor OK")
    void testTutorSaveOk(){

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

        Mockito.when(tutorRepository.save(tutor)).thenReturn(tutor);
        //When

        var resposta = tutorService.save(tutor);

        //Asserts
        assertEquals(tutor, resposta);

    }


}
