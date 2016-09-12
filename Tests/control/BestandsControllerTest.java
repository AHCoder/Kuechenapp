package control;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Artikel;
import model.Einkaufsliste;
import model.Kategorie;
import model.Lager;
import model.Rezept;
import model.Rezeptliste;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.EinheitException;
import view.Einheit;

public class BestandsControllerTest {
	private BestandsController bCtrl;
	private KochController kochCtl = new KochController();
	private Lager lager;
	private Rezept meinRezept;
	private Rezept meinRezept2;
	private Rezeptliste rezeptListe;
	private ArrayList<Rezept> rezept = new ArrayList<>();
	private ArrayList<Artikel> artikel = new ArrayList<Artikel>();
	private ArrayList<Artikel> artikel2Rezept = new ArrayList<Artikel>();
	private ArrayList<String> artikelStringList = new ArrayList<String>();
	private Kategorie vorschlaege;
	private Kategorie kategorie;
	
	
	@Before
	public void setUp() throws Exception {
		 bCtrl = new BestandsController(kochCtl);
		 lager = kochCtl.getKuechenApp().getLager();
		 
		 //Artikel und Rezepte anlegen
		    kategorie = new Kategorie("schnellesEssen", null);
		    vorschlaege = new Kategorie("Vorschlaege",null);
			ArrayList<Kategorie> kategorie2 = new ArrayList<Kategorie>();
			kategorie2.add(kategorie);
			
			kochCtl.getKuechenApp().addKategorie(vorschlaege);
			
			
			Artikel artikelZutat1 = new Artikel("Nudeln", Einheit.KILOGRAMM, 500);
			Artikel artikelZutat2 = new Artikel("Öl", Einheit.MILLILITER, 20);
			Artikel artikelZutat3 = new Artikel("Salz", Einheit.GRAMM, 3);
			
			artikel.add(artikelZutat1);
			artikel.add(artikelZutat2);
			artikel.add(artikelZutat3);
			meinRezept = new Rezept("Nudeln", "Studentenfutter", "", 1, "30min", kategorie2 , artikel);
			
			
			//2.rezept
			Kategorie kategorie2Rezept = new Kategorie("schnellesEssen", null);
			ArrayList<Kategorie> kategorie2RezeptListe = new ArrayList<Kategorie>();
			kategorie2RezeptListe.add(kategorie2Rezept);
			
			
			Artikel artikelZutatX1 = new Artikel("Wasser", Einheit.GRAMM, 500);
			Artikel artikelZutatX2 = new Artikel("Fleisch", Einheit.GRAMM, 50);
			Artikel artikelZutatX3 = new Artikel("Salz", Einheit.GRAMM, 3);
			Artikel artikelZutatX4 = new Artikel("FertigMischung von Knorr", Einheit.GRAMM, 100);
			
			artikel2Rezept.add(artikelZutatX1);
			artikel2Rezept.add(artikelZutatX2);
			artikel2Rezept.add(artikelZutatX3);
			artikel2Rezept.add(artikelZutatX4);
			meinRezept2 = new Rezept("Hühnersuppe", "Studentenfutter", "", 1, "15min", kategorie2RezeptListe , artikel2Rezept);
			
			rezept.add(meinRezept2);
			rezept.add(meinRezept);
			rezeptListe = new Rezeptliste(rezept);
			
			kochCtl.getKuechenApp().getRezeptliste().addRezept(meinRezept);
			kochCtl.getKuechenApp().getRezeptliste().addRezept(meinRezept2);
	}
	
	// Konstruktor null übergeben, Exeption soll geworfen werden
	@Test(expected = NullPointerException.class)
	public void testBestandsControllerNull() {	
		new BestandsController(null);
	}

	@Test
	public void testBestandsController() {
		BestandsController bCtrl = new BestandsController(kochCtl);
		assertNotNull(bCtrl);
	}
	
	//Fuege einen neuen nicht vorhandenen Artikel hinzu
	@Test
	public void testAddArtikel() throws EinheitException {
		
		//Fuege Artikel zum Bestand hinzu. Pruefe danach ob der Artikel im Lager Vorhanden ist.
		int sizeBefore = lager.getArtikelList().size();
		bCtrl.addArtikel("Kaese", 5, Einheit.PAECKCHEN);
		assertEquals(lager.containsArtikel(lager.getArtikel("Kaese")),true);
		assertEquals(sizeBefore + 1, lager.getArtikelList().size());
		
	}
	
	//Fuege einen bereits vorhandenen Artikel mit anderer Einheit hinzu. Das hinzufuegen sollte nicht funktionieren.
	@Test(expected = EinheitException.class)
	public void testAddArtikelEinheit() throws EinheitException {
		
		//Fuege Artikel zum Bestand hinzu. Pruefe danach ob der Artikel im Lager Vorhanden ist.
		int sizeBefore = lager.getArtikelList().size();
		bCtrl.addArtikel("Kaese", 5, Einheit.PAECKCHEN);
		assertEquals(lager.containsArtikel(lager.getArtikel("Kaese")),true);
		assertEquals(sizeBefore + 1, lager.getArtikelList().size());
		bCtrl.addArtikel("Kaese", 5, Einheit.KILOGRAMM);
	}

