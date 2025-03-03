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

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(PetStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }
}
