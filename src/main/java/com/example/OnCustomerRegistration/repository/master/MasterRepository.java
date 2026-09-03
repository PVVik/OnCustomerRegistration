package com.example.OnCustomerRegistration.repository.master;

import com.example.OnCustomerRegistration.model.Master;

public interface MasterRepository {

    Master addMaster(long salonId, Master master);

    Master updateMaster(long salonId, Master master);

    void deleteMasterFromSalon(long salonId, long masterId);

    Master getMasterById(long masterId);

}
