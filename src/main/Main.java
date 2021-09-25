package main;

import java.awt.Image;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.*;

import ui.*;


public class Main
{
	public static final long version = 0;
	
	static RatingCalculator env = new RatingCalculator(0.06, 0.5);
	static RatingPeriodResults results = new RatingPeriodResults();
	static HashMap<String, Rating> players = new HashMap<String, Rating>();
	static MainWindow mainWindow = new MainWindow();
	static ArrayList<String[]> data = new ArrayList<>();
	static HashMap<String, String> localizedNames = new HashMap<>();
	
	static char[] sortOrder = "0>".toCharArray();
	static long gamesPending = 0;
	
	public static void main(String[] argv)
	{
		initLocale();
		refreshData();
		MainWindow.exec();
	}
	
	public static void refreshData()
	{
		data.clear();
		
		for (Rating value : players.values())
			data.add(value.toString().split(" / "));
		
		Collections.sort(data, new Comparator<String[]>()
		{
            public int compare(String[] first, String[] second)
            {
            	int index = Character.getNumericValue(sortOrder[0]);
            	
            	if (sortOrder[1] == '<')
            		return second[index].compareTo(first[index]);
            	else
            		return first[index].compareTo(second[index]);
            }
        });
		
		MainWindow.ratingsTableModel.refresh();
		MainWindow.ratingsTableModel.fireTableDataChanged();
		
		MainWindow.lblGamesPending.setText(getLocalizedName("pendingGames")
				.replace("%", String.valueOf(getPendingGames()))
				.replaceAll("^\"|\"$", "")); // Remove trailing ""
	}
	
	public static void newPlayer()
	{
		NewPlayerDialog newPlayerDialog = new NewPlayerDialog();
		newPlayerDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		newPlayerDialog.setVisible(true);
	}
	
	public static void removePlayer()
	{
		RemovePlayerDialog removePlayerDialog = new RemovePlayerDialog();
		removePlayerDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		removePlayerDialog.setVisible(true);
	}
	
	public static void newMatchup()
	{
		NewMatchupDialog newMatchupDialog = new NewMatchupDialog();
		newMatchupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		newMatchupDialog.setVisible(true);
	}
	
	public static void updateRatings()
	{
		if (gamesPending == 0)
		{
			infoDialog (getLocalizedName("noGameToUpdate").replaceAll("^\"|\"$", ""), false);
		}
		else if (optionDialog (getLocalizedName("aysToUpdate")
						.replace("%", String.valueOf(getPendingGames()))
						.replaceAll("^\"|\"$", ""),
						false))
		{
			env.updateRatings(results);
			gamesPending = 0;
			refreshData();
		}
	}
	
	public static void saveRatings()
	{
		JFileChooser saveDialog = new JFileChooser();
		saveDialog.setDialogTitle(getLocalizedName("saveRatings").replaceAll("^\"|\"$", ""));
		
		// loadDialog.setFileFilter(new FileNameExtensionFilter ("CSV Spreadsheets (*.csv)", "csv"));
		
		try
		{
			int selection = saveDialog.showSaveDialog(new JFrame());
			 
			if (selection == JFileChooser.APPROVE_OPTION)
			{
			    File fileToSave = saveDialog.getSelectedFile();
			    CSV.writeCSV(data, fileToSave.getAbsolutePath());
			}
		}
		catch (IOException e) {} // Don't save the file
	}
	
	public static void loadRatings()
	{
		JFileChooser loadDialog = new JFileChooser();
		loadDialog.setDialogTitle(getLocalizedName("loadRatings").replaceAll("^\"|\"$", ""));
		
		// loadDialog.setFileFilter(new FileNameExtensionFilter ("CSV Spreadsheets (*.csv)", "csv"));
	    
		try
		{
			int selection = loadDialog.showOpenDialog(new JFrame());
			
			if (selection == JFileChooser.APPROVE_OPTION)
			if (optionDialog(getLocalizedName("aysToLoad").replaceAll("^\"|\"$", ""), false))
			{
			    File fileToLoad = loadDialog.getSelectedFile();
			    ArrayList<String[]> newData = CSV.readCSV(fileToLoad.getAbsolutePath());
			    data = new ArrayList<>(newData);
			    players.clear();
			    
			    for (String[] player : data)
			    {
			    	players.put(player[0], new Rating(player[0], env));
			    	players.get(player[0]).setRating(Double.parseDouble(player[1]));
			    	players.get(player[0]).setRatingDeviation(Double.parseDouble(player[2]));
			    	players.get(player[0]).setVolatility(Double.parseDouble(player[3]));
			    	players.get(player[0]).setNumberOfResults(Integer.parseInt(player[4]));
			    }
			}
		}
		catch (IOException | NullPointerException | NumberFormatException e) {} // Don't load the file
		refreshData();
	}
	
	public static void sort()
	{
		SortDialog sortDialog = new SortDialog();
		sortDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		sortDialog.setVisible(true);
	}
	
	public static String[][] getData()
	{
		final String[][] defaultData = {{"", "", "", "", ""}};
		
		if (data == null) return defaultData;
		else return data.toArray(new String[][] {});
	}
	
	public static String[] getPlayerNames()
	{
		return players.keySet().toArray(new String[] {});
	}
	
	public static long getPendingGames()
	{
		return gamesPending;
	}
	
