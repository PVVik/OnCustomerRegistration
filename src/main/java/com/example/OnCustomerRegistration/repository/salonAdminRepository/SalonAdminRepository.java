package com.example.OnCustomerRegistration.repository.salonAdminRepository;

import com.example.OnCustomerRegistration.model.ServiceInSalon;

import java.util.List;

public interface SalonAdminRepository {

    ServiceInSalon addService(long salonId, ServiceInSalon service);

    ServiceInSalon updateService(long salonId, ServiceInSalon service);

    void deleteService(long salonId, long serviceId);

    List<ServiceInSalon> getServicesBySalonId(long salonId);
}
