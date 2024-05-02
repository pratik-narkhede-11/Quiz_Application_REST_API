package com.pratik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pratik.dao.QuestionDao;
import com.pratik.model.Question;

@Service
public class QuestionService 
{
	@Autowired
	QuestionDao questionDao;
	
	public List<Question> getAllQuestions() {
		return questionDao.findAll();
	}

	public List<Question> getQuestionsByCategory(String category) {
		return questionDao.findByCategory(category);
	}
	
	public void addQuestion(Question question) {
		questionDao.save(question);
	}
	
}
