package fr.pizzeria.services;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pizzeria.model.Pizza;
import fr.pizzeria.persistence.IPizzaDao;

/**
 * Classe service permettant d'afficher la liste des pizzas dans la console.
 * Appelée par la MenuServiceFactory selon le choix de l'utilisateur.
 * @author Formation
 *
 */

public class ListerPizzasService extends MenuService {
	/**
	 * Affiche la trace de l'utilisation de la classe.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ListerPizzasService.class);
	
	/**
	 * Affiche la liste des pizzas dans la console.
	 * Récupère la liste des pizzas depuis une implémentation de IPizzaDao et l'affiche dans la console.
	 * @param IPizzaDao
	 * @param Scanner
	 * 
	 * @See IPizzaDao
	 * @see PizzaMemDao.findAllPizzas()
	 * 
	 */
	@Override
	public void executeUC(IPizzaDao pmd, Scanner questionUser) {
		System.out.println("Liste des pizzas :");
		try {
			for(Pizza pizz : pmd.findAllPizzas()) {
				try {
					System.out.println(pizz.toString());
				}catch (NullPointerException e) {
					//En cas de null dans la liste ou de pizza contenant un parametre null => trace
					LOG.debug("La liste des pizzas contient un élément erroné");
				}
			}
		} catch (NullPointerException e) {
			System.out.println("   Aucune pizza n'est enregistrée pour l'instant.");
		}
	}


}
