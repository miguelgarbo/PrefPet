package com.uni.PrefPet.model;

import jakarta.persistence.*;
import lombok.Data;

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
            name = "denuncia_contato",
            joinColumns = @JoinColumn(name = "denuncia_id"),
            inverseJoinColumns = @JoinColumn(name = "contato_id"))
    private List<Contato> contatos;

    public void addContato(Contato c) {
        contatos.add(c);
        c.getEmergencias().add(this);
    }
    public void removeContato(Contato c) {
        contatos.remove(c);
        c.getEmergencias().remove(this);
    }

}
