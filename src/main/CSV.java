package main;

import java.io.*;
import java.util.ArrayList;


public class CSV
{
	public static ArrayList<String[]> readCSV (String path) throws IOException
	{
		BufferedReader csvReader = new BufferedReader(new FileReader(path));
		ArrayList<String[]> data = new ArrayList<>();
		String row;
		
		while ((row = csvReader.readLine()) != null) {
		    String[] dataRow = row.split(",");
		    data.add(dataRow);
		}
		csvReader.close();
		return data;
	}
	
	public static void writeCSV (ArrayList<String[]> data, String path) throws IOException
	{
		FileWriter csvWriter = new FileWriter(path);
		
		for (String[] row : data)
		{
		    csvWriter.append(String.join(",", row));
		    csvWriter.append("\n");
		}
		
		csvWriter.flush();
		csvWriter.close();
	}
}
