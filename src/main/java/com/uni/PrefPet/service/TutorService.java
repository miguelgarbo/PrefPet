package com.uni.PrefPet.service;

import com.uni.PrefPet.model.Tutor;
import com.uni.PrefPet.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TutorService {
    private final TutorRepository tutorRepository;

    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    public List<Tutor> listAll(){
        return tutorRepository.findAll();
    }

    public Tutor save(Tutor tutor){
        if (tutorRepository.findByNome(tutor.getNome()).isPresent()) {
            throw new IllegalArgumentException("Já existe um tutor com este nome.");
        }

        if (tutorRepository.findByCPF(tutor.getCPF()).isPresent()) {
            throw new IllegalArgumentException("Já existe um tutor com este CPF.");
        }

        if (tutorRepository.findByTelefone(tutor.getTelefone()).isPresent()) {
            throw new IllegalArgumentException("Já existe um tutor com este telefone.");
        }
        return tutorRepository.save(tutor);
    }

    public Tutor findById(Long id){
        return tutorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Tutor update(Long id, Tutor tutorAtualizado){
        Tutor existente = tutorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tutor com id " + id + " não encontrado."));
        if (!existente.getNome().equalsIgnoreCase(tutorAtualizado.getNome()) &&
                tutorRepository.findByNome(tutorAtualizado.getNome()).isPresent()) {
            throw new IllegalArgumentException("Já existe um tutor com este nome.");
        }
        if (!existente.getCPF().equals(tutorAtualizado.getCPF()) &&
                tutorRepository.findByCPF(tutorAtualizado.getCPF()).isPresent()) {
            throw new IllegalArgumentException("Já existe um tutor com este CPF.");
        }
        if (!existente.getTelefone().equals(tutorAtualizado.getTelefone()) &&
                tutorRepository.findByTelefone(tutorAtualizado.getTelefone()).isPresent()) {
            throw new IllegalArgumentException("Já existe um tutor com este telefone.");
        }existente.setNome(tutorAtualizado.getNome());
        existente.setCPF(tutorAtualizado.getCPF());
        existente.setTelefone(tutorAtualizado.getTelefone());
        existente.setAnimais(tutorAtualizado.getAnimais());
        return tutorRepository.save(existente);
    }

}

