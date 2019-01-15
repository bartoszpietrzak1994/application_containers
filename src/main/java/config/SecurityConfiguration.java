package main.java.config;

import main.java.authentication.DaoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private DaoUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(
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
        .antMatchers("/**/admin/**").access("hasRole('ROLE_ADMIN')")
        .antMatchers("/**/shop/**").permitAll()
        .antMatchers("/shop/register").permitAll()
        .and()
        .formLogin()
                .loginPage("/shop/login")
                .permitAll()
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/shop/welcome")
                .failureForwardUrl("/accessDenied")
                .usernameParameter("email")
                .passwordParameter("password")
        .and()
        .exceptionHandling().accessDeniedPage("/accessDenied");
    }
}
