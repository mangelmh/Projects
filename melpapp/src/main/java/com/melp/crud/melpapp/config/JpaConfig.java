package com.melp.crud.melpapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.melp.crud.melpapp.repository")
public class JpaConfig {

}
