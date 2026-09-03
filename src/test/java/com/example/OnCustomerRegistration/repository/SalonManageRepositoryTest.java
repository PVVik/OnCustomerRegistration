package com.example.OnCustomerRegistration.repository;

import com.example.OnCustomerRegistration.exception.NotFoundException;
import com.example.OnCustomerRegistration.exception.ValidationException;
import com.example.OnCustomerRegistration.model.BeautySalon;
import com.example.OnCustomerRegistration.repository.salonManageRepository.InMemorySalonManageRepository;
import com.example.OnCustomerRegistration.repository.salonManageRepository.SalonManageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

public class SalonManageRepositoryTest {

    private SalonManageRepository salonManageRepository;
    private final BeautySalon beautySalon = BeautySalon.builder().name("name").address("address").build();

    @BeforeEach
    public void beforeEach() {
        salonManageRepository = new InMemorySalonManageRepository();
    }

    @Test
    @DisplayName("Метод должен создать салон красоты")
    public void addSalon_shouldAddSalon() {
        BeautySalon salon = salonManageRepository.addSalon(beautySalon);
        BeautySalon expectedSalon = BeautySalon.builder().id(1L).name("name").address("address")
                .masterIds(new HashSet<>()).serviceIds(new HashSet<>()).build();

        Assertions.assertEquals(salon, expectedSalon);
    }

    @Test
    @DisplayName("Метод должен вернуть ошибку при пустом названии")
    public void addSalon_shouldGetErrorEmptyName() {
        BeautySalon salon = BeautySalon.builder().address("address").build();

        Assertions.assertThrows(ValidationException.class, () -> salonManageRepository.addSalon(salon));
    }

    @Test
    @DisplayName("Метод должен вернуть ошибку при пустом адресе")
    public void addSalon_shouldGetErrorEmptyAddress() {
        BeautySalon salon = BeautySalon.builder().name("name").build();

        Assertions.assertThrows(ValidationException.class, () -> salonManageRepository.addSalon(salon));
    }

    @Test
    @DisplayName("Метод должен успешно обновить название")
    public void updateSalon_shouldUpdateName() {
        salonManageRepository.addSalon(beautySalon);
        BeautySalon updatedSalon = salonManageRepository.updateSalon(BeautySalon.builder().id(1L)
                .name("updatedName").build());
        BeautySalon expectedSalon = BeautySalon.builder().id(1L).name("updatedName").address("address")
                .masterIds(new HashSet<>()).serviceIds(new HashSet<>()).build();

        Assertions.assertEquals(updatedSalon, expectedSalon);
    }

    @Test
    @DisplayName("Метод должен успешно обновить адрес")
    public void updateSalon_shouldUpdateAddress() {
        salonManageRepository.addSalon(beautySalon);
        BeautySalon updatedSalon = salonManageRepository.updateSalon(BeautySalon.builder().id(1L)
                .address("updatedAddress").build());
        BeautySalon expectedSalon = BeautySalon.builder().id(1L).name("name").address("updatedAddress")
                .masterIds(new HashSet<>()).serviceIds(new HashSet<>()).build();

        Assertions.assertEquals(updatedSalon, expectedSalon);
    }

    @Test
    @DisplayName("Метод должен успешно обновить список услуг")
    public void updateSalon_shouldUpdateServiceIds() {
        salonManageRepository.addSalon(beautySalon);
        BeautySalon updatedSalon = salonManageRepository.updateSalon(BeautySalon.builder().id(1L)
                .serviceIds(new HashSet<>(List.of(1L))).build());
        BeautySalon expectedSalon = BeautySalon.builder().id(1L).name("name").address("address")
                .masterIds(new HashSet<>()).serviceIds(new HashSet<>(List.of(1L))).build();

        Assertions.assertEquals(updatedSalon, expectedSalon);
    }

    @Test
    @DisplayName("Метод должен успешно обновить список мастеров")
    public void updateSalon_shouldUpdateMastersIds() {
        salonManageRepository.addSalon(beautySalon);
        BeautySalon updatedSalon = salonManageRepository.updateSalon(BeautySalon.builder().id(1L)
                .masterIds(new HashSet<>(List.of(1L))).build());
        BeautySalon expectedSalon = BeautySalon.builder().id(1L).name("name").address("address")
                .masterIds(new HashSet<>(List.of(1L))).serviceIds(new HashSet<>()).build();

        Assertions.assertEquals(updatedSalon, expectedSalon);
    }

    @Test
    @DisplayName("Метод должен успешно вернуть салон красоты")
    public void getSalonById_shouldGetSalon() {
        salonManageRepository.addSalon(beautySalon);
        BeautySalon expectedSalon = BeautySalon.builder().id(1L).name("name").address("address")
                .masterIds(new HashSet<>()).serviceIds(new HashSet<>()).build();

        Assertions.assertEquals(expectedSalon, salonManageRepository.getSalonById(1L));
    }

    @Test
    @DisplayName("Метод должен вернуть ошибку, так как салон с указанным id не существует")
    public void getSalonById_shouldGetErrorNotExistsMaster() {
        Assertions.assertThrows(NotFoundException.class, () -> salonManageRepository.getSalonById(1L));
    }
}
