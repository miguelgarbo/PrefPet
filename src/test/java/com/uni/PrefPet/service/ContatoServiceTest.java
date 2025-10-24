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
    void deleteContantoInexistente() {
        when(contatoRepository.existsById(999L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> contatoService.delete(999L));
        verify(contatoRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("atualiza apenas os campos que não são nulos")
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

        when(contatoRepository.findById(999L)).thenReturn(Optional.empty());

        // 2️⃣ Verifica que a exceção é lançada
        assertThrows(EntityNotFoundException.class, () -> contatoService.update(999L, new Contato()));
        verify(contatoRepository, never()).save(any());
    }

    @Test
    @DisplayName("existsByEmail retorna true quando email existe")
    void existsByEmailTrue() {
        when(contatoRepository.existsByEmail("pablo@mail.com")).thenReturn(true);
        boolean resultado = contatoService.existsByEmail("pablo@mail.com");
        assertTrue(resultado);
        verify(contatoRepository, times(1)).existsByEmail("pablo@mail.com");
    }

    @Test
    @DisplayName("existsByEmail retorna false quando email não existe")
    void existsByEmailFalse() {
        when(contatoRepository.existsByEmail("naoexiste@mail.com")).thenReturn(false);
        boolean resultado = contatoService.existsByEmail("naoexiste@mail.com");
        assertFalse(resultado);
        verify(contatoRepository, times(1)).existsByEmail("naoexiste@mail.com");
    }

    @Test
    @DisplayName("findByEmail retorna contatos com sucesso")
    void findByEmailSucesso() {
        when(contatoRepository.findByEmail("pablo@mail.com")).thenReturn(Optional.of(List.of(contato)));

        List<Contato> resultado = contatoService.findByEmail("pablo@mail.com");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("pablo@mail.com", resultado.get(0).getEmail());
        verify(contatoRepository, times(1)).findByEmail("pablo@mail.com");
    }

    @Test
    @DisplayName("findByEmail lança exceção quando não encontra")
    void findByEmailErro() {
        when(contatoRepository.findByEmail("naoexiste@mail.com")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> contatoService.findByEmail("naoexiste@mail.com"));

        verify(contatoRepository, times(1)).findByEmail("naoexiste@mail.com");
    }

    @Test
    @DisplayName("findByNomeOrgaoContainingIgnoreCase retorna contatos com sucesso")
    void findByNomeOrgaoSucesso() {
        when(contatoRepository.findByNomeOrgaoContainingIgnoreCase("Prefeitura"))
                .thenReturn(Optional.of(List.of(contato)));

        List<Contato> resultado = contatoService.findByNomeOrgaoContainingIgnoreCase("Prefeitura");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Prefeitura de Foz", resultado.get(0).getNomeOrgao());
        verify(contatoRepository, times(1)).findByNomeOrgaoContainingIgnoreCase("Prefeitura");
    }

    @Test
    @DisplayName("findByNomeOrgaoContainingIgnoreCase lança exceção quando não encontra")
    void findByNomeOrgaoErro() {
        when(contatoRepository.findByNomeOrgaoContainingIgnoreCase("NaoExiste"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> contatoService.findByNomeOrgaoContainingIgnoreCase("NaoExiste"));

        verify(contatoRepository, times(1)).findByNomeOrgaoContainingIgnoreCase("NaoExiste");
    }

    @Test
    @DisplayName("findByTelefone retorna contatos com sucesso")
    void findByTelefoneSucesso() {
        when(contatoRepository.findByTelefoneContaining("45991240945"))
                .thenReturn(Optional.of(List.of(contato)));

        List<Contato> resultado = contatoService.findByTelefone("45991240945");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("45991240945", resultado.get(0).getTelefone());
        verify(contatoRepository, times(1)).findByTelefoneContaining("45991240945");
    }

    @Test
    @DisplayName("findByTelefone lança exceção quando não encontra")
    void findByTelefoneErro() {
        when(contatoRepository.findByTelefoneContaining("0000000000"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> contatoService.findByTelefone("0000000000"));

        verify(contatoRepository, times(1)).findByTelefoneContaining("0000000000");
    }

    @Test
    @DisplayName("mensagemContatoNotFounded retorna EntityNotFoundException com mensagem correta")
    void mensagemContatoNotFounded() {
        EntityNotFoundException excecao = contatoService.mensagemContatoNotFounded();
        assertEquals("Contato Não Encontrado", excecao.getMessage());
    }

}

