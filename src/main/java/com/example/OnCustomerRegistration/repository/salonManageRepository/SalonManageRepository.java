package com.example.OnCustomerRegistration.repository.salonManageRepository;

import com.example.OnCustomerRegistration.model.BeautySalon;

public interface SalonManageRepository {

    BeautySalon addSalon(BeautySalon beautySalon);

    BeautySalon updateSalon(BeautySalon salon);

    BeautySalon getSalonById(long salonId);
}
