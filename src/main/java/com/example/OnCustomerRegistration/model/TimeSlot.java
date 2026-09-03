package com.example.OnCustomerRegistration.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

@Data
@Builder
public class TimeSlot {
    private long id;
    private long salonId;
    private long masterId;
    @Future(message = "Начало окошка должно находиться в будущем")
    private LocalDateTime start;
    @Future(message = "Конец окошка должен находиться в будущем")
    private LocalDateTime end;
    private boolean isBooked;
    private Long bookingId;

    public boolean overlapsWith(TimeSlot o) {
        return this.start.isBefore(o.end) && o.start.isBefore(this.end);
    }
}
