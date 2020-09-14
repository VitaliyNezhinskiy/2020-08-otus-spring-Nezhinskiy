package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Класс IOService: ")
public class IOServiceImplTest {
    private final InputStream inputStream = new ByteArrayInputStream(
            String.join(System.lineSeparator(), List.of("1", "2", "3"))
                    .getBytes(StandardCharsets.UTF_8));

    @Test
    @DisplayName(" корректно работает с входящим потоком")
    public void shouldHaveCorrectReadMessage() {
        IOService ioService = new IOServiceImpl(inputStream,
                Mockito.mock(OutputStream.class));
        assertAll("ioService",
                () -> assertEquals("1", ioService.readMessage()),
                () -> assertEquals("2", ioService.readMessage()),
                () -> assertEquals("3", ioService.readMessage())
        );
    }

    @Test
    @DisplayName(" корректно работает с исходящим потоком")
    public void shouldHaveCorrectWriteMessage() throws IOException {
        List<String> inputList = List.of("ola1", "ola2", "ola3");
        final String pathname = "src/test/resources/test_file.txt";
        try (OutputStream outputStream = new FileOutputStream(new File(pathname))
        ) {
            IOService ioService2 = new IOServiceImpl(Mockito.mock(InputStream.class), outputStream);
            for (String str : inputList) {
                ioService2.writeMessage(str);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        List<String> listFromFile = Files.readAllLines(Path.of(pathname));

        assertEquals(inputList, listFromFile);
    }
}