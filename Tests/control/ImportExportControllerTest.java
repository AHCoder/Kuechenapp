package control;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Artikel;
import model.Kategorie;
import model.KuechenApp;
import model.Rezept;
import model.Rezeptliste;

import org.jdom2.JDOMException;
import org.junit.Before;
import org.junit.Test;

import view.Einheit;
import control.KochController;
import control.ImportExportController;



public class ImportExportControllerTest {

	private Rezept meinRezept;
	private Rezept meinRezept2;
	private Rezept meinRezept2x;
	private Rezept meinRezept2xx;
	private Rezept meinRezept2xxx;
	private Rezept meinRezept2IV;
	private Rezept meinRezept2V;
	
	private ArrayList<Rezept> rezept = new ArrayList<>();
	private Rezeptliste rezeptListe; 
	private ArrayList<String> auswahlListe;
	
	
	@Before
	public void setUp() throws Exception {
		
		Kategorie kategorie = new Kategorie("schnelles Essen", null);
		ArrayList<Kategorie> kategorie2 = new ArrayList<Kategorie>();
		kategorie2.add(kategorie);
		
		ArrayList<Artikel> artikel = new ArrayList<Artikel>();
		Artikel artikelZutat1 = new Artikel("Nudeln", Einheit.KILOGRAMM, 500);
		Artikel artikelZutat2 = new Artikel("Oel", Einheit.MILLILITER, 20);
		Artikel artikelZutat3 = new Artikel("Salz", Einheit.GRAMM, 3);
		
		artikel.add(artikelZutat1);
		artikel.add(artikelZutat2);
		artikel.add(artikelZutat3);
		meinRezept = new Rezept("Nudeln", "Studentenfutter", "", 1, "30min", kategorie2 , artikel);
		
		
		//2.rezept
		Kategorie kategorie2Rezept = new Kategorie("schnelles Essen", null);
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
		
		//3.Rezept
		Kategorie kategorie2x = new Kategorie("FeinSchmecker", null);
		ArrayList<Kategorie> kategorie2RezeptListex = new ArrayList<Kategorie>();
		kategorie2RezeptListex.add(kategorie2x);
		
		ArrayList<Artikel> artikel2Rezeptx = new ArrayList<Artikel>();
		Artikel artikelZutatX1x = new Artikel("Roulade vom Rind", Einheit.GRAMM, 500);
		Artikel artikelZutatX2x = new Artikel("Kochschinken mit Speckrand", Einheit.GRAMM, 150);
		Artikel artikelZutatX3x = new Artikel("Schmelzkaese (Chester)", Einheit.GRAMM, 60);
		Artikel artikelZutatX4x = new Artikel("Zwiebel", Einheit.GRAMM, 75);
		Artikel artikelZutatX5x = new Artikel("Rotwein", Einheit.MILLILITER, 200);
		Artikel artikelZutatX6x = new Artikel("Olivenoel", Einheit.MILLILITER, 10);
		Artikel artikelZutatX7x = new Artikel("Bratensaft", Einheit.MILLILITER, 20);
		Artikel artikelZutatX8x = new Artikel("Salz und Pfeffer", Einheit.GRAMM, 15);
		Artikel artikelZutatX9x = new Artikel("Rinderbruehe", Einheit.MILLILITER, 400);
		Artikel artikelZutatX10x = new Artikel("dunkler Saucenbinder", Einheit.GRAMM, 75);
		
		artikel2Rezeptx.add(artikelZutatX1x);
		artikel2Rezeptx.add(artikelZutatX2x);
		artikel2Rezeptx.add(artikelZutatX3x);
		artikel2Rezeptx.add(artikelZutatX4x);
		artikel2Rezeptx.add(artikelZutatX5x);
		artikel2Rezeptx.add(artikelZutatX6x);
		artikel2Rezeptx.add(artikelZutatX7x);
		artikel2Rezeptx.add(artikelZutatX8x);
		artikel2Rezeptx.add(artikelZutatX9x);
		artikel2Rezeptx.add(artikelZutatX10x);
		
		String zubereitungx = "Die Rouladen pfeffern und salzen, jede Roulade mit einer Scheibe Schinken und 2 Scheiben Schmelzkaese belegen. Zusammenrollen und mit Spießen fixieren.\n" +
				"In einem Braeter (Pfanne geht auch) scharf anbraten, Zwiebeln wuerfeln und mit braten. Mit Rotwein abloeschen und 10 min. einkoecheln lassen, dann mit der Bruehe aufgießen.\n"
				+ "Aufkochen und die Bratensaftwuerfel dazugeben. 1/2 Stunden, mit geschlossenem Deckel, bei kleiner Flamme garen. Die Rouladen ca. alle 15 min drehen. Bei Bedarf\n" +
				"am Schluss mit braunem Soßenbinder binden."; 
		
		meinRezept2x = new Rezept("Rinder Roulade", "Roulade", zubereitungx , 3, "65min", kategorie2RezeptListex , artikel2Rezeptx);
		
		
		//4.Rezept
		Kategorie kategorie2xx = new Kategorie("Hauptgericht", null);
		ArrayList<Kategorie> kategorie2RezeptListexx = new ArrayList<Kategorie>();
		kategorie2RezeptListexx.add(kategorie2xx);
		
		ArrayList<Artikel> artikel2Rezeptxx = new ArrayList<Artikel>();
		Artikel artikelZutatX1xx = new Artikel("Blumenkohl", Einheit.GRAMM, 150);
		Artikel artikelZutatX2xx = new Artikel("Schmand", Einheit.GRAMM, 25);
		Artikel artikelZutatX3xx = new Artikel("Petersilie", Einheit.STUECK, 3);
		Artikel artikelZutatX4xx = new Artikel("Meersalz", Einheit.GRAMM, 6);
		Artikel artikelZutatX5xx = new Artikel("Pfeffer", Einheit.GRAMM, 6);
		Artikel artikelZutatX6xx = new Artikel("Fett", Einheit.GRAMM, 13);
		
		artikel2Rezeptxx.add(artikelZutatX1xx);
		artikel2Rezeptxx.add(artikelZutatX2xx);
		artikel2Rezeptxx.add(artikelZutatX3xx);
		artikel2Rezeptxx.add(artikelZutatX4xx);
		artikel2Rezeptxx.add(artikelZutatX5xx);
		artikel2Rezeptxx.add(artikelZutatX6xx);
		
		String zubereitungxx = "Die geputzten Stiele in kleine Stücke schneiden. Fett in einer"
				+ "20iger-Pfanne auf mittlerer Stufe erhitzen und die Stiele kurz rundum leicht"
				+ "anrösten. Die Hitze auf kleinere Stufe runterschalten, Deckel drauf und ca."
				+ "15-20 Minuten dünsten lassen. Nach 15 Minuten Bissprobe nehmen. Mit Salz und"
				+ "Pfeffer abschmecken, den EL Schmand unterrühren, Petersilie unterheben und servieren." 
				+ "Der feine Blumenkohlgeschmack - aufgepeppt mit Pfeffer und veredelt mit Schmand"
				+ "- begeistert!";
		
		meinRezept2xx = new Rezept("Gedünstete Stiele", "Gedünstete Stiele", zubereitungxx, 1, "15min", kategorie2RezeptListexx, artikel2Rezeptxx);
		
		//5.Rezept
		Kategorie kategorie2xxx = new Kategorie("Dessert", null);
		ArrayList<Kategorie> kategorie2RezeptListexxx = new ArrayList<Kategorie>();
		kategorie2RezeptListexxx.add(kategorie2xxx);
		
		ArrayList<Artikel> artikel2Rezeptxxx = new ArrayList<Artikel>();
		Artikel artikelZutatX1xxx = new Artikel("Gelatine", Einheit.GRAMM, 50);
		Artikel artikelZutatX2xxx = new Artikel("Zitrone", Einheit.STUECK, 1);
		Artikel artikelZutatX3xxx = new Artikel("Orangensaft", Einheit.MILLILITER, 150);
		Artikel artikelZutatX4xxx = new Artikel("Wein, weiß", Einheit.MILLILITER, 125);
		Artikel artikelZutatX5xxx = new Artikel("Eigelb", Einheit.STUECK, 5);
		Artikel artikelZutatX6xxx = new Artikel("Zucker", Einheit.GRAMM, 125);
		Artikel artikelZutatX7xxx = new Artikel("Vanillezucker", Einheit.PAECKCHEN, 2);
		Artikel artikelZutatX8xxx = new Artikel("Schlagsahne", Einheit.MILLILITER, 250);
		Artikel artikelZutatX9xxx = new Artikel("Löffelbiskuits", Einheit.STUECK, 4);
		
		artikel2Rezeptxxx.add(artikelZutatX1xxx);
		artikel2Rezeptxxx.add(artikelZutatX2xxx);
		artikel2Rezeptxxx.add(artikelZutatX3xxx);
		artikel2Rezeptxxx.add(artikelZutatX4xxx);
		artikel2Rezeptxxx.add(artikelZutatX5xxx);
		artikel2Rezeptxxx.add(artikelZutatX6xxx);
		artikel2Rezeptxxx.add(artikelZutatX7xxx);
		artikel2Rezeptxxx.add(artikelZutatX8xxx);
		artikel2Rezeptxxx.add(artikelZutatX9xxx);
		
		String zubereitungxxx = "Gelatine in etwas Wasser einweichen. Die Zitrone abreiben"
				+ " und auspressen. 4 EL davon abmessen und zum Orangensaft geben. Mit dem"
				+ " Weißwein mischen. Eigelb, Zucker und Vanillezucker sehr schaumig rühren."
				+ " Die Zitronenschale und das Saftgemisch zugeben. Die Gelatine auflösen und"
				+ " unterrühren. Die Creme kalt stellen. Erst wenn sie zu Gelieren beginnt, die"
				+ " Sahne steif schlagen und unterheben. Bis zum Verzehr kalt stellen. Zum"
				+ " Servieren legt man 1 Löffelbiskuit in ein Schälchen und gibt kräftig Portwein"
				+ " nach Bedarf darüber. Nun wartet man etwas, bis der Löffelbiskuit sich voll"
				+ " gesogen hat. Dann gibt man die Creme darüber und serviert sofort.";
		
		meinRezept2xxx = new Rezept("Eis", "lecker lecker", zubereitungxxx, 2, "30min", kategorie2RezeptListexxx, artikel2Rezeptxxx);
		
		//6.Rezept
		Kategorie kategorie2IV = new Kategorie("Süßspeise", null);
		ArrayList<Kategorie> kategorie2RezeptListeIV = new ArrayList<Kategorie>();
		kategorie2RezeptListeIV.add(kategorie2IV);
		
		ArrayList<Artikel> artikel2RezeptIV = new ArrayList<Artikel>();
		Artikel artikelZutatX1IV = new Artikel("Mascarpone", Einheit.GRAMM, 500);
		Artikel artikelZutatX2IV = new Artikel("Magerquark", Einheit.GRAMM, 250);
		Artikel artikelZutatX3IV = new Artikel("Sauerrahm", Einheit.GRAMM , 200);
		Artikel artikelZutatX4IV = new Artikel("Vanillezucker", Einheit.PAECKCHEN, 1);
		Artikel artikelZutatX5IV= new Artikel("Zucker, braun", Einheit.GRAMM, 50);
		Artikel artikelZutatX6IV = new Artikel("Beeren, gemischt", Einheit.GRAMM, 800);
		
		artikel2RezeptIV.add(artikelZutatX1IV);
		artikel2RezeptIV.add(artikelZutatX2IV);
		artikel2RezeptIV.add(artikelZutatX3IV);
		artikel2RezeptIV.add(artikelZutatX4IV);
		artikel2RezeptIV.add(artikelZutatX5IV);
		artikel2RezeptIV.add(artikelZutatX6IV);
		
		String zubereitungIV = "Mascarpone mit Rahm und Quark vermischen, Vanillezucker"
				+ " und Honig dazugeben bis es angenehm leicht süßlich schmeckt.In geeignete"
				+ " Gläser (Eisbecher) Mascarponemasse und gefrorene Früchte schichten. Auf die"
				+ " Schicht Früchte etwas braunen Zucker geben. Die letzte Schicht ist die"
				+ " Mascarponemasse, darauf wird Dekorativ noch etwas brauner Zucker gestreut."
				+ "Die Süßspeise sollte mind. 6 Std. vor Verzehr gemacht werden, damit die"
				+ " Früchte auch auftauen. Man kann sie auch einen Abend vorher machen, dann"
				+ " sollte sie aber im Kühlschrank aufbewahrt werden.";
		
		meinRezept2IV = new Rezept("Beerenmix", "voll lecker", zubereitungIV, 3, "6 1/2 std", kategorie2RezeptListeIV, artikel2RezeptIV);
		
		//7.Rezept
		Kategorie kategorie2V = new Kategorie("Vorspeise", null);
		ArrayList<Kategorie> kategorie2RezeptListeV = new ArrayList<Kategorie>();
		kategorie2RezeptListeV.add(kategorie2IV);
		
		ArrayList<Artikel> artikel2RezeptV = new ArrayList<Artikel>();
		Artikel artikelZutatX1V = new Artikel("Möhren", Einheit.GRAMM, 500);
		Artikel artikelZutatX2V = new Artikel("Olivenöl", Einheit.MILLILITER, 110);
		Artikel artikelZutatX3V = new Artikel("Knoblauch", Einheit.STUECK , 2);
		Artikel artikelZutatX4V = new Artikel("Salz", Einheit.GRAMM, 5);
		Artikel artikelZutatX5V= new Artikel("Naturjoghurt, cremig", Einheit.GRAMM, 300);
		Artikel artikelZutatX6V = new Artikel("Chiliflocken", Einheit.GRAMM, 5);
		Artikel artikelZutatX7V = new Artikel("Minze, getrocknete", Einheit.GRAMM, 5);
		Artikel artikelZutatX8V = new Artikel("etersilie, gehackte", Einheit.GRAMM, 20);
		
		artikel2RezeptV.add(artikelZutatX1V);
		artikel2RezeptV.add(artikelZutatX2V);
		artikel2RezeptV.add(artikelZutatX3V);
		artikel2RezeptV.add(artikelZutatX4V);
		artikel2RezeptV.add(artikelZutatX5V);
		artikel2RezeptV.add(artikelZutatX6V);
		artikel2RezeptV.add(artikelZutatX7V);
		artikel2RezeptV.add(artikelZutatX8V);
		
		String zubereitungV = "Die Möhren waschen, schälen und grob raspeln. Das Olivenöl"
				+ " in einer Pfanne erhitzen und die Möhrenraspel darin dünsten (keine Farbe"
				+ " nehmen lassen). Dieser Vorgang dauert ca. 15 min. Zwischendurch immer"
				+ " wieder umrühren. Die Möhren abkühlen lassen. Inzwischen den Knoblauch"
				+ " schälen und mit dem Salz zu Brei verarbeiten (alternativ mit Knoblauchpresse"
				+ " zerquetschen). Den Jogurt mit dem Knoblauch, anschließend mit den Möhren"
				+ " vermischen und anrichten. Zum Schluss mit der Petersilie, der Minze oder"
				+ " den Chiliflocken nach Belieben verzieren.";
		
		meinRezept2V = new Rezept("Möhrenauflauf", "schnelle, leckere Vorspeise mit Karotten und Knoblauchjoghurt",
									zubereitungV, 4, "30min", kategorie2RezeptListeV, artikel2RezeptV);
		
		rezept.add(meinRezept2);
		rezept.add(meinRezept);
		rezept.add(meinRezept2x);
		rezept.add(meinRezept2xx);
		rezept.add(meinRezept2xxx);
		rezept.add(meinRezept2IV);
		rezept.add(meinRezept2V);
		rezeptListe = new Rezeptliste(rezept);
	}

