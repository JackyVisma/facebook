package com.mkyong.utils;

import com.restfb.Facebook;
import com.restfb.types.AbstractFacebookType;

import com.restfb.Facebook;

public class UserDeviceMe extends AbstractFacebookType{

	private static final long serialVersionUID = 1L;
	
    @Facebook
	private String name;
	
    @Facebook
	private String description;
	
    @Facebook
	private String platform;
	
    @Facebook
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
