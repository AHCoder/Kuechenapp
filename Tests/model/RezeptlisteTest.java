package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import view.Einheit;

public class RezeptlisteTest {

	private Rezept meinRezept;
	private Rezept meinRezept2;
	private ArrayList<Rezept> rezept = new ArrayList<>();
	private Rezeptliste rezeptListe; 
	
	
	@Before
	public void setUp() throws Exception {
		Kategorie kategorie = new Kategorie("schnellesEssen", null);
		ArrayList<Kategorie> kategorie2 = new ArrayList<Kategorie>();
		kategorie2.add(kategorie);
		
		ArrayList<Artikel> artikel = new ArrayList<Artikel>();
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
		
		ArrayList<Artikel> artikel2Rezept = new ArrayList<Artikel>();
		Artikel artikelZutatX1 = new Artikel("Wasser", Einheit.GRAMM, 500);
		Artikel artikelZutatX2 = new Artikel("Fleisch", Einheit.GRAMM, 50);
		Artikel artikelZutatX3 = new Artikel("Salz", Einheit.GRAMM, 3);
		Artikel artikelZutatX4 = new Artikel("FertigMischung von Knorr", Einheit.GRAMM, 100);
		
		artikel2Rezept.add(artikelZutatX1);
		artikel2Rezept.add(artikelZutatX2);
		artikel2Rezept.add(artikelZutatX3);
		artikel2Rezept.add(artikelZutatX4);
		meinRezept2 = new Rezept("Huehnersuppe", "Studentenfutter", "", 1, "15min", kategorie2RezeptListe , artikel2Rezept);
		
		rezept.add(meinRezept2);
		rezept.add(meinRezept);
		rezeptListe = new Rezeptliste(rezept);
		
		
		
	}

	@Test
	public void testRezeptliste() {
		//Testen ob Rezeptliste erzeugt wurde und leer ist
		Rezeptliste rezepte = new Rezeptliste();
		assertNotNull(rezepte.getRezeptliste());
		assertTrue(rezepte.getRezeptliste().isEmpty());	
	}

	@Test
	public void testVorschlaege() {
		
		//Test1: Suche nach Rezepten mit Artikeln Wasser und Salz. Vorschlag sollte Huehnersuppe sein
		ArrayList<String> artikelListTest1 = new ArrayList<String>();
		ArrayList<Rezept> test1 = new ArrayList<Rezept>();
		test1.add(meinRezept2);
		artikelListTest1.add("Wasser");
		artikelListTest1.add("Salz");
		
		assertEquals(rezeptListe.vorschlaege(artikelListTest1),test1);
		
		//Test2: Suche nach Artikel Paprika. Vorschlag sollte leere ArrayList<Rezept> sein.
		ArrayList<String> artikelListTest2 = new ArrayList<String>();
		artikelListTest2.add("Paprika");
		ArrayList<Rezept> test2 = new ArrayList<Rezept>();
		
		assertEquals(rezeptListe.vorschlaege(artikelListTest2),test2);
		
		//Test3: Suche nach Artikel: Fleisch und Paprika. Vorschlag sollte leere AraryList<Rezept> sein.
		ArrayList<String> artikelListTest3 = new ArrayList<String>();
		artikelListTest3.add("Fleisch");
		artikelListTest3.add("Paprika");
		ArrayList<Rezept> test3 = new ArrayList<Rezept>();
		assertEquals(rezeptListe.vorschlaege(artikelListTest3),test3);
		
		//Test4: Suche nach Artikel: Fleisch. Vorschlag sollte meinRezept2 sein.
		ArrayList<String> artikelListTest4 = new ArrayList<String>();
		ArrayList<Rezept> test4 = new ArrayList<Rezept>();
		test4.add(meinRezept2);
		artikelListTest4.add("Fleisch");
		
		assertEquals(rezeptListe.vorschlaege(artikelListTest4),test4);
	}

	@Test
	public void testSucheRezept() {
		
		//Test1: Suche nach einem vorhandenem Rezept. Es sollte das entsprechende Rezept zurueckegegeben werden.
		assertEquals(rezeptListe.sucheRezept("Huehnersuppe"),meinRezept2);
		
		//Test2: Suche nach einem nicht vorhandenem Rezept. Es sollte null zurueckgegeben werden.
		assertEquals(rezeptListe.sucheRezept("nicht Vorhandenes Rezept"),null);
	}

