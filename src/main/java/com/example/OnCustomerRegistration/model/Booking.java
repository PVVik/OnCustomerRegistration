package com.example.OnCustomerRegistration.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.Instant;

@Data
@Builder
public class Booking {
    private long id;
    private long slotId;
    @NotBlank(message = "Имя клиента обязательно к заполнению")
    private String clientName;
    @NotBlank(message = "Номер телефона обязателен к заполнению")
    @Pattern(regexp = "^(?:\\+?7|8)?[\\s\\-()]*\\d{10}$", message = "Неверный формат номера телефона")
    private String phone;
    private BookingStatus status;
    private Instant createdAt;
}
