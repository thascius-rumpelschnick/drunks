package org.kappa.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

  private final UserDetailsService userDetailsService;


  public WebSecurityConfig(final UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }


  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }


  // @Bean
  // public JwtAuthenticationProvider jwtAuthenticationProvider() {
  //   return new JwtAuthenticationProvider();
  // }


  @Bean
  public AuthenticationManager customAuthenticationManager(final HttpSecurity http) throws Exception {
    final AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject
        (AuthenticationManagerBuilder.class);

    authenticationManagerBuilder
        // .authenticationProvider(this.jwtAuthenticationProvider())
        .userDetailsService(this.userDetailsService)
        .passwordEncoder(this.bCryptPasswordEncoder());

    return authenticationManagerBuilder.build();
  }


  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults())
        .authorizeHttpRequests(
            requests -> requests.requestMatchers("/api/v1/user/register", "/swagger-ui/**", "/v3/api-docs/**").permitAll().anyRequest().authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }

}