	@Test
	public void testImportExportController(){
		ImportExportController importExportController = new ImportExportController(new KochController());
		assertNotNull(importExportController);
	}
	
	@Test(expected = NullPointerException.class)
	public void testImportExportControllerNull() throws NullPointerException{
		new ImportExportController(null);
	}
	
	@Test
	public void testExport() throws IOException, Exception {
		ArrayList<String> auswahlListe = new ArrayList<>();
		auswahlListe.add("Nudeln");
		auswahlListe.add("Huehnersuppe");
		auswahlListe.add("Rinder Roulade");
		auswahlListe.add("Gedünstete Stiele");
		auswahlListe.add("Eis");
		auswahlListe.add("Beerenmix");
		auswahlListe.add("Möhrenauflauf");
		
		
		////***************FILESELECTOR*****************//
		JFileChooser filechooser = new JFileChooser();
		File file;
		FileNameExtensionFilter fnef = new FileNameExtensionFilter("Markup: xml", "xml");
		filechooser.setFileFilter(fnef);
		filechooser.setDialogType(JFileChooser.SAVE_DIALOG);
		filechooser.setDialogTitle("Speichern unter...");

		int result = filechooser.showSaveDialog(null);
		String pfad = "";
		
		if(result == JFileChooser.APPROVE_OPTION){
			pfad = filechooser.getSelectedFile().toString();
			file = new File(pfad);
			if(fnef.accept(file)){
                System.out.println(pfad + " kann gespeichert werden."); 
			}
			else
				pfad = pfad + ".xml";
			
			//mit diesem konstruktor wird das null-problem der rezeptliste gelöst
			//ausgelöst durch sucheRezeptMethode
			// arraylist<rezept> wird als parameter übergeben und weitergeleitet, mehrere Konstruktor-aufrufe
			KochController kctrl = new KochController(rezept);
			ImportExportController iec = kctrl.getImportExportController();
			try {
				iec.export(auswahlListe, pfad);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			} 
			catch(Exception e){
				e.printStackTrace();
			}
		}
		////***************FILESELECTOR*****************//
	}
	
