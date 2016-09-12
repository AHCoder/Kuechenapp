package model;

import java.util.ArrayList;

import view.Einheit;

public class Einkaufsliste {

	private ArrayList<Artikel> artikel;

	// TODO Jenny
	/**
	 * Konstruktor
	 * 
	 * @param artikel
	 */
	public Einkaufsliste() {
		this.artikel = new ArrayList<Artikel>();
	}
	
	public ArrayList<Artikel> getArtikelList() {
		return artikel;
	}
	
	public void setArtikelList(ArrayList<Artikel> a) {
		artikel = a;
	}
	

	/**
	 * Gibt den gewünschten Artikel aus der Artikelliste von der Einkaufsliste zurück.
	 *  
	 * @param artikelName
	 * 				der zu suchende Artikelname.
	 * @return gibt den gewünschten Artikel als Rückgabewert zurück.
	 */
	public Artikel getArtikel(String artikelName) {
		Artikel gesuchterArtikel = null;
		
		if (artikelName != null) {			 
			for (int i = 0; i < artikel.size(); i++) {
				if (artikel.get(i).getName().equals(artikelName)) {		// Bezeichnung des gesuchten Artikels wird in der ArrayList von Artikeln gesucht
					gesuchterArtikel = artikel.get(i);
				}
			}
		}
		return gesuchterArtikel;
		
	}
	
	/**
	 * Fügt einen Artikel der ArtikelListe der Einkaufsliste hinzu
	 * 
	 * @param name
	 * 			Bezeichnung für den einzufügenden Artikel
	 * @param einheit
	 * 			Einheit für den einzufügenden Artikel
	 * @param menge
	 * 			Wert für die Menge des einzufügenden Artikels
	 */
	public void addElement(String name, Einheit einheit, double menge) {
		if ( name != null) { 
			artikel.add( new Artikel(name, einheit, menge));
		}
	}
	
	/**
	 * Prüft ob ein Artikel schon in der Einkaufsliste vorhanden ist.
	 * 
	 * @param artikelToCheck
	 * 				Der Artikel, von dem man wissen möchte, ob es in der Einkaufsliste ist.
	 * @return true
	 * 				True wird zurückgegeben falls der Artikel in der Einkaufsliste vorhanden ist.
	 * @return false
	 * 				False wird zurückgegeben falls der Artikel nicht in der Einkaufsliste ist.
	 */
	public boolean containsArtikel(Artikel artikelToCheck) {
		
		//DURCHLÄUFT DIE EINKAUFSLISTE UND PRÜFT OB ES EINEN ARTIKEL MIT DEM GLEICHEN NAMEN GIBT
		for(Artikel artikel2 : artikel) {
			if(artikel2.getName().equals(artikelToCheck.getName())) {
				return true;
			}
		}
		return false;
	}
}
