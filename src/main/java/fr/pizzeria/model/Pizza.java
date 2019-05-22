package fr.pizzeria.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.pizzeria.exception.PizzaException;
import fr.pizzeria.utils.Rule;
import fr.pizzeria.utils.StringUtils;
import fr.pizzeria.utils.ToString;
import fr.pizzeria.utils.Validator;

@Entity
@Table(name="PIZZA")
public class Pizza {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="CODE")
	@ToString(uppercase = true)
	private String code;
	
	@Column(name="LIBELLE")
	@ToString(uppercase = true)
	private String libelle;
	
	@Column(name="PRIX")
	@ToString
	@Rule(min = 0)
	private double prix;

	@Column(name="CATEGORIE")
	@ToString
	private CategoriePizza categorie;
	
	
	public Pizza() {
	}

	public Pizza(String code, String libelle, double prix, CategoriePizza categorie) throws PizzaException {
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

	public CategoriePizza getCategorie() {
		return categorie;
	}

	public void setCategorie(CategoriePizza categorie) {
		this.categorie = categorie;
	}
	
	
	
	
}
