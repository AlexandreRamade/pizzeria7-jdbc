package fr.pizzeria.persistence;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.pizzeria.exception.PizzaException;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public class PizzaMemDaoTest {
	
	private List<Pizza> pizzas;
	private int pizzasInitialSize;
	
	@Before
	public void initializePizzaList() {
		 pizzas = new LinkedList<Pizza>();
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
		
		pizzasInitialSize = pizzas.size();
	}
	
	@Test
	public void testFindAllPizzas() {
		PizzaMemDao pmd = new PizzaMemDao(pizzas);
		
		List<Pizza> resultList = pmd.findAllPizzas();
		
		Assert.assertEquals(pizzas, resultList);
	}

	@Test
	public void testSaveNewPizza() {
		PizzaMemDao pmd = new PizzaMemDao(pizzas);
		String codePizza = "AAA";
		
		try {
			Pizza pizza = new Pizza(codePizza, "Aaaaaa", 10.3, CategoriePizza.POISSON);
			pmd.saveNewPizza(pizza);
			
			Pizza result = pmd.findPizzaByCode(codePizza);
			
			Assert.assertEquals(pizza, result);
			Assert.assertEquals(pizzasInitialSize + 1, pmd.findAllPizzas().size());
		} catch (PizzaException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testSaveNewPizzaNull() {
		PizzaMemDao pmd = new PizzaMemDao(pizzas);
		String codePizza = "AAA";
		
		pmd.saveNewPizza(null);
		
		Assert.assertEquals(pizzas.size(), pmd.findAllPizzas().size());
		Assert.assertEquals(pizzasInitialSize, pmd.findAllPizzas().size());
	}
	
	@Test
	public void testSaveNewPizzaThatExist() {
		PizzaMemDao pmd = new PizzaMemDao(pizzas);
		String codePizza = "PEP";
		
		pmd.saveNewPizza(pizzas.get(0));
		
		Assert.assertEquals(pizzasInitialSize, pmd.findAllPizzas().size());
	}
	
	
	@Test
	public void updatePizza() {
		PizzaMemDao pmd = new PizzaMemDao(pizzas);
		String oldCodePizza = "PEP";
		String newCodePizza = "AAA";
		
		try {
			Pizza pizza = new Pizza(newCodePizza, "Aaaaaa", 10.3, CategoriePizza.POISSON);
			pmd.updatePizza(oldCodePizza, pizza);
			
			Pizza resultNewPizza = pmd.findPizzaByCode(newCodePizza);
			Pizza resultOldPizza = pmd.findPizzaByCode(oldCodePizza);
			
			Assert.assertEquals(pizza, resultNewPizza);
			Assert.assertNull(resultOldPizza);
			Assert.assertEquals(pizzasInitialSize, pmd.findAllPizzas().size());
			
		} catch (PizzaException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void updatePizzaNull() {
		PizzaMemDao pmd = new PizzaMemDao(pizzas);
		String oldCodePizza = "PEP";
		
		pmd.updatePizza(oldCodePizza, null);
		
		Pizza resultOldPizza = pmd.findPizzaByCode(oldCodePizza);
		
		Assert.assertEquals(pizzas.get(0), resultOldPizza);
		Assert.assertEquals(pizzasInitialSize, pmd.findAllPizzas().size());
		
	}
	
	public void updatePizzaNotExist() {
		PizzaMemDao pmd = new PizzaMemDao(pizzas);
		String falseCodePizza = "NOT";
		String newCodePizza = "AAA";
		
		try {
			Pizza pizza = new Pizza(newCodePizza, "Aaaaaa", 10.3, CategoriePizza.POISSON);
			pmd.updatePizza(falseCodePizza, pizza);
			
			Pizza resultNewPizza = pmd.findPizzaByCode(newCodePizza);
			
			Assert.assertNull(resultNewPizza);
			Assert.assertEquals(pizzasInitialSize, pmd.findAllPizzas().size());
			
		} catch (PizzaException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void deletePizza() {
		PizzaMemDao pmd = new PizzaMemDao(pizzas);
		String oldCodePizza = "PEP";
		
		pmd.deletePizza(oldCodePizza);
			
		Pizza resultOldPizza = pmd.findPizzaByCode(oldCodePizza);
		
		Assert.assertNull(resultOldPizza);
		Assert.assertEquals(pizzasInitialSize - 1, pmd.findAllPizzas().size());
	}
	
	@Test
	public void deletePizzaNull() {
		PizzaMemDao pmd = new PizzaMemDao(pizzas);
		
		pmd.deletePizza(null);

		Assert.assertEquals(pizzasInitialSize, pmd.findAllPizzas().size());
	}
	
	@Test
	public void deletePizzaNotExist() {
		PizzaMemDao pmd = new PizzaMemDao(pizzas);
		String falseCodePizza = "NOT";
		
		pmd.deletePizza(falseCodePizza);
		
		Assert.assertEquals(pizzasInitialSize, pmd.findAllPizzas().size());
	}
	
	
	@Test
	public void findPizzaByCode() {
		PizzaMemDao pmd = new PizzaMemDao(pizzas);
		String codePizza = "PEP";
		
		Pizza result = pmd.findPizzaByCode(codePizza);
		
		Assert.assertEquals(pizzas.get(0), result);
	}
	
	@Test
	public void findPizzaByCodeNotExist() {
		PizzaMemDao pmd = new PizzaMemDao(pizzas);
		String codePizza = "XXX";
		
		Pizza result = pmd.findPizzaByCode(codePizza);
		
		Assert.assertNull(result);
	}
	
	@Test
	public void findPizzaByCodeNull() {
		PizzaMemDao pmd = new PizzaMemDao(pizzas);
		
		Pizza result = pmd.findPizzaByCode(null);
		
		Assert.assertNull(result);
	}
	
	@Test
	public void pizzaExists() {
		PizzaMemDao pmd = new PizzaMemDao(pizzas);
		
		boolean resultTrue = pmd.pizzaExists("PEP");
		boolean resultFalse = pmd.pizzaExists("XXX");
		boolean resultFalseForNullCode = pmd.pizzaExists(null);
		
		Assert.assertTrue("Erreur sur PizzaExists True", resultTrue);
		Assert.assertFalse("Erreur sur PizzaExists False", resultFalse);
		Assert.assertFalse("Erreur sur PizzaExists Null", resultFalseForNullCode);
	}
	
		
	//méthode de l'interface non redéfinie dans PizzaMemDao
	public void initialiserPizzaDB() {
		
	}
}
