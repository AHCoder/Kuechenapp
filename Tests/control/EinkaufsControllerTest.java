package control;

import static org.junit.Assert.*;
import java.util.ArrayList;
import model.Einkaufsliste;
import org.junit.Before;
import org.junit.Test;

import exceptions.EinheitException;
import view.Einheit;

public class EinkaufsControllerTest {
	private EinkaufsController ekCtrl;
	private KochController kochCtl = new KochController();
	private Einkaufsliste ekList;
	
	@Before
	public void setUp() throws Exception {
		 ekCtrl = new EinkaufsController(kochCtl);
		 ekList = kochCtl.getKuechenApp().getEinkaufsliste();
	}
	
	/*
	 * Soll Referenz auf KochController zurückgeben, wenn der übergebene nicht null ist
	 */
	@Test(expected = NullPointerException.class)
	public void testEinkaufsControllerNull() {
		// Konstruktor null übergeben, Exeption soll geworfen werden
		new EinkaufsController(null);
	}
	
	@Test
	public void testEinkaufsController() {
		new EinkaufsController(new KochController());
	}
	/*
	 * Test, wenn richtige Parameter übergeben wurden
	 */
	@Test
	public void testAddArtikel() throws EinheitException {
		int sizeBefore = ekList.getArtikelList().size();
		assertEquals(0, sizeBefore);
		ekCtrl.addArtikel("Milch", 12, Einheit.LITER);
		assertEquals(sizeBefore + 1, ekList.getArtikelList().size());
	}
	/*
	 * Test, wenn Einheit beim zweiten Mal anders ist als vorher
	 */
	@Test(expected = EinheitException.class)
	public void testAddArtikelEinheitFalsch() throws EinheitException {
		int sizeBefore = ekList.getArtikelList().size();
		assertEquals(0, sizeBefore);
		ekCtrl.addArtikel("Milch", 12, Einheit.LITER);
		assertEquals(sizeBefore + 1, ekList.getArtikelList().size());
		ekCtrl.addArtikel("Milch", 12, Einheit.KILOGRAMM);
	}
	/*
	 * Test, wenn Artikelmenge richtig geändert wird 
	 */
	@Test
	public void testAddArtikelMengeAendern() throws EinheitException {
		ekCtrl.addArtikel("Apfel", 10, Einheit.STUECK);
		double mengeAlt = ekList.getArtikel("Apfel").getMenge();
		ekCtrl.addArtikel("Apfel", 5, Einheit.STUECK);
		assertNotEquals(mengeAlt, ekList.getArtikel("Apfel").getMenge());
		assertEquals(mengeAlt + 5, ekList.getArtikel("Apfel").getMenge(), 0);
	}
	/*
	 * Test, wenn zwei Artikel geloescht werden ohne Fehler
	 */
	@Test
	public void testDeleteArtikel() throws EinheitException {
		ekCtrl.addArtikel("Apfel", 10, Einheit.STUECK);
		ekCtrl.addArtikel("Milch", 12, Einheit.LITER);
		ekCtrl.addArtikel("Mehl", 2, Einheit.KILOGRAMM);
		int sizeBefore = ekList.getArtikelList().size();
		ArrayList<String> temp = new ArrayList<>();
		temp.add("Apfel"); temp.add("Milch"); 
		ekCtrl.deleteArtikel(temp);
		assertNotEquals(sizeBefore, ekList.getArtikelList().size());
		assertEquals(sizeBefore - temp.size(), ekList.getArtikelList().size());
	}
	/*
	 * Test, um einen Artikel auf der Einkaufsliste zu ändern in einen Artikel, den es schon gibt
	 * Ergebnis sollte sein, dass ein Artikel weniger auf der Liste ist und die Menge des anderen Artikels 
	 * erhöht wurden ist
	 */
	@Test
	public void testChangeArtikelArtikelBesteht() throws EinheitException {
		ekCtrl.addArtikel("Salz", 2, Einheit.KILOGRAMM);
		ekCtrl.addArtikel("Wasser", 2, Einheit.LITER);
		String bezAlt = ekList.getArtikel("Salz").getName();
		double mengeAlt = ekList.getArtikel("Salz").getMenge();
		int sizeAlt = ekList.getArtikelList().size();
		ekCtrl.changeArtikel(bezAlt, "Wasser", 500, Einheit.LITER);
		assertEquals( sizeAlt - 1, ekList.getArtikelList().size());
		assertEquals(mengeAlt + 500, ekList.getArtikel("Wasser").getMenge(), 0);
	}
	/*
	 * Test, wenn alles vom Artikel geändert wurde
	 */
	@Test
	public void testChangeArtikel() throws EinheitException {
		ekCtrl.addArtikel("Salz", 2, Einheit.KILOGRAMM);
		ekCtrl.addArtikel("Wasser", 2, Einheit.LITER);
		String bezAlt = ekList.getArtikel("Salz").getName();
		double mengeAlt = ekList.getArtikel("Salz").getMenge();
		Einheit einheitAlt = ekList.getArtikel("Salz").getMasseinheit();
		int sizeAlt = ekList.getArtikelList().size();
		String bezNeu = "Apfel";
		ekCtrl.changeArtikel(bezAlt, bezNeu, 500, Einheit.STUECK);
		assertEquals( sizeAlt, ekList.getArtikelList().size());
		assertNotEquals(bezAlt, ekList.getArtikel(bezNeu).getName());
		assertNull("Artikel mit dem alten Namen darf nicht mehr vorhanden sein.", ekList.getArtikel(bezAlt));
		assertFalse(mengeAlt == ekList.getArtikel(bezNeu).getMenge());
		assertFalse(einheitAlt == ekList.getArtikel(bezNeu).getMasseinheit());
	}
	/*
	 * Test, wenn nur Menge geändert wird
	 */
	@Test
	public void testChangeArtikelMenge() throws EinheitException {
		ekCtrl.addArtikel("Salz", 2, Einheit.KILOGRAMM);
		ekCtrl.addArtikel("Wasser", 2, Einheit.LITER);
		String bezAlt = ekList.getArtikel("Salz").getName();
		Einheit einheitAlt = ekList.getArtikel("Salz").getMasseinheit();
		int sizeAlt = ekList.getArtikelList().size();
		String bezNeu = bezAlt;
		ekCtrl.changeArtikel(bezAlt, bezNeu, 500, Einheit.KILOGRAMM);
		assertEquals( sizeAlt, ekList.getArtikelList().size());
		assertEquals(bezAlt, ekList.getArtikel(bezNeu).getName());
		assertEquals(500, ekList.getArtikel(bezNeu).getMenge(), 0);
		assertEquals(einheitAlt, ekList.getArtikel(bezNeu).getMasseinheit());
	}
	/*
	 * Test, wenn Artikel zu Bestand hinzugefügt wird
	 * Artikel darf nicht mehr auf der Einkaufsliste vorhanden sein
	 * Im Lager muss der Artikel angkommen sein, wird hier nicht getestet, da verschiedene Fälle und hier
	 * nicht notwendig
	 */
	@Test
	public void testAddToBestand() throws EinheitException {
		String testArtikel = "Banane";
		ekList.addElement(testArtikel, Einheit.STUECK, 5);
		int sizeAltEkl = ekList.getArtikelList().size();
		ArrayList<String> toBestand = new ArrayList<>();
		toBestand.add(testArtikel);
		//
		ekCtrl.addToBestand(toBestand);
		//
		assertEquals(sizeAltEkl - toBestand.size(), ekList.getArtikelList().size());
		assertNull(ekList.getArtikel(testArtikel));
	}
}