	@Test
	public void testExportListeLeer() throws Exception{
		ArrayList<String> auswahlListe2 = new ArrayList<String>();
		
	////***************FILESELECTOR*****************//
			JFileChooser filechooser = new JFileChooser();
			File file;
			FileNameExtensionFilter fnef = new FileNameExtensionFilter("Markup: xml", "xml");
			filechooser.setFileFilter(fnef);
			filechooser.setDialogType(JFileChooser.SAVE_DIALOG);
			filechooser.setDialogTitle("Speichern unter...");

			int result = filechooser.showSaveDialog(null);
			String pfad = "";
			
			if(result == JFileChooser.APPROVE_OPTION){
				pfad = filechooser.getSelectedFile().toString();
				file = new File(pfad);
				if(fnef.accept(file)){
	                System.out.println(pfad + " kann gespeichert werden."); 
				}
				else
					pfad = pfad + ".xml";
				
				//mit diesem konstruktor wird das null-problem der rezeptliste geloest
				//ausgeloest durch sucheRezeptMethode
				// arraylist<rezept> wird als parameter übergeben und weitergeleitet, (mehrere Konstruktor-aufrufe)
				KochController kctrl = new KochController(rezept);
				ImportExportController iec = kctrl.getImportExportController();
				try {
					iec.export(auswahlListe2, pfad);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw e;
				} 
				catch (Exception e){
					assertTrue(true);
				}
			}
		////***************FILESELECTOR*****************//
	}
	

