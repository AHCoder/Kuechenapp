/**
 * 
 */
package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import view.Einheit;


public class LagerTest {

	private Lager bestandsListe; 

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		bestandsListe = new Lager();
	}
	
	/**
	 * Test method for {@link model.Lager#Lager(java.util.ArrayList)}.
	 */
	@Test
	public void testLager() {
		assertNotNull(bestandsListe.getArtikelList());
		assertTrue(bestandsListe.getArtikelList().isEmpty());
	}

	/**
	 * Test method for {@link model.Lager#getArtikelList()}.
	 */
	@Test
	public void testGetArtikelList() {
		ArrayList<Artikel> list = bestandsListe.getArtikelList();
		Artikel artikel1 = new Artikel("Käse", Einheit.GRAMM, 250);
		Artikel artikel2 = new Artikel("Milch", Einheit.LITER, 3);
		bestandsListe.addElement("Käse", Einheit.GRAMM, 250);
		bestandsListe.addElement("Milch", Einheit.LITER, 3);
		
		assertEquals("Das Lager sollte 2 Artikel enthalten", 2, list.size());
		assertEquals("Artikel Käse sollte vorhanden sein", artikel1.getName(), bestandsListe.getArtikel("Käse").getName());
		assertEquals("Artikel Käse sollte vorhanden sein", artikel1.getMenge(), bestandsListe.getArtikel("Käse").getMenge());
		assertEquals("Artikel Käse sollte vorhanden sein", artikel1.getMasseinheit(), bestandsListe.getArtikel("Käse").getMasseinheit());
		assertEquals("Der Artikel sollte an Position 1 stehen", "Käse", list.get(0).getName());
		
		assertEquals("Das Lager sollte 2 Artikel enthalten", 2, list.size());
		assertEquals("Artikel Milch sollte vorhanden sein", artikel2.getName(), bestandsListe.getArtikel("Milch").getName());
		assertEquals("Artikel Milch sollte vorhanden sein", artikel2.getMenge(), bestandsListe.getArtikel("Milch").getMenge());
		assertEquals("Artikel Milch sollte vorhanden sein", artikel2.getMasseinheit(), bestandsListe.getArtikel("Milch").getMasseinheit());
		assertEquals("Der Artikel sollte an Pos. 2 stehen", "Milch", list.get(1).getName());
	}

	/**
	 * Test method for {@link model.Lager#addElement(java.lang.String, view.Einheit, int)}.
	 */
	@Test
	public void testAddElement() {
		ArrayList<Artikel> list = bestandsListe.getArtikelList();
		
		assertEquals("Das Lager sollte keine Artikel enthalten", 0, list.size());
		//Artikel anhängen und testen
		bestandsListe.addElement("Kinderriegel", Einheit.STUECK, 10);
		assertEquals("Das Lager sollte 1 Artikel haben", 1, list.size());
		assertEquals("Der Artikel sollte an Position 1 stehen", "Kinderriegel", list.get(0).getName());
		
		bestandsListe.addElement("Duplo", Einheit.STUECK, 3);
		assertEquals("Das Lager sollte 2 Artikel enthalten", 2, list.size());
		assertEquals("Der Artikel sollte an Pos. 2 stehen", "Duplo", list.get(1).getName());
	}

	/**
	 * Test method for {@link model.Lager#getArtikel(java.lang.String)}.
	 */
	@Test
	public void testGetArtikel() {
		Artikel artikel1 = new Artikel("Käse", Einheit.GRAMM, 250);
		Artikel artikel2 = new Artikel("Milch", Einheit.LITER, 3);
		bestandsListe.addElement("Käse", Einheit.GRAMM, 250);
		bestandsListe.addElement("Milch", Einheit.LITER, 3);
		
		// nicht vorhandener Artikel
		assertEquals("Artikel sollte nicht auf der Einkaufsliste vorhanden sein", null, bestandsListe.getArtikel("Lakritz"));
				
		// artikel1 testen
		assertEquals("Artikel Käse sollte vorhanden sein", artikel1.getName(), bestandsListe.getArtikel("Käse").getName());
		assertEquals("Artikel Käse sollte vorhanden sein", artikel1.getMenge(), bestandsListe.getArtikel("Käse").getMenge());
		assertEquals("Artikel Käse sollte vorhanden sein", artikel1.getMasseinheit(), bestandsListe.getArtikel("Käse").getMasseinheit());
				
		// artikel2 testen
		assertEquals("Artikel Milch sollte vorhanden sein", artikel2.getName(), bestandsListe.getArtikel("Milch").getName());
		assertEquals("Artikel Milch sollte vorhanden sein", artikel2.getMenge(), bestandsListe.getArtikel("Milch").getMenge());
		assertEquals("Artikel Milch sollte vorhanden sein", artikel2.getMasseinheit(), bestandsListe.getArtikel("Milch").getMasseinheit());
	}

}
