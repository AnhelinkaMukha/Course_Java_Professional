package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class ProcessorExceptionOnEvenSecond implements Processor{

    private final Supplier<LocalDateTime> timeProvider;

    public ProcessorExceptionOnEvenSecond(Supplier<LocalDateTime> timeProvider) {
        this.timeProvider = timeProvider;
    }

    @Override
    public Message process(Message message) {
        int second = timeProvider.get().getSecond();
        if (second % 2 == 0) {
            throw new RuntimeException("Even second exception: " + second);
        }
        return message;
    }
}
