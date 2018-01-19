package com.mkyong.utils;

import java.io.FileInputStream;
import java.io.IOException;
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
import com.restfb.types.User;
import com.restfb.types.User.UserDevice;

public class RicercaDeviceFacebook {
	
	private final FacebookClient facebookClientDevice;
	
	private List<UserDeviceMe> userDevice;
	
	private List<UserDevicePlatform> platformWithDevices;
		
	public static void main(String[] args) throws IOException {
		 if (args.length == 0)
		      throw new IllegalArgumentException(
		        "You must provide an OAuth access token parameter. See README for more information.");
		    new RicercaDeviceFacebook(args[0]).writeCSVFile();

	}
	RicercaDeviceFacebook(String accessToken) {
	    facebookClientDevice = new DefaultFacebookClient(accessToken, Version.VERSION_2_11);
	  }
	
	public void fetchDevice(){
		System.out.println("fetching Device");
		userDevice = new ArrayList();
		Connection<UserDeviceMe> publicSearch = null;
		
		publicSearch = facebookClientDevice.fetchConnection("search", UserDeviceMe.class,
				Parameter.with("type", "adTargetingCategory"),Parameter.with("class", "user_device"));
		userDevice = publicSearch.getData();
		
		
		
	}
	public void writeCSVFile() throws IOException{
		fetchDevice();
		
		Properties prop = new Properties();
  		InputStream input = null;
  		
  		input = new FileInputStream("resources/config.properties");

		// load a properties file
		prop.load(input);
		
		this.platformWithDevices = new ArrayList();
		
		boolean verifica = false;
		Iterator<UserDeviceMe> iteratorDevice = userDevice.iterator();
		Iterator<UserDevicePlatform> iteratorDevicesPlatform = platformWithDevices.iterator();
		
		while(iteratorDevice.hasNext()){
			verifica = false;
			UserDeviceMe appDevice = iteratorDevice.next();
			iteratorDevicesPlatform = platformWithDevices.iterator();
			while(iteratorDevicesPlatform.hasNext()){
				UserDevicePlatform deviceAndPlatform = iteratorDevicesPlatform.next();
				if(appDevice.getPlatform().equals(deviceAndPlatform.getPlatform())){
					deviceAndPlatform.getDevices().add(appDevice);
					verifica = true;
				}
			}
			if(!verifica){
				UserDevicePlatform deviceAndPlatform = new UserDevicePlatform(appDevice);
				platformWithDevices.add(deviceAndPlatform);
			}	
		}
		
		
		
		System.out.println("Numero di platform trovate: "+platformWithDevices.size());
		
		System.out.println("Write in Csv");
		
		
		iteratorDevicesPlatform = platformWithDevices.iterator();
		Iterator<UserDeviceMe> platformWithDevice;
		while(iteratorDevicesPlatform.hasNext()){
			UserDevicePlatform platforms = iteratorDevicesPlatform.next();
			ProvaCSVutils write = new ProvaCSVutils(prop.getProperty("local_path2")+platforms.getPlatform().replace('/','_')+".csv");
			platforms.divisionSeveralPlatform(platforms,write);
			write.close();
		}

		System.out.println("end write");
	}
	public static List<String> convertFromPost(UserDeviceMe device){
  		List<String> postToString = new ArrayList();
			postToString.add(device.getName());
			postToString.add(device.getDescription());
			postToString.add(device.getPlatform());
			postToString.add(device.getType());
  		return postToString;
  	}

}
