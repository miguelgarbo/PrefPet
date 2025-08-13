package com.uni.PrefPet.model;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Instituicao {

    private Long id;
    private String nome;
    private String descricao;

}
