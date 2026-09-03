package com.example.OnCustomerRegistration.service;

import com.example.OnCustomerRegistration.model.Master;
import com.example.OnCustomerRegistration.model.ServiceInSalon;
import com.example.OnCustomerRegistration.model.TimeSlot;
import com.example.OnCustomerRegistration.repository.salonAdminRepository.SalonAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalonAdminService {

    private final MasterService masterService;
    private final SalonAdminRepository salonAdminRepository;
    private final TimeSlotService timeSlotService;

    public ServiceInSalon addService(long salonId, ServiceInSalon service) {
        return salonAdminRepository.addService(salonId, service);
    }

    public ServiceInSalon updateService(long salonId, ServiceInSalon service) {
        return salonAdminRepository.updateService(salonId, service);
    }

    public void deleteService(long salonId, long serviceId) {
        salonAdminRepository.deleteService(salonId, serviceId);
    }

    public List<ServiceInSalon> getServicesBySalonId(long salonId) {
        return salonAdminRepository.getServicesBySalonId(salonId);
    }

    public Master addMaster(long salonId, Master master) {
        return masterService.addMaster(salonId, master);
    }

    public Master updateMaster(long salonId, Master master) {
        return masterService.updateMaster(salonId, master);
    }

    public void deleteMasterFromSalon(long salonId, long masterId) {
        masterService.deleteMasterFromSalon(salonId, masterId);
    }

    public TimeSlot addTimeSlot(long salonId, long masterId, TimeSlot timeSlot) {
        return timeSlotService.addTimeSlot(salonId, masterId, timeSlot);
    }
}
