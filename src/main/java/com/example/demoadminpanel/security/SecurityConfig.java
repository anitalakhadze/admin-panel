package com.example.demoadminpanel.security;

import com.example.demoadminpanel.user.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/**")
//                .permitAll()
                .anyRequest()
                .authenticated().and()
                .formLogin()
                .permitAll().and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true).permitAll();
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
////                .loginPage("/login")
//                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}
