package com.ideapool.springboot.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ideapool.springboot.app.auth.handler.LoginSuccessHandler;
import com.ideapool.springboot.app.models.service.JpaUserDetailsService;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig {
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JpaUserDetailsService userDetailsService; 

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authz -> authz
				.requestMatchers("/", "/list", "/css/**", "/js/**", "/images/**", "/login").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin(login -> login
				.successHandler(successHandler)
				.loginPage("/login")
				.permitAll()
			)
			.logout(logout -> logout
				.logoutSuccessUrl("/login?logout")
				.permitAll()
			)
			.exceptionHandling(exception -> exception
				.accessDeniedPage("/error_403")
			);

		return http.build();
	}
	
	@Autowired
	public void configureAuthenticationManager(AuthenticationManagerBuilder build) throws Exception {
		build.userDetailsService(userDetailsService)
			 .passwordEncoder(passwordEncoder);
	}
	
	/*
	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		
		authBuilder.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(passwordEncoder)
			.usersByUsernameQuery("select username, password, enabled from users where username=?")
			.authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?");
		
		return authBuilder.build();
	}	@Autowired
	public void configureAuthenticationManager(AuthenticationManagerBuilder build) throws Exception {
		build.userDetailsService(userDetailsService)
			 .passwordEncoder(passwordEncoder);
	}*/
}