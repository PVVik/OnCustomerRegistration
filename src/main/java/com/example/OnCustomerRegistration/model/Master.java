package com.example.OnCustomerRegistration.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class Master {
    private long id;
    @NotBlank(message = "Имя специалиста обязательно к заполнению")
    private String fullName;
    @NotBlank(message = "Номер телефона обязателен к заполнению")
    @Pattern(regexp = "^(?:\\+?7|8)?[\\s\\-()]*\\d{10}$", message = "Неверный формат номера телефона")
    private String phoneNumber;
    private Set<Long> salonIds;
    private Map<Long, List<TimeSlot>> timeSlots;
}
