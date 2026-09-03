package com.example.OnCustomerRegistration.repository.salonManageRepository;

import com.example.OnCustomerRegistration.model.BeautySalon;
import com.example.OnCustomerRegistration.service.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Slf4j
public class InMemorySalonManageRepository implements SalonManageRepository {

    private final Map<Long, BeautySalon> salons = new ConcurrentHashMap<>();
    private final AtomicLong salonId = new AtomicLong(1);

    @Override
    public BeautySalon addSalon(BeautySalon beautySalon) {
        Validator.checkNotNull(beautySalon.getName(), "название");
        Validator.checkNotNull(beautySalon.getAddress(), "адрес");

        BeautySalon newBeautySalon = BeautySalon.builder().id(salonId.getAndIncrement()).name(beautySalon.getName())
                .address(beautySalon.getAddress()).serviceIds(new HashSet<>()).masterIds(new HashSet<>()).build();

        salons.put(newBeautySalon.getId(), newBeautySalon);

        log.info("Создали салон красоты с id {}", newBeautySalon.getId());

        return newBeautySalon;
    }

    @Override
    public BeautySalon updateSalon(BeautySalon salon) {
        BeautySalon oldSalon = getSalonById(salon.getId());

        if (StringUtils.hasText(salon.getName())) {
            oldSalon.setName(salon.getName());
        }
        if (StringUtils.hasText(salon.getAddress())) {
            oldSalon.setAddress(salon.getAddress());
        }
        if (salon.getServiceIds() != null) {
            oldSalon.setServiceIds(salon.getServiceIds());
        }
        if (salon.getMasterIds() != null) {
            oldSalon.setMasterIds(salon.getMasterIds());
        }

        salons.put(oldSalon.getId(), oldSalon);

        log.info("Обновили салон красоты с id {}", oldSalon.getId());

        return oldSalon;
    }

    @Override
    public BeautySalon getSalonById(long salonId) {
        Validator.checkIdExists(salons, salonId);

        log.info("Получили из списка салон с id {}", salonId);

        return salons.get(salonId);
    }
}
