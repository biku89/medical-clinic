package com.biku89.medical_clinic;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String postalCode;
    private String street;
    private String buildingNumber;
    @ManyToMany(mappedBy = "institutions")
    private List<Doctor> doctors;

}
