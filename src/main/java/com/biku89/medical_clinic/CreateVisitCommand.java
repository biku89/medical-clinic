package com.biku89.medical_clinic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateVisitCommand {
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
}
