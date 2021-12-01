package com.fpII.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private BCryptPasswordEncoder codificador;
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/","/index","/css/**","/js/**","/img/**").permitAll()
			.antMatchers("/principal").hasAnyRole("Administrador","Detective","Vigilante")
			.antMatchers("/menu_personas").hasAnyRole("Administrador","Detective","Vigilante")
			.antMatchers("/menu_bancos").hasAnyRole("Administrador","Detective","Vigilante")
			.antMatchers("/menu_delincuencia").hasAnyRole("Administrador","Detective")
				.anyRequest().authenticated()
				.and()
			.formLogin()
		        .loginPage("/login")
		        .permitAll()
		        .defaultSuccessUrl("/principal")
		        .failureUrl("/login?error=true")
		        .usernameParameter("usuario")
		        .passwordParameter("contrasenia")
		        .and()
	        .logout()
		        .permitAll()
		        .logoutSuccessUrl("/login?logout");
	}
	
	@Autowired
	public void configuracionSeguridad(AuthenticationManagerBuilder aut) throws Exception {
		aut.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(codificador)
		.usersByUsernameQuery("SELECT usuario as username, contrasenia as password, estado as enabled FROM usuarios WHERE usuario=?")
		.authoritiesByUsernameQuery("SELECT a.usuario as username, b.descripcion as rol FROM usuarios a INNER JOIN tipo_usuarios b ON a.id_tipo=b.id WHERE a.usuario=?")
		.rolePrefix("ROLE_");//es necesario para que funcione spring security
	}
}