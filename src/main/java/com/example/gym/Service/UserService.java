package com.example.gym.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.gym.Entity.Trainee;
import com.example.gym.Entity.User;
import com.example.gym.Exception.UserNotFoundException;
import com.example.gym.Repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class UserService {
	
	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	}

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public User saveUser(User user) {
		
		// Codificar la contraseña antes de guardarla en la base de datos
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
	
	public List<User>readUser(){
		return userRepository.findAll();}
	
	 public Optional<User> findOneByUsername(String username){
	        return userRepository.findOneByUserName(username);
	    }
	
	public User deleteUserById(Long idUser) throws UserNotFoundException{
		
		
		User userTemp=null;
		
		if (userRepository.existsById(idUser)){
			userTemp=userRepository.findById(idUser).get();
			userRepository.deleteById(idUser);;
		}else {
			throw new UserNotFoundException("User not found with ID: " + idUser);
		}
		
		return userTemp;
	}//delete
	
	
	public User updateUser(Long idUser, String firstName, String lastName, String userName, String password, Boolean isActive)throws UserNotFoundException {
        User user = userRepository.findById(idUser)
            .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + idUser));

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserName(userName);
        user.setPassword(password);
        user.setActive(isActive);

        return userRepository.save(user);
    }
		
	public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
    }

}
