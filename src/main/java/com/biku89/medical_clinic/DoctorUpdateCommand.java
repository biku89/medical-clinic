package com.biku89.medical_clinic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorUpdateCommand {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
