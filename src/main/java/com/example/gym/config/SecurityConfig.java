package com.example.gym.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import com.example.gym.config.UserDetailsServiceImp ;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	UserDetailsServiceImp userDetailsServiceImp;
	
	@Autowired
	JwtAuthorizationFilter jwtAuthorizationFilter;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity,AuthenticationManager authenticationManager ) throws Exception {
		
		
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenUtil);
	        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
	        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

		httpSecurity
		.csrf((csrf) -> csrf
                .disable())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests((requests) -> requests
			.requestMatchers("/gym/profiles/home").permitAll()
			.requestMatchers("/gym/profiles/create-trainee").permitAll()
			.requestMatchers("/gym/profiles/create-trainer").permitAll()
			.anyRequest().authenticated()
				 );
		
		httpSecurity.addFilter(jwtAuthenticationFilter);
		httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

	return httpSecurity.build();
}
	
	//@Bean
	//public UserDetailsService userDetailsService() {
		
		//InMemoryUserDetailsManager manager= new InMemoryUserDetailsManager();
		//manager.createUser(User.withUsername("user")
			//.password("1234")
			//.roles()
			//.build());
		
		//return manager;
		
	//}
	
	//encriptar contraseña:
	
	@Bean
   public PasswordEncoder passwordEncoder() {
        
        return new BCryptPasswordEncoder();
    }
	
	
	@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsServiceImp)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }
}
