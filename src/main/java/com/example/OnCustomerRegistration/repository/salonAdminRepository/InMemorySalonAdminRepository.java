package com.example.OnCustomerRegistration.repository.salonAdminRepository;

import com.example.OnCustomerRegistration.model.BeautySalon;
import com.example.OnCustomerRegistration.model.ServiceInSalon;
import com.example.OnCustomerRegistration.service.SalonManageService;
import com.example.OnCustomerRegistration.service.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class InMemorySalonAdminRepository implements SalonAdminRepository {

    private final SalonManageService salonManageService;
    private final ConcurrentHashMap<Long, ServiceInSalon> services = new ConcurrentHashMap<>();
    private final AtomicLong serviceId = new AtomicLong(1);

    @Override
    public ServiceInSalon addService(long salonId, ServiceInSalon service) {
        Validator.checkNotNull(service.getName(), "название");
        Validator.checkNotNull(service.getDurationMinutes(), "длительность");
        Validator.checkNotNull(service.getPrice(), "стоимость");

        BeautySalon beautySalon = salonManageService.getSalonById(salonId);
        ServiceInSalon newService = ServiceInSalon.builder().id(serviceId.getAndIncrement()).name(service.getName())
                .durationMinutes(service.getDurationMinutes()).price(service.getPrice()).build();

        Set<Long> salonServiceIds = beautySalon.getServiceIds();
        salonServiceIds.add(newService.getId());
        beautySalon.setServiceIds(salonServiceIds);
        salonManageService.updateSalon(beautySalon);

        services.put(newService.getId(), newService);

        log.info("Создали услугу с id {}", newService.getId());

        return newService;
    }

    @Override
    public ServiceInSalon updateService(long salonId, ServiceInSalon service) {
        Validator.checkIdExists(services, service.getId());

        ServiceInSalon oldService = services.get(service.getId());

        if (StringUtils.hasText(service.getName())) {
            oldService.setName(service.getName());
        }
        if (service.getDurationMinutes() != null) {
            oldService.setDurationMinutes(service.getDurationMinutes());
        }
        if (service.getPrice() != null) {
            oldService.setPrice(service.getPrice());
        }

        services.put(oldService.getId(), oldService);

        log.info("Обновили услугу с id {}", oldService.getId());

        return oldService;
    }

    @Override
    public void deleteService(long salonId, long serviceId) {
        Validator.checkIdExists(services, serviceId);

        BeautySalon beautySalon = salonManageService.getSalonById(salonId);
        Set<Long> salonServiceIds = beautySalon.getServiceIds();
        salonServiceIds.remove(serviceId);
        beautySalon.setServiceIds(salonServiceIds);
        salonManageService.updateSalon(beautySalon);

        services.remove(serviceId);

        log.info("Удалили услугу с id {}", serviceId);
    }

    @Override
    public List<ServiceInSalon> getServicesBySalonId(long salonId) {
        BeautySalon beautySalon = salonManageService.getSalonById(salonId);
        Set<Long> salonServices = beautySalon.getServiceIds();

        log.info("Получили список услуг салона с id {}", salonId);

        return salonServices.stream()
                .filter(services::containsKey)
                .map(services::get)
                .collect(Collectors.toList());
    }
}
