package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import main.Main;

import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JTextField;


public class NewPlayerDialog extends JDialog
{
	static final long serialVersionUID = Main.version;

	// Launch the application.
	
	public static void main(String[] argv)
	{
		try
		{
			NewPlayerDialog dialog = new NewPlayerDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// Create the dialog.
	
	public NewPlayerDialog()
	{
		setTitle(Main.getLocalizedName("newPlayer").replaceAll("^\"|\"$", ""));
		
		try
		{
			setIconImage(ImageIO.read(new FileInputStream("icons/icon.png")));
		}
		catch (IOException e) {}
		
		setBounds(500, 200, 250, 150);
		
		getContentPane().setLayout(new BorderLayout());
		
		
		JPanel topPanel = new JPanel();
		getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JLabel lblPlayerName = new JLabel(Main.getLocalizedName("playerName").replaceAll("^\"|\"$", ""));
		topPanel.add(lblPlayerName);
		
		JTextField textFieldPlayerName = new JTextField();
		topPanel.add(textFieldPlayerName);
		textFieldPlayerName.setColumns(10);
		
		
		JPanel bottomPanel = new JPanel();
		getContentPane().add(bottomPanel, BorderLayout.CENTER);
		
		JLabel lblPlayerRating = new JLabel(Main.getLocalizedName("playerRating").replaceAll("^\"|\"$", ""));
		bottomPanel.add(lblPlayerRating);
		
		JTextField textFieldPlayerRating = new JTextField();
		textFieldPlayerRating.setText("1500");
		bottomPanel.add(textFieldPlayerRating);
		textFieldPlayerRating.setColumns(10);
		
		
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
					"newPlayer",
					textFieldPlayerName.getText(),
					textFieldPlayerRating.getText()
				};
				Main.returnValue(ret);
				dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		cancelButton.setActionCommand(Main.getLocalizedName("cancel").replaceAll("^\"|\"$", ""));
		buttonPane.add(cancelButton);
	}
}
