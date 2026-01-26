package dev.juancastro.otel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setPatternParser(patternParser());
        configurer.addPathPrefix("/api", HandlerTypePredicate.forAnnotation(RestController.class));
    }

    private PathPatternParser patternParser() {
        PathPatternParser pathPatternParser = new PathPatternParser();
        pathPatternParser.setCaseSensitive(false);
        return pathPatternParser;
    }

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer
                .setDefaultVersion("1.0.0")
                .useRequestHeader("API-Version");
    }
}
