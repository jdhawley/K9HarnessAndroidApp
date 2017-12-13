package com.ua.k9backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to K 9 Backend.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

}
