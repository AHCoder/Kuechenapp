package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import view.Einheit;

//Jenny
public class EinkaufslisteTest {
	
	private Einkaufsliste einkaufsliste;


	@Before
	public void setUp() throws Exception {
		einkaufsliste = new Einkaufsliste();
		
	}

	/**
	 * Testet den Einkaufslisten-Konstruktor
	 * Artikelliste der Einkaufsliste wurde erstellt und ist leer
	 */
	@Test
	public void testEinkaufsliste() {
		//Testen ob Artikelliste erzeugt wurde und leer ist
		assertNotNull("Artikelliste sollte nicht null sein", einkaufsliste.getArtikelList());
		assertTrue("Artikelliste sollte leer sein", einkaufsliste.getArtikelList().isEmpty());		
	}

	/**
	 * Testet das Suchen eines bestimmten Artikels auf der Einkaufsliste, der vorhanden ist
	 */
	@Test
	public void testGetArtikelVorhanden() { 
		Artikel artikel1 = new Artikel("Milch", Einheit.LITER, 5);
		Artikel artikel2 = new Artikel("Apfel", Einheit.STUECK, 3);
		einkaufsliste.addElement("Milch", Einheit.LITER, 5);
		einkaufsliste.addElement("Apfel", Einheit.STUECK, 3);
		
		// Artikel1 testen "Milch" (Artikel nicht vergleichbar, deshalb Attribute einzeln vergleichen)
		assertEquals("Artikel Milch sollte vorhanden sein", artikel1.getName(), einkaufsliste.getArtikel("Milch").getName());
		assertEquals("Artikel Milch sollte vorhanden sein", artikel1.getMenge(), einkaufsliste.getArtikel("Milch").getMenge());
		assertEquals("Artikel Milch sollte vorhanden sein", artikel1.getMasseinheit(), einkaufsliste.getArtikel("Milch").getMasseinheit());
		// Artikel2 testen "Apfel" (Artikel nicht vergleichbar, deshalb Attribute einzeln vergleichen)
		assertEquals("Artikel Apfel sollte vorhanden sein", artikel2.getName(), einkaufsliste.getArtikel("Apfel").getName());
		assertEquals("Artikel Apfel sollte vorhanden sein", artikel2.getMenge(), einkaufsliste.getArtikel("Apfel").getMenge());
		assertEquals("Artikel Apfel sollte vorhanden sein", artikel2.getMasseinheit(), einkaufsliste.getArtikel("Apfel").getMasseinheit());
				
	}
	/**
	 * Testet das Suchen eines bestimmten Artikels auf der Einkaufsliste, der nicht vorhanden ist
	 */
	@Test
	public void testGetArtikelNichtVorhanden() {
		// Test mit Artikel, der nicht vorhanden ist
		assertEquals("Artikel sollte nicht auf der Einkaufsliste vorhanden sein", null, einkaufsliste.getArtikel("Banane"));
		Artikel artikel1 = new Artikel("Banane", Einheit.STUECK, 7);
		einkaufsliste.addElement("Banane", Einheit.STUECK, 7);
		assertEquals("Artikel sollte jetzt vorhanden sein und gefunden werden", artikel1.getName(), einkaufsliste.getArtikel("Banane").getName());
		assertEquals("Artikel sollte jetzt vorhanden sein und gefunden werden", artikel1.getMenge(), einkaufsliste.getArtikel("Banane").getMenge());
		assertEquals("Artikel sollte jetzt vorhanden sein und gefunden werden", artikel1.getMasseinheit(), einkaufsliste.getArtikel("Banane").getMasseinheit());
	}

	/**
	 * Testet das Hinzufügen eines Artikels auf die Einkaufsliste
	 */
	@Test
	public void testAddElement() {
		ArrayList<Artikel> list = einkaufsliste.getArtikelList();
		
		assertEquals("Die Artikelliste der Einkaufsliste sollte keine Artikel enthalten", 0, list.size());
		einkaufsliste.addElement("Milch", Einheit.LITER, 5);
		assertEquals("Die Artikelliste der Einkaufsliste sollte einen Artikel besitzen", 1, list.size());
		assertEquals("Der Artikel sollte an erster Stelle stehen", "Milch", list.get(0).getName());
		einkaufsliste.addElement("Apfel", Einheit.STUECK, 3);
		assertEquals("Die Artikelliste der Einkaufsliste sollte zwei Artikel besitzen", 2, list.size());
		assertEquals("Der Artikel sollte an zweiter Stelle stehen", "Apfel", list.get(1).getName());
				
	}
	
	/**
	 * Testet das Suchen eines vorhandenen Artikels auf der Artikelliste der Einkaufsliste
	 */
	@Test
	public void testContainsArtikelVorhanden() {
		Artikel artikel1 = new Artikel("Milch", Einheit.LITER, 6);
		
		einkaufsliste.addElement("Milch", Einheit.LITER, 6);
		assertTrue("Artikel Milch sollte vorhanden sein", einkaufsliste.containsArtikel(artikel1));
	}
	
	/**
	 * Testet das Suchen eines nicht-vorhandenen Artikels auf der Artikelliste der Einkaufsliste
	 */
	@Test
	public void testContainsArtikelNichtVorhanden() {
		Artikel artikel1 = new Artikel("Milch", Einheit.LITER, 6);
		
		assertFalse("Artikel Milch sollte noch nicht vorhanden sein", einkaufsliste.containsArtikel(artikel1));
		einkaufsliste.addElement("Milch", Einheit.LITER, 6);
		assertTrue("Artikel Milch sollte jetzt vorhanden sein", einkaufsliste.containsArtikel(artikel1));
	}

}
