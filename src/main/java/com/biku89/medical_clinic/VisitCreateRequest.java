package com.biku89.medical_clinic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class VisitCreateRequest {
    private LocalDateTime dateTime;
}
