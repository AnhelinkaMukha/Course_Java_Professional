package ru.otus.listener.homework;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    private final Deque<Memento> stack = new ArrayDeque<>();
    @Override
    public void onUpdated(Message msg) {
        if(msg != null) {
            stack.push(new Memento(msg));
        }else{
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(stack.stream()
                .map(Memento::message)
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Message not found: " + id)));
    }
}
