package io.bootify.book_shop.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("io.bootify.book_shop.domain")
@EnableJpaRepositories("io.bootify.book_shop.repos")
@EnableTransactionManagement
public class DomainConfig {
}