	// Test, wenn Artikelmenge richtig geändert wird 
	@Test
	public void testAddArtikelMengeAendern() throws EinheitException {
		bCtrl.addArtikel("Kaese", 5, Einheit.PAECKCHEN);
		double mengeAlt = lager.getArtikel("Kaese").getMenge();
		bCtrl.addArtikel("Kaese", 5, Einheit.PAECKCHEN);
		assertNotEquals(mengeAlt, lager.getArtikel("Kaese").getMenge());
		assertEquals(mengeAlt + 5, lager.getArtikel("Kaese").getMenge(), 0);
	}
		

	
	//Aendern eines Artikels
	@Test
	public void testChangeArtikelName() throws EinheitException {
		bCtrl.addArtikel("Kaese", 5, Einheit.PAECKCHEN);
		bCtrl.changeArtikel("Kaese", "Gouda", 5, Einheit.PAECKCHEN);
		assertEquals(lager.containsArtikel(lager.getArtikel("Gouda")),true);
	}
	
	@Test
	public void testChangeArtikelNameBereitsVorhanden() throws EinheitException {
		bCtrl.addArtikel("Kaese", 5, Einheit.PAECKCHEN);
		bCtrl.addArtikel("Gouda", 5, Einheit.PAECKCHEN);
		double mengeAlt = lager.getArtikel("Gouda").getMenge();
		bCtrl.changeArtikel("Kaese", "Gouda", 5, Einheit.PAECKCHEN);
		assertNotEquals(mengeAlt, lager.getArtikel("Gouda").getMenge());
		assertEquals(mengeAlt + 5, lager.getArtikel("Gouda").getMenge(), 0);
	}
	@Test
	public void testChangeArtikelMenge() throws EinheitException {
		bCtrl.addArtikel("Kaese", 5, Einheit.PAECKCHEN);
		bCtrl.changeArtikel("Kaese", "Kaese", 10, Einheit.PAECKCHEN);
		assertEquals(10, lager.getArtikel("Kaese").getMenge(), 0);
	}
	
	//Delete Artikel mit leerer Liste es sollte eine NullPointerException geworfen werden
	@Test(expected = NullPointerException.class)
	public void testDeleteArtikelmitLeererListe() {
		bCtrl.deleteArtikel(artikelStringList);
	}
	
	//Delete Artikel. Der Artikel sollte aus der Bestandsliste geloescht werden.
	@Test
	public void testDeleteArtikel() throws EinheitException {
		artikelStringList.add("Gouda");
		bCtrl.addArtikel("Kaese", 5, Einheit.PAECKCHEN);
		bCtrl.addArtikel("Gouda", 5, Einheit.PAECKCHEN);
		int mengeAlt = lager.getArtikelList().size();
		bCtrl.deleteArtikel(artikelStringList);
		assertNotEquals(lager.getArtikelList().size(),mengeAlt);
	}
	

	@Test
	public void testSuggestRezept() {
		//Suche nach Rezepten mit Salz: Beide Rezepte sollten in der Kategorie Vorschläge landen und die Kategorie in den Rezepten.
		//Es sollten die 2 einzigen Rezeot in der Kategorie Vorschlaege sein.
		artikelStringList.add("Salz");
		bCtrl.suggestRezept(artikelStringList);
		
		assertEquals(meinRezept2.getKategorie().contains(vorschlaege),true);
		assertEquals(meinRezept.getKategorie().contains(vorschlaege),true);
		assertEquals(vorschlaege.getRezepte().contains(meinRezept2),true);
		assertEquals(vorschlaege.getRezepte().contains(meinRezept),true);
		assertEquals(vorschlaege.getRezepte().size(),2);
		
		//Entferne Salz wieder aus der Suchliste
		artikelStringList.remove(0);
		
		artikelStringList.add("Fleisch");
		artikelStringList.add("Wasser");
		
		//Suche nach Rezepten mit Fleisch. Die Referenz von Rezept auf Kategorie sollte gelöscht werden		
		bCtrl.suggestRezept(artikelStringList);
		assertEquals(meinRezept2.getKategorie().contains(vorschlaege),true);
		assertEquals(meinRezept.getKategorie().contains(vorschlaege),false);
		assertEquals(vorschlaege.getRezepte().contains(meinRezept2),true);
		assertEquals(vorschlaege.getRezepte().contains(meinRezept),false);
		assertEquals(vorschlaege.getRezepte().size(),1);
		
		
		//suche nach einem nicht vorhandenen Artikel. Es sollten keine Vorschlaege gemacht werden und alte Referenzen geloescht werden
		artikelStringList.remove(0);
		artikelStringList.add("Fleisch");
		artikelStringList.add("Gouda");
		bCtrl.suggestRezept(artikelStringList);
		assertEquals(meinRezept2.getKategorie().contains(vorschlaege),false);
		assertEquals(meinRezept.getKategorie().contains(vorschlaege),false);
		assertEquals(vorschlaege.getRezepte().contains(meinRezept2),false);
		assertEquals(vorschlaege.getRezepte().contains(meinRezept),false);
		assertEquals(vorschlaege.getRezepte().size(),0);
		
	}

}
