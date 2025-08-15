package com.uni.PrefPet.repository;

import com.uni.PrefPet.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long> {
}
