package com.pratik.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pratik.dao.QuestionDao;
import com.pratik.dao.QuizDao;
import com.pratik.model.Question;
import com.pratik.model.QuestionWrapper;
import com.pratik.model.Quiz;
import com.pratik.model.Response;

@Service
public class QuizService 
{
	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuestionDao questionDao;
	
	public ResponseEntity<String> createQuiz(String category, int numQ, String title) 
	{
		List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		quizDao.save(quiz);
		return new ResponseEntity<>("Success", HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) 
	{
		Optional<Quiz> quiz = quizDao.findById(id);
		List<Question> questionsFromDB = quiz.get().getQuestions();
		List<QuestionWrapper> questionsForUser = new ArrayList<>();
		for(Question q : questionsFromDB) 
		{
			QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(),q.getOption3(),q.getOption4());
			questionsForUser.add(qw);
		}
		
		return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		Quiz quiz = quizDao.findById(id).get();
		List<Question> questions = quiz.getQuestions();
		int right = 0;
		int i=0;
		for(Response r : responses)
		{
			if(r.getResponse().equals(questions.get(i).getRightAnswer()))
				right++;
			i++;
		}
		return new ResponseEntity<>(right,HttpStatus.OK);
	}
	
}
