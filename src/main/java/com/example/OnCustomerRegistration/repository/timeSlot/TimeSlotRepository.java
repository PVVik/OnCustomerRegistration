package com.example.OnCustomerRegistration.repository.timeSlot;

import com.example.OnCustomerRegistration.model.TimeSlot;

public interface TimeSlotRepository {

    TimeSlot addTimeSlot(long salonId, long masterId, TimeSlot timeSlot);
}
