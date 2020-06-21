package com.jap.board.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jap.board.domain.User;
import com.jap.board.domain.UserRepository;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/{id}")
	public User show(@PathVariable Long id) {

		User user = new User();
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
		}
		return user;
	}

}
