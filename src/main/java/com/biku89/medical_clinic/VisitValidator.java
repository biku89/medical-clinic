package com.biku89.medical_clinic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VisitValidator {

    public static void visitNotInPast(LocalDateTime startVisit) {
        if (startVisit.isBefore(LocalDateTime.now())) {
            throw new IncorrectDataException("The visit cannot be in the past");
        }
    }

    public static void visitStartBeforeEnd(LocalDateTime startVisit, LocalDateTime endVisit) {
        if (!endVisit.isAfter(startVisit)) {
            throw new IncorrectDataException("Visit end time must be after start time visit");
        }
    }

    public static void visitFullQuarterHour(LocalDateTime startVisit) {
        if (startVisit.getMinute() % 15 != 0) {
            throw new IllegalArgumentException("The deadline must start on the full quarter of an hour");

        }
    }

    public static void visitConflicts(List<Visit> conflictVisits) {
        if (!conflictVisits.isEmpty()) {
            throw new VisitConflictException("Visit has wrong time. Overlapping with some visits");
        }
    }

    public static void visitAlreadyBooked(Visit visit) {
        if (visit.getPatient() != null) {
            throw new VisitExistingException("The visit is already booked");

        }
    }
}
