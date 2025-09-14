package com.uni.PrefPet.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDate;
import java.util.List;
@Data
@Entity
public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotBlank(message = "nome é um campo obrigatório")
    private String nome;

    @OneToMany(mappedBy = "vacina", cascade = CascadeType.ALL)
    private List<AplicacaoVacina> aplicacoes;

}
