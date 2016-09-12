package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Artikel;
import model.Kategorie;
import model.Rezept;

public class IOController {

	private KochController kC;

	
	public IOController(KochController kochC){
		kC = kochC;
	}
	
	
	/**
	 * Diese Methode läd alle Komponenten der Anwendung, wie z.B das Lager, die Rezeptliste, etc.
	 */
	public void laden(){
		ladeEinkaufListe();
		ladeBestand();
		ladeRezepte();
		ladeKategorien();
	}
	
	@SuppressWarnings("unchecked")
	public void ladeEinkaufListe() {
		try{
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream("einkaufliste"));
			kC.getKuechenApp().getEinkaufsliste().setArtikelList((ArrayList<Artikel>)stream.readObject());
			stream.close();
		}catch(FileNotFoundException fnfex){
			System.err.println("Einkaufslistendatei nicht gefunden.");
		}catch(IOException ioex){
			System.err.println("Fehler beim Laden des Objekts aufgetreten");
			ioex.printStackTrace();
		}catch(ClassNotFoundException cnfex){
			cnfex.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void ladeBestand() {
		try{
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream("bestand"));
			kC.getKuechenApp().getLager().setArtikelList((ArrayList<Artikel>)stream.readObject()); 
			stream.close();
		}catch(FileNotFoundException fnfex){
			System.err.println("Bestandsdatei nicht gefunden");
		}catch(IOException ioex){
			System.err.println("Fehler beim Laden des Objekts aufgetreten");
			ioex.printStackTrace();
		}catch(ClassNotFoundException cnfex){
			cnfex.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void ladeRezepte() {
		try{
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream("rezepte"));
			kC.getKuechenApp().getRezeptliste().setRezepte((ArrayList<Rezept>)stream.readObject()); 
			stream.close();
		}catch(FileNotFoundException fnfex){
			System.err.println("Rezeptliste nicht gefunden.");
		}catch(IOException ioex){
			System.err.println("Fehler beim Laden des Objekts aufgetreten");
			ioex.printStackTrace();
		}catch(ClassNotFoundException cnfex){
			cnfex.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void ladeKategorien(){
		try{
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream("kategorien"));
			ArrayList<Kategorie> k = (ArrayList<Kategorie>) stream.readObject();
			for(int i = 0; i < k.size(); i++)
				kC.getKuechenApp().getKategorie().add(k.get(i));
			stream.close();
		}catch(FileNotFoundException fnfex){
			System.err.println("Kategoriendatei nicht gefunden");
		}catch(IOException ioex){
			System.err.println("Fehler beim LAden des Objekts aufgetreten");
			ioex.printStackTrace();
		}catch(ClassNotFoundException cnfex){
			cnfex.printStackTrace();
		}
	}

	
	/**
	 * Diese Methode speichert alle Komponenten der Anwendung.
	 **/
	public void speichern(){
		speicherEinkaufListe();
		speicherBestand();
		speicherRezepte();
		speicherKategorien();
	}
	
	public void speicherEinkaufListe() {
		try{
			
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("einkaufliste"));
			stream.writeObject(kC.getKuechenApp().getEinkaufsliste().getArtikelList());
			stream.close();
			
		}catch(IOException ioex){
			System.err.println("Fehler beim Schreiben des Objekts aufgetreten.");
            ioex.printStackTrace();
		}
	}
	
	public void speicherBestand() {
		try{
			
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("bestand"));
			stream.writeObject(kC.getKuechenApp().getLager().getArtikelList());
			stream.close();
			
		}catch(IOException ioex){
			System.err.println("Fehler beim Schreiben des Objekts aufgetreten.");
            ioex.printStackTrace();
		}
	}
	
	public void speicherRezepte(){
		try{
			
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("rezepte"));
			stream.writeObject(kC.getKuechenApp().getRezeptliste().getRezepte());
			stream.close();
			
		}catch(IOException ioex){
			System.err.println("Fehler beim Schreiben des Objekts aufgetreten.");
            ioex.printStackTrace();
		}
	}
	
	public void speicherKategorien(){
		try{
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("kategorien"));
			stream.writeObject(kC.getKuechenApp().getKategorie());
			stream.close();
		}catch(IOException ioex){
			System.err.println("Fehler beim Schreiben des Objekts aufgetreten.");
			ioex.printStackTrace();
		}
	}
}
