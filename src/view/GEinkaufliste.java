package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import control.KochController;
import exceptions.EinheitException;


public class GEinkaufliste {
	
	private int 		borderWidth; 
	private String		myFont;
	
	private JPanel				panEListe;
	private JLabel 				labelBez;
	private JLabel 				labelMenge;
	private JLabel 				labelMass;
	private JLabel 				labelError;
	private JTextField	 		textBez;
	private JTextField			textMenge;
	private JComboBox<Einheit> 	comboBoxMass;
	private JButton				btnHinz;
	private JButton				btnAend;
	private JButton 			btnLoe;
	private JButton 			btnZuBest;
	private JButton 			btnDruck;
	private DefaultTableModel 	model;
	private JTable				tableEinkauf;
	private JScrollPane			scrollEinkauf;
	private KochController		kochCtrl;
	private final GBestand			gBestand;
	
	public GEinkaufliste(JTabbedPane tabs, KochController kochControl, GBestand guiBestand){
		this.kochCtrl = kochControl;
		this.gBestand = guiBestand;
		borderWidth 	= 	10;
		myFont			=	"Dialog";
		
		panEListe = new JPanel();
		panEListe.setLayout(null);
		panEListe.setBackground(Color.white);
		tabs.addTab("Einkaufsliste", null, panEListe, null);
		
		// ----- Einkaufslistenelemente einfügen ----- //
		labelBez = new JLabel("Bezeichnung");
		labelBez.setBounds(borderWidth, borderWidth, 105, 25);
		labelBez.setFont(new Font(myFont, Font.BOLD, 14));
		panEListe.add(labelBez);
		
		labelMenge = new JLabel("Menge");
		labelMenge.setBounds(borderWidth, 2*borderWidth + labelBez.getHeight(), 105, 25);
		labelMenge.setFont(new Font(myFont, Font.BOLD, 14));
		panEListe.add(labelMenge);
		
		labelMass = new JLabel("Maßeinheit");
		labelMass.setBounds(borderWidth, 3*borderWidth + 2*labelBez.getHeight(), 105, 25);
		labelMass.setFont(new Font(myFont, Font.BOLD, 14));
		panEListe.add(labelMass);
		
		textBez = new JTextField("");
		textBez.setBounds(2*borderWidth + labelBez.getWidth(), borderWidth, 105, 25);
		textBez.setHorizontalAlignment(SwingConstants.RIGHT);
		panEListe.add(textBez);
		
		textMenge = new JTextField("");
		textMenge.setBounds(2*borderWidth + labelBez.getWidth(), 2*borderWidth + textBez.getHeight(), 105, 25);
		textMenge.setHorizontalAlignment(SwingConstants.RIGHT);
		panEListe.add(textMenge);
		
		comboBoxMass = new JComboBox<Einheit>();
		comboBoxMass.setBackground(Color.white);
		comboBoxMass.setFont(new Font(myFont, Font.PLAIN, 12));
		comboBoxMass.addItem(Einheit.KILOGRAMM);
		comboBoxMass.addItem(Einheit.GRAMM);
		comboBoxMass.addItem(Einheit.LITER);
		comboBoxMass.addItem(Einheit.MILLILITER);
		comboBoxMass.addItem(Einheit.STUECK);
		comboBoxMass.addItem(Einheit.FLASCHE);
		comboBoxMass.addItem(Einheit.PAECKCHEN);
		comboBoxMass.setBounds(2*borderWidth + labelBez.getWidth(), 3*borderWidth + 2*textBez.getHeight(), 105, 25);
		panEListe.add(comboBoxMass);
		
		btnHinz = new JButton("Hinzufügen");
		btnHinz.setBounds(3*borderWidth + 2*labelBez.getWidth(), borderWidth, 150, 25);
		panEListe.add(btnHinz);
		
		btnAend = new JButton("Ändern");
		btnAend.setBounds(3*borderWidth + 2*labelBez.getWidth(), 2*borderWidth + btnHinz.getHeight(), 150, 25);
		panEListe.add(btnAend);
		
		btnLoe = new JButton("Löschen");
		btnLoe.setBounds(3*borderWidth + 2*labelBez.getWidth(), 3*borderWidth + 2*btnHinz.getHeight(), 150, 25);
		panEListe.add(btnLoe);
		
		labelError = new JLabel();
		labelError.setBounds(10*borderWidth + 3*labelBez.getWidth(), borderWidth, 400, 25);
		labelError.setForeground(Color.red);
		labelError.setVisible(false);
		panEListe.add(labelError);
		
		btnZuBest = new JButton("Zu Bestand hinzufügen");
		btnZuBest.setBounds(tabs.getWidth()- 200 - (int)(1.5*borderWidth), borderWidth, 200, 2*borderWidth + 3*btnHinz.getHeight());
		panEListe.add(btnZuBest);
		
		btnDruck = new JButton("Drucken");
		btnDruck.setBounds(tabs.getWidth()- 2*btnZuBest.getWidth() - 3*borderWidth, borderWidth, 200, 2*borderWidth + 3*btnHinz.getHeight());
		panEListe.add(btnDruck);
		

		model = new DefaultTableModel(new String[]{"Bezeichnung", "Menge", "Maßeinheit"}, 0){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		tableEinkauf = new JTable(model);
		tableEinkauf.getTableHeader().setFont(new Font(myFont, Font.BOLD, 14));
		tableEinkauf.setBackground(Color.white);
		
		scrollEinkauf = new JScrollPane(tableEinkauf);		
		scrollEinkauf.setToolTipText("Zur Auswahl STRG + (Auswahl der Artikel)");
		scrollEinkauf.setBounds(borderWidth, 4*borderWidth + 3*labelBez.getHeight(), tabs.getWidth() - (int)(2.5*borderWidth), tabs.getHeight()-(8*borderWidth + 3*labelBez.getHeight()));
		panEListe.add(scrollEinkauf);
		
		
		
		
		
		
		textBez.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textBez.setSelectionStart(0);
				textBez.setSelectionEnd(textMenge.getText().toString().length());
			}
		});
		
		textMenge.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textMenge.setSelectionStart(0);
				textMenge.setSelectionEnd(textMenge.getText().toString().length());
			}
		});
		
		btnHinz.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(textBez.getText().toString().equals("")){
					labelError.setText("Keine Bezeichnung angegeben.");
					labelError.setVisible(true);
				}else if(textMenge.getText().toString().equals("")){
					labelError.setText("Keine Menge angegeben.");
					labelError.setVisible(true);
				}else if(comboBoxMass.getSelectedItem().toString().equals("")){
					labelError.setText("Keine Maßeinheit angegeben.");
					labelError.setVisible(true);
				}else{
					labelError.setVisible(false);
					try {
						double menge = Double.parseDouble(textMenge.getText());
						if(menge < 0) {
							labelError.setText("Menge darf nicht negativ sein oder 0.");
							labelError.setVisible(true);
						} else {
							// addArtikel mit den Werten aus den Textboxen aufrufen
							kochCtrl.getEinkaufsController().addArtikel(textBez.getText(), menge, (Einheit) comboBoxMass.getSelectedItem());
							updateModel();
							kochCtrl.getIOController().speicherEinkaufListe();
							textBez.setText("");
							textMenge.setText("");
							comboBoxMass.setSelectedIndex(0);
						}
					} catch (NumberFormatException e2) {
						labelError.setText("Keine ganzzahlige Menge eingetragen!");
						labelError.setVisible(true);
					} catch (EinheitException e3){
						labelError.setText("Artikel schon vorhanden. Maßeinheit überprüfen!");
						labelError.setVisible(true);
					}
				}
			}
		});
		
		btnAend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tableEinkauf.getSelectedRow() == -1){
					if(tableEinkauf.getRowCount() == 0){
						labelError.setText("Einkaufliste leer.");
						labelError.setVisible(true);
					}else{
						labelError.setText("Kein Element ausgewählt.");
						labelError.setVisible(true);
					}
				}else{
					if(tableEinkauf.getSelectedRowCount() == 1){
						if(textBez.getText().toString().equals("")){
							labelError.setText("Keine Bezeichnung angegeben.");
							labelError.setVisible(true);
						}else if(textMenge.getText().toString().equals("")){
							labelError.setText("Keine Menge angegeben.");
							labelError.setVisible(true);
						}else if(comboBoxMass.getSelectedItem().toString().equals("")){
							labelError.setText("Keine Maßeinheit angegeben.");
							labelError.setVisible(true);
						}else{
							labelError.setVisible(false);
							try {
								double menge = Double.parseDouble(textMenge.getText());
								if(menge < 0) {
									labelError.setText("Menge darf nicht negativ sein oder 0.");
									labelError.setVisible(true);
								} else {
									// changeArtikel mit den Werten aus den Textboxen aufrufen
									String altBez = model.getValueAt(tableEinkauf.getSelectedRow(), 0).toString();
									// Methode changeArtikel wird geändert auf 4 Parameter
									kochCtrl.getEinkaufsController().changeArtikel(altBez, textBez.getText(), menge, (Einheit) comboBoxMass.getSelectedItem());
									updateModel();
									kochCtrl.getIOController().speicherEinkaufListe();
									textBez.setText("");
									textMenge.setText("");
									comboBoxMass.setSelectedIndex(0);
								}
							} catch (NumberFormatException e2) {
								labelError.setText("Keine ganzzahlige Menge eingetragen!");
								labelError.setVisible(true);
							}
						}
					}else{
						if(textMenge.getText().toString().equals("")){
							labelError.setText("Keine Menge angegeben.");
							labelError.setVisible(true);
						}else{
							labelError.setVisible(false);
							updateModel();
						}
					}
				}
			}
		});
		
		btnLoe.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tableEinkauf.getSelectedRow() == -1){
					if(tableEinkauf.getRowCount() == 0){
						labelError.setText("Einkaufliste leer.");
						labelError.setVisible(true);
					}else{
						labelError.setText("Kein Element ausgewählt.");
						labelError.setVisible(true);
					}
				}else{
					// Anlegen einer "LoeschListe" und füllen der Liste mit den ausgewählten Artikelnamen
					labelError.setVisible(false);
					ArrayList<String> loeschListe = new ArrayList<String>();
					for (int x : tableEinkauf.getSelectedRows() ) {
						loeschListe.add(model.getValueAt(x, 0).toString());
					}
					// Aufruf der Methode zum Löschen im BestandsController
					kochCtrl.getEinkaufsController().deleteArtikel(loeschListe);
					// Ansicht anpassen
					updateModel();
					kochCtrl.getIOController().speicherEinkaufListe();
					textBez.setText("");
					textMenge.setText("");
					comboBoxMass.setSelectedIndex(0);
				}
			}
		});
		
		btnDruck.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tableEinkauf.getRowCount() == 0 || kochCtrl.getKuechenApp().getEinkaufsliste().getArtikelList().isEmpty()) {
					labelError.setText("Einkaufsliste ist leer!");
					labelError.setVisible(true);
				} else {
					kochCtrl.getEinkaufsController().drucken();
				}
			}
		});
		
		btnZuBest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tableEinkauf.getSelectedRow() == -1){
					if(tableEinkauf.getRowCount() == 0){
						labelError.setText("Einkaufliste leer.");
						labelError.setVisible(true);
					}else{
						labelError.setText("Kein Element ausgewählt.");
						labelError.setVisible(true);
					}						
				} else {
					labelError.setVisible(false);
					ArrayList<String> zuBestandListe = new ArrayList<String>();
					for (int x : tableEinkauf.getSelectedRows() ) {
						zuBestandListe.add(model.getValueAt(x, 0).toString());
					}
					try {
						kochCtrl.getEinkaufsController().addToBestand(zuBestandListe);
					} catch (EinheitException e3){
						labelError.setText("Artikel schon vorhanden. Maßeinheit überprüfen!");
						labelError.setVisible(true);
					}
					updateModel();
					gBestand.updateModel();
					kochCtrl.getIOController().speicherEinkaufListe();
				}
				
			}
		});
		
		tableEinkauf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(tableEinkauf.getSelectedRowCount() == 1){
					textBez.setText(model.getValueAt(tableEinkauf.getSelectedRow(), 0).toString());
					textMenge.setText(model.getValueAt(tableEinkauf.getSelectedRow(), 1).toString());
					comboBoxMass.setSelectedItem(model.getValueAt(tableEinkauf.getSelectedRow(), 2));
				}else{
					textBez.setText("");
					textMenge.setText("");
					comboBoxMass.setSelectedItem("");
				}
			}
		});
		// ------------------------- //
	}
	
	public void update(JTabbedPane tabs){
		scrollEinkauf.setBounds(borderWidth, 4*borderWidth + 3*labelBez.getHeight(), tabs.getWidth() - (int)(2.5*borderWidth), tabs.getHeight()-(8*borderWidth + 3*labelBez.getHeight()));
		btnZuBest.setBounds(tabs.getWidth()- 200 - (int)(1.5*borderWidth), borderWidth, 200, 2*borderWidth + 3*btnHinz.getHeight());
		btnDruck.setBounds(tabs.getWidth()- 2*btnZuBest.getWidth() - 3*borderWidth, borderWidth, 200, 2*borderWidth + 3*btnHinz.getHeight());
	}
	
	public void updateModel() {
		model = new DefaultTableModel(new String[]{"Bezeichnung", "Menge", "Maßeinheit"}, 0){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		for (model.Artikel a : kochCtrl.getKuechenApp().getEinkaufsliste().getArtikelList() ) {
			model.addRow(new Object[] {a.getName(), a.getMenge(), a.getMasseinheit()});
		}
		tableEinkauf.setModel(model);
		tableEinkauf.updateUI();
	}
}
