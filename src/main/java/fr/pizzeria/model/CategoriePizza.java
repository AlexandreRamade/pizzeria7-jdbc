package fr.pizzeria.model;

public enum CategoriePizza {
	VIANDE("Viande"),
	POISSON("Poisson"),
	SANS_VIANDE("Sans Viande");
	
	private String valeur = "";
	
	CategoriePizza(String str) {
		this.valeur = str;
	}
	
	public String toString() {
		return valeur;
	}
	
	public static CategoriePizza obtain(String categString) {
		for(CategoriePizza categ : CategoriePizza.values()) {
			if(categString.equals(categ.toString())) {
				return categ;
			}
		}
		return null;
	}
}
