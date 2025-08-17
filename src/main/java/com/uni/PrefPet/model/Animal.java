package com.uni.PrefPet.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Data
@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    private String registroGeral;

    @NotBlank(message = "A espécie é obrigatória")


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "especie_id")
    private Especie especie;

    private Boolean castrado;

    private String cor;

    private String sexo;

    private Boolean microchip;

    private LocalDate dataNascimento;

    private String naturalidade;

    private String imagemUrl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
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

    @Transient // não persiste no banco pq é um numero dinamico
    public int getIdade() {
        if (dataNascimento == null) return 0;

        int idadeFinal = 0;

        idadeFinal = LocalDate.now().getYear() - dataNascimento.getYear();

        //validation de idade mudar só no aniversario
        //se a mes atual for menor que o mes do nascimento ou o mes atual for igual ao mes do nascimento  && o dia atual for menor que o dia da data de nascimento, diminui um
        if (LocalDate.now().getMonthValue() < dataNascimento.getMonthValue() || (LocalDate.now().getMonthValue() == dataNascimento.getMonthValue() && LocalDate.now().getDayOfMonth() < dataNascimento.getDayOfMonth())) {
            idadeFinal--;
        }
        return idadeFinal;
    }
}



