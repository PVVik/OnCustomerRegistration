package com.example.OnCustomerRegistration.service;

import com.example.OnCustomerRegistration.model.BeautySalon;
import com.example.OnCustomerRegistration.repository.salonManageRepository.SalonManageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalonManageService {

    private final SalonManageRepository salonManageRepository;

    public BeautySalon addSalon(BeautySalon beautySalon) {
        return salonManageRepository.addSalon(beautySalon);
    }

    public BeautySalon updateSalon(BeautySalon salon) {
        return salonManageRepository.updateSalon(salon);
    }

    public BeautySalon getSalonById(long salonId) {
        return salonManageRepository.getSalonById(salonId);
    }

}
