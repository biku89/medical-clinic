package com.biku89.medical_clinic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class Patient {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String idCardNo;
    private String phoneNumber;
    private String birthday;

}
