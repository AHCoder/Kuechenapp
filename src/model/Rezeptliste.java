package model;
import java.util.ArrayList;

public class Rezeptliste {
	// TO-DO Damian
	private ArrayList<Rezept> rezepte;



	/**
	 * @param rezepte
	 * @param kuechenApp
	 */
	public Rezeptliste(ArrayList<Rezept> rezepte) {
		this.rezepte = rezepte;

	}
	public Rezeptliste() {
		this.rezepte = new ArrayList<Rezept>();
	}
	
	/**
	 * Erstellt eine Vorschlagsliste mit passenden Rezetpten, zu ausgewaehlten Artikeln.
	 * 
	 * @param artikelList
	 *           Liste von Artikeln, zu denen passende Rezpte gesucht werden sollen.	   	 
	 */
	

	public void removeRezept(String rezept) throws NullPointerException {
			
		if(rezept != null) {		
			for (Rezept rez : rezepte) {
				if(rez.getName().equals(rezept)) {
					this.rezepte.remove(rez);
				}	
			}
		}	
	}	
	

	public ArrayList<Rezept> vorschlaege(ArrayList<String> artikelList) throws NullPointerException{
		//übergebene Liste leer?
		if(artikelList.isEmpty() || artikelList == null){
			throw new NullPointerException("Keine Elemente ausgewählt.");
		}else{ 
		
			
		//Init	
		ArrayList<Rezept> vorschlagsListe = new ArrayList<Rezept>();
		Rezept aktuellesRezept;
		String aktuellerArtikelName;
		Boolean contains;
		
		
		//Suche nach passenden Rezepten
		for(int i = 0;i<rezepte.size();i++) {
			aktuellesRezept = rezepte.get(i);
			contains = false;
			for(int j = 0;j<artikelList.size();j++) {
				aktuellerArtikelName = artikelList.get(j);
				if (aktuellesRezept.containsArtikel(aktuellerArtikelName) == true) {
					contains = true;
				} else {
					contains = false;
				}
				if (contains == false) {
					j = artikelList.size();
				}
			}
			
			if (contains == true) {
				vorschlagsListe.add(aktuellesRezept);
			} else {
				//i = rezepte.size();
			}
		}
		return vorschlagsListe;
		}
	}
	
	/**
	 * Sucht in der Rezeptliste nach einem Rezept, welches den gleichen Namen hat wie der Übergebene Parametar
	 * und gibt dieses Rezept zurueck
	 * 
	 * @param name
	 *           Name des gesuchten Rezeptes als String
	 *           
	 * @postconditions Falls das Rezept nicht vorhanden ist (Tippfehler o. Aehnliches) wird eine nullreferenz zurueckgegeben!
	 *           
	 */


	public Rezept sucheRezept(String name) throws NullPointerException {
		
		if(rezepte == null){
			throw new NullPointerException("Rezeptliste Leer");
			
		}else{ 
		
		Rezept rueckgabeRezept = null;
		Rezept aktuellesRezept;
		for(int i = 0;i<rezepte.size();i++) {
			aktuellesRezept = rezepte.get(i);
			if(aktuellesRezept.getName().equals(name)) {
				rueckgabeRezept = aktuellesRezept;
			}
		}
		return rueckgabeRezept;
		}
	}
	
	/**
	 * Fügt einen Rezept hinzu
	 * 
	 * @preconditions Das Rezept darf nicht null sein
	 * 
	 * @param rezept
	 * 			Das Rezeptobjekt, das hinzugefuegt werden soll
	 * 
	 * @postconditions Die Rezeptliste enthaelt ein Rezept mehr

	 */

	public void addRezept(Rezept rezept) throws NullPointerException {
		
		if(rezepte == null){
			throw new NullPointerException("Rezeptliste ist null");
		}else{ 
			rezepte.add(rezept);
		}	
	}
	
	/**
	 * Gibt den Namen von allen in der Rezeptliste enthaltenen Rezepten in einer ArrayList<String> zurueck
	 * 
	 * @preconditions Die Rezeptliste darf nicht null sein
	 * 
	 */

	public ArrayList<String> getRezeptliste() throws NullPointerException {
		
		if(rezepte == null){
			throw new NullPointerException("Rezeptliste null");
		}else{ 
		
			ArrayList<String> rueckgabeListe = new ArrayList<String>();
			Rezept aktuellesRezept;
			for(int i = 0;i<rezepte.size();i++) {
				aktuellesRezept = rezepte.get(i);
				rueckgabeListe.add(aktuellesRezept.getName());
			}
		
			return rueckgabeListe;
		}	
	}
	
	// TODO Ilker
	
	/**
	 * Erstellt einen String im XML-Format mit allen uebergebenen Rezepten
	 * 
	 * @param rezeptListe enthaelt die Rezepte, die in dem XML String angezeigt werden sollen.
	 * 		rezeptListe darf auch nicht null sein.  
	 * 
	 * @return Es wird ein String zurückgegebn mit dem XML-Struktur von den ausgewählten Rezepten
	*/
	public String toXML(ArrayList<Rezept> rezeptListe) throws NullPointerException{
		if(rezeptListe == null)
			throw new NullPointerException("rezeptListe ist leer!");
		else{
			
			String rezeptListeString = "<rezeptbuch>\n";
			
			for(Rezept rezAusListe : rezeptListe)
				rezeptListeString = rezeptListeString + rezAusListe.toXML();
			
			rezeptListeString = rezeptListeString + "</rezeptbuch>";
			return rezeptListeString;
		}
			
	}
	
	public ArrayList<Rezept> getRezepte() {
		return rezepte;
	}

	public void setRezepte(ArrayList<Rezept> rezepte) {
		this.rezepte = rezepte;
	}
	
	public boolean istLeer()
	{
		return rezepte.isEmpty();
	}
	public int groesse() 
	{
		return rezepte.size();
	}
	
	public boolean enthaelt(String a){
		if(this.sucheRezept(a) != null) return true;
		else return false;
	}
	
	public Rezept getAt(int i)
	{
		return rezepte.get(i);
	}
	



}
