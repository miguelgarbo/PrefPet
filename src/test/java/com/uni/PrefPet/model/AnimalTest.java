package com.uni.PrefPet.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AnimalTest {

    Animal animal = new Animal();

    @BeforeEach
    void setUp(){
        animal.setId(1L);
        animal.setNome("Rex");
        animal.setEspecie("Canina");
        animal.setCor("Marrom");
        animal.setSexo("Macho");
        animal.setCastrado(true);
        animal.setMicrochip(true);
        animal.setNumeroMicrochip("1234567890");
        animal.setDataNascimento(LocalDate.now().plusYears(5));
        animal.setNaturalidade("Foz do Iguaçu");
    }

    @Test
    @DisplayName("TESTE UNITARIO: esse teste deve retornar a idade do animal de acordo com sua data de nascimento")
    void getIdade() {
        var idade = animal.getIdade();
        Assertions.assertEquals(-5, idade);

    }
}