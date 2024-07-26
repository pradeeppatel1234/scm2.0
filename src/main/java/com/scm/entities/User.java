package com.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user")
//becouse we used lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//implement karne se jahan userdetails ko use karna tha ab User ko use karenge(terms of spring security)
public class User implements UserDetails {

	@Id
	private String userId;
	@Column(nullable=false)
	private String name;
	@Column(unique=true,nullable=false)
	private String email;
	
	@Getter(value=AccessLevel.NONE)
	private String password;
	@Column(length=1000)
	private String about;
	@Column(length=1000)
	private String profile;
	private String profilePic;
	private String phoneNumber;
	
	@Getter(value=AccessLevel.NONE)
	private boolean enabled=true;
	private boolean emailVerified=false;
	private boolean phoneVerified=false;
	
	//keise signup kiya user ne=self,by google,twitter..
	
	@Enumerated(value=EnumType.STRING)
	private Providers provider=Providers.SELF;
	private String providerUserId;
	//cascade lagane se yadi user delete hota hai to uske sabhi contacts bhi delete ho jayenge ya koi bhi operaion 
	//perform ho jayega,mappedby se one directional mapping hogi jisse ek table banegi manage karne ke liye
	//fetch type laze karne se jaise humne user fetch kiya ab hame jab tak contacts ki jaroorat nhi automatic db me query na chale 
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<Contact>contacts=new ArrayList<>();
	
	//implemented methods
	
	@ElementCollection(fetch=FetchType.EAGER)
	private List<String>rolelist=new ArrayList<>();
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//list of roles(admin,user)
		//collections of SimpleGrantedAuthority[roles(admin,user)]or yaha SimpleGrantedAuthority ke pass roles honge
		Collection<SimpleGrantedAuthority> roles=rolelist.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList());
				return roles;
	}
	//yaha user name hi email hai
	@Override
	public String getUsername() {
		
		return this.email;
	}

	@Override
	public boolean isEnabled() {
		
		return this.enabled;
	}
	
	
	
	@Override
	public String getPassword() {
		
		return this.password;
	}
	@Override
   public boolean isAccountNonExpired() {
		
		return true;
	}
	
	@Override
	   public boolean isAccountNonLocked() {
			
			return true;
		}
	
	@Override
	   public boolean isCredentialsNonExpired() {
			
			return true;
		}
}
