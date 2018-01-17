package com.mkyong.utils;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Page;
import com.restfb.types.Post;



public class RecuperaPagineWeb  {
  /**
   * RestFB Graph API client.
   */
  private final FacebookClient facebookClient25;
  
  private List<Page> pages;
  
  private List<PostWithPage> pageAndPosts;
  
  private List<CategoryWithPages> categoriesWithPages;
 
  public static void main(String[] args) throws Exception{
	  
    if (args.length == 0)
      throw new IllegalArgumentException(
        "You must provide an OAuth access token parameter. See README for more information.");
    new RecuperaPagineWeb(args[0]).divisionSeveralFileByCategory();
  }

  RecuperaPagineWeb(String accessToken) {
    facebookClient25 = new DefaultFacebookClient(accessToken, Version.VERSION_2_11);
  }
 
  public void fetchPages() throws InterruptedException{
	  
	  List<Page> pagesSupport = new ArrayList();
	  
	  Connection<Page> publicSearch = null;
	  int searchedPages;
	  /*for(char c1 = 'A'; c1 <= 'Z'; c1++){
		  for(char c2 = 'A'; c2 <= 'Z'; c2++){
		    for(char c3 = 'A'; c3 <= 'Z'; c3++){
		    	System.out.println("" +c1 + c2 + c3);
		    	  
		    	  TimeUnit.SECONDS.sleep(1);*/
	  		
	  		for(int i = 1; i <= 10; i++){
	  			  searchedPages = 0;
				  publicSearch = facebookClient25.fetchConnection("search", Page.class,
						    Parameter.with("q",i), Parameter.with("type", "page"),Parameter.with("fields","category,name,id"));
				  pagesSupport = publicSearch.getData();
				  System.out.println("tot " + pagesSupport.size());
				  controlli(pagesSupport);
				  searchedPages+=pagesSupport.size();
			  
				  String next = publicSearch.getNextPageUrl();
				  System.out.println("Ricerca pagine aggiuntive");
				  while((searchedPages < 25) && (next!= null)){
					  publicSearch = facebookClient25.fetchConnectionPage(next, Page.class);
					  pagesSupport = publicSearch.getData();
					  controlli(pagesSupport);
					  
					  next = publicSearch.getNextPageUrl();
					  searchedPages+=pagesSupport.size();
				  }
				  System.out.println("parziale:" + this.pages.size());
			 // }
		 // }
	  	//}  
	  		}   
	  		System.out.println("Numero di pagine: "+this.pages.size());
		
	}
  
  public void controlli(List<Page> searchResult){
	  boolean verifica = false;
	  
	  Iterator<Page> iteratorSearchResult = searchResult.iterator();
	  Iterator<Page> iteratorPages = this.pages.iterator();
	  
	  
	  while(iteratorSearchResult.hasNext()){
		  verifica = false;
		  Page pageSupport = iteratorSearchResult.next();
		  iteratorPages = pages.iterator();
		  while(iteratorPages.hasNext()){
			 Page page = iteratorPages.next();
			 if((page.getId().equals(pageSupport.getId()))||(pageSupport.getCategory() == null)){
				 verifica = true;
				 break;
			 }
			 else{
				 verifica = false;
			 }
		  }
		  if(!verifica){
			  this.pages.add(pageSupport);
		  }
	  }
  }
  
  	public void fetchPost() throws Exception{
  		fetchPages();
  		
  		Iterator<Page> iterator = this.pages.iterator();
  		Page page;
  		
  		System.out.println("start fetching posts");
  		
  		while(iterator.hasNext()){
  			page = iterator.next();
  			Connection<Post> myFeed = facebookClient25.fetchConnection(page.getId()+"/posts", Post.class);
  			List<Post> postOfPages =myFeed.getData();
  			Iterator<Post> postIterator = postOfPages.iterator();
  			while(postIterator.hasNext()){
  				PostWithPage postWithPage = new PostWithPage(postIterator.next(),page);
  				pageAndPosts.add(postWithPage);
  			}
  		}
  		
  		System.out.println("fetched posts");
  		
  	}
  	public void divisionSeveralFileByCategory() throws Exception{
  		this.pages = new ArrayList();
  		this.pageAndPosts = new ArrayList();
  		
  		Properties prop = new Properties();
  		InputStream input = null;
  		
  		input = new FileInputStream("resources/config.properties");

		// load a properties file
		prop.load(input);
  		fetchPost();
  		
  		this.categoriesWithPages = new ArrayList();
  		
  		Iterator<PostWithPage> iteratorPosts = this.pageAndPosts.iterator();
  		Iterator<CategoryWithPages> iteratorCategory = this.categoriesWithPages.iterator();
  		
  		while(iteratorPosts.hasNext()){
  			boolean found = false;
  			PostWithPage postWithPage = iteratorPosts.next();
  			iteratorCategory = categoriesWithPages.iterator();
  			while(iteratorCategory.hasNext()){
  				CategoryWithPages categoryWithPages = iteratorCategory.next();
  				if(categoryWithPages.getCategory().equals(postWithPage.getPage().getCategory())){
  					categoryWithPages.getPosts().add(postWithPage);
  					found = true;
  				}
  			}
  			if(!found){
  				CategoryWithPages categoryWithPages = new CategoryWithPages(postWithPage);
  				categoriesWithPages.add(categoryWithPages);
  			}
  		}
  		System.out.println("Numero di categorie trovate: "+categoriesWithPages.size());
  		String appCategory;
  		iteratorCategory = categoriesWithPages.iterator();
  		Iterator<PostWithPage> postWithPage;
  		while(iteratorCategory.hasNext()){
  			
  			CategoryWithPages categories = iteratorCategory.next();
  			if(categories.getCategory().indexOf('/') >= 0){
  				appCategory = categories.getCategory().replace('/','_');
  			}
  			else{
  				appCategory = categories.getCategory();
  			}
  			ProvaCSVutils write = new ProvaCSVutils(prop.getProperty("local_path")+appCategory+".csv");
  			postWithPage = categories.getPosts().iterator();
  			
  			while(postWithPage.hasNext()){
  				
  				PostWithPage support = postWithPage.next();
  				write.writeCsv(convertFromPost(support));
  				
  			}

  			write.close();
  		}
  		
  		System.out.println("end write");
	  	
  		
  	}
  	public List<String> convertFromPost(PostWithPage post){
  		List<String> postToString = new ArrayList();
  		postToString.add(post.getPage().getId());
  		postToString.add(post.getPage().getName() != null ? post.getPage().getName():"");
  		postToString.add(post.getPage().getCategory() != null ? post.getPage().getCategory():"");
  		postToString.add(post.getPost().getId());
  		postToString.add(post.getPost().getMessage() != null ? post.getPost().getMessage().substring(0, post.getPost().getMessage().length() <20 ? post.getPost().getMessage().length() -1 : 20) : "");
  		return postToString;
  	}
  
  
}