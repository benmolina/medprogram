package medProgram;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFormattedTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.ScrollPane;

public class EditMedRecords extends JFrame {

	private JPanel contentPane;
	private static JTextField txtPatientID;
	private static JTextField txtPatientName;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtAddress;
	private JTextField txtCity;
	private JTextField txtApt;
	private JTextField txtZip;
	private JTextField txtInsurance;
	private JTextField txtInsuranceInfo;
	private JFormattedTextField txtSSN;
	private JComboBox<String> cbxIsOrganDonor;
	private JComboBox<String> cbxOrganTransplant;
	private int dialogBtn = JOptionPane.YES_NO_OPTION;
	
	private static DefaultListModel<String> listModel;
	private static DefaultListModel<String> allergyModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new EditMedRecords();
	}

	/**
	 * Create the frame.
	 */
	public EditMedRecords() {
		setTitle("Medical Records");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnlSearch = new JPanel();
		pnlSearch.setBounds(6, 6, 420, 91);
		pnlSearch.setLayout(null);
		pnlSearch.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(pnlSearch);
		
		JLabel lblPatientId = new JLabel("Patient ID:");
		lblPatientId.setBounds(6, 12, 75, 16);
		pnlSearch.add(lblPatientId);
		
		txtPatientID = new JTextField();
		txtPatientID.setColumns(10);
		txtPatientID.setBounds(75, 6, 55, 28);
		txtPatientID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (txtPatientID.getText().length() < 1) 
					return;
				else
				{
					int id = -1;
					String firstName = "";
					String lastName = "";
					try {
						id = Integer.parseInt(txtPatientID.getText());
					} catch (Exception idExcept) {
						JOptionPane.showMessageDialog(null, "Please enter only numbers for the ID");
						return;
					}
					
					try {
						Connection myConn = DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
						
						String query = "select firstname, lastname from medprogram.patientheader where patientid = ?";
						PreparedStatement pstmt = myConn.prepareStatement(query);
						pstmt.setInt(1, id);
						ResultSet myRes;
						
						myRes = pstmt.executeQuery();						
						
						while(myRes.next()){
							firstName = myRes.getString("firstname");
							lastName = myRes.getString("lastname");
							}
						
						pstmt.closeOnCompletion();
						
						if (firstName.equals("") || lastName.equals(""))
						{
							JOptionPane.showMessageDialog(null, "A user with this id does not exist. If you believe you see this message in error, contact System Admin.");
							return;	
						}
						else
						{
							
							txtPatientName.setText(firstName + " " + lastName);
						}
							 
						myConn.close();
					}
					catch (Exception exec){
						exec.printStackTrace();
						JOptionPane.showMessageDialog(null, "There was an error with patient lookup. Please try again later. If the issue persists, please contact Customer Service.");
					}
				}
			}
		});
		pnlSearch.add(txtPatientID);
		
		JLabel lblPatientName = new JLabel("Patient Name:");
		lblPatientName.setBounds(175, 12, 88, 16);
		pnlSearch.add(lblPatientName);
		
		txtPatientName = new JTextField();
		txtPatientName.setColumns(10);
		txtPatientName.setBounds(265, 6, 150, 28);
		txtPatientName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (txtPatientName.getText().length() < 1) 
					return;
				else
				{
					String [] fullName = txtPatientName.getText().split("\\s+");
					
					int id = -1;
					int dupCount = 0;
					try {
						Connection myConn = DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
						
						Statement myStmt = myConn.createStatement();
						ResultSet myRes;
						
						if (fullName.length < 2) {
							//myRes = myStmt.executeQuery("select patientid from medprogram.patientheader where firstname like '%"
									//+ fullName[0] + "%';");
							String query = "select patientid from medprogram.patientheader where firstname like ?;";
							PreparedStatement pstmt = myConn.prepareStatement(query);
							pstmt.setString(1,"%" + fullName[0] + "%");
							myRes = pstmt.executeQuery();
						}
						else
						{
							//myRes = myStmt.executeQuery("select patientid from medprogram.patientheader where firstname like '%"
									//+ fullName[0] + "%' and lastname like '%" + fullName[1] + "%';");
							String query = "select patientid from medprogram.patientheader where firstname like ? and lastname like ?;";
							PreparedStatement pstmt = myConn.prepareStatement(query);
							pstmt.setString(1, "%" +fullName[0] + "%");
							pstmt.setString(2, "%" +fullName[1] + "%");
							myRes = pstmt.executeQuery();
						}
						
						
						while(myRes.next()){
							id = myRes.getInt("patientid");
							dupCount++;
							}
						
						myStmt.closeOnCompletion();
						
						if (dupCount > 1)
						{
							if (fullName.length < 2)
							{
								PmtPatientSearch pmt = new PmtPatientSearch(fullName[0], 1);
							}
							else 
							{
								PmtPatientSearch pmt = new PmtPatientSearch(fullName[0] + " " + fullName[1], 1);
							}
								
						}
						else
						{
							if (id > 0)
								txtPatientID.setText(Integer.toString(id));
						}
							 
						myConn.close();
					}
					catch (Exception exec){
						exec.printStackTrace();
						JOptionPane.showMessageDialog(null, "There was an error with patient lookup. Please try again later. If the issue persists, please contact Customer Service.");
					}
				}
					
					
					
			}
		});
		pnlSearch.add(txtPatientName);
		
		JButton btnSearchPatient = new JButton("Search Patient");
		btnSearchPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PmtPatientSearch pmt = new PmtPatientSearch("", 1);
			}
		});
		btnSearchPatient.setBounds(6, 56, 117, 29);
		pnlSearch.add(btnSearchPatient);
		
		JButton btnSelect = new JButton("Select Patient");
		
		btnSelect.setBounds(298, 56, 117, 29);
		pnlSearch.add(btnSelect);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(483, 461, 126, 29);
		contentPane.add(btnSave);
		
		MaskFormatter phoneNum = null;
		try {
			phoneNum = new MaskFormatter("(###) ###-####");
			phoneNum.setPlaceholderCharacter('_');
		} catch (ParseException e2) {	
			e2.printStackTrace();
		}
		
		MaskFormatter ssnLimit = null;
		try {
			ssnLimit = new MaskFormatter("#########");
			ssnLimit.setPlaceholderCharacter('_');
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel?", "Warning", dialogBtn);
				if (dialogResult == 0) {
					dispose();
				}
			}
		});
		btnCancel.setBounds(6, 461, 126, 29);
		contentPane.add(btnCancel);
		
		JTabbedPane tbpContact = new JTabbedPane(JTabbedPane.TOP);
		tbpContact.setBounds(6, 109, 603, 340);
		contentPane.add(tbpContact);
		
		JLayeredPane lpnContact = new JLayeredPane();
		tbpContact.addTab("Contact Info", null, lpnContact, null);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(6, 6, 80, 16);
		lpnContact.add(lblFirstName);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(6, 27, 130, 22);
		lpnContact.add(txtFirstName);
		txtFirstName.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(173, 6, 80, 16);
		lpnContact.add(lblLastName);
		
		txtLastName = new JTextField();
		txtLastName.setBounds(170, 24, 130, 28);
		lpnContact.add(txtLastName);
		txtLastName.setColumns(10);
		
		JLabel lblSSN = new JLabel("SSN");
		lblSSN.setBounds(333, 6, 80, 16);
		lpnContact.add(lblSSN);
		
		txtSSN = new JFormattedTextField(ssnLimit);
		txtSSN.setBounds(329, 24, 130, 28);
		lpnContact.add(txtSSN);
		txtSSN.setColumns(10);
		
		JLabel lblDOB = new JLabel("Date of Birth");
		lblDOB.setBounds(8, 64, 97, 16);
		lpnContact.add(lblDOB);
		
		JDateChooser jdcDOB = new JDateChooser();
		jdcDOB.setBounds(7, 79, 130, 28);
		lpnContact.add(jdcDOB);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setBounds(170, 64, 61, 16);
		lpnContact.add(lblGender);
		
		JComboBox<String> cbxGender = new JComboBox<String>();
		cbxGender.setBounds(166, 78, 134, 29);
		lpnContact.add(cbxGender);
		cbxGender.setModel(new DefaultComboBoxModel<String>(new String[] {"Female", "Male"}));
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(8, 124, 61, 16);
		lpnContact.add(lblAddress);
		
		txtAddress = new JTextField();
		txtAddress.setBounds(6, 143, 180, 22);
		lpnContact.add(txtAddress);
		txtAddress.setColumns(10);
		
		JLabel lblApt = new JLabel("Apt Bldg/#");
		lblApt.setBounds(201, 124, 80, 16);
		lpnContact.add(lblApt);
		
		txtApt = new JTextField();
		txtApt.setBounds(198, 143, 180, 22);
		lpnContact.add(txtApt);
		txtApt.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(421, 124, 105, 16);
		lpnContact.add(lblPhoneNumber);
		
		JFormattedTextField txtPhoneNumber;
		txtPhoneNumber = new JFormattedTextField(phoneNum);
		txtPhoneNumber.setBounds(419, 140, 136, 28);
		lpnContact.add(txtPhoneNumber);
		
		JLabel lblZipCode = new JLabel("Zip Code");
		lblZipCode.setBounds(423, 183, 61, 16);
		lpnContact.add(lblZipCode);
		
		txtZip = new JTextField();
		txtZip.setBounds(419, 202, 120, 22);
		lpnContact.add(txtZip);
		txtZip.setColumns(10);
		
		JLabel lblState = new JLabel("State");
		lblState.setBounds(205, 179, 61, 16);
		lpnContact.add(lblState);
		
		JComboBox<String> cbxState = new JComboBox<String>();
		cbxState.setBounds(201, 196, 181, 28);
		lpnContact.add(cbxState);
		cbxState.setModel(new DefaultComboBoxModel<String>(new String[] {"ALABAMA - AL", "ALASKA - AK", "ARIZONA - AZ", "ARKANSAS - AR", "CALIFORNIA - CA", "COLORADO - CO", "CONNECTICUT - CT", "DELAWARE - DE", "FLORIDA - FL", "GEORGIA - GA", "HAWAII - HI", "IDAHO - ID", "ILLINOIS - IL", "INDIANA - IN", "IOWA - IA", "KANSAS - KS", "KENTUCKY - KY", "LOUISIANA - LA", "MAINE - ME", "MARYLAND - MD", "MASSACHUSETTS - MA", "MICHIGAN - MI", "MINNESOTA - MN", "MISSISSIPPI - MS", "MISSOURI - MO", "MONTANA - MT", "NEBRASKA - NE", "NEVADA - NV", "NEW HAMPSHIRE - NH", "NEW JERSEY - NJ", "NEW MEXICO - NM", "NEW YORK - NY", "NORTH CAROLINA - NC", "NORTH DAKOTA - ND", "OHIO - OH", "OKLAHOMA - OK", "OREGON - OR", "PENNSYLVANIA - PA", "RHODE ISLAND - RI", "SOUTH CAROLINA - SC", "SOUTH DAKOTA - SD", "TENNESSEE - TN", "TEXAS - TX", "UTAH - UT", "VERMONT - VT", "VIRGINIA - VA", "WASHINGTON - WA", "WEST VIRGINIA - WV", "WISCONSIN - WI", "WYOMING - WY", "GUAM - GU", "PUERTO RICO - PR", "VIRGIN ISLANDS - VI"}));
		
		JLabel lblCity = new JLabel("City");
		lblCity.setBounds(9, 183, 61, 16);
		lpnContact.add(lblCity);
		
		txtCity = new JTextField();
		txtCity.setBounds(6, 202, 180, 22);
		lpnContact.add(txtCity);
		txtCity.setColumns(10);
		
		JLayeredPane lpnAdditional = new JLayeredPane();
		tbpContact.addTab("Additional Info", null, lpnAdditional, null);
		
		JLabel lblInsuranceProvider = new JLabel("Insurance Provider");
		lblInsuranceProvider.setBounds(10, 6, 125, 16);
		lpnAdditional.add(lblInsuranceProvider);
		
		txtInsurance = new JTextField();
		txtInsurance.setBounds(6, 24, 180, 22);
		lpnAdditional.add(txtInsurance);
		txtInsurance.setColumns(10);
		
		JLabel lblInsuranceInfo = new JLabel("Insurance Info");
		lblInsuranceInfo.setBounds(212, 6, 100, 16);
		lpnAdditional.add(lblInsuranceInfo);
		
		txtInsuranceInfo = new JTextField();
		txtInsuranceInfo.setBounds(208, 24, 180, 22);
		lpnAdditional.add(txtInsuranceInfo);
		txtInsuranceInfo.setToolTipText("Insurance Information includes Policy Number, Type of Membership, etc.");
		txtInsuranceInfo.setColumns(10);
		
		JLabel lblOrganDonor = new JLabel("Is Patient an Organ Donor?");
		lblOrganDonor.setBounds(12, 65, 176, 16);
		lpnAdditional.add(lblOrganDonor);
		
		cbxIsOrganDonor = new JComboBox<String>();
		cbxIsOrganDonor.setBounds(6, 83, 90, 22);
		lpnAdditional.add(cbxIsOrganDonor);
		cbxIsOrganDonor.setModel(new DefaultComboBoxModel<String>(new String[] {"No", "Yes"}));
		
		JLabel lblDoesPatientNeed = new JLabel("Does Patient Need an Organ Transplant Soon?");
		lblDoesPatientNeed.setBounds(214, 65, 295, 16);
		lpnAdditional.add(lblDoesPatientNeed);
		
		cbxOrganTransplant = new JComboBox<String>();
		cbxOrganTransplant.setBounds(209, 83, 90, 22);
		lpnAdditional.add(cbxOrganTransplant);
		cbxOrganTransplant.setModel(new DefaultComboBoxModel<String>(new String[] {"No", "Yes"}));
		cbxOrganTransplant.setToolTipText("Insurance Information includes Policy Number, Type of Membership, etc.");
		
		JLabel lblImportantNotes = new JLabel("Important Notes");
		lblImportantNotes.setBounds(317, 124, 103, 16);
		lpnAdditional.add(lblImportantNotes);
		
		JTextArea txtNotes = new JTextArea();
		txtNotes.setBounds(317, 145, 259, 95);
		lpnAdditional.add(txtNotes);
		txtNotes.setWrapStyleWord(true);
		txtNotes.setLineWrap(true);
		
		JLabel lblAllergies = new JLabel("Allergies");
		lblAllergies.setBounds(6, 124, 61, 16);
		lpnAdditional.add(lblAllergies);
		
		allergyModel = new DefaultListModel<String>();
		
		JScrollPane scpAllergyList = new JScrollPane();
		scpAllergyList.setBounds(6, 145, 225, 95);
		lpnAdditional.add(scpAllergyList);
		
		JList<String> lstAllergies = new JList<String>();
		lstAllergies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scpAllergyList.setViewportView(lstAllergies);
		lstAllergies.setModel(allergyModel);
		
		JLayeredPane lpnAppointments = new JLayeredPane();
		tbpContact.addTab("Appointments", null, lpnAppointments, null);
		
		JLabel lblAppointments = new JLabel("Appointments");
		lblAppointments.setBounds(6, 6, 100, 16);
		lpnAppointments.add(lblAppointments);
		
		listModel = new DefaultListModel<String>();
				
		JScrollPane scpApptList = new JScrollPane();
		scpApptList.setBounds(6, 28, 570, 132);
		lpnAppointments.add(scpApptList);
		
		JList<String> lstAppts = new JList<String>();
		scpApptList.setViewportView(lstAppts);
		lstAppts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExport.setBounds(231, 461, 126, 29);
		contentPane.add(btnExport);
				
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtPatientID.equals("") || txtPatientName.equals("")) {
					JOptionPane.showMessageDialog(null, "Please make sure a patient is selected.");
					return;
				} 
				else
				{
										
					int id = Integer.parseInt(txtPatientID.getText());
					
					if (id <= 0) {
						JOptionPane.showMessageDialog(null, "Please select a patient to view ");
					}
					
					try {
						Connection conn = DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
						
						String query = "SELECT ph.firstname,ph.lastname,ph.dateofbirth,ph.gender,ph.street,ph.aptnumber,ph.city,ph.state,ph.zipcode,ph.phonenumber,"
								+ "pd.insuranceprovider,pd.insuranceinfo,pd.isorgandonor,pd.needsorgdonation,pd.importantnotes,pd.ssn FROM medprogram.patientheader ph "
								+ "left outer JOIN medprogram.patientdetails pd on ph.patientid = pd.patientid "
								+ "WHERE ph.patientid = ?;";
						
						PreparedStatement pstmt = conn.prepareStatement(query);
						pstmt.setInt(1, id);
						
						ResultSet getAllPatInfo;
						
						getAllPatInfo = pstmt.executeQuery();
						
						while (getAllPatInfo.next())
						{
							txtFirstName.setText(getAllPatInfo.getString("firstname"));
							txtLastName.setText(getAllPatInfo.getString("lastname"));
							Timestamp dob = getAllPatInfo.getTimestamp("dateofbirth");
							jdcDOB.setDate(dob);
							
							String gender = getAllPatInfo.getString("gender");
							
							if (gender.equals("Male"))
								cbxGender.setSelectedIndex(1);
							else
								cbxGender.setSelectedIndex(0);
							
							txtAddress.setText(getAllPatInfo.getString("street"));
							
							txtApt.setText(getAllPatInfo.getString("aptnumber"));
							
							txtCity.setText(getAllPatInfo.getString("city"));

							
							String state = getAllPatInfo.getString("state");
							
							for (int i = 0; i < cbxState.getItemCount(); i++)
							{
								String [] tmpState = cbxState.getItemAt(i).toString().split("-\\s");
								if (state.equals(tmpState[1]))
								{
									cbxState.setSelectedIndex(i);
									break;
								}
									
							}
							
							txtZip.setText(getAllPatInfo.getString("zipcode"));
							txtPhoneNumber.setValue(getAllPatInfo.getString("phonenumber"));
							txtInsurance.setText(getAllPatInfo.getString("insuranceprovider"));
							txtInsuranceInfo.setText(getAllPatInfo.getString("insuranceinfo"));
							
							if (getAllPatInfo.getInt("isorgandonor") == 0)
								cbxIsOrganDonor.setSelectedIndex(0);
							else
								cbxIsOrganDonor.setSelectedIndex(1);
							
							if (getAllPatInfo.getInt("needsorgdonation") == 0)
								cbxOrganTransplant.setSelectedIndex(0);
							else
								cbxOrganTransplant.setSelectedIndex(1);
							
							txtSSN.setText(getAllPatInfo.getString("ssn"));
							txtNotes.setText(getAllPatInfo.getString("importantnotes"));
							
						}
						
						query = "select s.supplydesc from allergylist a "
								+ "join supplies s on a.supplyid = s.supplyid "
								+ "where patientid = ? "
								+ "and isactiveallergy = 1;";
						
						PreparedStatement allergy = conn.prepareStatement(query);
						allergy.setInt(1, id);
						
						ResultSet allergyInfo = allergy.executeQuery();
						
						while (allergyInfo.next()) {
							String allergyDesc = allergyInfo.getString("supplydesc");
							allergyModel.addElement(allergyDesc);
						}
						
						lstAllergies.setModel(allergyModel);
						
						query = "select appttime, visitreason, comments from medprogram.appointments "
								+ "where patientid = ? "
								+ "order by appttime desc;";
						
						PreparedStatement appt = conn.prepareStatement(query);
						appt.setInt(1, id);
						
						ResultSet apptInfo = appt.executeQuery();
						
						while (apptInfo.next()) {
							
							Timestamp compDate = apptInfo.getTimestamp("appttime");
							
							java.text.SimpleDateFormat sdf = 
									new java.text.SimpleDateFormat("MM-dd-yyyy   HH:mm");
							
							String dateTime = sdf.format(compDate);
							

							String reason = apptInfo.getString("visitreason");
							
							String comments = apptInfo.getString("comments");
		
							String completeInfo = dateTime + "   " + reason + "   " + comments;
							
							listModel.addElement(completeInfo);
						}
						
						lstAppts.setModel(listModel);
						
						conn.close();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Unable to retrieve patient information. Please make sure that server is intact. If issue persists, please contact System Admin");
						e1.printStackTrace();
					}
					
				}
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int id = -1;
				
				try
				{
					id = Integer.parseInt(txtPatientID.getText());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Please select a patient and try again.");
					return;
				}
				
				if (id == -1) {
					JOptionPane.showMessageDialog(null, "Please select a patient.");
					return;
				}
				
				try {
					
					String firstName = "", lastName = "", ssn = "", finalDOB = "", gender = "", 
							address = "", apt = "", phoneNumber = "", city = "", state = "", 
							zipCode = "", insurance = "", insuranceInfo = "", notes = "";
					int organDonor = -1;
					int donateNeeded = -1;
					
				firstName = txtFirstName.getText();
				lastName = txtLastName.getText();
				ssn = txtSSN.getText();
				System.out.println(ssn);
				
				java.util.Date birth = jdcDOB.getDate();
				Date dateofbirth = new Date(birth.getTime());
				Calendar conversion = Calendar.getInstance();
				conversion.setTime(dateofbirth);
				java.util.Date convertedDate = conversion.getTime();				
				java.text.SimpleDateFormat sdf = 
					     new java.text.SimpleDateFormat("yyyy-MM-dd");
				finalDOB = sdf.format(convertedDate);
				System.out.print(finalDOB);
				
				gender = cbxGender.getSelectedItem().toString();
				address = txtAddress.getText();
				apt = txtApt.getText();
				phoneNumber = txtPhoneNumber.getText();
				city = txtCity.getText();
				
				String[] stateArray = cbxState.getSelectedItem().toString().split("-\\s");
				state = stateArray[1];
				
				zipCode = txtZip.getText();
				insurance = txtInsurance.getText();
				insuranceInfo = txtInsuranceInfo.getText();
				organDonor = cbxIsOrganDonor.getSelectedIndex();
				donateNeeded = cbxOrganTransplant.getSelectedIndex();
				notes = txtNotes.getText();
				
				if (firstName.equals("")){
					JOptionPane.showMessageDialog(null, "Please fill out first name.");
					return;
				}
				if (lastName.equals("")){
					JOptionPane.showMessageDialog(null, "Please fill out last name.");
					return;
				}
				if (ssn.equals("") || !(isNumeric(ssn))){
					JOptionPane.showMessageDialog(null, "Please fill out SSN and make sure there are only numbers included.");
					return;
				}
				if (finalDOB.equals("")){
					JOptionPane.showMessageDialog(null, "Please fill out date of birth.");
					return;
				}
				if (gender.equals("")){
					JOptionPane.showMessageDialog(null, "Please select a gender.");
					return;
				}
				if (address.equals("")){
					JOptionPane.showMessageDialog(null, "Please fill out address.");
					return;
				}
				String [] tmpText = phoneNumber.split("\\s+");
				if (tmpText.length > 2) {
					JOptionPane.showMessageDialog(null, "Please fill out phone number.");
					return;
				}
				if (city.equals("")){
					JOptionPane.showMessageDialog(null, "Please fill out city.");
					return;
				}
				if (state.equals("")){
					JOptionPane.showMessageDialog(null, "Please select a state.;");
					return;
				}
				if (zipCode.equals("")){
					JOptionPane.showMessageDialog(null, "Please fill out zip code.");
					return;
				}
				if (organDonor == -1){
					JOptionPane.showMessageDialog(null, "Please select an option for Organ Donor.");
					return;
				}
				if (donateNeeded == -1){
					JOptionPane.showMessageDialog(null, "Please select an option for Needing an Organ Donation.");
					return;
				}
				
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to save all information?", "Attention", dialogBtn);
				if (dialogResult == 1) {
					return;
				}
				
				Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
				
				String hQuery = "update medprogram.patientheader "
						+ "set firstname = ?, lastname = ?, dateofbirth = ?, gender = ?, street = ?, aptnumber = ?, city = ?, "
						+ "state = ?, zipcode = ?, phonenumber = ? "
						+ "where patientid = ?;";
				
				String dQuery = "insert into medprogram.patientdetails (patientid, insuranceprovider, insuranceinfo, isorgandonor, "
						+ "needsorgdonation, importantnotes, ssn) "
						+ "values(?,?,?,?,?,?,?) "
						+ "on duplicate key update insuranceprovider = ?, insuranceinfo = ?, isorgandonor = ?, "
						+ "needsorgdonation = ?, importantnotes = ?, ssn = ?;";
				
				PreparedStatement header, details;
				
				header = conn.prepareStatement(hQuery);
				details = conn.prepareStatement(dQuery);
				
				header.setString(1, firstName);
				header.setString(2, lastName);
				header.setString(3, finalDOB);
				header.setString(4, gender);
				header.setString(5, address);
				header.setString(6, apt);
				header.setString(7, city);
				header.setString(8, state);
				header.setString(9, zipCode);
				header.setString(10, phoneNumber);
				header.setInt(11, id);
				
				details.setInt(1, id);
				details.setString(2, insurance);
				details.setString(3, insuranceInfo);
				details.setInt(4, organDonor);
				details.setInt(5, donateNeeded);
				details.setString(6, notes);
				details.setString(7, ssn);
				details.setString(8, insurance);
				details.setString(9, insuranceInfo);
				details.setInt(10, organDonor);
				details.setInt(11, donateNeeded);
				details.setString(12, notes);
				details.setString(13, ssn);
				
				header.executeUpdate();
				
				header.closeOnCompletion();
				
				details.executeUpdate();
				
				details.closeOnCompletion();
				
				conn.close();
				
				JOptionPane.showMessageDialog(null, "Patient record for " + firstName + " " + lastName + " has been modified and saved.");
				
				dispose();
				new EditMedRecords();
				
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Please make sure all values are filled out correctly and try again. If the issue persists, please contact System Admin");
					ex.printStackTrace();
					return;
				}
				
			}
		});
		
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtPatientID.getText().equals("") || txtPatientName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please select a patient before exporting records.");
					return;
				}
						String userHomeFolder = System.getProperty("user.home")+"/Desktop";
						String filename = String.format("%s%s_Medical_Record.txt",txtFirstName.getText(),txtLastName.getText());
						File textFile = new File(userHomeFolder, filename);
						DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				        java.util.Date today = Calendar.getInstance().getTime();
				        String reportDate = df.format(today);
						BufferedWriter out;
						
						try {
							out = new BufferedWriter(new FileWriter(textFile));
							out.write("Patient Name: "+ txtFirstName.getText() + " " + txtLastName.getText() +"\n\n");
							out.newLine();
							out.write("Gender: " + cbxGender.getSelectedItem());
							out.newLine();
							out.write("Date of Birth: " + jdcDOB.getDateFormatString());
							out.newLine();
							out.write("SSN: " + txtSSN.getText());
							out.newLine();
							out.newLine();
							out.write("Address: " + txtAddress.getText());
							out.newLine();
							out.write("Apt #/Building: " + txtApt.getText());
							out.newLine();
							out.write("City: " + txtCity.getText());
							out.newLine();
							out.write("State: " + cbxState.getSelectedItem().toString());
							out.newLine();
							out.write("Phone number: " + txtPhoneNumber.getText());
							out.newLine();
							out.newLine();
							out.write("Insurance Provider: " + txtInsurance.getText());
							out.newLine();
							out.write("Insurance Information: " + txtInsuranceInfo.getText());
							out.newLine();
							out.write("Is patient an organ donor? " + cbxIsOrganDonor.getSelectedItem());
							out.newLine();
							out.write("Does patient need an organ donation soon? " + cbxOrganTransplant.getSelectedItem());
							out.newLine();
							out.write("Allergies: ");
							for (int i = 0; i < lstAllergies.getModel().getSize(); i++) {
								if (i == 0)
									out.write(lstAllergies.getModel().getElementAt(i) + ", ");
								else
									out.write(", " + lstAllergies.getModel().getElementAt(i));
							}
							out.newLine();
							out.write("Doctor's Additional Notes: "+ txtNotes.getText() + "\n\n");
							out.newLine();
							out.write("Exporting time: "+reportDate+"\n");
							out.close();
							JOptionPane.showMessageDialog(null, "The medical record has been exported to Desktop.");
						} catch (IOException e2) {
							e2.printStackTrace();
						}
			}
		});
		

		
		setLocationRelativeTo(this);
		setVisible(true);
	}
	
	public static void setValues(String id, String name) {
		txtPatientID.setText(id);
		txtPatientName.setText(name);
	}
	
	public boolean isNumeric(String input) {
		
		try {
			long possibleNum = Long.parseLong(input);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
}
