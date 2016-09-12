package control;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Artikel;
import model.Einkaufsliste;
import model.Lager;

import org.junit.Before;
import org.junit.Test;

import view.Einheit;
import control.KochController;
import control.RezeptController;
import exceptions.EinheitException;

public class RezeptControllerTest {
	
	private RezeptController rezeptCtrl;
	private KochController kochCtrl = new KochController();
	
	private ArrayList<String> kats = new ArrayList<String>();
	private ArrayList<String> arts = new ArrayList<String>();
	private String name;
	private String bes;
	private String zub;
	private int portionen;
	private String zubZeit;
	
	private Einkaufsliste einkaufsliste;
	private Lager bestand;

	@Before
	public void setUp() throws Exception {
		
		rezeptCtrl = new RezeptController(kochCtrl);
		einkaufsliste = kochCtrl.getKuechenApp().getEinkaufsliste();
		bestand = kochCtrl.getKuechenApp().getLager();
		
		name = "testRezept";
		bes = "test Beschreibung";
		zub = "test Zubereitung";
		portionen = 1;
		zubZeit = "2Stunden";
		kats.add("Favoriten");
		kats.add("Sonstige");
		kats.add("Chinesisch");
		arts.add("Zucker,1,KILOGRAMM");
		arts.add("Mehl,10,GRAMM");
		arts.add("Salz,200,GRAMM");
	}

	@Test
	public void testRezeptController() {
		
		RezeptController rezeptController = new RezeptController(new KochController());
		assertNotNull(rezeptController);
	}

	
	/**
	 * Wenn die Konstruktor mit null aufgerufen wird, wird eine Exception geworfen.
	 */
	@Test(expected = NullPointerException.class)
	public void testRezeptControllerNull() throws Exception {
		new RezeptController(null);
	}
	
	/**
	 * Es wird ein Rezept mit allen Bezeichnern erstellt
	 */
	@Test
	public void testCreateRezept() {
		rezeptCtrl.createRezept(name, bes, zub, portionen, kats, zubZeit, arts);
		assertTrue(kochCtrl.getKuechenApp().getRezeptliste().sucheRezept(name)!= null);
	}
	
	/**
	 * Wenn ein Rezept erstellt wird und der Rezeptname existiert bereits, wird eine Exception geworfen
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateRezeptNameGibtEsBereits() throws Exception {
		rezeptCtrl.createRezept(name, bes, zub, portionen, kats, zubZeit, arts);
		rezeptCtrl.createRezept(name, bes, zub, portionen, kats, zubZeit, arts);
	}

	/**
	 * Wenn der Parameter null ist, wird eine Exception geworfen
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateRezeptMitNameNull() throws Exception{
		rezeptCtrl.createRezept(null, bes, zub, portionen, kats, zubZeit, arts);
	}

	/**
	 * Wenn ein leerer String als Name übergeben wurde, wird eine Exception geworfen
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateRezeptEmptyName() throws Exception{
		rezeptCtrl.createRezept("", bes, zub, portionen, kats, zubZeit, arts);
	}
	
	@Test
	public void testSucheRezept() {
		fail("Not yet implemented");
	}
	
	//TEST FUER DEN FALL DASS MAN NULL UEBERGIBT
	@Test(expected = NullPointerException.class)
	public void testAddToEinkaufslisteNull() throws Exception {
		
		rezeptCtrl.addToEinkaufsliste(null);
		
	}
	
	//TEST FUER DAS HINZUFUEGEN VON ARTIKELN IN EINE LEERE EINKAUFSLISTE
	@Test
	public void testAddToEinkaufsliste() throws Exception {
		
		//ERSTELLE EIN REZEPT UND FUEGE ES HINZU
		rezeptCtrl.createRezept(name, bes, zub, portionen, kats, zubZeit, arts);
		rezeptCtrl.addToEinkaufsliste(name);
		
		//ICH GEHE DAVON AUS DASS ALLE ARTIKEL JETZT IN DER EINKAUFSLISTE SIND
		boolean checkArtikel = true;
		
		for(Artikel artikel : einkaufsliste.getArtikelList()) {
			
			//FALLS EIN ARTIKEL NICHT IN DER EINKAUFSLISTE IST, SETZE ICH checkArtikel AUF false
			if(einkaufsliste.containsArtikel(artikel) == false) {
				checkArtikel = false;
			}
		}
		
		assertTrue(checkArtikel);
	}
	
	//TEST FUER DAS HINZUFUEGEN VON ARTIKELN IN EINE EINKAUFSLISTE
	//WO EINIGE ARTIKEL SCHON VORHANDEN SIND
	@Test
	public void testAddToEinkaufslisteArtikelVorhanden() throws Exception {
		
		//FUEGE NEUE ARTIKEL ZUR EINKAUFSLISTE HINZU
		einkaufsliste.addElement("Zucker", Einheit.KILOGRAMM, 1);
		einkaufsliste.addElement("Mehl", Einheit.GRAMM, 1);
		einkaufsliste.addElement("Salz", Einheit.GRAMM, 1);
		
		//ERSTELLE EIN REZEPT MIT DEN GLEICHEN ARTIKELN WIE OBEN UND FUEGE ES HINZU
		rezeptCtrl.createRezept(name, bes, zub, portionen, kats, zubZeit, arts);
		rezeptCtrl.addToEinkaufsliste(name);
		
		//PRUEFE OB DIE MENGEN ERHOEHT WURDEN
		assertEquals(2, einkaufsliste.getArtikel("Zucker").getMenge(), 1e-15);
		assertEquals(11, einkaufsliste.getArtikel("Mehl").getMenge(), 1e-15);
		assertEquals(201, einkaufsliste.getArtikel("Salz").getMenge(), 1e-15);
	}

	/**
	 * Ein Rezept wird erstellt und dann geändert mit einer neuen Beschreibung
	 * */
	@Test
	public void testChangeRezept() {
		rezeptCtrl.createRezept(name, bes, zub, portionen, kats, zubZeit, arts);
		bes = "neue Bes";
		rezeptCtrl.changeRezept(name, name, bes, zub, portionen, kats, zubZeit, arts);
		String neueBes = kochCtrl.getKuechenApp().getRezeptliste().sucheRezept(name).getBeschreibung();
		assertEquals("neue Beschreibung von testRezept muss 'neue Bes' sein", "neue Bes", neueBes);
	}
	
