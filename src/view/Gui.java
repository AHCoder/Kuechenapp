package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import control.KochController;


@SuppressWarnings("serial")
public class Gui extends JFrame {

	private JPanel contentPane;
	
	private static KochController kochCtrl;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					kochCtrl = new KochController();
					try{
						kochCtrl.getIOController().laden();
					}catch(Exception e){
						e.printStackTrace();
					}
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					Gui frame = new Gui("KüchenApp");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui(String title) {
		setTitle(title);
		
		// ----- Content Pane -----//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		contentPane.setLayout(null);
		contentPane.setBounds(100, 100, 1600, 900);
		setBounds(100, 100, 1600, 900);
		setContentPane(contentPane);
		setMinimumSize(new Dimension(1200,500));
		
		final GHauptfenster gHauptfenster = new GHauptfenster(contentPane, kochCtrl);
		final GBestand gBestand = new GBestand(gHauptfenster.getTabs(), kochCtrl, gHauptfenster);
		final GEinkaufliste gEinkaufsliste = new GEinkaufliste(gHauptfenster.getTabs(), kochCtrl, gBestand);
		final GImportExport gImportExport = new GImportExport(gHauptfenster.getTabs(), kochCtrl, gHauptfenster);
		gHauptfenster.updateModel(gBestand, gEinkaufsliste);
		gEinkaufsliste.updateModel();
		gBestand.updateModel();
		gHauptfenster.updateTree();
		

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				gHauptfenster.update(contentPane.getWidth(), contentPane.getHeight());
				gEinkaufsliste.update(gHauptfenster.getTabs());
				gBestand.update(gHauptfenster.getTabs());
				gImportExport.update(gHauptfenster.getTabs());
			}
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				kochCtrl.getIOController().speichern();
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				kochCtrl.getIOController().speichern();
			}
		});
		
	}
}
