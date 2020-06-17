package com.jap.board.web;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jap.board.domain.Question;
import com.jap.board.domain.QuestionRepository;
import com.jap.board.domain.Result;
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

		questionRepository.save(question);

		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {

		Question question = new Question();
		Optional<Question> optionalQuestion = questionRepository.findById(id);
		if (optionalQuestion.isPresent()) {
			question = optionalQuestion.get();
		}

		model.addAttribute("question", question);

		return "/qna/show";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {

		Result result = new Result();

		Question question = new Question();
		Optional<Question> optionalQuestion = questionRepository.findById(id);
		if (optionalQuestion.isPresent()) {
			question = optionalQuestion.get();
		}

		result = valid(session, question);

		if (!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}

		model.addAttribute("question", question);

		return "/qna/updateForm";
	}

	@PutMapping("/{id}")
	public String update(HttpSession session, @PathVariable Long id, String title, String contents, Model model) {

		Result result = new Result();

		Question question = new Question();
		Optional<Question> optionalQuestion = questionRepository.findById(id);
		if (optionalQuestion.isPresent()) {
			question = optionalQuestion.get();
		}

		result = valid(session, question);

		if (!result.isValid()) {
			
			model.addAttribute("errorMessage", result.getErrorMessage());

			return "/user/login";
		}
		
		question.update(title, contents);

		questionRepository.save(question);

		return String.format("redirect:/questions/%d", id);
	}

	@DeleteMapping("/{id}")
	public String delete(HttpSession session, @PathVariable Long id, Model model) {

		Result result = new Result();

		Question question = new Question();
		Optional<Question> optionalQuestion = questionRepository.findById(id);
		if (optionalQuestion.isPresent()) {
			question = optionalQuestion.get();
		}

		result = valid(session, question);

		if (!result.isValid()) {
			
			model.addAttribute("errorMessage", result.getErrorMessage());

			return "/user/login";
		}
		
		questionRepository.deleteById(id);

		return "redirect:/";
	}

	private Result valid(HttpSession session, Question question) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이 필요합니다.");
		}

		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!question.isSameWriter(loginUser)) {
			return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}

		return Result.ok();
	}
}