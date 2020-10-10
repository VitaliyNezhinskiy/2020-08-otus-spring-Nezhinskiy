package ru.otus.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class IOServiceImpl implements IOService {
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public IOServiceImpl(@Value("#{ T(java.lang.System).in}") InputStream inputStream,
                         @Value("#{ T(java.lang.System).out}") OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @SneakyThrows
    @Override
    public String readMessage() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.readLine();
    }

    @Override
    public void writeMessage(String input) {
        PrintWriter writer = new PrintWriter(outputStream);
        writer.println(input);
        writer.flush();
    }

    @Override
    public String writeAndRead(String input) {
        writeMessage(input);
        return readMessage();
    }
}