package medProgram;
import javax.swing.table.AbstractTableModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Calendar;

import java.sql.*;

public class SuppliesViewer extends AbstractTableModel{
			
	//Array to hold names of columns
	private String[] columnName = {"Supply Description", "Supply Type", "Controlled Substance", "Price", "Available Quantity",
			  "Reorder Quantity"};

	//Two dimension array to hold the info each supply
        private static Object[][] info = new String[100][6];
	
	public int getColumnCount() {
		return columnName.length;
	}
	
    public int getRowCount(){
    	return info.length;
    }
    
    public String getColumnName(int col){
    	return columnName[col];
    }
    
    public Object getValueAt(int row, int col){
    	return info[row][col];
    }
   
    public boolean isCellEditable(int row,int col) {
    	if (col > 2)
    		return true;
    	else
    		return false;
    }
    
    public void setValueAt(Object value, int row, int col) {  
    	info[row][col] = value;
        System.out.println("Setting value");
        fireTableCellUpdated(row, col);
        
        String Data = (String) info[row][col];
        String ColumnName = null;
        if (col == 4){
        	ColumnName = "qtyOnHand";
        }
        else if (col == 5){
        	ColumnName = "reorderqty";
        }
        else if (col == 3){
        	ColumnName = "supplyprice";
        }
        
        String Identifier = (String) info[row][0];
        
        String query = String.format("update supplies set %s=%s where supplyDesc='%s'", ColumnName, Data, Identifier);
        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
            PreparedStatement myStmt = myConn.prepareStatement(query);
            myStmt.executeUpdate();
            
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
  //Function called to create the viewer
    private static void createWindow(){
    	//Set the date of the appointments
    	Calendar cal = Calendar.getInstance();
    	int dateMonth = cal.get(Calendar.MONTH) + 1;
    	int dateDay = cal.get(Calendar.DAY_OF_MONTH); 
    	String dateStr = String.valueOf(dateMonth) + "/" + String.valueOf(dateDay);
    	String title = "Supplies " + dateStr;
    	
    	//Setup frame of window
    	JFrame frame = new JFrame(title);
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    	//Fill window with the table
    	ViewSupply newContentPane = new ViewSupply();
    	newContentPane.setOpaque(true);
    	frame.setContentPane(newContentPane);
    	
    	frame.pack();
    	frame.setVisible(true);
    	
    }

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main() {
		
		try{
			
			int n = 0;
			int type = 0;
			int ctrlSub = 0;
			int checkId;
			
			
			Connection myConn = DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
			
			Statement myStmt = myConn.createStatement();
			
			ResultSet myRes = myStmt.executeQuery("select * from supplies;");
			
			while (myRes.next()){
				checkId = Integer.parseInt(myRes.getString("supplyid"));
				if (checkId != 0){
				info[n][0] = myRes.getString("supplydesc");
				
				//Check type of supply
				type = Integer.parseInt(myRes.getString("supplytype"));
					if (type == 0){
						info[n][1] = "In-House Supply";
					}
					else if (type == 1){
						info[n][1] = "Services";
					}
					else if (type == 2){
						info[n][1] = "Medicine";
					}
					else if (type == 3){
						info[n][1] = "Vaccine";
					}
					else if (type == 4){
						info[n][1] = "Lab Work";
					}
					else if (type == 5){
						info[n][1] = "Medical Supply";
					}
					else if (type == 6){
						info[n][1] = "Over-Counter Item";
					}
					else if (type == 7){
						info[n][1] = "Miscellaneous";
					}
					else
						info[n][1] = "Other";
			
				info[n][2] = myRes.getString("isctrlsub");
				
				//Check controlled substance
				ctrlSub = Integer.parseInt((String) info[n][2]);
					if (ctrlSub == 0){
						info[n][2] = "No";
					}
					else
						info[n][2] = "Yes";
				
				info[n][3] = myRes.getString("supplyprice");
				info[n][4] = myRes.getString("qtyonhand");
				info[n][5] = myRes.getString("reorderqty");

				n++;
				}
				
				
			}
						
		}

		catch (Exception exec){
			exec.printStackTrace();
		}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		        createWindow();
		      }
		    });
		
		
	}

}