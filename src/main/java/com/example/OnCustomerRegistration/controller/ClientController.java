package com.example.OnCustomerRegistration.controller;

import com.example.OnCustomerRegistration.model.BeautySalon;
import com.example.OnCustomerRegistration.model.Booking;
import com.example.OnCustomerRegistration.model.ServiceInSalon;
import com.example.OnCustomerRegistration.model.TimeSlot;
import com.example.OnCustomerRegistration.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/salons")
    public List<BeautySalon> getSalons() {
        return null;
    }

    @GetMapping("/salons/{salonId}/services")
    public List<ServiceInSalon> getServices(@PathVariable long salonId) {
        return new ArrayList<>();
    }

    @GetMapping("/salons/{salonId}/slots/{date}")
    public List<TimeSlot> getSlots(@PathVariable long salonId, @PathVariable String date) {
        return new ArrayList<>();
    }

    @PostMapping("/bookings")
    public Booking addBooking(@RequestBody Booking booking) {
        return booking;
    }

    @DeleteMapping("/bookings/{id}")
    public void deleteBooking(@PathVariable int id) {

    }
}
