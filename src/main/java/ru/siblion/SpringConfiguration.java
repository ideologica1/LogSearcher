package ru.siblion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ru.siblion.service.entity.request.SignificantDateInterval;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

@Configuration
@ComponentScan
public class SpringConfiguration {

    @Bean
    public SignificantDateInterval significantDateInterval() {
        return new SignificantDateInterval();
    }

 /*   @Bean
    public DataSource dataSource() throws Exception {
        Context context = new InitialContext();
        DataSource dataSource = (DataSource) context.lookup("MyDataSource");
        return dataSource;
    } */

}
