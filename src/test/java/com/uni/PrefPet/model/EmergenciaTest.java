package com.uni.PrefPet.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class EmergenciaTest {

    Emergencia emergencia = new Emergencia();

    Contato contato = new Contato();


    @BeforeEach
    void setUp(){
        emergencia.setId(1L);
        emergencia.setNome("MAUS TRATOS");

        contato.setId(1L);
        contato.setNomeOrgao("Unila");
        contato.setTelefone("45988366777");
        contato.setEmail("unila@example.com");
        contato.setAtivo(true);
    }

    @Test
    @DisplayName("TESTE UNITARIO: esse teste deve adicionar um contato de emergencia")
    void addContato() {

        emergencia.addContato(contato);
        Assertions.assertEquals(1,emergencia.getContatos().size());
    }

    @Test
    @DisplayName("TESTE UNITARIO: esse teste deve remover um contato de emergencia")
    void removeContato() {

        emergencia.addContato(contato);
        Assertions.assertEquals(1,emergencia.getContatos().size());

        emergencia.removeContato(contato);
        Assertions.assertEquals(0,emergencia.getContatos().size());

    }
}