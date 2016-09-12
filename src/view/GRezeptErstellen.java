package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.Kategorie;
import control.KochController;

@SuppressWarnings("serial")
public class GRezeptErstellen extends JFrame {

	private final JPanel contentPane;
	private final JTable table;

	private final JLabel lblErrorZutat;
	private final JLabel lblError;
	private final JLabel lblGericht;
	private final JLabel lblZutaten;
	private final JLabel lblBez;
	private final JLabel lblMenge;
	private final JLabel lblEinheit;
	private final JLabel lblRezeptErstellen;
	private final JLabel lblPersonenanzahl;
	private final JLabel lblKategorie;
	private final JLabel lblZubereitung;
	private final JLabel lblBeschreibung;
	private final JLabel lblZubereitungszeit;
	private JLabel labelKat;

	private final JTextField txtGericht;
	private final JTextField txtBez;
	private final JTextField txtMenge;
	private final JTextField txtZubZeit;
	private final JTextPane textPaneZub;
	private final JTextPane textPaneBes;

	private final JScrollPane scrollPane;
	private final JScrollPane scrollPane_Kat;
	private final JScrollPane scrollPane_Zub;
	private final JScrollPane scrollPane_Bes;

	private final JComboBox<Einheit> comboBoxEinheit;
	private final JComboBox<String> comboBoxPersAnz;

	private final JButton btnHinzu;
	private final JButton btnAendern;
	private final JButton btnLoeschen;
	private final JButton btnAuswaehlen;
	private final JButton btnSpeichern;

	private JDialog katWaehler;
	private ArrayList<String> selectedKat;
	private KochController kochCtrl;
	private GHauptfenster gHauptfenster;

	// Funktionen für KategorieDialog
	public JLabel getLabelKat() {
		return labelKat;
	}

	public void setLabelKat(final JLabel labelKat) {
		this.labelKat = labelKat;
	}

	public void setSelectedKat(ArrayList<String> kats) {
		selectedKat = kats;
	}

	public ArrayList<String> getSelectedKat() {
		return selectedKat;
	}

	public ArrayList<String> getKategorien() {
		ArrayList<String> katListe = new ArrayList<>();
		ArrayList<Kategorie> gegebeneKat = new ArrayList<Kategorie>();

		// Default Kategorien festlegen und zur Gui hinzufügen
		ArrayList<String> defaultKat = new ArrayList<>();
		defaultKat.add("Favoriten");
		defaultKat.add("Eigene Rezepte");
		defaultKat.add("Sonstige");
		defaultKat.add("Hauptgericht");
		defaultKat.add("Dessert");
		defaultKat.add("Suppe");
		defaultKat.add("Vorspeise");
		defaultKat.add("Süßspeise");

		for (String k : defaultKat) {
			katListe.add(k);
		}

		// Falls es weitere Kategorien gibt und diese nicht schon in den
		// Default-Kategorien existieren
		try {
			gegebeneKat = kochCtrl.getKuechenApp().getKategorie();
		} catch (Exception e) {

		}
		if (gegebeneKat != null) {
			for (Kategorie kat : gegebeneKat) {
				if (!defaultKat.contains(kat.getName())) {
					katListe.add(kat.getName());
				}
			}
		}
		return katListe;
	}

	/**
	 * Create the panel.
	 */
	public GRezeptErstellen(GHauptfenster gh, final KochController kochCtrl,
			final JTabbedPane tabs) {
		this.kochCtrl = kochCtrl;
		this.gHauptfenster = gh;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 903, 800);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// ------ Error-Label -------//
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(168, 21, 594, 14);
		contentPane.add(lblError);

		// ------ Rezept-Name -------//
		lblGericht = new JLabel("Name:");
		lblGericht.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGericht.setBounds(30, 49, 106, 14);
		getContentPane().add(lblGericht);

		txtGericht = new JTextField();
		txtGericht.setBounds(146, 46, 400, 25);
		getContentPane().add(txtGericht);
		txtGericht.setColumns(10);

		// ------ Zutaten-Elemente -------//
		lblZutaten = new JLabel("Zutaten:");
		lblZutaten.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblZutaten.setBounds(30, 85, 85, 14);
		getContentPane().add(lblZutaten);

