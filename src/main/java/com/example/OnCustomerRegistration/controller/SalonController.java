package com.example.OnCustomerRegistration.controller;

import com.example.OnCustomerRegistration.model.BeautySalon;
import com.example.OnCustomerRegistration.model.Master;
import com.example.OnCustomerRegistration.model.ServiceInSalon;
import com.example.OnCustomerRegistration.model.TimeSlot;
import com.example.OnCustomerRegistration.service.SalonAdminService;
import com.example.OnCustomerRegistration.service.SalonManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/salon")
@RequiredArgsConstructor
public class SalonController {

    private final SalonManageService salonManageService;
    private final SalonAdminService salonAdminService;

    @PostMapping
    public BeautySalon addSalon(@RequestBody @Valid BeautySalon salon) {
        return salonManageService.addSalon(salon);
    }

    @PostMapping("/{salonId}/services")
    public ServiceInSalon addService(@PathVariable long salonId, @RequestBody @Valid ServiceInSalon serviceInSalon) {
        return salonAdminService.addService(salonId, serviceInSalon);
    }

    @PatchMapping("/{salonId}/services")
    public ServiceInSalon updateService(@PathVariable long salonId, @RequestBody @Valid ServiceInSalon serviceInSalon) {
        return salonAdminService.updateService(salonId, serviceInSalon);
    }

    @DeleteMapping("/{salonId}/services/{serviceId}")
    public void deleteService(@PathVariable long salonId, @PathVariable long serviceId) {
        salonAdminService.deleteService(salonId, serviceId);
    }

    @GetMapping("/{salonId}/services")
    public List<ServiceInSalon> getServicesBySalonId(@PathVariable long salonId) {
        return salonAdminService.getServicesBySalonId(salonId);
    }

    @PostMapping("/{salonId}/masters")
    public Master addMaster(@PathVariable long salonId, @RequestBody @Valid Master master) {
        return salonAdminService.addMaster(salonId, master);
    }

    @PatchMapping("/{salonId}/masters")
    public Master updateMaster(@PathVariable long salonId, @RequestBody @Valid Master master) {
        return salonAdminService.updateMaster(salonId, master);
    }

    @DeleteMapping("/{salonId}/masters/{masterId}")
    public void deleteMaster(@PathVariable long salonId, @PathVariable long masterId) {
        salonAdminService.deleteMasterFromSalon(salonId, masterId);
    }

    @PostMapping("/{salonId}/masters/{masterId}/slots")
    public TimeSlot addTimeSlot(@PathVariable long salonId, @PathVariable long masterId,
                                @Valid @RequestBody TimeSlot timeSlot) {
        return salonAdminService.addTimeSlot(salonId, masterId, timeSlot);
    }

    @DeleteMapping("/bookings/{id}")
    public void deleteBooking(long id) {

    }

}
