package main.java.config;

import main.java.authentication.DaoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(1)
public class AdminSecurityConfiguration extends WebSecurityConfigurerAdapter
{
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private DaoUserDetailsService userDetailsService;

    @Autowired
    public AdminSecurityConfiguration(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            DaoUserDetailsService userDetailsService
    ) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
        .authorizeRequests()
        .antMatchers("/admin/register").permitAll()
        .antMatchers("/admin/**").access("hasRole('ADMIN')")
        .and()
            .formLogin()
            .loginPage("/admin/login")
            .permitAll()
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/admin/welcome")
            .failureForwardUrl("/accessDenied")
            .usernameParameter("email")
            .passwordParameter("password")
        .and()
        .exceptionHandling().accessDeniedPage("/accessDenied");
    }
}