		String[] title = new String[] { "Bezeichnung", "Menge", "Maßeinheit" };
		final DefaultTableModel model = new DefaultTableModel(title, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		scrollPane = new JScrollPane();
		scrollPane.setBounds(146, 77, 400, 127);
		getContentPane().add(scrollPane);

		table = new JTable(model);
		table.addInputMethodListener(new InputMethodListener() {
			@Override
			public void caretPositionChanged(final InputMethodEvent event) {
				txtBez.setText(model.getValueAt(table.getSelectedRow(), 0)
						.toString());
				txtMenge.setText(model.getValueAt(table.getSelectedRow(), 1)
						.toString());
				comboBoxEinheit.setSelectedItem(model.getValueAt(
						table.getSelectedRow(), 2).toString());
			}

			@Override
			public void inputMethodTextChanged(final InputMethodEvent event) {
			}
		});

		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.setBorder(new LineBorder(new Color(0, 0, 0)));

		scrollPane.setViewportView(table);

		// Labels
		lblRezeptErstellen = new JLabel("Rezept erstellen");
		lblRezeptErstellen.setFont(new Font("Arial Black", Font.PLAIN, 16));
		lblRezeptErstellen.setBounds(10, 0, 175, 22);
		contentPane.add(lblRezeptErstellen);

		// ----- Fehleranzeige Zutaten ------//
		lblErrorZutat = new JLabel("");
		lblErrorZutat.setForeground(Color.RED);
		lblErrorZutat.setBounds(569, 210, 265, 16);
		contentPane.add(lblErrorZutat);

		lblBez = new JLabel("Bezeichnung:");
		lblBez.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBez.setBounds(121, 206, 105, 20);
		contentPane.add(lblBez);

		lblMenge = new JLabel("Menge:");
		lblMenge.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMenge.setBounds(121, 237, 70, 20);
		contentPane.add(lblMenge);

		lblEinheit = new JLabel("Einheit:");
		lblEinheit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEinheit.setBounds(121, 268, 70, 20);
		contentPane.add(lblEinheit);

		// Textfields
		txtBez = new JTextField();
		txtBez.setBounds(236, 207, 175, 25);
		contentPane.add(txtBez);
		txtBez.setColumns(10);

		txtMenge = new JTextField();
		txtMenge.setBounds(236, 238, 175, 25);
		contentPane.add(txtMenge);
		txtMenge.setColumns(10);

		comboBoxEinheit = new JComboBox<>();
		comboBoxEinheit.setBackground(Color.white);
		// comboBoxEinheit.addItem("");
		comboBoxEinheit.addItem(Einheit.KILOGRAMM);
		comboBoxEinheit.addItem(Einheit.GRAMM);
		comboBoxEinheit.addItem(Einheit.LITER);
		comboBoxEinheit.addItem(Einheit.MILLILITER);
		comboBoxEinheit.addItem(Einheit.STUECK);
		comboBoxEinheit.addItem(Einheit.FLASCHE);
		comboBoxEinheit.addItem(Einheit.PAECKCHEN);
		comboBoxEinheit.addItem(Einheit.PRISE);
		comboBoxEinheit.setBounds(236, 269, 175, 25);
		contentPane.add(comboBoxEinheit);

		// Buttons und ActionListener
		txtBez.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtBez.setSelectionStart(0);
				txtBez.setSelectionEnd(txtMenge.getText().toString().length());
			}
		});

		txtMenge.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtMenge.setSelectionStart(0);
				txtMenge.setSelectionEnd(txtMenge.getText().toString().length());
			}
		});

		btnHinzu = new JButton("Hinzufügen");
		btnHinzu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnHinzu.setBounds(441, 206, 105, 20);
		contentPane.add(btnHinzu);

		btnHinzu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtBez.getText().toString().equals("")) {
					lblErrorZutat.setText("Keine Bezeichnung angegeben.");
					lblErrorZutat.setVisible(true);
				} else if (txtMenge.getText().toString().equals("")) {
					lblErrorZutat.setText("Keine Menge angegeben.");
					lblErrorZutat.setVisible(true);
				} else if (comboBoxEinheit.getSelectedItem().toString()
						.equals("")) {
					lblErrorZutat.setText("Keine Maßeinheit angegeben.");
					lblErrorZutat.setVisible(true);
				} else {
					lblErrorZutat.setVisible(false);
					try {
						double menge = Double.parseDouble(txtMenge.getText());
						Boolean artVorhanden = false;
						for (int i = 0; i < model.getRowCount(); i++) {
							if (model.getValueAt(i, 0).equals(
									txtBez.getText().toString())) {
								artVorhanden = true;
							}
						}
						if (menge < 0) {
							lblErrorZutat
									.setText("Menge darf nicht negativ sein oder 0.");
							lblErrorZutat.setVisible(true);
						} else if (artVorhanden) {
							lblErrorZutat.setText("Artikel schon vorhanden");
							lblErrorZutat.setVisible(true);
						} else {
							model.addRow(new Object[] {
									txtBez.getText(),
									txtMenge.getText(),
									comboBoxEinheit.getSelectedItem()
											.toString() });
							txtBez.setText("");
							txtMenge.setText("");
							comboBoxEinheit.setSelectedIndex(0);
						}
					} catch (NumberFormatException e2) {
						lblErrorZutat
								.setText("Keine korrekte Menge eingetragen!");
						lblErrorZutat.setVisible(true);
					}
				}
			}
		});

		btnAendern = new JButton("Ändern");
		btnAendern.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAendern.setBounds(441, 237, 105, 20);
		contentPane.add(btnAendern);

		btnAendern.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() == -1) {
					if (table.getRowCount() == 0) {
						lblErrorZutat.setText("Zutatenliste ist leer.");
						lblErrorZutat.setVisible(true);
					} else {
						lblErrorZutat.setText("Keine Zutat ausgewählt.");
						lblErrorZutat.setVisible(true);
					}
				} else {
					if (table.getSelectedRowCount() == 1) {
						if (txtBez.getText().toString().equals("")) {
							lblErrorZutat
									.setText("Keine Bezeichnung angegeben.");
							lblErrorZutat.setVisible(true);
						} else if (txtMenge.getText().toString().equals("")) {
							lblErrorZutat.setText("Keine Menge angegeben.");
							lblErrorZutat.setVisible(true);
						} else if (comboBoxEinheit.getSelectedItem().toString()
								.equals("")) {
							lblErrorZutat
									.setText("Keine Maßeinheit angegeben.");
							lblErrorZutat.setVisible(true);
						} else {
							lblErrorZutat.setVisible(false);
							try {
								double menge = Double.parseDouble(txtMenge
										.getText());
								if (menge < 0) {
									lblErrorZutat
											.setText("Menge darf nicht negativ sein oder 0.");
									lblErrorZutat.setVisible(true);
								} else {
									// changeArtikel mit den Werten aus den
									// Textboxen aufrufen
									model.getValueAt(table.getSelectedRow(), 0)
											.toString();
									model.addRow(new Object[] {
											txtBez.getText(),
											txtMenge.getText(),
											comboBoxEinheit.getSelectedItem()
													.toString() });
									txtBez.setText("");
									txtMenge.setText("");
									comboBoxEinheit.setSelectedIndex(0);
								}
							} catch (NumberFormatException e2) {
								lblErrorZutat
										.setText("Keine korrekte Menge eingetragen!");
								lblErrorZutat.setVisible(true);
							}
							model.setValueAt(txtBez.getText(),
									table.getSelectedRow(), 0);
							model.setValueAt(txtMenge.getText(),
									table.getSelectedRow(), 1);
							model.setValueAt(comboBoxEinheit.getSelectedItem()
									.toString(), table.getSelectedRow(), 2);
							for (int i = 0; i < model.getRowCount(); i++) {
								if (model.getValueAt(i, 0).equals("")) {
									model.removeRow(i);
								}
							}
						}
					} else {
						if (txtMenge.getText().toString().equals("")) {
							lblErrorZutat.setText("Keine Menge angegeben.");
							lblErrorZutat.setVisible(true);
						} else {
							lblErrorZutat.setVisible(false);
							for (int i = 0; i < table.getSelectedRowCount(); i++) {
								model.setValueAt(txtMenge.getText(),
										table.getSelectedRows()[i], 1);
							}
						}
					}
				}
			}
		});

		btnLoeschen = new JButton("Löschen");
		btnLoeschen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnLoeschen.setBounds(441, 268, 105, 20);
		contentPane.add(btnLoeschen);

		btnLoeschen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() == -1) {
					if (table.getRowCount() == 0) {
						lblErrorZutat.setText("Keine Zutat vorhanden.");
						lblErrorZutat.setVisible(true);
					} else {
						lblErrorZutat.setText("Kein Element ausgewählt.");
						lblErrorZutat.setVisible(true);
					}
				} else {
					lblErrorZutat.setVisible(false);

					for (int i = table.getSelectedRowCount() - 1; i >= 0; i--) {
						model.removeRow(table.getSelectedRows()[i]);
					}
				}
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (table.getSelectedRowCount() == 1) {
					txtBez.setText(model.getValueAt(table.getSelectedRow(), 0)
							.toString());
					txtMenge.setText(model
							.getValueAt(table.getSelectedRow(), 1).toString());
					comboBoxEinheit.setSelectedItem(model.getValueAt(
							table.getSelectedRow(), 2).toString());
				} else {
					txtBez.setText("");
					txtMenge.setText("");
					comboBoxEinheit.setSelectedItem("");
				}
			}
		});

		// ------ Personenanzahl -------//
		lblPersonenanzahl = new JLabel("Personenanzahl:");
		lblPersonenanzahl.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPersonenanzahl.setBounds(30, 302, 120, 14);
		contentPane.add(lblPersonenanzahl);

		comboBoxPersAnz = new JComboBox<>();
		comboBoxPersAnz.setBackground(Color.WHITE);
		comboBoxPersAnz.addItem("");
		comboBoxPersAnz.addItem("1");
		comboBoxPersAnz.addItem("2");
		comboBoxPersAnz.addItem("3");
		comboBoxPersAnz.addItem("4");
		comboBoxPersAnz.addItem("5");
		comboBoxPersAnz.addItem("6");
		comboBoxPersAnz.addItem("7");
		comboBoxPersAnz.addItem("8");
		comboBoxPersAnz.addItem("9");
		comboBoxPersAnz.addItem("10");
		comboBoxPersAnz.setBounds(146, 300, 46, 25);
		contentPane.add(comboBoxPersAnz);

		// ------ Kategorie anzeigen/auswählen -------//
		lblKategorie = new JLabel("Kategorie:");
		lblKategorie.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblKategorie.setBounds(30, 334, 115, 14);
		contentPane.add(lblKategorie);

		scrollPane_Kat = new JScrollPane();
		scrollPane_Kat.setBounds(146, 335, 265, 38);
		contentPane.add(scrollPane_Kat);

		labelKat = new JLabel("");
		scrollPane_Kat.setViewportView(labelKat);

		btnAuswaehlen = new JButton("Auswählen");
		btnAuswaehlen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				katWaehler = new GKategorieDialog(GRezeptErstellen.this);
				katWaehler.setVisible(true);
			}
		});

		btnAuswaehlen.setBounds(423, 342, 123, 23);
		contentPane.add(btnAuswaehlen);

		// ------ Zubereitung -------//
		lblZubereitung = new JLabel("Zubereitung:");
		lblZubereitung.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblZubereitung.setBounds(30, 386, 115, 14);
		contentPane.add(lblZubereitung);

		scrollPane_Zub = new JScrollPane();
		scrollPane_Zub.setBounds(146, 386, 400, 71);
		contentPane.add(scrollPane_Zub);

		textPaneZub = new JTextPane();
		scrollPane_Zub.setViewportView(textPaneZub);

		// ------ Beschreibung -------//
		lblBeschreibung = new JLabel("Beschreibung: ");
		lblBeschreibung.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBeschreibung.setBounds(30, 468, 98, 14);
		contentPane.add(lblBeschreibung);

		scrollPane_Bes = new JScrollPane();
		scrollPane_Bes.setBounds(146, 468, 400, 70);
		contentPane.add(scrollPane_Bes);

		textPaneBes = new JTextPane();
		scrollPane_Bes.setViewportView(textPaneBes);

		// -------- Zubereitungszeit -------//
		lblZubereitungszeit = new JLabel("Zubereitungszeit:");
		lblZubereitungszeit.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblZubereitungszeit.setBounds(35, 550, 129, 14);
		contentPane.add(lblZubereitungszeit);

		txtZubZeit = new JTextField();
		txtZubZeit.setBounds(146, 563, 400, 25);
		contentPane.add(txtZubZeit);
		txtZubZeit.setColumns(10);

		// --------- Speichern --------//
		final JLabel lblGespeichert = new JLabel("");
		lblGespeichert.setBounds(145, 599, 587, 14);
		contentPane.add(lblGespeichert);
		
		btnSpeichern = new JButton("Speichern");
		btnSpeichern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (txtGericht.getText().trim().equals("")) {
					lblError.setText("Bitte geben Sie einen Rezeptnamen an.");
					lblError.setVisible(true);
				} else if (kochCtrl.getKuechenApp().getRezeptliste()
						.sucheRezept(txtGericht.getText()) != null) {
					lblError.setText("Bitte geben Sie einen anderen Rezeptnamen an.");
					lblError.setVisible(true);
				} else if (model.getRowCount() == 0) {
					lblError.setText("Bitte geben Sie mindestens eine Zutat an.");
					lblError.setVisible(true);
				} else if (comboBoxPersAnz.getSelectedItem().toString()
						.equals("")) {
					lblError.setText("Bitte geben Sie eine Personenanzahl an.");
					lblError.setVisible(true);
				} else if (selectedKat.isEmpty()) {
					lblError.setText("Bitte wählen Sie mindestens eine Kategorie an.");
					lblError.setVisible(true);
				} else if (textPaneZub.getText().trim().equals("")) {
					lblError.setText("Bitte geben Sie eine Zubereitungsbeschreibung an.");
					lblError.setVisible(true);
				} else if (txtZubZeit.getText().trim().equals("")) {
					lblError.setText("Bitte geben Sie eine Zubereitungszeit an.");
					lblError.setVisible(true);
				} else {
					ArrayList<String> zutatenListe = new ArrayList<String>();
					for (int i = 0; i < model.getRowCount(); i++) {
						zutatenListe.add(model.getValueAt(i, 0).toString()
								+ "," + model.getValueAt(i, 1).toString() + ","
								+ model.getValueAt(i, 2).toString());

					}
					kochCtrl.getRezeptController().createRezept(
							txtGericht.getText(),
							textPaneBes.getText(),
							textPaneZub.getText(),
							Integer.parseInt(comboBoxPersAnz.getSelectedItem()
									.toString()), selectedKat,
							txtZubZeit.getText(), zutatenListe);
					lblGespeichert.setText("Rezept gespeichert. Sie können es nun links im Rezeptbaum wiederfinden");
					lblGespeichert.setVisible(true);
					gHauptfenster.updateTree();
					
					txtGericht.setText("");;
					txtBez.setText("");;
					txtMenge.setText("");;
					txtZubZeit.setText("");
					textPaneZub.setText("");
					textPaneBes.setText("");
					while(model.getRowCount() != 0){
						model.removeRow(0);
					}
					selectedKat.clear();
					labelKat.setText("");
					comboBoxPersAnz.setSelectedIndex(0);
					
				}
			}
		});
		btnSpeichern.setBounds(385, 619, 161, 23);
		contentPane.add(btnSpeichern);
		
		JButton btnClose = new JButton("Schließen");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(lblGespeichert.getText().equals("")){
					lblGespeichert.setText("Rezept noch nicht gespeichert. Wirklich schließen?");
				}else if(lblGespeichert.getText().equals("Rezept noch nicht gespeichert. Wirklich schließen?")){
					tabs.remove(tabs.getSelectedIndex());
					txtGericht.setText("");;
					txtBez.setText("");;
					txtMenge.setText("");;
					txtZubZeit.setText("");
					textPaneZub.setText("");
					textPaneBes.setText("");
					while(model.getRowCount() != 0){
						model.removeRow(0);
					}
					selectedKat.clear();
					labelKat.setText("");
					comboBoxPersAnz.setSelectedIndex(0);
					lblGespeichert.setText("");
				}else{
					tabs.remove(tabs.getSelectedIndex());
					txtGericht.setText("");;
					txtBez.setText("");;
					txtMenge.setText("");;
					txtZubZeit.setText("");
					textPaneZub.setText("");
					textPaneBes.setText("");
					while(model.getRowCount() != 0){
						model.removeRow(0);
					}
					selectedKat.clear();
					labelKat.setText("");
					comboBoxPersAnz.setSelectedIndex(0);
					lblGespeichert.setText("");
				}				
			}
		});
		btnClose.setBounds(146, 619, 161, 23);
		contentPane.add(btnClose);

	}
}
