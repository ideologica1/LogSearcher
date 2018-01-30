package ru.siblion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

@Configuration
@ComponentScan
public class SpringConfiguration {

    @Bean(destroyMethod = "")
    @SessionScope
    public DataSource dataSource() throws Exception {
        Context context = new InitialContext();
        DataSource dataSource = (DataSource) context.lookup("MyDataSource");
        return dataSource;
    }

}