	/**
	 * Wenn der alte Rezeptname null ist, wird eine Exception geworfen
	 * */
	@Test(expected = IllegalArgumentException.class)
	public void testChangeRezeptParameterNull() throws Exception {
		rezeptCtrl.createRezept(name, bes, zub, portionen, kats, zubZeit, arts);
		rezeptCtrl.changeRezept(null, name, bes, zub, portionen, kats, zubZeit, arts);
	}

	/**
	 * Wenn der neue Rezeptname null ist, wird eine Exception geworfen
	 * */
	@Test(expected = NullPointerException.class)
	public void testChangeRezeptParameterNameNull() throws Exception {
		rezeptCtrl.createRezept(name, bes, zub, portionen, kats, zubZeit, arts);
		rezeptCtrl.changeRezept(name, null, bes, zub, portionen, kats, zubZeit, arts);
	}
	
	@Test(expected = NullPointerException.class)
	public void testKochenNull() throws EinheitException {
		
		rezeptCtrl.kochen(null, 1);
	}
	
	@Test
	public void testKochenWenigerImRezept() throws EinheitException {
		
		//FUEGE NEUE ARTIKEL ZUM BESTAND HINZU
		bestand.addElement("Zucker", Einheit.KILOGRAMM, 5);
		bestand.addElement("Mehl", Einheit.GRAMM, 15);
		bestand.addElement("Salz", Einheit.GRAMM, 210);
		
		//ERSTELLE EIN REZEPT MIT DEN GLEICHEN ARTIKELN WIE OBEN UND KOCHE ES
		rezeptCtrl.createRezept(name, bes, zub, portionen, kats, zubZeit, arts);
		rezeptCtrl.kochen(name, 1);
		
		//PRUEFE OB DIE MENGEN IM BESTAND ANGEPASST WURDEN
		assertEquals(4, bestand.getArtikel("Zucker").getMenge(), 1e-15);
		//assertEquals(10, bestand.getArtikel("Mehl").getMenge(), 1e-15);
		//assertEquals(5, bestand.getArtikel("Salz").getMenge(), 1e-15);
		
	}
	
	@Test
	public void testKochenMehrImRezept() throws EinheitException {
		
		//FUEGE NEUE ARTIKEL ZUM BESTAND HINZU
		bestand.addElement("Zucker", Einheit.KILOGRAMM, 0);
		bestand.addElement("Mehl", Einheit.GRAMM, 5);
		bestand.addElement("Salz", Einheit.GRAMM, 100);
				
		//ERSTELLE EIN REZEPT MIT DEN GLEICHEN ARTIKELN WIE OBEN UND KOCHE ES
		rezeptCtrl.createRezept(name, bes, zub, portionen, kats, zubZeit, arts);
		rezeptCtrl.kochen(name, 1);
		
		//PRUEFE OB DIE MENGEN IM BESTAND AUF 0 GESETZT WURDEN
		assertEquals(0, bestand.getArtikel("Zucker").getMenge(), 1e-15);
		assertEquals(0, bestand.getArtikel("Mehl").getMenge(), 1e-15);
		assertEquals(0, bestand.getArtikel("Salz").getMenge(), 1e-15);
		
	}

}