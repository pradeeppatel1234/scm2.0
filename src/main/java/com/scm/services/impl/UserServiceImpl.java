package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helpers.AppConstant;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.services.UserServices;

@Service
public class UserServiceImpl implements UserServices {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Override
	public User saveUser(User user) {
		
		//user id:have to generate
		String userId=UUID.randomUUID().toString();
		user.setUserId(userId);
		
		//password encoder
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		//set the user role
		user.setRolelist(List.of(AppConstant.ROLE_USER));
		
		return userRepo.save(user);
	}

	@Override
	public Optional<User> getUserById(String id) {
		
		return userRepo.findById(id);
	}

	@Override
	public Optional<User> updateUser(User user) {
		User user2=userRepo.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("User not Found"));
		
		//update karenge user2 from user
		//yaha user me update wala data aa raha hai
		//and user2 me set kar raha hi and yahi user2 ko db me update kar denge
		user2.setName(user.getName());
		user2.setEmail(user.getEmail());
		user2.setPassword(user.getPassword());
		user2.setAbout(user.getAbout());
		user2.setPhoneNumber(user.getPhoneNumber());
		user2.setProfilePic(user.getProfilePic());
		user2.setEnabled(user.isEnabled());
		user2.setEmailVerified(user.isEmailVerified());
		user2.setPhoneVerified(user.isPhoneVerified());
		user2.setProvider(user.getProvider());
		user2.setProviderUserId(user.getProviderUserId());
		//save user2 in db
		User user3=userRepo.save(user2);
		
		return Optional.ofNullable(user3) ;
	}

	@Override
	public void deleteUser(String id) {
		User user2=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not Found"));

		userRepo.delete(user2);
	}

	@Override
	public boolean isUserExist(String userId) {
		User user2=userRepo.findById(userId).orElse(null);
      return user2 !=null ? true : false;
		
		
	}

	@Override
	public boolean isUserExistBYEmail(String email) {
		User user2=userRepo.findById(email).orElse(null);
		 return user2 !=null ? true : false;
	}

	@Override
	public List<User> getAllUsers() {
	
		return userRepo.findAll();
	}

}
