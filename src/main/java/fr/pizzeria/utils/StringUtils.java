package fr.pizzeria.utils;

import java.lang.reflect.Field;

import fr.pizzeria.model.Pizza;

public class StringUtils {
	
	public static String returnStrSelonAnnotToString(Pizza pizza) {
		
		String msg = "  ";
		Object value = null;
		
		for(Field field : pizza.getClass().getDeclaredFields()) {
			if(field.isAnnotationPresent(ToString.class)) {
				ToString tStrAnnot = field.getAnnotation(ToString.class);
				try {
					value = field.get(pizza);
					
					if(field.getName().equals("prix")) {
						msg += "(" + (Double) value + " €) / ";
					} else {
						msg += tStrAnnot.uppercase() ? value.toString().toUpperCase() : value.toString();
						
						msg += field.getName().equals("code") ? "-> " : " ";
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
		return msg;
	}
	

}
