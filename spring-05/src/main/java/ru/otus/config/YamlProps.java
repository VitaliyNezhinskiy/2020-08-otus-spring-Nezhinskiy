package ru.otus.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "questions")
@Data
public class YamlProps {
   private Locale locale;
}
