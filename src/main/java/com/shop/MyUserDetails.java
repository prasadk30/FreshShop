package com.shop;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shop.models.Users;

public class MyUserDetails implements UserDetails{
	
	private String username;
	private String password;
	private String enabled;
	
	private List<GrantedAuthority> authorities;
	
	public MyUserDetails(Users user) {
		this.username=user.getUsername();
		this.password=user.getPassword();
		this.enabled=user.getEnabled();
		this.authorities=Arrays.stream(user.getAuthority().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return authorities;
		
	}
	
	@Override
	public String getPassword() {
		return password;
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
	
	@Override
	public boolean isEnabled() {
		if(this.enabled.equals("Y"))
			return true;
		else
			return false;
		
	}
	
	@Override
	public String getUsername() {
		return username;
	}
}
