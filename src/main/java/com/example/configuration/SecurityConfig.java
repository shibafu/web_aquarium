package com.example.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.example.service.LoginUserDetailsService;

/**
 * 認証を管理するコンフィグBean
 * @author chu31
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	//独自の認証UserDetailServiceを注入
	@Autowired
	LoginUserDetailsService loService;

	/**
	 * デバッグモード
	 */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(true);
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
				//LoginFormと同じものをパラメーターを作成
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.usernameParameter("loginName")
				.passwordParameter("loginPassword")
				.defaultSuccessUrl("/user_top")
					.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.permitAll()
			.and()
				.authorizeRequests()
				.antMatchers("/register","/register_confirm","/register_complete").permitAll()
				.antMatchers("/management_console","/management_console/*").hasAuthority("ROLE_ADMIN")
				.anyRequest()
				.authenticated()
			.and()
				.csrf()
				.csrfTokenRepository(csrfTokenRepository());			//csrfトークン挿入

	}

	/**
	 * 実際セキュリティを動かすメソッド。
	 * 今は管理者Authorのみ
	 * @param auth ユーザー認証情報。
	 * @throws Exception
	 */
	@Autowired
	void configureAuthentivationManager(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(loService)
		.passwordEncoder(passwordEncoder());
	}

	//パスワードエンコーダー
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/**
	 *
	 * @return csrdトークンオブジェクト
	 */
	private CsrfTokenRepository csrfTokenRepository()
	{
	    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
	    repository.setHeaderName("X-XSRF-TOKEN");
	    return repository;
	}
}
