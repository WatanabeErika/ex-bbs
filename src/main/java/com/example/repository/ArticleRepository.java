package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

@Repository
public class ArticleRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER=(rs,i)->{
		Article article=new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};
	
//	記事全件検索
	public List<Article> findAll(){
		String sql="SELECT * FROM articles ORDER BY id DESC";
		return template.query(sql, ARTICLE_ROW_MAPPER);
	}
	
//	記事挿入
	public void insert(Article article) {
		SqlParameterSource param=new BeanPropertySqlParameterSource(article);
		String sql="INSERT INTO articles (name,content) VALUES (:name,:content)";
		template.update(sql, param);
	}
	
//	記事削除
	public void deleteById(int id) {
		String sql="DELETE FROM articles WHERE id = :id";
		SqlParameterSource param=new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
