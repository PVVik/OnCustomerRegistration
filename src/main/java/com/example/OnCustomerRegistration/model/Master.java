package com.example.OnCustomerRegistration.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Master {
    private long id;
    @NotBlank(message = "Имя специалиста обязательно к заполнению")
    private String fullName;
    private List<Long> salonIds = new ArrayList<>();
}
