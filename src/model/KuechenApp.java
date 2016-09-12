package model;

import java.util.ArrayList;

public class KuechenApp {

	private Lager lager;

	private Einkaufsliste einkaufsliste;

	private Rezeptliste rezeptliste;

	private ArrayList<Kategorie> kategorie;

	//2.ter Konstruktor fürs testen (export)
	public KuechenApp(ArrayList<Rezept> rezeptliste) {
		this.lager = new Lager();
		this.einkaufsliste = new Einkaufsliste();
		this.rezeptliste = new Rezeptliste(rezeptliste);
		this.kategorie = new ArrayList<Kategorie>();
	}
	
	public KuechenApp() {
		this.lager = new Lager();
		this.einkaufsliste = new Einkaufsliste();
		this.rezeptliste = new Rezeptliste(new ArrayList<Rezept>());
		this.kategorie = new ArrayList<Kategorie>();
	}

	/**
	 * @return the lager
	 */
	public Lager getLager() {
		return lager;
	}

	/**
	 * @return the einkaufsliste
	 */
	public Einkaufsliste getEinkaufsliste() {
		return einkaufsliste;
	}

	/**
	 * @return the rezeptliste
	 */
	public Rezeptliste getRezeptliste() {
		return rezeptliste;
	}

	/**
	 * @return the kategorie
	 */
	public ArrayList<Kategorie> getKategorie() {
		return kategorie;
	}
	
	public Kategorie getKategorie(String kategorieName) {
		Kategorie kategorieGesucht = null;
		for(int i=0; i< kategorie.size();i++){
			if(kategorie.get(i).getName().equals(kategorieName)){
				kategorieGesucht = kategorie.get(i);
			}
		}
		return kategorieGesucht;
	}
	
	public void addKategorie(Kategorie eKategorie) {
		this.kategorie.add(eKategorie);
	}



}
