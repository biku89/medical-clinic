package com.biku89.medical_clinic;

import lombok.Data;

@Data
public class DoctorUpdateCommand {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
