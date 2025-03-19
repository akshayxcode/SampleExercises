package com.example.micro_services.service;

import com.example.micro_services.model.ApiResponse;
import com.example.micro_services.model.ErrorResponse;
import com.example.micro_services.model.Pet;
import com.example.micro_services.model.PetStatus;
import com.example.micro_services.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {
    private PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public ResponseEntity<ApiResponse> addPet(Pet pet) {
        if (pet == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(400, "error", "Pet cannot be null"));
        }
        petRepository.save(pet);
        return ResponseEntity.ok(new ApiResponse(200, "success", "Pet added successfully"));
    }
    @CachePut(value = "pets", key = "#petId")
    public ResponseEntity<?> updatePetByFormData(Long petId, String name, String status) {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if (optionalPet.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, "invalid pet id"));
        }

        Pet pet = optionalPet.get();
        pet.setName(name);

        try {
            pet.setStatus(PetStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status. Allowed values: AVAILABLE, PENDING, SOLD");
        }

        petRepository.save(pet);
        return ResponseEntity.ok(pet);
    }
    @CachePut(value = "pets", key = "#pet.id")
    public ResponseEntity<?> updatePet(Pet pet) {
        if (!petRepository.existsById(pet.getId())) {
            return ResponseEntity.badRequest().body(new ApiResponse(404, "error", "Pet not found"));
        }
        petRepository.save(pet);
        return ResponseEntity.ok(new ApiResponse(200, "success", "Pet updated successfully"));
    }
    @Cacheable(value = "pets_by_status", key = "#status.toString()")
    public ResponseEntity<List<Pet>> getPetByStatus(List<String> status) {
        List<PetStatus> petStatuses = status.stream()
                .map(String::toUpperCase)
                .map(PetStatus::valueOf)
                .collect(Collectors.toList());

        List<Pet> pets = petRepository.findByStatusIn(petStatuses);
        if (pets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pets);
    }
    @Cacheable(value = "pets", key = "#petId")
    public ResponseEntity<?> getPetById(Long petId) {
        Optional<Pet> pet = petRepository.findById(petId);
        return pet.map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().body(new Pet()));
    }
    @CacheEvict(value = "pets", key = "#petId")
    public ResponseEntity<?> deletePet(Long petId) {
        if (!petRepository.existsById(petId)) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, "Pet not found"));
        }
        petRepository.deleteById(petId);
        return ResponseEntity.ok(new ApiResponse(200, "success", "Pet deleted successfully"));
    }


    public ResponseEntity<ApiResponse> uploadImage(Long petId, String additionalMetadata, MultipartFile file) {
        Optional<Pet> petOptional = petRepository.findById(petId);
        if (petOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(404, "error", "Pet not found"));
        }

        try {
            // Define the upload directory
            Path uploadDir = Path.of("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir); // Create directory if it doesn't exist
            }

            // Save the file
            Path filePath = uploadDir.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Update pet with image path
            Pet pet = petOptional.get();
            pet.setImageUrl(filePath.toString());
            petRepository.save(pet);

            return ResponseEntity.ok(new ApiResponse(200, "success", "Image uploaded successfully: " + filePath));

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(new ApiResponse(500, "error", "Error uploading image"));
        }
    }



}
