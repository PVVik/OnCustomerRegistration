package com.example.OnCustomerRegistration.repository.master;

import com.example.OnCustomerRegistration.model.BeautySalon;
import com.example.OnCustomerRegistration.model.Master;
import com.example.OnCustomerRegistration.service.SalonManageService;
import com.example.OnCustomerRegistration.service.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Slf4j
public class InMemoryMasterRepository implements MasterRepository {

    private final Map<String, Master> masters = new ConcurrentHashMap<>();
    private final Map<Long, Master> mastersByIds = new ConcurrentHashMap<>();
    private final AtomicLong masterId = new AtomicLong(1);
    private final SalonManageService salonManageService;

    @Override
    public Master addMaster(long salonId, Master master) {
        Validator.checkNotNull(master.getFullName(), "\"имя\"");
        Validator.checkNotNull(master.getPhoneNumber(), "\"номер телефона\"");

        Master newMaster;
        BeautySalon beautySalon = salonManageService.getSalonById(salonId);

        if (masters.containsKey(master.getPhoneNumber())) {
            newMaster = masters.get(master.getPhoneNumber());
        } else {
            newMaster = Master.builder().id(masterId.getAndIncrement()).fullName(master.getFullName())
                    .phoneNumber(master.getPhoneNumber()).salonIds(new HashSet<>()).timeSlots(new ConcurrentHashMap<>())
                    .build();
        }

        Set<Long> salonMasterIds = beautySalon.getMasterIds();
        salonMasterIds.add(newMaster.getId());
        beautySalon.setMasterIds(salonMasterIds);
        salonManageService.updateSalon(beautySalon);

        Set<Long> salonsIdsWhereMasterWorks = newMaster.getSalonIds();
        salonsIdsWhereMasterWorks.add(beautySalon.getId());
        newMaster.setSalonIds(salonsIdsWhereMasterWorks);

        masters.put(newMaster.getPhoneNumber(), newMaster);
        mastersByIds.put(newMaster.getId(), newMaster);

        log.info("Создали и добавили мастера с id {}", newMaster.getId());

        return newMaster;
    }

    @Override
    public Master updateMaster(long salonId, Master master) {
        Master oldMaster = this.getMasterById(master.getId());

        if (StringUtils.hasText(master.getFullName())) {
            oldMaster.setFullName(master.getFullName());
        }
        if (StringUtils.hasText(master.getPhoneNumber())) {
            oldMaster.setPhoneNumber(master.getPhoneNumber());
        }
        if (master.getSalonIds() != null) {
            oldMaster.setSalonIds(master.getSalonIds());
        }
        if (master.getTimeSlots() != null) {
            oldMaster.setTimeSlots(master.getTimeSlots());
        }

        masters.put(oldMaster.getPhoneNumber(), oldMaster);
        mastersByIds.put(oldMaster.getId(), oldMaster);

        log.info("Обновили мастера с id {}", oldMaster.getId());

        return oldMaster;
    }

    @Override
    public void deleteMasterFromSalon(long salonId, long masterId) {
        Master master = this.getMasterById(masterId);
        BeautySalon salon = salonManageService.getSalonById(salonId);

        Set<Long> salonIds = master.getSalonIds();
        Set<Long> masterIds = salon.getMasterIds();
        salonIds.remove(salonId);
        masterIds.remove(masterId);
        master.setSalonIds(salonIds);
        salon.setMasterIds(masterIds);

        this.updateMaster(salonId, master);
        salonManageService.updateSalon(salon);

        log.info("Удалили мастера с id {} из салона с id {}", masterId, salonId);
    }

    @Override
    public Master getMasterById(long masterId) {
        Validator.checkIdExists(mastersByIds, masterId);

        log.info("Получили мастера из списка по id {}", masterId);

        return mastersByIds.get(masterId);
    }
}
