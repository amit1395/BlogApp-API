package com.fitstam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fitstam.security.CustomUserDetailService;
import com.fitstam.security.JwtAuthenticationEntryPoint;
import com.fitstam.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private static final String[] PUBLIC_URLS= {"/api/v1/auth/**","/v3/api-docs"
			,"/v2/api-docs"
			,"/swagger-ui/**"
			,"/swagger-resources/**",
			"/webjars/**"};
	
	@Autowired
	private CustomUserDetailService userDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint authEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter authFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//below code is for basic authentiction
		/*
		 * http .csrf() .disable() .authorizeHttpRequests() .anyRequest()
		 * .authenticated() .and() .httpBasic();
		 */
		
		//below code is for JWT authentication
		http
		.csrf()
		.disable()
		.authorizeHttpRequests()
		//.antMatchers("/api/v1/auth/login").permitAll()
		//.antMatchers("/api/v1/auth/**").permitAll()
		//.antMatchers("/v3/api-docs").permitAll()
		.antMatchers(PUBLIC_URLS).permitAll()
		.antMatchers(HttpMethod.GET).permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(this.authEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http
		.addFilterBefore(this.authFilter,UsernamePasswordAuthenticationFilter.class);
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailService).passwordEncoder(passwordEncoder());
	}
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	public FilterRegistrationBean corsFilter(){
		UrlBasedCorsConfigurationSource corsSource= new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfig= new CorsConfiguration();
		corsConfig.setAllowCredentials(true);
		corsConfig.addAllowedOriginPattern("*");
		corsConfig.addAllowedHeader("Authorization");
		corsConfig.addAllowedHeader("Content-Type");
		corsConfig.addAllowedHeader("Accept");
		corsConfig.addAllowedMethod("POST");
		corsConfig.addAllowedMethod("GET");
		corsConfig.addAllowedMethod("DELETE");
		corsConfig.addAllowedMethod("PUT");
		corsConfig.addAllowedMethod("OPTIONS");
		corsConfig.setMaxAge(3600l);

		corsSource.registerCorsConfiguration("/**",corsConfig);

		FilterRegistrationBean corsBean = new FilterRegistrationBean(new CorsFilter(corsSource));
		return corsBean;
	}
	

}
