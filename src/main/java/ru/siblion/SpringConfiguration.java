package ru.siblion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
@ComponentScan
public class SpringConfiguration {

   @Bean(destroyMethod = "", name = "dataSource")
   public DataSource dataSource() {
       JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
       return jndiDataSourceLookup.getDataSource("SpringDB");
   }
}
