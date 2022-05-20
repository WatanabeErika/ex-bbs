package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ArticleForm {
	@NotBlank(message="名前を入力してください")
	
	@Size(min=0,max=50,message="名前は50字以内で入力してください")
	private String name;
	
	@NotBlank(message="記事を入力してください")
	private String content;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
