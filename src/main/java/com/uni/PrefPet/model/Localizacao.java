package com.uni.PrefPet.model;
import jakarta.persistence.Embeddable;

@Embeddable
public class Localizacao {

    private String endereco;
    private String cidade;
    private String estado;
    private String cep;
    private Double latitude;
    private Double longitude;

}
