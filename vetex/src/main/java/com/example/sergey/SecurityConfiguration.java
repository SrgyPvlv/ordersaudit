package com.example.sergey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
    private AccessDeniedHandler accessDeniedHandler;

	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;}
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                   .password(this.passwordEncoder().encode("rbs123#"))
                   .authorities("ROLE_USER")
                .and()
                .withUser("admin")
                   .password(this.passwordEncoder().encode("mts2001@"))
                   .authorities("ROLE_ADMIN");
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
              .antMatchers("/admin/*").hasRole( "ADMIN")
              .antMatchers("/").hasAnyRole("USER", "ADMIN")
              .and().formLogin()
              .defaultSuccessUrl("/priceItems")
              .and()
              .logout()
              .permitAll()
              .and()
              .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }
}
