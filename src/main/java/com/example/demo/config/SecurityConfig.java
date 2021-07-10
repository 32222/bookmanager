package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	public void configure(WebSecurity web) throws Exception{
		// 静的リソースはセキュリティ対象から除外
		web.ignoring().antMatchers("/js/**","/css/**", "/img/**", "/webjars/**");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
		    //認証リクエスト設定
		    .authorizeRequests()
		        // アクセス制限を設定
		        .antMatchers("/login","/register").permitAll()
		        .anyRequest().authenticated()
		        .and()
		    .formLogin()
		        // ログイン時のURL
		        .loginPage("/login")
		        // ログイン成功時のリダイレクト先
		        .defaultSuccessUrl("/booklist")
		        .and()
		    .logout()
		        //ログアウト時のURLを設定
		        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		        .and()
		    // ログインしたままにする機能をオン
		    .rememberMe();
		    
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
}
