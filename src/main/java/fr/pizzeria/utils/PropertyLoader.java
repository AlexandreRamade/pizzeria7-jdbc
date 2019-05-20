package fr.pizzeria.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
	static final String PROPERTIES_FILE_PATH =  "C:\\Users\\Formation\\Workspaces\\SpringWorkspace\\pizzeria7-jdbc\\jdbc.properties";
	static Properties properties = new Properties();
	
	public static void loadProperties() {
		
		FileInputStream input = null;
			
		try {
			input = new FileInputStream(PROPERTIES_FILE_PATH);
			properties.load(input);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
				input = null;
			}
		}
		
	}
	
	public static String getProperty(String property) {
		return properties.getProperty(property);
	}
	
	
}
