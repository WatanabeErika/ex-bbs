package com.example.controller;

import java.util.List;

import javax.servlet.ServletContext;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;


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
	
	@Autowired
	private CommentRepository commentrepository;
	
	@Autowired
	private ServletContext application;
	
	@RequestMapping("")
	public String index() {
		List<Article> articleList=repository.findAll();
		application.setAttribute("articleList", articleList);
		return "board";
	}
	
//	記事投稿
	@RequestMapping("/insertArticle")
	public String insertArticle(Article article) {
		repository.insert(article);
		return "board";
	}

//	@RequestMapping("")
//	public String insertComment() {
//		return "";
//	}
//	
//	@RequestMapping("")
//	public String deleteArticle() {
//		return "";
//	}
}
