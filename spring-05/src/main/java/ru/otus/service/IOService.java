package ru.otus.service;

public interface IOService {
    String readMessage();

    void writeMessage(String input);

    String writeAndRead(String input);
}