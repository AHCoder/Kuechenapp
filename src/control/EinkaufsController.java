package control;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.EinheitException;
import view.Einheit;
import model.KuechenApp;
import model.Einkaufsliste;
import model.Artikel;


public class EinkaufsController {
	//TODO Jenny

	private KochController kochController;

	/**
	 * Konstruktor
	 * 
	 * @param kochCtl
	 *			Die Referenz auf den zentralen Controller, der zum Austausch zwischen den
	 *          Controllern dient.
	 * @throws NullPointerException
	 *             Die Runtime-Exception wird geworfen, wenn der Parameter null ist.          
	 * @postconditions Das Attribut kochController verweist auf den zentralen Controller         
	 */
	public EinkaufsController(KochController kochCtl) throws NullPointerException {
		if ( kochCtl == null ) throw new NullPointerException("Referenz ist null!");
		else this.kochController = kochCtl;
	}
	/**
	 * Fügt einen Artikel auf die Einkaufsliste mit den übergebenen Parametern hinzu.
	 * 
	 * @param artikel
	 * 			Bezeichnung des Artikels der hinzugefügt werden soll. Es darf weder null, noch ein leerer String übergeben werden.
	 * @param menge
	 * 			Der Wert für die Menge des Artikels, der hinzugefügt werden soll. Es darf kein negativer Wert übergeben werden. 
	 * @param einheit
	 * 			Bezeichnung der Menge des Artikels, der hinzugefügt werden soll.
	 * @throws EinheitException 
	 * @postconditions Die Einkaufsliste enthält einen Artikel mehr.
	 */
	public void addArtikel(String artikel, double menge, Einheit einheit) throws EinheitException {
		KuechenApp kueche = this.kochController.getKuechenApp();
		Einkaufsliste einkauf = kueche.getEinkaufsliste();
		Artikel art = einkauf.getArtikel(artikel);
			
		if ( einkauf.getArtikelList().isEmpty() || einkauf.getArtikel(artikel) == null || einkauf.getArtikelList() == null) {		//Abfrage ob bereits irgendein Artikel auf der Einkaufliste vorhanden ist
			einkauf.addElement(artikel, einheit, menge);
		} 
		else if (!einkauf.getArtikel(artikel).getMasseinheit().equals(einheit)){
			if(einkauf.getArtikel(artikel).getMasseinheit() == Einheit.KILOGRAMM && einheit == Einheit.GRAMM){
				art.setMenge((Einheit.toGr(einkauf.getArtikel(artikel).getMenge())) + (menge));
				art.setMasseinheit(Einheit.GRAMM);
			}
			else if(einkauf.getArtikel(artikel).getMasseinheit() == Einheit.GRAMM && einheit == Einheit.KILOGRAMM){
				art.setMenge((einkauf.getArtikel(artikel).getMenge()) + Einheit.toGr(menge));
				art.setMasseinheit(Einheit.GRAMM);
			}
			else if(einkauf.getArtikel(artikel).getMasseinheit() == Einheit.LITER && einheit == Einheit.MILLILITER){
				art.setMenge((Einheit.toMl(einkauf.getArtikel(artikel).getMenge())) + (menge));
				art.setMasseinheit(Einheit.MILLILITER);
			}
			else if(einkauf.getArtikel(artikel).getMasseinheit() == Einheit.MILLILITER && einheit == Einheit.LITER){
				art.setMenge((einkauf.getArtikel(artikel).getMenge() + Einheit.toMl(menge)));
				art.setMasseinheit(Einheit.MILLILITER);
			}else{
			throw new EinheitException();
			}
		}
		else {
				art.setMenge(menge + einkauf.getArtikel(artikel).getMenge());	//Wenn ja, nur Menge addieren
				art.setMasseinheit(einheit);
		}
		
		//Update der Masseinheiten
		art = einkauf.getArtikel(artikel);
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
	 * Entfernt alle, in der übergebenen String Liste, enthaltenen Artikel von der Einkaufsliste.
	 * 
	 * @param artikelList
	 * 			Liste mit Namen (Strings) der Artikel, die selectiert wurden und entfernt werden sollen.
	 * @postconditions Die übergebenen Artikel wurden von der  Einkaufsliste entfernt.
	 */
	public void deleteArtikel(ArrayList<String> artikelList) {
		
		if ( artikelList.isEmpty()) {		// zu übergebene Stringliste darf nicht leer sein.
				throw new NullPointerException("Keine Elemente ausgewählt.");
			}else{
				KuechenApp kueche = this.kochController.getKuechenApp();
				Einkaufsliste einkauf = kueche.getEinkaufsliste();
				ArrayList<Artikel> einkaufsList = einkauf.getArtikelList();
				
				//Einkaufsliste leer
				if(einkaufsList.isEmpty()){
					throw new NullPointerException("Einkaufsliste ist leer.");
				}
			
		
					
			for (int i = 0; i < artikelList.size(); i++) {				//suchen nach übergebenen Artikeln auf der Artikelliste
				for (int j = 0; j < einkaufsList.size(); j++) {
					if ( artikelList.get(i).equals(einkaufsList.get(j).getName())) { 		//wenn vorhanden, löschen
						einkaufsList.remove(einkaufsList.get(j));
					}
				}
			}
		}
	}

	/**
	 * Ändert die Werte eines ausgewählten Artikels auf der Einkaufsliste. Der Artikel wird mit den übergebenen Parametern geändert.
	 * 
	 * @param artikelAlt
	 * 			Die alte Bezeichnug des Artikels, worüber der Artikel gefunden werden kann.
	 * @param artikelNeu
	 * 			Bezeichnung des Artikels der hinzugefügt werden soll. Es darf weder null, noch ein leerer String übergeben werden.
	 * @param menge
	 * 			Der Wert für die Menge des Artikels, der hinzugefügt werden soll. Es darf kein negativer Wert übergeben werden.
	 * @param einheit
	 * 			Bezeichnung der Menge des Artikels, der hinzugefügt werden soll.
	 * @preconditions Es muss mindetsens ein Artikel auf der Einkaufslsite existieren.
	 * @postconditions Werte eines Artikels wurden geändert. Die Anzahl der Artikel auf der Einkaufsliste bleibt gleich.
	 */
	public void changeArtikel(String artikelAlt, String artikelNeu, double menge, Einheit einheit) {
		KuechenApp kueche = this.kochController.getKuechenApp();
		Einkaufsliste einkauf = kueche.getEinkaufsliste();
		
		if ( !einkauf.getArtikelList().isEmpty()) { 		// Abfragen ob Artikel auf der Einkaufsliste vorhanden
			Artikel art = einkauf.getArtikel(artikelAlt);
			
			if ( artikelAlt.equals(artikelNeu)) { 			//Abfragen ob Name auch geändert wurde
				art.setMenge(menge);						// Wenn Name nicht geändert, nur Menge und Einheit ändern
				art.setMasseinheit(einheit);
			} else {									//Name wurde geändert, dann alle drei Attribute neu setzen
				Artikel artikel = einkauf.getArtikel(artikelNeu);
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
	 * Fügt alle selectierten Artikel von der Einkaufsliste dem Lager hinzu und entfernt diese von der Einkaufsliste.
	 *  
	 * @param artikelList
	 * 			Liste mit Namen (Strings) der Artikel, die selectiert wurden und zu Lager hinzugefügt werden sollen. Diese werden 
	 * 			im Anschluss von der Einkaufsliste entfrent.
	 * @throws EinheitException 
	 * @postconditions Die übergebenen Artikel wurden von der Einkuafsliste entfent und das Lager wird verändert.
	 */
	public void addToBestand(ArrayList<String> artikelList) throws EinheitException {
		KuechenApp kueche = this.kochController.getKuechenApp();
		Einkaufsliste einkauf = kueche.getEinkaufsliste();
		
				
		for (int i = 0; i < artikelList.size(); i++) {			//von allen Artikeln die ins Lager sollen, werden Attribute geholt 
																//und dem Lager angehängt
			Artikel posArtikel = einkauf.getArtikel(artikelList.get(i));
			
			String name = posArtikel.getName();
			Einheit einheit = posArtikel.getMasseinheit();
			double menge = posArtikel.getMenge();
			this.kochController.getBestandsController().addArtikel(name, menge, einheit);
		}
		this.deleteArtikel(artikelList); 		//die ausgewählten Artikel werden danach von der Einkaufsliste gelöscht
	}

	/**
	 * Druckt die aktuelle Einkaufsliste.
	 * 
	 * @postconditions Die Einkaufsliste wird nicht verändert.
	 */
	public void drucken() {
		String print = "EINKAUFSLISTE\n";
		KuechenApp kueche = this.kochController.getKuechenApp();
		Einkaufsliste einkauf = kueche.getEinkaufsliste();
		
		for (Artikel x : einkauf.getArtikelList()) {
			print += "- " + x.getName() + " " + x.getMenge() + " " + x.getMasseinheit() + "\n";
		}
		FileWriter fw;			
				try {
					fw = new FileWriter("Einkaufsliste.txt");			//ins Arbeitsverzeichnis speichern
				    fw.write(print);
                    fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                 
        //}

	}

}
