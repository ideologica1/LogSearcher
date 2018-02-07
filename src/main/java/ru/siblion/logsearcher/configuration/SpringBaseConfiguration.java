package ru.siblion.logsearcher.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Configuration
@ComponentScan
public class SpringBaseConfiguration {

   @Bean(destroyMethod = "", name = "dataSource")
   public DataSource dataSource() {
       JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
       return jndiDataSourceLookup.getDataSource("SpringDB");
   }
}
