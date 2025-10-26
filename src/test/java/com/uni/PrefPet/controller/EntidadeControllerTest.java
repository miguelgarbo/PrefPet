package com.uni.PrefPet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uni.PrefPet.exception.NotFoundException;
import com.uni.PrefPet.model.Enum.TipoEntidade;
import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.service.EntidadeService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EntidadeController.class)
class EntidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EntidadeService entidadeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Entidade entidade;

    @BeforeEach
    void setUp() {
        entidade = new Entidade();
        entidade.setId(1L);
        entidade.setNome("Prefeitura de Foz");
        entidade.setCpf("111.444.777-35"); // CPF válido
        entidade.setCnpj("11.222.333/0001-81"); // CNPJ válido
        entidade.setEmail("prefeitura@foz.gov.br"); // e-mail válido
        entidade.setTelefone("+5545999999999"); // telefone válido
        entidade.setCidade("Foz do Iguaçu");
        entidade.setEstado("PR");
        entidade.setCep("85800000"); // obrigatório
        entidade.setSenha("Senha123"); // obrigatório
        entidade.setTipoEntidade(TipoEntidade.PREFEITURA);
    }

    @Test
    @DisplayName("Deve criar entidade com sucesso (POST /entidades)")
    void saveEntidadeSucesso() throws Exception {
        when(entidadeService.save(any(Entidade.class))).thenReturn(entidade);

        mockMvc.perform(post("/entidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entidade)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("prefeitura@foz.gov.br"))
                .andExpect(jsonPath("$.nome").value("Prefeitura de Foz"));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar entidade inexistente (GET /entidades/{id})")
    void findByIdErro() throws Exception {
        when(entidadeService.findById(999L))
                .thenThrow(new NotFoundException("Entidade Não Encontrada"));

        mockMvc.perform(get("/entidades/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar entidade existente (GET /entidades/{id})")
    void findByIdSucesso() throws Exception {
        when(entidadeService.findById(1L)).thenReturn(entidade);

        mockMvc.perform(get("/entidades/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("prefeitura@foz.gov.br"));
    }

    @Test
    @DisplayName("Deve listar todas as entidades (GET /entidades/findAll)")
    void findAllEntidades() throws Exception {
        Entidade e2 = new Entidade();
        e2.setId(2L);
        e2.setNome("Universidade X");
        e2.setCpf("222.333.444-50");
        e2.setCnpj("22.333.444/0001-90");
        e2.setEmail("universidade@x.com");
        e2.setTelefone("+5545988888888");
        e2.setCep("85810000");
        e2.setSenha("Senha123");
        e2.setTipoEntidade(TipoEntidade.UNIVERSIDADE);

        when(entidadeService.findAll()).thenReturn(List.of(entidade, e2));

        mockMvc.perform(get("/entidades/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Deve atualizar entidade existente (PUT /entidades/update/{id})")
    void updateEntidadeSucesso() throws Exception {
        Entidade atualizado = new Entidade();
        atualizado.setEmail("novo@mail.com");
        atualizado.setNome("Prefeitura Nova");
        atualizado.setCpf(entidade.getCpf());
        atualizado.setCnpj(entidade.getCnpj());
        atualizado.setTelefone(entidade.getTelefone());
        atualizado.setCep(entidade.getCep());
        atualizado.setSenha(entidade.getSenha());
        atualizado.setCidade(entidade.getCidade());
        atualizado.setEstado(entidade.getEstado());
        atualizado.setTipoEntidade(entidade.getTipoEntidade());

        when(entidadeService.update(eq(1L), any(Entidade.class))).thenReturn(atualizado);

        mockMvc.perform(put("/entidades/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("novo@mail.com"))
                .andExpect(jsonPath("$.nome").value("Prefeitura Nova"));
    }

    @Test
    @DisplayName("Deve retornar 404 ao atualizar entidade inexistente (PUT /entidades/update/{id})")
    void updateEntidadeErro() throws Exception {
        Entidade atualizado = new Entidade();
        atualizado.setEmail("novo@mail.com");

        when(entidadeService.update(eq(999L), any(Entidade.class))).thenThrow(new NotFoundException("Entidade Não Encontrada"));

        mockMvc.perform(put("/entidades/update/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve deletar entidade existente (DELETE /entidades/{id})")
    void deleteEntidadeSucesso() throws Exception {
        mockMvc.perform(delete("/entidades/1")).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 404 ao deletar entidade inexistente (DELETE /entidades/{id})")
    void deleteEntidadeErro() throws Exception {
        doThrow(new NotFoundException("Entidade Não Encontrada"))
                .when(entidadeService).delete(999L);

        mockMvc.perform(delete("/entidades/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar true se CPF existir (GET /entidades/existsByCPF)")
    void existsByCPFSucesso() throws Exception {
        when(entidadeService.existsByCPF("111.444.777-35")).thenReturn(true);

        mockMvc.perform(get("/entidades/existsByCPF")
                        .param("cpf", "111.444.777-35"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("Deve retornar false se CPF não existir (GET /entidades/existsByCPF)")
    void existsByCPFErro() throws Exception {
        when(entidadeService.existsByCPF("000.000.000-00")).thenReturn(false);

        mockMvc.perform(get("/entidades/existsByCPF")
                        .param("cpf", "000.000.000-00"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @DisplayName("Deve buscar entidades por tipo (GET /entidades/findByTipoEntidade)")
    void findByTipoEntidadeSucesso() throws Exception {
        when(entidadeService.findByTipoEntidade(TipoEntidade.PREFEITURA))
                .thenReturn(List.of(entidade));

        mockMvc.perform(get("/entidades/findByTipoEntidade")
                        .param("tipoEntidade", "PREFEITURA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoEntidade").value("PREFEITURA"));
    }

    @Test
    @DisplayName("Deve buscar entidades por nome (GET /entidades/findByNome)")
    void findByNomeSucesso() throws Exception {
        when(entidadeService.findByNome("Prefeitura")).thenReturn(List.of(entidade));

        mockMvc.perform(get("/entidades/findByNome")
                        .param("nome", "Prefeitura"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Prefeitura de Foz"));
    }

}
