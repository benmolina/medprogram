package medProgram;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	public static void main() {
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
		frmCheckin.setBounds(100, 100, 320, 253);
		frmCheckin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCheckin.getContentPane().setLayout(null);
		frmCheckin.setLocationRelativeTo(null);
		
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
		lblError.setBounds(54, 87, 191, 16);
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
					rs = stmt.executeQuery("SELECT ap.apptid, ph.patientid,ph.firstname,ph.lastname,ap.appttime,ap.visitreason,st.statusdesc, CONCAT(u.firstname, ' ', u.lastname) as doctor FROM medprogram.patientheader ph JOIN medprogram.appointments ap on ap.patientid = ph.patientid JOIN medprogram.status st on st.statusid = ap.checkinstatus JOIN medprogram.user u on ap.doctor = u.userid "
							+ "WHERE ap.isdeleted = 0 and ph.firstname like '%" + First_Name.getText() + "%' and ph.lastname like '%" + Last_Name.getText() + "%'");
					String test = null;
					int count = 0;
					if(rs.next()){
						do{
							int apptId = rs.getInt("apptid");
							int ID = rs.getInt("patientid");
							String first = rs.getString("firstname");
							String last = rs.getString("lastname");
							Timestamp apt_time = rs.getTimestamp("appttime");
							String visit_reason = rs.getString("visitreason");
							String status = rs.getString("statusdesc");
							String doc = rs.getString("doctor");
//							if(status.equals("Not Checked In"))
//							{
//								status = "";
//							}
							//java.util.Date apptDateTime = new java.util.Date(apt_time.getTime());
							java.text.SimpleDateFormat sdf = 
									new java.text.SimpleDateFormat("MM-dd-yyyy HH:mm");
							
							String appt = sdf.format(apt_time);
							
							if(status.equals("Not Checked In"))
							{
								status = "";
								test = String.format("%1d %17s %1s %45s %19s %52s %25s",ID,first,last,appt.toString(),visit_reason,status, doc);
							}
							else
								test = String.format("%1d %17s %1s %45s %19s %44s %25s",ID,first,last,appt.toString(),visit_reason,status, doc);
							listModel.addElement(test);
							patients.add(new Patient(apptId, ID,first,last,apt_time,visit_reason,status, doc));
						}while(rs.next());
						List.NewScreen(listModel, patients);
						frmCheckin.dispose();
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
		btnSearch.setBounds(5, 115, 137, 38);
		frmCheckin.getContentPane().add(btnSearch);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCheckin.dispose();
			}
		});
		btnClose.setBounds(162, 163, 132, 38);
		frmCheckin.getContentPane().add(btnClose);
		
		JButton btnAddPatient = new JButton("Add Patient");
		btnAddPatient.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("test");
			}
		});
		btnAddPatient.setBounds(162, 114, 132, 38);
		frmCheckin.getContentPane().add(btnAddPatient);	
		
		JButton btnCreate = new JButton("Create Appointment");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null,"This feature has not been implemented yet.");
				CreateAppt ca = new CreateAppt();
				ca.main();
				frmCheckin.dispose();
				
			}
		});
		btnCreate.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		btnCreate.setBounds(5, 165, 137, 38);
		frmCheckin.getContentPane().add(btnCreate);
	}
}
