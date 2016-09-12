package control;

import java.util.ArrayList;

import exceptions.EinheitException;
import view.Einheit;
import model.Artikel;
import model.Kategorie;
import model.KuechenApp;
import model.Lager;
import model.Rezept;
import model.Rezeptliste;

/**
 * Controller zur Verwaltung der Rezepte.
 * 
 * @author Ajnol, Patrick
 *
 */
public class RezeptController {

	/**
	 * Referenz auf den Main-Kochcontroller
	 */
	private KochController kochCtrl;

	/**
	 * Konstruktor.
	 * 
	 * 
	 * @param kochCtrl
	 *            Die Referenz auf den Main-Controller
	 * 
	 * @throws NullPointerException
	 *             Die Exception wird geworfen wenn der Parameter null ist.
	 * 
	 * @postconditions Das Attribut kochCtrl verweist auf den Main-Controller,
	 *                 sofern nicht null uebergeben wird.
	 */
	public RezeptController(KochController kochController)
			throws NullPointerException {
		if (kochController == null) {
			throw new NullPointerException("Referenz ist null!");
		} else {
			this.kochCtrl = kochController;
		}
	}

	/**
	 * Erstellt ein neues Rezept.
	 * 
	 * @param name
	 *            Der Name von dem Rezept, das erstellt werden soll.
	 * @param beschreibung
	 *            Die Beschreibung des Rezepts
	 * @param zubereitung
	 *            Die Zubereitung des Rezepts
	 * @param portionenzahl
	 *            Die Anzahl der Personen, fuer die das Rezept gedacht ist.
	 * @param kategorie
	 *            Die Liste von Kategorien, die das Rezept haben soll.
	 * @param zubereitungszeit
	 *            Die Zubereitungszeit vom Rezept
	 * @param zutaten
	 *            Die Liste von Zutaten, die das Rezept haben soll.
	 * 
	 * @throws NullPointerException
	 *             Diese Exception wird geworfen wenn der Name, die
	 *             Portionenzahl, die Kategorie oder die Zutatenliste null sind.
	 * @throws IllegalArgumentException
	 *             Diese Exception wird geworfen wenn irgendein Parameter vom
	 *             falschen Typ uebergeben wird.
	 * 
	 * @postconditions Ein neues Rezept-Objekt mit den uebergebenen Werten wird
	 *                 erzeugt.
	 */
	
