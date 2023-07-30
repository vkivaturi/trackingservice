package org.digit.tracking;

import com.fasterxml.jackson.databind.Module;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

@SpringBootApplication(
    nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
@ComponentScan(
    basePackages = { "org.digit"},
    nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
public class TrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackingApplication.class, args);
    }
    @Bean(name = "org.digit.tracking.TrackingApplication.jsonNullableModule")
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }

}