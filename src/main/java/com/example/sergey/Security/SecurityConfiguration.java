package com.example.sergey.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import com.example.sergey.Service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Autowired
    private AccessDeniedHandler accessDeniedHandler;
	
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService);
    } 
    
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
              .csrf().disable()
              .authorizeRequests()
              .antMatchers("/superadmin/*", "/superadmin/**").hasRole("SUPERADMIN")
              .antMatchers("/admin/*","/admin/**","/orders/orderDelete").hasAnyRole( "ADMIN", "SUPERADMIN")
              .antMatchers("/orders/createOrder","/orders/orderEdit","/orders/orderCopy").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
              .antMatchers("/").hasAnyRole("USER", "READER", "ADMIN", "SUPERADMIN")
              .and().formLogin()
              .loginPage("/login")
              .defaultSuccessUrl("/")
              .and()
              .logout()
              .permitAll()
              .and()
              .logout().deleteCookies("JSESSIONID")
              .and()
              .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
              .and().rememberMe().userDetailsService(this.userDetailsService).key("webservice").alwaysRemember(true)
              .and()
              .sessionManagement()
              .maximumSessions(1)
              .expiredUrl("/login?invalid-session=true");
    }
    
}
