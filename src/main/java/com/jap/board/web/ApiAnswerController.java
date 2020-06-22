package com.jap.board.web;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jap.board.domain.Answer;
import com.jap.board.domain.AnswerRepository;
import com.jap.board.domain.Question;
import com.jap.board.domain.QuestionRepository;
import com.jap.board.domain.Result;
import com.jap.board.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@PostMapping("")
	public Answer create(HttpSession session, @PathVariable Long questionId, String contents) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}

		User loginUser = HttpSessionUtils.getUserFromSession(session);

		Question question = new Question();		
		Optional<Question> optionalQuestion = questionRepository.findById(questionId);
		if(optionalQuestion.isPresent()) {
			question = optionalQuestion.get();
		}
		
		Answer answer = new Answer(loginUser, question, contents);

		return answerRepository.save(answer);
	}
	
	@DeleteMapping("{id}")
	public Result delete(HttpSession session, @PathVariable Long questionId, @PathVariable Long id) {
		
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인 시 삭제가 가능합니다.");
		}
		
		Answer answer = new Answer();
		Optional<Answer> optionalAnswer = answerRepository.findById(id);
		if(optionalAnswer.isPresent()) {
			answer = optionalAnswer.get();
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!answer.isSameWriter(loginUser)) {
			return Result.fail("자신의 글만 삭제할 수 있습니다.");
		}
		
		answerRepository.delete(answer);
		
		return Result.ok();
	}
}
