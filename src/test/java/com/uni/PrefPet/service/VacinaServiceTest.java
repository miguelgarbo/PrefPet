package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Vacina;
import com.uni.PrefPet.repository.VacinaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacinaServiceTest {

    @Mock
    private VacinaRepository vacinaRepository;

    @InjectMocks
    private VacinaService vacinaService;

    @Test
    @DisplayName("Teste p/ salvar a Vacina")
    void salvarVacina() {
        Vacina vacina = new Vacina();
        vacina.setNome("Raiva");

        when(vacinaRepository.save(any(Vacina.class)))
                .thenAnswer(inv -> {
                    Vacina v = inv.getArgument(0);
                    v.setId(1L);
                    return v;
                });

        Vacina resultado = vacinaService.save(vacina);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Raiva", resultado.getNome());
        verify(vacinaRepository, times(1)).save(any(Vacina.class));
    }

    @Test
    @DisplayName("Teste p/ buscar vacida por ID existente")

    void buscarVacinaPorIdExistente() {
        Vacina vacina = new Vacina();
        vacina.setId(2L);
        vacina.setNome("Cinomose");

        when(vacinaRepository.findById(2L)).thenReturn(Optional.of(vacina));

        Vacina resultado = vacinaService.findById(2L);

        assertEquals("Cinomose", resultado.getNome());
        verify(vacinaRepository, times(1)).findById(2L);
    }

    @Test
    @DisplayName("Teste p/ buscar vacina por ID inexistente")
    void buscarPorIdInexistente() {
        when(vacinaRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> vacinaService.findById(99L));

        assertTrue(ex.getMessage().contains("Vacina Não Encontradaa"));
    }

    @Test
    @DisplayName("Listar todas as vacinas")
    void listarTodas() {
        Vacina v1 = new Vacina(); v1.setNome("A");
        Vacina v2 = new Vacina(); v2.setNome("B");

        when(vacinaRepository.findAll()).thenReturn(List.of(v1, v2));

        var lista = vacinaService.findAll();

        assertEquals(2, lista.size());
        verify(vacinaRepository).findAll();
    }

    @Test
    @DisplayName("Deletar vacina existente")
    void deletarVacinaExistente() {
        when(vacinaRepository.existsById(1L)).thenReturn(true);

        String msg = vacinaService.delete(1L);

        assertEquals("Vacina Deletada com Sucesso", msg);
        verify(vacinaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Tentar deletar vacina inexistente deve lançar exceção")
    void deletarVacinaInexistente() {
        when(vacinaRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> vacinaService.delete(1L));
        verify(vacinaRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Atualizar vacina existente")
    void atualizarVacina() {
        Vacina existente = new Vacina();
        existente.setId(10L);
        existente.setNome("Antiga");

        Vacina atualizada = new Vacina();
        atualizada.setNome("Nova");

        when(vacinaRepository.findById(10L)).thenReturn(Optional.of(existente));
        when(vacinaRepository.save(any(Vacina.class))).thenAnswer(inv -> inv.getArgument(0));

        Vacina resultado = vacinaService.update(10L, atualizada);

        assertEquals("Nova", resultado.getNome());
        verify(vacinaRepository).save(existente);
    }

    @Test
    @DisplayName("Atualizar vacina inexistente deve lançar exceção")
    void atualizarVacinaInexistente() {
        when(vacinaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> vacinaService.update(99L, new Vacina()));
    }

    @Test
    @DisplayName("Buscar vacinas por nome contendo parte do texto")
    void buscarVacinasPorNome() {
        Vacina v1 = new Vacina(); v1.setNome("Antirrábica");
        Vacina v2 = new Vacina(); v2.setNome("Antitetânica");

        when(vacinaRepository.findByNomeContainingIgnoreCase("anti"))
                .thenReturn(Optional.of(List.of(v1, v2)));

        List<Vacina> resultado = vacinaService.findByNome("anti");

        assertEquals(2, resultado.size());
        assertTrue(resultado.get(0).getNome().contains("Anti") || resultado.get(0).getNome().contains("anti"));
    }

    @Test
    @DisplayName("Buscar vacinas por nome inexistente deve lançar exceção")
    void buscarVacinasPorNomeInexistente() {
        when(vacinaRepository.findByNomeContainingIgnoreCase("xyz"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> vacinaService.findByNome("xyz"));
    }
}