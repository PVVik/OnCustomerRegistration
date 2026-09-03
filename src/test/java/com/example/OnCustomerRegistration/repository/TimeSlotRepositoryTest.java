package com.example.OnCustomerRegistration.repository;

import com.example.OnCustomerRegistration.exception.ValidationException;
import com.example.OnCustomerRegistration.model.BeautySalon;
import com.example.OnCustomerRegistration.model.Master;
import com.example.OnCustomerRegistration.model.TimeSlot;
import com.example.OnCustomerRegistration.repository.master.InMemoryMasterRepository;
import com.example.OnCustomerRegistration.repository.master.MasterRepository;
import com.example.OnCustomerRegistration.repository.salonManageRepository.InMemorySalonManageRepository;
import com.example.OnCustomerRegistration.repository.timeSlot.InMemoryTimeSlotRepository;
import com.example.OnCustomerRegistration.repository.timeSlot.TimeSlotRepository;
import com.example.OnCustomerRegistration.service.SalonManageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TimeSlotRepositoryTest {

    private TimeSlotRepository timeSlotRepository;
    private SalonManageService salonManageService;
    private MasterRepository masterRepository;
    private final BeautySalon beautySalon = BeautySalon.builder().name("name").address("address").build();
    private final Master master = Master.builder().phoneNumber("89999999999").fullName("name").build();
    private TimeSlot timeSlot1 = TimeSlot.builder().start(LocalDateTime.now().withSecond(0).withNano(0))
            .end(LocalDateTime.now().withSecond(0).withNano(0).plusHours(1)).build();

    @BeforeEach
    public void beforeEach() {
        salonManageService = new SalonManageService(new InMemorySalonManageRepository());
        masterRepository = new InMemoryMasterRepository(salonManageService);
        timeSlotRepository = new InMemoryTimeSlotRepository(salonManageService, masterRepository);
    }

    @Test
    @DisplayName("Метод должен добавить окошко")
    public void addTimeSlot_shouldAddTimeSlot() {
        BeautySalon newSalon = salonManageService.addSalon(beautySalon);
        Master newMaster = masterRepository.addMaster(newSalon.getId(), master);
        TimeSlot newTimeSlot = timeSlotRepository.addTimeSlot(newSalon.getId(), newMaster.getId(), timeSlot1);
        TimeSlot predictableTimeSlot = TimeSlot.builder().id(1L).salonId(1L).masterId(1L)
                .start(LocalDateTime.now().withSecond(0).withNano(0))
                .end(LocalDateTime.now().withSecond(0).withNano(0).plusHours(1)).isBooked(false).build();

        Assertions.assertEquals(predictableTimeSlot, newTimeSlot);
        Assertions.assertEquals(1, newMaster.getTimeSlots().size());
    }

    @Test
    @DisplayName("Метод должен вернуть ошибку из-за пустой даты старта")
    public void addTimeSlot_shouldGetErrorEmptyStart() {
        BeautySalon newSalon = salonManageService.addSalon(beautySalon);
        Master newMaster = masterRepository.addMaster(newSalon.getId(), master);
        TimeSlot wrongTimeSlot = TimeSlot.builder()
                .end(LocalDateTime.now().withSecond(0).withNano(0).plusHours(1)).build();

        Assertions.assertThrows(ValidationException.class, () -> timeSlotRepository.addTimeSlot(newSalon.getId(),
                newMaster.getId(), wrongTimeSlot));
    }

    @Test
    @DisplayName("Метод должен вернуть ошибку из-за пустой даты окончания")
    public void addTimeSlot_shouldGetErrorEmptyEnd() {
        BeautySalon newSalon = salonManageService.addSalon(beautySalon);
        Master newMaster = masterRepository.addMaster(newSalon.getId(), master);
        TimeSlot wrongTimeSlot = TimeSlot.builder()
                .start(LocalDateTime.now().withSecond(0).withNano(0)).build();

        Assertions.assertThrows(ValidationException.class, () -> timeSlotRepository.addTimeSlot(newSalon.getId(),
                newMaster.getId(), wrongTimeSlot));
    }

    @Test
    @DisplayName("Метод должен вернуть ошибку, так как старт позже окончания")
    public void addTimeSlot_shouldGetErrorStartAfterEnd() {
        BeautySalon newSalon = salonManageService.addSalon(beautySalon);
        Master newMaster = masterRepository.addMaster(newSalon.getId(), master);
        TimeSlot wrongTimeSlot = TimeSlot.builder().start(LocalDateTime.now().withSecond(0).withNano(0).plusHours(1))
                .end(LocalDateTime.now().withSecond(0).withNano(0)).build();

        Assertions.assertThrows(ValidationException.class, () -> timeSlotRepository.addTimeSlot(newSalon.getId(),
                newMaster.getId(), wrongTimeSlot));
    }
}
