package service;

import lombok.SneakyThrows;

import java.io.*;

public class IOServiceImpl implements IOService {
    public static BufferedReader reader;
    public static PrintWriter writer;

    public IOServiceImpl(InputStream inputStream, OutputStream outputStream) {
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
}