package com.uni.PrefPet.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uni.PrefPet.model.Usuarios.Tutor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Column(unique = true, nullable = false)
    private String registroGeral;

    private String especie;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @NotNull(message = "Esse campo deve haver algo")
    private Boolean castrado;

    @NotBlank(message = "O Campo cor não deve ser nulo")
    private String cor;

    @NotBlank(message = "O Campo sexo não deve ser nulo")
    private String sexo;

    private Boolean microchip;

    @Column(unique = true)
    private String numeroMicrochip;

    @Past(message = "A data de nascimento deve estar no passado")
    @NotNull(message = "Data de nascimento nao deve se null")
    private LocalDate dataNascimento;

    @NotBlank(message = "O Campo de naturalidade não deve ser nulo")
    private String naturalidade;

    private String imagemUrl;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Notificacao> notificacoes = new ArrayList<>();


    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AplicacaoVacina> aplicacoes =new ArrayList<>();;

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

    public String gerarRg(){

        Random random = new Random();
        int numeroRgAleatorio = random.nextInt(10);
        return "RG"+ numeroRgAleatorio;
    }

    @PrePersist
    public void prePersist(){
        if(registroGeral == null){
            registroGeral = gerarRg();
        }
    }

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

    public String getRegistroGeral() {
        return registroGeral;
    }

    public void setRegistroGeral(String registroGeral) {
        this.registroGeral = registroGeral;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Boolean getCastrado() {
        return castrado;
    }

    public void setCastrado(Boolean castrado) {
        this.castrado = castrado;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Boolean getMicrochip() {
        return microchip;
    }

    public void setMicrochip(Boolean microchip) {
        this.microchip = microchip;
    }

    public String getNumeroMicrochip() {
        return numeroMicrochip;
    }

    public void setNumeroMicrochip(String numeroMicrochip) {
        this.numeroMicrochip = numeroMicrochip;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public List<AplicacaoVacina> getAplicacoes() {
        return aplicacoes;
    }

    public void setAplicacoes(List<AplicacaoVacina> aplicacoes) {
        this.aplicacoes = aplicacoes;
    }
}



