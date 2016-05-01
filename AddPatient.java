package medProgram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import javax.swing.JFormattedTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class AddPatient extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtstreet;
	private JTextField txtcity;
	private JComboBox cbxState;
	private JTextField txtzipcode;
	private JFormattedTextField txtphonenumber;
	private JComboBox cbxGender;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new AddPatient();
	}

	/**
	 * Create the frame.
	 */
	public AddPatient() {
		
		setTitle("Add Patient");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 316, 562);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(6, 17, 73, 16);
		contentPane.add(lblFirstName);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(129, 11, 134, 28);
		contentPane.add(txtFirstName);
		txtFirstName.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(6, 57, 73, 16);
		contentPane.add(lblLastName);
		
		txtLastName = new JTextField();
		txtLastName.setBounds(129, 51, 134, 28);
		contentPane.add(txtLastName);
		txtLastName.setColumns(10);
		
		JLabel lbldateofbirth = new JLabel("Date of Birth:");
		lbldateofbirth.setBounds(6, 102, 97, 16);
		contentPane.add(lbldateofbirth);
		
		JLabel lblRole = new JLabel("Gender:");
		lblRole.setBounds(6, 141, 61, 16);
		contentPane.add(lblRole);
		
		JLabel lblPassword = new JLabel("Street:");
		lblPassword.setBounds(6, 177, 61, 16);
		contentPane.add(lblPassword);
		
		JLabel lblConfirm = new JLabel("City:");
		lblConfirm.setBounds(6, 217, 61, 16);
		contentPane.add(lblConfirm);

		JButton btnAddPatient = new JButton("Add Patient");
		btnAddPatient.setBounds(6, 483, 117, 29);
		contentPane.add(btnAddPatient);
		
		txtstreet = new JTextField();
		txtstreet.setBounds(129, 171, 134, 29);
		contentPane.add(txtstreet);
		txtstreet.setColumns(10);
		
		txtcity = new JTextField();
		txtcity.setBounds(129, 211, 134, 28);
		contentPane.add(txtcity);
		txtcity.setColumns(10);
		
		cbxState = new JComboBox();
		cbxState.setModel(new DefaultComboBoxModel(new String[] {"ALABAMA - AL", "ALASKA - AK", "ARIZONA - AZ", "ARKANSAS - AR", "CALIFORNIA - CA", "COLORADO - CO", "CONNECTICUT - CT", "DELAWARE - DE", "FLORIDA - FL", "GEORGIA - GA", "HAWAII - HI", "IDAHO - ID", "ILLINOIS - IL", "INDIANA - IN", "IOWA - IA", "KANSAS - KS", "KENTUCKY - KY", "LOUISIANA - LA", "MAINE - ME", "MARYLAND - MD", "MASSACHUSETTS - MA", "MICHIGAN - MI", "MINNESOTA - MN", "MISSISSIPPI - MS", "MISSOURI - MO", "MONTANA - MT", "NEBRASKA - NE", "NEVADA - NV", "NEW HAMPSHIRE - NH", "NEW JERSEY - NJ", "NEW MEXICO - NM", "NEW YORK - NY", "NORTH CAROLINA - NC", "NORTH DAKOTA - ND", "OHIO - OH", "OKLAHOMA - OK", "OREGON - OR", "PENNSYLVANIA - PA", "RHODE ISLAND - RI", "SOUTH CAROLINA - SC", "SOUTH DAKOTA - SD", "TENNESSEE - TN", "TEXAS - TX", "UTAH - UT", "VERMONT - VT", "VIRGINIA - VA", "WASHINGTON - WA", "WEST VIRGINIA - WV", "WISCONSIN - WI", "WYOMING - WY", "GUAM - GU", "PUERTO RICO - PR", "VIRGIN ISLANDS - VI"}));
		cbxState.setBounds(129, 254, 181, 28);
		contentPane.add(cbxState);
		
		JLabel lblState = new JLabel("State:");
		lblState.setBounds(6, 261, 46, 14);
		contentPane.add(lblState);
		
		JLabel lblZipcode = new JLabel("Zipcode:");
		lblZipcode.setBounds(6, 304, 73, 14);
		contentPane.add(lblZipcode);
		
		JLabel lblPhonenumber = new JLabel("Phone number:");
		lblPhonenumber.setBounds(6, 347, 97, 14);
		contentPane.add(lblPhonenumber);
		
		txtzipcode = new JTextField();
		txtzipcode.setBounds(129, 297, 134, 28);
		contentPane.add(txtzipcode);
		txtzipcode.setColumns(10);
		
		MaskFormatter phoneNum;
		try {
			phoneNum = new MaskFormatter("(###) ###-####");
			txtphonenumber = new JFormattedTextField(phoneNum);
		} catch (ParseException e2) {
			txtphonenumber = new JFormattedTextField();
			e2.printStackTrace();
		}		
		txtphonenumber.setBounds(127, 340, 136, 28);
		contentPane.add(txtphonenumber);
		
		cbxGender = new JComboBox();
		cbxGender.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female"}));
		cbxGender.setBounds(129, 135, 134, 29);
		contentPane.add(cbxGender);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialogBtn = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel?", "Warning", dialogBtn);
				if (dialogResult == 0)
					{
						dispose();
					}
			}
		});
		btnCancel.setBounds(157, 483, 117, 29);
		contentPane.add(btnCancel);
		
		JDateChooser dtcDOB = new JDateChooser();
		dtcDOB.setBounds(129, 95, 130, 28);
		contentPane.add(dtcDOB);
		
		
		this.setLocationRelativeTo(this);
		setVisible(true);
	
		btnAddPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
					//Statement stmt = conn.createStatement();
					
					
					String firstName, lastName, gender, street, city, state, zipcode, phonenumber, finalDOB;
					Date dateofbirth;
					
					//Form checking
					if(txtFirstName.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"First name cannot be empty");
						return;
					}
					else {
						firstName = txtFirstName.getText();
					}
					
					if(txtLastName.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"Last name cannot be empty");
						return;
					}
					else {
						lastName = txtLastName.getText();						
					}
					
					if(dtcDOB.getDate().equals("")) {
						JOptionPane.showMessageDialog(null,"Date of Birth cannot be empty");
						return;
					}
					else {
						java.util.Date birth = dtcDOB.getDate();
						dateofbirth = new Date(birth.getTime());
						Calendar conversion = Calendar.getInstance();
						conversion.setTime(dateofbirth);
						java.util.Date convertedDate = conversion.getTime();
						
						java.text.SimpleDateFormat sdf = 
							     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");

						finalDOB = sdf.format(convertedDate);
						System.out.print(finalDOB);
					}
					
					if(txtstreet.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"Street cannot be empty");
						return;
					}
					else {
						street = txtstreet.getText();						
					}
					
					if(txtcity.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"City cannot be empty");
						return;
					}
					else {
						city = txtcity.getText();						
					}
					
					if(cbxState.getSelectedItem().equals("")) {
						JOptionPane.showMessageDialog(null,"State cannot be empty");
						return;
					}
					else {
						String [] tmpState = cbxState.getSelectedItem().toString().split("-\\s");
						state = tmpState[1];
					}
					
					if(txtzipcode.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"Zip Code cannot be empty");
						return;
					}
					else {
						zipcode = txtzipcode.getText();						
					}
					
					
					String [] tmpText = txtphonenumber.getText().split("\\s+");
					if(tmpText.length > 2) {
						JOptionPane.showMessageDialog(null,"Phone number must be filled out as typical format: (123) 456-7890");
						return;
					}
					else {
						phonenumber = txtphonenumber.getText();	
						System.out.println(phonenumber);
					}
					
					if(cbxGender.getSelectedItem().equals("")) {
						JOptionPane.showMessageDialog(null,"Gender cannot be empty");
						return;
					}
					else {
						gender = cbxGender.getSelectedItem().toString();						
					}
					
					//end of form checking
					String query = "Insert into patientheader (firstname, lastname, dateofbirth, gender, street, city, state, zipcode, phonenumber)" 
							+ "Values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1, firstName);
					pstmt.setString(2, lastName);
					pstmt.setString(3, finalDOB);
					pstmt.setString(4, gender);
					pstmt.setString(5, street);
					pstmt.setString(6, city);
					pstmt.setString(7, state);
					pstmt.setString(8, zipcode);
					pstmt.setString(9, phonenumber);
					
					pstmt.executeUpdate();
					
					/*stmt.executeUpdate("Insert into patientheader (firstname, lastname, dateofbirth, gender, street, city, state, zipcode, phonenumber)" 
							+ "Values ('" + firstName + "', '" + lastName + "', '" + finalDOB + "', '" + gender + "', '" + street + "', '" + city + "', '" + state + "', '" + zipcode + "', '" + 
							phonenumber + "');");*/
					pstmt.closeOnCompletion();
					//stmt.closeOnCompletion();
					
					conn.close();
					
					JOptionPane.showMessageDialog(null,"Patient " + firstName + " " + lastName + " has been added. ");
					
					dispose();
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"There was an error adding this Patient.");
					e1.printStackTrace();
				}
				
			}
		});
		
	}
}