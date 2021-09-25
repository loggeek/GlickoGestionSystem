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


public class NewMatchupDialog extends JDialog
{
	static final long serialVersionUID = Main.version;

	// Launch the application.
	
	public static void main(String[] argv)
	{
		try
		{
			NewMatchupDialog dialog = new NewMatchupDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// Create the dialog.
	
	public NewMatchupDialog()
	{
		setTitle(Main.getLocalizedName("newMatchup").replaceAll("^\"|\"$", ""));
		
		try
		{
			setIconImage(ImageIO.read(new FileInputStream("icons/icon.png")));
		}
		catch (IOException e) {}
		
		setBounds(400, 200, 350, 150);
		
		getContentPane().setLayout(new BorderLayout());
		
	
		JPanel topPanel = new JPanel();
		getContentPane().add(topPanel, BorderLayout.NORTH);
	
		JLabel lblPlayer1 = new JLabel(Main.getLocalizedName("playerN")
				.replace("%", "1")
				.replaceAll("^\"|\"$", ""));
		topPanel.add(lblPlayer1);
		
		JComboBox<String> comboBoxPlayer1 = new JComboBox<>(Main.getPlayerNames());
		comboBoxPlayer1.setEditable(true);
		topPanel.add(comboBoxPlayer1);
		
		
		JPanel bottomPanel = new JPanel();
		getContentPane().add(bottomPanel, BorderLayout.CENTER);
		
		JLabel lblPlayer2 = new JLabel(Main.getLocalizedName("playerN")
				.replace("%", "2")
				.replaceAll("^\"|\"$", ""));
		bottomPanel.add(lblPlayer2);
		
		JComboBox<String> comboBoxPlayer2 = new JComboBox<>(Main.getPlayerNames());
		comboBoxPlayer2.setEditable(true);
		bottomPanel.add(comboBoxPlayer2);
		
	
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton winButton = new JButton(Main.getLocalizedName("win").replaceAll("^\"|\"$", ""));
		winButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String[] ret =
				{
					"newMatchup",
					comboBoxPlayer1.getSelectedItem() == null ? "" : comboBoxPlayer1.getSelectedItem().toString(),
					comboBoxPlayer2.getSelectedItem() == null ? "" : comboBoxPlayer2.getSelectedItem().toString(),
					"w"
				};
				Main.returnValue(ret);
				dispose();
			}
		});
		buttonPane.add(winButton);
		getRootPane().setDefaultButton(winButton);
		
		JButton drawButton = new JButton(Main.getLocalizedName("draw").replaceAll("^\"|\"$", ""));
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String[] ret =
				{
					"newMatchup",
					comboBoxPlayer1.getSelectedItem() == null ? "" : comboBoxPlayer1.getSelectedItem().toString(),
					comboBoxPlayer2.getSelectedItem() == null ? "" : comboBoxPlayer2.getSelectedItem().toString(),
					"d"
				};
				Main.returnValue(ret);
				dispose();
			}
		});
		buttonPane.add(drawButton);
		
		JButton lossButton = new JButton(Main.getLocalizedName("loss").replaceAll("^\"|\"$", ""));
		lossButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String[] ret =
				{
					"newMatchup",
					comboBoxPlayer1.getSelectedItem() == null ? "" : comboBoxPlayer1.getSelectedItem().toString(),
					comboBoxPlayer2.getSelectedItem() == null ? "" : comboBoxPlayer2.getSelectedItem().toString(),
					"l"
				};
				Main.returnValue(ret);
				dispose();
			}
		});
		buttonPane.add(lossButton);
		
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
