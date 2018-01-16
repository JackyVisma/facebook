package com.mkyong.utils;
import java.util.ArrayList;
import java.util.List;


public class CategoryWithPages {

	private String category;
	private List<PostWithPage> posts;
	
	public CategoryWithPages(PostWithPage post){
		this.posts = new ArrayList();
		this.category = post.getPage().getCategory();
		this.posts.add(post);
	}

	public String getCategory() {
		return category;
	}

	public List<PostWithPage> getPosts() {
		return posts;
	}
}
