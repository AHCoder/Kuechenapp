package model;

import java.io.Serializable;
import java.util.ArrayList;

import view.Einheit;

public class Rezept implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8222408551964394720L;

	private String name;

	private String beschreibung;

	private String zubereitung;

	private int portionenzahl;

	private String zubereitungszeit;
	
	private ArrayList<Kategorie> kategorie;

	private ArrayList<Artikel> artikel;
	//TODO Patrick

	/**
	 * @param name
	 * @param beschreibung
	 * @param zubereitung
	 * @param portionenzahl
	 * @param zubereitungszeit
	 * @param kategorie
	 * @param artikel
	 */
	public Rezept(String name, String beschreibung, String zubereitung,
			int portionenzahl, String zubereitungszeit,
			ArrayList<Kategorie> kategorie, ArrayList<Artikel> artikel) {
		this.name = name;
		this.beschreibung = beschreibung;
		this.zubereitung = zubereitung;
		this.portionenzahl = portionenzahl;
		this.zubereitungszeit = zubereitungszeit;
		this.kategorie = kategorie;
		this.artikel = artikel;
	} 
	
	public Rezept() {
	}

	/**
	 * @param name
	 */
	public void removeKategorie(String name) {
		Kategorie gesuchteKat = new Kategorie("", null);
		for(Kategorie kat : this.kategorie){
			if(kat.getName().equals(name)){
				gesuchteKat = kat;
				kat.removeRezept(this);
			}
		}
		if(!gesuchteKat.equals("")){
			this.kategorie.remove(gesuchteKat);
		}
	}

	/**
	 * @param katName
	 */
	public void addKategorie(Kategorie katName) {
		//Prüfen ob Kategorie bereits vorhanden
		for(Kategorie kat : this.kategorie){
			if(kat.equals(katName)){
				return;
			}
		}
		this.kategorie.add(katName);
		katName.addRezept(this);
	}


	/**
	 * @param artikelName
	 * @return
	 */
	public Boolean containsArtikel(String artikelName) {
		for(Artikel artikel : this.artikel){
			if(artikel.getName().equals(artikelName)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param name
	 * @param einheit
	 * @param menge
	 */
	public void addArtikel(String name, Einheit einheit, double menge) {
		for(Artikel art : this.artikel){
			if(art.getName().equals(name)){
				art.setMasseinheit(einheit);
				art.setMenge(menge);
				return;
			}
		}
		artikel.add(new Artikel(name, einheit, menge));
	}
	
	// TODO ilker
	/**
	 * Diese Methode erstellt die xml-Formatierung fürs Rezept. Bsp, <rezept><gericht> ......</gericht>.
	 * 
	 * @return man kriegt ein String mit der xml-Formatierung fürs Rezept zurück.
	 */
	public String toXML() {
		// 1 - 3. zeile von xml-datei
		String rezeptString = "<rezept>\n" + "<gericht>" + getName() + "</gericht>\n";
		
		// einzelne artikeln werden hinzugefügt
		for(Artikel artAusListe : artikel ){
			rezeptString = rezeptString + artAusListe.toXML(); 
		}
		
		
		// Kategorie (!vorsicht als Liste) hinzufügen
		//[FIXED]toString gibt mit eckigen Klammern aus...
		ArrayList<Kategorie> kategorielist = new ArrayList<Kategorie>();
		kategorielist = getKategorie();
		
					////*******************///// Schönheitsfehler
		String listString = "";
		
		if(kategorielist.size() == 1){
			for (Kategorie s : kategorielist){
			    listString += s.getName();
			}
		}
		else{
			for (Kategorie s : kategorielist){
			    listString += s.getName() + ",";
			}
			listString = listString.substring(0, listString.length()-1);
		}
					////****************///// Schönheitsfehler
		
		rezeptString = rezeptString + "<kategorie>" + listString + "</kategorie>\n";
		
		//rezeptString = rezeptString + "<kategorie>" + getKategorie().toString() + "</kategorie>";
		//[/FIXED]
		
		// letzte 6 zeilen von unten der xml datei werden hinzugefügt
		rezeptString = rezeptString + "<portionenzahl>" + getPortionenzahl() + "</portionenzahl>\n"
						+ "<zubereitung>" + getZubereitung() + "</zubereitung>\n" + "<beschreibung>"
						+ getBeschreibung() + "</beschreibung>\n" + "<zubereitungszeit>" +
						getZubereitungszeit() + "</zubereitungszeit>\n" + "</rezept>\n";
		
		return rezeptString;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getZubereitung() {
		return zubereitung;
	}

	public void setZubereitung(String zubereitung) {
		this.zubereitung = zubereitung;
	}

	public int getPortionenzahl() {
		return portionenzahl;
	}

	public void setPortionenzahl(int portionenzahl) {
		this.portionenzahl = portionenzahl;
	}

	public String getZubereitungszeit() {
		return zubereitungszeit;
	}

	public void setZubereitungszeit(String zubereitungszeit) {
		this.zubereitungszeit = zubereitungszeit;
	}

	public ArrayList<Kategorie> getKategorie() {
		return kategorie;
	}

	public void setKategorie(ArrayList<Kategorie> kategorie) {
		this.kategorie = kategorie;
	}

	public ArrayList<Artikel> getArtikel() {
		return artikel;
	}

	public void setArtikel(ArrayList<Artikel> artikel) {
		this.artikel = artikel;
	}
	
	public Artikel getArt(int i)
	{
		if(i>artikel.size()) return null;
		else return artikel.get(i);
	}
	
	public Kategorie getKat(int i)
	{
		if(i > kategorie.size()) return null;
		else return kategorie.get(i);
	}
}
