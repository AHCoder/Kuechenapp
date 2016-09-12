package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import view.Einheit;

public class RezeptTest {

	private Rezept testRezept;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ArrayList<Kategorie> katListe = new ArrayList<Kategorie>();
		ArrayList<Artikel> artListe = new ArrayList<Artikel>();
		ArrayList<Rezept> rezListe = new ArrayList<Rezept>();
		katListe.add(new Kategorie("Favoriten", rezListe));
		katListe.add(new Kategorie("Suppe", rezListe));
		katListe.add(new Kategorie("Sonstiges", rezListe));
		artListe.add(new Artikel("Zucker", Einheit.GRAMM, 200));
		artListe.add(new Artikel("Wasser", Einheit.LITER, 2));
		testRezept = new Rezept("TestRezept", "test Beschreibung", "test zubereitung", 2, "test zubzeit", katListe, artListe);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRezept() {
		assertEquals("Rezeptname muss 'TestRezept' sein", "TestRezept", testRezept.getName());
		assertEquals("Beschreibung muss 'test Beschreibung' sein", "test Beschreibung", testRezept.getBeschreibung());
		assertEquals("Zubereitung muss 'test zubereitung' sein", "test zubereitung", testRezept.getZubereitung());
		assertEquals("Portionenzahl muss '2' sein", 2, testRezept.getPortionenzahl());
		assertEquals("Zubereitungszeit muss 'test zubzeit' ergeben", "test zubzeit", testRezept.getZubereitungszeit());
		assertNotNull("Kategorie darf nicht null sein", testRezept.getKategorie());
		assertNotNull("Artikel darf nicht null sein", testRezept.getArtikel());		
	}

	@Test
	public void testRemoveKategorie() {
		ArrayList<Rezept> rez = new ArrayList<Rezept>();
		Kategorie kat = new Kategorie("Dessert", rez);
		testRezept.addKategorie(kat);
		assertTrue("Zunächst sollte die Kategorie Dessert enthalten sein", testRezept.getKategorie().contains(kat));
		testRezept.removeKategorie("Dessert");
		assertFalse("Dessert sollte nicht mehr in Kategorie sein", testRezept.getKategorie().contains(kat));
	}

	@Test
	public void testAddKategorie() {
		ArrayList<Rezept> rez = new ArrayList<Rezept>();
		Kategorie kat = new Kategorie("Dessert", rez);
		assertFalse("Dessert sollte nicht in Kategorie sein", testRezept.getKategorie().contains(kat));
		testRezept.addKategorie(kat);
		assertTrue("Zunächst sollte die Kategorie Suppe enthalten sein", testRezept.getKategorie().contains(kat));
	}

	@Test
	public void testContainsArtikel() {
		assertTrue("Zucker sollte enhalten sein", testRezept.containsArtikel("Zucker"));
	}

	@Test
	public void testAddArtikel() {
		assertFalse("Kaffee sollte noch nicht enhalten sein", testRezept.containsArtikel("Kaffee"));
		testRezept.addArtikel("Kaffee", Einheit.KILOGRAMM, 1);
		assertTrue("Kaffee sollte nun enhalten sein", testRezept.containsArtikel("Kaffee"));
	}

	@Test
	public void testToXML() {
		assertEquals(testRezept.toXML(), "<rezept>\n" + "<gericht>TestRezept</gericht>\n" + "<zutat>\n" + "<menge>200</menge>\n"
				+ "<masseinheit>GRAMM</masseinheit>\n" + "<name>Zucker</name>\n" + "</zutat>\n" + "<zutat>\n" + "<menge>2</menge>\n"
				+ "<masseinheit>LITER</masseinheit>\n" + "<name>Wasser</name>\n" + "</zutat>\n" 
				+ "<kategorie>Favoriten,Suppe,Sonstiges</kategorie>\n" + "<portionenzahl>2</portionenzahl>\n"
				+ "<zubereitung>test zubereitung</zubereitung>\n" + "<beschreibung>test Beschreibung</beschreibung>\n"
				+ "<zubereitungszeit>test zubzeit</zubereitungszeit>\n" + "</rezept>\n");
	}

}
