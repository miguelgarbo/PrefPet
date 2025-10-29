package com.uni.PrefPet.service;
import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.repository.AnimalRepository;
import com.uni.PrefPet.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class AnimalService  {

    @Autowired
    private AnimalRepository animalRepository;
    private TutorRepository usuarioRepository;

    public Animal findById(Long id){
        return animalRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Animal> findAll(){
        return animalRepository.findAll();
    }

    public Animal save(Animal animal) {return animalRepository.save(animal);}

    public Animal update(Long id, Animal animalAtualizado) {
        Animal animalExistente = animalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado"));

        if (animalAtualizado.getNome() != null && !animalAtualizado.getNome().trim().isEmpty()) {
            animalExistente.setNome(animalAtualizado.getNome());
        }

        if (animalAtualizado.getEspecie() != null && !animalAtualizado.getEspecie().trim().isEmpty()) {
            animalExistente.setEspecie(animalAtualizado.getEspecie());
        }

        if (animalAtualizado.getSexo() != null && !animalAtualizado.getSexo().trim().isEmpty()) {
            String sexo = animalAtualizado.getSexo().toLowerCase();
            if (!sexo.equals("macho") && !sexo.equals("fêmea") && !sexo.equals("m") && !sexo.equals("f")) {
                throw new IllegalArgumentException("Sexo inválido. Use 'Macho', 'Fêmea', 'M' ou 'F'.");
            }
            animalExistente.setSexo(animalAtualizado.getSexo());
        }

        if (animalAtualizado.getRegistroGeral() != null && !animalAtualizado.getRegistroGeral().trim().isEmpty()) {
            boolean rgExiste = animalRepository.existsByRegistroGeral(animalAtualizado.getRegistroGeral().trim());
            if (rgExiste && !animalAtualizado.getRegistroGeral().equals(animalExistente.getRegistroGeral())) {
                throw new IllegalArgumentException("Já existe outro animal com este Registro Geral.");
            }
            animalExistente.setRegistroGeral(animalAtualizado.getRegistroGeral().trim());
        }

        if (animalAtualizado.getCastrado() != null) {
            animalExistente.setCastrado(animalAtualizado.getCastrado());
        }

        if (animalAtualizado.getCor() != null) {
            animalExistente.setCor(animalAtualizado.getCor());
        }

        if (animalAtualizado.getMicrochip() != null) {
            animalExistente.setMicrochip(animalAtualizado.getMicrochip());
            animalExistente.setNumeroMicrochip(animalAtualizado.getNumeroMicrochip());
        }

        if (animalAtualizado.getDataNascimento() != null) {
            if (animalAtualizado.getDataNascimento().isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("A data de nascimento não pode ser no futuro.");
            }
            animalExistente.setDataNascimento(animalAtualizado.getDataNascimento());
        }

        if (animalAtualizado.getNaturalidade() != null) {
            animalExistente.setNaturalidade(animalAtualizado.getNaturalidade());
        }

        if (animalAtualizado.getTutor() != null && animalAtualizado.getTutor().getId() != null) {
            boolean tutorExiste = usuarioRepository.existsById(animalAtualizado.getTutor().getId());
            if (!tutorExiste) {
                throw new EntityNotFoundException("Tutor com ID " + animalAtualizado.getTutor().getId() + " não encontrado.");
            }
            animalExistente.setTutor(animalAtualizado.getTutor());
        }

        return animalRepository.save(animalExistente);
    }

    public String delete(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new EntityNotFoundException("Animal com id " + id + " não encontrado.");
        }

        animalRepository.deleteById(id);
        return "Animal com id " + id + " foi excluído com sucesso.";
    }
    // servicos especificos

//    public List<Animal> findAnimaisIdadeAcima(int idade) {
//        LocalDate limite = LocalDate.now().minusYears(idade);
//
//        return animalRepository.findAnimaisIdadeAcima(limite)
//                .orElseThrow(() -> new EntityNotFoundException("Nenhum animal com a espécie informada foi encontrado"));
//    }

    public List<Animal> findAnimaisIdadeAcima(int idade) {
        LocalDate limite = LocalDate.now().minusYears(idade);

        List<Animal> animais = animalRepository.findAnimaisIdadeAcima(limite);

        if (animais.isEmpty()) {
            throw new EntityNotFoundException("Nenhum animal encontrado com idade acima de " + idade + " anos");
        }

        return animais;
    }


    public List<Animal> findAnimaisEntreIdade(int idadeMax, int idadeMin) {
        LocalDate anoMax = LocalDate.now().minusYears(idadeMax);
        LocalDate anoMin = LocalDate.now().minusYears(idadeMin);

        List<Animal> animais = animalRepository.findAnimaisIdadeEntre(anoMax, anoMin);

        if (animais.isEmpty()) {
            throw new EntityNotFoundException("Nenhum animal encontrado entre as idades informadas");
        }

        return animais;
    }

    public Animal findByNome(String nome){
        return animalRepository.findByNomeContainingIgnoreCase(nome).orElseThrow(()->
                 new EntityNotFoundException("Esse Animal Não Foi Encontrado"));
    }
    public List<Animal> findByEspecieNome(String especie) {
        return animalRepository.findByEspecieIgnoreCase(especie)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum animal com a espécie informada foi encontrado"));
    }

    public List<Animal> findByCor(String cor) {
        return animalRepository.findByCorIgnoreCase(cor)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum animal com a cor informada foi encontrado"));
    }

    public List<Animal> findBySexo(String sexo) {
        return animalRepository.findBySexoIgnoreCase(sexo)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum animal com o sexo informado foi encontrado"));
    }

    public List<Animal> findByCastrado(Boolean castrado) {
        return animalRepository.findByCastrado(castrado)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum animal com o status de castrado informado foi encontrado"));
    }

    public Animal findByMicrochip(String numeroMicrochip) {
        return animalRepository.findByNumeroMicrochip(numeroMicrochip)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum animal com o microchip informado foi encontrado"));
    }

    public List<Animal> findByTutorId(Long tutor_id) {
        return animalRepository.findByTutorId(tutor_id)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum animal encontrado para o usuário informado"));
    }

    public List<Animal> findByDataNascimento(LocalDate dataNascimento) {
        return animalRepository.findByDataNascimento(dataNascimento)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum animal encontrado com a data de nascimento informada"));
    }

    public List<Animal> findByRegistroGeral(String registroGeral) {
        return animalRepository.findByRegistroGeral(registroGeral)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum animal encontrado com o registro geral informado"));
    }


    //fim serviços especificios


}
