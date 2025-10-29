package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Publicacao;
import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.repository.PublicacaoRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

//100%
@ExtendWith(MockitoExtension.class)
public class PublicacaoServiceTest {

    @InjectMocks
    PublicacaoService publicacaoService;

    @Mock
    PublicacaoRepository publicacaoRepository;

    Publicacao publicacao = new Publicacao();
    List<Publicacao> publicacoes = new ArrayList<>();

    @BeforeEach
    void setUp(){
        Entidade entidade = new Entidade();
        entidade.setNome("Unila");
        entidade.setId(1L);

        publicacao.setDescricao("Lorem Ipsum");
        publicacao.setEntidade(entidade);
        publicacao.setTipoPublicacao("CAMPANHA DE VACINAÇÃO");

        publicacoes.add(publicacao);
    }

    @Test
    @DisplayName("Teste Unitário: buscar todas as publicações")
    void findAll() {
        Mockito.when(publicacaoRepository.findAll()).thenReturn(publicacoes);
        var resposta = publicacaoService.findAll();

        Assertions.assertEquals(publicacoes, resposta);
        Mockito.verify(publicacaoRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Teste Unitário: salvar uma publicação")
    void save() {
        Publicacao publicacaoNova = new Publicacao();
        Mockito.when(publicacaoRepository.save(any())).thenReturn(publicacaoNova);

        var resposta = publicacaoService.save(publicacaoNova);

        Assertions.assertEquals(publicacaoNova, resposta);
        Mockito.verify(publicacaoRepository, Mockito.times(1)).save(publicacaoNova);
    }

    @Test
    @DisplayName("Teste Unitário: buscar publicação por ID")
    void findById() {
        Mockito.when(publicacaoRepository.findById(1L)).thenReturn(Optional.of(publicacao));

        var resposta = publicacaoService.findById(1L);

        Assertions.assertEquals(publicacao, resposta);
        Mockito.verify(publicacaoRepository, Mockito.times(1)).findById(any());
    }

    @Test
    @DisplayName("Teste Unitário: falha ao buscar publicação por ID inexistente")
    void findByIdError() {
        Mockito.when(publicacaoRepository.findById(1L))
                .thenThrow(new EntityNotFoundException("Publicacao com id 1 não encontrado."));

        Assertions.assertThrows(EntityNotFoundException.class, () -> publicacaoService.findById(1L));
        Mockito.verify(publicacaoRepository, Mockito.times(1)).findById(any());
    }

    @Test
    @DisplayName("Teste Unitário: atualizar publicação com sucesso")
    void update() {
        Publicacao publicacaoNova = new Publicacao();
        publicacaoNova.setDescricao("Texto atualizado");

        Mockito.when(publicacaoRepository.findById(publicacao.getId())).thenReturn(Optional.of(publicacao));
        Mockito.when(publicacaoRepository.save(publicacao)).thenReturn(publicacao);

        var resposta = publicacaoService.update(publicacao.getId(), publicacaoNova);

        Assertions.assertEquals("Texto atualizado", resposta.getDescricao());
        Mockito.verify(publicacaoRepository, Mockito.times(1)).findById(any());
    }

    @Test
    @DisplayName("Teste Unitário: falha ao atualizar publicação inexistente")
    void updateError() {
        Mockito.when(publicacaoRepository.findById(publicacao.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () ->
                publicacaoService.update(publicacao.getId(), any())
        );

        Mockito.verify(publicacaoRepository, Mockito.times(1)).findById(any());
    }

    @Test
    @DisplayName("Teste Unitário: buscar publicação por descrição inexistente")
    void testFindByDescricaoNotFound() {
        Mockito.when(publicacaoRepository.findByDescricaoContaining(any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () ->
                publicacaoService.findByDescricao("hdfhsd")
        );
    }

    @Test
    @DisplayName("Teste Unitário: buscar publicação por tipo inexistente")
    void testFindByTipoPublicacaoNotFound() {
        Mockito.when(publicacaoRepository.findByTipoPublicacao(any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () ->
                publicacaoService.findByTipoPublicacao("CAMPANHA DE CASTRAÇÃO")
        );
    }

    @Test
    @DisplayName("Teste Unitário: buscar publicação por entidade inexistente")
    void testFindByEntidadeNotFound() {
        Mockito.when(publicacaoRepository.findByEntidade(any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () ->
                publicacaoService.findByEntidade(new Entidade())
        );
    }

    @Test
    @DisplayName("Teste Unitário: buscar publicação por nome de entidade inexistente")
    void testFindByEntidadeNomeNotFound() {
        Mockito.when(publicacaoRepository.findByEntidadeNome(any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () ->
                publicacaoService.findByEntidadeNome("Prefeitura")
        );
    }

    @Test
    @DisplayName("Teste Unitário: deletar publicação com sucesso")
    void delete() {
        Mockito.when(publicacaoRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(publicacaoRepository).deleteById(1L);

        String mensagem = publicacaoService.delete(1L);

        Assertions.assertEquals("Publicacao com id 1 foi excluído com sucesso.", mensagem);
        Mockito.verify(publicacaoRepository, Mockito.times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Teste Unitário: falha ao deletar publicação inexistente")
    void deleteError() {
        Mockito.when(publicacaoRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(EntityNotFoundException.class, () -> publicacaoService.delete(1L));
        Mockito.verify(publicacaoRepository, Mockito.times(1)).existsById(any());
    }

    @Test
    @DisplayName("Teste Unitário: buscar publicação por entidade com sucesso")
    void findByEntidade() {
        Mockito.when(publicacaoRepository.findByEntidade(new Entidade())).thenReturn(Optional.of(publicacoes));

        var resposta = publicacaoService.findByEntidade(new Entidade());
        Assertions.assertEquals(publicacoes, resposta);
    }

    @Test
    @DisplayName("Teste Unitário: buscar publicação por nome de entidade com sucesso")
    void findByEntidadeNome() {
        Mockito.when(publicacaoRepository.findByEntidadeNome("Unila")).thenReturn(Optional.of(publicacoes));

        var resposta = publicacaoService.findByEntidadeNome("Unila");
        Assertions.assertEquals(publicacoes, resposta);
    }

    @Test
    @DisplayName("Teste Unitário: buscar publicação por tipo com sucesso")
    void findByTipoPublicacao() {
        Mockito.when(publicacaoRepository.findByTipoPublicacao("CAMPANHA DE VACINAÇÃO"))
                .thenReturn(Optional.of(publicacoes));

        var resposta = publicacaoService.findByTipoPublicacao("CAMPANHA DE VACINAÇÃO");
        Assertions.assertEquals(publicacoes, resposta);
    }

    @Test
    @DisplayName("Teste Unitário: buscar publicação por descrição com sucesso")
    void findByDescricao() {
        Mockito.when(publicacaoRepository.findByDescricaoContaining("Lorem"))
                .thenReturn(Optional.of(publicacoes));

        var resposta = publicacaoService.findByDescricao("Lorem");
        Assertions.assertEquals(publicacoes, resposta);
    }



}

