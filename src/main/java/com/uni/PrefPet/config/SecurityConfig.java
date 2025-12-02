package com.uni.PrefPet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

//classse de configuração pra essa classe roda primeiro antes da aplicação
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	///////////////////////////////////////////////////////

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http    
		.csrf(AbstractHttpConfigurer::disable)
		.cors(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests((requests) -> requests
				//end points publicos
				.requestMatchers("/login").permitAll()
				.requestMatchers("/users/**").permitAll()
				.requestMatchers(HttpMethod.PUT,"/tutores/**").hasAnyAuthority("TUTOR","ADMIN")
				.requestMatchers(HttpMethod.DELETE,"/tutores").hasAnyAuthority("TUTOR","ADMIN")
				.requestMatchers(HttpMethod.GET,"/tutores/**").hasAnyAuthority("TUTOR", "ADMIN", "VETERINARIO",'ENTIDADE')
				.requestMatchers(HttpMethod.POST,"/animais/**").hasAnyAuthority("TUTOR", "ADMIN")
				.requestMatchers(HttpMethod.PUT,"/animais/**").hasAnyAuthority("TUTOR", "ADMIN")
				.requestMatchers(HttpMethod.GET,"/animais/**").hasAnyAuthority("TUTOR", "ADMIN", "VETERINARIO")
				.requestMatchers(HttpMethod.DELETE,"/animais/**").hasAnyAuthority("TUTOR", "ADMIN")
				.requestMatchers(HttpMethod.GET,"/contatos/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/notificacoes/{id}").hasAnyAuthority("TUTOR","ADMIN")
				.requestMatchers(HttpMethod.GET, "/emergencia/**")
				.anyRequest().authenticated())
		.authenticationProvider(authenticationProvider)
				//aqui definimos a forma de autenticação
		.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
		.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}
	///////////////////////////////////////////////////////
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOriginPatterns(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION,HttpHeaders.CONTENT_TYPE,HttpHeaders.ACCEPT));
		config.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(),HttpMethod.POST.name(),HttpMethod.PUT.name(),HttpMethod.DELETE.name()));
		config.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		bean.setOrder(-102);
		return bean;
	}

}
