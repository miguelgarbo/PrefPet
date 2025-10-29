package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Enum.TipoEntidade;
import com.uni.PrefPet.model.Usuarios.Entidade;
import com.uni.PrefPet.repository.EntidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntidadeServiceTest {

    @Mock
    private EntidadeRepository entidadeRepository;

    @InjectMocks
    private EntidadeService entidadeService;

    private Entidade entidade;

    @BeforeEach
    void setUp() {
        entidade = new Entidade();
        entidade.setId(1L);
        entidade.setNome("Prefeitura de Foz");
        entidade.setCnpj("1234567890001");
        entidade.setCpf("12345678900");
        entidade.setTelefone("45999998888");
        entidade.setEmail("prefeitura@foz.com");
        entidade.setCidade("Foz do Iguaçu");
        entidade.setEstado("PR");
        entidade.setCep("85851100");
        entidade.setTipoEntidade(TipoEntidade.PREFEITURA);
    }

    @Test
    @DisplayName("save deve salvar entidade quando não há duplicatas")
    void saveSucesso() {
        when(entidadeRepository.existsByCnpj(anyString())).thenReturn(false);
        when(entidadeRepository.existsByCpf(anyString())).thenReturn(false);
        when(entidadeRepository.existsByTelefone(anyString())).thenReturn(false);
        when(entidadeRepository.existsByEmail(anyString())).thenReturn(false);
        when(entidadeRepository.save(entidade)).thenReturn(entidade);

        Entidade resultado = entidadeService.save(entidade);

        assertNotNull(resultado);
        assertEquals("Prefeitura de Foz", resultado.getNome());
        verify(entidadeRepository, times(1)).save(entidade);
    }

    @Test
    @DisplayName("save lança exceção se CNPJ já existir")
    void saveFalhaCnpjDuplicado() {
        when(entidadeRepository.existsByCnpj(entidade.getCnpj())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> entidadeService.save(entidade));
        verify(entidadeRepository, never()).save(any());
    }

    @Test
    @DisplayName("save lança exceção se telefone já existir")
    void saveFalhaTelefoneDuplicado() {
        when(entidadeRepository.existsByCnpj(anyString())).thenReturn(false);
        when(entidadeRepository.existsByCpf(anyString())).thenReturn(false);
        when(entidadeRepository.existsByTelefone("45999998888")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> entidadeService.save(entidade));
        verify(entidadeRepository, never()).save(any());
    }

    @Test
    @DisplayName("save lança exceção se email já existir")
    void saveFalhaEmailDuplicado() {
        when(entidadeRepository.existsByCnpj(anyString())).thenReturn(false);
        when(entidadeRepository.existsByCpf(anyString())).thenReturn(false);
        when(entidadeRepository.existsByTelefone(anyString())).thenReturn(false);
        when(entidadeRepository.existsByEmail("prefeitura@foz.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> entidadeService.save(entidade));
        verify(entidadeRepository, never()).save(any());
    }

    @Test
    @DisplayName("save lança exceção se CPF já existir")
    void saveFalhaCpfDuplicado() {
        when(entidadeRepository.existsByCnpj(anyString())).thenReturn(false);
        when(entidadeRepository.existsByCpf("12345678900")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> entidadeService.save(entidade));
        verify(entidadeRepository, never()).save(any());
    }

    @Test
    @DisplayName("update atualiza entidade existente com sucesso")
    void updateSucesso() {
        when(entidadeRepository.findById(1L)).thenReturn(Optional.of(entidade));
        when(entidadeRepository.existsByCpf(anyString())).thenReturn(false);
        when(entidadeRepository.existsByTelefone(anyString())).thenReturn(false);
        when(entidadeRepository.existsByCnpj(anyString())).thenReturn(false);
        when(entidadeRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Entidade atualizada = new Entidade();
        atualizada.setNome("Universidade Estadual de Foz");
        atualizada.setCpf("98765432100");
        atualizada.setTelefone("45911112222");
        atualizada.setCnpj("987654320001");
        atualizada.setTipoEntidade(TipoEntidade.UNIVERSIDADE);

        Entidade resultado = entidadeService.update(1L, atualizada);

        assertAll(
                () -> assertEquals("Universidade Estadual de Foz", resultado.getNome()),
                () -> assertEquals("98765432100", resultado.getCpf()),
                () -> assertEquals("45911112222", resultado.getTelefone()),
                () -> assertEquals(TipoEntidade.UNIVERSIDADE, resultado.getTipoEntidade())
        );

        verify(entidadeRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("update lança exceção se entidade não existir")
    void updateFalhaIdInexistente() {
        when(entidadeRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> entidadeService.update(999L, entidade));
    }

    @Test
    @DisplayName("update lança exceção se telefone já existir")
    void updateFalhaTelefoneDuplicado() {
        when(entidadeRepository.findById(1L)).thenReturn(Optional.of(entidade));
        when(entidadeRepository.existsByTelefone("45911112222")).thenReturn(true);

        Entidade atualizado = new Entidade();
        atualizado.setTelefone("45911112222");

        assertThrows(IllegalArgumentException.class,
                () -> entidadeService.update(1L, atualizado));

        verify(entidadeRepository, never()).save(any());
    }

    @Test
    @DisplayName("update lança exceção se o CNPJ já existir")
    void updateFalhaCnpjDuplicado() {
        when(entidadeRepository.findById(1L)).thenReturn(Optional.of(entidade));
        when(entidadeRepository.existsByCnpj("987654320001")).thenReturn(true);

        Entidade atualizado = new Entidade();
        atualizado.setCnpj("987654320001");

        assertThrows(IllegalArgumentException.class,
                () -> entidadeService.update(1L, atualizado));

        verify(entidadeRepository, never()).save(any());
    }

    @Test
    @DisplayName("update lança exceção se CPF já existir")
    void updateFalhaCpfDuplicado() {
        when(entidadeRepository.findById(1L)).thenReturn(Optional.of(entidade));
        when(entidadeRepository.existsByCpf("98765432100")).thenReturn(true);

        Entidade atualizado = new Entidade();
        atualizado.setCpf("98765432100");

        assertThrows(IllegalArgumentException.class,
                () -> entidadeService.update(1L, atualizado));

        verify(entidadeRepository, never()).save(any());
    }

    @Test
    @DisplayName("findById retorna entidade existente")
    void findByIdSucesso() {
        when(entidadeRepository.findById(1L)).thenReturn(Optional.of(entidade));
        Entidade resultado = entidadeService.findById(1L);
        assertEquals("Prefeitura de Foz", resultado.getNome());
    }

    @Test
    @DisplayName("findById lança exceção se não encontrar")
    void findByIdErro() {
        when(entidadeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> entidadeService.findById(1L));
    }

    @Test
    @DisplayName("delete exclui entidade existente")
    void deleteSucesso() {
        when(entidadeRepository.existsById(1L)).thenReturn(true);
        String resposta = entidadeService.delete(1L);
        assertEquals("Usuário com id 1 foi excluído com sucesso.", resposta);
        verify(entidadeRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete lança exceção se entidade não existir")
    void deleteFalha() {
        when(entidadeRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> entidadeService.delete(1L));
        verify(entidadeRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("findByCnpj retorna entidade com sucesso")
    void findByCnpjSucesso() {
        when(entidadeRepository.findByCnpj("1234567890001")).thenReturn(Optional.of(entidade));
        Entidade resultado = entidadeService.findByCnpj("1234567890001");
        assertEquals("Prefeitura de Foz", resultado.getNome());
    }

    @Test
    @DisplayName("findByCnpj lança exceção quando não encontra")
    void findByCnpjErro() {
        when(entidadeRepository.findByCnpj("0000000000000")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> entidadeService.findByCnpj("0000000000000"));
    }

    @Test
    @DisplayName("findByTipoEntidade retorna lista com sucesso")
    void findByTipoEntidadeSucesso() {
        when(entidadeRepository.findByTipoEntidade(TipoEntidade.PREFEITURA))
                .thenReturn(Optional.of(List.of(entidade)));

        List<Entidade> resultado = entidadeService.findByTipoEntidade(TipoEntidade.PREFEITURA);
        assertEquals(1, resultado.size());
        verify(entidadeRepository, times(1)).findByTipoEntidade(TipoEntidade.PREFEITURA);
    }

    @Test
    @DisplayName("findByTipoEntidade lança exceção quando não encontra")
    void findByTipoEntidadeErro() {
        when(entidadeRepository.findByTipoEntidade(TipoEntidade.ONG))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> entidadeService.findByTipoEntidade(TipoEntidade.ONG));
    }

    @Test
    @DisplayName("existsByCPF retorna verdadeiro quando CPF existe")
    void existsByCpfSucesso() {
        when(entidadeRepository.existsByCpf("12345678900")).thenReturn(true);

        boolean resultado = entidadeService.existsByCPF("12345678900");

        assertTrue(resultado);
        verify(entidadeRepository, times(1)).existsByCpf("12345678900");
    }

    @Test
    @DisplayName("existsByCPF retorna falso quando CPF não existe")
    void existsByCpfErro() {
        when(entidadeRepository.existsByCpf("12345678900")).thenReturn(false);

        boolean resultado = entidadeService.existsByCPF("12345678900");

        assertFalse(resultado);
        verify(entidadeRepository, times(1)).existsByCpf("12345678900");
    }

    @Test
    @DisplayName("existsByEmail retorna verdadeiro quando email existe")
    void existsByEmailSucesso() {
        when(entidadeRepository.existsByEmail("prefeitura@foz.com")).thenReturn(true);

        boolean resultado = entidadeService.existsByEmail("prefeitura@foz.com");

        assertTrue(resultado);
        verify(entidadeRepository, times(1)).existsByEmail("prefeitura@foz.com");
    }

    @Test
    @DisplayName("existsByEmail retorna falso quando email não existe")
    void existsByEmailErro() {
        when(entidadeRepository.existsByEmail("prefeitura@foz.com")).thenReturn(false);

        boolean resultado = entidadeService.existsByEmail("prefeitura@foz.com");

        assertFalse(resultado);
        verify(entidadeRepository, times(1)).existsByEmail("prefeitura@foz.com");
    }

    @Test
    @DisplayName("existsByTelefone retorna verdadeiro quando telefone existe")
    void existsByTelefoneSucesso() {
        when(entidadeRepository.existsByTelefone("45999998888")).thenReturn(true);

        boolean resultado = entidadeService.existsByTelefone("45999998888");

        assertTrue(resultado);
        verify(entidadeRepository, times(1)).existsByTelefone("45999998888");
    }

    @Test
    @DisplayName("existsByTelefone retorna falso quando telefone não existe")
    void existsByTelefoneErro() {
        when(entidadeRepository.existsByTelefone("45999998888")).thenReturn(false);

        boolean resultado = entidadeService.existsByTelefone("45999998888");

        assertFalse(resultado);
        verify(entidadeRepository, times(1)).existsByTelefone("45999998888");
    }

    @Test
    @DisplayName("findByNome retorna lista de entidades quando encontra resultados")
    void findByNomeSucesso() {
        when(entidadeRepository.findByNomeContainingIgnoreCase("Prefeitura"))
                .thenReturn(Optional.of(List.of(entidade)));

        List<Entidade> resultado = entidadeService.findByNome("Prefeitura");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Prefeitura de Foz", resultado.get(0).getNome());
        verify(entidadeRepository, times(1)).findByNomeContainingIgnoreCase("Prefeitura");
    }

    @Test
    @DisplayName("findByNome lança exceção quando não encontra resultados")
    void findByNomeErro() {
        when(entidadeRepository.findByNomeContainingIgnoreCase("Inexistente"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> entidadeService.findByNome("Inexistente"));

        verify(entidadeRepository, times(1)).findByNomeContainingIgnoreCase("Inexistente");
    }

    @Test
    @DisplayName("findByCPF retorna lista de entidades quando encontra resultados")
    void findByCpfSucesso() {
        when(entidadeRepository.findByCpf("12345678900"))
                .thenReturn(Optional.of((entidade)));

        var resultado = entidadeService.findByCPF("12345678900");

        assertNotNull(resultado);
        assertEquals("12345678900", resultado.getCpf());
        verify(entidadeRepository, times(1)).findByCpf("12345678900");
    }

    @Test
    @DisplayName("findByCPF lança exceção quando não encontra resultados")
    void findByCpfErro() {
        when(entidadeRepository.findByCpf("00000000000"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> entidadeService.findByCPF("00000000000"));

        verify(entidadeRepository, times(1)).findByCpf("00000000000");
    }

    @Test
    @DisplayName("findByTelefone retorna lista de entidades quando encontra resultados")
    void findByTelefoneSucesso() {
        when(entidadeRepository.findByTelefone("45999998888"))
                .thenReturn(Optional.of(List.of(entidade)));

        List<Entidade> resultado = entidadeService.findByTelefone("45999998888");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("45999998888", resultado.get(0).getTelefone());
        verify(entidadeRepository, times(1)).findByTelefone("45999998888");
    }

    @Test
    @DisplayName("findByTelefone lança exceção quando não encontra resultados")
    void findByTelefoneErro() {
        when(entidadeRepository.findByTelefone("00000000000"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> entidadeService.findByTelefone("00000000000"));

        verify(entidadeRepository, times(1)).findByTelefone("00000000000");
    }

    @Test
    @DisplayName("findByEmail retorna lista de entidades quando encontra resultados")
    void findByEmailSucesso() {
        when(entidadeRepository.findByEmail("prefeitura@foz.com"))
                .thenReturn(Optional.of((entidade)));

        Entidade resultado = entidadeService.findByEmail("prefeitura@foz.com");

        assertNotNull(resultado);
        assertEquals("prefeitura@foz.com", resultado.getEmail());
        verify(entidadeRepository, times(1)).findByEmail("prefeitura@foz.com");
    }

    @Test
    @DisplayName("findByEmail lança exceção quando não encontra resultados")
    void findByEmailErro() {
        when(entidadeRepository.findByEmail("naoexiste@foz.com"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> entidadeService.findByEmail("naoexiste@foz.com"));

        verify(entidadeRepository, times(1)).findByEmail("naoexiste@foz.com");
    }

    @Test
    @DisplayName("findAll retorna lista de entidades")
    void findAllSucesso() {
        Entidade outraEntidade = new Entidade();
        outraEntidade.setId(2L);
        outraEntidade.setNome("Universidade Estadual de Foz");
        outraEntidade.setCpf("98765432100");
        outraEntidade.setCnpj("987654320001");
        outraEntidade.setTelefone("45911112222");
        outraEntidade.setEmail("universidade@foz.com");
        outraEntidade.setCidade("Foz do Iguaçu");
        outraEntidade.setEstado("PR");
        outraEntidade.setCep("85851101");
        outraEntidade.setTipoEntidade(TipoEntidade.UNIVERSIDADE);

        when(entidadeRepository.findAll()).thenReturn(List.of(entidade, outraEntidade));

        List<Entidade> resultado = entidadeService.findAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(e -> e.getNome().equals("Prefeitura de Foz")));
        assertTrue(resultado.stream().anyMatch(e -> e.getNome().equals("Universidade Estadual de Foz")));

        verify(entidadeRepository, times(1)).findAll();
    }
}
