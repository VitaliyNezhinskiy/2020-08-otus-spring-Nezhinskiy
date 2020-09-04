package ru.otus.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class IOServiceImpl implements IOService {
    public static BufferedReader reader;
    public static PrintWriter writer;

    public IOServiceImpl(@Value("#{ T(java.lang.System).in}") InputStream inputStream,
                         @Value("#{ T(java.lang.System).out}") OutputStream outputStream) {
        reader = new BufferedReader(new InputStreamReader(inputStream));
        writer = new PrintWriter(new OutputStreamWriter(outputStream));
    }

    @SneakyThrows
    @Override
    public String readMessage() {
        return reader.readLine();
    }

    @Override
    public void writeMessage(String input) {
        writer.println(input);
        writer.flush();
    }

    @Override
    public String writeAndRead(String input) {
        writeMessage(input);
        return readMessage();
    }
}