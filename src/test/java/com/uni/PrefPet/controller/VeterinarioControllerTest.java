package com.uni.PrefPet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uni.PrefPet.model.Usuarios.Veterinario;
import com.uni.PrefPet.service.VeterinarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(VeterinarioController.class)
public class VeterinarioControllerTest {

    @MockitoBean
    VeterinarioService veterinarioService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    String jsonVet;

    Veterinario veterinario = new Veterinario();

    List<Veterinario> veterinarios = new ArrayList<>();

    @BeforeEach
    void setUp() throws  Exception{

        veterinario.setId(1L);
        veterinario.setNome("Marcos");
        veterinario.setTelefone("+5545999998878");
        veterinario.setCep("85851-000");
        veterinario.setCpf("434.484.890-00");
        veterinario.setCidade("Foz do Iguaçu");
        veterinario.setEstado("PR");
        veterinario.setSenha("senha123");
        veterinario.setEmail("marcos@example.com");
        veterinario.setCRMV("123456");

        jsonVet = objectMapper.writeValueAsString(veterinario);

        veterinarios.add(veterinario);
    }

    @Test
    void save() throws Exception {
        Mockito.when(veterinarioService.save(veterinario)).thenReturn(veterinario);

        mockMvc.perform(post("/veterinarios")
                .contentType("application/json")
                .content(jsonVet))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonVet));
    }

    @Test
    void findById() throws Exception {
        Mockito.when(veterinarioService.findById(any())).thenReturn(veterinario);

        mockMvc.perform(get("/veterinarios/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonVet));

    }

    @Test
    void delete_test() throws Exception {
        Mockito.when(veterinarioService.delete(1L)).thenReturn("Veterinario com id " + 1 + " foi excluído com sucesso.");

        mockMvc.perform(delete("/veterinarios/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Veterinario com id " + 1 + " foi excluído com sucesso."));
    }

    @Test
    void update() throws Exception {

        Veterinario vet = new Veterinario();
        vet.setNome("Gabriel");

        veterinario.setNome(vet.getNome());

        String jsonVetBody = objectMapper.writeValueAsString(vet);

        Mockito.when(veterinarioService.update(anyLong(), any())).thenReturn(veterinario);

        mockMvc.perform(put("/veterinarios/1")
                        .contentType("application/json")
                        .content(jsonVetBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Gabriel"))
                .andExpect(jsonPath("$.email").value("marcos@example.com")
                        );
    }

    @Test
    void findAll() throws Exception {

        Mockito.when(veterinarioService.findAll()).thenReturn(veterinarios);

        String jsonLista = objectMapper.writeValueAsString(veterinarios);

        mockMvc.perform(get("/veterinarios/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonLista));

    }

    @Test
    void findByNome() throws Exception {
        Mockito.when(veterinarioService.findByNome("Marcos")).thenReturn(veterinarios);

        String jsonLista = objectMapper.writeValueAsString(veterinarios);

        mockMvc.perform(get("/veterinarios/findByNome")
                        .contentType("application/json")
                        .param("nome", "Marcos"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonLista));
    }

    @Test
    void findByCPF()  throws Exception{
        Mockito.when(veterinarioService.findByCPF("434.484.890-00")).thenReturn(veterinario);

        mockMvc.perform(get("/veterinarios/findByCPF")
                        .contentType("application/json")
                        .param("cpf", "434.484.890-00"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonVet));
    }



    @Test
    void findByEmail() throws Exception {
        Mockito.when(veterinarioService.findByEmail("marcos@example.com")).thenReturn(veterinario);

        mockMvc.perform(get("/veterinarios/findByEmail")
                        .contentType("application/json")
                        .param("email", "marcos@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonVet));

    }
}
