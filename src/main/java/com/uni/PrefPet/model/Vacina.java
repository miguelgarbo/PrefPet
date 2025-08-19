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

    @NotNull(message = "Data de aplicação não deve ser nula")
    private LocalDate dataAplicacao;

    @NotBlank(message = "lote é um campo obrigatório")
    private String lote;

    private LocalDate dataValidade;

    @ToString.Exclude
    @ManyToMany(mappedBy = "vacinas")
    @JsonIgnoreProperties("vacinas")
    private List<Animal> animais;

}
