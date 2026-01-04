package ru.otus.listener.homework;

import ru.otus.model.Message;

record Memento(Message message) {
    Memento(Message message) {
        this.message = new Message(message);
    }

    @Override
    public Message message() {
        return message;
    }
}