		@Test
		public void testImportieren() 
		{
			JFileChooser fileChooser = new JFileChooser();
			File xml;
			FileNameExtensionFilter fnef = new FileNameExtensionFilter("Markup: xml", "xml");
			
			fileChooser.setMultiSelectionEnabled(false);
			fileChooser.setDialogTitle("Datei importieren");
			fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
			fileChooser.setFileFilter(fnef);
			
			int result = fileChooser.showOpenDialog(null);
			String pfad = "";
			if(result == JFileChooser.APPROVE_OPTION)
			{
				pfad = fileChooser.getSelectedFile().toString();
				xml = new File(pfad);
			}
			KochController kochCtrl = new KochController();
			ImportExportController impExpCtrl = kochCtrl.getImportExportController();
			
			List<String> liste1 = new LinkedList<String>();
			
			List<String> liste2 = new LinkedList<String>();
			liste2.add("Kirschen");
			liste2.add("Waffeln");
			liste2.add("Bananen");
			
			List<String> liste3 = new LinkedList<String>();
			liste3.add("Waffeln");
			liste3.add("Nudeln");
			liste3.add("Huehnersuppe");
			liste3.add("Rinder Roulade");
			
			List<String> liste4 = new LinkedList<String>();
			liste4.add("Waffeln");
			liste4.add("Nudeln");
			liste4.add("Rinder Roulade");
			
			
			int before;
			try
			{	
				before=kochCtrl.getKuechenApp().getRezeptliste().groesse();
				impExpCtrl.importieren(liste1, pfad);
				assertEquals(before, kochCtrl.getKuechenApp().getRezeptliste().groesse());
				
				before=kochCtrl.getKuechenApp().getRezeptliste().groesse();
				impExpCtrl.importieren(liste2, pfad);
				assertEquals(before, kochCtrl.getKuechenApp().getRezeptliste().groesse());
				assertFalse(kochCtrl.getKuechenApp().getRezeptliste().enthaelt("Kirschen"));
				assertFalse(kochCtrl.getKuechenApp().getRezeptliste().enthaelt("Waffeln"));
				assertFalse(kochCtrl.getKuechenApp().getRezeptliste().enthaelt("Bananen"));
				
				before = kochCtrl.getKuechenApp().getRezeptliste().groesse();
				impExpCtrl.importieren(liste3, pfad);
				assertEquals(before+3, kochCtrl.getKuechenApp().getRezeptliste().groesse());
				assertFalse(kochCtrl.getKuechenApp().getRezeptliste().enthaelt("Waffeln"));
				assertTrue(kochCtrl.getKuechenApp().getRezeptliste().enthaelt("Nudeln"));
				assertTrue(kochCtrl.getKuechenApp().getRezeptliste().enthaelt("Huehnersuppe"));
				assertTrue(kochCtrl.getKuechenApp().getRezeptliste().enthaelt("Rinder Roulade"));
				
				
				before = kochCtrl.getKuechenApp().getRezeptliste().groesse();
				impExpCtrl.importieren(liste4, pfad);
				assertEquals(before+2, kochCtrl.getKuechenApp().getRezeptliste().groesse());
				assertFalse(kochCtrl.getKuechenApp().getRezeptliste().enthaelt("Waffeln"));
				assertTrue(kochCtrl.getKuechenApp().getRezeptliste().enthaelt("Nudeln"));
				assertTrue(kochCtrl.getKuechenApp().getRezeptliste().enthaelt("Rinder Roulade"));
			}
			
			catch(Exception e)
			{
				if(e instanceof NullPointerException)
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				if(e instanceof JDOMException)
				{
					System.out.println(e.getMessage());
				}
				if(e instanceof IOException)
				{
					System.out.println(e.getMessage());
				}
			}
		}
	
