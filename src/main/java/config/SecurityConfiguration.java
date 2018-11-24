package main.java.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    private DataSource dataSource;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${spring.queries.users_query}")
    private String usersQuery;

    @Value("${spring.queries.roles_query}")
    private String rolesQuery;

    public SecurityConfiguration(DataSource dataSource, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.dataSource = dataSource;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception
    {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(this.usersQuery)
                .authoritiesByUsernameQuery(this.rolesQuery)
                .passwordEncoder(bCryptPasswordEncoder)
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable();

        http.authorizeRequests()

        .antMatchers("/welcome").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
        .and()
        .formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/welcome").failureForwardUrl("/accessDenied")
        .usernameParameter("email").passwordParameter("password")
        .and()
        .exceptionHandling().accessDeniedPage("/accessDenied");
    }
}
