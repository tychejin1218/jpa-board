package com.jap.board.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jap.board.domain.Question;
import com.jap.board.domain.QuestionRepository;
import com.jap.board.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/form")
	public String form(HttpSession session) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}

		return "/qna/form";
	}

	@PostMapping("")
	public String create(HttpSession session, String title, String contents) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}

		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question question = new Question(sessionUser, title, contents);

		System.out.println("question : " + question);
		questionRepository.save(question);

		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		model.addAttribute("question", questionRepository.findById(id)
		                                                 .get());
		return "/qna/show";
	}
}
