package com.uni.PrefPet.model;

import com.uni.PrefPet.model.Enum.TipoDenuncia;
import jakarta.persistence.*;
import lombok.Data;


//DENUNCIA VAI SER UMA PARTE SÓ COM INFO DO QUE FAZER QUANDO QUISER DENUNCIAR
@Data
@Entity
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoDenuncia tipo; // MAUS_TRATOS ou ANIMAL_SILVESTRE



}
