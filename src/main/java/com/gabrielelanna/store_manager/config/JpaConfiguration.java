package com.gabrielelanna.store_manager.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.gabrielelanna.store_manager.repository"})
@EntityScan(basePackages = {"com.gabrielelanna.store_manager.model.entity"})
public class JpaConfiguration {
}
