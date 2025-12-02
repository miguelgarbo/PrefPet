package com.uni.PrefPet.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotBlank(message = "nome é um campo obrigatório")
    private String nome;

    @OneToMany(mappedBy = "vacina")
    @JsonIgnore
    private List<AplicacaoVacina> aplicacoes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<AplicacaoVacina> getAplicacoes() {
        return aplicacoes;
    }

    public void setAplicacoes(List<AplicacaoVacina> aplicacoes) {
        this.aplicacoes = aplicacoes;
    }
}
