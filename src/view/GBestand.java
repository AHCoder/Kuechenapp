package view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import control.KochController;
import exceptions.EinheitException;


public class GBestand{
	
	private int 		borderWidth; 
	private String		myFont;
	
	private JPanel				panBestand;
	private JLabel 				labelBez;
	private JLabel 				labelMenge;
	private JLabel 				labelMass;
	private JLabel 				labelError;
	private JTextField	 		textBez;
	private JTextField			textMenge;
	private JComboBox<Einheit>	comboBoxMass;
	private JButton				btnHinz;
	private JButton				btnAend;
	private JButton 			btnLoe;
	private JButton 			btnVors;
	private DefaultTableModel 	model;
	private JTable				tableBestand;
	private JScrollPane			scrollBestand;
	private KochController		kochCtrl;
	
	public GBestand(JTabbedPane tabs, KochController kochControl, final GHauptfenster gH){
		this.kochCtrl = kochControl;
		borderWidth 	= 	10;
		myFont			=	"Dialog";
		
		panBestand = new JPanel();
		panBestand.setLayout(null);
		panBestand.setBackground(Color.white);
		tabs.addTab("Bestand", null, panBestand, null);
		
		// ----- Bestandslistenelemente einfügen ----- //
		labelBez = new JLabel("Bezeichnung");
		labelBez.setBounds(borderWidth, borderWidth, 105, 25);
		labelBez.setFont(new Font(myFont, Font.BOLD, 14));
		panBestand.add(labelBez);
		
		labelMenge = new JLabel("Menge");
		labelMenge.setBounds(borderWidth, 2*borderWidth + labelBez.getHeight(), 105, 25);
		labelMenge.setFont(new Font(myFont, Font.BOLD, 14));
		panBestand.add(labelMenge);
		
		labelMass = new JLabel("Maßeinheit");
		labelMass.setBounds(borderWidth, 3*borderWidth + 2*labelBez.getHeight(), 105, 25);
		labelMass.setFont(new Font(myFont, Font.BOLD, 14));
		panBestand.add(labelMass);
		
		textBez = new JTextField("");
		textBez.setBounds(2*borderWidth + labelBez.getWidth(), borderWidth, 105, 25);
		textBez.setHorizontalAlignment(JTextField.RIGHT);
		panBestand.add(textBez);
		
		textMenge = new JTextField("");
		textMenge.setBounds(2*borderWidth + labelBez.getWidth(), 2*borderWidth + textBez.getHeight(), 105, 25);
		textMenge.setHorizontalAlignment(JTextField.RIGHT);
		panBestand.add(textMenge);
		
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
		panBestand.add(comboBoxMass);
		
		btnHinz = new JButton("Hinzufügen");
		btnHinz.setBounds(3*borderWidth + 2*labelBez.getWidth(), borderWidth, 150, 25);
		panBestand.add(btnHinz);
		
		btnAend = new JButton("Ändern");
		btnAend.setBounds(3*borderWidth + 2*labelBez.getWidth(), 2*borderWidth + btnHinz.getHeight(), 150, 25);
		panBestand.add(btnAend);
		
		btnLoe = new JButton("Löschen");
		btnLoe.setBounds(3*borderWidth + 2*labelBez.getWidth(), 3*borderWidth + 2*btnHinz.getHeight(), 150, 25);
		panBestand.add(btnLoe);
		
		labelError = new JLabel();
		labelError.setBounds(10*borderWidth + 3*labelBez.getWidth(), borderWidth, 400, 25);
		labelError.setForeground(Color.red);
		labelError.setVisible(false);
		panBestand.add(labelError);
		
		btnVors = new JButton("Rezepte vorschlagen");
		btnVors.setBounds(tabs.getWidth()- 200 - (int)(1.5*borderWidth), borderWidth, 200, 2*borderWidth + 3*btnHinz.getHeight());
		panBestand.add(btnVors);
		

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
		
		tableBestand = new JTable(model);
		tableBestand.getTableHeader().setFont(new Font(myFont, Font.BOLD, 14));
		tableBestand.setBackground(Color.white);
		
		scrollBestand = new JScrollPane(tableBestand);		
		scrollBestand.setToolTipText("Zur Auswahl STRG + (Auswahl der Artikel)");
		scrollBestand.setBounds(borderWidth, 4*borderWidth + 3*labelBez.getHeight(), tabs.getWidth() - (int)(2.5*borderWidth), tabs.getHeight()-(8*borderWidth + 3*labelBez.getHeight()));
		panBestand.add(scrollBestand);
		// ------------------------------------------- //
		
		
		// 
		
		
		
		
		
		
		
		
		
		// ----- Bestandsliste ----- //
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
							kochCtrl.getBestandsController().addArtikel(textBez.getText(), menge, (Einheit) comboBoxMass.getSelectedItem());
							updateModel();
							kochCtrl.getIOController().speicherBestand();
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
				if(tableBestand.getSelectedRow() == -1){
					if(tableBestand.getRowCount() == 0){
						labelError.setText("Bestandliste leer.");
						labelError.setVisible(true);
					}else{
						labelError.setText("Kein Element ausgewählt.");
						labelError.setVisible(true);
					}
				}else{
					if(tableBestand.getSelectedRowCount() == 1){
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
									String altBez = model.getValueAt(tableBestand.getSelectedRow(), 0).toString();
									// Methode changeArtikel wird geändert auf 4 Parameter
									kochCtrl.getBestandsController().changeArtikel(altBez, textBez.getText(), menge, (Einheit) comboBoxMass.getSelectedItem());
									updateModel();
									kochCtrl.getIOController().speicherBestand();
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
							for(int i = 0; i < tableBestand.getSelectedRowCount(); i++){
								model.setValueAt(textMenge.getText(), tableBestand.getSelectedRows()[i], 1);
							}
						}
					}
				}
			}
		});
		
		btnLoe.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tableBestand.getSelectedRow() == -1){
					if(tableBestand.getRowCount() == 0){
						labelError.setText("Bestandliste leer.");
						labelError.setVisible(true);
					}else{
						labelError.setText("Kein Element ausgewählt.");
						labelError.setVisible(true);
					}
				}else{
					// Anlegen einer "LoeschListe" und füllen der Liste mit den ausgewählten Artikelnamen
					labelError.setVisible(false);
					ArrayList<String> loeschListe = new ArrayList<String>();
					for (int x : tableBestand.getSelectedRows() ) {
						loeschListe.add(model.getValueAt(x, 0).toString());
					}
					// Aufruf der Methode zum Löschen im BestandsController
					kochCtrl.getBestandsController().deleteArtikel(loeschListe);
					// Ansicht anpassen
					updateModel();
					kochCtrl.getIOController().speicherBestand();
					// 
					textBez.setText("");
					textMenge.setText("");
					comboBoxMass.setSelectedIndex(0);
				}
			}
		});
		
		
		
		tableBestand.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(tableBestand.getSelectedRowCount() == 1){
					textBez.setText(model.getValueAt(tableBestand.getSelectedRow(), 0).toString());
					textMenge.setText(model.getValueAt(tableBestand.getSelectedRow(), 1).toString());
					comboBoxMass.setSelectedItem(model.getValueAt(tableBestand.getSelectedRow(), 2));
				}else{
					textBez.setText("");
					textMenge.setText("");
					comboBoxMass.setSelectedItem("");
				}
			}
		});
		btnVors.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tableBestand.getSelectedRowCount() == 0){
					labelError.setText("Keine Zutat(en) ausgewählt.");
					labelError.setVisible(true);
				}else{
					labelError.setVisible(false);
					ArrayList<String> zutatenListe = new ArrayList<String>();
					for (int x : tableBestand.getSelectedRows() ) {
						zutatenListe.add(model.getValueAt(x, 0).toString());
					}
					kochCtrl.getBestandsController().suggestRezept(zutatenListe);
					gH.updateTree();
				}
			}
		});
		// ------------------------- //
	}
	
	public void update(JTabbedPane tabs){
		scrollBestand.setBounds(borderWidth, 4*borderWidth + 3*labelBez.getHeight(), tabs.getWidth() - (int)(2.5*borderWidth), tabs.getHeight()-(8*borderWidth + 3*labelBez.getHeight()));
		btnVors.setBounds(tabs.getWidth()- 200 - (int)(1.5*borderWidth), borderWidth, 200, 2*borderWidth + 3*btnHinz.getHeight());
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
		for (model.Artikel a : kochCtrl.getKuechenApp().getLager().getArtikelList() ) {
			model.addRow(new Object[] {a.getName(), a.getMenge(), a.getMasseinheit()});
		}
		tableBestand.setModel(model);
		tableBestand.updateUI();
	}
}
