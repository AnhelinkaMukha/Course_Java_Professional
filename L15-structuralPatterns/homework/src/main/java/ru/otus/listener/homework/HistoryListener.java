package ru.otus.listener.homework;

import java.util.*;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> map = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        if(msg != null) {
            map.put(msg.getId(), new Message(msg));
        }else{
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(map.get(id));
    }
}
