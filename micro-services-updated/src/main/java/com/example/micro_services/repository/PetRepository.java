package com.example.micro_services.repository;

import com.example.micro_services.model.Pet;
import com.example.micro_services.model.PetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByStatusIn(List<PetStatus> status);
}
