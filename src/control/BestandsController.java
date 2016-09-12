package control;

import java.util.ArrayList;

import exceptions.EinheitException;
import view.Einheit;
import model.Artikel;
import model.Kategorie;
import model.Lager;
import model.KuechenApp;
import model.Rezept;
import model.Rezeptliste;

public class BestandsController {
	// TODO Lisa
	private KochController kochController;

	/**
	 * Konstruktor
	 * 
	 * @param kochCtl
	 *            Die Referenz auf den zentralen Controller, der zum Austausch
	 *            zwischen den Controllern dient.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn der Parameter null
	 *             ist.
	 * @postconditions Das Attribut kochController verweist auf den zentralen
	 *                 Controller
	 */

	public BestandsController(KochController kochCtl)
			throws NullPointerException {
		if (kochCtl == null)
			throw new NullPointerException("Referenz ist null!");
		else
			this.kochController = kochCtl;

	}

	/**
	 * Fügt einen Artikel in den Bestand mit den übergebenen Parametern hinzu.
	 * 
	 * @param artikel
	 *            Bezeichnung des Artikels der hinzugefügt werden soll. Es darf
	 *            weder null, noch ein leerer String übergeben werden.
	 * @param menge
	 *            Der Wert für die Menge des Artikels, der hinzugefügt werden
	 *            soll. Es darf kein negativer Wert übergeben werden.
	 * @param einheit
	 *            Bezeichnung der Menge des Artikels, der hinzugefügt werden
	 *            soll.
	 * @postconditions Der Bestand enthält einen Artikel mehr.
	 */

	public void addArtikel(String artikel, double menge, Einheit einheit) throws EinheitException{
		KuechenApp kueche = this.kochController.getKuechenApp();
		Lager lager = kueche.getLager();
		Artikel art = lager.getArtikel(artikel);
		
		//Abfrage ob bereits irgendein Artikel auf der Bestandsliste vorhanden ist
		if ( lager.getArtikelList().isEmpty() || lager.getArtikel(artikel) == null || lager.getArtikelList() == null) {		//Abfrage ob bereits irgendein Artikel auf der lagerliste vorhanden ist
			lager.addElement(artikel, einheit, menge);
		} 
		else if (!lager.getArtikel(artikel).getMasseinheit().equals(einheit)){
			if(lager.getArtikel(artikel).getMasseinheit() == Einheit.KILOGRAMM && einheit == Einheit.GRAMM){
				art.setMenge((Einheit.toGr(lager.getArtikel(artikel).getMenge())) + (menge));
				art.setMasseinheit(Einheit.GRAMM);
			}
			else if(lager.getArtikel(artikel).getMasseinheit() == Einheit.GRAMM && einheit == Einheit.KILOGRAMM){
				art.setMenge((lager.getArtikel(artikel).getMenge()) + Einheit.toGr(menge));
				art.setMasseinheit(Einheit.GRAMM);
			}
			else if(lager.getArtikel(artikel).getMasseinheit() == Einheit.LITER && einheit == Einheit.MILLILITER){
				art.setMenge((Einheit.toMl(lager.getArtikel(artikel).getMenge())) + (menge));
				art.setMasseinheit(Einheit.MILLILITER);
			}
			else if(lager.getArtikel(artikel).getMasseinheit() == Einheit.MILLILITER && einheit == Einheit.LITER){
				art.setMenge((lager.getArtikel(artikel).getMenge() + Einheit.toMl(menge)));
				art.setMasseinheit(Einheit.MILLILITER);
			}else{
			throw new EinheitException();
			}
		}
		
		else {
				//Wenn Artikel vorhanden und die Einheit übereinstimmt, Menge addieren
				art.setMenge(menge + lager.getArtikel(artikel).getMenge());	
				art.setMasseinheit(einheit);
		}
		
		//Update der Masseinheiten
		art = lager.getArtikel(artikel);
		if (art.getMasseinheit() == Einheit.GRAMM && art.getMenge() >= 1000) {
			art.setMenge(art.getMenge()/1000);
			art.setMasseinheit(Einheit.KILOGRAMM);
		}
		if (art.getMasseinheit() == Einheit.KILOGRAMM && art.getMenge() < 1) {
			art.setMenge(art.getMenge()*1000);
			art.setMasseinheit(Einheit.GRAMM);
		}
		if (art.getMasseinheit() == Einheit.MILLILITER && art.getMenge() >= 1000) {
			art.setMenge(art.getMenge()/1000);
			art.setMasseinheit(Einheit.LITER);
		}
		if (art.getMasseinheit() == Einheit.LITER && art.getMenge() < 1) {
			art.setMenge(art.getMenge()*1000);
			art.setMasseinheit(Einheit.MILLILITER);
		}
		
	}

	/**
	 * Ändert einen Artikel inm Bestand mit den übergebenen Parametern hinzu.
	 * 
	 * @param artikel
	 *            Bezeichnung des Artikels der geändert werden soll. Es darf
	 *            weder null, noch ein leerer String übergeben werden.
	 * @param menge
	 *            Neuer Wert für die Menge des Artikels, der geändert werden
	 *            soll. Es darf kein negativer Wert übergeben werden.
	 * @param einheit
	 *            Bezeichnung der Menge des Artikels, der geändert werden soll.
	 * @postconditions Der Artikel wurde um mindestens einen Wert geändert.
	 */

