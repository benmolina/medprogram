package medProgram;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.sql.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import java.awt.Color;


public class MedRecords {

	private JFrame frmMedicalRecord;
	private static JTextField txtFirstName;
	private static JTextField txtLastName;
	private static Patient patient;
	private DefaultListModel patList;
	private DefaultListModel appointment;
	private ArrayList<String> Appointments = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main() {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MedRecords window = new MedRecords();
					window.frmMedicalRecord.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MedRecords() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMedicalRecord = new JFrame();
		frmMedicalRecord.setTitle("Medical Record");
		frmMedicalRecord.setBounds(100, 100, 379, 176);
		frmMedicalRecord.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmMedicalRecord.getContentPane().setLayout(null);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(44, 65, 86, 20);
		frmMedicalRecord.getContentPane().add(txtFirstName);
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setBounds(162, 65, 86, 20);
		frmMedicalRecord.getContentPane().add(txtLastName);
		txtLastName.setColumns(10);
		
		final JLabel lblNameNotFound = new JLabel("Name not found. Please input a different name");
		lblNameNotFound.setForeground(Color.RED);
		lblNameNotFound.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNameNotFound.setBounds(44, 97, 305, 14);
		lblNameNotFound.setVisible(false);
		frmMedicalRecord.getContentPane().add(lblNameNotFound);
		
		final JLabel lblPleaseFillOut = new JLabel("Please fill out both fields wtih valid names");
		lblPleaseFillOut.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPleaseFillOut.setForeground(Color.RED);
		lblPleaseFillOut.setBounds(44, 113, 267, 16);
		lblPleaseFillOut.setVisible(false);
		frmMedicalRecord.getContentPane().add(lblPleaseFillOut);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtFirstName.getText().trim().equals("") || txtLastName.getText().trim().equals("")){
					lblPleaseFillOut.setVisible(true);
				}
				else{
					lblPleaseFillOut.setVisible(false);
					lblNameNotFound.setVisible(false);
				try {
					final ArrayList<String> patients = new ArrayList<String>();
					appointment = new DefaultListModel();
					Connection myConn = DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
					Statement iq = myConn.createStatement();
					String query = "SELECT ph.patientid, ph.firstname, ph.lastname, ph.dateofbirth,ph.street,ph.aptnumber,ph.city,ph.state,ph.zipcode,ph.phonenumber,pd.importantnotes,pd.insuranceprovider,"
	+ "pd.insuranceinfo,su.supplydesc FROM medprogram.patientheader ph "
    + "RIGHT JOIN medprogram.patientdetails pd on pd.patientid = ph.patientid RIGHT JOIN medprogram.allergylist al on al.patientid = ph.patientid JOIN medprogram.supplies su on su.supplyid = al.supplyid WHERE ph.firstname like ? and ph.lastname like ?";
					PreparedStatement pstmt = myConn.prepareStatement(query);
					pstmt.setString(1,'%' + txtFirstName.getText() + '%');
					pstmt.setString(2,'%' + txtLastName.getText() + '%');
					ResultSet patientInfo = pstmt.executeQuery();
					int ID = 0;
					if (patientInfo.next())
					{
						lblNameNotFound.setVisible(false);
						do{
							ID = patientInfo.getInt("patientid");
							String first = patientInfo.getString("firstname");
							String last = patientInfo.getString("lastname");
							Timestamp birth = patientInfo.getTimestamp("dateofbirth");
							String street = patientInfo.getString("street");
							int aptnumber = patientInfo.getInt("aptnumber");
							String state = patientInfo.getString("state");
							int zipcode = patientInfo.getInt("zipcode");
							String phone = patientInfo.getString("phonenumber");
							String notes = patientInfo.getString("importantnotes");
							String insurance = patientInfo.getString("insuranceprovider");
							String insuranceinfo = patientInfo.getString("insuranceinfo");
							String allergy = patientInfo.getString("supplydesc");
							String fullName = first + last;
							patient = new Patient(ID,first,last,birth,street,aptnumber,state,zipcode,phone,notes,insurance,insuranceinfo,allergy);
							patients.add(fullName);
						}while(patientInfo.next());
						query = "SELECT ph.patientid, ap.appttime, ap.visitreason FROM medprogram.patientheader ph RIGHT JOIN medprogram.appointments ap on ap.patientid = ph.patientid JOIN medprogram.status st on st.statusid = ap.checkinstatus" +
								" WHERE ph.patientid = " + ID;
						ResultSet appointments = iq.executeQuery(query);
						java.text.SimpleDateFormat sdf = 
								new java.text.SimpleDateFormat("MM-dd-yyyy HH:mm");
						while(appointments.next()){
							ID = appointments.getInt("patientid");
							Timestamp appttime = appointments.getTimestamp("appttime");
							String time = sdf.format(appttime);
							String reason = appointments.getString("visitreason");
							Appointments.add(String.format("%1s %17s",time,reason));
						}
						Records.newScreen(patient, Appointments);
						
					}
					else
					{
						lblNameNotFound.setVisible(true);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			}
		});
		btnSearch.setBounds(260, 65, 89, 23);
		frmMedicalRecord.getContentPane().add(btnSearch);
		
		JLabel lblNewLabel = new JLabel("First:");
		lblNewLabel.setBounds(10, 68, 46, 14);
		frmMedicalRecord.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Last:");
		lblNewLabel_1.setBounds(130, 68, 46, 14);
		frmMedicalRecord.getContentPane().add(lblNewLabel_1);
		
		JLabel lblPlease = new JLabel("Please input a name to bring up their medical record.");
		lblPlease.setBounds(6, 6, 529, 47);
		frmMedicalRecord.getContentPane().add(lblPlease);
		
		
		
	}
}