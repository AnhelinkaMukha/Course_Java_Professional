package ru.otus.processor;

import java.time.LocalDateTime;

public class SystemTimeProviderImpl implements TimeProvider{
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
