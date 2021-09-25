package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;

import main.Main;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.event.*;
import javax.swing.JScrollPane;

public class MainWindow
{
	private JFrame frame;
	private JPanel panel;
	private JButton btnNewPlayer;
	private JButton btnRemovePlayer;
	private JButton btnNewMatchup;
	private JButton btnUpdateRatings;
	private JButton btnLoadRatings;
	private JButton btnSaveRatings;
	private JButton btnSort;
	private JScrollPane scrollPane;
	private JTable ratingsTable;
	
	public static JLabel lblGamesPending;
	public static RatingsTableModel ratingsTableModel;

	// Launch the application.
	
	public static void exec()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainWindow window = new MainWindow();
					
					window.frame.setVisible(true);
					window.frame.setTitle(Main.getLocalizedName("title").replaceAll("^\"|\"$", ""));
					
					window.btnNewPlayer.setText(Main.getLocalizedName("newPlayer").replaceAll("^\"|\"$", ""));
					window.btnRemovePlayer.setText(Main.getLocalizedName("removePlayer").replaceAll("^\"|\"$", ""));
					window.btnNewMatchup.setText(Main.getLocalizedName("newMatchup").replaceAll("^\"|\"$", ""));
					window.btnUpdateRatings.setText(Main.getLocalizedName("updateRatings").replaceAll("^\"|\"$", ""));
					window.btnLoadRatings.setText(Main.getLocalizedName("loadRatings").replaceAll("^\"|\"$", ""));
					window.btnSaveRatings.setText(Main.getLocalizedName("saveRatings").replaceAll("^\"|\"$", ""));
					window.btnSort.setText(Main.getLocalizedName("sort").replaceAll("^\"|\"$", ""));
					
					lblGamesPending.setText(Main.getLocalizedName("pendingGames")
							.replace("%", "0")
							.replaceAll("^\"|\"$", ""));
					
					ratingsTableModel.refresh();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	// Create the application.
	
	public MainWindow()
	{
		initialize();
	}

	// Initialize the contents of the frame.
	
	private void initialize()
	{
		frame = new JFrame();
		
		try
		{
			frame.setIconImage(ImageIO.read(new FileInputStream("icons/icon.png")));
		}
		catch (IOException e) {}
		
		frame.setTitle("?");
		frame.setBounds(100, 100, 450, 300);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		btnNewPlayer = new JButton("?");
		btnNewPlayer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Main.newPlayer();
			}
		});
		panel.add(btnNewPlayer);
		
		btnRemovePlayer = new JButton("?");
		btnRemovePlayer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Main.removePlayer();
			}
		});
		panel.add(btnRemovePlayer);
		
		btnNewMatchup = new JButton("?");
		btnNewMatchup.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Main.newMatchup();
			}
		});
		panel.add(btnNewMatchup);
		
		btnUpdateRatings = new JButton("?");
		btnUpdateRatings.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Main.updateRatings();
			}
		});
		panel.add(btnUpdateRatings);
		
		btnLoadRatings = new JButton("?");
		btnLoadRatings.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Main.loadRatings();
			}
		});
		panel.add(btnLoadRatings);
		
		btnSaveRatings = new JButton("?");
		btnSaveRatings.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Main.saveRatings();
			}
		});
		panel.add(btnSaveRatings);
		
		btnSort = new JButton("?");
		btnSort.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Main.sort();
			}
		});
		panel.add(btnSort);
		
		
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				boolean result = Main.optionDialog(Main.getLocalizedName("aysToExit").replaceAll("^\"|\"$", ""), false);
				
				if (result) frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				else frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		});
		
		
		lblGamesPending = new JLabel("?");
		frame.add(lblGamesPending, BorderLayout.SOUTH);
		
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		ratingsTableModel = new RatingsTableModel();
		ratingsTable = new JTable(ratingsTableModel);
		scrollPane.setViewportView(ratingsTable);
	}
}
