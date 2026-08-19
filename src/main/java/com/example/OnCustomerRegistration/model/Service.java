package com.example.OnCustomerRegistration.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
public class Service {
    private long id;
    @NotBlank(message = "Название услуги обязательно к заполнению")
    private String name;
    @Positive
    private int durationMinutes;
    @Positive
    private BigDecimal price;
}
