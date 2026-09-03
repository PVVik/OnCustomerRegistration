package com.example.OnCustomerRegistration.service;

import com.example.OnCustomerRegistration.model.Master;
import com.example.OnCustomerRegistration.repository.master.MasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MasterService {

    private final MasterRepository masterRepository;

    public Master addMaster(long salonId, Master master) {
        return masterRepository.addMaster(salonId, master);
    }

    public Master updateMaster(long salonId, Master master) {
        return masterRepository.updateMaster(salonId, master);
    }

    public void deleteMasterFromSalon(long salonId, long masterId) {
        masterRepository.deleteMasterFromSalon(salonId, masterId);
    }

    public Master getMasterById(long masterId) {
        return masterRepository.getMasterById(masterId);
    }
}
