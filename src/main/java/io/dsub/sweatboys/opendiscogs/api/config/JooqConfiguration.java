package io.dsub.sweatboys.opendiscogs.api.config;

import io.r2dbc.spi.ConnectionFactory;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.connection.TransactionAwareConnectionFactoryProxy;

@Configuration(proxyBeanMethods = false)
public class JooqConfiguration {

  @Bean
  public DSLContext dslContext(@Autowired ConnectionFactory connectionFactory) {
    return DSL.using(
        new TransactionAwareConnectionFactoryProxy(connectionFactory),
        SQLDialect.POSTGRES
    );
  }
}
