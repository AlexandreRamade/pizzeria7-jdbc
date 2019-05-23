package fr.pizzeria.services;

import java.util.Scanner;

import fr.pizzeria.exception.PizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.persistence.IPizzaDao;

public class AjouterPizzaService extends MenuService {

	@Override
	public void executeUC(IPizzaDao pmd, Scanner questionUser) throws PizzaException {
		System.out.println("----- Ajout d'une nouvelle pizza");
		System.out.println("Veuillez saisir le code :");
					
		String codeNouvellePizza = questionUser.next();
		
		if(pmd.pizzaExists(codeNouvellePizza)) {
			throw new SavePizzaException("Le code de pizza existe déjà !");
		} else {
			System.out.println("Veuillez saisir le nom (sans espace) :");
			String nomNouvellePizza = questionUser.next();
			System.out.println("Veuillez saisir le prix :");
			Double prixNouvellePizza = Double.valueOf(questionUser.next());
			
			CategoriePizza categorie = new ListerChoixCategoriePizzaService().listerChoixCategoriePizza(questionUser);
			
			try {
				Pizza nouvellePizza = new Pizza(codeNouvellePizza, nomNouvellePizza, prixNouvellePizza, categorie);
				pmd.saveNewPizza(nouvellePizza);
			} catch (PizzaException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
}
