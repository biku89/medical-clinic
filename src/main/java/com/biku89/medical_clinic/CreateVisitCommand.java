package com.biku89.medical_clinic;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateVisitCommand {
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
}
