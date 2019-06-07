package nl.inholland.configuration;

import nl.inholland.model.Login;
import nl.inholland.repository.LoginRepository;
import nl.inholland.repository.UserRepository;
import nl.inholland.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = LoginRepository.class)
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
                .antMatchers(HttpMethod.POST, "/accounts").hasRole("Employee")
                .antMatchers(HttpMethod.POST, "/users").hasRole("Employee")
                .antMatchers(HttpMethod.GET, "/accounts/{userId}")
                .access("@webSecurity.checkUserId(authentication,#userId)")
                .antMatchers(HttpMethod.GET, "/accounts").hasRole("Employee")
                .antMatchers(HttpMethod.GET, "/users").hasRole("Employee")
                .antMatchers(HttpMethod.GET, "/users/{userId}")
                .access("@webSecurity.checkUserId(authentication,#userId)")
                .antMatchers(HttpMethod.DELETE, "/accounts/**").hasRole("Employee")
                .antMatchers(HttpMethod.DELETE, "/users/**").hasRole("Employee")
                .antMatchers(HttpMethod.PUT, "/users/{userId}/**")
                .access("@webSecurity.checkUserId(authentication,#userId)")
                .anyRequest().authenticated()/*.and().httpBasic();*/
                .and()
                .formLogin().permitAll();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

   /* private PasswordEncoder getPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return true;
            }
        };
    }*/

}
