package ru.otus.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.YamlProps;

import java.util.Locale;

@Service
public class MessageSourceWrapper {
    private final MessageSource messageSource;
    private final Locale locale;

    @Autowired
    public MessageSourceWrapper(MessageSource messageSource, YamlProps yamlProps) {
        this.messageSource = messageSource;
        locale = yamlProps.getLocale();
    }

    public String getMessage(String message) {
        return messageSource.getMessage(message, new String[0], locale);
    }

    public String getMessage(String message, String ...params) {
        return messageSource.getMessage(message, params, locale);
    }
}