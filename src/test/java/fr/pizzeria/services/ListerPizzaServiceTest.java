package fr.pizzeria.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;
import org.mockito.Mockito;

import fr.pizzeria.exception.PizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.persistence.IPizzaDao;
import fr.pizzeria.persistence.PizzaMemDao;

public class ListerPizzaServiceTest {

	
	@Test
	public void tetsExecuteUCNull() {
		PizzaMemDao mockedDao = Mockito.mock(PizzaMemDao.class);
		
		Mockito.when(mockedDao.findAllPizzas()).thenReturn(null);
		
		ListerPizzasService lpS = new ListerPizzasService();
		lpS.executeUC(mockedDao, new Scanner(System.in));

	}
	
	@Test
	public void tetsExecuteUCListWithNullAndWrongPizza() {
		PizzaMemDao mockedDao = Mockito.mock(PizzaMemDao.class);
		
		//initialisation de la liste contenant un null et une pizza avec un code null et une pizza normale
		List<Pizza> pizzas = new LinkedList<Pizza>();
		try {
			pizzas.add(null);
			pizzas.add(new Pizza(null, "Aaaaaa", 10.5, CategoriePizza.POISSON));
			pizzas.add(new Pizza("PIZ", "Ma pizza", 10.5, CategoriePizza.SANS_VIANDE));
		} catch (PizzaException e) {
			e.printStackTrace();
		}

		Mockito.when(mockedDao.findAllPizzas()).thenReturn(pizzas);
		
		ListerPizzasService lpS = new ListerPizzasService();
		lpS.executeUC(mockedDao, new Scanner(System.in));
		
		
	}
	
	
}
