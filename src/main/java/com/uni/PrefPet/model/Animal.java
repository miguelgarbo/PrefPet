package com.uni.PrefPet.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String registroGeral;
    private String especie;
    private Boolean castrado;
    private String cor;
    private String sexo;
    private Boolean microchip;
    private LocalDate dataNascimento;
    private String naturalidade;

    @ManyToOne(cascade = CascadeType.ALL)
    private Usuario usuario;

    @OneToMany
    private List<InscricaoCampanha> inscricaoCampanhas;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "animal_vacina",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "vacina_id")
    )
    @JsonIgnoreProperties("vacinas")
    private List<Vacina> vacinas;
}



