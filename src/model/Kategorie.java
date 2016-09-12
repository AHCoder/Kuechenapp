package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author Ajnol
 *
 */
public class Kategorie implements Serializable {
	
	private static final long serialVersionUID = -5952331607488535536L;

	/**
	 * Referenz auf den Kategorienamen
	 */
	private String name;
	
	/**
	 * Referenz auf die Liste der Rezepte in einer Kategorie
	 */
	private ArrayList<Rezept> rezepte; 
	
	/**
	 * Konstruktor.
	 * 
	 * @param name
	 * @param rezept
	 */
	public Kategorie(String name, ArrayList<Rezept> rezepte) {
		this.name = name;
		if (rezepte == null) {
			this.rezepte = new ArrayList<Rezept>();
		} else {
			this.rezepte = rezepte;
		}
		
	}
	
	/**
	 * Fuegt ein Rezept einer Kategorie hinzu, falls es nicht schon vorhanden ist.
	 * 
	 * @param rezept
	 * 				Das Rezept-Objekt, das hinzugefügt werden soll.
	 * 
	 * @throws NullPointerException
	 * 				Diese Exception wird geworfen wenn das Rezept-Objekt null ist.
	 * 
	 * @postconditions
	 * 				Das Rezept, das hinzugefügt werden sollte, erscheint in der Rezeptliste dieser Kategorie.
	 */
	public void addRezept(Rezept rezept) throws NullPointerException {
		
		// Rezept und sein Name werden lokal gespeichert, falls nicht null
		if(rezept != null) {
			
			Rezept thisRezept = rezept;
			String thisRezeptName = thisRezept.getName();
		
			// Das Rezept wird in der Liste gesucht
			Rezept foundRezept = null;
			Rezept currentRezept;
			for(int i = 0; i < rezepte.size(); i++) {
				currentRezept = rezepte.get(i);
				if(currentRezept.getName().equals(thisRezeptName)) {
					foundRezept = currentRezept;
				}
			}
		
			/*
			 * 
			 * Falls das gefundene Rezept null ist (also nicht in der Kategorie schon vorhanden ist),
			 * wird es in die Kategorie eingefuegt
			 * 
			 */
			if(foundRezept == null) {
				rezepte.add(rezepte.size(), thisRezept);
			}
		} else {
			throw new NullPointerException("Sie haben ein null Objekt übergeben");
		}
		
	}
	
	/**
	 * Entfernt ein Rezept aus einer Kategorie.
	 * 
	 * @param rezept
	 * 				Das Rezept-Objekt das entfernt werden soll.
	 * 
	 * @throws NullPointerException
	 * 				Diese Exception wird geworfen wenn das übergebene Objekt null ist.
	 * 
	 * @postconditions
	 * 				Das Rezept erscheint nicht mehr in der Kategorie.
	 */
	public void removeRezept(Rezept rezept) throws NullPointerException {
			
		if(rezept != null) {
			
			this.rezepte.remove(rezept);
			
		} else {
			throw new NullPointerException("Sie haben ein null Objekt übergeben");
		}
		
	}
	
	public void removeRezept(String rezept) throws NullPointerException {
		
		if(rezept != null) {
			
			for (Rezept rez : rezepte) {
				if(rez.getName().equals(rezept)) {
					this.rezepte.remove(rez);
				}	
			}
		}	
	}	
	
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 * 				Name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return rezepte
	 */
	public ArrayList<Rezept> getRezepte() {
		return rezepte;
	}

	/**
	 * @param rezepte
	 * 				Rezepte to set
	 */
	public void setRezepte(ArrayList<Rezept> rezepte) {
		this.rezepte = rezepte;
	}
	
}