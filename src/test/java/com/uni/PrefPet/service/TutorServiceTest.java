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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

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

        Mockito.when(tutorRepository.existsByEmail(any())).thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class, ()->{
            tutorService.save(tutor);
        });

        assertEquals(exception.getMessage(),"Já existe um usuário com este email.");
    }

    @Test
    @DisplayName("teste de cadastro invalido, CPF ja existe")
    void testTutorSaveErrorCPFDuplicate(){

        Mockito.when(tutorRepository.existsByCpf(any())).thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class, ()->{
            tutorService.save(tutor);
        });
        assertEquals(exception.getMessage(),"Já existe um usuário com este CPF.");
    }

    @Test
    @DisplayName("teste de cadastro invalido, telefone ja existe")
    void testTutorSaveErrorTelefoneDuplicate(){

        Mockito.when(tutorRepository.existsByTelefone(any())).thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class, ()->{
            tutorService.save(tutor);
        });

        assertEquals("Já existe um usuário com este telefone.", exception.getMessage());

        Mockito.verify(tutorRepository, Mockito.never()).save(any());
    }


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

        Mockito.when(tutorRepository.findById(1L)).thenReturn(Optional.empty());

        var excepetion = assertThrows(EntityNotFoundException.class, ()->{
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

        Mockito.when(tutorRepository.existsById(tutor.getId())).thenReturn(false);

        var resposta = assertThrows(EntityNotFoundException.class, ()->{
           tutorService.delete(tutor.getId());
        });

        assertEquals(resposta.getMessage(), "Usuário com id " + tutor.getId() + " não encontrado.");
        Mockito.verify(tutorRepository, Mockito.times(1)).existsById(any());
        Mockito.verify(tutorRepository, Mockito.times(0)).deleteById(any());
    }

    @Test
    @DisplayName("teste de atualizar um tutor valido com id")
    void testUpdateTutorIdValido(){

        Mockito.when(tutorRepository.findById(tutor.getId())).thenReturn(Optional.of(tutor));

        Mockito.when(tutorRepository.existsByTelefoneAndIdNot(anyString(), anyLong()))
                .thenReturn(false);

        Mockito.when(tutorRepository.existsByEmailAndIdNot(anyString(), anyLong()))
                .thenReturn(false);

        Mockito.when(tutorRepository.existsByCpfAndIdNot(anyString(), anyLong()))
                .thenReturn(false);

        Mockito.when(tutorRepository.save(tutor)).thenReturn(tutor);

        var resposta = tutorService.update(tutor.getId(), outroTutor);

        Assertions.assertEquals(resposta, tutor);

        Mockito.verify(tutorRepository, Mockito.times(1)).findById(any());
        Mockito.verify(tutorRepository, Mockito.times(1)).save(any());

    }

    @Test
    @DisplayName("teste de atualizar um tutor invalido e receber exceção")
    void testUpdateTutorIdInvalido() {

        Mockito.when(tutorRepository.findById(tutor.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> {
            tutorService.update(tutor.getId(), outroTutor);
        });

    }

    @Test
    @DisplayName("teste de atualizar com cpf e ja existir e receber exceção")
    void testUpdateTutorCpfJaExiste(){

        Mockito.when(tutorRepository.findById(tutor.getId())).thenReturn(Optional.of(tutor));
        Mockito.when(tutorRepository.existsByCpfAndIdNot(anyString(), anyLong())).thenReturn(true);

        var resposta = Assertions.assertThrows(IllegalArgumentException.class, ()->{
            tutorService.update(tutor.getId(), outroTutor);
        });

        Assertions.assertEquals(resposta.getMessage(), "Já existe um usuário com este CPF.");
        Mockito.verify(tutorRepository, Mockito.times(1)).findById(any());
        Mockito.verify(tutorRepository, Mockito.times(1)).existsByCpfAndIdNot(any(), anyLong());
        Mockito.verify(tutorRepository, Mockito.times(0)).save(any());
    }


    @Test
    @DisplayName("teste de atualizar com email e ja existir e receber exceção")
    void testUpdateTutorEmailJaExiste(){

        Mockito.when(tutorRepository.findById(tutor.getId())).thenReturn(Optional.of(tutor));
        Mockito.when(tutorRepository.existsByEmailAndIdNot(anyString(), anyLong())).thenReturn(true);

        var resposta = Assertions.assertThrows(IllegalArgumentException.class, ()->{
            tutorService.update(tutor.getId(), outroTutor);
        });

        Assertions.assertEquals(resposta.getMessage(), "Já existe um usuário com este email.");
        Mockito.verify(tutorRepository, Mockito.times(1)).findById(any());
        Mockito.verify(tutorRepository, Mockito.times(1)).existsByEmailAndIdNot(anyString(), anyLong());
        Mockito.verify(tutorRepository, Mockito.times(1)).existsByCpfAndIdNot(anyString(), anyLong());
        Mockito.verify(tutorRepository, Mockito.times(1)).existsByTelefoneAndIdNot(anyString(), anyLong());
        Mockito.verify(tutorRepository, Mockito.times(0)).save(any());
    }

    @Test
    @DisplayName("teste de atualizar com email e ja existir e receber exceção")
    void testUpdateTutorTelefoneJaExiste(){

        Mockito.when(tutorRepository.findById(tutor.getId())).thenReturn(Optional.of(tutor));
        Mockito.when(tutorRepository.existsByTelefoneAndIdNot(anyString(), anyLong())).thenReturn(true);

        var resposta = Assertions.assertThrows(IllegalArgumentException.class, ()->{
            tutorService.update(tutor.getId(), outroTutor);
        });

        Assertions.assertEquals(resposta.getMessage(), "Já existe um usuário com este telefone.");
        Mockito.verify(tutorRepository, Mockito.times(1)).findById(any());
        Mockito.verify(tutorRepository, Mockito.times(1)).existsByCpfAndIdNot(anyString(), anyLong());
        Mockito.verify(tutorRepository, Mockito.times(1)).existsByTelefoneAndIdNot(anyString(), anyLong());
        Mockito.verify(tutorRepository, Mockito.times(0)).save(any());
    }


    @Test
    void findByNome() {
        Mockito.when(tutorRepository.findByNomeContainingIgnoreCase(any())).thenReturn(Optional.of(tutor));

        var resposta = tutorService.findByNome(tutor.getNome());

        Assertions.assertEquals(tutor.getNome(), resposta.getNome());
    }

    @Test
    void findByNomeError() {
        Mockito.when(tutorRepository.findByNomeContainingIgnoreCase(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            tutorService.findByNome(tutor.getNome());
        });
    }

    @Test
    void findByCPF() {

        Mockito.when(tutorRepository.findByCpf(any())).thenReturn(Optional.of(tutor));

        var resposta = tutorService.findByCPF(tutor.getCpf());

        Assertions.assertEquals(tutor.getCpf(), resposta.getCpf());
    }

    @Test
    void findByCPFerror() {
        Mockito.when(tutorRepository.findByCpf(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            tutorService.findByCPF(tutor.getCpf());
        });
    }


    @Test
    void findByEmail() {

        Mockito.when(tutorRepository.findByEmail(any())).thenReturn(Optional.of(tutor));

        var resposta = tutorService.findByEmail(tutor.getEmail());

        Assertions.assertEquals(tutor.getEmail(), resposta.getEmail());
    }

    @Test
    void findByEmailerror() {
        Mockito.when(tutorRepository.findByEmail(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            tutorService.findByEmail(tutor.getEmail());
        });
    }

    @Test
    void login() {

        Mockito.when(tutorService.findAll()).thenReturn(tutores);

        var login = tutorService.login(tutor.getEmail(), tutor.getSenha());

        Tutor current_user = tutorService.getCurrentUser();

        Assertions.assertTrue(login);
        Assertions.assertNotNull(current_user);
        assertEquals(tutor, current_user);

    }

    @Test
    void loginFalse() {

        Mockito.when(tutorService.findAll()).thenReturn(new ArrayList<>());

        var login = tutorService.login(tutor.getEmail(), tutor.getSenha());
        Tutor current = tutorService.getCurrentUser();

        assertNull(current);
        Assertions.assertFalse(login);
    }


//    @Test
//    void getCurrentUser() {
//
//
//
//
//
//    }

    @Test
    void logout() {
        Mockito.when(tutorService.findAll()).thenReturn(tutores);

        var login = tutorService.login(tutor.getEmail(), tutor.getSenha());

        Assertions.assertTrue(login);

        tutorService.logout();

        assertNull(tutorService.getCurrentUser());

    }
}
