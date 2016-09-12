package model;

import java.util.ArrayList;

import view.Einheit;

public class Lager{
	
	private ArrayList<Artikel> artikel;
	
	
	// TODO Lisa
	/**
	 * Konstruktor
	 * @param artikel bezeichnet die ArrayList
	 */
	public Lager() {
		this.artikel = new ArrayList<>();
	}
	
	public ArrayList<Artikel> getArtikelList() {
		return artikel;
	}
	
	public void setArtikelList(ArrayList<Artikel> a){
		artikel = a;
	}

	/**
	 * Mit der Methode hängt man einen neuen Artikel an die ArrayList<Artikel> im Lager.
	 */
	public void addElement(String name, Einheit einheit, double menge) {
		artikel.add(new Artikel(name, einheit, menge));
	}
	
	/**
	 * Mit der Methode sucht man im Lager nach der Bezeichnung des Artikels und erhält diesen Artikel als Rückgabewert.
	 * @param artikelName enthält die Bezeichnung des gesuchten Artikels.
	 */
	public Artikel getArtikel(String artikelName) {
		Artikel artikelGesucht = null;
		for(int i=0; i< artikel.size();i++){
			if(artikel.get(i).getName().equals(artikelName)){
				artikelGesucht = artikel.get(i);
			}
		}
		return artikelGesucht;
	}
	
	/**
	 * Prueft ob ein Artikel schon im Bestand vorhanden ist.
	 * 
	 * @param artikelToCheck
	 * 				Der Artikel, von dem man wissen moechte, ob es im Bestand ist.
	 * @return true
	 * 				True wird zurueckgegeben falls der Artikel im Bestand vorhanden ist.
	 * @return false
	 * 				False wird zurueckgegeben falls der Artikel nicht im Bestand ist.
	 */
	public boolean containsArtikel(Artikel artikelToCheck) {
		
		//DURCHLAEUFT DEN BESTAND UND PRUEFT OB ES EINEN ARTIKEL MIT DEM GLEICHEN NAMEN GIBT
		for(Artikel artikel2 : artikel) {
			if(artikel2.getName().equals(artikelToCheck.getName())) {
				return true;
			}
		}
		return false;
	}
}
