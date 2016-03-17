package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.awt.Color;

public class Search {

	private DefaultListModel listModel;
	private JFrame frmCheckin;
	private static JTextField First_Name;
	private static JTextField Last_Name;
	public static String Full_Name;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search window = new Search();
					window.frmCheckin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static String getFirst_Name()
	{
		return First_Name.getText();
	}
	
	public static String getLast_Name()
	{
		return Last_Name.getText();
	}

	/**
	 * Create the application.
	 */
	public Search() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCheckin = new JFrame();
		frmCheckin.setTitle("Check-In");
		frmCheckin.setBounds(100, 100, 406, 203);
		frmCheckin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCheckin.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Input patient's name to search:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 11, 414, 14);
		frmCheckin.getContentPane().add(lblNewLabel);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFirstName.setBounds(20, 36, 95, 14);
		frmCheckin.getContentPane().add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLastName.setBounds(20, 61, 95, 14);
		frmCheckin.getContentPane().add(lblLastName);
		
		final JLabel lblError = new JLabel("Patient's name was not found");
		lblError.setVisible(false);
		lblError.setForeground(Color.RED);
		lblError.setBounds(99, 87, 191, 16);
		frmCheckin.getContentPane().add(lblError);
		
		First_Name = new JTextField();
		First_Name.setBounds(108, 36, 137, 20);
		frmCheckin.getContentPane().add(First_Name);
		First_Name.setColumns(10);
		
		Last_Name = new JTextField();
		Last_Name.setColumns(10);
		Last_Name.setBounds(108, 61, 137, 20);
		frmCheckin.getContentPane().add(Last_Name);
		
		//Executed when search is clicked
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final ArrayList<Patient> patients = new ArrayList<Patient>();
				try{
					Class.forName("com.mysql.jdbc.Driver").newInstance();
				}
				catch(ClassNotFoundException | InstantiationException | IllegalAccessException e2)
				{
					System.out.println("Driver not found");
					e2.printStackTrace();
				}
				try{
					listModel = new DefaultListModel();
					Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
					Statement stmt = conn.createStatement();
					ResultSet rs;
					rs = stmt.executeQuery("SELECT ph.patientid,ph.firstname,ph.lastname,ap.appttime,ap.visitreason,st.statusdesc FROM medprogram.patientheader ph JOIN medprogram.appointments ap on ap.patientid = ph.patientid JOIN medprogram.status st on st.statusid = ap.checkinstatus "
							+ "WHERE ph.firstname like '%" + First_Name.getText() + "%' and ph.lastname like '%" + Last_Name.getText() + "%'");
					String test = null;
					int count = 0;
					if(rs.next()){
						do{
							int ID = rs.getInt("patientid");
							String first = rs.getString("firstname");
							String last = rs.getString("lastname");
							Date apt_time = rs.getDate("appttime");
							String visit_reason = rs.getString("visitreason");
							String status = rs.getString("statusdesc");
							if(status == "Not Checked In")
							{
								status = "";
							}
							String date_string = String.format("%15tc", apt_time);
							test = String.format("%1d %21s %1s %45s %20s %35s",ID,first,last,date_string,visit_reason,status);
							System.out.println(test);
							listModel.addElement(test);
							patients.add(new Patient(ID,first,last,apt_time,visit_reason,status));
						}while(rs.next());
						List.NewScreen(listModel, patients);
						frmCheckin.setVisible(false);
						conn.close();
					}
					else{
						lblError.setVisible(true);
					}
				}
				catch (Exception e1)
				{
					System.out.println("Connection Exception");
					System.err.println(e1.getMessage());
				}
			}
		});
		btnSearch.setBounds(10, 115, 106, 38);
		frmCheckin.getContentPane().add(btnSearch);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnClose.setBounds(274, 115, 106, 38);
		frmCheckin.getContentPane().add(btnClose);
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("test");
			}
		});
		btnAddUser.setBounds(144, 115, 106, 38);
		frmCheckin.getContentPane().add(btnAddUser);
		
	}
}
