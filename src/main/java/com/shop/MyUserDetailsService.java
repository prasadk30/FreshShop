package com.shop;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shop.models.Users;
import com.shop.repo.UserRepository;

@Service
public class MyUserDetailsService  implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Optional<Users> user=userRepository.findByUsername(username);
		
		user.orElseThrow(()-> new UsernameNotFoundException("Not found:"+username));
		
		
		return user.map(MyUserDetails::new).get();
		
	}
}
