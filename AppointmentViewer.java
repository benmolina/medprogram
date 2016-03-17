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
	private String[] columnName = {"Appointment ID", "Patient First Name", "Patient Last Name","Appointment Time",
								  "Doctor", "Reason for visit", "Comments", "Check-In Status",
								  "Creator User"};
	
	//Two dimension array to hold the info each patient
	private static String[][] info = new String[20][9];
	
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
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	//Fill window with the table
    	View newContentPane = new View();
    	newContentPane.setOpaque(true);
    	frame.setContentPane(newContentPane);
    	
    	frame.pack();
    	frame.setVisible(true);
    	
    }

	public static void main(String[] args) {
		info[0][4] = "My stomach hurts really really bad";
		try{
			Connection myConn = DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
			
			Statement myStmt = myConn.createStatement();
			int n = 0;
			ResultSet myRes = myStmt.executeQuery("select appointments.apptid, patientheader.firstname, patientheader.lastname, appointments.appttime, A.lastname, appointments.visitreason, "
					+ "appointments.comments, status.statusdesc, B.username from appointments "
					+ "inner join patientheader on patientheader.patientid = appointments.patientid inner join user A on A.userid = appointments.doctor "
					+ "inner join status on status.statusid = appointments.checkinstatus inner join user B on B.userid = appointments.creatoruser;");
			
				while (myRes.next()){
					info[n][0] = myRes.getString("apptid");
					info[n][1] = myRes.getString("firstname");
					info[n][2] = myRes.getString("lastname");
					info[n][3] = myRes.getString("appttime");
					info[n][4] = myRes.getString("A.lastname");
					info[n][5] = myRes.getString("visitreason");
					info[n][6] = myRes.getString("comments");
					info[n][7] = myRes.getString("statusdesc");
					info[n][8] = myRes.getString("B.username");
					n++;
				}
			
		}

		catch (Exception exec){
			exec.printStackTrace();
		}
		createWindow();
	}

}
