package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


	@RequestMapping("")
	public String index(Model model) {
//		全件・結合検索
		List<Article> articleList = articlerepository.findAll();
		
//		全件検索
//		for (Article article : articleList) {
//			List<Comment> commentList = commentrepository.findByArticleId(article.getId());
//			article.setCommentList(commentList);
//		}
	
		model.addAttribute("articleList", articleList);
		
		return "board";
	}

//	初級記事投稿
//	@RequestMapping("/insertArticle")
//	public String insertArticle(Article article) {
//		articlerepository.insert(article);
//		return "redirect:/ex";
//	}
	
	
//	form使った記事投稿
	@RequestMapping("/insertArticle")
	public String insertArticle(ArticleForm articleForm) {
		Article article=new Article();
		BeanUtils.copyProperties(articleForm, article);
		articlerepository.insert(article);
		return "redirect:/ex";
	}

//	初級コメント投稿
//	@RequestMapping("/insertComment")
//	public String insertComment(Comment comment) {
//		commentrepository.insert(comment);
//		return "redirect:/ex";
//	}
	
//	form使ったコメント投稿
	@RequestMapping("/insertComment")
	public String insertComment(CommentForm commentForm) {
		Comment comment=new Comment();
		BeanUtils.copyProperties(commentForm, comment);
		comment.setArticleId(Integer.parseInt(commentForm.getArticleId()));
		commentrepository.insert(comment);
		return "redirect:/ex";
	}
	
//	初級記事削除
//	  @RequestMapping("/deleteArticle") 
//	  public String deleteArticle(Article article,Comment comment) {
//		 commentrepository.deleteByArticleId(comment.getArticleId());
//		 articlerepository.deleteById(article.getId());
//		 return "redirect:/ex";
//	 }
	
	 
//	 form使った記事削除
	  @RequestMapping("/deleteArticle") 
	  public String deleteArticle(ArticleForm articleForm,CommentForm commentForm) {
		 Article article=new Article();
		 Comment comment=new Comment();
		 BeanUtils.copyProperties(articleForm, article);
		 BeanUtils.copyProperties(commentForm, comment);
		 comment.setArticleId(Integer.parseInt(commentForm.getArticleId()));
		 commentrepository.deleteByArticleId(comment.getArticleId());
		 articlerepository.deleteById(article.getId());
		 return "redirect:/ex";
	 }
}