	public void deleteRezept(String name) {
		KuechenApp kueche = kochCtrl.getKuechenApp();
		ArrayList<Kategorie> katList = kueche.getKategorie(); 
		Rezeptliste rezeptlist = kueche.getRezeptliste();
		Rezept muellRezept = rezeptlist.sucheRezept(name);
		
		if (muellRezept != null){
			for (Kategorie kat : katList) {
				kat.removeRezept(muellRezept.getName());
			}
			System.out.println("Stinkt");
			rezeptlist.removeRezept(name);
		}
		
		
	}
	
	
	public void createRezept(String name, String beschreibung,
			String zubereitung, int portionenzahl, ArrayList<String> kategorie,
			String zubereitungszeit, ArrayList<String> zutaten)
			throws NullPointerException, IllegalArgumentException {
		
		/*
		 * createRezept wird von GRezeptErstellen, changeRezept und ImportRezept aufgerufen
		 * dort wird bereits sichergestellt das alle wichtigen Angaben vorhanden sind
		 * zur Sicherheit wird hier nochmal geprüft das auch wirklich ein Rezeptname 
		 * angegeben wurde
		 * */
		if(name.equals("")){
			throw new IllegalArgumentException("Kein Rezeptname angegeben");
		}else if(!(kochCtrl.getKuechenApp().getRezeptliste().getRezeptliste().isEmpty()) 
				&& (kochCtrl.getKuechenApp().getRezeptliste().sucheRezept(name) != null)){
			throw new IllegalArgumentException("Rezeptname bereitsvorhanden");
		}
		// Kategorie umwandeln und in ArrayList speichern
		ArrayList<Kategorie> katListe = new ArrayList<Kategorie>();
		ArrayList<Kategorie> kochKatListe = kochCtrl.getKuechenApp().getKategorie();
		for (String neueKat : kategorie) {
			try {
				Boolean katVorhanden = false;
				for (Kategorie kat : kochKatListe) {
					if (kat.getName().equals(neueKat)) {
						katListe.add(kat);
						katVorhanden = true;
					}
				}
				// Wenn eine Kategorie noch nie benutzt wurde, muss Sie neu
				// angelegt werden und zu den Kategorien hinzugefügt werden
				if (!katVorhanden) {
					ArrayList<Rezept> rezKatList = new ArrayList<Rezept>();
					Kategorie kat = new Kategorie(neueKat, rezKatList);
					kochCtrl.getKuechenApp().addKategorie(kat);;
					katListe.add(kat);
				}
			} catch (Exception e) {
				if (e instanceof NullPointerException) {
					throw new NullPointerException(
							"Fehler bei den Kategorien (NullPointerException)");
				} else if (e instanceof IllegalArgumentException) {
					throw new IllegalArgumentException(
							"Fehler bei einem Kategorien (IllegalArgumentException)");
				}
			}
		}

		// Zutaten umwandeln und in ArrayList speichern
		ArrayList<Artikel> artListe = new ArrayList<Artikel>();
		for (String art : zutaten) {
			String[] artTeile = art.split(",");
			try {
				String artName = artTeile[0];
				double artMenge = Double.parseDouble(artTeile[1]);
				Einheit artEinheit = Einheit.valueOf(artTeile[2]);				
				artListe.add(new Artikel(artName, artEinheit, artMenge));
			} catch (Exception e) {
				if (e instanceof NullPointerException) {
					throw new NullPointerException(
							"Fehler bei einem Artikel (NullPointerException)");
				} else if (e instanceof IllegalArgumentException) {
					throw new IllegalArgumentException(
							"Fehler bei einem Artikel (IllegalArgumentException)");
				}
			}
		}

		try {
			// Das neue Rezept anlegen
			Rezept neuesRez = new Rezept(name, beschreibung, zubereitung,
					portionenzahl, zubereitungszeit, katListe, artListe);
			
			// neues Rezept der Rezeptliste hinzufügen
			kochCtrl.getKuechenApp().getRezeptliste().addRezept(neuesRez);
			
			// Rezept bei den verwendeten Kategorien hinzugefügen
			for (String neueKat : kategorie) {
				for (Kategorie kat : kochCtrl.getKuechenApp().getKategorie()) {
					if (kat.getName().equals(neueKat)) {
						kat.addRezept(neuesRez);
					}
				}
			}
		} catch (Exception e) {
			if (e instanceof NullPointerException) {
				throw new NullPointerException(
						"Fehler beim Erstellen des Rezeptes (NullPointerException)");
			} else if (e instanceof IllegalArgumentException) {
				throw new IllegalArgumentException(
						"Fehler beim Erstellen des Rezeptes (IllegalArgumentException)");
			}
		}
	}

	/**
	 * Sucht ein Rezept.
	 * 
	 * @param name
	 *            Der Name von dem gesuchten Rezept.
	 * 
	 * @throws NullPointerException
	 *             Diese Exception wird geworfen wenn als Name ein leeres String
	 *             übergeben wird.
	 * @throws IllegalArgumentException
	 *             Diese Exception wird geworfen wenn als Name kein String
	 *             uebergeben wird.
	 * 
	 * @return Gibt das gesuchte Rezept-Objekt zurück, wenn es existiert. Sonst
	 *         wird null zurueckgegeben.
	 */

	public Rezept sucheRezept(String name) throws NullPointerException,
			IllegalArgumentException {

		// Exception wenn uebergebener Rezeptname null oder eine leerer String
		// ist
		if (name.isEmpty() || name == null) {
			throw new NullPointerException(
					"Gesuchtes Rezept ist null oder leerer String");
		}

		// Hole benoetigten Stuff und deligiere Aufgabe an Rezeptliste
		KuechenApp kueche = kochCtrl.getKuechenApp();
		Rezeptliste rezeptliste = kueche.getRezeptliste();
		return rezeptliste.sucheRezept(name);
	}

