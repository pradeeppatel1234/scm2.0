package com.scm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.scm.services.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//yaha login page ko config karenge ya spring security ko
// matlab jo user name and password user ke by signup ke time diya hoga vo db se acces hokar login se check kar lega 

@Configuration
public class SecurityConfig {

	@Autowired
	private SecurityCustomUserDetailService UserDetailService;
	
 //user create and login using java code with in memory service
  //ye hath se likha gya hai yaha jitne user crete karenge vo hi login kar payenge jaise do kiye gye hai yaha.
	//ye technique production me use nhi hoti
	/*@Bean
	public UserDetailsService userDetailService() {
		
		UserDetails user=User.withDefaultPasswordEncoder().username("admin123").password("admin123").roles("ADMIN","USER").build();
		
		UserDetails user1=User.withDefaultPasswordEncoder().username("admin1234").password("admin1234").roles("ADMIN","USER").build();
		
		var inMemoryUserDetailsManager=new InMemoryUserDetailsManager(user,user1);
		return inMemoryUserDetailsManager;
	}*/
	
	
	
	// ab yaha db me aye hue users ko login karane ka code hai
	//configuaration of authentication provider for spring security
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		//user detail ka ob object pass karna hai means userdetailsservice bana hai jo db se interact akrega and db me rakhe users ko lekar ayega
		daoAuthenticationProvider.setUserDetailsService(UserDetailService);
		//password encoder ka object
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
	//configuaration	or yaha route ko manage kiya ja rha hai 
	httpSecurity.authorizeHttpRequests(authorize->{
		///home wale url public ho jayega or other will be protected
		//authorize.requestMatchers("/home","/signup","/services").permitAll();
		//isse hum user se start hone wale url protect karenge
		authorize.requestMatchers("/user/**").authenticated();
		authorize.anyRequest().permitAll();
	});
	//form defaoult login
	//if we want change anything in form then comes here:related to form login
	
	httpSecurity.formLogin(Customizer.withDefaults());		
	/*
		formlogin.loginPage("/login");
		formlogin.loginProcessingUrl("/authenticate");
		formlogin.successForwardUrl("/user/dashboard");
		//formlogin.failureForwardUrl("/login?error=true");
		formlogin.usernameParameter("email");
		formlogin.passwordParameter("password");
		
		
	});*/
	
	
	/*	formlogin.failureHandler(new AuthenticationFailureHandler() {

			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				
				throw new 	UnsupportedOperationException("unimplemented method");
			}
			
		});
		formlogin.successHandler(new AuthenticationSuccessHandler() {

			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				
				
			}
			
		});*/
		
	//});
	return httpSecurity.build();
	}
	
	  @Bean
	  public PasswordEncoder passwordEncoder() {
	  return new BCryptPasswordEncoder();
	}
	
}
