import java.io.*;
import java.net.*;

import javax.net.ssl.*;

public class ProvaAd {

	public static void main(String[] args) {
		
		String urlString = "https://graph.facebook.com/v2.10/me";
		
        String query = "access_token=EAACEdEose0cBAIQtFGKfZAZBqGtOpNYtYxOoE9wZBq92ZCuWGYX2aqFmdLuvkpyv1GQIklp9LRNvOCiQHCnkbqupb8C2z7u5oDSpfTcb24lqZAfUTNFeqmhb2DofEEoZAjfB8jMvjWRHIPa8ezeKwHrUHlTGZC0UKWnZBa7VZCqE9I0NGy7xQ5GdmL15wT1i4RMt8eHNsRiNbm69geYOJ5XNp";

       try{
   		URL url = new URL(urlString + "?" + query);
   		//URL url = new URL("https://httpbin.org/get");


           //make connection
           HttpsURLConnection urlc = (HttpsURLConnection) url.openConnection();
           urlc.setRequestMethod("GET");
           
           //use post mode
           urlc.setDoOutput(false);
           
           System.out.println(urlc.getResponseCode());
           System.exit(0);
           //send query
           //PrintStream ps = new PrintStream(urlc.getOutputStream());
           //ps.print(query);
           //ps.close();
           
           InputStreamReader isr = new InputStreamReader(urlc.getInputStream());

           //get result
           BufferedReader br = new BufferedReader(isr);
           
           String l = null;
           
           while ((l=br.readLine())!=null) {
               System.out.println(l);
           }
       }catch(Exception e){
    	   System.out.println(e.getMessage());
       }

        
        

	}

}
