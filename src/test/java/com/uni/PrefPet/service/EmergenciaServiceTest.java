package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Contato;
import com.uni.PrefPet.model.Emergencia;
import com.uni.PrefPet.repository.EmergenciaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmergenciaServiceTest {

    @InjectMocks
    EmergenciaService emergenciaService;

    @Mock
    EmergenciaRepository emergenciaRepository;

    Emergencia emergencia = new Emergencia();

    List<Emergencia> emergencias = new ArrayList<>();

    Contato contato  = new Contato();

    List<Contato> contatos = new ArrayList<>();

    @BeforeEach
    void setUp(){
        contato.setAtivo(true);
        contato.setTelefone("45988366777");
        contato.setEmail("unila@example.com");
        contato.setNomeOrgao("Unila");
        contato.setId(1L);

        emergencia.setNome("MAUS TRATOS");
        emergencia.setId(1L);
        emergencia.addContato(contato);

        emergencias.add(emergencia);
    }

    @Test
    void save() {
        Mockito.when(emergenciaRepository.save(emergencia)).thenReturn(emergencia);

        var resposta = emergenciaService.save(emergencia);

        Assertions.assertEquals(emergencia,resposta);
    }

    @Test
    void saveError() {
        Mockito.when(emergenciaRepository.existsByNome(emergencia.getNome())).thenReturn(true);

        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            emergenciaService.save(emergencia);
        });

    }


    @Test
    void findById() {
        Mockito.when(emergenciaRepository.findById(1L)).thenReturn(Optional.of(emergencia));

        var resposta = emergenciaService.findById(1L);

        Assertions.assertEquals(emergencia,resposta);
    }

    @Test
    void findByIdError() {
        Mockito.when(emergenciaRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            emergenciaService.findById(1L);
        });
    }

    @Test
    void findAll() {
        Mockito.when(emergenciaRepository.findAll()).thenReturn(emergencias);

        var resposta = emergenciaService.findAll();

        Assertions.assertEquals(emergencias,resposta);
    }

    @Test
    void delete() {

        Mockito.when(emergenciaRepository.existsById(1L)).thenReturn(true);

        Mockito.doNothing().when(emergenciaRepository).deleteById(1L);

        var resposta = emergenciaService.delete(1L);

        Assertions.assertEquals("Emergencia deletada com sucesso",resposta);
    }


    @Test
    void deleteError() {
        Mockito.when(emergenciaRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            emergenciaService.delete(1L);
        });
    }

    @Test
    void update() {
        Emergencia emergencia1 = new Emergencia();

        emergencia1.setNome("Prefeitura");

        emergencia.setNome(emergencia1.getNome());

        Mockito.when(emergenciaRepository.findById(1L)).thenReturn(Optional.of(emergencia));

        Mockito.when(emergenciaRepository.save(emergencia)).thenReturn(emergencia);

        var resposta = emergenciaService.update(1L, emergencia1);

        Assertions.assertEquals("Prefeitura",resposta.getNome());
    }

    @Test
    void updateError() {

        Emergencia emergencia1 = new Emergencia();

        emergencia1.setNome("Prefeitura");

        emergencia.setNome(emergencia1.getNome());

        Mockito.when(emergenciaRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,()->{
            emergenciaService.update(1L, emergencia1);
        });

    }

    @Test
    void findByNome() {
        Mockito.when(emergenciaRepository.findByNome("Unila")).thenReturn(Optional.of(emergencias));

        var resposta = emergenciaService.findByNome("Unila");

        Assertions.assertEquals(emergencias,resposta);
    }

    @Test
    void findByNomeError() {
        Mockito.when(emergenciaRepository.findByNome("Unila")).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, ()->{
            emergenciaService.findByNome("Unila");
        });

    }
}
