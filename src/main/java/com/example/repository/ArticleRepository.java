package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

@Repository
public class ArticleRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

//	初級
//	private static final RowMapper<Article> ARTICLE_ROW_MAPPER=(rs,i)->{
//		Article article=new Article();
//		article.setId(rs.getInt("id"));
//		article.setName(rs.getString("name"));
//		article.setContent(rs.getString("content"));
//		return article;
//	};

//	中級
	private static final ResultSetExtractor<List<Article>> ARTICLE_COMMENT_RESUTSET = (rs) -> {
	
		List<Article> articleList = new ArrayList<>();

		List<Comment> commentList = null;

		int beforeId = 0;

		while (rs.next()) {
			int nowId = rs.getInt("id");

			if (nowId != beforeId) {
				Article article=new Article();
				article.setId(nowId);
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				commentList = new ArrayList<Comment>();
				article.setCommentList(commentList);
				articleList.add(article);
			}

			if (nowId != 0) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("com_id"));
				comment.setName(rs.getString("com_name"));
				comment.setContent(rs.getString("com_content"));
				comment.setArticleId(rs.getInt("article_id"));
				commentList.add(comment);
			}
			beforeId = nowId;
		}
		return articleList;
	};

//	記事全件検索
//	public List<Article> findAll(){
//		String sql="SELECT id,name,content FROM articles ORDER BY id DESC";
//		return template.query(sql, ARTICLE_ROW_MAPPER);
//	}

//	結合
	public List<Article> findAll() {
		String sql = "SELECT a.id,a.name,a.content,c.id AS com_id,c.name AS com_name,c.content AS com_content,c.article_id "
				+ "FROM articles AS a LEFT OUTER JOIN comments AS c ON a.id = c.article_id";
		return template.query(sql, ARTICLE_COMMENT_RESUTSET);
	}

//	記事挿入
	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String sql = "INSERT INTO articles (name,content) VALUES (:name,:content)";
		template.update(sql, param);
	}

//	記事削除
	public void deleteById(int id) {
		String sql = "DELETE FROM articles WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
