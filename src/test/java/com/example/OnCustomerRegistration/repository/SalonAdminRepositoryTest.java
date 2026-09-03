package com.example.OnCustomerRegistration.repository;

import com.example.OnCustomerRegistration.exception.NotFoundException;
import com.example.OnCustomerRegistration.exception.ValidationException;
import com.example.OnCustomerRegistration.model.BeautySalon;
import com.example.OnCustomerRegistration.model.ServiceInSalon;
import com.example.OnCustomerRegistration.repository.salonAdminRepository.InMemorySalonAdminRepository;
import com.example.OnCustomerRegistration.repository.salonAdminRepository.SalonAdminRepository;
import com.example.OnCustomerRegistration.repository.salonManageRepository.InMemorySalonManageRepository;
import com.example.OnCustomerRegistration.service.SalonManageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class SalonAdminRepositoryTest {

    private SalonAdminRepository salonAdminRepository;
    private SalonManageService salonManageService;
    private final BeautySalon beautySalon = BeautySalon.builder().name("name").address("address").build();
    private final ServiceInSalon serviceInSalon1 = ServiceInSalon.builder().name("name1").durationMinutes(10L)
            .price(BigDecimal.valueOf(100)).build();
    private final ServiceInSalon serviceInSalon2 = ServiceInSalon.builder().name("name2").durationMinutes(20L)
            .price(BigDecimal.valueOf(200)).build();

    @BeforeEach
    public void beforeEach() {
        salonManageService = new SalonManageService(new InMemorySalonManageRepository());
        salonAdminRepository = new InMemorySalonAdminRepository(salonManageService);
    }

    @Test
    @DisplayName("Метод должен добавить услугу")
    public void addService_shouldAddService() {
        BeautySalon salon = salonManageService.addSalon(beautySalon);
        ServiceInSalon newService = salonAdminRepository.addService(salon.getId(), serviceInSalon1);
        ServiceInSalon predictableService = ServiceInSalon.builder().id(1L).name("name1").durationMinutes(10L)
                .price(BigDecimal.valueOf(100)).build();

        Assertions.assertEquals(predictableService, newService);
    }

    @Test
    @DisplayName("Метод должен вернуть ошибку из-за пустого названия")
    public void addService_shouldGetErrorEmptyName() {
        BeautySalon salon = salonManageService.addSalon(beautySalon);
        ServiceInSalon newService = ServiceInSalon.builder().durationMinutes(10L).price(BigDecimal.valueOf(100)).build();

        Assertions.assertThrows(ValidationException.class, () -> salonAdminRepository.addService(salon.getId(), newService));
    }

    @Test
    @DisplayName("Метод должен вернуть ошибку из-за пустой продолжительности")
    public void addService_shouldGetErrorEmptyDuration() {
        BeautySalon salon = salonManageService.addSalon(beautySalon);
        ServiceInSalon newService = ServiceInSalon.builder().name("name").price(BigDecimal.valueOf(100)).build();

        Assertions.assertThrows(ValidationException.class, () -> salonAdminRepository.addService(salon.getId(), newService));
    }

    @Test
    @DisplayName("Метод должен вернуть ошибку из-за пустой цены")
    public void addService_shouldGetErrorEmptyPrice() {
        BeautySalon salon = salonManageService.addSalon(beautySalon);
        ServiceInSalon newService = ServiceInSalon.builder().durationMinutes(10L).name("name").build();

        Assertions.assertThrows(ValidationException.class, () -> salonAdminRepository.addService(salon.getId(), newService));
    }

    @Test
    @DisplayName("Метод должен успешно обновить название")
    public void updateService_shouldUpdateName() {
        BeautySalon salon = salonManageService.addSalon(beautySalon);
        salonAdminRepository.addService(salon.getId(), serviceInSalon1);
        ServiceInSalon update = ServiceInSalon.builder().id(1L).name("updateName").build();
        ServiceInSalon updatedService = salonAdminRepository.updateService(salon.getId(), update);
        ServiceInSalon predictableService = ServiceInSalon.builder().id(1L).name("updateName").durationMinutes(10L)
                .price(BigDecimal.valueOf(100)).build();

        Assertions.assertEquals(predictableService, updatedService);
    }

    @Test
    @DisplayName("Метод должен успешно обновить продолжительность")
    public void updateService_shouldUpdateDuration() {
        BeautySalon salon = salonManageService.addSalon(beautySalon);
        salonAdminRepository.addService(salon.getId(), serviceInSalon1);
        ServiceInSalon update = ServiceInSalon.builder().id(1L).durationMinutes(100L).build();
        ServiceInSalon updatedService = salonAdminRepository.updateService(salon.getId(), update);
        ServiceInSalon predictableService = ServiceInSalon.builder().id(1L).name("name1").durationMinutes(100L)
                .price(BigDecimal.valueOf(100)).build();

        Assertions.assertEquals(predictableService, updatedService);
    }

    @Test
    @DisplayName("Метод должен успешно обновить цену")
    public void updateService_shouldUpdatePrice() {
        BeautySalon salon = salonManageService.addSalon(beautySalon);
        salonAdminRepository.addService(salon.getId(), serviceInSalon1);
        ServiceInSalon update = ServiceInSalon.builder().id(1L).price(BigDecimal.valueOf(1000)).build();
        ServiceInSalon updatedService = salonAdminRepository.updateService(salon.getId(), update);
        ServiceInSalon predictableService = ServiceInSalon.builder().id(1L).name("name1").durationMinutes(10L)
                .price(BigDecimal.valueOf(1000)).build();

        Assertions.assertEquals(predictableService, updatedService);
    }

    @Test
    @DisplayName("Метод должен успешно удалить услугу")
    public void deleteService_shouldDeleteService() {
        BeautySalon salon = salonManageService.addSalon(beautySalon);
        ServiceInSalon newService = salonAdminRepository.addService(salon.getId(), serviceInSalon1);
        salonAdminRepository.deleteService(salon.getId(), newService.getId());

        Assertions.assertEquals(0, salonAdminRepository.getServicesBySalonId(salon.getId()).size());
    }

    @Test
    @DisplayName("Метод должен вернуть ошибку, так как услуги не существует")
    public void deleteService_shouldGetErrorServiceNotExists() {
        BeautySalon salon = salonManageService.addSalon(beautySalon);

        Assertions.assertThrows(NotFoundException.class, () -> salonAdminRepository.deleteService(salon.getId(), 1L));
    }

    @Test
    @DisplayName("Метод должен вернуть список услуг")
    public void getServicesBySalonId_shouldGetListServices() {
        BeautySalon salon = salonManageService.addSalon(beautySalon);
        ServiceInSalon newService1 = salonAdminRepository.addService(salon.getId(), serviceInSalon1);
        ServiceInSalon newService2 = salonAdminRepository.addService(salon.getId(), serviceInSalon2);

        Assertions.assertEquals(2, salonAdminRepository.getServicesBySalonId(salon.getId()).size());
        Assertions.assertTrue(salonAdminRepository.getServicesBySalonId(salon.getId()).contains(newService1));
        Assertions.assertTrue(salonAdminRepository.getServicesBySalonId(salon.getId()).contains(newService2));
    }
}
