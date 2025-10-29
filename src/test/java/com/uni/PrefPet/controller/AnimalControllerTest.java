package com.uni.PrefPet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.service.AnimalService;
import com.uni.PrefPet.service.TutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnimalController.class)
class AnimalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AnimalService animalService;

    @MockitoBean
    TutorService tutorService;

    @Autowired
    ObjectMapper objectMapper;

    Animal animal;
    Tutor tutor;
    String animalJson;

    @BeforeEach
    void setUp() throws Exception {
        tutor = new Tutor();
        tutor.setId(1L);
        tutor.setNome("Miguel");
        tutor.setSenha("senha123");
        tutor.setEmail("miguel@example.com");
        tutor.setCep("02919-020");
        tutor.setCpf("466.027.230-30");
        tutor.setCidade("São Paulo");
        tutor.setEstado("São Paulo");
        tutor.setTelefone("45988366777");

        animal = new Animal();
        animal.setId(1L);
        animal.setNome("Rex");
        animal.setEspecie("Cachorro");
        animal.setTutor(tutor);
        animal.setCastrado(true);
        animal.setCor("Marrom");
        animal.setSexo("Macho");
        animal.setMicrochip(true);
        animal.setNumeroMicrochip("1234567890ABC");
        animal.setDataNascimento(LocalDate.of(2020, 5, 12));
        animal.setNaturalidade("São Paulo");
        animal.setImagemUrl("https://exemplo.com/imagem.jpg");
        animal.setRegistroGeral("RG12345");

        animalJson = objectMapper.writeValueAsString(animal);
    }

    // ======================== TESTES SAVE ========================

    @Test
    @DisplayName("Teste Unitario -Salvar animal com sucesso")
    void salvarAnimalSucesso() throws Exception {
        Mockito.when(animalService.save(Mockito.any(Animal.class))).thenReturn(animal);

        mockMvc.perform(post("/animais/save")
                        .contentType("application/json")
                        .content(animalJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(animalJson));
    }

    @Test
    @DisplayName("Teste Unitario - Erro ao salvar animal por campo obrigatório ausente")
    void salvarAnimalErro() throws Exception {
        Mockito.when(animalService.save(Mockito.any(Animal.class)))
                .thenThrow(new IllegalArgumentException("ERRO: campo obrigatório nome não preenchido"));

        mockMvc.perform(post("/animais/save")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(animal)))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("ERRO: campo obrigatório nome não preenchido")));
    }

    // ======================== TESTES FIND BY ID ========================

    @Test
    @DisplayName(" Teste Unitario - Buscar animal por ID com sucesso")
    void buscarPorIdSucesso() throws Exception {
        Mockito.when(animalService.findById(1L)).thenReturn(animal);

        mockMvc.perform(get("/animais/findById/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(animalJson));
    }

    @Test
    @DisplayName("Teste Unitario - Erro ao buscar animal por ID inexistente")
    void buscarPorIdErro() throws Exception {
        Mockito.when(animalService.findById(99L)).thenThrow(new RuntimeException("ERRO: Animal não encontrado"));

        mockMvc.perform(get("/animais/findById/99"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("ERRO: Animal não encontrado")));
    }

    // ======================== TESTES FIND ALL ========================

    @Test
    @DisplayName("Teste Unitario - Listar todos os animais com sucesso")
    void listarTodosAnimaisSucesso() throws Exception {
        Mockito.when(animalService.findAll()).thenReturn(List.of(animal));

        mockMvc.perform(get("/animais/findAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(animal))));
    }

    @Test
    @DisplayName(" Teste Unitario - Erro ao listar animais (vazio ou falha no serviço)")
    void listarTodosAnimaisErro() throws Exception {
        Mockito.when(animalService.findAll()).thenThrow(new RuntimeException("ERRO: Nenhum animal encontrado"));

        mockMvc.perform(get("/animais/findAll"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("ERRO: Nenhum animal encontrado")));
    }

    // ======================== TESTES UPDATE ========================

    @Test
    @DisplayName("Teste Unitario - Atualizar animal com sucesso")
    void atualizarAnimalSucesso() throws Exception {
        Mockito.when(animalService.update(Mockito.eq(1L), Mockito.any(Animal.class))).thenReturn(animal);

        mockMvc.perform(put("/animais/update/1")
                        .contentType("application/json")
                        .content(animalJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(animalJson));
    }

    @Test
    @DisplayName("Teste Unitario - Erro ao atualizar animal inexistente")
    void atualizarAnimalErro() throws Exception {
        Mockito.when(animalService.update(Mockito.eq(99L), Mockito.any(Animal.class)))
                .thenThrow(new RuntimeException("ERRO: Animal não encontrado para atualização"));

        mockMvc.perform(put("/animais/update/99")
                        .contentType("application/json")
                        .content(animalJson))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("ERRO: Animal não encontrado para atualização")));
    }

    // ======================== TESTES DELETE ========================

    @Test
    @DisplayName(" Teste Unitario -Excluir animal com sucesso")
    void deletarAnimalSucesso() throws Exception {
        Mockito.when(animalService.delete(animal.getId()))
                .thenReturn("Animal com id " + animal.getId() + " foi excluído com sucesso.");

        mockMvc.perform(delete("/animais/delete/" + animal.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Teste Unitario - Erro ao excluir animal inexistente")
    void deletarAnimalErro() throws Exception {
        Mockito.doThrow(new RuntimeException("ERRO: Animal não encontrado para exclusão"))
                .when(animalService).delete(99L);

        mockMvc.perform(delete("/animais/delete/99"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("ERRO: Animal não encontrado para exclusão")));
    }

    // ======================== TESTES FIND BY NOME ========================

    @Test
    @DisplayName("Teste Unitario - Buscar animal por nome com sucesso")
    void buscarPorNomeSucesso() throws Exception {
        Mockito.when(animalService.findByNome("Rex")).thenReturn(animal);

        mockMvc.perform(get("/animais/findByNome").param("nome", "Rex"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(animalJson));
    }

    @Test
    @DisplayName("Teste Unitario - Erro ao buscar animal por nome inexistente")
    void buscarPorNomeErro() throws Exception {
        Mockito.when(animalService.findByNome("Inexistente"))
                .thenThrow(new RuntimeException("ERRO: Animal não encontrado"));

        mockMvc.perform(get("/animais/findByNome").param("nome", "Inexistente"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("ERRO: Animal não encontrado")));
    }

    // ======================== TESTES FIND POR ESPÉCIE ========================

    @Test
    @DisplayName("Teste Unitario - Buscar animal por espécie com sucesso")
    void buscarPorEspecieSucesso() throws Exception {
        Mockito.when(animalService.findByEspecieNome("Cachorro")).thenReturn(List.of(animal));

        mockMvc.perform(get("/animais/findByEspecie").param("especie", "Cachorro"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(animal))));
    }

    @Test
    @DisplayName("Teste Unitario - Erro ao buscar animal por espécie inexistente")
    void buscarPorEspecieErro() throws Exception {
        Mockito.when(animalService.findByEspecieNome("Gato"))
                .thenThrow(new RuntimeException("ERRO: Nenhum animal encontrado para a espécie informada"));

        mockMvc.perform(get("/animais/findByEspecie").param("especie", "Gato"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("ERRO: Nenhum animal encontrado")));
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animais por cor - sucesso")
    void findByCorSucesso() throws Exception {
        List<Animal> animais = List.of(animal);

        Mockito.when(animalService.findByCor("preto")).thenReturn(animais);

        mockMvc.perform(get("/animais/findByCor")
                        .param("cor", "preto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value(animal.getNome()));
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animais por sexo - sucesso")
    void findBySexoSucesso() throws Exception {
        List<Animal> animais = List.of(animal);

        Mockito.when(animalService.findBySexo("Macho")).thenReturn(animais);

        mockMvc.perform(get("/animais/findBySexo")
                        .param("sexo", "Macho"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sexo").value(animal.getSexo()));
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animais por castrado - sucesso")
    void findByCastradoSucesso() throws Exception {
        List<Animal> animais = List.of(animal);

        Mockito.when(animalService.findByCastrado(true)).thenReturn(animais);

        mockMvc.perform(get("/animais/findByCastrado")
                        .param("castrado", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].castrado").value(animal.getCastrado()));
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animal por número do microchip - sucesso")
    void findByMicrochipSucesso() throws Exception {
        Mockito.when(animalService.findByMicrochip("123456789")).thenReturn(animal);

        mockMvc.perform(get("/animais/findByMicrochip")
                        .param("numeroMicrochip", "123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(animal.getNome()));
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animais por tutorId - sucesso")
    void findByTutorIdSucesso() throws Exception {
        List<Animal> animais = List.of(animal);

        Mockito.when(animalService.findByTutorId(1L)).thenReturn(animais);

        mockMvc.perform(get("/animais/findByTutorId/{tutorId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tutor.id").value(animal.getTutor().getId()));
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animais por data de nascimento - sucesso")
    void findByDataNascimentoSucesso() throws Exception {
        List<Animal> animais = List.of(animal);

        LocalDate dataNascimento = LocalDate.of(2020, 5, 12);

        Mockito.when(animalService.findByDataNascimento(dataNascimento)).thenReturn(animais);

        mockMvc.perform(get("/animais/findByDataNascimento")
                        .param("dataNascimento", dataNascimento.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dataNascimento").value(dataNascimento.toString()));
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animais por registro geral - sucesso")
    void findByRegistroGeralSucesso() throws Exception {
        List<Animal> animais = List.of(animal);

        Mockito.when(animalService.findByRegistroGeral("RG123")).thenReturn(animais);

        mockMvc.perform(get("/animais/findByRegistroGeral")
                        .param("registroGeral", "RG123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].registroGeral").value(animal.getRegistroGeral()));
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animais com idade acima - sucesso")
    void findAnimaisIdadeAcimaSucesso() throws Exception {
        List<Animal> animais = List.of(animal);

        Mockito.when(animalService.findAnimaisIdadeAcima(5)).thenReturn(animais);

        mockMvc.perform(get("/animais/findAnimaisIdadeAcima")
                        .param("idade", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value(animal.getNome()));
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animais entre idades - sucesso")
    void findAnimaisEntreIdadeSucesso() throws Exception {
        List<Animal> animais = List.of(animal);

        Mockito.when(animalService.findAnimaisEntreIdade(10, 2)).thenReturn(animais);

        mockMvc.perform(get("/animais/findAnimaisEntreIdade")
                        .param("idadeMin", "2")
                        .param("idadeMax", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value(animal.getNome()));
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animais por cor - erro")
    void findByCorErro() throws Exception {
        Mockito.when(animalService.findByCor("vermelho"))
                .thenThrow(new RuntimeException("Erro ao buscar por cor"));

        mockMvc.perform(get("/animais/findByCor")
                        .param("cor", "vermelho"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animais por sexo - erro")
    void findBySexoErro() throws Exception {
        Mockito.when(animalService.findBySexo("Desconhecido"))
                .thenThrow(new RuntimeException("Sexo inválido"));

        mockMvc.perform(get("/animais/findBySexo")
                        .param("sexo", "Desconhecido"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animais por castrado - erro")
    void findByCastradoErro() throws Exception {
        Mockito.when(animalService.findByCastrado(true))
                .thenThrow(new RuntimeException("Erro ao buscar castrados"));

        mockMvc.perform(get("/animais/findByCastrado")
                        .param("castrado", "true"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animal por número do microchip - erro")
    void findByMicrochipErro() throws Exception {
        Mockito.when(animalService.findByMicrochip("000000"))
                .thenThrow(new RuntimeException("Microchip não encontrado"));

        mockMvc.perform(get("/animais/findByMicrochip")
                        .param("numeroMicrochip", "000000"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animais por tutorId - erro")
    void findByTutorIdErro() throws Exception {
        Mockito.when(animalService.findByTutorId(99L))
                .thenThrow(new RuntimeException("Tutor não encontrado"));

        mockMvc.perform(get("/animais/findByTutorId/{tutorId}", 99L))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animais por data de nascimento - erro")
    void findByDataNascimentoErro() throws Exception {
        LocalDate dataNascimento = LocalDate.of(2050, 1, 1);

        Mockito.when(animalService.findByDataNascimento(dataNascimento))
                .thenThrow(new RuntimeException("Data inválida"));

        mockMvc.perform(get("/animais/findByDataNascimento")
                        .param("dataNascimento", dataNascimento.toString()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animais por registro geral - erro")
    void findByRegistroGeralErro() throws Exception {
        Mockito.when(animalService.findByRegistroGeral("RG999"))
                .thenThrow(new RuntimeException("Registro geral não encontrado"));

        mockMvc.perform(get("/animais/findByRegistroGeral")
                        .param("registroGeral", "RG999"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Teste Unitario - Buscar animais entre idades - erro de validação (idadeMin > idadeMax)")
    void findAnimaisEntreIdadeErroValidacao() throws Exception {
        Mockito.when(animalService.findAnimaisEntreIdade(5, 10))
                .thenThrow(new RuntimeException("erro de busca de animais entre idades"));
        // Realiza a requisição HTTP com intervalo inválido
        mockMvc.perform(get("/animais/findAnimaisEntreIdade")
                        .param("idadeMin", "10")  // idadeMin maior
                        .param("idadeMax", "5"))   // idadeMax menor
                .andExpect(status().is5xxServerError())  // Espera um erro 404 Not Found
                .andExpect(content().string(containsString("erro de busca de animais entre idades")));
    }

    @Test
    @DisplayName("Teste Unitario - erro ao buscar idade de animais acima")
    void findAnimaisIdadeAcimaErro() throws Exception {
        Mockito.when(animalService.findAnimaisIdadeAcima(1))
                .thenThrow(new RuntimeException("Erro ao buscar animais acima"));

        mockMvc.perform(get("/animais/findAnimaisIdade")
                .param("idade", "1"))
                .andExpect(status().is5xxServerError());
    }

}
