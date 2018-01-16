package com.mkyong.utils;

import com.restfb.types.Page;
import com.restfb.types.Post;

public class PostWithPage{

	private Post post;
	
	private Page page;
	
	public PostWithPage(Post p,Page page){
		this.post = p;
		this.page = page;
	}
	public Page getPage(){
		return page;
	}
	public Post getPost() {
		return post;
	}
	
}
