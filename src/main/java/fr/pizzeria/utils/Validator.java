package fr.pizzeria.utils;

import java.lang.reflect.Field;

import fr.pizzeria.exception.PizzaException;
import fr.pizzeria.exception.RulePizzaException;
import fr.pizzeria.model.Pizza;

public class Validator {
	public static void validate(Pizza pizza) throws PizzaException {
		//Object value = null;
		
		for(Field field : pizza.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if(field.isAnnotationPresent(Rule.class)) {
				Rule ruleAnnot = field.getAnnotation(Rule.class);
				
				try {
					Double value = field.getDouble(pizza);
										
					if(value < ruleAnnot.min()) {
						throw new RulePizzaException("La valeur de '" + field.getName() + "' doit être supérieure ou égale à " + ruleAnnot.min());
					}
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
}
