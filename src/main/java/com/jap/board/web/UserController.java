package com.jap.board.web;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jap.board.domain.User;
import com.jap.board.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}

	@PostMapping("/login")
	public String login(HttpSession session, String userId, String password) {

		User user = userRepository.findByUserId(userId);

		if (user == null) {
			System.out.println("Loing Failure! ID does not exist.");
			return "redirect:/users/loginForm";
		}

		if (!user.matchPassword(password)) {
			System.out.println("Loing Failure! Password does not match.");
			return "redirect:/users/loginForm";
		}
		session.setAttribute("test1", user);
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);

		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {

		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);

		return "redirect:/";
	}

	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}

	@PostMapping("")
	public String create(User user) {
		System.out.println("User : " + user);
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}

	@GetMapping("/{id}/form")
	public String updateForm(HttpSession session, @PathVariable Long id, Model model) {
		
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}

		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("It's the wrong approach.");
		}

		User user = new User();		
		Optional<User> optionalUser = userRepository.findById(sessionedUser.getId());
		if(optionalUser.isPresent()) {
			user = optionalUser.get();
		}
		
		System.out.println("updateForm ==== S");
		System.out.println(user);
		System.out.println("updateForm ==== E");

		model.addAttribute("user", user);
		return "/user/updateForm";
	}

	@PutMapping("/{id}")
	public String update(HttpSession session, @PathVariable Long id, User updatedUser) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}

		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if (!sessionedUser.matchId(id)) {
			throw new IllegalStateException("It's the wrong approach.");
		}

		User user = new User();		
		Optional<User> optionalUser = userRepository.findById(sessionedUser.getId());
		if(optionalUser.isPresent()) {
			user = optionalUser.get();
		}

		System.out.println("update ==== S");
		System.out.println(user);
		System.out.println("update ==== E");

		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
}