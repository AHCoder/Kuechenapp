package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Rezept;
import model.Rezeptliste;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import control.KochController;


public class GImportExport {
	
	private JPanel panI_E;
	private JPanel panHelp;
	private JPanel panModes;
	private JPanel panInh;
	private JButton btnImport;
	private JButton btnExport;
	private JButton btnDateiI;
	private JButton btnDateiE;
	private JButton btnSpeichI;
	private JButton btnToLeft;
	private JButton btnToRight;
	private JButton btnAllToLeft;
	private JButton btnAllToRight;
	private JList listLeft; 
	private JList listRight;
	private JScrollPane scrollImp;
	private JScrollPane scrollExp;
	private DefaultListModel dfModelLeft;
	private DefaultListModel dfModelRight;
	private KochController kochCtrl;
	
	private String myFont;
	private String pfad;
	private int borderWidth;
	
	public GImportExport(JTabbedPane tabs, KochController kochControl, final GHauptfenster gHaupt){
		this.kochCtrl = kochControl;
		borderWidth = 10;
		myFont = "Dialog";
		pfad = "";
		
		panI_E = new JPanel();
		panI_E.setLayout(null);
		panI_E.setBackground(Color.white);
		tabs.addTab("Import/Export", null, panI_E, null);
		
		panHelp = new JPanel();
		panHelp.setBackground(Color.white);
		panHelp.setBounds(0, 0, tabs.getWidth(), 50);
		panI_E.add(panHelp);
		
		final JPanel panLeft = new JPanel();
		panLeft.setVisible(false);
		panHelp.add(panLeft);
		panModes = new JPanel();
		panHelp.add(panModes);
		final JPanel panRight = new JPanel();
		panRight.setVisible(false);
		panHelp.add(panRight);
		
		btnImport = new JButton();
		btnImport.setText("Import");
		btnImport.setFont(new Font(myFont, Font.BOLD, 20));
		panModes.add(btnImport);
		
		btnExport = new JButton();
		btnExport.setText("Export");
		btnExport.setFont(new Font(myFont, Font.BOLD, 20));
		panModes.add(btnExport);
		
		btnDateiI = new JButton();
		btnDateiI.setText("Datei auswählen");
		panLeft.add(btnDateiI);
		
		btnSpeichI = new JButton();
		btnSpeichI.setText("Speichern");
		panLeft.add(btnSpeichI);
		
		btnDateiE = new JButton();
		btnDateiE.setText("in Datei exportieren");
		panRight.add(btnDateiE);
		
		panInh = new JPanel();
		panInh.setBounds(0,  panHelp.getHeight(), tabs.getWidth(), tabs.getHeight() - panHelp.getHeight());
		panInh.setBackground(Color.white);
		panInh.setLayout(null);
		panI_E.add(panInh);
		dfModelLeft = new DefaultListModel<>();
		listLeft = new JList<>(dfModelLeft);
		scrollImp = new JScrollPane(listLeft);
		scrollImp.setBounds(borderWidth, borderWidth , tabs.getWidth()/2 - 6*borderWidth, panInh.getHeight()- 5*borderWidth);
		panInh.add(scrollImp);
		
		btnToRight = new JButton("-->");
		btnToRight.setBounds(tabs.getWidth()/2 - 4*borderWidth, panInh.getHeight()/2 - (int)(1.5*borderWidth) - 2*20 , 8*borderWidth, 20);
		panInh.add(btnToRight);
		btnAllToRight = new JButton("==>");
		btnAllToRight.setBounds(tabs.getWidth()/2 - 4*borderWidth, panInh.getHeight()/2 - (int)(.5*borderWidth) - 20 , 8*borderWidth, 20);
		panInh.add(btnAllToRight);
		btnAllToLeft = new JButton("<==");
		btnAllToLeft.setBounds(tabs.getWidth()/2 - 4*borderWidth, panInh.getHeight()/2 + (int)(0.5*borderWidth) + 20 , 8*borderWidth, 20);
		panInh.add(btnAllToLeft);
		btnToLeft = new JButton("<--");
		btnToLeft.setBounds(tabs.getWidth()/2 - 4*borderWidth, panInh.getHeight()/2 + (int)(1.5*borderWidth) + 2*20 , 8*borderWidth, 20);
		panInh.add(btnToLeft);
		
		dfModelRight = new DefaultListModel<>();
		listRight = new JList<>(dfModelRight);
		scrollExp = new JScrollPane(listRight);
		scrollExp.setBounds(tabs.getWidth()/2 + 5*borderWidth, borderWidth, tabs.getWidth()/2 -(int)(6.5*borderWidth), panInh.getHeight()- 5*borderWidth);
		panInh.add(scrollExp);
		
		
		
		
		
		
		
		
		btnAllToLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for(int i = 0; i < dfModelRight.getSize(); i++){
					dfModelLeft.addElement(dfModelRight.getElementAt(i));
				}
				dfModelRight.removeAllElements();
			}
		});
		btnToLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] selected = listRight.getSelectedIndices();
				for(int i = 0; i < selected.length; i++){
					dfModelLeft.addElement(dfModelRight.getElementAt(selected[i]));
				}
				for(int i = selected.length-1; i >= 0; i--){
					dfModelRight.remove(selected[i]);
				}
			}
		});
		
		btnAllToRight.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for(int i = 0; i < dfModelLeft.getSize(); i++){
					dfModelRight.addElement(dfModelLeft.getElementAt(i));
				}
				dfModelLeft.removeAllElements();
			}
		});
		
		btnToRight.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] selected = listLeft.getSelectedIndices();
				for(int i = 0; i < selected.length; i++){
					dfModelRight.addElement(dfModelLeft.getElementAt(selected[i]));
				}
				for(int i = selected.length-1; i >= 0; i--){
					dfModelLeft.remove(selected[i]);
				}
			}
		});
		
		
		//doppelte rezepte aus der liste rausnehmen, rezept wird (rechts) angezeigt 
		btnImport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnImport.setBackground(Color.green);
				btnExport.setBackground(Color.gray);
				panLeft.setVisible(true);
				panRight.setVisible(false);
				pfad = "";
				dfModelLeft.removeAllElements();
				dfModelRight.removeAllElements();
				
				Rezeptliste liste = kochCtrl.getKuechenApp().getRezeptliste();
				for(int i = 0; i < liste.groesse() ; i++){
					Rezept neu = liste.getAt(i);
					dfModelRight.addElement(neu.getName());
				}
				btnSpeichI.setEnabled(false);
			}
		});
		
		btnExport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnExport.setBackground(Color.green);
				btnImport.setBackground(Color.gray);
				panLeft.setVisible(false);
				panRight.setVisible(true);
				dfModelLeft.removeAllElements();
				dfModelRight.removeAllElements();
				
				Rezeptliste liste = kochCtrl.getKuechenApp().getRezeptliste();
				for(int i = 0; i < liste.groesse() ; i++){
					Rezept neu = liste.getAt(i);
					dfModelLeft.addElement(neu.getName());
				}
					
					
					
			}
		});
					//**************************//
							//importieren
					//**************************//
		btnDateiI.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dfModelLeft.removeAllElements();
				dfModelRight.removeAllElements();
				JFileChooser filechooser = new JFileChooser();
				File xmlFile;
				FileNameExtensionFilter fnef = new FileNameExtensionFilter("Markup: xml", "xml");
				filechooser.setMultiSelectionEnabled(false);
				filechooser.setDialogTitle("Datei importieren");
				filechooser.setFileFilter(fnef);
				int result = filechooser.showOpenDialog(null);
				pfad = "";
				if(result == JFileChooser.APPROVE_OPTION){
					pfad = filechooser.getSelectedFile().toString();
					xmlFile = new File(pfad);
					if(fnef.accept(xmlFile)){
		                System.out.println(pfad + " kann geöffnet werden."); 
		                //TODO Read XML File, Get Rezepte
		                SAXBuilder builder = new SAXBuilder();
		                try{
		                	Document d = (Document) builder.build(xmlFile);
		                	Element rootNode = d.getRootElement();
		                	List list = rootNode.getChildren("rezept");
		                	for(int i = 0; i < list.size(); i++){
		                		Element rezept = (Element) list.get(i);
		                		dfModelLeft.addElement(rezept.getChildText("gericht"));
		                	}
		                	btnSpeichI.setEnabled(true);
		                }catch(IOException ioex){
		                	ioex.printStackTrace();
		                }catch(JDOMException jdomex){
		                	jdomex.printStackTrace();
		                }
					}
				}
				gHaupt.updateTree();
			}
		});
		
		
		//************************//
				//exportieren
		//***********************//
		btnDateiE.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser filechooser = new JFileChooser();
				File file;
				FileNameExtensionFilter fnef = new FileNameExtensionFilter("Markup: xml", "xml");
				filechooser.setFileFilter(fnef);
				filechooser.setDialogType(JFileChooser.SAVE_DIALOG);
				filechooser.setDialogTitle("Speichern unter...");

				int result = filechooser.showSaveDialog(null);
				if(result == JFileChooser.APPROVE_OPTION){
					pfad = filechooser.getSelectedFile().toString();
					file = new File(pfad);
					if(fnef.accept(file)){
		                System.out.println(pfad + " kann gespeichert werden."); 
					}else{
						pfad += ".xml";
					}
					ArrayList<String> auswahlListe = new ArrayList<>();
					for(int i = 0; i < dfModelRight.getSize(); i++){
						auswahlListe.add(dfModelRight.getElementAt(i).toString());
					}
					
					try {
						kochCtrl.getImportExportController().export(auswahlListe, pfad);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		btnSpeichI.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!pfad.equals("")){
					ArrayList<String> auswahlList = new ArrayList<>();
					for(int i = 0; i < dfModelRight.size(); i++){
						auswahlList.add(dfModelRight.get(i).toString());
					}
					try {
						kochCtrl.getImportExportController().importieren(auswahlList, pfad);
					} catch (NullPointerException | JDOMException | IOException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "FEHLER", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
					gHaupt.updateTree();
				}
				btnSpeichI.setEnabled(false);
			}
		});
	}
	
	public void update(JTabbedPane tabs){
		panHelp.setBounds(0, 0, tabs.getWidth(), 50);
		panInh.setBounds(0,  panHelp.getHeight(), tabs.getWidth(), tabs.getHeight() - panHelp.getHeight());
		scrollImp.setBounds(borderWidth, borderWidth , tabs.getWidth()/2 - 6*borderWidth, panInh.getHeight()- 5*borderWidth);
		scrollExp.setBounds(tabs.getWidth()/2 + 5*borderWidth, borderWidth, tabs.getWidth()/2 -(int)(6.5*borderWidth), panInh.getHeight()- 5*borderWidth);
		btnToRight.setBounds(tabs.getWidth()/2 - 4*borderWidth, panInh.getHeight()/2 - (int)(1.5*borderWidth) - 2*20 , 8*borderWidth, 20);
		btnAllToRight.setBounds(tabs.getWidth()/2 - 4*borderWidth, panInh.getHeight()/2 - (int)(.5*borderWidth) - 20 , 8*borderWidth, 20);
		btnAllToLeft.setBounds(tabs.getWidth()/2 - 4*borderWidth, panInh.getHeight()/2 + (int)(0.5*borderWidth) + 20 , 8*borderWidth, 20);
		btnToLeft.setBounds(tabs.getWidth()/2 - 4*borderWidth, panInh.getHeight()/2 + (int)(1.5*borderWidth) + 2*20 , 8*borderWidth, 20);
	}
}
