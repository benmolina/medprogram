package medProgram;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Panel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class CreateAppt extends JFrame {

	private JPanel contentPane;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JLabel lblDOB;
	private JLabel lblAppointmentDate;
	private JButton btnCreate;
	private JButton btnCancel;
	private JLabel lblCreatedBy;
	private JLabel lblDoctor;
	private JLabel lblReasonOfVisit;
	private JTextField txtReason;
	private JLabel lblComments;
	private JTextField txtComments;
	private Panel pnlApptDateTime;
	private Panel pnlDoctorUserInfo;
	private Panel pnlReasonComments;
	private JComboBox<String> cbxDoctor;
	private JComboBox<String> cbxUserCreator;
	private Date dateOfBirth;
	private Date apptDateTime;
	private String currentTime;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAppt frame = new CreateAppt();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateAppt() {
		setTitle("Create an Appointment");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 730, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setLocationRelativeTo(null);
		
		
		
		btnCreate = new JButton("Create");
		
		btnCreate.setBounds(6, 342, 148, 40);
		contentPane.add(btnCreate);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search patient = new Search();
				patient.main();
				dispose();
			}
		});
		btnCancel.setBounds(576, 342, 148, 40);
		contentPane.add(btnCancel);
		
		Panel pnlPatientInfo = new Panel();
		pnlPatientInfo.setBounds(20, 6, 305, 175);
		contentPane.add(pnlPatientInfo);
		pnlPatientInfo.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(6, 10, 65, 17);
		pnlPatientInfo.add(lblFirstName);
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(83, 5, 162, 28);
		pnlPatientInfo.add(txtFirstName);
		txtFirstName.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(6, 61, 70, 14);
		pnlPatientInfo.add(lblLastName);
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtLastName = new JTextField();
		txtLastName.setBounds(83, 55, 162, 28);
		pnlPatientInfo.add(txtLastName);
		txtLastName.setColumns(10);
		
		lblDOB = new JLabel("Date of Birth");
		lblDOB.setBounds(5, 111, 82, 14);
		pnlPatientInfo.add(lblDOB);
		lblDOB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JDateChooser jdcDOB = new JDateChooser();
		jdcDOB.setBounds(86, 105, 155, 28);
		pnlPatientInfo.add(jdcDOB);
//		jdcDOB.addPropertyChangeListener(new PropertyChangeListener() {
//			public void propertyChange(PropertyChangeEvent evt) {
//				dateOfBirth = jdcDOB.getDate().toString();
//			}
//		});
		jdcDOB.setToolTipText("Selects a date of birth; this is to differntiate between patients who have the same name, as well as verification of identity");
		
		pnlApptDateTime = new Panel();
		pnlApptDateTime.setBounds(20, 197, 305, 117);
		contentPane.add(pnlApptDateTime);
		pnlApptDateTime.setLayout(null);
		
		lblAppointmentDate = new JLabel("Appointment Date");
		lblAppointmentDate.setBounds(6, 10, 112, 17);
		pnlApptDateTime.add(lblAppointmentDate);
		lblAppointmentDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JDateChooser jdcDate = new JDateChooser();
		jdcDate.setBounds(125, 7, 145, 28);
		pnlApptDateTime.add(jdcDate);
