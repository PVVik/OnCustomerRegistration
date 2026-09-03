package com.example.OnCustomerRegistration.service;

import com.example.OnCustomerRegistration.exception.NotFoundException;
import com.example.OnCustomerRegistration.exception.ValidationException;
import com.example.OnCustomerRegistration.model.TimeSlot;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
public class Validator {

    public static void checkNotNull(Object value, String fieldName) {
        if (value == null) {
            log.warn("Получили исключение, так как поле \"{}\" пустое", fieldName);
            throw new ValidationException(String.format("Значение поля %s не может быть пустым", fieldName));
        }
    }

    public static <K, V> void checkIdExists(Map<K, V> map, K key) {
        if (!map.containsKey(key)) {
            log.warn("Получили исключение, так как элемента с id {} не существует", key);
            throw new NotFoundException(String.format("Элемент с id %s не был найден", key));
        }
    }

    public static void validEndAfterStart(LocalDateTime start, LocalDateTime end) {
        if (start.equals(end) || start.isAfter(end)) {
            log.warn("Получили исключение, так как введенная дата старта позднее даты окончания");
            throw new ValidationException("Дата старта должна быть раньше даты окончания");
        }
    }

    public static void checkTimeSlotNotOverlapWith(TimeSlot newTimeSlot, Map<Long, List<TimeSlot>> masterTimeSlots) {
        boolean isOverlap = masterTimeSlots.values().stream()
                .flatMap(List::stream)
                .anyMatch(slot -> slot.overlapsWith(newTimeSlot));

        if (isOverlap) {
            log.warn("Получили исключение, так как окно пересекается с другим");
            throw new ValidationException("Окошко пересекается с другим, создание невозможно");
        }
    }
}