	@Test
	public void testAddRezept() {
		
		Rezeptliste rezListe = new Rezeptliste(new ArrayList<Rezept>());
		
		//Test 1: Nach der Initialisierung der Rezeptliste sollte diese leer sein.
		assertEquals("Die Rezeptliste sollte keine Rezepte enthalten", 0, rezListe.getRezeptliste().size());
		
		//Test 2: Nach hinzufuegen des ersten Rezeptes, sollte die Rezeptliste ein Rezept enthalten und dieses sollte
		//		  an erster Stelle stehen.
		rezListe.addRezept(meinRezept);
		assertEquals("Die Rezeptliste sollte ein Rezept enthalten", 1, rezListe.getRezeptliste().size());
		assertEquals("Das Rezept sollte an erster stelle stehen", meinRezept.getName(), rezListe.getRezeptliste().get(0));
		
		//Test 3: Nach hinzufuegen des weiteren Rezeptes, sollte die Rezeptliste n+1 Rezepte  enthalten. Das neue sollte
		//		  an Stelle n+1 stehen.
		
		int n = rezListe.getRezeptliste().size();
		rezListe.addRezept(meinRezept2);
		
		assertEquals("Die Rezeptliste sollte zwei Rezept enthalten", n+1, rezListe.getRezeptliste().size());
		assertEquals("Das Rezept sollte an zweiter stelle stehen", meinRezept2.getName(), rezListe.getRezeptliste().get(n)); //get n, da index bei 0 anfaengt.
		
	}

	@Test
	public void testGetRezeptliste() {
		
		//Test1: Ein String mit 2 Rezeptnamen wird angelegt. Da im SetUp 2 Rezepte hinzugefuegt worden sind, sollte die get-Methode die Namen dieser 
		//		 Rezepte liefern
		ArrayList<String> testGet = new ArrayList<String>();		
		testGet.add("Huehnersuppe");
		testGet.add("Nudeln");
		assertEquals(rezeptListe.getRezeptliste(),testGet);


	}

	@Test
	public void testToXML() {

		
		assertEquals(rezeptListe.toXML(rezept), "<rezeptbuch>\n" + "<rezept>\n" + "<gericht>Huehnersuppe</gericht>\n"
				+ "<zutat>\n" + "<menge>500</menge>\n" + "<masseinheit>GRAMM</masseinheit>\n" + "<name>Wasser</name>\n"
				+ "</zutat>\n" + "<zutat>\n" + "<menge>50</menge>\n" + "<masseinheit>GRAMM</masseinheit>\n"
				+ "<name>Fleisch</name>\n" + "</zutat>\n" + "<zutat>\n" + "<menge>3</menge>\n" 
				+ "<masseinheit>GRAMM</masseinheit>\n" + "<name>Salz</name>\n" + "</zutat>\n" + "<zutat>\n"
				+ "<menge>100</menge>\n" + "<masseinheit>GRAMM</masseinheit>\n" + "<name>FertigMischung von Knorr</name>\n"
				+ "</zutat>\n" + "<kategorie>schnellesEssen</kategorie>\n" + "<portionenzahl>1</portionenzahl>\n"
				+ "<zubereitung></zubereitung>\n" + "<beschreibung>Studentenfutter</beschreibung>\n" 
				+ "<zubereitungszeit>15min</zubereitungszeit>\n" + "</rezept>\n" + "<rezept>\n"
				+ "<gericht>Nudeln</gericht>\n" + "<zutat>\n" + "<menge>500</menge>\n" + "<masseinheit>KILOGRAMM</masseinheit>\n"
				+ "<name>Nudeln</name>\n" + "</zutat>\n" + "<zutat>\n" + "<menge>20</menge>\n" + "<masseinheit>MILLILITER</masseinheit>\n"
				+ "<name>Öl</name>\n" + "</zutat>\n" + "<zutat>\n" + "<menge>3</menge>\n" + "<masseinheit>GRAMM</masseinheit>\n" 
				+ "<name>Salz</name>\n" + "</zutat>\n" + "<kategorie>schnellesEssen</kategorie>\n"
				+ "<portionenzahl>1</portionenzahl>\n" + "<zubereitung></zubereitung>\n" 
				+ "<beschreibung>Studentenfutter</beschreibung>\n" + "<zubereitungszeit>30min</zubereitungszeit>\n"
				+ "</rezept>\n" + "</rezeptbuch>");
	}

}
