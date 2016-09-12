package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class GKategorieDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final JPanel katPanel;

	/**
	 * Create the dialog.
	 */
	public GKategorieDialog(final GRezeptErstellen f) {
		super(f);
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblKategorienAuswhlen = new JLabel("Kategorien auswählen:");
			lblKategorienAuswhlen.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblKategorienAuswhlen.setBounds(10, 11, 145, 19);
			contentPanel.add(lblKategorienAuswhlen);
		}
		{

			katPanel = new JPanel();
			katPanel.setBounds(10, 41, 414, 176);
			contentPanel.add(katPanel);
			katPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			for (String kat : f.getKategorien()) {
				katPanel.add(new JCheckBox(kat));
			}

			// Prüfen ob schon einmal Kategorien Ausgewählt worden sind, wenn ja diese selektieren
			if ((f.getSelectedKat() != null)) {
				for (String skat : f.getSelectedKat()) {
					Component[] katComponents = katPanel.getComponents();
					for (Component comp : katComponents) {
						if (comp instanceof JCheckBox) {
							if (((JCheckBox) comp).getText().equals(skat)) {
								((JCheckBox) comp).setSelected(true);
							}
						}
					}
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ArrayList<String> selectedKat = new ArrayList<String>();
						Component[] katComponents = katPanel.getComponents();
						for (Component comp : katComponents) {
							if (comp instanceof JCheckBox) {
								if (((JCheckBox) comp).isSelected()) {
									selectedKat.add(((JCheckBox) comp)
											.getText());
								}
							}
						}
						String kategorien = "";
						for (String kat : selectedKat) {
							kategorien = kategorien + kat + "; ";
						}
						f.setSelectedKat(selectedKat);
						f.getLabelKat().setText(kategorien);
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}