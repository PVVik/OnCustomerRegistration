package com.example.OnCustomerRegistration.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
public class ServiceInSalon {
    private long id;
    @NotBlank(message = "Название услуги обязательно к заполнению")
    private String name;
    @Positive(message = "Продолжительность процедуры должна быть положительной")
    private Long durationMinutes;
    @Positive(message = "Цена процедуры должна быть положительной")
    private BigDecimal price;
}
