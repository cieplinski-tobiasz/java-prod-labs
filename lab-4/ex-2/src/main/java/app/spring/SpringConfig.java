package app.spring;

import app.spring.conditions.JsonCondition;
import app.spring.conditions.XmlCondition;
import app.spring.conditions.YamlCondition;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import serializers.TransactionDtoFactory;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class SpringConfig {
    @Bean
    public ThreadLocalRandom threadLocalRandom() {
        return ThreadLocalRandom.current();
    }

    @Bean
    public TransactionDtoFactory transactionDtoFactory() {
        return new TransactionDtoFactory(dateTimeFormatter());
    }

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }

    @Bean
    @Conditional(XmlCondition.class)
    public XmlMapper xmlMapper() {
        XmlMapper xmlMapper = new XmlMapper();
        addCustomDateTimeSerializationToMapper(xmlMapper);
        return xmlMapper;
    }

    @Bean
    @Conditional(YamlCondition.class)
    public YAMLMapper yamlMapper() {
        YAMLMapper yamlMapper = new YAMLMapper();
        addCustomDateTimeSerializationToMapper(yamlMapper);
        return yamlMapper;
    }

    @Bean
    @Conditional(JsonCondition.class)
    public ObjectMapper jsonMapper() {
        ObjectMapper jsonMapper = new ObjectMapper();
        addCustomDateTimeSerializationToMapper(jsonMapper);
        return jsonMapper;
    }

    private void addCustomDateTimeSerializationToMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
