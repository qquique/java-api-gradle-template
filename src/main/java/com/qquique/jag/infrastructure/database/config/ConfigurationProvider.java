package com.qquique.jag.infrastructure.database.config;
import org.hibernate.cfg.Configuration;

public interface ConfigurationProvider {
    Configuration getConfiguration();
}
