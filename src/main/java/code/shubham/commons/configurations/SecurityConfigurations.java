package code.shubham.commons.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

	// private final KeycloakLogoutHandler keycloakLogoutHandler;
	//
	// public SecurityConfigurations(final KeycloakLogoutHandler keycloakLogoutHandler) {
	// this.keycloakLogoutHandler = keycloakLogoutHandler;
	// }
	//
	// @Bean
	// protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
	// return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	// }
	//
	// @Order(1)
	// @Bean
	// public SecurityFilterChain clientFilterChain(final HttpSecurity http) throws
	// Exception
	//// {
	// http.authorizeRequests()
	// .requestMatchers(new AntPathRequestMatcher("/"))
	// .permitAll()
	// .anyRequest()
	// .authenticated();
	// http.oauth2Login(a -> {
	// }).logout(a ->
	// a.addLogoutHandler(this.keycloakLogoutHandler).logoutSuccessUrl("/"));
	// return http.build();
	// }
	//
	// @Order(2)
	// @Bean
	// public SecurityFilterChain resourceServerFilterChain(final HttpSecurity http)
	// throws
	//// Exception {
	// http.authorizeRequests()
	// .requestMatchers(new AntPathRequestMatcher("/secure*"))
	// .hasRole("template-service-spring-boot-access-role")
	// .anyRequest()
	// .authenticated();
	// http.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
	// return http.build();
	// }
	//
	// @Bean
	// public AuthenticationManager authenticationManager(final HttpSecurity http) throws
	//// Exception {
	// return http.getSharedObject(AuthenticationManagerBuilder.class).build();
	// }

	/*
	 * OAuth2
	 */
	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(a -> a.requestMatchers("/**").fullyAuthenticated())
			.sessionManagement(a -> a.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.oauth2ResourceServer(a -> a.jwt(Customizer.withDefaults()))
			.cors(Customizer.withDefaults())
			.csrf(a -> a.disable())
			.build();
	}

}