package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ajnol
 *
 */
public class KategorieTest {
	
	private Kategorie kategorie;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ArrayList<Rezept> rezepte = new ArrayList<Rezept>();
		kategorie = new Kategorie("Test-Kategorie", rezepte);
	}

	/**
	 * Test method for {@link model.Kategorie#Kategorie(java.lang.String, java.util.ArrayList)}.
	 */
	@Test
	public void testKategorie() {
		
		//TEST FÜR DEN NAMEN
		assertEquals("Die Kategorie sollte den Namen 'Test-Kategorie' haben", "Test-Kategorie", kategorie.getName());
		
		//TEST FÜR DIE LISTE DER REZEPTE
		assertNotNull("Die Liste der Rezepte sollte nicht null sein", kategorie.getRezepte());
	}

	/**
	 * Test method for {@link model.Kategorie#addRezept(model.Rezept)}.
	 */
	@Test
	public void testAddRezept() {
		
		//EIN REZEPT WIRD HINZUGEFÜGT
		kategorie.addRezept(new Rezept("Test-Rezept", "beschreibung", "zubereitung", 1, "zubereitungszeit", null, null));
		
		//DIE LISTE DARF NICHT EINE LEERE ARRAY LISTE SEIN
		assertNotSame(new ArrayList<Rezept>(), kategorie.getRezepte());
	}

	/**
	 * Test method for {@link model.Kategorie#removeRezept(model.Rezept)}.
	 */
	@Test
	public void testRemoveRezept() {
		
		//VORBEREITUNG
		Rezept rezept = new Rezept("Test-Rezept", "beschreibung", "zubereitung", 1, "zubereitungszeit", null, null);
		
		//EIN REZEPT WIRD HINZUGEFÜGT
		kategorie.addRezept(rezept);
		
		//DAS REZEPT WIRD ENTFERNT
		kategorie.removeRezept(rezept);
		
		//FAIL, FALLS DIE LISTE DER REZEPTE NICHT LEER IST
		assertEquals(new ArrayList<Rezept>(), kategorie.getRezepte());
	}
	
	//AB HIER GET UND SET METHODEN

	/**
	 * Test method for {@link model.Kategorie#getName()}.
	 */
	@Test
	public void testGetName() {
		
		assertEquals("Test-Kategorie", kategorie.getName());
	}

	/**
	 * Test method for {@link model.Kategorie#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		
		kategorie.setName("gesundes Essen");
		assertEquals("gesundes Essen", kategorie.getName());
	}

	/**
	 * Test method for {@link model.Kategorie#getRezepte()}.
	 */
	@Test
	public void testGetRezepte() {
		
		//VORBEREITUNG
		ArrayList<Rezept> rezepte2 = new ArrayList<Rezept>();
		Rezept rezept = new Rezept("Test-Rezept", "beschreibung", "zubereitung", 1, "zubereitungszeit", null, null);
		rezepte2.add(rezept);
		
		//DIE LISTE DER ERZEUGTEN KATEGORIE IST LEER, REZEPTE2 IST NICHT LEER ALSO MÜSSEN SIE UNTERSCHEIDLICH SEIN
		//assertNotEquals(rezepte2, kategorie.getRezepte());
	}

	/**
	 * Test method for {@link model.Kategorie#setRezepte(java.util.ArrayList)}.
	 */
	@Test
	public void testSetRezepte() {
		
		//VORBEREITUNG
		ArrayList<Rezept> rezepte = new ArrayList<Rezept>();
		Rezept rezept = new Rezept("Test-Rezept", "beschreibung", "zubereitung", 1, "zubereitungszeit", null, null);
		Rezept rezept2 = new Rezept("Test-Rezept2", "beschreibung", "zubereitung", 1, "zubereitungszeit", null, null);
		rezepte.add(rezept);
		rezepte.add(rezept2);
		
		//LISTE DER REZEPTE WIRD AUF 'rezepte' GESETZT
		kategorie.setRezepte(rezepte);
		
		//DIE LISTEN MÜSSEN JETZT GLEICH SEIN
		assertEquals(rezepte, kategorie.getRezepte());
	}

}