	public void changeArtikel(String artikelAlt, String artikelNeu, double menge, Einheit einheit) {
		KuechenApp kueche = this.kochController.getKuechenApp();
		Lager lager = kueche.getLager();		
		if ( !lager.getArtikelList().isEmpty()) { 		// Abfragen ob Artikel auf der Einkaufsliste vorhanden
			Artikel art = lager.getArtikel(artikelAlt);
			
			if ( artikelAlt.equals(artikelNeu)) { 			//Abfragen ob Name auch geändert wurde
				art.setMenge(menge);						// Wenn Name nicht geändert, nur Menge und Einheit ändern
				art.setMasseinheit(einheit);
			} else {									//Name wurde geändert, dann alle drei Attribute neu setzen
				Artikel artikel = lager.getArtikel(artikelNeu);
				if ( artikel != null) {
					artikel.setMenge(artikel.getMenge() + menge);
					ArrayList<String> list = new ArrayList<>();
					list.add(artikelAlt);
					deleteArtikel(list);
				} else {
					art.setName(artikelNeu);
					art.setMenge(menge);
					art.setMasseinheit(einheit);
				}
			}
		}
	}

	/**
	 * Entfernt alle, in der übergebenen String Liste, enthaltenen Artikel aus
	 * dem Bestand.
	 * 
	 * @param artikelList
	 *            Liste mit Namen (Strings) der Artikel, die selectiert wurden
	 *            und entfernt werden sollen.
	 * @postconditions Die übergebenen Artikel wurden aus dem Bestand entfernt.
	 */

	public void deleteArtikel(ArrayList<String> artikelList) throws NullPointerException{
		//übergebene Liste leer?
		if(artikelList.isEmpty()){
			throw new NullPointerException("Keine Elemente ausgewählt.");
		}else{

			KuechenApp kueche = this.kochController.getKuechenApp();
			Lager lager = kueche.getLager();
			ArrayList<Artikel> bestandsListe = lager.getArtikelList();
			
			//Bestand leer?
			if(bestandsListe.isEmpty()){
				throw new NullPointerException("Lager ist leer.");
			}
			//übergebene Artikelliste durchlaufen
			for (int i = 0; i < artikelList.size(); i++) {
				//Lager durchlaufen
				for (int j = 0; j < bestandsListe.size(); j++) {
					if (artikelList.get(i).equals(bestandsListe.get(j).getName())) {
						bestandsListe.remove(bestandsListe.get(j));
					}
				}
			}
		}
	}

	/**
	 * Aktualisiert die Kategorie "Vorschläge" mit passenden Rezepten zu den
	 * ausgewählten Artikeln.
	 * 
	 * @preconditions Bestehende Referenzen zwischen Rezepten und der Kategorie
	 *                "Vorschläge" werden entfernt.
	 * 
	 * @param artikelList
	 *            Liste mit Namen (Strings) der Artikel, die selectiert wurden
	 *            und zu denen passende Rezepte gesucht werden sollen.
	 * @postconditions Die Rezepte werden in der Kategorie Vorschläge
	 *                 gespeichert. Es werden nur Rezepte gespeichert, die ALLE
	 *                 Artikel der artikelList in ihrer Zutatenliste enthalten.
	 */
	// TODO Damian
	public void suggestRezept(ArrayList<String> artikelList) throws NullPointerException{
		//übergebene Liste leer?
		if(artikelList.isEmpty()){
			throw new NullPointerException("Keine Elemente ausgewählt.");
		}else{
		
			//Init
			KuechenApp kueche = this.kochController.getKuechenApp();
			ArrayList<Kategorie> kategorien = kueche.getKategorie();
			Kategorie kategorieVorschlaege = kueche.getKategorie("Vorschlaege");
			ArrayList<Rezept> vorgeschlageneRezepte;
			if(kategorieVorschlaege != null){
				//hole Kategorie: Vorschlaege
				for(int i = 0;i<kategorien.size();i++) {
					if(kategorien.get(i).getName().equals("Vorschlaege")) {
						kategorieVorschlaege = kategorien.get(i);
					}
				}
				
				//entferne alle bisherigen Rezepte aus der Kategorie Vorschlaege
				Rezept aktuellesRezept = null;
				ArrayList<Rezept> rezepteInVorschlage = kategorieVorschlaege.getRezepte();
				if (rezepteInVorschlage != null) {
					int vorschlaegeUrspruenglicheSize = rezepteInVorschlage.size();
					for(int i = 0;i<vorschlaegeUrspruenglicheSize;i++){
						aktuellesRezept = rezepteInVorschlage.get(0);
						aktuellesRezept.removeKategorie("Vorschlaege");
					}
				}			
				kategorieVorschlaege.setRezepte(new ArrayList<Rezept>());
			}else{
				kochController.getKuechenApp().getKategorie().add(new Kategorie("Vorschlaege", new ArrayList<Rezept>()));
				kategorieVorschlaege = kochController.getKuechenApp().getKategorie("Vorschlaege");
				
			}
			//Suche nach passenden Rezepten
			Rezeptliste rezeptliste = kueche.getRezeptliste();
			vorgeschlageneRezepte = rezeptliste.vorschlaege(artikelList);
			
			//Setze die gefundenen Rezepte in die Kategorie Vorschlaege
			kategorieVorschlaege.setRezepte(vorgeschlageneRezepte);
			
			//Trage die Kategorie Vorschlaege in den gefundenen Rezepten ein
			for(int i = 0;i<vorgeschlageneRezepte.size();i++) {
				vorgeschlageneRezepte.get(i).addKategorie(kategorieVorschlaege);
			}
		}
	}
	
}	
