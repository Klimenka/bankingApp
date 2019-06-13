package nl.inholland.configuration;

import nl.inholland.repository.LoginRepository;
import nl.inholland.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.StaticAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.net.URI;

/**
 * This class is to handle the authentication using the user Details
 * model and service. also give authorities based on the endpoint(uri)
 * and the user ID (who is logged in) and encrypt all passwords.
 */
@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = LoginRepository.class) //inject jpa repo
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginService loginService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/accounts").hasAnyRole("Employee", "Owner")
                .antMatchers(HttpMethod.POST, "/users").hasAnyRole("Employee", "Owner")
                .antMatchers(HttpMethod.GET, "/accounts/{userId}")
                .access("@webSecurity.checkUserId(authentication,#userId)") // give authority based on user ID
                .antMatchers(HttpMethod.GET, "/accounts").hasAnyRole("Employee", "Owner")
                .antMatchers(HttpMethod.GET, "/users").hasAnyRole("Employee", "Owner")
                .antMatchers(HttpMethod.GET, "/users/{userId}")
                .access("@webSecurity.checkUserId(authentication,#userId)")
                .antMatchers(HttpMethod.DELETE, "/accounts/**").hasAnyRole("Employee", "Owner")
                .antMatchers(HttpMethod.DELETE, "/users/**").hasAnyRole("Employee", "Owner")
                .antMatchers(HttpMethod.PUT, "/users/{userId}/**")
                .access("@webSecurity.checkUserId(authentication,#userId)")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
}
