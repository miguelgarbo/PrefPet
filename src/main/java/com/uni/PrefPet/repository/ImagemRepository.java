package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {
}
