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
    void setUp() {
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

    @Test
    @DisplayName("FindById Funcionou")
    void findById() {
        Mockito.when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));
        var idResposta = contatoService.findById(1L);
        assertEquals(contato, idResposta);
        verify(contatoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("FindById não Funcionou")
    void findByIdErro() {
        Mockito.when(contatoRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> contatoService.findById(999L));
        verify(contatoRepository, times(1)).findById(999L);

    }

    @Test
    void findAll() {
        Contato contatoTeste = new Contato();
        contatoTeste.setId(2L);
        contatoTeste.setEmail("testeMockito@mail.com");
        contatoTeste.setNomeOrgao("MockitoTeste123");
        contatoTeste.setTelefone("45991240945");
        contatoTeste.setAtivo(true);

        when(contatoRepository.findAll()).thenReturn(List.of(contato, contatoTeste));//simula que o retorna um lista com os dois contatos
        List<Contato> resultadoTeste = contatoService.findAll();

        assertNotNull(resultadoTeste);
        assertEquals(2, resultadoTeste.size());
        assertTrue(resultadoTeste.stream()
                .anyMatch(c -> c.getNomeOrgao().equals("Prefeitura de Foz")));
        assertTrue(resultadoTeste.stream()
                .anyMatch(c -> c.getNomeOrgao().equals("MockitoTeste123")));


        verify(contatoRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Deletar Contato existente Sucesso")
    void deleteContatoExistente() {
        when(contatoRepository.existsById(1L)).thenReturn(true);
        String resposta = contatoService.delete(1L);
        assertEquals("Contato Deletado com Sucesso", resposta);
        verify(contatoRepository, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("Tenta deletar um contato inexistente")
    void deleteContantoInexistente(){
        when(contatoRepository.existsById(999L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, ()-> contatoService.delete(999L));
        verify(contatoRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("update atualiza apenas os campos que não são nulos")
    void updateContatoComSucesso() {
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));

        Contato atualizado = new Contato();
        atualizado.setEmail("novo@mail.com");
        atualizado.setTelefone(null); // não deve alterar
        atualizado.setAtivo(false);   // deve alterar

        when(contatoRepository.save(any(Contato.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Contato resultado = contatoService.update(1L, atualizado);
        assertEquals("novo@mail.com", resultado.getEmail()); // email mudou
        assertEquals(false, resultado.getAtivo());           // ativo mudou
        assertEquals("45991240945", resultado.getTelefone()); // telefone original mantido

        verify(contatoRepository, times(1)).save(any(Contato.class));
    }

    @Test
    @DisplayName("lança a exceção se o contato não existir")
    void updateContatoInexistente() {
        // 1️⃣ Simula que o contato não existe
        when(contatoRepository.findById(999L)).thenReturn(Optional.empty());

        // 2️⃣ Verifica que a exceção é lançada
        assertThrows(EntityNotFoundException.class, () ->
                contatoService.update(999L, new Contato()));

        // 3️⃣ Garante que não tenta salvar nada
        verify(contatoRepository, never()).save(any());
    }

/*
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
