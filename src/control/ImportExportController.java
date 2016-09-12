package control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import view.Einheit;

import model.Artikel;
import model.Kategorie;
import model.KuechenApp;
import model.Rezept;
import model.Rezeptliste;

/**
 * Controller für importieren & exportieren der Rezepte.
 * @author Ilker
 *
 */
public class ImportExportController {

	private KochController kochController;

	/**
	 * 
	 * @param kochController
	 * 		Die Referenz auf den zentralen Controller, der zum Austausch zwischen den
	 * 		Controllern dient.
	 * @throws NullPointerException
	 * 		Die Exception wird geworfen wenn der Parameter null ist.
	 * @postconditions
	 * 		Das Attribut kochController verweist auf den zentralen Controller
	 */
	public ImportExportController(KochController kochController) throws NullPointerException{
		if(kochController == null)
			throw new NullPointerException("Die Referenz ist null");
		else
			this.kochController = kochController;
	}
	
	/**
	 * Diese Methode kann die ausgew&auml;hlten Rezepte in eine Xml-Datei exportieren. 
	 * 
	 * @param auswahlList
	 * 		In der auswahlList befinden sich die Strings der Rezepten. Die auswahlListe
	 * 		darf nicht null sein.
	 * @param pfad
	 * 		Der Dateipfad pfad beschreibt den Speicherort der Xml-Datei. Des Weiteren muss
	 * 		der Pfad g&uuml;ltig sein.
	 * @postconditions
	 * 		Die ausgew&auml;hlten rezepten wurden in eine XML-Datei exportiert.
	 */
	public void export(ArrayList<String> auswahlList, String pfad) throws IOException, Exception{
	//TODO Ilker
		
		if(auswahlList.size() == 0){
			JOptionPane.showMessageDialog(null, "Es wurden keine Rezepte ausgewählt", "FEHLER", JOptionPane.ERROR_MESSAGE);
			throw new Exception();
		}
		//controller holen und rezeptliste drankommen
		KuechenApp kueche = this.kochController.getKuechenApp();
		Rezeptliste rezeptListe = kueche.getRezeptliste();
		
		//nötig für die Speicherung der gefundenen Rezepte
		ArrayList<Rezept> rezepte = new ArrayList<>();
		Rezept rez = null;
		
		//rezepte suchen
		for(String rezeptName : auswahlList){
			rez = rezeptListe.sucheRezept(rezeptName);
			rezepte.add(rez);
		}
		
		//Xml-Datei generieren
		String xml = "";
		
			//standardkonstruktor???
		Rezeptliste listeToXml = new Rezeptliste(rezepte);
		xml = listeToXml.toXML(rezepte);
		
		//xml-Datei erzeugen
		File meineDatei = new File(pfad);
		
		try{
			meineDatei.createNewFile();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		//xml-String in die xml-Datei reinschreiben
		FileWriter writeFile = null;
		BufferedWriter writer = null;
		
		try{
			writeFile = new FileWriter(meineDatei);
			writer = new BufferedWriter(writeFile);
			writer.write(xml);
		}
		catch(IOException e){
			e.printStackTrace();
			throw e;
		}
		finally{
			if(writer != null){
				try{
					writer.close();
				}
				catch(IOException e){
					e.printStackTrace();
					throw e;
				}
			}
		}
	}
	
	/**
	 * Diese Methode importiert die ausgewaehlten Rezepte und fuegt diese der Rezeptliste hinzu
	 * 
	 * @author Alexander
	 * 
	 * @param auswahlList 
	 * 		Die auswahlList enthaelt eine Liste mit den Namen der ausgewaehlten Rezepte. 
	 * 		Diese Liste wird dann mit der Rezeptliste aus dem XML-File verglichen und die gewaehlten Rezepte zur Rezptliste hinzugefuegt. 
	 * 
	 * @postconditions 
	 * 		Die ausgewaehlten Rezepte wurden der Rezeptliste hinzugefuegt.
	 */
	
	public void importieren(List<String> auswahlList, String pfad)throws NullPointerException, JDOMException, IOException {
		Rezeptliste XMLListe = readFile(pfad);
		Rezeptliste rezepte = kochController.getKuechenApp().getRezeptliste();
		try
		{
			for(String e : auswahlList)
			{
				if (XMLListe.sucheRezept(e) != null)
				{
					Rezept neu = XMLListe.sucheRezept(e);
					if(rezepte.sucheRezept(neu.getName()) != null && kochController.getKuechenApp().getRezeptliste().istLeer() != true)
					{
						int i=1;
						while(rezepte.sucheRezept(neu.getName()+"("+i+")") != null)
						{
							i++;
						}
						neu.setName(neu.getName()+"("+i+")");
					}
					
					//Nicht vorhandene Kategorien hinzufuegen
					for (Kategorie neueKat : neu.getKategorie()) {
						Boolean katVorhanden = false;
						for (Kategorie kat : kochController.getKuechenApp().getKategorie()) {
							if (kat.getName().equals(neueKat.getName())) {
								katVorhanden = true;
							}
						}
						if (!katVorhanden) {
							
							kochController.getKuechenApp().getKategorie().add(new Kategorie(neueKat.getName(), new ArrayList<Rezept>()));
							kochController.getKuechenApp().getKategorie(neueKat.getName()).addRezept(neu);
							
						}
						kochController.getKuechenApp().getKategorie(neueKat.getName()).addRezept(neu);
					}
					kochController.getKuechenApp().getRezeptliste().addRezept(neu);
				}
			}
		}
		
		catch(Exception e)
		{
			if(e instanceof NullPointerException){
				throw new NullPointerException("Keine Rezepte in der Auswahl");
			}
			if(e instanceof JDOMException){
				throw new JDOMException("Problem beim lesen der XML-Datei");
			}
			if (e instanceof IOException)
			{
				throw new IOException("Pfad nicht vorhanden");
			}
		}

	}
	
//	public void importieren(List<String> auswahlList, String pfad)throws NullPointerException, JDOMException, IOException {
//		Rezeptliste XMLListe = readFile(pfad);
//		Rezeptliste rezepte = kochController.getKuechenApp().getRezeptliste();
//		try
//		{
//			for(String e : auswahlList)
//			{
//				if (XMLListe.sucheRezept(e) != null)
//				{
//					Rezept neu = XMLListe.sucheRezept(e);
//					if(rezepte.sucheRezept(neu.getName()) != null && kochController.getKuechenApp().getRezeptliste().istLeer() != true)
//					{
//						int i=1;
//						while(rezepte.sucheRezept(neu+"("+i+")") != null)
//						{
//							i++;
//						}
//						neu.setName(neu.getName()+"("+i+")");
//					}
//					for (Kategorie neueKat : neu.getKategorie()) {
//						Boolean katVorhanden = false;
//						for (Kategorie kat : kochController.getKuechenApp().getKategorie()) {
//							if (kat.getName().equals(neueKat.getName())) {
//								kochController.getKuechenApp().getKategorie().add(kat);
//								katVorhanden = true;
//							}
//						}
//						if (!katVorhanden) {
//							ArrayList<Rezept> rezKatList = new ArrayList<Rezept>();
//							Kategorie kat = new Kategorie(neueKat.getName(), null);
//							kochController.getKuechenApp().addKategorie(kat);
//						}
//						for (Kategorie kat : kochController.getKuechenApp().getKategorie()) {
//							if (kat.getName().equals(neueKat.getName())) {
//								kat.addRezept(neu);
//							}
//						}
//					}
//					kochController.getKuechenApp().getRezeptliste().addRezept(neu);
//				}
//			}
//		}
//		
//		catch(Exception e)
//		{
//			if(e instanceof NullPointerException){
//				throw new NullPointerException("Keine Rezepte in der Auswahl");
//			}
//			if(e instanceof JDOMException){
//				throw new JDOMException("Problem beim lesen der XML-Datei");
//			}
//			if (e instanceof IOException)
//			{
//				throw new IOException("Pfad nicht vorhanden");
//			}
//		}
//
//	}
	
	/**
	 * @param pfad
	 * 		Der String pfad enthaelt den Dateipfad, des ueber den FileSelector ausgewaehlten XML-File
	 * 
	 *  @postconditions
	 *  		Der gewaehlte Dateipfad ist gueltig
	 */
	
		public Rezeptliste readFile(String pfad) throws JDOMException, IOException{
			
			SAXBuilder builder = new SAXBuilder();
			File xml = new File(pfad);
			Rezeptliste auswahlListe = new Rezeptliste();
			
			try
			{
				Document doc = builder.build(xml);
				Element root = doc.getRootElement();
				List<Element> XMLList = root.getChildren(); 	//Enthaelt alle Rezepte ab dem ersten
				
				for(Element akt : XMLList)				//laeuft ueber alle im Rezeptbuch enthaltenen Rezepte
				{
					String name = akt.getChildText("gericht");
					
					//Kategorieliste aus XML lesen und in ArrayList<String> umwandeln
					String kategorien = akt.getChildText("kategorie");
					ArrayList<Kategorie> katList = new ArrayList<Kategorie>();
					String[] katArr = kategorien.split(",");
					for(int i=0; i<katArr.length; i++)
					{
						Kategorie kat = new Kategorie(katArr[i], null);;
						katList.add(kat);
					}
					
					
					//Zutatenliste aus XML lesen und in ArrayList<String> umwandeln
					List<Element> List= akt.getChildren("zutat");
					ArrayList<Artikel> zutList = new ArrayList<Artikel>();
					for(Element e : List)
					{
						String zName = e.getChildText("name");
						String menge = e.getChildText("menge");
						double zMenge = Double.parseDouble(menge);
						String masseinheit = e.getChildText("masseinheit").toLowerCase();
						Einheit zMasseinheit = Einheit.GRAMM;
						if(masseinheit.equals("flasche") || masseinheit.equals("f.") || masseinheit.equals("fla.")||masseinheit.equals("flaschen")){
							zMasseinheit = Einheit.FLASCHE;
						}else if(masseinheit.equals("kilogramm")||masseinheit.equals("kg.")||masseinheit.equals("kg.")||masseinheit.equals("kilo")){
							zMasseinheit = Einheit.KILOGRAMM;
						}else if(masseinheit.equals("liter")||masseinheit.equals("l")||masseinheit.equals("l.")||masseinheit.equals("lit")||masseinheit.equals("lit.")){
							zMasseinheit = Einheit.LITER;
						}else if(masseinheit.equals("milliliter")||masseinheit.equals("ml")||masseinheit.equals("ml.")){
							zMasseinheit = Einheit.MILLILITER;
						}else if(masseinheit.equals("paekchen")||masseinheit.equals("päckchen")||masseinheit.equals("pck.")||masseinheit.equals("packung")||masseinheit.equals("pck")){
							zMasseinheit = Einheit.PAECKCHEN;
						}else if(masseinheit.equals("stueck")||masseinheit.equals("stück")||masseinheit.equals("stck")||masseinheit.equals("stck.")){
							zMasseinheit = Einheit.STUECK;
						}else if(masseinheit.equals("prise")){
							zMasseinheit = Einheit.PRISE;
						}
						Artikel zutat = new Artikel(zName, zMasseinheit, zMenge);
						zutList.add(zutat);						
					}
					
					String beschreibung = akt.getChildText("beschreibung");
					
					String zubereitungszeit = akt.getChildText("zubereitungszeit");
					
					String zubereitung = akt.getChildText("zubereitung");
					
					//Portionenzahl aus XML suchen und in int umwandeln
					String port = akt.getChildText("portionenzahl");
					int portionen = Integer.parseInt(port);
					
					//neues Rezept erstellen und in die auswahlList einfuegen
					Rezept neu =  new Rezept(name, beschreibung, zubereitung, portionen, zubereitungszeit, katList, zutList);
					auswahlListe.addRezept(neu);
				}
			}
			
			catch (Exception e)
			{
				if(e instanceof JDOMException)
				{
					throw new JDOMException("Problem beim lesen der XML-Datei");
				}
				if(e instanceof IOException)
				{
					throw new IOException("Pfad nicht vorhanden");
				}
			}
			return auswahlListe;
		}
		
	

}
