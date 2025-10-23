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
import java.util.ArrayList;
import java.util.List;
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
    @DisplayName("Lançar exceção ao tentar atualizar aplicação inexistente")
    void atualizarAplicacaoInexistente() {
        AplicacaoVacina atualizada = new AplicacaoVacina();
        atualizada.setDataAplicacao(LocalDate.of(2023, 4, 4));

        when(aplicacaoVacinaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> aplicacaoVacinaService.update(99L, atualizada));
    }


    @Test
    @DisplayName("Deletar aplicação de vacina existente")
    void deletarAplicacaoExistente() {
        when(aplicacaoVacinaRepository.existsById(1L)).thenReturn(true);

        String msg = aplicacaoVacinaService.delete(1L);

        assertEquals("AplicacaoVacina Deletada com Sucesso", msg);
        verify(aplicacaoVacinaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Lançar exceção ao tentar deletar aplicação inexistente")
    void deletarAplicacaoInexistente() {
        when(aplicacaoVacinaRepository.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> aplicacaoVacinaService.delete(99L));
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar aplicação por lote inexistente")
    void buscarPorLoteInexistente() {
        when(aplicacaoVacinaRepository.findByLote("X999")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                aplicacaoVacinaService.findByLote("X999"));
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar por data de aplicação inexistente")
    void buscarPorDataAplicacaoInexistente() {
        when(aplicacaoVacinaRepository.findByDataAplicacao(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                aplicacaoVacinaService.findByDataAplicacao(LocalDate.now()));
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar por validade anterior inexistente")
    void buscarPorValidadeBeforeInexistente() {
        when(aplicacaoVacinaRepository.findByDataValidadeBefore(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                aplicacaoVacinaService.findByValidadeBefore(LocalDate.now()));
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar por validade posterior inexistente")
    void buscarPorValidadeAfterInexistente() {
        when(aplicacaoVacinaRepository.findByDataValidadeAfter(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                aplicacaoVacinaService.findByValidadeAfter(LocalDate.now()));
    }

    @Test
    @DisplayName("Deve lançar exceção ao validar data de aplicação posterior à validade")
    void validarDataAplicacaoPosteriorAValidade() {
        LocalDate aplicacao = LocalDate.of(2025, 10, 10);
        LocalDate validade = LocalDate.of(2025, 9, 10); // antes → deve dar erro

        assertThrows(IllegalArgumentException.class, () ->
                aplicacaoVacinaService.validarDataAplicacaoEValidade(aplicacao, validade));
    }

    @Test
    @DisplayName("Deve gerar data de validade corretamente com meses adicionados")
    void gerarDataValidadeCorretamente() {
        LocalDate aplicacao = LocalDate.of(2024, 1, 10);

        LocalDate validade = aplicacaoVacinaService.gerarDataValidade(aplicacao, 6);

        assertEquals(LocalDate.of(2024, 7, 10), validade);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar aplicação por animal inexistente")
    void buscarPorAnimalInexistente() {
        when(aplicacaoVacinaRepository.findByAnimalId(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                aplicacaoVacinaService.findByAnimal(1L));
    }

    @Test
    @DisplayName("Deve buscar aplicação por animal")
    void buscarPorAnimal() {

        List<AplicacaoVacina> aplicacaoVacinas = new ArrayList<>();

        when(aplicacaoVacinaRepository.findByAnimalId(1L)).thenReturn(Optional.of(aplicacaoVacinas));

        var resposta = aplicacaoVacinaService.findByAnimal(1L);

        assertEquals(aplicacaoVacinas,resposta);

    }


    @DisplayName("Teste deve listar as vacinas aplicadas")
    void listarVacinasAplicadas() {
        AplicacaoVacina a1 = new AplicacaoVacina();
        AplicacaoVacina a2 = new AplicacaoVacina();
        when(aplicacaoVacinaRepository.findAll()).thenReturn(List.of(a1, a2));

        var resultado = aplicacaoVacinaService.findAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(aplicacaoVacinaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("delete deve lançar EntityNotFoundException quando id inexistente")
    void delete_deveLancarQuandoIdInexistente() {
        Long idInexistente = 999L;
        when(aplicacaoVacinaRepository.existsById(idInexistente)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> aplicacaoVacinaService.delete(idInexistente));
        verify(aplicacaoVacinaRepository, never()).deleteById(anyLong());
    }
}
