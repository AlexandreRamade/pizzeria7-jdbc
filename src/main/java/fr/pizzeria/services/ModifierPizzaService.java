package fr.pizzeria.services;

import java.util.Scanner;

import fr.pizzeria.exception.PizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.persistence.IPizzaDao;

public class ModifierPizzaService extends MenuService{

	@Override
	public void executeUC(IPizzaDao pmd, Scanner questionUser) throws PizzaException {
		System.out.println("----- Mise à jour d'une pizza");
		System.out.println("Veuillez saisir le code de la pizza à modifier :");
		questionUser.nextLine();
		String codePizzaAModifier = questionUser.nextLine();
		
		if(pmd.pizzaExists(codePizzaAModifier)) {
			System.out.println("Veuillez saisir le nouveau code :");
			String codeModifPizza = questionUser.nextLine();
			
			if(pmd.pizzaExists(codeModifPizza)) {
				System.err.println("ATENTION : Ce code de pizza existe déjà !");
			} else {
				System.out.println("Veuillez saisir le nouveau nom (sans espace) :");
				String nomModifPizza = questionUser.nextLine();
				System.out.println("Veuillez saisir le nouveau prix :");
				Double prixModifPizza = questionUser.nextDouble();
				
				CategoriePizza categorie = new ListerChoixCategoriePizzaService().listerChoixCategoriePizza(questionUser);
				
				Pizza pizzaModifie = new Pizza(codeModifPizza, nomModifPizza, prixModifPizza, categorie);
				pmd.updatePizza(codePizzaAModifier, pizzaModifie);
			}
		} else {
			throw new UpdatePizzaException("Code pizza invalide !");
		}
		
	}

}
