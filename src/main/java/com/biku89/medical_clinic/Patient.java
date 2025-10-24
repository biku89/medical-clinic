package com.biku89.medical_clinic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PATIENTS")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String idCardNo;
    private String phoneNumber;
    private String birthday;

    public void updatePatient(PatientUpdateCommand updatedPatient){
        this.firstName = updatedPatient.getFirstName();
        this.lastName = updatedPatient.getLastName();
        this.email = updatedPatient.getEmail();
        this.password = updatedPatient.getPassword();
        this.phoneNumber = updatedPatient.getPhoneNumber();
        this.birthday = updatedPatient.getBirthday();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient))
            return false;
        Patient patient = (Patient) o;
        return id != null && id.equals(patient.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
