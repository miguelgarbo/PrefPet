package com.uni.PrefPet.model.Usuarios;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uni.PrefPet.model.Animal;
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
    @JsonManagedReference
    private List<Animal> animais;

}
