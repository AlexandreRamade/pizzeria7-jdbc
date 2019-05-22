package fr.pizzeria.console;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import fr.pizzeria.exception.MenuNumberException;
import fr.pizzeria.exception.PizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.persistence.IPizzaDao;
import fr.pizzeria.persistence.PizzaJpaDao;
import fr.pizzeria.services.MenuServiceFactory;

public class PizzeriaAdminConsoleApp {

	public static void main(String[] args) throws PizzaException {

		Scanner questionUser = new Scanner(System.in);
		int optionUser = 0;
		
		//----------pizzas initialement présentes
		List<Pizza> pizzas = new LinkedList<Pizza>();
		pizzas.add(new Pizza("PEP", "Pépéroni", 12.50D, CategoriePizza.VIANDE));
		pizzas.add(new Pizza("MAR", "Margharita", 14.00D, CategoriePizza.VIANDE));
		pizzas.add(new Pizza("REIN", "La Reine", 11.50D, CategoriePizza.SANS_VIANDE));
		pizzas.add(new Pizza("FRO", "La 4 fromages", 12.00D, CategoriePizza.SANS_VIANDE));
		pizzas.add(new Pizza("CAN", "La cannibale", 12.50D, CategoriePizza.VIANDE));
		pizzas.add(new Pizza("SAV", "La savoyarde", 13.00D, CategoriePizza.VIANDE));
		pizzas.add(new Pizza("ORI", "L'orientale", 13.50D, CategoriePizza.VIANDE));
		pizzas.add(new Pizza("IND", "L'indienne", 14.00D, CategoriePizza.VIANDE));
		
		//Initialisation du Dao
		IPizzaDao pmd = new PizzaJpaDao(pizzas);
		
		
		
		do {
			
			try {
				//Menu principal
				System.out.println("\n***** Pizzeria Administration *****");
				System.out.println("  1. Lister les pizzas");
				System.out.println("  2. Ajouter une nouvelle pizza");
				System.out.println("  3. Mettre à jour une pizza");
				System.out.println("  4. Supprimer une pizza");
				System.out.println("  5. Initialiser la BDD");
				System.out.println("  99. Sortir");
				
				System.out.println("\nFaites votre choix :");
				//r�cup�ration du choix de l'utilisateur
				
				
				optionUser = questionUser.nextInt();
				
				
				if(0 < optionUser && optionUser < 6) {
					try {
						MenuServiceFactory.obtenirMenuService(optionUser).executeUC(pmd, questionUser);
					} catch (PizzaException e) {
						System.err.println(e.getMessage());
					}
				}else if(optionUser == 99) {
					System.out.println("Au revoir !");
				} else {
					throw new MenuNumberException("Ce nombre ne corespond � rien dans le menu.");
				}
			
			} catch (PizzaException e) {
				System.err.println(e.getMessage());
			}
		
		} while (optionUser != 99);
		
	}

}
