package main.java.config;

import main.java.authentication.DaoUserDetailsService;
import main.java.authentication.TicketAppAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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

    @Bean
    public AuthenticationSuccessHandler ticketAppAuthenticationSuccessHandler() {
        return new TicketAppAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
        .authorizeRequests()
            .antMatchers("/shop/register").permitAll()
            .antMatchers("/shop/login").permitAll()
            .antMatchers("/admin/register").permitAll()
            .antMatchers("/logout").permitAll()
            .antMatchers("/shop/**").authenticated().anyRequest().hasRole("USER")
            .antMatchers("/admin/**").authenticated().anyRequest().hasRole("ADMIN")
        .and()
        .formLogin()
                .loginPage("/shop/login")
                .permitAll()
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/shop/welcome")
                .successHandler(ticketAppAuthenticationSuccessHandler())
                .failureForwardUrl("/accessDenied")
                .usernameParameter("email")
                .passwordParameter("password")
        .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/shop/login")
        .and()
        .exceptionHandling().accessDeniedPage("/accessDenied");
    }
}
