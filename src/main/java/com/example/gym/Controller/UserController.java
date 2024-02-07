package com.example.gym.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.example.gym.Entity.User;
import com.example.gym.Service.UserService;

@RestController
@RequestMapping (path="/gym/users")

//Anotacion CrossOrigin 
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })

public class UserController {
	
	
	//inyeccion de dependecias
	
	public final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		
		this.userService = userService;
	}
	
	// Metodos HTTP
	
			@GetMapping
		    public List<User> getAllUsers() {
		        return userService.readUser();
		    }
		    @PostMapping
		    public User createUser(@RequestBody User user) {
		        return userService.saveUser(user);
		    }

	
	
	

}
