package com.example.OnCustomerRegistration.repository;

import com.example.OnCustomerRegistration.exception.NotFoundException;
import com.example.OnCustomerRegistration.exception.ValidationException;
import com.example.OnCustomerRegistration.model.BeautySalon;
import com.example.OnCustomerRegistration.model.Master;
import com.example.OnCustomerRegistration.model.TimeSlot;
import com.example.OnCustomerRegistration.repository.master.InMemoryMasterRepository;
import com.example.OnCustomerRegistration.repository.master.MasterRepository;
import com.example.OnCustomerRegistration.repository.salonManageRepository.InMemorySalonManageRepository;
import com.example.OnCustomerRegistration.service.SalonManageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MasterRepositoryTest {

    private MasterRepository masterRepository;
    private SalonManageService salonManageService;
    private final Master master1 = Master.builder().fullName("name1").phoneNumber("89999999999").build();
    private final BeautySalon beautySalon1 = BeautySalon.builder().name("salonName1").address("address1").build();
    private final BeautySalon beautySalon2 = BeautySalon.builder().name("salonName2").address("address2").build();

    @BeforeEach
    public void beforeEach() {
        salonManageService = new SalonManageService(new InMemorySalonManageRepository());
        masterRepository = new InMemoryMasterRepository(salonManageService);
    }

    @Test
    @DisplayName("Метод должен добавить специалиста")
    public void addMaster_shouldAddMaster() {
        BeautySalon salon = salonManageService.addSalon(beautySalon1);
        Master newMaster = masterRepository.addMaster(salon.getId(), master1);

        Master expectedMaster = Master.builder().id(1L).fullName("name1").phoneNumber("89999999999")
                .salonIds(new HashSet<>(List.of(1L))).timeSlots(new HashMap<>()).build();

        Assertions.assertEquals(expectedMaster, newMaster);
        Assertions.assertEquals(1, newMaster.getSalonIds().size());
        Assertions.assertEquals(1, salon.getMasterIds().size());
    }

    @Test
    @DisplayName("Метод должен вернуть существующего специалиста с обновленным списком салонов красоты")
    public void addMaster_shouldReturnExistingMaster() {
        BeautySalon salonOne = salonManageService.addSalon(beautySalon1);
        masterRepository.addMaster(salonOne.getId(), master1);
        BeautySalon salonTwo = salonManageService.addSalon(beautySalon2);
        Master addedMaster = masterRepository.addMaster(salonTwo.getId(), master1);

        Master expectedMaster = Master.builder().id(1L).fullName("name1").phoneNumber("89999999999")
                .salonIds(new HashSet<>(List.of(1L, 2L))).timeSlots(new HashMap<>()).build();

        Assertions.assertEquals(expectedMaster, addedMaster);

    }

    @Test
    @DisplayName("Метод должен вернуть ошибку из-за пустого имени")
    public void addMaster_shouldGetErrorEmptyName() {
        BeautySalon salon = salonManageService.addSalon(beautySalon1);

        Assertions.assertThrows(ValidationException.class, () -> masterRepository.addMaster(salon.getId(),
                Master.builder().phoneNumber("89999999999").build()));
    }

    @Test
    @DisplayName("Метод должен вернуть ошибку из-за пустого номера телефона")
    public void addMaster_shouldGetErrorEmptyPhone() {
        BeautySalon salon = salonManageService.addSalon(beautySalon1);

        Assertions.assertThrows(ValidationException.class, () -> masterRepository.addMaster(salon.getId(),
                Master.builder().fullName("name").build()));
    }

    @Test
    @DisplayName("Метод должен успешно обновить имя")
    public void updateMaster_shouldUpdateName() {
        BeautySalon salon = salonManageService.addSalon(beautySalon1);
        masterRepository.addMaster(salon.getId(), master1);
        Master updatedMaster = masterRepository.updateMaster(salon.getId(), Master.builder().id(1L)
                .fullName("updatedName").build());
        Master expectedMaster = Master.builder().id(1L).fullName("updatedName").phoneNumber("89999999999")
                .salonIds(new HashSet<>(List.of(1L))).timeSlots(new HashMap<>()).build();

        Assertions.assertEquals(expectedMaster, updatedMaster);
    }

    @Test
    @DisplayName("Метод должен успешно обновить номер телефона")
    public void updateMaster_shouldUpdatePhone() {
        BeautySalon salon = salonManageService.addSalon(beautySalon1);
        masterRepository.addMaster(salon.getId(), master1);
        Master updatedMaster = masterRepository.updateMaster(salon.getId(), Master.builder().id(1L)
                .phoneNumber("88888888888").build());
        Master expectedMaster = Master.builder().id(1L).fullName("name1").phoneNumber("88888888888")
                .salonIds(new HashSet<>(List.of(1L))).timeSlots(new HashMap<>()).build();

        Assertions.assertEquals(expectedMaster, updatedMaster);
    }

    @Test
    @DisplayName("Метод должен успешно обновить список id салонов красоты")
    public void updateMaster_shouldUpdateSalonIds() {
        BeautySalon salon = salonManageService.addSalon(beautySalon1);
        masterRepository.addMaster(salon.getId(), master1);
        Master updatedMaster = masterRepository.updateMaster(salon.getId(), Master.builder().id(1L)
                .salonIds(new HashSet<>(List.of(1L, 2L, 3L))).build());
        Master expectedMaster = Master.builder().id(1L).fullName("name1").phoneNumber("89999999999")
                .salonIds(new HashSet<>(List.of(1L, 2L, 3L))).timeSlots(new HashMap<>()).build();

        Assertions.assertEquals(expectedMaster, updatedMaster);
    }

    @Test
    @DisplayName("Метод должен успешно обновить список окошек")
    public void updateMaster_shouldUpdateTimeSlots() {
        BeautySalon salon = salonManageService.addSalon(beautySalon1);
        masterRepository.addMaster(salon.getId(), master1);
        TimeSlot timeSlot = TimeSlot.builder().id(1L).salonId(salon.getId()).masterId(1L)
                .start(LocalDateTime.now()).end(LocalDateTime.now().plusHours(1)).isBooked(false).build();
        Map<Long, List<TimeSlot>> timeSlots = new HashMap<>();
        timeSlots.put(salon.getId(), List.of(timeSlot));
        Master updatedMaster = masterRepository.updateMaster(salon.getId(), Master.builder().id(1L)
                .timeSlots(timeSlots).build());
        Master expectedMaster = Master.builder().id(1L).fullName("name1").phoneNumber("89999999999")
                .salonIds(new HashSet<>(List.of(1L))).timeSlots(timeSlots).build();

        Assertions.assertEquals(expectedMaster, updatedMaster);
    }

    @Test
    @DisplayName("Метод должен успешно удалить мастера из списка салона красоты")
    public void deleteMaster_shouldDeleteMaster() {
        BeautySalon beautySalon = salonManageService.addSalon(beautySalon1);
        Master master = masterRepository.addMaster(beautySalon.getId(), master1);

        masterRepository.deleteMasterFromSalon(beautySalon.getId(), master.getId());

        Assertions.assertEquals(0, beautySalon.getMasterIds().size());
        Assertions.assertEquals(0, master.getSalonIds().size());
    }

    @Test
    @DisplayName("Метод должен вернуть ошибку, так как мастера не существует")
    public void getMasterById_shouldGetErrorMasterNotExists() {
        Assertions.assertThrows(NotFoundException.class, () -> masterRepository.getMasterById(1L));
    }

    @Test
    @DisplayName("Метод должен вернуть мастера по id")
    public void getMasterById_shouldGetMasterById() {
        BeautySalon beautySalon = salonManageService.addSalon(beautySalon1);
        masterRepository.addMaster(beautySalon.getId(), master1);

        Master expectedMaster = Master.builder().id(1L).fullName("name1").phoneNumber("89999999999")
                .salonIds(new HashSet<>(List.of(1L))).timeSlots(new HashMap<>()).build();

        Assertions.assertEquals(expectedMaster, masterRepository.getMasterById(1L));
    }
}
