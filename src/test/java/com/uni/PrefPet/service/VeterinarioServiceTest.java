package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Usuarios.Veterinario;
import com.uni.PrefPet.repository.VeterinarioRepository;
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

@ExtendWith(MockitoExtension.class)
public class VeterinarioServiceTest {

    @InjectMocks
    VeterinarioService veterinarioService;

    @Mock
    VeterinarioRepository veterinarioRepository;

    Veterinario veterinario = new Veterinario();
    List<Veterinario> veterinarios = new ArrayList<>();

    @BeforeEach
    void setUp(){
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

        veterinarios.add(veterinario);
    }

    @Test
    @DisplayName("Unitário - Salvar veterinário com sucesso")
    void save() {
        Mockito.when(veterinarioRepository.save(veterinario)).thenReturn(veterinario);
        var response = veterinarioService.save(veterinario);

        Assertions.assertEquals("Marcos", response.getNome());
        Assertions.assertEquals("123456", response.getCRMV());
    }

    @Test
    @DisplayName("Unitário - Falha ao salvar veterinário com CRMV duplicado")
    void saveError() {
        Mockito.when(veterinarioRepository.existsByCRMV(veterinario.getCRMV())).thenReturn(true);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            veterinarioService.save(veterinario);
        });
    }

    @Test
    @DisplayName("Unitário - Buscar todos os veterinários")
    void findAll() {
        Mockito.when(veterinarioRepository.findAll()).thenReturn(veterinarios);
        var response = veterinarioService.findAll();

        Assertions.assertEquals(response.get(0).getNome(), veterinario.getNome());
        Assertions.assertNotNull(response);
    }

    @Test
    @DisplayName("Unitário - Atualizar veterinário com sucesso")
    void update() {
        Veterinario veterinario_update = new Veterinario();
        veterinario_update.setNome("Miguel");
        veterinario_update.setTelefone("+5545999998876");
        veterinario_update.setCep("85851-000");
        veterinario_update.setCpf("434.484.890-00");
        veterinario_update.setCidade("Foz do Iguaçu");
        veterinario_update.setEstado("PR");
        veterinario_update.setSenha("senha123");
        veterinario_update.setEmail("miguel@example.com");
        veterinario_update.setCRMV("123456");

        veterinario.setNome(veterinario_update.getNome());
        veterinario.setTelefone(veterinario_update.getTelefone());
        veterinario.setCep(veterinario_update.getCep());
        veterinario.setCpf(veterinario_update.getCpf());
        veterinario.setCidade(veterinario_update.getCidade());
        veterinario.setEstado(veterinario_update.getEstado());
        veterinario.setSenha(veterinario_update.getSenha());
        veterinario.setEmail(veterinario_update.getEmail());
        veterinario.setCRMV(veterinario_update.getCRMV());

        Mockito.when(veterinarioRepository.findById(1L)).thenReturn(Optional.of(veterinario));
        Mockito.when(veterinarioRepository.save(veterinario)).thenReturn(veterinario);

        var response = veterinarioService.update(1L, veterinario_update);
        Assertions.assertEquals("Miguel", response.getNome());
        Assertions.assertEquals(veterinario, response);
    }

    @Test
    @DisplayName("Unitário - Falha ao atualizar veterinário inexistente")
    void updateError1() {
        Veterinario veterinario_update = new Veterinario();
        veterinario_update.setNome("Miguel");
        veterinario_update.setTelefone("+5545999998876");
        veterinario_update.setCep("85851-000");
        veterinario_update.setCpf("434.484.890-00");
        veterinario_update.setCidade("Foz do Iguaçu");
        veterinario_update.setEstado("PR");
        veterinario_update.setSenha("senha123");
        veterinario_update.setEmail("miguel@example.com");
        veterinario_update.setCRMV("123456");

        Mockito.when(veterinarioRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            veterinarioService.update(1L, veterinario_update);
        });
    }

    @Test
    @DisplayName("Unitário - Falha ao atualizar veterinário com CPF duplicado")
    void updateError2() {
        Mockito.when(veterinarioRepository.findById(1L)).thenReturn(Optional.of(veterinario));
        Mockito.when(veterinarioRepository.existsByCpf(any())).thenReturn(true);

        Veterinario veterinario_update = new Veterinario();
        veterinario_update.setNome("Miguel");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            veterinarioService.update(1L, veterinario_update);
        });
    }

    @Test
    @DisplayName("Unitário - Falha ao atualizar veterinário com telefone duplicado")
    void updateError3() {
        Mockito.when(veterinarioRepository.findById(1L)).thenReturn(Optional.of(veterinario));
        Mockito.when(veterinarioRepository.existsByTelefone(any())).thenReturn(true);

        Veterinario veterinario_update = new Veterinario();
        veterinario_update.setNome("Miguel");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            veterinarioService.update(1L, veterinario_update);
        });
    }

    @Test
    @DisplayName("Unitário - Falha ao atualizar veterinário com CNPJ duplicado")
    void updateError4() {
        Mockito.when(veterinarioRepository.findById(1L)).thenReturn(Optional.of(veterinario));
        Mockito.when(veterinarioRepository.existsByCnpj(any())).thenReturn(true);

        Veterinario veterinario_update = new Veterinario();
        veterinario_update.setNome("Miguel");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            veterinarioService.update(1L, veterinario_update);
        });
    }

    @Test
    @DisplayName("Unitário - Falha ao atualizar veterinário com email duplicado")
    void updateError5() {
        Mockito.when(veterinarioRepository.findById(1L)).thenReturn(Optional.of(veterinario));
        Mockito.when(veterinarioRepository.existsByEmail(any())).thenReturn(true);

        Veterinario veterinario_update = new Veterinario();
        veterinario_update.setNome("Miguel");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            veterinarioService.update(1L, veterinario_update);
        });
    }

    @Test
    @DisplayName("Unitário - Buscar veterinário por ID com sucesso")
    void findById() {
        Mockito.when(veterinarioRepository.findById(1L)).thenReturn(Optional.of(veterinario));
        var response = veterinarioService.findById(1L);
        Assertions.assertEquals("Marcos", response.getNome());
    }

    @Test
    @DisplayName("Unitário - Falha ao buscar veterinário por ID inexistente")
    void findByIdError() {
        Mockito.when(veterinarioRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            veterinarioService.findById(1L);
        });
    }

    @Test
    @DisplayName("Unitário - Excluir veterinário com sucesso")
    void delete() {
        Mockito.when(veterinarioRepository.existsById(1L)).thenReturn(true);
        var mensagem = veterinarioService.delete(1L);
        Assertions.assertEquals("Veterinario com id " + 1 + " foi excluído com sucesso.", mensagem);
    }

    @Test
    @DisplayName("Unitário - Falha ao excluir veterinário inexistente")
    void deleteError() {
        Mockito.when(veterinarioRepository.existsById(1L)).thenReturn(false);
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            veterinarioService.delete(1L);
        });
    }

    @Test
    @DisplayName("Unitário - Buscar veterinário por CRMV com sucesso")
    void findByCRMV() {
        Mockito.when(veterinarioRepository.findByCRMV(any())).thenReturn(Optional.of(veterinario));
        var response = veterinarioService.findByCRMV(veterinario.getCRMV());
        Assertions.assertEquals(veterinario, response);
    }

    @Test
    @DisplayName("Unitário - Falha ao buscar veterinário por CRMV inexistente")
    void findByCRMVError() {
        Mockito.when(veterinarioRepository.findByCRMV(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            veterinarioService.findByCRMV(veterinario.getCRMV());
        });
    }

    @Test
    @DisplayName("Unitário - Buscar veterinário por nome com sucesso")
    void findByNome() {
        Mockito.when(veterinarioRepository.findByNomeContainingIgnoreCase(any())).thenReturn(Optional.of(veterinarios));
        var response = veterinarioService.findByNome(veterinario.getNome());
        Assertions.assertEquals(veterinarios, response);
    }

    @Test
    @DisplayName("Unitário - Falha ao buscar veterinário por nome inexistente")
    void findByNomeError() {
        Mockito.when(veterinarioRepository.findByNomeContainingIgnoreCase(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            veterinarioService.findByNome(veterinario.getNome());
        });
    }

    @Test
    @DisplayName("Unitário - Buscar veterinário por CPF com sucesso")
    void findByCPF() {
        Mockito.when(veterinarioRepository.findByCpf(any())).thenReturn(Optional.of(veterinario));
        var response = veterinarioService.findByCPF(veterinario.getCpf());
        Assertions.assertEquals(veterinario, response);
    }

    @Test
    @DisplayName("Unitário - Falha ao buscar veterinário por CPF inexistente")
    void findByCpfError() {
        Mockito.when(veterinarioRepository.findByCpf(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            veterinarioService.findByCPF(veterinario.getCpf());
        });
    }

    @Test
    @DisplayName("Unitário - Buscar veterinário por email com sucesso")
    void findByEmail() {
        Mockito.when(veterinarioRepository.findByEmail(any())).thenReturn(Optional.of(veterinario));
        var response = veterinarioService.findByEmail(veterinario.getEmail());
        Assertions.assertEquals(veterinario, response);
    }

    @Test
    @DisplayName("Unitário - Falha ao buscar veterinário por email inexistente")
    void findByEmailError() {
        Mockito.when(veterinarioRepository.findByEmail(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            veterinarioService.findByEmail(veterinario.getEmail());
        });
    }
}
