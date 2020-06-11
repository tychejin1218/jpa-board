package com.jap.board.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	/*@PostMapping("/create")
	public String create(String userId, String password, String name, String email) {
		System.out.println("userId : " + userId);
		System.out.println("password : " + password);
		System.out.println("name : " + name);
		System.out.println("email : " + email);
		return "index";
	}*/

	@PostMapping("/create")
	public String create(User user) {
		System.out.println("User : " + user);
		return "index";
	}
}