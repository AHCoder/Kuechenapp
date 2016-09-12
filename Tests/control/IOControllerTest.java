package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;

import model.Artikel;
import model.Kategorie;
import model.Rezept;

import org.junit.Before;
import org.junit.Test;

import view.Einheit;
import exceptions.EinheitException;

public class IOControllerTest {
	private KochController kc1;
	private KochController kc2;
	private IOController ioc1;
	private IOController ioc2;
	@Before
	public void setUp() throws Exception {
		kc1 = new KochController();
		kc2 = new KochController();
		ioc1 = kc1.getIOController();
		ioc2 = kc2.getIOController();
	}
	
	@Test
	public void testSpeichern(){
		Artikel a1 = new Artikel("Ei",Einheit.STUECK, 1);
		Artikel a2 = new Artikel("Zwieback", Einheit.PAECKCHEN, 2);
		Artikel a3 = new Artikel("Milch",Einheit.FLASCHE, 3);
		Artikel a4 = new Artikel("Zucker",Einheit.GRAMM, 100);
		Artikel a5 = new Artikel("Mehl",Einheit.KILOGRAMM, 4);
		Artikel a6 = new Artikel("Wasser",Einheit.LITER, 5);
		Artikel a7 = new Artikel("Honig",Einheit.MILLILITER, 6);
		
		ArrayList<Rezept> rezepte = new ArrayList<>();
		ArrayList<Kategorie> kat1 = new ArrayList<>();
		kat1.add(new Kategorie("Suppe", null));
		ArrayList<Artikel> art1 = new ArrayList<>();

		art1.add(a1);
		art1.add(a2);
		art1.add(a3);
		art1.add(a4);
		art1.add(a5);
		art1.add(a6);
		art1.add(a7);
		Rezept rez1 = new Rezept("Hühnersuppe", "Leckere und gesunde Suppe mit Hühnerfleisch", "Wasser in den Topf, alles rein",
				5, "5 Stunden", kat1, art1);
		rezepte.add(rez1);
		kc1.getKuechenApp().getRezeptliste().setRezepte(rezepte);
		
		kc1.getKuechenApp().getLager().addElement("Ei", Einheit.STUECK, 1);
		kc1.getKuechenApp().getLager().addElement("Zwieback", Einheit.PAECKCHEN, 2);
		kc1.getKuechenApp().getLager().addElement("Milch", Einheit.FLASCHE, 3);
		kc1.getKuechenApp().getLager().addElement("Zucker", Einheit.GRAMM, 100);
		kc1.getKuechenApp().getLager().addElement("Mehl", Einheit.KILOGRAMM, 4);
		kc1.getKuechenApp().getLager().addElement("Wasser", Einheit.LITER, 5);
		kc1.getKuechenApp().getLager().addElement("Honig", Einheit.MILLILITER, 6);
		
		kc1.getKuechenApp().getEinkaufsliste().addElement("Ei", Einheit.STUECK, 1);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Zwieback", Einheit.PAECKCHEN, 2);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Milch", Einheit.FLASCHE, 3);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Zucker", Einheit.GRAMM, 100);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Mehl", Einheit.KILOGRAMM, 4);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Wasser", Einheit.LITER, 5);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Honig", Einheit.MILLILITER, 6);
		
		ioc1.speichern();
	}
	
	@Test
	public void testSpeicherBestand(){
		kc1.getKuechenApp().getLager().addElement("Ei", Einheit.STUECK, 1);
		kc1.getKuechenApp().getLager().addElement("Zwieback", Einheit.PAECKCHEN, 2);
		kc1.getKuechenApp().getLager().addElement("Milch", Einheit.FLASCHE, 3);
		kc1.getKuechenApp().getLager().addElement("Zucker", Einheit.GRAMM, 100);
		kc1.getKuechenApp().getLager().addElement("Mehl", Einheit.KILOGRAMM, 4);
		kc1.getKuechenApp().getLager().addElement("Wasser", Einheit.LITER, 5);
		kc1.getKuechenApp().getLager().addElement("Honig", Einheit.MILLILITER, 6);
		
		ioc1.speicherBestand();
	}
	
	@Test
	public void testSpeicherEinkaufListe(){
		kc1.getKuechenApp().getEinkaufsliste().addElement("Ei", Einheit.STUECK, 1);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Zwieback", Einheit.PAECKCHEN, 2);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Milch", Einheit.FLASCHE, 3);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Zucker", Einheit.GRAMM, 100);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Mehl", Einheit.KILOGRAMM, 4);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Wasser", Einheit.LITER, 5);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Honig", Einheit.MILLILITER, 6);
		
		ioc1.speicherEinkaufListe();
	}
	
	@Test
	public void testSpeicherRezepte(){
		ArrayList<Rezept> rezepte = new ArrayList<>();
		ArrayList<Kategorie> kat1 = new ArrayList<>();
		kat1.add(new Kategorie("Suppe", null));
		ArrayList<Artikel> art1 = new ArrayList<>();
		Artikel a1 = new Artikel("Ei",Einheit.STUECK, 1);
		Artikel a2 = new Artikel("Zwieback", Einheit.PAECKCHEN, 2);
		Artikel a3 = new Artikel("Milch",Einheit.FLASCHE, 3);
		Artikel a4 = new Artikel("Zucker",Einheit.GRAMM, 100);
		Artikel a5 = new Artikel("Mehl",Einheit.KILOGRAMM, 4);
		Artikel a6 = new Artikel("Wasser",Einheit.LITER, 5);
		Artikel a7 = new Artikel("Honig",Einheit.MILLILITER, 6);
		art1.add(a1);
		art1.add(a2);
		art1.add(a3);
		art1.add(a4);
		art1.add(a5);
		art1.add(a6);
		art1.add(a7);
		Rezept rez1 = new Rezept("Hühnersuppe", "Leckere und gesunde Suppe mit Hühnerfleisch", "Wasser in den Topf, alles rein",
				5, "5 Stunden", kat1, art1);
		rezepte.add(rez1);
		kc1.getKuechenApp().getRezeptliste().setRezepte(rezepte);
		
		ioc1.speicherRezepte();
	}
	
	
	@Test
	public void testLaden() throws EinheitException{
		//Teste mit mehreren Artikeln und Rezepten in Bestand, Einkaufsliste, und Rezeptliste das Laden und Speichern aller Elemente
		Artikel a1 = new Artikel("Ei",Einheit.STUECK, 1);
		Artikel a2 = new Artikel("Zwieback", Einheit.PAECKCHEN, 2);
		Artikel a3 = new Artikel("Milch",Einheit.FLASCHE, 3);
		Artikel a4 = new Artikel("Zucker",Einheit.GRAMM, 100);
		Artikel a5 = new Artikel("Mehl",Einheit.KILOGRAMM, 4);
		Artikel a6 = new Artikel("Wasser",Einheit.LITER, 5);
		Artikel a7 = new Artikel("Honig",Einheit.MILLILITER, 6);
		
		ArrayList<Rezept> rezepte = new ArrayList<>();
		ArrayList<Kategorie> kat1 = new ArrayList<>();
		kat1.add(new Kategorie("Suppe", null));
		ArrayList<Artikel> art1 = new ArrayList<>();

		art1.add(a1);
		art1.add(a2);
		art1.add(a3);
		art1.add(a4);
		art1.add(a5);
		art1.add(a6);
		art1.add(a7);
		Rezept rez1 = new Rezept("Hühnersuppe", "Leckere und gesunde Suppe mit Hühnerfleisch", "Wasser in den Topf, alles rein",
				5, "5 Stunden", kat1, art1);
		rezepte.add(rez1);
		kc1.getKuechenApp().getRezeptliste().setRezepte(rezepte);
		
		kc1.getKuechenApp().getLager().addElement("Ei", Einheit.STUECK, 1);
		kc1.getKuechenApp().getLager().addElement("Zwieback", Einheit.PAECKCHEN, 2);
		kc1.getKuechenApp().getLager().addElement("Milch", Einheit.FLASCHE, 3);
		kc1.getKuechenApp().getLager().addElement("Zucker", Einheit.GRAMM, 100);
		kc1.getKuechenApp().getLager().addElement("Mehl", Einheit.KILOGRAMM, 4);
		kc1.getKuechenApp().getLager().addElement("Wasser", Einheit.LITER, 5);
		kc1.getKuechenApp().getLager().addElement("Honig", Einheit.MILLILITER, 6);
		
		kc1.getKuechenApp().getEinkaufsliste().addElement("Ei", Einheit.STUECK, 1);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Zwieback", Einheit.PAECKCHEN, 2);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Milch", Einheit.FLASCHE, 3);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Zucker", Einheit.GRAMM, 100);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Mehl", Einheit.KILOGRAMM, 4);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Wasser", Einheit.LITER, 5);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Honig", Einheit.MILLILITER, 6);
		
		assertNotEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().size(), kc2.getKuechenApp().getRezeptliste().getRezepte().size());
		assertNotEquals(kc1.getKuechenApp().getEinkaufsliste().getArtikelList().size(), kc2.getKuechenApp().getEinkaufsliste().getArtikelList().size());
		assertNotEquals(kc1.getKuechenApp().getLager().getArtikelList().size(), kc2.getKuechenApp().getLager().getArtikelList().size());
		
		ioc1.speichern();
		ioc2.laden();
		
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().size(), kc2.getKuechenApp().getRezeptliste().getRezepte().size());
		assertEquals(kc1.getKuechenApp().getEinkaufsliste().getArtikelList().size(), kc2.getKuechenApp().getEinkaufsliste().getArtikelList().size());
		assertEquals(kc1.getKuechenApp().getLager().getArtikelList().size(), kc2.getKuechenApp().getLager().getArtikelList().size());
		
		ArrayList<Artikel> aList = kc2.getKuechenApp().getLager().getArtikelList();
		assertEquals(a1.getName(), aList.get(0).getName());
		assertEquals(a1.getMenge(), aList.get(0).getMenge());
		assertEquals(a1.getMasseinheit(), aList.get(0).getMasseinheit());
		assertEquals(a2.getName(), aList.get(1).getName());
		assertEquals(a2.getMenge(), aList.get(1).getMenge());
		assertEquals(a2.getMasseinheit(), aList.get(1).getMasseinheit());
		assertEquals(a3.getName(), aList.get(2).getName());
		assertEquals(a3.getMenge(), aList.get(2).getMenge());
		assertEquals(a3.getMasseinheit(), aList.get(2).getMasseinheit());
		assertEquals(a4.getName(), aList.get(3).getName());
		assertEquals(a4.getMenge(), aList.get(3).getMenge());
		assertEquals(a4.getMasseinheit(), aList.get(3).getMasseinheit());
		assertEquals(a5.getName(), aList.get(4).getName());
		assertEquals(a5.getMenge(), aList.get(4).getMenge());
		assertEquals(a5.getMasseinheit(), aList.get(4).getMasseinheit());
		assertEquals(a6.getName(), aList.get(5).getName());
		assertEquals(a6.getMenge(), aList.get(5).getMenge());
		assertEquals(a6.getMasseinheit(), aList.get(5).getMasseinheit());
		assertEquals(a7.getName(), aList.get(6).getName());
		assertEquals(a7.getMenge(), aList.get(6).getMenge());
		assertEquals(a7.getMasseinheit(), aList.get(6).getMasseinheit());
		
		aList = kc2.getKuechenApp().getEinkaufsliste().getArtikelList();
		assertEquals(a1.getName(), aList.get(0).getName());
		assertEquals(a1.getMenge(), aList.get(0).getMenge());
		assertEquals(a1.getMasseinheit(), aList.get(0).getMasseinheit());
		assertEquals(a2.getName(), aList.get(1).getName());
		assertEquals(a2.getMenge(), aList.get(1).getMenge());
		assertEquals(a2.getMasseinheit(), aList.get(1).getMasseinheit());
		assertEquals(a3.getName(), aList.get(2).getName());
		assertEquals(a3.getMenge(), aList.get(2).getMenge());
		assertEquals(a3.getMasseinheit(), aList.get(2).getMasseinheit());
		assertEquals(a4.getName(), aList.get(3).getName());
		assertEquals(a4.getMenge(), aList.get(3).getMenge());
		assertEquals(a4.getMasseinheit(), aList.get(3).getMasseinheit());
		assertEquals(a5.getName(), aList.get(4).getName());
		assertEquals(a5.getMenge(), aList.get(4).getMenge());
		assertEquals(a5.getMasseinheit(), aList.get(4).getMasseinheit());
		assertEquals(a6.getName(), aList.get(5).getName());
		assertEquals(a6.getMenge(), aList.get(5).getMenge());
		assertEquals(a6.getMasseinheit(), aList.get(5).getMasseinheit());
		assertEquals(a7.getName(), aList.get(6).getName());
		assertEquals(a7.getMenge(), aList.get(6).getMenge());
		assertEquals(a7.getMasseinheit(), aList.get(6).getMasseinheit());
		
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getBeschreibung(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getBeschreibung());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getPortionenzahl(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getPortionenzahl());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getZubereitung(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getZubereitung());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getZubereitungszeit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getZubereitungszeit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getKategorie().get(0).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getKategorie().get(0).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(0).getMasseinheit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(0).getMasseinheit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(0).getMenge(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(0).getMenge());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(0).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(0).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(1).getMasseinheit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(1).getMasseinheit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(1).getMenge(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(1).getMenge());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(1).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(1).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(2).getMasseinheit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(2).getMasseinheit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(2).getMenge(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(2).getMenge());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(2).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(2).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(3).getMasseinheit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(3).getMasseinheit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(3).getMenge(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(3).getMenge());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(3).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(3).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(4).getMasseinheit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(4).getMasseinheit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(4).getMenge(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(4).getMenge());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(4).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(4).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(5).getMasseinheit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(5).getMasseinheit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(5).getMenge(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(5).getMenge());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(5).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(5).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(6).getMasseinheit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(6).getMasseinheit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(6).getMenge(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(6).getMenge());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(6).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(6).getName());
	}
	@Test
	public void testLadeBestand() throws EinheitException {
		Artikel a1 = new Artikel("Ei",Einheit.STUECK, 1);
		Artikel a2 = new Artikel("Zwieback", Einheit.PAECKCHEN, 2);
		Artikel a3 = new Artikel("Milch",Einheit.FLASCHE, 3);
		Artikel a4 = new Artikel("Zucker",Einheit.GRAMM, 100);
		Artikel a5 = new Artikel("Mehl",Einheit.KILOGRAMM, 4);
		Artikel a6 = new Artikel("Wasser",Einheit.LITER, 5);
		Artikel a7 = new Artikel("Honig",Einheit.MILLILITER, 6);
		
		kc1.getKuechenApp().getLager().addElement("Ei", Einheit.STUECK, 1);
		kc1.getKuechenApp().getLager().addElement("Zwieback", Einheit.PAECKCHEN, 2);
		kc1.getKuechenApp().getLager().addElement("Milch", Einheit.FLASCHE, 3);
		kc1.getKuechenApp().getLager().addElement("Zucker", Einheit.GRAMM, 100);
		kc1.getKuechenApp().getLager().addElement("Mehl", Einheit.KILOGRAMM, 4);
		kc1.getKuechenApp().getLager().addElement("Wasser", Einheit.LITER, 5);
		kc1.getKuechenApp().getLager().addElement("Honig", Einheit.MILLILITER, 6);
		
		assertNotEquals(kc1.getKuechenApp().getLager().getArtikelList().size(), kc2.getKuechenApp().getLager().getArtikelList().size());
		
		ioc1.speicherBestand();
		
		ioc2.ladeBestand();
		
		assertEquals(kc1.getKuechenApp().getLager().getArtikelList().size(), kc2.getKuechenApp().getLager().getArtikelList().size());
		
		ArrayList<Artikel> aList = kc2.getKuechenApp().getLager().getArtikelList();
		assertEquals(a1.getName(), aList.get(0).getName());
		assertEquals(a1.getMenge(), aList.get(0).getMenge());
		assertEquals(a1.getMasseinheit(), aList.get(0).getMasseinheit());
		assertEquals(a2.getName(), aList.get(1).getName());
		assertEquals(a2.getMenge(), aList.get(1).getMenge());
		assertEquals(a2.getMasseinheit(), aList.get(1).getMasseinheit());
		assertEquals(a3.getName(), aList.get(2).getName());
		assertEquals(a3.getMenge(), aList.get(2).getMenge());
		assertEquals(a3.getMasseinheit(), aList.get(2).getMasseinheit());
		assertEquals(a4.getName(), aList.get(3).getName());
		assertEquals(a4.getMenge(), aList.get(3).getMenge());
		assertEquals(a4.getMasseinheit(), aList.get(3).getMasseinheit());
		assertEquals(a5.getName(), aList.get(4).getName());
		assertEquals(a5.getMenge(), aList.get(4).getMenge());
		assertEquals(a5.getMasseinheit(), aList.get(4).getMasseinheit());
		assertEquals(a6.getName(), aList.get(5).getName());
		assertEquals(a6.getMenge(), aList.get(5).getMenge());
		assertEquals(a6.getMasseinheit(), aList.get(5).getMasseinheit());
		assertEquals(a7.getName(), aList.get(6).getName());
		assertEquals(a7.getMenge(), aList.get(6).getMenge());
		assertEquals(a7.getMasseinheit(), aList.get(6).getMasseinheit());
	}
	
	@Test
	public void testLadeEinkaufsliste(){
		Artikel a1 = new Artikel("Ei",Einheit.STUECK, 1);
		Artikel a2 = new Artikel("Zwieback", Einheit.PAECKCHEN, 2);
		Artikel a3 = new Artikel("Milch",Einheit.FLASCHE, 3);
		Artikel a4 = new Artikel("Zucker",Einheit.GRAMM, 100);
		Artikel a5 = new Artikel("Mehl",Einheit.KILOGRAMM, 4);
		Artikel a6 = new Artikel("Wasser",Einheit.LITER, 5);
		Artikel a7 = new Artikel("Honig",Einheit.MILLILITER, 6);
		
		kc1.getKuechenApp().getEinkaufsliste().addElement("Ei", Einheit.STUECK, 1);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Zwieback", Einheit.PAECKCHEN, 2);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Milch", Einheit.FLASCHE, 3);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Zucker", Einheit.GRAMM, 100);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Mehl", Einheit.KILOGRAMM, 4);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Wasser", Einheit.LITER, 5);
		kc1.getKuechenApp().getEinkaufsliste().addElement("Honig", Einheit.MILLILITER, 6);
		
		assertNotEquals(kc1.getKuechenApp().getEinkaufsliste().getArtikelList().size(), kc2.getKuechenApp().getEinkaufsliste().getArtikelList().size());
		
		ioc1.speicherEinkaufListe();
		
		ioc2.ladeEinkaufListe();;
		
		assertEquals(kc1.getKuechenApp().getEinkaufsliste().getArtikelList().size(), kc2.getKuechenApp().getEinkaufsliste().getArtikelList().size());
		
		ArrayList<Artikel> aList = kc2.getKuechenApp().getEinkaufsliste().getArtikelList();
		assertEquals(a1.getName(), aList.get(0).getName());
		assertEquals(a1.getMenge(), aList.get(0).getMenge());
		assertEquals(a1.getMasseinheit(), aList.get(0).getMasseinheit());
		assertEquals(a2.getName(), aList.get(1).getName());
		assertEquals(a2.getMenge(), aList.get(1).getMenge());
		assertEquals(a2.getMasseinheit(), aList.get(1).getMasseinheit());
		assertEquals(a3.getName(), aList.get(2).getName());
		assertEquals(a3.getMenge(), aList.get(2).getMenge());
		assertEquals(a3.getMasseinheit(), aList.get(2).getMasseinheit());
		assertEquals(a4.getName(), aList.get(3).getName());
		assertEquals(a4.getMenge(), aList.get(3).getMenge());
		assertEquals(a4.getMasseinheit(), aList.get(3).getMasseinheit());
		assertEquals(a5.getName(), aList.get(4).getName());
		assertEquals(a5.getMenge(), aList.get(4).getMenge());
		assertEquals(a5.getMasseinheit(), aList.get(4).getMasseinheit());
		assertEquals(a6.getName(), aList.get(5).getName());
		assertEquals(a6.getMenge(), aList.get(5).getMenge());
		assertEquals(a6.getMasseinheit(), aList.get(5).getMasseinheit());
		assertEquals(a7.getName(), aList.get(6).getName());
		assertEquals(a7.getMenge(), aList.get(6).getMenge());
		assertEquals(a7.getMasseinheit(), aList.get(6).getMasseinheit());
	}
	
	@Test
	public void testLadeRezepte(){
		ArrayList<Rezept> rezepte = new ArrayList<>();
		ArrayList<Kategorie> kat1 = new ArrayList<>();
		kat1.add(new Kategorie("Suppe", null));
		ArrayList<Artikel> art1 = new ArrayList<>();
		Artikel a1 = new Artikel("Ei",Einheit.STUECK, 1);
		Artikel a2 = new Artikel("Zwieback", Einheit.PAECKCHEN, 2);
		Artikel a3 = new Artikel("Milch",Einheit.FLASCHE, 3);
		Artikel a4 = new Artikel("Zucker",Einheit.GRAMM, 100);
		Artikel a5 = new Artikel("Mehl",Einheit.KILOGRAMM, 4);
		Artikel a6 = new Artikel("Wasser",Einheit.LITER, 5);
		Artikel a7 = new Artikel("Honig",Einheit.MILLILITER, 6);
		art1.add(a1);
		art1.add(a2);
		art1.add(a3);
		art1.add(a4);
		art1.add(a5);
		art1.add(a6);
		art1.add(a7);
		Rezept rez1 = new Rezept("Hühnersuppe", "Leckere und gesunde Suppe mit Hühnerfleisch", "Wasser in den Topf, alles rein",
				5, "5 Stunden", kat1, art1);
		rezepte.add(rez1);
		kc1.getKuechenApp().getRezeptliste().setRezepte(rezepte);
		
		assertNotEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().size(), kc2.getKuechenApp().getRezeptliste().getRezepte().size());
		
		ioc1.speicherRezepte();
		
		ioc2.ladeRezepte();

		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().size(), kc2.getKuechenApp().getRezeptliste().getRezepte().size());
		
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getBeschreibung(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getBeschreibung());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getPortionenzahl(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getPortionenzahl());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getZubereitung(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getZubereitung());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getZubereitungszeit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getZubereitungszeit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getKategorie().get(0).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getKategorie().get(0).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(0).getMasseinheit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(0).getMasseinheit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(0).getMenge(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(0).getMenge());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(0).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(0).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(1).getMasseinheit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(1).getMasseinheit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(1).getMenge(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(1).getMenge());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(1).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(1).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(2).getMasseinheit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(2).getMasseinheit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(2).getMenge(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(2).getMenge());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(2).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(2).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(3).getMasseinheit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(3).getMasseinheit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(3).getMenge(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(3).getMenge());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(3).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(3).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(4).getMasseinheit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(4).getMasseinheit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(4).getMenge(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(4).getMenge());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(4).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(4).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(5).getMasseinheit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(5).getMasseinheit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(5).getMenge(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(5).getMenge());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(5).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(5).getName());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(6).getMasseinheit(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(6).getMasseinheit());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(6).getMenge(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(6).getMenge());
		assertEquals(kc1.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(6).getName(),
				kc2.getKuechenApp().getRezeptliste().getRezepte().get(0).getArtikel().get(6).getName());
	}
}
