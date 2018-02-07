package ru.siblion.logsearcher.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import ru.siblion.logsearcher.service.authentication.AuthenticationFilter;
import ru.siblion.logsearcher.service.authentication.CustomAuthenticationSuccessHandler;
import ru.siblion.logsearcher.service.authentication.CustomLogoutSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) {

        try {

            auth.jdbcAuthentication().dataSource(dataSource)
                    .usersByUsernameQuery(
                            "select username,password, enabled from users where username=?")
                    .authoritiesByUsernameQuery(
                            "select username, role from user_roles where username=?");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        try {
            http.
                    addFilterAfter(new AuthenticationFilter(), SecurityContextPersistenceFilter.class).authorizeRequests()
                    .antMatchers("/form").access("hasRole('ROLE_ADMIN')")
                    .and()
                    .formLogin().loginPage("/login").failureUrl("/login?error").loginProcessingUrl("/login").successHandler(new CustomAuthenticationSuccessHandler())
                    .usernameParameter("username").passwordParameter("password")
                    .and()
                    .logout().logoutSuccessHandler(new CustomLogoutSuccessHandler())
                    .and()
                    .exceptionHandling().accessDeniedPage("/403")
                    .and()
                    .csrf().disable();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SecurityConfiguration() {
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
