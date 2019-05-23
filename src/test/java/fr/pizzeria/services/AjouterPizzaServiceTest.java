package fr.pizzeria.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import fr.pizzeria.exception.PizzaException;
import fr.pizzeria.exception.SavePizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.persistence.PizzaMemDao;

public class AjouterPizzaServiceTest {
	
	private int pizzasInitialSize;
	PizzaMemDao pmd;
	
	@Rule
	public TextFromStandardInputStream systemInMock = TextFromStandardInputStream.emptyStandardInputStream();
	
	@Before
	public void initializePizzaList() {
		List<Pizza> pizzas = new LinkedList<Pizza>();
		try {
			pizzas.add(new Pizza("PEP", "Pépéroni", 12.50D, CategoriePizza.VIANDE));
			pizzas.add(new Pizza("MAR", "Margharita", 14.00D, CategoriePizza.VIANDE));
			pizzas.add(new Pizza("REIN", "La Reine", 11.50D, CategoriePizza.SANS_VIANDE));
			pizzas.add(new Pizza("FRO", "La 4 fromages", 12.00D, CategoriePizza.SANS_VIANDE));
			pizzas.add(new Pizza("CAN", "La cannibale", 12.50D, CategoriePizza.VIANDE));
			pizzas.add(new Pizza("SAV", "La savoyarde", 13.00D, CategoriePizza.VIANDE));
			pizzas.add(new Pizza("ORI", "L'orientale", 13.50D, CategoriePizza.VIANDE));
			pizzas.add(new Pizza("IND", "L'indienne", 14.00D, CategoriePizza.VIANDE));
		} catch (PizzaException e) {
			System.err.println("Erreur lors de l'initialisation de la liste Pizza dans la classe de test PizzaMemDaoTest");
		}
		
		pmd = new PizzaMemDao(pizzas);
		pizzasInitialSize = pizzas.size();
	}
	
	
	
	@Test
	public void testExecuteUC() {
		try {
			Pizza pizza = new Pizza("NEW", "NouvellePizza", 10.5, CategoriePizza.POISSON);
			systemInMock.provideLines("NEW", "NouvellePizza", "10.5", "1");
			
			new AjouterPizzaService().executeUC(pmd, new Scanner(System.in));
			
			Pizza result = pmd.findPizzaByCode("NEW");
			
			Assert.assertEquals(pizzasInitialSize + 1, pmd.findAllPizzas().size());
			for(Pizza p : pmd.findAllPizzas()) {
				System.out.println(p.toString());
			}
			
			Assert.assertEquals(pizza, result);
			
		} catch (PizzaException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
}
