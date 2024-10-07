package com.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.spring.security.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
@Autowired
	private MyUserDetailsService userDetailsService;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//disabel csrf
		http.csrf(customizer -> customizer.disable());
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/login", "/register").permitAll() // allow access to these pages without authentication
                .anyRequest().authenticated() // all other requests require authentication
            );
        http.formLogin(form -> form
                .loginPage("/login")             // URL of the login page
                .loginProcessingUrl("/login")    // URL where login form is submitted
                .defaultSuccessUrl("/")          // Redirect after successful login
                .permitAll()                     // Allow everyone to access the login page
            );
        http.logout(logout -> logout
                .logoutUrl("/logout") // URL to submit the logout POST request
                .logoutSuccessUrl("/login?logout") // Redirect to login page after logout
                .invalidateHttpSession(true) // Invalidate the session
                .deleteCookies("JSESSIONID") // Clear the JSESSIONID cookie
                .permitAll()
            );
        //for restcontrollers for postman
		http.httpBasic(Customizer.withDefaults());
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.maximumSessions(1)
		  	    .expiredUrl("/login?expired=true"));
		return http.build();	
	}
//	@Bean 
//	public UserDetailsService userDetailService() {
//		UserDetails user1 = User.withDefaultPasswordEncoder().username("admin").password("admin").build();
//		return new InMemoryUserDetailsManager(user1);
//	}
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
}
