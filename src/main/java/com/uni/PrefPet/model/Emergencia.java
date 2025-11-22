package com.uni.PrefPet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


//DENUNCIA VAI SER UMA PARTE SÓ COM INFO DO QUE FAZER QUANDO QUISER DENUNCIAR
@Data
@Entity
public class Emergencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome; // MAUS_TRATOS ou ANIMAL_SILVESTRE

    @ManyToMany
    @JoinTable(
            name = "emergencia_contato",
            joinColumns = @JoinColumn(name = "emergencia_id"),
            inverseJoinColumns = @JoinColumn(name = "contato_id"))
    @JsonIgnoreProperties("emergencias")
    private List<Contato> contatos = new ArrayList<>();

    public void addContato(Contato c) {
        contatos.add(c);
        c.getEmergencias().add(this);
    }

    public void removeContato(Contato c){
        contatos.remove(c);
        c.getEmergencias().remove(this);
    }
}
