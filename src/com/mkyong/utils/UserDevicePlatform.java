package com.mkyong.utils;

import java.util.ArrayList;
import java.util.List;

public class UserDevicePlatform {

	private String platform;
	private List<UserDeviceMe> devices;
	
	public UserDevicePlatform(UserDeviceMe device){
		devices = new ArrayList();
		platform = device.getPlatform();
		devices.add(device);
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public List<UserDeviceMe> getDevices() {
		return devices;
	}

	public void setDevices(List<UserDeviceMe> devices) {
		this.devices = devices;
	}
	
	
}
