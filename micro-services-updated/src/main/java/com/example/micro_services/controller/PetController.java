package com.example.micro_services.controller;

import com.example.micro_services.model.*;
import com.example.micro_services.service.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;


    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addPet(@RequestBody Pet pet) {
        return petService.addPet(pet);
    }

    @PostMapping("/{petId}")
    public ResponseEntity<?> updatePetByFormData(
            @PathVariable Long petId,
            @RequestParam("name") String name,
            @RequestParam("status") String status) {
        return petService.updatePetByFormData(petId, name, status);
    }

    @PutMapping
    public ResponseEntity<?> updatePet(@RequestBody Pet pet) {
        return petService.updatePet(pet);
    }

    //GET /pet/{findByStatus} - Find pet by Status
    @GetMapping("/findByStatus")
    public ResponseEntity<List<Pet>> getPetByStatus(@RequestParam List<String> status) {
        return petService.getPetByStatus(status);
    }

    //GET /pet/{petId} - Find pet by ID
    @GetMapping("/{petId}")
    public ResponseEntity<?> getPetById(@PathVariable Long petId) {
        return petService.getPetById(petId);
    }

    //DELETE /pet/{petId} - Delete a pet
    @DeleteMapping("/{petId}")
    public ResponseEntity<?> deletePet(@PathVariable Long petId) {
        return petService.deletePet(petId);
    }

    //POST /pet/{petId}/uploadImage - Upload an image
    @PostMapping("/{petId}/uploadImage")
    public ResponseEntity<ApiResponse> uploadImage(
            @PathVariable Long petId,
            @RequestParam(value = "additionalMetadata", required = false) String additionalMetadata,
            @RequestParam("file") MultipartFile file) {
        return petService.uploadImage(petId, additionalMetadata, file);
    }

}
