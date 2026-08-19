package com.example.OnCustomerRegistration.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TimeSlot {
    private long id;
    private long salonId;
    private long masterId;
    private LocalDateTime start;
    private LocalDateTime end;
    private boolean isBooked;
    private Long bookingId;
}
