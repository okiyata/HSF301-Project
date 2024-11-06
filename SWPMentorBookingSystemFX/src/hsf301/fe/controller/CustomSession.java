package hsf301.fe.controller;

import java.util.HashMap;
import java.util.Map;

public class CustomSession {
	private static CustomSession instance;
	private Map<String, Object> properties = new HashMap<>();
	
	public static CustomSession getInstance() {
		if (instance == null) instance = new CustomSession();
		return instance;
	}
	
	public Map<String, Object> getProperties() {
		return properties;
	}
	
	
	public void clearSession() {
		properties.clear();
	}
}
