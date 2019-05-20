package fr.pizzeria.services;

public class MenuServiceFactory {
	
	public static MenuService obtenirMenuService(int choixMenuUtilisateur) {
		switch (choixMenuUtilisateur) {
		case 1 :
			return new ListerPizzasService();
		case 2 :
			return new AjouterPizzaService();		
		case 3 :
			return new ModifierPizzaService();
		case 4 :
			return new SupprimerPizzaService();
		case 5 :
			return new InitialiserBDD();
		default :
			return null;
		}
	}
	
}
