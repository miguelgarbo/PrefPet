package com.uni.PrefPet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uni.PrefPet.exception.NotFoundException;
import com.uni.PrefPet.model.Contato;
import com.uni.PrefPet.service.ContatoService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContatoController.class)
class ContatoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ContatoService contatoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar contato com sucesso (POST /contatos/save)")
    void createContatoSucesso() throws Exception {
        Contato contato = new Contato();
        contato.setId(1L);
        contato.setNomeOrgao("Prefeitura de Foz");
        contato.setEmail("pablo@mail.com");
        contato.setTelefone("45991240945");
        contato.setAtivo(true);

        Mockito.when(contatoService.save(any(Contato.class))).thenReturn(contato);

        mockMvc.perform(post("/contatos/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contato)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("pablo@mail.com"))
                .andExpect(jsonPath("$.nomeOrgao").value("Prefeitura de Foz"));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar contato inexistente (GET /contatos/findById/{id})")
    void getContatoByIdErro() throws Exception {
        Mockito.when(contatoService.findById(999L))
                .thenThrow(new NotFoundException("Contato Não Encontrado"));

        mockMvc.perform(get("/contatos/findById/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar contato existente (GET /contatos/findById/{id})")
    void getContatoByIdSucesso() throws Exception {
        Contato contato = new Contato();
        contato.setId(1L);
        contato.setEmail("pablo@mail.com");

        Mockito.when(contatoService.findById(1L)).thenReturn(contato);

        mockMvc.perform(get("/contatos/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("pablo@mail.com"));
    }

    @Test
    @DisplayName("Deve listar todos os contatos (GET /contatos/findAll)")
    void getAllContatos() throws Exception {
        Contato c1 = new Contato();
        c1.setId(1L);
        c1.setEmail("a@mail.com");
        Contato c2 = new Contato();
        c2.setId(2L);
        c2.setEmail("b@mail.com");

        Mockito.when(contatoService.findAll()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/contatos/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Deve atualizar contato existente (PUT /contatos/update/{id})")
    void updateContatoSucesso() throws Exception {
        Contato atualizado = new Contato();
        atualizado.setEmail("novo@mail.com");

        Mockito.when(contatoService.update(eq(1L), any(Contato.class))).thenReturn(atualizado);

        mockMvc.perform(put("/contatos/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("novo@mail.com"));
    }

    @Test
    @DisplayName("Deve retornar 404 ao atualizar contato inexistente (PUT /contatos/update/{id})")
    void updateContatoErro() throws Exception {
        Contato atualizado = new Contato();
        atualizado.setEmail("novo@mail.com");

        Mockito.when(contatoService.update(eq(999L), any(Contato.class)))
                .thenThrow(new NotFoundException("Contato Não Encontrado"));

        mockMvc.perform(put("/contatos/update/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve deletar contato existente (DELETE /contatos/delete/{id})")
    void deleteContatoSucesso() throws Exception {
        Mockito.when(contatoService.delete(1L)).thenReturn("Contato Deletado com Sucesso");


        mockMvc.perform(delete("/contatos/delete/1")).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 404 ao deletar contato inexistente (DELETE /contatos/delete/{id})")
    void deleteContatoErro() throws Exception {
        Mockito.doThrow(new NotFoundException("Contato Não Encontrado"))
                .when(contatoService).delete(999L);

        mockMvc.perform(delete("/contatos/delete/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve buscar contatos por email (GET /contatos/findByEmail/{email})")
    void findByEmailSucesso() throws Exception {
        Contato c = new Contato();
        c.setEmail("pablo@mail.com");

        Mockito.when(contatoService.findByEmail("pablo@mail.com")).thenReturn(List.of(c));

        mockMvc.perform(get("/contatos/findByEmail/pablo@mail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("pablo@mail.com"));
    }

    @Test
    @DisplayName("Deve retornar 404 se não encontrar contato por email")
    void findByEmailErro() throws Exception {
        Mockito.when(contatoService.findByEmail("nao@mail.com"))
                .thenThrow(new NotFoundException("Contato Não Encontrado"));

        mockMvc.perform(get("/contatos/findByEmail/nao@mail.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve buscar contatos por nome do órgão (GET /contatos/findByNomeOrgao/{nomeOrgao})")
    void findByNomeOrgaoSucesso() throws Exception {
        Contato c = new Contato();
        c.setNomeOrgao("Prefeitura de Foz");

        Mockito.when(contatoService.findByNomeOrgaoContainingIgnoreCase("Prefeitura"))
                .thenReturn(List.of(c));

        mockMvc.perform(get("/contatos/findByNomeOrgao/Prefeitura"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeOrgao").value("Prefeitura de Foz"));
    }

    @Test
    @DisplayName("Deve retornar 404 se não encontrar contato por nome do órgão")
    void findByNomeOrgaoErro() throws Exception {
        Mockito.when(contatoService.findByNomeOrgaoContainingIgnoreCase("NaoExiste"))
                .thenThrow(new NotFoundException("Contato Não Encontrado"));

        mockMvc.perform(get("/contatos/findByNomeOrgao/NaoExiste"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve buscar contatos por telefone (GET /contatos/findByTelefone/{telefone})")
    void findByTelefoneSucesso() throws Exception {
        Contato c = new Contato();
        c.setTelefone("45991240945");

        Mockito.when(contatoService.findByTelefone("45991240945")).thenReturn(List.of(c));

        mockMvc.perform(get("/contatos/findByTelefone/45991240945"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].telefone").value("45991240945"));
    }

    @Test
    @DisplayName("Deve retornar 404 se não encontrar contato por telefone")
    void findByTelefoneErro() throws Exception {
        Mockito.when(contatoService.findByTelefone("0000000000"))
                .thenThrow(new NotFoundException("Contato Não Encontrado"));

        mockMvc.perform(get("/contatos/findByTelefone/0000000000"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar true se o email existir (GET /contatos/existsByEmail/{email})")
    void existsByEmailTrue() throws Exception {
        Mockito.when(contatoService.existsByEmail("pablo@mail.com")).thenReturn(true);

        mockMvc.perform(get("/contatos/existsByEmail/pablo@mail.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("Deve retornar false se o email não existir (GET /contatos/existsByEmail/{email})")
    void existsByEmailFalse() throws Exception {
        Mockito.when(contatoService.existsByEmail("nao@mail.com")).thenReturn(false);

        mockMvc.perform(get("/contatos/existsByEmail/nao@mail.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }


}
