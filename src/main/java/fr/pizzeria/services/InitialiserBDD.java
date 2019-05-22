package fr.pizzeria.services;

import java.util.Scanner;

import fr.pizzeria.exception.PizzaException;
import fr.pizzeria.persistence.IPizzaDao;
import fr.pizzeria.persistence.PizzaJdbcDao;

public class InitialiserBDD extends MenuService {

	@Override
	public void executeUC(IPizzaDao pmd, Scanner questionUser) throws PizzaException {
		pmd.initialiserPizzaDB();
	}

}
