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

public class AppointmentViewer extends AbstractTableModel {
	
	//Titles for the appointment viewer
	private String[] columnName = {"Patient First Name", "Patient Last Name","Appointment Time",
								  "Doctor", "Reason for visit", "Comments", "Check-In Status",
								  "Creator User"};
	
	//Two dimension array to hold the info each patient
	private static String[][] info = new String[20][8];
	
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
    
    //Function called to create the viewer
    private static void createWindow(){
    	//Set the date of the appointments
    	Calendar cal = Calendar.getInstance();
    	int dateMonth = cal.get(Calendar.MONTH) + 1;
    	int dateDay = cal.get(Calendar.DAY_OF_MONTH); 
    	String dateStr = String.valueOf(dateMonth) + "/" + String.valueOf(dateDay);
    	String title = "Appointments " + dateStr;
    	
    	//Setup frame of window
    	JFrame frame = new JFrame(title);
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    	//Fill window with the table
    	View newContentPane = new View();
    	newContentPane.setOpaque(true);
    	frame.setContentPane(newContentPane);
    	
    	frame.setSize(1100,400);
    	frame.setVisible(true);
    	frame.setLocationRelativeTo(null);
    	
    }

	public static void main() {
		try{
			Connection myConn = DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
			
			Statement myStmt = myConn.createStatement();
			int n = 0;
			ResultSet myRes = myStmt.executeQuery("select patientheader.firstname, patientheader.lastname, appointments.appttime, A.lastname, appointments.visitreason, "
					+ "appointments.comments, status.statusdesc, B.username from appointments "
					+ "inner join patientheader on patientheader.patientid = appointments.patientid inner join user A on A.userid = appointments.doctor "
					+ "inner join status on status.statusid = appointments.checkinstatus inner join user B on B.userid = appointments.creatoruser " 
					+ "where appointments.isdeleted = 0;");
			
				while (myRes.next()){
					
					Timestamp tempTime = myRes.getTimestamp("appttime");
					
					java.text.SimpleDateFormat sdf = 
							new java.text.SimpleDateFormat("MM-dd-yyyy HH:mm");
					
					String simpleTime = sdf.format(tempTime);
					
					
					
					
					info[n][0] = myRes.getString("firstname");
					info[n][1] = myRes.getString("lastname");
					info[n][2] = simpleTime;
					info[n][3] = myRes.getString("A.lastname");
					info[n][4] = myRes.getString("visitreason");
					info[n][5] = myRes.getString("comments");
					
					if (myRes.getString("statusdesc").equals("Not Checked In"))
						info[n][6] = "";
					else
						info[n][6] = myRes.getString("statusdesc");
					info[n][7] = myRes.getString("B.username");
					n++;
				}
			
		}

		catch (Exception exec){
			exec.printStackTrace();
		}
		createWindow();
	}

}
