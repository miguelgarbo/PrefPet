package com.uni.PrefPet.service;

import com.uni.PrefPet.model.AplicacaoVacina;
import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.Vacina;
import com.uni.PrefPet.repository.AplicacaoVacinaRepository;
import com.uni.PrefPet.repository.AnimalRepository;
import com.uni.PrefPet.repository.VacinaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AplicacaoVacinaServiceTest {

    @Mock
    private AplicacaoVacinaRepository aplicacaoVacinaRepository;
    @Mock
    private VacinaRepository vacinaRepository;
    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AplicacaoVacinaService aplicacaoVacinaService;

    @Test
    @DisplayName("Salvar aplicação de vacina com sucesso")
    void salvarAplicacaoVacina() {
        Animal animal = new Animal(); animal.setId(1L);
        Vacina vacina = new Vacina(); vacina.setId(2L);

        AplicacaoVacina app = new AplicacaoVacina();
        app.setAnimal(animal);
        app.setVacina(vacina);
        app.setDataAplicacao(LocalDate.of(2023, 1, 1));
        app.setLote("L1");

        when(vacinaRepository.findById(2L)).thenReturn(Optional.of(vacina));
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(aplicacaoVacinaRepository.countByAnimalAndVacina(animal, vacina)).thenReturn(1L);
        when(aplicacaoVacinaRepository.save(any(AplicacaoVacina.class))).thenAnswer(inv -> inv.getArgument(0));

        AplicacaoVacina saved = aplicacaoVacinaService.save(app, 6);

        assertEquals(2, saved.getNumeroDose());
        assertEquals(LocalDate.of(2023, 7, 1), saved.getDataValidade());
        verify(aplicacaoVacinaRepository).save(any(AplicacaoVacina.class));
    }

    @Test
    @DisplayName("Lançar exceção se vacina não for encontrada ao salvar")
    void erroSalvarVacinaNaoEncontrada() {
        Animal animal = new Animal(); animal.setId(1L);
        Vacina vacina = new Vacina(); vacina.setId(2L);
        AplicacaoVacina app = new AplicacaoVacina();
        app.setAnimal(animal); app.setVacina(vacina); app.setDataAplicacao(LocalDate.now());

        when(vacinaRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> aplicacaoVacinaService.save(app, 6));
    }

    @Test
    @DisplayName("Lançar exceção se animal não for encontrado ao salvar")
    void erroSalvarAnimalNaoEncontrado() {
        Animal animal = new Animal(); animal.setId(1L);
        Vacina vacina = new Vacina(); vacina.setId(2L);
        AplicacaoVacina app = new AplicacaoVacina();
        app.setAnimal(animal); app.setVacina(vacina); app.setDataAplicacao(LocalDate.now());

        when(vacinaRepository.findById(2L)).thenReturn(Optional.of(vacina));
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> aplicacaoVacinaService.save(app, 6));
    }

    @Test
    @DisplayName("Buscar aplicação de vacina existente por ID")
    void buscarAplicacaoPorId() {
        AplicacaoVacina a = new AplicacaoVacina(); a.setId(1L);
        when(aplicacaoVacinaRepository.findById(1L)).thenReturn(Optional.of(a));

        AplicacaoVacina result = aplicacaoVacinaService.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Buscar aplicação de vacina inexistente deve lançar exceção")
    void buscarAplicacaoPorIdInexistente() {
        when(aplicacaoVacinaRepository.findById(9L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> aplicacaoVacinaService.findById(9L));
    }

    @Test
    @DisplayName("Atualizar data de aplicação com sucesso")
    void atualizarAplicacaoVacina() {
        AplicacaoVacina existente = new AplicacaoVacina();
        existente.setId(10L);
        existente.setDataAplicacao(LocalDate.of(2023, 1, 1));

        AplicacaoVacina atualizada = new AplicacaoVacina();
        atualizada.setDataAplicacao(LocalDate.of(2023, 3, 3));

        when(aplicacaoVacinaRepository.findById(10L)).thenReturn(Optional.of(existente));
        when(aplicacaoVacinaRepository.save(any(AplicacaoVacina.class))).thenAnswer(inv -> inv.getArgument(0));

        AplicacaoVacina result = aplicacaoVacinaService.update(10L, atualizada);

        assertEquals(LocalDate.of(2023, 3, 3), result.getDataAplicacao());
    }

    @Test
    @DisplayName("Deletar aplicação de vacina existente")
    void deletarAplicacaoExistente() {
        when(aplicacaoVacinaRepository.existsById(1L)).thenReturn(true);

        String msg = aplicacaoVacinaService.delete(1L);

        assertEquals("AplicacaoVacina Deletada com Sucesso", msg);
        verify(aplicacaoVacinaRepository).deleteById(1L);
    }
}
