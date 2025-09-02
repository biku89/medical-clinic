package com.biku89.medical_clinic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PatientsDeleteCommand {
    private List<Long> ids;

}
