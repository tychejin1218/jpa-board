package com.jap.board.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jap.board.domain.Answer;
import com.jap.board.domain.AnswerRepository;
import com.jap.board.domain.Question;
import com.jap.board.domain.QuestionRepository;
import com.jap.board.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@PostMapping("")
	public String create(HttpSession session, @PathVariable Long questionId, String contents) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}

		User loginUser = HttpSessionUtils.getUserFromSession(session);

		Question question = questionRepository.findById(questionId)
		                                      .get();

		Answer answer = new Answer(loginUser, question, contents);

		answerRepository.save(answer);

		return String.format("redirect:/questions/%d", questionId);
	}
}
