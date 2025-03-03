package com.example.micro_services.controller;

import com.example.micro_services.model.ApiResponse;
import com.example.micro_services.model.ErrorResponse;
import com.example.micro_services.model.Pet;
import com.example.micro_services.model.PetStatus;
import com.example.micro_services.repository.PetRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetRepository petRepository;

    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addPet(@RequestBody Pet pet) {
        if (pet == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(400, "error", "Pet cannot be null"));
        }
        petRepository.save(pet);
        return ResponseEntity.ok(new ApiResponse(200, "success", "Pet added successfully"));
    }

    @PostMapping("/{petId}")
    public ResponseEntity<?> updatePetByFormData(
            @PathVariable Long petId,
            @RequestParam("name") String name,
            @RequestParam("status") String status) {
     Optional<Pet> optionalPet = petRepository.findById(petId);
     if(optionalPet.isEmpty()) {
         return ResponseEntity.status(404).body(new ErrorResponse(404,"invalid pet id"));
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

    @PutMapping
    public ResponseEntity<ApiResponse> updatePet(@RequestBody Pet pet) {
        if (!petRepository.existsById(pet.getId())) {
            return ResponseEntity.badRequest().body(new ApiResponse(404, "error", "Pet not found"));
        }
        petRepository.save(pet);
        return ResponseEntity.ok(new ApiResponse(200, "success", "Pet updated successfully"));
    }

    //GET /pet/{findByStatus} - Find pet by Status
    @GetMapping("/findByStatus")
    public ResponseEntity<List<Pet>> getPetByStatus(@RequestParam List<String> status) {
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

    //GET /pet/{petId} - Find pet by ID
    @GetMapping("/{petId}")
    public ResponseEntity<?> getPetByStatus(@PathVariable Long petId) {
        Optional<Pet> pet = petRepository.findById(petId);
        return pet.map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().body(new Pet()));
    }

    //DELETE /pet/{petId} - Delete a pet
    @DeleteMapping("/{petId}")
    public ResponseEntity<ApiResponse> deletePet(@PathVariable Long petId) {
        if (!petRepository.existsById(petId)) {
            return ResponseEntity.badRequest().body(new ApiResponse(404, "error", "Pet not found"));
        }
        petRepository.deleteById(petId);
        return ResponseEntity.ok(new ApiResponse(200, "success", "Pet deleted successfully"));
    }

    //POST /pet/{petId}/uploadImage - Upload an image
    @PostMapping("/{petId}/uploadImage")
    public ResponseEntity<ApiResponse> uploadImage(
            @PathVariable Long petId,
            @RequestParam(value = "additionalMetadata", required = false) String additionalMetadata,
            @RequestParam("file") MultipartFile file) {

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
