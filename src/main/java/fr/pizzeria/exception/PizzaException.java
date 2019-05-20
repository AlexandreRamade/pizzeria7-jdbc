package fr.pizzeria.exception;

public abstract class PizzaException extends Exception {
	public PizzaException() {
	}
	
	public PizzaException(String msg) {
		super(msg);
	}
}
