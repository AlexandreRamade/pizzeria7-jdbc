package fr.pizzeria.services;

import java.util.Scanner;

import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.persistence.IPizzaDao;

public class SupprimerPizzaService extends MenuService {

	@Override
	public void executeUC(IPizzaDao pmd, Scanner questionUser) throws DeletePizzaException {
		System.out.println("----- Suppression d'une pizza");
		
		new ListerPizzasService().executeUC(pmd, questionUser);
		
		System.out.println("Veuillez saisir le code de la pizza à supprimer :");
		questionUser.nextLine();
		
		String codePizzaASupprimer = questionUser.nextLine();
		if(pmd.pizzaExists(codePizzaASupprimer)) {
			pmd.deletePizza(codePizzaASupprimer);
		} else {
			throw new DeletePizzaException("Code pizza invalide !");
		}
		
	}

}
