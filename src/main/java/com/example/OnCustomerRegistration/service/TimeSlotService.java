package com.example.OnCustomerRegistration.service;

import com.example.OnCustomerRegistration.model.TimeSlot;
import com.example.OnCustomerRegistration.repository.timeSlot.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlot addTimeSlot(long salonId, long masterId, TimeSlot timeSlot) {
        return timeSlotRepository.addTimeSlot(salonId, masterId, timeSlot);
    }
}
