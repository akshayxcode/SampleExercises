package com.example.micro_services;
import com.example.micro_services.model.Pet;
import com.example.micro_services.model.PetStatus;
import com.example.micro_services.repository.PetRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PetControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetRepository petRepository;


    String petJson = """
             {
                  "name": "Pinkyzzz", \s
                         "category": {"id": 2, "name": "Goat"},
                         "photoUrls": ["url1", "url2"],
                         "tags": ["friendly"],
                         "status": "AVAILABLE"
                 }
            """;

    @Test
    public void addPetTest() throws Exception {
        Pet mockPet = new Pet(null, "Buddy", List.of("https://example.com/image1.jpg"), List.of("friendly"), PetStatus.AVAILABLE);
        petRepository.save(mockPet);
        //when(petRepository.save(any(Pet.class))).thenReturn(mockPet);

        mockMvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.type").value("success"))
                .andExpect(jsonPath("$.message").value("Pet added successfully"));

        // verify(petRepository, times(1)).save(any(Pet.class));

    }

    @Test
    public void testAddPet_Fail_NullPet() throws Exception {
        mockMvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdatePetByFormData_Success() throws Exception {
        Long petId;
        Pet existingPet = new Pet();
        existingPet.setName("Old Name");
        existingPet.setStatus(PetStatus.AVAILABLE);
        existingPet = petRepository.save(existingPet);
        petId = existingPet.getId();

        mockMvc.perform(post("/pet/{petId}", petId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "NewName")
                        .param("status", "SOLD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NewName"))
                .andExpect(jsonPath("$.status").value("SOLD"));

        Pet updatedPet = petRepository.findById(petId).orElseThrow();
        assert updatedPet.getName().equals("NewName");
        assert updatedPet.getStatus() == PetStatus.SOLD;
    }

    @Test
    public void testUpdatePetByFormData_PetNotFound() throws Exception {
        Long petId = 999L;
        //when(petRepository.findById(petId)).thenReturn(Optional.empty());

        mockMvc.perform(post("/pet/{petId}", petId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "NewName")
                        .param("status", "SOLD"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("invalid pet id"));

        //verify(petRepository, never()).save(any(Pet.class));
    }


    @Test
    public void getPetsFindByStatus_Success() throws Exception {
        Pet pet1 = new Pet(null, "Buddy", List.of("url1"), List.of("friendly"), PetStatus.AVAILABLE);
        Pet pet2 = new Pet(null, "Max", List.of("url2"), List.of("playful"), PetStatus.AVAILABLE);
        petRepository.saveAll(List.of(pet1, pet2));

        mockMvc.perform(get("/pet/findByStatus")
                        .param("status", "AVAILABLE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Buddy"))
                .andExpect(jsonPath("$[1].name").value("Max"));
    }

    @Test
    public void getPetFindByStatus_NoPetsFound() throws Exception {
        //when(petRepository.findByStatusIn(List.of(PetStatus.SOLD))).thenReturn(List.of());

        mockMvc.perform(get("/pet/findByStatus")
                        .param("status", "SOLD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPetById_Success() throws Exception {

        Pet pet = new Pet(null, "Buddy", List.of("url1"), List.of("friendly"), PetStatus.AVAILABLE);
        pet = petRepository.save(pet);
        mockMvc.perform(get("/pet/{petId}", pet.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));

    }

    @Test
    public void testDeletePet_Success() throws Exception {
        Pet pet = new Pet(null, "Buddy", List.of("url1"), List.of("friendly"), PetStatus.AVAILABLE);
        pet = petRepository.save(pet);

        mockMvc.perform(delete("/pet/{petId}", pet.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Pet deleted successfully"));
        assert petRepository.findById(pet.getId()).isEmpty();

    }

    @Test
    public void testDeletePet_PetNotFound() throws Exception {
        Long petId = 999L;

        mockMvc.perform(delete("/pet/{petId}", petId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Pet not found"));
    }

}
