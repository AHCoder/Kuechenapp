package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import model.Artikel;
import model.Kategorie;
import model.Rezept;
import model.Rezeptliste;
import control.KochController;
import exceptions.EinheitException;


public class GHauptfenster {
	
	private int 		borderWidth; 
	private int 		linkeZ;
	private String		myFont;
	
	private JTextField 	textSuche;
	private JButton 	btnSuche;
	private JButton		btnRezErst;
	private JTree 		treeRecipes;
	private JScrollPane scrollTree;
	private JTabbedPane	tabs;
	private KochController kochCtrl;
	private DefaultMutableTreeNode root;
	private DefaultTreeModel treeModel;
	private GRezeptErstellen gRezeptErstellen;
	private GBestand		gBestand;
	private GEinkaufliste	gEinkaufliste;
	
	public GHauptfenster(final Container contentPane, KochController kochControl){
		this.kochCtrl = kochControl;
		borderWidth = 	10;
		linkeZ 		=	200;
		myFont 		= 	"Dialog";
		gBestand = null;
		gEinkaufliste = null;

		// ----- Linke Zone -------------------------------------------------------------------------------------------------------- //

		// ----- Suchen - Textfeld -----//
		textSuche = new JTextField();
		textSuche.setText("Suchen ...");
		textSuche.setBounds(borderWidth, borderWidth, linkeZ, 25);
		contentPane.add(textSuche);
		
		// ----- Suchen - Button -----//
		btnSuche = new JButton();
		btnSuche.setText("Suchen");
		btnSuche.setBounds(borderWidth, borderWidth + textSuche.getHeight() + borderWidth, linkeZ, 25);
		contentPane.add(btnSuche);
		
		// ----- Rezepte - Tree -----//
		root = new DefaultMutableTreeNode("Rezepte - Kategorien");
		treeModel = new DefaultTreeModel(root);
		treeRecipes = new JTree(treeModel);
		treeRecipes.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		scrollTree = new JScrollPane(treeRecipes);
		scrollTree.setBounds(borderWidth,3*borderWidth + textSuche.getHeight() + btnSuche.getHeight(), linkeZ, contentPane.getHeight()
				- (3*borderWidth + textSuche.getHeight() + btnSuche.getHeight()) - 25 - 2*borderWidth);
		contentPane.add(scrollTree);
		
		// ----- Rezept hinzufügen - Button ----- //
		btnRezErst = new JButton();
		btnRezErst.setText("Rezept erstellen");
		btnRezErst.setBounds(borderWidth, 4*borderWidth + textSuche.getHeight() + btnSuche.getHeight() + scrollTree.getHeight(), linkeZ, 25);
		contentPane.add(btnRezErst);
		
		// -------------------------------------------------------------------------------------------------------------------------- //
		
		// ----- Tabs --------------------------------------------------------------------------------------------------------------- //
		tabs = new JTabbedPane();
		tabs.setFont(new Font(myFont, Font.BOLD, 16));
		tabs.setBounds(2*borderWidth + linkeZ, borderWidth, contentPane.getWidth() - (3*borderWidth + linkeZ), contentPane.getHeight() - 2*borderWidth);
		contentPane.add(tabs);
		
		
		
		gRezeptErstellen = new GRezeptErstellen(this, kochControl, tabs);
		
		
		
		
		// ----- Suchen - Textfeld ----- //
		textSuche.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textSuche.setSelectionStart(0);
				textSuche.setSelectionEnd(textSuche.getText().toString().length());
			}
		});
		// ----------------------------- //
		// ----- Suchen - Button ----- //
		btnSuche.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String suche = textSuche.getText();
				if ( suche.isEmpty() || suche.equals("") || suche.equals("Suchen ...") || suche.equals("Keine gültige Eingabe!")) {
					// Error anzeigen
					textSuche.setText("Keine gültige Eingabe!");
					textSuche.setForeground(Color.RED);
				} else {
					Rezeptliste rezList = kochCtrl.getKuechenApp().getRezeptliste();
					if (rezList == null || rezList.getRezeptliste().isEmpty() || rezList.getRezeptliste() == null) {
						textSuche.setText("Rezeptliste ist leer!");
						textSuche.setForeground(Color.RED);
					} else{
						textSuche.setForeground(Color.BLACK);
						ArrayList<Kategorie> kategorien = kochCtrl.getKuechenApp().getKategorie();
						Kategorie kategorieVorschlaege = kochCtrl.getKuechenApp().getKategorie("Vorschlaege");
						ArrayList<Rezept> vorgeschlageneRezepte = new ArrayList<Rezept>();
						if(kategorieVorschlaege != null){
							//hole Kategorie: Vorschlaege
							for(int i = 0;i<kategorien.size();i++) {
								if(kategorien.get(i).getName().equals("Vorschlaege")) {
									kategorieVorschlaege = kategorien.get(i);
								}
							}
							
							//entferne alle bisherigen Rezepte aus der Kategorie Vorschlaege
							Rezept aktuellesRezept = null;
							ArrayList<Rezept> rezepteInVorschlage = kategorieVorschlaege.getRezepte();
							if (rezepteInVorschlage != null) {
								int vorschlaegeUrspruenglicheSize = rezepteInVorschlage.size();
								for(int i = 0;i<vorschlaegeUrspruenglicheSize;i++){
									aktuellesRezept = rezepteInVorschlage.get(0);
									aktuellesRezept.removeKategorie("Vorschlaege");
								}
							}			
							kategorieVorschlaege.setRezepte(new ArrayList<Rezept>());
						}else{
							kochCtrl.getKuechenApp().getKategorie().add(new Kategorie("Vorschlaege", new ArrayList<Rezept>()));
							kategorieVorschlaege = kochCtrl.getKuechenApp().getKategorie("Vorschlaege");
						}
						ArrayList<Rezept> rezepte = kochCtrl.getKuechenApp().getRezeptliste().getRezepte();
						for(int i = 0; i < rezepte.size(); i++){
							if(rezepte.get(i).getName().toLowerCase().contains(suche.toLowerCase())){
								vorgeschlageneRezepte.add(rezepte.get(i));
								rezepte.get(i).addKategorie(kategorieVorschlaege);
							}
						}
						if (vorgeschlageneRezepte.size() == 0) {
							textSuche.setText("Kein passendes Rezept gefunden!");
							textSuche.setForeground(Color.RED);
						} else {
							kategorieVorschlaege.setRezepte(vorgeschlageneRezepte);
							updateTree();
						}
					} 
				} 
				
			}
		});
		// --------------------------- //
		// ----- Rezepte - Tree -----//
		treeRecipes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeRecipes.getLastSelectedPathComponent();
				JPanel panRecipe = new JPanel();
				panRecipe.setLayout(null);
				boolean nichtVorhanden = true;
				for(int i = 0; i< tabs.getTabCount(); i++){
					if(node != null && node.getUserObject().toString().equals(tabs.getTitleAt(i))){
						nichtVorhanden = false;
						tabs.setSelectedIndex(i);
					}
				}
				if(node != null && node.isLeaf() && nichtVorhanden){
					if(tabs.getTabCount() > 5)
						tabs.remove(3);
					final Rezept rez = kochCtrl.getRezeptController().sucheRezept(treeRecipes.getLastSelectedPathComponent().toString());
					if(rez != null){
						JLabel lblName = new JLabel("Rezept: "+rez.getName());
						lblName.setBounds(borderWidth, borderWidth, 600, 25);
						lblName.setFont(new Font(myFont,Font.BOLD,20));
						panRecipe.add(lblName);
						
						JLabel lblPersonen = new JLabel("Personenanzahl:");
						lblPersonen.setBounds(borderWidth, 2*borderWidth + 25, 120, 35);
						lblPersonen.setFont(new Font(myFont, Font.PLAIN,14));
						panRecipe.add(lblPersonen);
						
						final JTextField textPersonen = new JTextField("1");
						textPersonen.setBounds(lblPersonen.getWidth()+ 2*borderWidth, 2*borderWidth + 25, 40, 25);
						panRecipe.add(textPersonen);
						
						final JEditorPane paneZutatenBeschreibung = new JEditorPane("text/html", "");
						String zutatenBeschreibung = "<html><body><div style = 'font-family: dialog; font-size: 12px;'><strong>Zutaten:</strong><br>";
						final ArrayList<Artikel> artikel = rez.getArtikel();
						for(int i = 0; i < artikel.size(); i++){
							zutatenBeschreibung += artikel.get(i).getMenge() +" "+ artikel.get(i).getMasseinheit().toString().charAt(0) + artikel.get(i).getMasseinheit().toString().substring(1).toLowerCase() + " " +artikel.get(i).getName() + "<br>";
						}
						zutatenBeschreibung += "<br><strong>Beschreibung:<br></strong>" +rez.getBeschreibung()+ "</body></html>";
						paneZutatenBeschreibung.setEditable(false);
						paneZutatenBeschreibung.setText(zutatenBeschreibung);
						final JScrollPane scrollZutatenBeschreibung = new JScrollPane(paneZutatenBeschreibung);
						scrollZutatenBeschreibung.setBorder(BorderFactory.createEmptyBorder());
						scrollZutatenBeschreibung.setBounds(borderWidth, 70, tabs.getWidth()/2-2*borderWidth, tabs.getHeight()-150);
						panRecipe.add(scrollZutatenBeschreibung);
						
						final JEditorPane paneZubereitung = new JEditorPane("text/html","");
						String zubereitung = "<html><body><div style = 'font-family: dialog; font-size: 12px;'><strong>Zubereitung:</strong><br>"+rez.getZubereitung()+"<br><strong>Zubereitungszeit:</strong><br>"+rez.getZubereitungszeit()+"</body></html>";
						paneZubereitung.setEditable(false);
						paneZubereitung.setText(zubereitung);
						final JScrollPane scrollZubereitung = new JScrollPane(paneZubereitung);
						scrollZubereitung.setBorder(BorderFactory.createEmptyBorder());
						scrollZubereitung.setBounds(2*borderWidth + scrollZutatenBeschreibung.getWidth(), 70, tabs.getWidth()/2-2*borderWidth, tabs.getHeight()-150);
						panRecipe.add(scrollZubereitung);
						
						final JLabel lblMessage = new JLabel("");
						lblMessage.setBounds(borderWidth, tabs.getHeight()-4*borderWidth-50, 840, 30);
						panRecipe.add(lblMessage);
						
						final JButton btnEinkauf = new JButton("Zur Einkaufsliste hinzuf.");
						btnEinkauf.setBounds(borderWidth, tabs.getHeight()-4*borderWidth-25, 240, 25);
						panRecipe.add(btnEinkauf);
						
						final JButton btnKochen = new JButton("Rezept kochen");
						btnKochen.setBounds(2*borderWidth + btnEinkauf.getWidth(), btnEinkauf.getY(), 200,25);
						panRecipe.add(btnKochen);
						
						final JButton btnLoeschen = new JButton("Rezept löschen");
						btnLoeschen.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								
							}
						});
						btnLoeschen.setBounds(2*borderWidth + btnKochen.getWidth() + btnKochen.getX(), btnEinkauf.getY(), 200, 25);
						panRecipe.add(btnLoeschen);
						
						final JButton btnSchliesen = new JButton("Schließen");
						btnSchliesen.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {								
									tabs.remove(tabs.getSelectedIndex());												
							}
						});
						btnSchliesen.setBounds(2*borderWidth + btnLoeschen.getX() + btnLoeschen.getWidth(),  btnEinkauf.getY(), 200, 25);
						panRecipe.add(btnSchliesen);
						
						panRecipe.setBackground(Color.WHITE);
						tabs.add(node.getUserObject().toString(), panRecipe);
						tabs.setSelectedComponent(panRecipe);
						
						
						//Loesche Rezept
						btnLoeschen.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								try{
									kochCtrl.getRezeptController().deleteRezept(tabs.getTitleAt(tabs.getSelectedIndex()));
									tabs.remove(tabs.getSelectedIndex());
								}catch(Exception ex){
									
								}
								updateTree();
							}
						});
						
						// ----- Rezept kochen - Button ----- //
						btnKochen.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								try{
									kochCtrl.getRezeptController().kochen(tabs.getTitleAt(tabs.getSelectedIndex()), Integer.parseInt(textPersonen.getText()));
									lblMessage.setText("Kochen: Die benötigten Zutaten wurden von Bestand abgezogen.");
									lblMessage.setVisible(true);
								}catch(Exception ex){
									
								}
								gBestand.updateModel();
							}
						});
						// ---------------------------------- //
						btnEinkauf.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								//for(int i = 0; i < artikel.size(); i++){
									try {
										//kochCtrl.getEinkaufsController().addArtikel(artikel.get(i).getName(), Integer.parseInt(textPersonen.getText()) * artikel.get(i).getMenge(), artikel.get(i).getMasseinheit());
										kochCtrl.getRezeptController().addToEinkaufsliste(rez.getName(), Integer.parseInt(textPersonen.getText()));
										lblMessage.setText("Zutaten, die im Bestand fehlen wurden der Einkaufsliste hinzugefügt.");
										lblMessage.setVisible(true);
									} catch (EinheitException e1) {
										e1.printStackTrace();
									}
								//}
								gEinkaufliste.updateModel();
								//tabs.remove(tabs.getSelectedIndex());
								tabs.setSelectedIndex(1);
							};
						});
						textPersonen.getDocument().addDocumentListener(new DocumentListener() {
							@Override
							public void removeUpdate(DocumentEvent e) {
								try{
									int pers = Integer.parseInt(textPersonen.getText());
									if(pers > 0){
										String zutatenBeschreibung = "<html><body><div style = 'font-family: dialog; font-size: 12px;'><strong>Zutaten:</strong><br>";
										final ArrayList<Artikel> artikel = rez.getArtikel();
										for(int i = 0; i < artikel.size(); i++){
											zutatenBeschreibung += pers*artikel.get(i).getMenge() +" "+ artikel.get(i).getMasseinheit().toString().charAt(0) + artikel.get(i).getMasseinheit().toString().substring(1).toLowerCase() + " " +artikel.get(i).getName() + "<br>";
										}
										zutatenBeschreibung += "<br><strong>Beschreibung:<br></strong>" +rez.getBeschreibung()+ "</body></html>";
										paneZutatenBeschreibung.setText(zutatenBeschreibung);
									}
								}catch(Exception ex){
								}
							}
							
							@Override
							public void insertUpdate(DocumentEvent e) {
								try{
									int pers = Integer.parseInt(textPersonen.getText());
									if(pers > 0){
										String zutatenBeschreibung = "<html><body><div style = 'font-family: dialog; font-size: 12px;'><strong>Zutaten:</strong><br>";
										final ArrayList<Artikel> artikel = rez.getArtikel();
										for(int i = 0; i < artikel.size(); i++){
											zutatenBeschreibung += pers*artikel.get(i).getMenge() +" "+ artikel.get(i).getMasseinheit().toString().charAt(0) + artikel.get(i).getMasseinheit().toString().substring(1).toLowerCase() + " " +artikel.get(i).getName() + "<br>";
										}
										zutatenBeschreibung += "<br><strong>Beschreibung:<br></strong>" +rez.getBeschreibung()+ "</body></html>";
										paneZutatenBeschreibung.setText(zutatenBeschreibung);
									}
								
								}catch(Exception ex){
								}
								
							}
							
							@Override
							public void changedUpdate(DocumentEvent e) {
								try{
									int pers = Integer.parseInt(textPersonen.getText());
									if(pers > 0){
										String zutatenBeschreibung = "<html><body><div style = 'font-family: dialog; font-size: 12px';><strong>Zutaten:</strong><br>";
										final ArrayList<Artikel> artikel = rez.getArtikel();
										for(int i = 0; i < artikel.size(); i++){
											zutatenBeschreibung += pers*artikel.get(i).getMenge() +" "+ artikel.get(i).getMasseinheit().toString().charAt(0) + artikel.get(i).getMasseinheit().toString().substring(1).toLowerCase() + " " +artikel.get(i).getName() + "<br>";
										}
										zutatenBeschreibung += "<br><strong>Beschreibung:<br></strong>" +rez.getBeschreibung()+ "</body></html>";
										paneZutatenBeschreibung.setText(zutatenBeschreibung);
									}
								}catch(Exception ex){
								}
							}
						});
						tabs.addComponentListener(new ComponentAdapter() {
							public void componentResized(java.awt.event.ComponentEvent e) {
								btnEinkauf.setBounds(borderWidth, tabs.getHeight()-4*borderWidth-25, 240, 25);
								btnKochen.setBounds(2*borderWidth + btnEinkauf.getWidth(), btnEinkauf.getY(), 200,25);
								scrollZutatenBeschreibung.setBounds(borderWidth, 70, tabs.getWidth()/2-2*borderWidth, tabs.getHeight()-150);
								scrollZubereitung.setBounds(2*borderWidth + scrollZutatenBeschreibung.getWidth(), 70, tabs.getWidth()/2-2*borderWidth, tabs.getHeight()-150);
							};
						});
					}
				}
			}
		});
		// --------------------------//
		// ----- Rezept erstellen - Button ----- //
		btnRezErst.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				boolean nichtVorhanden = true;
				for(int i = 0; i< tabs.getTabCount(); i++){
					if(tabs.getTitleAt(i).equals("Rezept erstellen")){
						nichtVorhanden = false;
					}
				}
				if(nichtVorhanden)
					tabs.add("Rezept erstellen",gRezeptErstellen.getContentPane());
				tabs.setSelectedComponent(gRezeptErstellen.getContentPane());
			}
		});
		// ------------------------------------- //
		
		
		// -------------------------------------------------------------------- //
	}
	
	public JTabbedPane getTabs(){
		return tabs;
	}
	
	public void update(int width, int height){
		scrollTree.setBounds(borderWidth,3*borderWidth + textSuche.getHeight() + btnSuche.getHeight(), linkeZ, height
				- (3*borderWidth + textSuche.getHeight() + btnSuche.getHeight()) - 25 - 2*borderWidth);
		btnRezErst.setBounds(borderWidth, 4*borderWidth + textSuche.getHeight() + btnSuche.getHeight() + scrollTree.getHeight(), linkeZ, 25);
		tabs.setBounds(2*borderWidth + linkeZ, borderWidth, width - (3*borderWidth + linkeZ), height - 2*borderWidth);
	}
	
	public void updateTree(){
		root = new DefaultMutableTreeNode("Rezepte - Kategorien");
		ArrayList<Kategorie> kategorien = kochCtrl.getKuechenApp().getKategorie();
		for(int i = 0; i < kategorien.size(); i++){
			DefaultMutableTreeNode kat = new DefaultMutableTreeNode(kategorien.get(i).getName());
			ArrayList<Rezept> rezepte = kategorien.get(i).getRezepte();
			for(int j = 0; j < rezepte.size(); j++){
				kat.add(new DefaultMutableTreeNode(rezepte.get(j).getName()));
			}
			root.add(kat);
		}
		treeModel.setRoot(root);
		for(int i = 0; i < treeRecipes.getRowCount(); i++)
			treeRecipes.expandRow(i);
	}
	
	public void updateModel(GBestand gB, GEinkaufliste gE){
		gBestand = gB;
		gEinkaufliste = gE;
	}
}
