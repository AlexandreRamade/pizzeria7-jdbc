package fr.pizzeria.model;

import fr.pizzeria.exception.PizzaException;
import fr.pizzeria.utils.Rule;
import fr.pizzeria.utils.StringUtils;
import fr.pizzeria.utils.ToString;
import fr.pizzeria.utils.Validator;

public class Pizza {
	public static int compteurId = 0;
	public int id;
	
	@ToString(uppercase = true)
	public String code;
	@ToString(uppercase = true)
	public String libelle;
	@ToString
	@Rule(min = 0)
	public double prix;
	@ToString
	public CategoriePizza categorie;
	
	public Pizza(String code, String libelle, double prix, CategoriePizza categorie) throws PizzaException {
		this.id = ++compteurId;
		this.code = code;
		this.libelle = libelle;
		this.prix = prix;
		this.categorie = categorie;
		Validator.validate(this);
	}
	
	public String toString() {
		//return "  " + code + " -> " + libelle + " (" + prix + " €) / " + categorie.toString();
		return StringUtils.returnStrSelonAnnotToString(this);
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getId() {
		return id;
	}
	
	
	
}
