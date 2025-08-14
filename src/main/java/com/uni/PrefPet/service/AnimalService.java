package com.uni.PrefPet.service;
import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.repository.AnimalRepository;
import com.uni.PrefPet.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class AnimalService  {
    private final AnimalRepository animalRepository;
    private final UsuarioRepository tutorRepository;

    public AnimalService(AnimalRepository animalRepository, UsuarioRepository tutorRepository) {
        this.animalRepository = animalRepository;
        this.tutorRepository = tutorRepository;
    }

    public Animal findById(Long id){
        return animalRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Animal> listAll(){
        return animalRepository.findAll();
    }


    public Animal save(Animal animal) {
        if (animal.getNome() == null || animal.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do animal é obrigatório.");
        }

        if (animal.getEspecie() == null || animal.getEspecie().trim().isEmpty()) {
            throw new IllegalArgumentException("A espécie do animal é obrigatória.");
        }

        if (animal.getSexo() == null || animal.getSexo().trim().isEmpty()) {
            throw new IllegalArgumentException("O sexo do animal é obrigatório.");
        }
        String sexo = animal.getSexo().toLowerCase();
        if (!sexo.equals("macho") && !sexo.equals("fêmea") && !sexo.equals("m") && !sexo.equals("f")) {
            throw new IllegalArgumentException("Sexo inválido. Use 'Macho', 'Fêmea', 'M' ou 'F'.");
        }

        if (animal.getRegistroGeral() != null && !animal.getRegistroGeral().trim().isEmpty()) {
            boolean rgExiste = animalRepository.existsByRegistroGeral(animal.getRegistroGeral().trim());
            if (rgExiste) {
                throw new IllegalArgumentException("Já existe um animal com o Registro Geral informado.");
            }
        }

        if (animal.getDataNascimento() != null && animal.getDataNascimento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de nascimento não pode ser no futuro.");
        }

        if (animal.getUsuario() == null || animal.getUsuario().getId() == null) {
            throw new IllegalArgumentException("O animal deve estar vinculado a um tutor.");
        }
        boolean tutorExiste = tutorRepository.existsById(animal.getUsuario().getId());
        if (!tutorExiste) {
            throw new EntityNotFoundException("Tutor com ID " + animal.getUsuario().getId() + " não encontrado.");
        }

        return animalRepository.save(animal);
    }
    public Animal update(Long id, Animal animalAtualizado) {
        Animal animalExistente = findById(id);
        if (animalAtualizado.getNome() == null || animalAtualizado.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do animal é obrigatório.");
        }
        if (animalAtualizado.getEspecie() == null || animalAtualizado.getEspecie().trim().isEmpty()) {
            throw new IllegalArgumentException("A espécie do animal é obrigatória.");
        }
        if (animalAtualizado.getSexo() == null || animalAtualizado.getSexo().trim().isEmpty()) {
            throw new IllegalArgumentException("O sexo do animal é obrigatório.");
        }
        String sexo = animalAtualizado.getSexo().toLowerCase();
        if (!sexo.equals("macho") && !sexo.equals("fêmea") && !sexo.equals("m") && !sexo.equals("f")) {
            throw new IllegalArgumentException("Sexo inválido. Use 'Macho', 'Fêmea', 'M' ou 'F'.");
        }
        if (animalAtualizado.getRegistroGeral() != null && !animalAtualizado.getRegistroGeral().trim().isEmpty()) {
            boolean rgExiste = animalRepository.existsByRegistroGeralSemId(
                    animalAtualizado.getRegistroGeral().trim(),
                    id);
            if (rgExiste) {
                throw new IllegalArgumentException("Já existe outro animal com este Registro Geral.");
            }
        }
        if (animalAtualizado.getDataNascimento() != null && animalAtualizado.getDataNascimento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de nascimento não pode ser no futuro.");
        }
        if (animalAtualizado.getUsuario() == null || animalAtualizado.getUsuario().getId() == null) {
            throw new IllegalArgumentException("O animal deve estar vinculado a um tutor.");
        }
        boolean tutorExiste = tutorRepository.existsById(animalAtualizado.getUsuario().getId());
        if (!tutorExiste) {
            throw new EntityNotFoundException("Tutor com ID " + animalAtualizado.getUsuario().getId() + " não encontrado.");
        }
        animalExistente.setNome(animalAtualizado.getNome());
        animalExistente.setEspecie(animalAtualizado.getEspecie());
        animalExistente.setSexo(animalAtualizado.getSexo());
        animalExistente.setRegistroGeral(animalAtualizado.getRegistroGeral());
        animalExistente.setCastrado(animalAtualizado.getCastrado());
        animalExistente.setCor(animalAtualizado.getCor());
        animalExistente.setMicrochip(animalAtualizado.getMicrochip());
        animalExistente.setDataNascimento(animalAtualizado.getDataNascimento());
        animalExistente.setNaturalidade(animalAtualizado.getNaturalidade());
        animalExistente.setUsuario(animalAtualizado.getUsuario());
        return animalRepository.save(animalExistente);
    }

    public String delete(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new EntityNotFoundException("Animal com id " + id + " não encontrado.");
        }

        animalRepository.deleteById(id);
        return "Animal com id " + id + " foi excluído com sucesso.";
    }


}