		@Test
		public void testReadFile() 
		{
			JFileChooser fileChooser = new JFileChooser();
			File xml;
			FileNameExtensionFilter fnef = new FileNameExtensionFilter("Markup: xml", "xml");
			
			fileChooser.setMultiSelectionEnabled(false);
			fileChooser.setDialogTitle("Datei importieren");
			fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
			fileChooser.setFileFilter(fnef);
			
			int result = fileChooser.showOpenDialog(null);
			String pfad = "";
			if(result == JFileChooser.APPROVE_OPTION)
			{
				pfad = fileChooser.getSelectedFile().toString();
				xml = new File(pfad);
			}
			KochController kochCtrl = new KochController();
			ImportExportController impExpCtrl = kochCtrl.getImportExportController();
			Rezeptliste rezepte = new Rezeptliste();
			int before = rezepte.groesse();
			
			try
			{
				rezepte = impExpCtrl.readFile(pfad);
				assertEquals(before+3, rezepte.groesse());
				assertTrue(rezepte.enthaelt("Nudeln"));
				assertTrue(rezepte.enthaelt("Huehnersuppe"));
				assertTrue(rezepte.enthaelt("Rinder Roulade"));
				
			}
			
			catch(Exception e)
			{
				if(e instanceof NullPointerException)
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				if(e instanceof JDOMException)
				{
					System.out.println(e.getMessage());
				}
				if(e instanceof IOException)
				{
					System.out.println(e.getMessage());
				}
			}
		}
		

}
