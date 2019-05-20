package fr.pizzeria.services;

import java.util.Scanner;

import fr.pizzeria.exception.PizzaException;
import fr.pizzeria.persistence.IPizzaDao;

public abstract class MenuService {
	public abstract void executeUC(IPizzaDao pmd, Scanner questionUser) throws PizzaException;
}
