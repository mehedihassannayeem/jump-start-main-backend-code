package com.jumpstart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jumpstart.security.CustomUserDetailsService;
import com.jumpstart.security.RestAuthenticationEntryPoint;
import com.jumpstart.security.TokenAuthenticationFilter;
import com.jumpstart.security.oauth2.CustomOAuth2UserService;
import com.jumpstart.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.jumpstart.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.jumpstart.security.oauth2.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	@Autowired
	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}

	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// public urls
	public static final String[] PUBLIC_URLS = { "/api/v1/auth/**", "/oauth2/**", "/", "/error", "/favicon.ico",
			"/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.jpeg", "/**/*.html", "/**/*.css", "/**/*.js",
			"/api/v1/contact-us/form"};

	// user urls
	public static final String[] USER_URLS = { "/api/v1/users/**" };

	// admin urls
	public static final String[] ADMIN_URLS = {"/api/v1/**"};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
		.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.exceptionHandling()
				.authenticationEntryPoint(new RestAuthenticationEntryPoint())
		.and()
			.authorizeRequests()
				.antMatchers(PUBLIC_URLS).permitAll()
				.antMatchers(USER_URLS).hasRole("NORMAL")
				.antMatchers(ADMIN_URLS).hasRole("ADMIN")
//				.antMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
					.anyRequest().authenticated()
		.and()
			.oauth2Login()
				.authorizationEndpoint()
					.baseUri("/oauth2/authorize")
				.authorizationRequestRepository(cookieAuthorizationRequestRepository())
			.and()
				.redirectionEndpoint()
					.baseUri("/oauth2/callback/*")
			.and()
				.userInfoEndpoint()
					.userService(customOAuth2UserService)
			.and()
				.successHandler(oAuth2AuthenticationSuccessHandler)
				.failureHandler(oAuth2AuthenticationFailureHandler);

		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
