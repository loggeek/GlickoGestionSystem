package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import main.Main;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;


public class SortDialog extends JDialog
{
	static final long serialVersionUID = Main.version;
	static final String[] orders =
	{
		Main.getLocalizedName("ascending").replaceAll("^\"|\"$", ""),
		Main.getLocalizedName("descending").replaceAll("^\"|\"$", "")
	};
	static final String[] arguments =
	{
		Main.getLocalizedName("name").replaceAll("^\"|\"$", ""),
		Main.getLocalizedName("rating").replaceAll("^\"|\"$", ""),
		Main.getLocalizedName("deviation").replaceAll("^\"|\"$", ""),
		Main.getLocalizedName("volatility").replaceAll("^\"|\"$", ""),
		Main.getLocalizedName("games").replaceAll("^\"|\"$", "")
	};
	
	// Launch the application.
	
	public static void main(String[] argv)
	{
		try
		{
			SortDialog dialog = new SortDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// Create the dialog.
	
	public SortDialog()
	{
		setTitle(Main.getLocalizedName("sort").replaceAll("^\"|\"$", ""));
		
		try
		{
			setIconImage(ImageIO.read(new FileInputStream("icons/icon.png")));
		}
		catch (IOException e) {}
		
		setBounds(500, 200, 250, 150);
		
		getContentPane().setLayout(new BorderLayout());
		
		
		JPanel topPanel = new JPanel();
		getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JLabel lblOrder = new JLabel(Main.getLocalizedName("order").replaceAll("^\"|\"$", ""));
		topPanel.add(lblOrder);
	
		JComboBox<String> comboBoxOrder = new JComboBox<>(orders);
		topPanel.add(comboBoxOrder);
		
		
		JPanel bottomPanel = new JPanel();
		getContentPane().add(bottomPanel, BorderLayout.CENTER);
		
		JLabel lblType = new JLabel(Main.getLocalizedName("sortBy").replaceAll("^\"|\"$", ""));
		bottomPanel.add(lblType);
	
		JComboBox<String> comboBoxType = new JComboBox<>(arguments);
		bottomPanel.add(comboBoxType);
				
		
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
					"sort",
					comboBoxOrder.getSelectedItem().toString(),
					comboBoxType.getSelectedItem().toString()
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
