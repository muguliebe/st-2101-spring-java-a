package com.example.fwk.config.db;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import javax.sql.DataSource;
import java.io.IOException;

@Profile("local")
@Configuration
public class EmbeddedDb {

    @Primary
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean(name = "embeddedPrimaryDb")
    public DataSource embeddedPrimaryDb() throws IOException {
        return EmbeddedPostgres.builder()
                .setServerConfig("timezone", "Asia/Seoul")
                .setPort(5432)
                .start().getPostgresDatabase();
    }

}
