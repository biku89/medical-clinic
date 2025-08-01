package com.biku89.medical_clinic;

import lombok.Data;

@Data
public class PatientUpdateCommand {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String idCardNo;
    private String phoneNumber;
    private String birthday;
}
