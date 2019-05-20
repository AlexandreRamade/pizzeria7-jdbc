package fr.pizzeria.services;

import java.util.Scanner;

import fr.pizzeria.model.Pizza;
import fr.pizzeria.persistence.IPizzaDao;

public class ListerPizzasService extends MenuService {

	@Override
	public void executeUC(IPizzaDao pmd, Scanner questionUser) {
		System.out.println("Liste des pizzas :");
		for(Pizza pizz : pmd.findAllPizzas()) {
			System.out.println(pizz.toString());
		}
	}


}
