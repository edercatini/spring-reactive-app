package com.ec.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {

    @Value("classpath:h2/init.sql")
    private Resource initSql;

    private final R2dbcEntityTemplate template;

    @Override
    @SneakyThrows
    public void run(String... args) {
        String query = StreamUtils.copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);

        template.getDatabaseClient()
                .sql(query)
                .then()
                .subscribe();
    }
}