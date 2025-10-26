package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.Usuarios.Tutor;
import com.uni.PrefPet.repository.AnimalRepository;
import com.uni.PrefPet.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnimalServiceTest {

    @InjectMocks
    AnimalService animalService;

    @Mock
    AnimalRepository animalRepository;

    @Mock
    TutorRepository tutorRepository;

    Animal animal;
    Tutor tutor;

    @BeforeEach
    void setUp() {
        tutor = new Tutor();
        tutor.setId(1L);
        tutor.setNome("Miguel");
        tutor.setEmail("miguel@example.com");

        animal = new Animal();
        animal.setId(1L);
        animal.setNome("Rex");
        animal.setEspecie("Cachorro");
        animal.setTutor(tutor);
        animal.setCastrado(true);
        animal.setCor("Marrom");
        animal.setSexo("Macho");
        animal.setMicrochip(true);
        animal.setNumeroMicrochip("123456");
        animal.setDataNascimento(LocalDate.of(2020, 5, 12));
        animal.setNaturalidade("São Paulo");
        animal.setRegistroGeral("RG123");
    }

    // --------------------------- CRUD BÁSICO ---------------------------

    @Test
    @DisplayName("Salvar animal com sucesso")
    void testSalvarAnimalOK() {
        Mockito.when(animalRepository.save(animal)).thenReturn(animal);
        var result = animalService.save(animal);

        assertNotNull(result);
        assertEquals("Rex", result.getNome());
        Mockito.verify(animalRepository, Mockito.times(1)).save(animal);
    }

    @Test
    @DisplayName("Buscar animal por ID com sucesso")
    void testFindByIdOK() {
        Mockito.when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        Animal result = animalService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Buscar animal por ID inexistente deve lançar exceção")
    void testFindByIdNotFound() {
        Mockito.when(animalRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> animalService.findById(1L));
    }

    @Test
    @DisplayName("Listar todos os animais")
    void testFindAll() {
        Mockito.when(animalRepository.findAll()).thenReturn(List.of(animal));
        List<Animal> result = animalService.findAll();

        assertEquals(1, result.size());
        assertEquals("Rex", result.get(0).getNome());
    }

    @Test
    @DisplayName("Deletar animal existente com sucesso")
    void testDeleteAnimalOK() {
        Mockito.when(animalRepository.existsById(1L)).thenReturn(true);
        String msg = animalService.delete(1L);

        Mockito.verify(animalRepository, Mockito.times(1)).deleteById(1L);
        assertEquals("Animal com id 1 foi excluído com sucesso.", msg);
    }

    @Test
    @DisplayName("Deletar animal inexistente deve lançar exceção")
    void testDeleteAnimalNotFound() {
        Mockito.when(animalRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> animalService.delete(1L));
    }

    @Test
    @DisplayName("Salvar animal - erro ao salvar (exceção lançada pelo repositório)")
    void testSalvarAnimalErro() {
        Mockito.when(animalRepository.save(animal)).thenThrow(new RuntimeException("Erro ao salvar animal"));
        assertThrows(RuntimeException.class, () -> animalService.save(animal));
    }

    @Test
    @DisplayName("Listar todos - erro (repositório lança exceção)")
    void testFindAllErro() {
        Mockito.when(animalRepository.findAll()).thenThrow(new RuntimeException("Erro ao listar animais"));
        assertThrows(RuntimeException.class, () -> animalService.findAll());
    }

    @Test
    @DisplayName("Atualizar animal - sucesso")
    void testUpdateAnimalOK() {
        Animal atualizado = new Animal();
        atualizado.setNome("Bolt");
        atualizado.setSexo("Macho");
        atualizado.setRegistroGeral("RG123");

        Mockito.when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        Mockito.when(animalRepository.existsByRegistroGeral("RG123")).thenReturn(false);
        Mockito.when(animalRepository.save(Mockito.any(Animal.class))).thenReturn(animal);

        Animal result = animalService.update(1L, atualizado);

        assertEquals("Bolt", result.getNome());
        Mockito.verify(animalRepository, Mockito.times(1)).save(animal);
    }

    @Test
    @DisplayName("Atualizar animal - erro (animal não encontrado)")
    void testUpdateAnimalNotFound() {
        Mockito.when(animalRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> animalService.update(99L, animal));
    }

    @Test
    @DisplayName("Atualizar animal - erro (data de nascimento no futuro)")
    void testUpdateAnimalDataFutura() {
        Animal atualizado = new Animal();
        atualizado.setDataNascimento(LocalDate.now().plusDays(5));

        Mockito.when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        assertThrows(IllegalArgumentException.class, () -> animalService.update(1L, atualizado));
    }

    @Test
    @DisplayName("Atualizar animal - erro (registro geral duplicado)")
    void testUpdateAnimalRgDuplicado() {
        Animal atualizado = new Animal();
        atualizado.setRegistroGeral("RG999");

        Mockito.when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        Mockito.when(animalRepository.existsByRegistroGeral("RG999")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> animalService.update(1L, atualizado));
    }

    @Test
    @DisplayName("Atualizar animal - erro (sexo inválido)")
    void testUpdateAnimalSexoInvalido() {
        Animal atualizado = new Animal();
        atualizado.setSexo("Outro");

        Mockito.when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        assertThrows(IllegalArgumentException.class, () -> animalService.update(1L, atualizado));
    }

    // --------------------------- MÉTODOS ESPECÍFICOS ---------------------------

    @Test
    @DisplayName("Buscar por espécie - erro (nenhum encontrado)")
    void testFindByEspecieErro() {
        Mockito.when(animalRepository.findByEspecieIgnoreCase("Gato"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> animalService.findByEspecieNome("Gato"));
    }

    @Test
    @DisplayName("Buscar por sexo - erro (nenhum encontrado)")
    void testFindBySexoErro() {
        Mockito.when(animalRepository.findBySexoIgnoreCase("Fêmea"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> animalService.findBySexo("Fêmea"));
    }

    @Test
    @DisplayName("Buscar por castrado - erro (nenhum encontrado)")
    void testFindByCastradoErro() {
        Mockito.when(animalRepository.findByCastrado(false))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> animalService.findByCastrado(false));
    }

    @Test
    @DisplayName("Buscar por microchip - erro (não encontrado)")
    void testFindByMicrochipErro() {
        Mockito.when(animalRepository.findByNumeroMicrochip("999999"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> animalService.findByMicrochip("999999"));
    }

    @Test
    @DisplayName("Buscar por tutorId - erro (nenhum encontrado)")
    void testFindByTutorIdErro() {
        Mockito.when(animalRepository.findByTutorId(999L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> animalService.findByTutorId(999L));
    }

    @Test
    @DisplayName("Buscar por data de nascimento - erro (nenhum encontrado)")
    void testFindByDataNascimentoErro() {
        Mockito.when(animalRepository.findByDataNascimento(LocalDate.of(2010, 1, 1)))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> animalService.findByDataNascimento(LocalDate.of(2010, 1, 1)));
    }

    @Test
    @DisplayName("Buscar por registro geral - erro (nenhum encontrado)")
    void testFindByRegistroGeralErro() {
        Mockito.when(animalRepository.findByRegistroGeral("RG999"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> animalService.findByRegistroGeral("RG999"));
    }

    @Test
    @DisplayName("Buscar animais com idade acima - erro (lista vazia)")
    void testFindAnimaisIdadeAcimaErro() {
        Mockito.when(animalRepository.findAnimaisIdadeAcima(Mockito.any(LocalDate.class)))
                .thenReturn(List.of());

        assertThrows(EntityNotFoundException.class, () -> animalService.findAnimaisIdadeAcima(10));
    }

    @Test
    @DisplayName("Buscar por nome - sucesso")
    void testFindByNomeOK() {
        Mockito.when(animalRepository.findByNomeContainingIgnoreCase("rex"))
                .thenReturn(Optional.of(animal));

        Animal result = animalService.findByNome("rex");
        assertEquals("Rex", result.getNome());
    }

    @Test
    @DisplayName("Buscar por nome - erro")
    void testFindByNomeErro() {
        Mockito.when(animalRepository.findByNomeContainingIgnoreCase("rex"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> animalService.findByNome("rex"));
    }

    @Test
    @DisplayName("Buscar por espécie - sucesso")
    void testFindByEspecieOK() {
        Mockito.when(animalRepository.findByEspecieIgnoreCase("Cachorro"))
                .thenReturn(Optional.of(List.of(animal)));

        List<Animal> result = animalService.findByEspecieNome("Cachorro");
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Buscar por cor - sucesso")
    void testFindByCorOK() {
        Mockito.when(animalRepository.findByCorIgnoreCase("Marrom"))
                .thenReturn(Optional.of(List.of(animal)));

        List<Animal> result = animalService.findByCor("Marrom");
        assertEquals("Marrom", result.get(0).getCor());
    }

    @Test
    @DisplayName("Buscar por cor - erro")
    void testFindByCorErro() {
        Mockito.when(animalRepository.findByCorIgnoreCase("Azul"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> animalService.findByCor("Azul"));
    }

    @Test
    @DisplayName("Buscar por sexo - sucesso")
    void testFindBySexoOK() {
        Mockito.when(animalRepository.findBySexoIgnoreCase("Macho"))
                .thenReturn(Optional.of(List.of(animal)));

        List<Animal> result = animalService.findBySexo("Macho");
        assertEquals("Macho", result.get(0).getSexo());
    }

    @Test
    @DisplayName("Buscar por castrado - sucesso")
    void testFindByCastradoOK() {
        Mockito.when(animalRepository.findByCastrado(true))
                .thenReturn(Optional.of(List.of(animal)));

        List<Animal> result = animalService.findByCastrado(true);
        assertTrue(result.get(0).getCastrado());
    }

    @Test
    @DisplayName("Buscar por microchip - sucesso")
    void testFindByMicrochipOK() {
        Mockito.when(animalRepository.findByNumeroMicrochip("123456"))
                .thenReturn(Optional.of(animal));

        Animal result = animalService.findByMicrochip("123456");
        assertEquals("123456", result.getNumeroMicrochip());
    }

    @Test
    @DisplayName("Buscar por tutorId - sucesso")
    void testFindByTutorIdOK() {
        Mockito.when(animalRepository.findByTutorId(1L))
                .thenReturn(Optional.of(List.of(animal)));

        List<Animal> result = animalService.findByTutorId(1L);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Buscar por data de nascimento - sucesso")
    void testFindByDataNascimentoOK() {
        Mockito.when(animalRepository.findByDataNascimento(animal.getDataNascimento()))
                .thenReturn(Optional.of(List.of(animal)));

        List<Animal> result = animalService.findByDataNascimento(animal.getDataNascimento());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Buscar por registro geral - sucesso")
    void testFindByRegistroGeralOK() {
        Mockito.when(animalRepository.findByRegistroGeral("RG123"))
                .thenReturn(Optional.of(List.of(animal)));

        List<Animal> result = animalService.findByRegistroGeral("RG123");
        assertEquals("RG123", result.get(0).getRegistroGeral());
    }

    @Test
    @DisplayName("Buscar animais com idade acima - sucesso")
    void testFindAnimaisIdadeAcimaOK() {
        Mockito.when(animalRepository.findAnimaisIdadeAcima(Mockito.any(LocalDate.class)))
                .thenReturn(List.of(animal));

        List<Animal> result = animalService.findAnimaisIdadeAcima(3);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Buscar animais entre idades - sucesso")
    void testFindAnimaisEntreIdadeOK() {
        Mockito.when(animalRepository.findAnimaisIdadeEntre(Mockito.any(), Mockito.any()))
                .thenReturn(List.of(animal));

        List<Animal> result = animalService.findAnimaisEntreIdade(10, 2);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Buscar animais entre idades - erro (nenhum encontrado)")
    void testFindAnimaisEntreIdadeNotFound() {
        Mockito.when(animalRepository.findAnimaisIdadeEntre(Mockito.any(), Mockito.any()))
                .thenReturn(List.of());

        assertThrows(EntityNotFoundException.class,
                () -> animalService.findAnimaisEntreIdade(10, 2));
    }
}
