package com.welltech.waterAffair.security.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.welltech.waterAffair.security.service.MyAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource
	private MyAuthenticationProvider provider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/doc/**", "/css/**", "/img/**", "/images/**", "/email_templates/**", "/font-awesome/**",
						"/fonts/**", "/js/**", "/locales/**","/login/**")
				.permitAll().anyRequest().authenticated().and().formLogin().failureUrl("/error").loginPage("/login")
				.defaultSuccessUrl("/", true).permitAll().and().logout().permitAll().logoutUrl("/logout")
				.logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("aid").and().rememberMe();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// 将验证过程交给自定义验证工具
		auth.authenticationProvider(provider);
		//auth.inMemoryAuthentication().withUser("admin").password("111111").roles("USER");
	}

}
