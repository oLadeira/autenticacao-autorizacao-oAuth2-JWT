package com.lucasladeira.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	
	private static final String[] PUBLIC_MATCHERS = { //array com recursos publicos
		"/h2-console/**",
		"/api/user/save"
	};
	
	public static final String[] USER_MATCHERS = { //array com recursos ROLE_USER
			
	};
	
	public static final String[] ADMIN_MATCHERS = { //array com recursos ROLE_ADMIN
			"/api/role/save",
			"/api/users",
			"/api/user/**",
			"/api/role/addtouser"
	};
	
	//configuracao de autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	
	//configuracao de autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.httpBasic() // utilizando o mecanismo Basic Auth
		.and()
		.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS).permitAll() //permitindo acesso a este endereco por qualquer um
			.antMatchers(USER_MATCHERS).access("hasAnyAuthority('USER', 'ADMIN')") //exige usuario autenticado
			.antMatchers(ADMIN_MATCHERS).access("hasAnyAuthority('ADMIN')")
		.and()
		.csrf().disable()
		.headers().frameOptions().disable()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //gerenciamento de sessão SEM ESTADO (api's não usam sessões)
	}
	
	
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable();
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		http.authorizeRequests().anyRequest().permitAll();
//		http.addFilter(new CustomAuthenticationFilter());
//	}

}
