package com.scm.services;

import java.util.List;
import java.util.Optional;

import com.scm.entities.User;

public interface UserServices {

	User saveUser(User user);
	//optional ka use karne se if else ki jaroorat nhi hai user hai ya nhi hai
	Optional<User> getUserById(String id);
	Optional<User> updateUser(User user);
	void deleteUser(String id);
	boolean isUserExist(String userId);
	boolean isUserExistBYEmail(String email);
	List<User>getAllUsers(); 

	//add more methods here related user services[logic]
	
}
