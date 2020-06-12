package com.jap.board.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jap.board.domain.User;
import com.jap.board.domain.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/create")
	public String create(User user) {
		System.out.println("User : " + user);
		userRepository.save(user);
		return "redirect:/list";
	}

	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "list";
	}
}