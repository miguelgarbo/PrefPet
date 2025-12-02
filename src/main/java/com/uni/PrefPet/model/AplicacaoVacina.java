package com.uni.PrefPet.model;

import com.uni.PrefPet.model.Usuarios.Veterinario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class AplicacaoVacina {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Integer getNumeroDose() {
        return numeroDose;
    }

    public void setNumeroDose(Integer numeroDose) {
        this.numeroDose = numeroDose;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotNull(message = "Data de aplicação não deve ser nula")
    private LocalDate dataAplicacao = LocalDate.now();

    private LocalDate dataValidade;
    //tirando lote, futuramente criar uma classe de tipo de vacina, e LoteVacina pra atrela
    //  @ NotBlank(message = "Lote é um campo obrigatório")
    //  private String lote;

    private Integer numeroDose;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "vacina_id")
    private Vacina vacina;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    private Veterinario veterinario;
}
