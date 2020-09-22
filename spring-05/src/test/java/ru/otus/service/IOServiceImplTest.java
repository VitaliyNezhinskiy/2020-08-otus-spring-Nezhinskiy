package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Класс IOService: ")
@SpringBootTest
public class IOServiceImplTest {
    private final static InputStream INPUT_STREAM = new ByteArrayInputStream(
            String.join(System.lineSeparator(), List.of("1"))
                    .getBytes(StandardCharsets.UTF_8));

    private final static String PATHNAME = "src/test/resources/test_file.txt";

    private final static List<String> INPUT_LIST = List.of("ola1", "ola2", "ola3");

    @Autowired
    private IOService ioService;

    @Configuration
    static class Conf {
        @Bean
        IOService ioService() {
            IOService ioService = null;
            try (OutputStream outputStream = new FileOutputStream(new File(PATHNAME))
            ) {
                ioService = new IOServiceImpl(INPUT_STREAM, outputStream);
                for (String str : INPUT_LIST) {
                    ioService.writeMessage(str);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return ioService;
        }
    }

    @Test
    @DisplayName(" корректно работает с входящим потоком")
    public void shouldHaveCorrectReadMessage() {
        assertEquals("1", ioService.readMessage());
    }

    @Test
    @DisplayName(" корректно работает с исходящим потоком")
    public void shouldHaveCorrectWriteMessage() throws IOException {
        List<String> listFromFile = Files.readAllLines(Path.of(PATHNAME));
        assertEquals(INPUT_LIST, listFromFile);
    }
}