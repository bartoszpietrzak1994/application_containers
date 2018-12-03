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
        http.authorizeRequests()

        .antMatchers("/welcome").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
        .antMatchers("/register").permitAll()
        .and()
        .formLogin()
                .loginPage("/user-login")
                .permitAll()
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/welcome")
                .failureForwardUrl("/accessDenied")
                .usernameParameter("email")
                .passwordParameter("password");
//        .and()
//        .exceptionHandling().accessDeniedPage("/accessDenied");
    }
}
