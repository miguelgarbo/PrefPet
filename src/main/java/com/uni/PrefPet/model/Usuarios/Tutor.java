package com.uni.PrefPet.model.Usuarios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uni.PrefPet.model.Animal;
import com.uni.PrefPet.model.Notificacao;
import com.uni.PrefPet.model.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;
@Entity
@Data
public class Tutor extends Usuario {

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Animal> animais;

    // Notificações recebidas
    @OneToMany(mappedBy = "tutorDestinatario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notificacao> notificacoesRecebidas;

    // Notificações enviadas (opcional)
    @OneToMany(mappedBy = "tutorRemetente", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notificacao> notificacoesEnviadas;


}
