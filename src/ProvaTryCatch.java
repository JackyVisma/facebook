import java.util.*;

public class ProvaTryCatch {
	
	static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		int divisione;
	    
	    
	    
	    try {
	    	divisione = 5/0;
	      	System.out.println("Il risultato della divisione è:" + divisione);
	    }

	    catch (ArithmeticException exc) {
	    	//System.out.println(exc);
	    	System.out.println("Non puoi effettuare una divisione per zero");
	    }
	    	        
	    catch (Exception exc) {
	    	System.out.println("Errore generico");
	    }  
	  }
}


