package com.example.OnCustomerRegistration.repository.timeSlot;

import com.example.OnCustomerRegistration.model.BeautySalon;
import com.example.OnCustomerRegistration.model.Master;
import com.example.OnCustomerRegistration.model.TimeSlot;
import com.example.OnCustomerRegistration.repository.master.MasterRepository;
import com.example.OnCustomerRegistration.service.SalonManageService;
import com.example.OnCustomerRegistration.service.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Slf4j
public class InMemoryTimeSlotRepository implements TimeSlotRepository {

    private final SalonManageService salonManageService;
    private final MasterRepository masterRepository;
    private final Map<Long, TimeSlot> timeSlots = new ConcurrentHashMap<>();
    private final AtomicLong timeSlotIds = new AtomicLong(1);

    @Override
    public TimeSlot addTimeSlot(long salonId, long masterId, TimeSlot timeSlot) {
        Validator.checkNotNull(timeSlot.getStart(), "дата старта");
        Validator.checkNotNull(timeSlot.getEnd(), "дата окончания");
        Validator.validEndAfterStart(timeSlot.getStart(), timeSlot.getEnd());

        BeautySalon salon = salonManageService.getSalonById(salonId);
        Master master = masterRepository.getMasterById(masterId);

        TimeSlot newTimeSlot = TimeSlot.builder().id(timeSlotIds.getAndIncrement()).salonId(salonId).masterId(masterId)
                .start(timeSlot.getStart()).end(timeSlot.getEnd()).isBooked(false).build();

        Map<Long, List<TimeSlot>> masterTimeSlots = master.getTimeSlots();

        Validator.checkTimeSlotNotOverlapWith(newTimeSlot, masterTimeSlots);

        List<TimeSlot> timeSlotsInSalon = masterTimeSlots.getOrDefault(salonId, new ArrayList<>());
        timeSlotsInSalon.add(newTimeSlot);
        masterTimeSlots.put(salonId, timeSlotsInSalon);
        master.setTimeSlots(masterTimeSlots);
        masterRepository.updateMaster(salonId, master);

        timeSlots.put(newTimeSlot.getId(), newTimeSlot);

        log.info("Создали окошко с id " + newTimeSlot.getId());

        return newTimeSlot;
    }
}