	public static void infoDialog (String message, boolean isError)
	{
		JOptionPane.showMessageDialog
		(
			new JFrame(),
		    message,
		    isError
		    	? getLocalizedName("error").replaceAll("^\"|\"$", "")
		    	: getLocalizedName("info"). replaceAll("^\"|\"$", ""),
		    isError ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE,
		    isError ? createImageIcon("icons/error.png") : createImageIcon("icons/info.png")
		);
	}
	
	public static boolean optionDialog (String message, boolean isWarning)
	{
		Object[] options =
		{
			getLocalizedName("yes").replaceAll("^\"|\"$", ""),
			getLocalizedName("no") .replaceAll("^\"|\"$", "")
		};
		
		int ret = JOptionPane.showOptionDialog
		(
			new JFrame(),
		    message,
		    isWarning
		    	? getLocalizedName("warning").replaceAll("^\"|\"$", "")
			    : getLocalizedName("question").replaceAll("^\"|\"$", ""),
		    JOptionPane.YES_NO_OPTION,
		    isWarning ? JOptionPane.WARNING_MESSAGE : JOptionPane.QUESTION_MESSAGE,
		    isWarning ? createImageIcon("icons/warning.png") : createImageIcon("icons/question.png"),
		    options,
		    options[0]
		);
		return ret == JOptionPane.YES_OPTION ? true : false;
	}
	
	public static void returnValue (String[] valueArray)
	{
		switch (valueArray[0])
		{
		case "newPlayer":
			try
			{
				valueArray[1] = valueArray[1]
						.replace(",", "-")
						.replace("\"", "-")
						.replace("'", "-");
				
				if (valueArray[1].equals(""))
				{
					infoDialog (getLocalizedName("inputPlayerName").replaceAll("^\"|\"$", ""), true);
				}
				else
				{
					players.put(valueArray[1], new Rating(valueArray[1], env));
					players.get(valueArray[1]).setRating(Double.parseDouble(valueArray[2]));
				}
			}
			catch (NumberFormatException e)
			{
				infoDialog (getLocalizedName("invalidRating").replaceAll("^\"|\"$", ""), true);
			}
			break;
		
		case "removePlayer":
			if (!players.containsKey(valueArray[1]))
			{
				infoDialog (getLocalizedName("inexistentPlayer").replaceAll("^\"|\"$", ""), true);
			}
			else if (optionDialog (getLocalizedName("aysToRemove")
					.replace("%", players.get(valueArray[1]).toString().split(" / ")[0])
					.replaceAll("^\"|\"$", "")
					, true))
			{
				players.remove(valueArray[1]);
			}
			break;
		
		case "newMatchup":
			if (!(players.containsKey(valueArray[1]) || players.containsKey(valueArray[2])))
			{
				infoDialog (getLocalizedName("inexistentOpponent").replaceAll("^\"|\"$", ""), true);
			}
			else try
			{
				switch (valueArray[3])
				{
					case "w": results.addResult(players.get(valueArray[1]), players.get(valueArray[2])); break;
					case "d": results.addDraw(players.get(valueArray[1]), players.get(valueArray[2])); break;
					case "l": results.addResult(players.get(valueArray[2]), players.get(valueArray[1]));
				}
				gamesPending++;
				
				infoDialog (getLocalizedName("pendingGames")
						.replace("%", String.valueOf(getPendingGames()))
						.replaceAll("^\"|\"$", "")
						, false);
			}
			catch (IllegalArgumentException e)
			{
				infoDialog (getLocalizedName("autoGame").replaceAll("^\"|\"$", ""), true);
			}
			break;
		
		case "sort":
			char[] order = sortOrder;
			
			if (valueArray[1].equals("Descending")) order[1] = '<';
			else order[1] = '>';
			
			switch (valueArray[2])
			{
				case "Name": order[0] = '0'; break;
				case "Rating": order[0] = '1'; break;
				case "Deviation": order[0] = '2'; break;
				case "Volatility": order[0] = '3'; break;
				case "Games": order[0] = '4';
			}
			sortOrder = order;
		}
		refreshData();
	}
	
	public static ImageIcon createImageIcon(String path)
	{
		try
		{
			return new ImageIcon(ImageIO.read(new FileInputStream(path))
					.getScaledInstance(48, 48, Image.SCALE_SMOOTH));
		}
		catch (IOException e)
		{
			return null;
		}
	}
	
	public static String getLocalizedName (String entry)
	{
		return localizedNames.get(entry);
	}
	
	public static void initLocale ()
	{
		String lang = Locale.getDefault().getLanguage();
		Properties prop = new Properties();
		FileInputStream fis;
		
		try
		{
			if (new File("locale/" + lang + ".lang").isFile())
			{
				fis = new FileInputStream("locale/" + lang + ".lang");
			}
			else
			{
				fis = new FileInputStream("locale/en.lang");
			}
			
			prop.load(fis);
			
			for (String name: prop.stringPropertyNames())
			{
				localizedNames.put(name, prop.getProperty(name));
			}
			
			fis.close();
		}
		catch (FileNotFoundException e)
		{
			infoDialog ("FileNotFoundException: \n" + e.getLocalizedMessage(), true);
			System.exit(-1);
		}
		catch (IOException e)
		{
			infoDialog ("IOException: \n" + e.getLocalizedMessage(), true);
			System.exit(-1);
		}
	}
}