	public void addToEinkaufsliste(String name, int personen) throws NullPointerException, EinheitException {

		if (name != null) {

			// Kuechenapp und Rezeptliste holen
			KuechenApp kuechenApp = kochCtrl.getKuechenApp();
			Rezeptliste rezeptliste = kuechenApp.getRezeptliste();

			// Das Rezept, dessen Artikel zur Einkaufsliste hinzugefuegt werden
			// sollen, wird in der Rezeptliste gesucht
			Rezept rezeptToAdd = rezeptliste.sucheRezept(name);

			if (rezeptToAdd != null) {

				// Artikelliste des Rezepts und der Einkaufscontroller werden geholt
				ArrayList<Artikel> artikelliste = rezeptToAdd.getArtikel();
				EinkaufsController einkaufsController = kochCtrl.getEinkaufsController();
				Lager lager = kuechenApp.getLager();
				double vorhandeneMenge;
				double hinzuzufuegendeMenge;
				String artikelName;
				Einheit vorhandeneEinheit;
				Einheit hinzuzufuegendeEinheit;
				// Man geht die Artikelliste durch
				for (Artikel artikel : artikelliste) {
					hinzuzufuegendeEinheit = artikel.getMasseinheit();
					hinzuzufuegendeMenge = artikel.getMenge()*personen;
					// Einheit des hinzuzufuegenden Artikels standardisieren
					if(artikel.getMasseinheit() == Einheit.KILOGRAMM) {
						hinzuzufuegendeMenge = artikel.getMenge()*1000;
						hinzuzufuegendeEinheit = Einheit.GRAMM;
					}
					if(artikel.getMasseinheit() == Einheit.LITER) {
						hinzuzufuegendeMenge = artikel.getMenge()*1000;
						hinzuzufuegendeEinheit = Einheit.MILLILITER;
					}
					
					
					// Artikel im Lager vorhanden?
					if(lager.containsArtikel(artikel)) { 					
						artikelName = artikel.getName();
						vorhandeneMenge = lager.getArtikel(artikelName).getMenge();
						vorhandeneEinheit = lager.getArtikel(artikelName).getMasseinheit();
						
						// Falls Einheit KG oder L, dann umrechnen
						if(vorhandeneEinheit == Einheit.KILOGRAMM) {
							lager.getArtikel(artikelName).setMenge(vorhandeneMenge*1000);
							vorhandeneMenge = vorhandeneMenge*1000;
							lager.getArtikel(artikelName).setMasseinheit(Einheit.GRAMM);
						}
						if(vorhandeneEinheit == Einheit.LITER) {
							lager.getArtikel(artikelName).setMenge(vorhandeneMenge*1000);
							vorhandeneMenge = vorhandeneMenge*1000;
							lager.getArtikel(artikelName).setMasseinheit(Einheit.MILLILITER);
						}
						
						// Im Bestand ist weniger vorhanden als benoetigt
						if(vorhandeneMenge<hinzuzufuegendeMenge) {
							einkaufsController.addArtikel(artikel.getName(), (hinzuzufuegendeMenge-vorhandeneMenge), hinzuzufuegendeEinheit);
						}
					} else {
						einkaufsController.addArtikel(artikel.getName(), (hinzuzufuegendeMenge), hinzuzufuegendeEinheit);
					}		
				}
			} else {
				throw new NullPointerException(
						"Das Rezept, das hinzugefuegt werden soll, ist null!");
			}

		} else {
			throw new NullPointerException(
					"Der uebergebene Name von dem Rezept ist null!");
		}

	}
	
	
	/**
	 * Fuegt die Artikel eines Rezepts zur Einkaufsliste hinzu.
	 * 
	 * @param name
	 *            Name von dem Rezept, dessen Artikel zur Einkaufsliste
	 *            hinzugefuegt werden sollen.
	 * 
	 * @throws NullPointerException
	 *             Diese Exception wird geworfen wenn als Name ein leeres String
	 *             übergeben wird.
	 * @throws EinheitException
	 * 
	 * @postconditions Die Artikel von dem uebergebenen Rezept erscheinen in der
	 *                 Einkaufsliste.
	 */
	public void addToEinkaufsliste(String name) throws NullPointerException, EinheitException {

		if (name != null) {

			// Kuechenapp und Rezeptliste holen
			KuechenApp kuechenApp = kochCtrl.getKuechenApp();
			Rezeptliste rezeptliste = kuechenApp.getRezeptliste();

			// Das Rezept, dessen Artikel zur Einkaufsliste hinzugefuegt werden
			// sollen, wird in der Rezeptliste gesucht
			Rezept rezeptToAdd = rezeptliste.sucheRezept(name);

			if (rezeptToAdd != null) {

				// Artikelliste des Rezepts und der Einkaufscontroller werden geholt
				ArrayList<Artikel> artikelliste = rezeptToAdd.getArtikel();
				EinkaufsController einkaufsController = kochCtrl.getEinkaufsController();
				Lager lager = kuechenApp.getLager();

				// Man geht die Artikelliste durch
				for (Artikel artikel : artikelliste) {

					boolean artikelPresent = kuechenApp.getEinkaufsliste().containsArtikel(artikel);
					Artikel lagerArtikel = lager.getArtikel(artikel.getName());
					Artikel einkaufslisteArtikel = kuechenApp.getEinkaufsliste().getArtikel(artikel.getName());
					
					Einheit artikelEinheit = artikel.getMasseinheit();
					double artikelMenge = artikel.getMenge();

					// Falls der Artikel nicht in der Einkaufsliste vorhanden ist
					// und auch nicht im Lager wird er hinzugefügt
					if (artikelPresent == false && lager.containsArtikel(artikel) == false) {
						einkaufsController.addArtikel(artikel.getName(), artikel.getMenge(), artikel.getMasseinheit());
					}
					
					// wenn der Artikel im Lager vorhanden ist
					else if(lager.containsArtikel(artikel) == true){
						
						Einheit lagerEinheit = lagerArtikel.getMasseinheit();
						Einheit einkaufsEinheit = einkaufslisteArtikel.getMasseinheit();
						
						double lagerMenge = lagerArtikel.getMenge();
						double einkaufsMenge = einkaufslisteArtikel.getMenge();
						
						// Pruefen, ob die Einheit ungleich ist
						if (!lagerEinheit.equals(artikelEinheit)){
							
							//--------------------im Lager kg und atikel g?------------------------------------------------
							if(lagerEinheit == Einheit.KILOGRAMM && artikelEinheit == Einheit.GRAMM){
								
								artikelMenge = Einheit.toKg(artikelMenge);
								
								//Wenn man mehr braucht als man hat 
								//und der Artikel noch nicht auf der EKListe steht wird die fehlende Menge hinzugefügt
								if((lagerMenge - artikelMenge) < 0 && artikelPresent == false){
									einkaufsController.addArtikel(lagerArtikel.getName(), 
											(artikelMenge - lagerMenge), lagerEinheit);
								}
								
								//wenn der Artikel schon auf der EKListe steht wird nochmal die Einheit überprüft
								else if((lagerMenge - artikelMenge) < 0 && artikelPresent == true){
									
									if(einkaufsEinheit == Einheit.KILOGRAMM){
										
										einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
												artikelMenge - lagerMenge + einkaufsMenge, lagerEinheit);
									}
									
									else if(einkaufsEinheit == Einheit.GRAMM){
										
										einkaufsMenge = Einheit.toKg(einkaufsMenge);
										
										einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
												artikelMenge - lagerMenge + einkaufsMenge, lagerEinheit);
									}
								}
							}
							
							//------------------------im Lager g und artikel kg?------------------------------------------------
							else if(lagerEinheit == Einheit.GRAMM && artikelEinheit == Einheit.KILOGRAMM){
								
								lagerMenge = Einheit.toKg(lagerMenge);
								
								if((lagerMenge - artikelMenge) < 0 && artikelPresent == false){
									
									einkaufsController.addArtikel(lagerArtikel.getName(), 
											(artikelMenge - lagerMenge), artikelEinheit);
								}
								//wenn der Artikel schon auf der EKListe steht wird nochmal die Einheit überprüft
								else if((lagerMenge - artikelMenge) < 0 && artikelPresent == true){
									
									if(einkaufsEinheit == Einheit.KILOGRAMM){
										
										einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
												artikelMenge - lagerMenge + einkaufsMenge, artikelEinheit);
									}
									else if(einkaufsEinheit == Einheit.GRAMM){
										
										einkaufsMenge = Einheit.toKg(einkaufsMenge);
										
										einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
												artikelMenge - lagerMenge + einkaufsMenge, artikelEinheit);
									}
								}
							}
							
							//-----------------------------im Lager l und artikel ml?-------------------------------------------
							else if(lagerEinheit == Einheit.LITER && artikelEinheit == Einheit.MILLILITER){
								
								artikelMenge = Einheit.toKg(artikelMenge);
								
								if((lagerMenge - artikelMenge) < 0 && artikelPresent == false){
									
									einkaufsController.addArtikel(lagerArtikel.getName(), 
											artikelMenge - lagerMenge, lagerEinheit);
								}
								//wenn der Artikel schon auf der EKListe steht wird nochmal die Einheit überprüft
								else if((lagerMenge - artikelMenge) < 0 && artikelPresent == true){
									
									if(einkaufsEinheit == Einheit.LITER){
										
										einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
												artikelMenge - lagerMenge + einkaufsMenge, lagerEinheit);
									}
									else if(einkaufsEinheit == Einheit.MILLILITER){
										
										einkaufsMenge = Einheit.toKg(einkaufsMenge);
										
										einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
												artikelMenge - lagerMenge + einkaufsMenge, lagerEinheit);
									}
								}
							}
							
