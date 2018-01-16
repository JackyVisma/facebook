package com.mkyong.utils;

//Solo per id e name di una pagina
/**
 * Examples of RestFB's Graph API functionality.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
@SuppressWarnings("deprecation")
public class PageSimple  {

	private String id;
	private String name;
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public PageSimple(String id,String name){
		this.id = id;
		this.name = name;
	}
	public String toString(){
		return this.getClass().getName() + "[id=" + this.id + ", name=" + this.name + "]";
	}
	
}