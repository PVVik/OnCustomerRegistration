package com.example.OnCustomerRegistration.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Builder
public class BeautySalon {
    private long id;
    @NotBlank(message = "Название салона обязательно к заполнению")
    private String name;
    @NotBlank(message = "Адрес салона обязателен к заполнению")
    private String address;
    private Set<Long> serviceIds;
    private Set<Long> masterIds;
}
