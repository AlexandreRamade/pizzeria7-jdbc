package fr.pizzeria.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import fr.pizzeria.exception.DataBasePizzaException;
import fr.pizzeria.exception.DeletePizzaException;
import fr.pizzeria.exception.PizzaException;
import fr.pizzeria.exception.UpdatePizzaException;
import fr.pizzeria.model.Pizza;

public class PizzaJpaDao implements IPizzaDao {

	private List<Pizza> pizzas;
	
	private EntityManagerFactory emFactory;
	private EntityManager em;
	
	public PizzaJpaDao(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	
	private void createManagers() {
		this.emFactory = Persistence.createEntityManagerFactory("pizzeria-jpa");
		this.em = emFactory.createEntityManager();
	}
	
	private void closeManagers() {
		this.em.close();
		this.emFactory.close();
	}
	
	
	public List<Pizza> findAllPizzas() {
		createManagers();
		TypedQuery<Pizza> query = (TypedQuery<Pizza>) em.createQuery("SELECT p FROM Pizza p");
		List<Pizza> pizzas = query.getResultList();
		
		try {
			if(pizzas == null) {
				throw new DataBasePizzaException("Erreur de lecture dans la base de donnée PIZZA");
			}
		} catch (PizzaException e) {
			System.err.println(e.getMessage());
		} finally {
			closeManagers();
		}
		
		return pizzas;
	}

	public void saveNewPizza(Pizza pizza) {
		createManagers();
		
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		em.persist(pizza);
		
		try {
			transaction.commit();
		} catch (IllegalStateException e) {
			e.getStackTrace();
		}catch (RollbackException e) {
			e.getStackTrace();
		} finally {
			closeManagers();
		}
	}

	public void updatePizza(String codePizza, Pizza pizza) {
		createManagers();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		Query query = em.createQuery("UPDATE Pizza p SET p.code=:newCode, p.libelle=:newLibelle, p.prix=:newPrix, p.categorie=:newCategorie WHERE p.code=:oldCode");
		query.setParameter("newCode", pizza.getCode());
		query.setParameter("newLibelle", pizza.getLibelle());
		query.setParameter("newPrix", pizza.getPrix());
		query.setParameter("newCategorie", pizza.getCategorie());
		query.setParameter("oldCode", codePizza);
		
		try {
			if(query.executeUpdate() == 0) {
				throw new UpdatePizzaException("Erreur lors de la modification de la pizza");
			}
			transaction.commit();
			
		} catch (PizzaException e) {
			System.err.println(e.getMessage());
		} finally {
			closeManagers();
		}
		
	}

	public void deletePizza(String codePizza) {
		createManagers();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		Query query = em.createQuery("DELETE FROM Pizza p WHERE p.code=:oldCode");
		query.setParameter("oldCode", codePizza);
		
		try {
			if(query.executeUpdate() == 0) {
				throw new DeletePizzaException("Erreur lors de la supression de la pizza");
			}
			transaction.commit();
		} catch (PizzaException e) {
			System.err.println(e.getMessage());
		} finally {
			closeManagers();
		}
		
	}

	public Pizza findPizzaByCode(String codePizza) {
		createManagers();
		
		TypedQuery<Pizza> query = (TypedQuery<Pizza>) em.createQuery("SELECT p FROM Pizza p WHERE p.code=:codeToFind");
		query.setParameter("codeToFind", codePizza);
		
		Pizza pizza = null;
		
		try {
			pizza = query.getSingleResult();
		} catch (NoResultException e) {
			System.err.println(e.getMessage());
		} finally {
			closeManagers();
		}
		
		return pizza;
	}

	public boolean pizzaExists(String codePizza) {
		createManagers();
		
		TypedQuery<Pizza> query = (TypedQuery<Pizza>) em.createQuery("SELECT p FROM Pizza p WHERE p.code=:codeToFind");
		query.setParameter("codeToFind", codePizza);
		
		try {
			query.getSingleResult();
		} catch (NoResultException e) {
			return false;
		}
	
		return true;
	}

	public void initialiserPizzaDB() {
		createManagers();
		
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		Query query = em.createQuery("DELETE FROM Pizza p");
		query.executeUpdate();
		
		for(Pizza pizza : pizzas) {
			em.persist(pizza);
		}

		try {
			transaction.commit();

		}catch (IllegalStateException e) {
			e.getStackTrace();
		}catch (RollbackException e) {
			e.getStackTrace();
		} finally {
			closeManagers();
		}
		
		
	}

}
