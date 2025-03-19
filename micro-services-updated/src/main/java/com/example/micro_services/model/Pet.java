package com.example.micro_services.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity @Getter @Setter
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("photoUrls")
    @ElementCollection
    private List<String> photoUrls;

    @JsonProperty("tags")
    @ElementCollection
    private List<String> tags;

    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private PetStatus status;

    private String imageUrl;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(PetStatus status) {
        this.status = status;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public PetStatus getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public Pet(Long id, String name, List<String> photoUrls, List<String> tags, PetStatus status) {
        this.id = id;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }
    public Pet() {

    }


}
