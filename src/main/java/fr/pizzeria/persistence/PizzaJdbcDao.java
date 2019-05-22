package fr.pizzeria.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import fr.pizzeria.exception.DataBasePizzaException;
import fr.pizzeria.exception.PizzaException;
import fr.pizzeria.jdbc.PiloteMySQL;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

public class PizzaJdbcDao implements IPizzaDao {
	
	private List<Pizza> pizzas;
	
	private PiloteMySQL pilote;
	
	public PizzaJdbcDao(List<Pizza> pizzas) {
		this.pizzas = pizzas;
		this.pilote = new PiloteMySQL();
	}
	
	public List<Pizza> findAllPizzas() {
		pilote.seConnecter();
		Statement statement = pilote.creerStatement();
		ResultSet results = null;
		List<Pizza> pizzas = new ArrayList<Pizza>();
		
		try {
			results = statement.executeQuery("SELECT * FROM PIZZA");
			while(results.next()) {
				pizzas.add(new Pizza(results.getString(2), results.getString(3), results.getDouble(4), CategoriePizza.obtain(results.getString(5))));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PizzaException e) {
			e.printStackTrace();
		} finally {
			try {
				results.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				results = null;
				statement = null;
			}
		}
		
		pilote.closeConnection();
		return pizzas;
	}

	public void saveNewPizza(Pizza pizza) {
		pilote.seConnecter();
		PreparedStatement newPizzaSt = pilote.creerPreparedStatement("INSERT INTO PIZZA (code, libelle, prix, categorie) VALUES(?, ?, ?, ?)");
		try {
			newPizzaSt.setString(1, pizza.getCode());
			newPizzaSt.setString(2, pizza.getLibelle());
			newPizzaSt.setDouble(3, pizza.getPrix());
			newPizzaSt.setString(4, pizza.getCategorie().toString());
			newPizzaSt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				newPizzaSt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				newPizzaSt = null;
			}
		}
		
		pilote.closeConnection();
		
	}

	public void updatePizza(String codePizza, Pizza pizza) {
		pilote.seConnecter();
		PreparedStatement updatePizzaSt = pilote.creerPreparedStatement("UPDATE PIZZA SET code=?, libelle=?, prix=?, categorie=? WHERE code=?");
		try {
			updatePizzaSt.setString(1, pizza.getCode());
			updatePizzaSt.setString(2, pizza.getLibelle());
			updatePizzaSt.setDouble(3, pizza.getPrix());
			updatePizzaSt.setString(4, pizza.getCategorie().toString());
			updatePizzaSt.setString(5, codePizza);
			updatePizzaSt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				updatePizzaSt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				updatePizzaSt = null;
			}
		}
		
		pilote.closeConnection();
		
	}

	public void deletePizza(String codePizza) {
		pilote.seConnecter();
		PreparedStatement delPizzaSt = pilote.creerPreparedStatement("DELETE FROM PIZZA WHERE code=?");
		try {
			delPizzaSt.setString(1, codePizza);
			delPizzaSt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				delPizzaSt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				delPizzaSt = null;
			}
		}
		
		pilote.closeConnection();
		
	}

	public Pizza findPizzaByCode(String codePizza) {
		pilote.seConnecter();
		Pizza pizza = null;
		PreparedStatement findPizzaSt = pilote.creerPreparedStatement("SELECT * FROM PIZZA WHERE code=?");
		
		try {
			findPizzaSt.setString(1, codePizza);
			ResultSet result = findPizzaSt.executeQuery();
			while(result.next()) {
				pizza = new Pizza(result.getString(2), result.getString(3), result.getDouble(4), CategoriePizza.obtain(result.getString(5)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PizzaException e) {
			System.err.println(e.getMessage());
		}finally {
			try {
				findPizzaSt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				findPizzaSt = null;
			}
		}
		
		pilote.closeConnection();
		
		return pizza;
	}

	public boolean pizzaExists(String codePizza) {
		if(findPizzaByCode(codePizza) != null) {
			return true;
		}
		return false;
	}
	
	public void initialiserPizzaDB() {
		pilote.seConnecter();
		
		Statement st = pilote.creerStatement();
		try {
			if(!st.execute("DROP TABLE IF EXISTS PIZZA")) {
				boolean newTableCree = st.execute("CREATE TABLE IF NOT EXISTS PIZZA ("
						+ "id INT UNSIGNED NOT NULL AUTO_INCREMENT, "
						+ "code CHAR(4) NOT NULL, "
						+ "libelle VARCHAR(20) NOT NULL, "
						+ "prix DOUBLE NOT NULL, "
						+ "categorie VARCHAR(20) NOT NULL, "
						+ "PRIMARY KEY(id), UNIQUE INDEX(code));"
						);
				if(!newTableCree) {
					for(Pizza pizza : pizzas) {
						saveNewPizza(pizza);
					}
				} else {
					throw new DataBasePizzaException("Erreur lors de la création de la base de donnée PIZZA");
				}
			} else {
				throw new DataBasePizzaException("Erreur lors de la supression de la base de donnée PIZZA");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PizzaException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
				st = null;
			}
		}
		
		pilote.closeConnection();
	}

	public List<Pizza> getPizzas() {
		return pizzas;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	
	

}
