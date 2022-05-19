package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;


@Controller
@RequestMapping("/ex")
public class ArticleController {
	@ModelAttribute
	public ArticleForm setUpForm() {
		return new ArticleForm();
	}
	
	@ModelAttribute
	public CommentForm setUpForm2() {
		return new CommentForm();
	}
	
	@Autowired
	private ArticleRepository repository;
	
	@RequestMapping("")
	public String index() {
		return "board";
	}
	
	@RequestMapping("/ex")
	public String insertArticle() {
		return "board";
	}
	
	@RequestMapping("")
	public String insertComment() {
		return "";
	}
	
	@RequestMapping("")
	public String deleteArticle() {
		return "";
	}
}