							//-----------------------------im Lager ml und artikel l?--------------------------------------
							else if(lagerEinheit == Einheit.MILLILITER && artikelEinheit == Einheit.LITER){
								
								lagerMenge = Einheit.toKg(lagerMenge);
								
								if((lagerMenge - artikelMenge) < 0 && artikelPresent == false){
									
									einkaufsController.addArtikel(lagerArtikel.getName(), 
											(artikelMenge - lagerMenge), artikelEinheit);
								}
								
								//wenn der Artikel schon auf der EKListe steht wird nochmal die Einheit überprüft
								else if((lagerMenge - artikelMenge) < 0 && artikelPresent == true){
									
									if(einkaufsEinheit == Einheit.LITER){
										
										einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
												artikelMenge - lagerMenge + einkaufsMenge, artikelEinheit);
									}
									else if(einkaufsEinheit == Einheit.MILLILITER){
										
										einkaufsMenge = Einheit.toLi(einkaufsMenge);
										
										einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
												artikelMenge - lagerMenge + einkaufsMenge, artikelEinheit);
									}
								}
							}else{
								throw new EinheitException();
							}
						}else{
							
							if((lagerMenge - artikelMenge) < 0 && artikelPresent == false){
								
								einkaufsController.addArtikel(lagerArtikel.getName(), 
										artikelMenge - lagerMenge, artikelEinheit);
							}
							
							else if((lagerMenge - artikelMenge) < 0 && artikelPresent == true){
								
								if(einkaufsEinheit == Einheit.KILOGRAMM && lagerEinheit == Einheit.KILOGRAMM){
									
									einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
											artikelMenge - lagerMenge + einkaufsMenge, lagerEinheit);
								}
								else if(einkaufsEinheit == Einheit.GRAMM && lagerEinheit == Einheit.GRAMM){
									
									einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
											artikelMenge - lagerMenge + einkaufsMenge, lagerEinheit);
								}
								
								else if(einkaufsEinheit == Einheit.KILOGRAMM && lagerEinheit == Einheit.GRAMM){
									
									einkaufsMenge = Einheit.toGr(einkaufsMenge);
									
									einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
											artikelMenge - lagerMenge + einkaufsMenge, lagerEinheit);
								}
								
								else if(einkaufsEinheit == Einheit.GRAMM && lagerEinheit == Einheit.KILOGRAMM){
									
									einkaufsMenge = Einheit.toKg(einkaufsMenge);
									
									einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
											artikelMenge - lagerMenge + einkaufsMenge, lagerEinheit);
								}
								
								else if(einkaufsEinheit == Einheit.LITER && lagerEinheit == Einheit.LITER){
									
									einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
											artikelMenge - lagerMenge + einkaufsMenge, lagerEinheit);
								}
								
								else if(einkaufsEinheit == Einheit.MILLILITER && lagerEinheit == Einheit.MILLILITER){
									
									einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
											artikelMenge - lagerMenge + einkaufsMenge, lagerEinheit);
								}
								
								else if(einkaufsEinheit == Einheit.LITER && lagerEinheit == Einheit.MILLILITER){
									
									einkaufsMenge = Einheit.toMl(einkaufsMenge);
									
									einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
											artikelMenge - lagerMenge + einkaufsMenge, lagerEinheit);
								}
								
								else if(einkaufsEinheit == Einheit.MILLILITER && lagerEinheit == Einheit.LITER){
									
									einkaufsMenge = Einheit.toLi(einkaufsMenge);
									
									einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
											artikelMenge - lagerMenge + einkaufsMenge, lagerEinheit);
								}
							}
						}
					}
					
					
					
					
					
					// Falls der Artikel schon in der Einkaufsliste vorhanden ist
					// aber nicht im Lager
					else if (artikelPresent == true && lager.containsArtikel(artikel) == false) {
						
						Einheit einkaufsEinheit = einkaufslisteArtikel.getMasseinheit();
						double einkaufsMenge = einkaufslisteArtikel.getMenge();

						//Prüfen, ob die Einheit ungleich ist
						if (!einkaufsEinheit.equals(artikelEinheit)){
							
							if(einkaufsEinheit == Einheit.KILOGRAMM && artikelEinheit == Einheit.GRAMM){
								
								artikelMenge = Einheit.toKg(artikelMenge);
								
								einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
										einkaufsMenge + artikelMenge, artikelEinheit);
							}
							
							else if(einkaufsEinheit == Einheit.GRAMM && artikelEinheit == Einheit.KILOGRAMM){
								
								einkaufsMenge = Einheit.toKg(einkaufsMenge);
								
								einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
										einkaufsMenge + artikelMenge, artikelEinheit);
							}
							
							else if(einkaufsEinheit == Einheit.LITER && artikelEinheit == Einheit.MILLILITER){
								
								artikelMenge = Einheit.toLi(artikelMenge);
								
								einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
										einkaufsMenge + artikelMenge, artikelEinheit);
							}
							else if(einkaufsEinheit == Einheit.MILLILITER && artikelEinheit == Einheit.LITER){
								
								einkaufsMenge = Einheit.toLi(einkaufsMenge);
								
								einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
										einkaufsMenge + artikelMenge, artikelEinheit);
							}else{
								throw new EinheitException();
							}
						}else{
							
							einkaufsController.changeArtikel(einkaufslisteArtikel.getName(), einkaufslisteArtikel.getName(), 
									einkaufsMenge + artikelMenge, einkaufsEinheit);
						}
					}
				}

			} else {
				throw new NullPointerException(
						"Das Rezept, das hinzugefuegt werden soll, ist null!");
			}

		} else {
			throw new NullPointerException(
					"Der uebergebene Name von dem Rezept ist null!");
		}

	}

	/**
	 * Aendert die Attribute von einem Rezept.
	 * 
	 * @param alterRezName 
	 * 			  Der Name von dem Rezept, das geaendert werden soll.
	 * @param name
	 *            Der neue Rezeptname.
	 * @param beschreibung
	 *            Die neue Beschreibung von dem Rezept.
	 * @param zubereitung
	 *            Die geaenderte Zubereitung vom Rezept.
	 * @param portionenzahl
	 *            Die geaenderte Portionenzahl vom Rezept.
	 * @param kategorie
	 *            Die Liste der Kategorien von dem Rezept.
	 * @param zubereitungszeit
	 *            Die neue Zubereitungszeit vom Rezept.
	 * @param zutaten
	 *            Die Liste von Zutaten in dem Rezept.
	 * 
	 * @throws NullPointerException
	 *             Diese Exception wird geworfen wenn der Name, die
	 *             Portionenzahl, die Kategorie oder die Zutatenliste null sind.
	 * @throws IllegalArgumentException
	 *             Diese Exception wird geworfen wenn irgendein Parameter vom
	 *             falschen Typ uebergeben wird.
	 * 
	 * @postconditions Die Attribute von dem Rezept (das geaendert werden soll)
	 *                 wurden durch die neuen uebergebenen Werte ersetzt.
	 */
	public void changeRezept(String alterRezName, String name, String beschreibung,
			String zubereitung, int portionenzahl, ArrayList<String> kategorie,
			String zubereitungszeit, ArrayList<String> zutaten)
			throws NullPointerException, IllegalArgumentException {
		Rezept altesRez;
		if(alterRezName != null && !alterRezName.equals("")){
			altesRez = kochCtrl.getKuechenApp().getRezeptliste().sucheRezept(alterRezName);
		}else{
			throw new IllegalArgumentException("Rezeptname des Rezept, das geändert werden soll leer oder null");
		}

		try {		
			if (altesRez != null) {
				// Aktuelles Rezept löschen und neues erstellen
				Rezeptliste rezListe = kochCtrl.getKuechenApp().getRezeptliste();
				rezListe.getRezepte().remove(altesRez);
				createRezept(name, beschreibung, zubereitung, portionenzahl,
						kategorie, zubereitungszeit, zutaten);
			}
		} catch (Exception e) {
			if (e instanceof NullPointerException) {
				// Falls ein Fehler beim erstellen des neuen Rezeptes aufgetreten ist,
				// muss das alte wieder zur Rezeptliste hinzugefügt werden
				kochCtrl.getKuechenApp().getRezeptliste().addRezept(altesRez);
				throw new NullPointerException(
						"Fehler beim Ändern des Rezeptes (NullPointerException)");
			} else if (e instanceof IllegalArgumentException) {
				// Falls ein Fehler beim erstellen des neuen Rezeptes aufgetreten ist,
				// muss das alte wieder zur Rezeptliste hinzugefügt werden
				kochCtrl.getKuechenApp().getRezeptliste().addRezept(altesRez);
				throw new IllegalArgumentException(
						"Fehler beim Ändern des Rezeptes (IllegalArgumentException)");
			}
		}
	}

	/**
	 * Verringert im Bestand die Artikelwerte von dem Rezept, das gekocht wird,
	 * um die Mengen, die n Personen benoetigen.
	 * 
	 * @param rezeptName
	 *            Name von dem Rezept, das gekocht werden soll.
	 * @param personenzahl
	 *            Anzahl der Personen fuer die gekocht wird.
	 * @throws EinheitException 
	 * 
	 * @postconditions Die Artikelwerte in dem Bestand wurden geaendert.
	 */
	public void kochen(String rezeptName, int personenzahl) throws EinheitException {

		// Bestandscontroller, Bestand und die Artikelliste vom Rezept holen
		BestandsController bestandsController = kochCtrl.getBestandsController();
		Lager bestand = kochCtrl.getKuechenApp().getLager();
		ArrayList<Artikel> rezeptArtikelliste = kochCtrl.getKuechenApp().getRezeptliste().sucheRezept(rezeptName).getArtikel();

		for (int i = 0; i < rezeptArtikelliste.size(); i++) {

			Artikel currentArtikel = rezeptArtikelliste.get(i);

			// Pruefe ob der Artikel schon im Bestand ist
			if (bestand.containsArtikel(currentArtikel) == true) {
				
				// Prueft, ob die Einheit gleich ist
				if (!currentArtikel.getMasseinheit().equals(bestand.getArtikel(currentArtikel.getName()).getMasseinheit())){
					
					if(currentArtikel.getMasseinheit() == Einheit.KILOGRAMM && 
							bestand.getArtikel(currentArtikel.getName()).getMasseinheit() == Einheit.GRAMM){
						// Menge und Einheit so setzen, dass sie mit der Einheit im Bestand kompatibilisieren
						currentArtikel.setMenge((currentArtikel.getMenge() * 1000) + 
								bestand.getArtikel(currentArtikel.getName()).getMenge());
						currentArtikel.setMasseinheit(bestand.getArtikel(currentArtikel.getName()).getMasseinheit());
					}
					
					if(currentArtikel.getMasseinheit() == Einheit.GRAMM && 
							bestand.getArtikel(currentArtikel.getName()).getMasseinheit() == Einheit.KILOGRAMM){
						// Menge und Einheit so setzen, dass sie mit der Einheit im Bestand kompatibilisieren
						currentArtikel.setMenge((currentArtikel.getMenge() / 1000) + 
								bestand.getArtikel(currentArtikel.getName()).getMenge());
						currentArtikel.setMasseinheit(bestand.getArtikel(currentArtikel.getName()).getMasseinheit());
					}
					
					if(currentArtikel.getMasseinheit() == Einheit.LITER && 
							bestand.getArtikel(currentArtikel.getName()).getMasseinheit() == Einheit.MILLILITER){
						// Menge und Einheit so setzen, dass sie mit der Einheit im Bestand kompatibilisieren
						currentArtikel.setMenge((currentArtikel.getMenge() * 1000) + 
								bestand.getArtikel(currentArtikel.getName()).getMenge());
						currentArtikel.setMasseinheit(bestand.getArtikel(currentArtikel.getName()).getMasseinheit());
					}
					
					if(currentArtikel.getMasseinheit() == Einheit.MILLILITER && 
							bestand.getArtikel(currentArtikel.getName()).getMasseinheit() == Einheit.LITER){
						// Menge und Einheit so setzen, dass sie mit der Einheit im Bestand kompatibilisieren
						currentArtikel.setMenge((currentArtikel.getMenge() / 1000) + 
								bestand.getArtikel(currentArtikel.getName()).getMenge());
						currentArtikel.setMasseinheit(bestand.getArtikel(currentArtikel.getName()).getMasseinheit());
					}else{
						throw new EinheitException();
					}
				}else{
					
					//Pruefe ob die Menge von dem Artikel in dem Rezept
					// (mit Beruecksichtigung der Personenzahl)
					// kleiner/gleich ist als die im Bestand
					if ((currentArtikel.getMenge() * personenzahl) < bestand.getArtikel(currentArtikel.getName()).getMenge()) {

						// Wenn ja, ziehe die Menge von dem Artikel im Bestand ab
						bestandsController.changeArtikel(currentArtikel.getName(), currentArtikel.getName(), bestand.getArtikel(currentArtikel.getName()).getMenge() - (currentArtikel.getMenge() * personenzahl), currentArtikel.getMasseinheit());
					}
					
					// wenn die Mengen übereinstimmen
					else if ((currentArtikel.getMenge() * personenzahl) == bestand.getArtikel(currentArtikel.getName()).getMenge()) {
						ArrayList<String> loeschListe = new ArrayList<String>();
						loeschListe.add(currentArtikel.getName());
						bestandsController.deleteArtikel(loeschListe);
					}

					// Falls die Menge von dem Artikel groesser ist setzt man
					// die Menge im Bestand auf 0
					else if ((currentArtikel.getMenge() * personenzahl) > bestand.getArtikel(currentArtikel.getName()).getMenge()) {

						bestandsController.changeArtikel(currentArtikel.getName(), currentArtikel.getName(), 0, currentArtikel.getMasseinheit());
					}
					
					
				}
			}
		}

	}

}