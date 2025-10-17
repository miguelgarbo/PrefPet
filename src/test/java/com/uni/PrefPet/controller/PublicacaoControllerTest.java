package com.uni.PrefPet.controller;

import com.uni.PrefPet.model.Enum.TipoEntidade;
import com.uni.PrefPet.model.Imagem;
import com.uni.PrefPet.model.Publicacao;
import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.repository.EntidadeRepository;
import com.uni.PrefPet.repository.PublicacaoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class PublicacaoControllerTest {

    @Autowired
    PublicacaoRepository publicacaoRepository;

    @Autowired
    EntidadeRepository entidadeRepository;

    @Autowired
    TestRestTemplate restTemplate;

    Publicacao publicacao = new Publicacao();
    Entidade entidade = new Entidade();

    @BeforeEach
    void setUp(){

        publicacaoRepository.deleteAll();

        entidade.setNome("Unila");
        entidade.setTelefone("+5545999998888");
        entidade.setCep("85863000");
        entidade.setCpf("12345678909");
        entidade.setCidade("Foz do Iguaçu");
        entidade.setEstado("PR");
        entidade.setCnpj("11.710.732/0001-91");
        entidade.setSenha("senhaSegura123");
        entidade.setEmail("contato@apaf.com.br");
        entidade.setTipoEntidade(TipoEntidade.ONG);

        entidadeRepository.save(entidade);

        publicacao.setTipoPublicacao("CAMPANHA");
        publicacao.setEntidade(entidade);
        publicacao.setDescricao("Teste");
        List<Imagem> imagems = new ArrayList<>();
        Imagem imagem = new Imagem();
        imagem.setUrl("https://www.mozaweb.com/pt/mozaik3D/TOR/okor/ur_varos_kozpontja/960.jpg");
        imagems.add(imagem);
        publicacao.setImagens(imagems);

        publicacaoRepository.save(publicacao);
    }


    @Test
    void save() {

        Publicacao publicacao1 = new Publicacao();

        publicacao1.setTipoPublicacao("CAMPANA");
        publicacao1.setEntidade(entidade);
        publicacao1.setDescricao("Testeee");

        HttpEntity<Publicacao> publicacaoHttpEntity = new HttpEntity<>(publicacao1);

        ResponseEntity<Publicacao> resposta =
                restTemplate.exchange("/publicacao", HttpMethod.POST, publicacaoHttpEntity, Publicacao.class);

        Assertions.assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        Assertions.assertNotNull(resposta.getBody().getId());
        Assertions.assertEquals(publicacao1.getDescricao(),resposta.getBody().getDescricao());

    }

    @Test
    void findById() {

        var resposta = restTemplate.exchange("/publicacao/1", HttpMethod.GET,null, Publicacao.class);

        Assertions.assertEquals(publicacao.getId(),resposta.getBody().getId());
        Assertions.assertEquals(publicacao.getDescricao(),resposta.getBody().getDescricao());

    }

    @Test
    void delete() {


        var resposta = restTemplate.exchange("/publicacao/1", HttpMethod.DELETE,null, Void.class);

        Assertions.assertEquals(HttpStatus.NO_CONTENT,resposta.getStatusCode());
        Assertions.assertEquals(publicacaoRepository.findAll().size(), 0);

    }

    @Test
    void update() {

        Publicacao publicacao1 = new Publicacao();

        publicacao1.setDescricao("Updated");
        publicacao1.setTipoPublicacao("ATIVIDADES");

        HttpEntity<Publicacao> publicacaoHttpEntity = new HttpEntity<>(publicacao1);

        var resposta = restTemplate.exchange("/publicacao/1", HttpMethod.PUT,publicacaoHttpEntity, Publicacao.class);

        Assertions.assertEquals(HttpStatus.OK,resposta.getStatusCode());
        Assertions.assertEquals(resposta.getBody().getDescricao(), publicacao1.getDescricao());
    }

    @Test
    void findAll() {

        var resposta = restTemplate.exchange("/publicacao/findAll", HttpMethod.GET,null, List.class);

        Assertions.assertEquals(HttpStatus.OK,resposta.getStatusCode());
        Assertions.assertEquals(resposta.getBody().size(), 1);
    }

    @Test
    void findByEntidadeNome() {

        var resposta = restTemplate.exchange("/publicacao/byEntidadeNome?nomeEntidade=Unila", HttpMethod.GET,null, List.class);

        Assertions.assertEquals(HttpStatus.OK,resposta.getStatusCode());
        Assertions.assertTrue(resposta.getBody().toString().contains("Teste"));
    }


    @Test
    void findByTipoPublicacao() {

        var resposta = restTemplate.exchange("/publicacao/byTipoPublicacao?tipoPublicacao=CAMPANHA", HttpMethod.GET,null, List.class);

        Assertions.assertEquals(HttpStatus.OK,resposta.getStatusCode());
        Assertions.assertTrue(resposta.getBody().toString().contains("CAMPANHA"));
    }

    @Test
    void findByDescricao() {

        var resposta = restTemplate.exchange("/publicacao/byDescricao?descricao=Teste", HttpMethod.GET,null, List.class);

        Assertions.assertEquals(HttpStatus.OK,resposta.getStatusCode());
        Assertions.assertTrue(resposta.getBody().toString().contains("Teste"));
    }
}
