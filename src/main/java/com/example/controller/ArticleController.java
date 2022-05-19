package com.example.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
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
	private ArticleRepository articlerepository;

	@Autowired
	private CommentRepository commentrepository;

	@Autowired
	private ServletContext application;

	@RequestMapping("")
	public String index() {
		List<Article> articleList = articlerepository.findAll();
		application.setAttribute("articleList", articleList);

		for (Article article : articleList) {
			List<Comment> commentList = commentrepository.findByArticleId(article.getId());
			article.setCommentList(commentList);
		}

		return "board";
	}

//	記事投稿
	@RequestMapping("/insertArticle")
	public String insertArticle(Article article) {
		articlerepository.insert(article);
		return "redirect:/ex";
	}

//	コメント投稿
	@RequestMapping("/insertComment")
	public String insertComment(Comment comment) {
		commentrepository.insert(comment);
		return "redirect:/ex";
	}
	
//	記事削除
	  @RequestMapping("/deleteArticle") 
	  public String deleteArticle(Article article,Comment comment) {
		 commentrepository.deleteByArticleId(comment.getArticleId());
		 articlerepository.deleteById(article.getId());
		 return "redirect:/ex";
	 }
	
}
