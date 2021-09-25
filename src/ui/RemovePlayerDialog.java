package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import main.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JComboBox;


public class RemovePlayerDialog extends JDialog
{

	static final long serialVersionUID = Main.version;

	// Launch the application.
	
	public static void main(String[] argv)
	{
		try
		{
			RemovePlayerDialog dialog = new RemovePlayerDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// Create the dialog.
	
	public RemovePlayerDialog()
	{
		setTitle(Main.getLocalizedName("removePlayer").replaceAll("^\"|\"$", ""));
		
		try
		{
			setIconImage(ImageIO.read(new FileInputStream("icons/icon.png")));
		}
		catch (IOException e) {}
		
		setBounds(500, 200, 250, 125);
		
		getContentPane().setLayout(new BorderLayout());
		
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
	
		JLabel lblPlayerName = new JLabel(Main.getLocalizedName("playerName").replaceAll("^\"|\"$", ""));
		panel.add(lblPlayerName);
		
		JComboBox<String> comboBoxPlayerName = new JComboBox<>(Main.getPlayerNames());
		comboBoxPlayerName.setEditable(true);
		panel.add(comboBoxPlayerName);
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String[] ret =
				{
					"removePlayer",
					comboBoxPlayerName.getSelectedItem().toString()
				};
				Main.returnValue(ret);
				dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	
	
		JButton cancelButton = new JButton(Main.getLocalizedName("cancel").replaceAll("^\"|\"$", ""));
		cancelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}
}
