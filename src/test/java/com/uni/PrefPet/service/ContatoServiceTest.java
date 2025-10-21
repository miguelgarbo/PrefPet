package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Contato;
import com.uni.PrefPet.repository.ContatoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class ContatoServiceTest {
    @Mock
    private ContatoRepository contatoRepository;
    @InjectMocks
    private ContatoService contatoService;

    private Contato contato;

    @BeforeEach
    void setUp(){
        contato = new Contato();
        contato.setId(1L);
        contato.setEmail("pablo@mail.com");
        contato.setTelefone("45991240945");
        contato.setNomeOrgao("Prefeitura de Foz");
        contato.setAtivo(true);

    }

    @Test
    @DisplayName("Salvar vacina Funcionou")
    void save() {
        Mockito.when(contatoRepository.save(contato)).thenReturn(contato);
        var resposta = contatoService.save(contato);

        assertEquals(contato, resposta);
        Mockito.verify(contatoRepository, Mockito.times(1)).save(any());
    }
/*
    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void existById() {
    }

    @Test
    void update() {
    }

    @Test
    void existsByEmail() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findByNomeOrgaoContainingIgnoreCase() {
    }

    @Test
    void findByTelefone() {
    }

    @Test
    void mensagemContatoNotFounded() {
    }*/
}
