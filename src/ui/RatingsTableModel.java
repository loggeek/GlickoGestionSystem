package ui;

import main.Main;

import javax.swing.table.AbstractTableModel;


public class RatingsTableModel extends AbstractTableModel
{
	static final long serialVersionUID = 0;
	
	String[][] data = {{}};
	String[] header = {"?", "?", "?", "?", "?"};
	
	public RatingsTableModel()
	{
		super();
		
		data = Main.getData();
	}
	
	public int getRowCount()
	{
        return data.length;
    }
 
    public int getColumnCount()
    {
        return header.length;
    }
 
    public String getColumnName(int columnIndex)
    {
        return header[columnIndex];
    }
 
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return data[rowIndex][columnIndex];
    }
    
    public void refresh()
    {
    	data = Main.getData();
    	
    	header = new String[]
		{
			Main.getLocalizedName("name").replaceAll("^\"|\"$", ""),
			Main.getLocalizedName("rating").replaceAll("^\"|\"$", ""),
			Main.getLocalizedName("deviation").replaceAll("^\"|\"$", ""),
			Main.getLocalizedName("volatility").replaceAll("^\"|\"$", ""),
			Main.getLocalizedName("games").replaceAll("^\"|\"$", "")
		};
    	this.fireTableStructureChanged();
    }
}
