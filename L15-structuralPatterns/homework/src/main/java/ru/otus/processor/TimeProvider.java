package ru.otus.processor;

import java.time.LocalDateTime;

public interface TimeProvider {
    LocalDateTime now();
}
