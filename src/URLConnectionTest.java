import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.net.HttpURLConnection;
 import java.net.MalformedURLException;
 import java.net.ProtocolException;
 import java.net.URL;

import javax.swing.text.html.parser.Parser;
import org.json.JSONException;
import org.json.JSONObject;






 
public class URLConnectionTest {
	
	public static void main(String[] args){
		try{
			JSONObject jsonO = getUserInfo("EAACEdEose0cBAMLjSN8Ex2OwDZCfLrvKc45KnZC0ww3cnYhaCYYKAHHkZAyw0f1zUyY8e909AKhNsvYMoAzOj6qBfw393dHdfuptmJW60taDu2DijFtKiTbHoZA8XODZALEb7ZACd7rbvtnDuDkEtmZCcXfTTuk3QZBLveT3DZBSMhmNU6Am3tHk7rFrzbUry9I96mA2vM8QbtRMhWnKdEAbG");
			System.out.println("name: "+jsonO.getString("name"));
			System.out.println("id: "+jsonO.getString("id"));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static JSONObject getUserInfo(String access_token) throws MalformedURLException, ProtocolException, IOException {
	    try {
	        String connection = connectionGet("https://graph.facebook.com/v2.10/me?access_token=" + access_token, "");
	        JSONObject jsonObj = new JSONObject(connection.toString());
	        return jsonObj;
	        
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        
	    }
	    return null;
	}


	public static String connectionGet(String url, String parameter) throws MalformedURLException, ProtocolException, IOException, JSONException {

	    URL url1 = new URL(url);
	    HttpURLConnection request1 = (HttpURLConnection) url1.openConnection();
	    request1.setRequestMethod("GET");
	    request1.connect();
	    InputStream is;
	    if(request1.getResponseCode() >= 400){
	    	is = request1.getErrorStream();
	    }else{
	    	is = request1.getInputStream();
	    }
	    String responseBody = convertStreamToString(is);
	    
	    return responseBody;

	    }

	  private static String convertStreamToString(InputStream is) {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line).append("\n");
	        }
	    } catch (IOException e) {
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	        }
	    }

	    return sb.toString();
	}
}