//		jdcDate.addPropertyChangeListener(new PropertyChangeListener() {
//			@Override
//			public void propertyChange(PropertyChangeEvent evt) {
//				apptDate = jdcDate.getDate().toString();
//			}
//		});
		
		JLabel lblApptTime = new JLabel("Time");
		lblApptTime.setBounds(89, 51, 35, 14);
		pnlApptDateTime.add(lblApptTime);
		lblApptTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JComboBox cbxApptTime = new JComboBox();
		cbxApptTime.setBounds(120, 47, 95, 27);
		pnlApptDateTime.add(cbxApptTime);
		cbxApptTime.setModel(new DefaultComboBoxModel(new String[] {"1:00", "1:10", "1:20", "1:30", "1:40", "1:50", "2:00", "2:10", "2:20", "2:30", "2:40", "2:50", "3:00", "3:10", "3:20", "3:30", "3:40", "3:50", "4:00", "4:10", "4:20", "4:30", "4:40", "4:50", "5:00", "5:10", "5:20", "5:30", "5:40", "5:50", "6:00", "6:10", "6:20", "6:30", "6:40", "6:50", "7:00", "7:10", "7:20", "7:30", "7:40", "7:50", "8:00", "8:10", "8:20", "8:30", "8:40", "8:50", "9:00", "9:10", "9:20", "9:30", "9:40", "9:50", "10:00", "10:10", "10:20", "10:30", "10:40", "10:50", "11:00", "11:10", "11:20", "11:30", "11:40", "11:50", "12:00", "12:10", "12:20", "12:30", "12:40", "12:50"}));
		cbxApptTime.setSelectedIndex(42);
		
		JComboBox cbxAmPm = new JComboBox();
		cbxAmPm.setBounds(211, 47, 84, 27);
		pnlApptDateTime.add(cbxAmPm);
		cbxAmPm.setModel(new DefaultComboBoxModel(new String[] {"A.M.", "P.M."}));
		cbxAmPm.setSelectedIndex(0);
		
		pnlDoctorUserInfo = new Panel();
		pnlDoctorUserInfo.setBounds(395, 6, 325, 104);
		contentPane.add(pnlDoctorUserInfo);
		pnlDoctorUserInfo.setLayout(null);
		
		lblDoctor = new JLabel("Doctor");
		lblDoctor.setBounds(6, 11, 75, 17);
		pnlDoctorUserInfo.add(lblDoctor);
		lblDoctor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblCreatedBy = new JLabel("Created By");
		lblCreatedBy.setBounds(6, 51, 75, 17);
		pnlDoctorUserInfo.add(lblCreatedBy);
		lblCreatedBy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		cbxDoctor = new JComboBox();
		cbxDoctor.setBounds(93, 8, 150, 27);
		pnlDoctorUserInfo.add(cbxDoctor);
		
		cbxUserCreator = new JComboBox();
		cbxUserCreator.setBounds(93, 48, 150, 27);
		pnlDoctorUserInfo.add(cbxUserCreator);
		
		pnlReasonComments = new Panel();
		pnlReasonComments.setBounds(395, 116, 325, 198);
		contentPane.add(pnlReasonComments);
		pnlReasonComments.setLayout(null);
		
		lblReasonOfVisit = new JLabel("Reason of Visit");
		lblReasonOfVisit.setBounds(6, 11, 90, 17);
		pnlReasonComments.add(lblReasonOfVisit);
		lblReasonOfVisit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtReason = new JTextField();
		txtReason.setBounds(105, 5, 152, 28);
		pnlReasonComments.add(txtReason);
		txtReason.setToolTipText("");
		txtReason.setColumns(10);
		
		lblComments = new JLabel("Comments/Extra Notes");
		lblComments.setBounds(6, 45, 152, 17);
		pnlReasonComments.add(lblComments);
		lblComments.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		txtComments = new JTextField();
		txtComments.setBounds(6, 74, 313, 118);
		pnlReasonComments.add(txtComments);
		txtComments.setToolTipText("");
		txtComments.setColumns(10);
		
		try {
			Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
			Statement stmt = conn.createStatement();
			ResultSet loadDoctors, loadUsers;
			loadDoctors = stmt.executeQuery("Select username from medprogram.user where userrole = 2;");
			while (loadDoctors.next()) {
				String tmp = loadDoctors.getString("username");
				cbxDoctor.addItem(tmp);
			}
			stmt.closeOnCompletion();
			
			loadUsers = stmt.executeQuery("Select username from medprogram.user;");
			while (loadUsers.next()) {
				String tmp = loadUsers.getString("username");
				cbxUserCreator.addItem(tmp);
			}
			stmt.closeOnCompletion();
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String reason, commentNotes, apptTime;
				
				int pid = -1;
				int creator = -1;
				int doctor = -1;
				
				if(txtFirstName.getText().equals("") || txtLastName.getText().equals("") || txtReason.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please enter the patient's first and last name, as well as a reason for the visit.");
					return;
				}
				
				try
				{
					java.util.Date birth = jdcDOB.getDate();
					
					dateOfBirth = new Date(birth.getTime());
				}
				catch (Exception dobEx) {
					// TODO Auto-generated catch block
					dobEx.printStackTrace();
					JOptionPane.showMessageDialog(null, "Please enter a birth date for patient");
					return;
				}
				
				try
				{
					java.util.Date appt = jdcDate.getDate();
					
					Calendar apptDate = Calendar.getInstance();
					apptDate.setTime(appt);
					apptTime = (String) cbxApptTime.getItemAt(cbxApptTime.getSelectedIndex());
					String[] timeStore = apptTime.split(":");
					int hour = Integer.parseInt(timeStore[0]);
					int minutes = Integer.parseInt(timeStore[1]);
					apptDate.add(Calendar.HOUR_OF_DAY, hour);
					apptDate.add(Calendar.MINUTE, minutes);
					
					if (cbxAmPm.getSelectedIndex() == 0 && (cbxApptTime.getSelectedIndex() >= 66 && cbxApptTime.getSelectedIndex() <= 77))
					{
						
						apptDate.add(Calendar.HOUR_OF_DAY, -12);
											
					}
					else if (cbxAmPm.getSelectedIndex() == 1 && cbxApptTime.getSelectedIndex() < 66)
					{
						apptDate.add(Calendar.HOUR_OF_DAY, 12);
					}
					
					java.util.Date convertedDate = apptDate.getTime();
					
					java.text.SimpleDateFormat sdf = 
						     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");

					currentTime = sdf.format(convertedDate);
					
					System.out.println(currentTime);
				}
				catch (Exception dateEx) {
					// TODO Auto-generated catch block
					dateEx.printStackTrace();
					JOptionPane.showMessageDialog(null, "Please enter an appointment date and time for patient");
					return;
				}
				
				
			try {
				
				Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
				Statement stmt = conn.createStatement();

					ResultSet firstRs, secondRs, thirdRs;
					System.out.println(txtFirstName.getText() + " " + txtLastName.getText() + " " + dateOfBirth);
					firstRs = stmt.executeQuery("SELECT ph.patientid, ph.firstname,ph.lastname,ph.dateofbirth FROM medprogram.patientheader ph JOIN medprogram.patientdetails pd on ph.patientid = pd.patientid "
							+ "WHERE ph.firstname = '" + txtFirstName.getText() + "' and ph.lastname = '" + txtLastName.getText() + "' and ph.dateofbirth = '" + dateOfBirth + "';");
					while (firstRs.next()){
						pid = firstRs.getInt("patientid");
					}
					
					if (pid == -1)
					{
						JOptionPane.showMessageDialog(null, "Unable to find patient. Please check the first and last names, as well as the date of birth, and try again.");
						return;
					}
						

					stmt.closeOnCompletion();
					
					secondRs = stmt.executeQuery("SELECT u.userid FROM medprogram.user u WHERE u.username = '" + cbxUserCreator.getItemAt(cbxUserCreator.getSelectedIndex()) + "';");

						while(secondRs.next())
						{
							creator = secondRs.getInt("userid");
						}
						
					stmt.closeOnCompletion();
			
					thirdRs = stmt.executeQuery("SELECT u.userid FROM medprogram.user u WHERE u.username = '" + cbxDoctor.getItemAt(cbxDoctor.getSelectedIndex()) + "';");
					while (thirdRs.next()){
						doctor = thirdRs.getInt("userid");
					}
					
					stmt.closeOnCompletion();
					
					reason = txtReason.getText();
					commentNotes = txtComments.getText();
					
					System.out.println(pid + " " + currentTime + " " + doctor + " " + reason + " " + commentNotes + " " + creator);
					
					stmt.executeUpdate("INSERT INTO appointments (patientid, appttime, doctor, visitreason, comments, creatoruser)" 
					+ " VALUES (" + pid + ", '" + currentTime + "', " + doctor +", '" + reason + "', '" + commentNotes + "', " + creator + ")");
					stmt.closeOnCompletion();
					
					conn.close();
					
					java.text.SimpleDateFormat simpleDate = 
						     new java.text.SimpleDateFormat("MM-dd-yyyy");
					
					String displayDate = simpleDate.format(jdcDate.getDate());
					
					JOptionPane.showMessageDialog(null, "The appointment has been made for " + txtFirstName.getText() + " " + txtLastName.getText() 
					+ " on " + displayDate + " at " + cbxApptTime.getSelectedItem() + " " + cbxAmPm.getSelectedItem());
					
					Search patient = new Search();
					patient.main();
					dispose();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Appointment creation was unsuccessful. The server may be down. Please wait a few minutes and try again.");
				}
				
			}
		});
	}
}