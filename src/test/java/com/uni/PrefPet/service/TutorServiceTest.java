package com.uni.PrefPet.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Test
    @DisplayName("teste de cadastro invalido, CPF ja existe")
    void testTutorSaveErrorCPFDuplicate(){

        Mockito.when(tutorRepository.save(tutor)).thenThrow(new IllegalArgumentException("Já existe um usuário com este CPF"));

        var exception = assertThrows(IllegalArgumentException.class, ()->{
            tutorService.save(tutor);
        });
        assertEquals(exception.getMessage(),"Já existe um usuário com este CPF");
        Mockito.verify(tutorRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("teste de cadastro invalido, telefone ja existe")
    void testTutorSaveErrorTelefoneDuplicate(){

        Mockito.when(tutorRepository.save(tutor)).thenThrow(new IllegalArgumentException("Já existe um usuário com este telefone"));

        var exception = assertThrows(IllegalArgumentException.class, ()->{
            tutorService.save(tutor);
        });
        assertEquals(exception.getMessage(),"Já existe um usuário com este telefone");
        Mockito.verify(tutorRepository, Mockito.times(1)).save(any());
    }

    //new EntityNotFoundException("Nenhum Tutor Com esse Id")

    @Test
    @DisplayName("teste de procurar tutor por id valido")
    void testTutorFindTutorByIdValid(){

        Mockito.when(tutorRepository.findById(1L)).thenReturn(Optional.of(tutor));

        var resposta = tutorService.findById(1L);

        assertEquals(tutor, resposta);

        Mockito.verify(tutorRepository, Mockito.times(1)).findById(any());
    }

    @Test
    @DisplayName("teste de procurar tutor por id invalido e retornar excepection")
    void testTutorFindTutorByIdInvalid(){

        Mockito.when(tutorRepository.findById(1L)).thenThrow(new EntityNotFoundException("Nenhum Tutor Com esse Id"));

        var excepetion = assertThrows(Exception.class, ()->{
           tutorService.findById(1L);
        });

        assertEquals(excepetion.getMessage(),"Nenhum Tutor Com esse Id");
        Mockito.verify(tutorRepository, Mockito.times(1)).findById(any());
    }

    @Test
    @DisplayName("teste de retornar lista de tutores")
    void testTutorFindAllValid(){

        Mockito.when(tutorRepository.findAll()).thenReturn(tutores);

        var respota = tutorService.findAll();

        assertEquals(respota.size(),2);
        Mockito.verify(tutorRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("teste de retornar lista de tutores vazia")
    void testTutorFindAllValidEmpty(){
        Mockito.when(tutorRepository.findAll()).thenReturn(Collections.emptyList());

        var respota = tutorService.findAll();

        assertEquals(respota.size(),0);
        Mockito.verify(tutorRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("teste de deletar um tutor valido com id")
    void testDeleteTutorIdValid(){

        Mockito.when(tutorRepository.existsById(tutor.getId())).thenReturn(true);
        Mockito.doNothing().when(tutorRepository).deleteById(tutor.getId());

        var resposta = tutorService.delete(tutor.getId());

        assertEquals(resposta, "Usuário com id " + tutor.getId() + " foi excluído com sucesso.");
        Mockito.verify(tutorRepository, Mockito.times(1)).deleteById(any());
    }

    @Test
    @DisplayName("teste de deletar um tutor invalido com id")
    void testDeleteTutorIdInvalid(){

        Mockito.when(tutorRepository.existsById(tutor.getId()))
                .thenThrow(new EntityNotFoundException("Usuário com id " + tutor.getId() + " não encontrado."));

//        Mockito.doNothing().when(tutorRepository).deleteById(tutor.getId());

        var resposta = assertThrows(EntityNotFoundException.class, ()->{
           tutorService.delete(tutor.getId());
        });

        assertEquals(resposta.getMessage(), "Usuário com id " + tutor.getId() + " não encontrado.");
        Mockito.verify(tutorRepository, Mockito.times(1)).existsById(any());
        Mockito.verify(tutorRepository, Mockito.times(0)).deleteById(any());
    }

    @Test
    @DisplayName("teste de atualizar um tutor valido com id")
    void testUpdateTutorIdValid(){

        Mockito.when(tutorRepository.findById(tutor.getId())).thenReturn(Optional.of(tutor));
        Mockito.when(tutorRepository.existsByCpfAndIdNot("298.940.440-69", tutor.getId()));
        Mockito.when(tutorRepository.existsByEmailAndIdNot("joao@example.com", tutor.getId()));
        Mockito.when(tutorRepository.existsByTelefoneAndIdNot("4599999999", tutor.getId()));

        var resposta = assertThrows(EntityNotFoundException.class, ()->{


        });

        Mockito.verify(tutorRepository, Mockito.times(1)).findById(any());

    }













}
