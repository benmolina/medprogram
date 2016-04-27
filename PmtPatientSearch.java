package medProgram;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.xml.transform.OutputKeys;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.awt.event.ActionEvent;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.awt.Color;
import javax.swing.border.LineBorder;

public class PmtPatientSearch extends JFrame {

	private JPanel contentPane;
	private JTextField txtLastName;
	private JTextField txtFirstName;
	private JTextField txtID;
	private JLabel lblPhoneNumber;
	private JTextField txtPhoneNum;
	private JLabel lblDateOfBirth;
	private JTextField txtDOB;
	private int selection = -1;
	private static DefaultListModel<String> listModel;
	private String baseSQLQuery = "Select patientid, lastname, firstname, dateofbirth, phonenumber From medprogram.patientheader where isactive = 1";
	private JButton btnSearch;
	private JList lstHeaders;
	private String finalString = "";
	private String patientInfo = "";
	private boolean status;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new PmtPatientSearch("");
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "serial", "unchecked" })
	public PmtPatientSearch(String name) {
		setTitle("Patient Lookup");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 814, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(168, 6, 70, 16);
		contentPane.add(lblLastName);
		
		txtLastName = new JTextField();
		txtLastName.setBounds(165, 24, 134, 28);
		contentPane.add(txtLastName);
		txtLastName.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(331, 6, 70, 16);
		contentPane.add(lblFirstName);
		
		txtFirstName = new JTextField();
		txtFirstName.setColumns(10);
		txtFirstName.setBounds(328, 24, 134, 28);
		contentPane.add(txtFirstName);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(6, 6, 19, 16);
		contentPane.add(lblId);
		
		txtID = new JTextField();
		txtID.setColumns(10);
		txtID.setBounds(2, 24, 134, 28);
		contentPane.add(txtID);
		
		lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(657, 6, 98, 16);
		contentPane.add(lblPhoneNumber);
		
		txtPhoneNum = new JTextField();
		txtPhoneNum.setColumns(10);
		txtPhoneNum.setBounds(654, 24, 134, 28);
		contentPane.add(txtPhoneNum);
		
		lblDateOfBirth = new JLabel("Date of Birth");
		lblDateOfBirth.setBounds(495, 6, 98, 16);
		contentPane.add(lblDateOfBirth);
		
		txtDOB = new JTextField();
		txtDOB.setColumns(10);
		txtDOB.setBounds(491, 24, 134, 28);
		contentPane.add(txtDOB);
		
		JList<String> lstPatients = new JList<String>();
		listModel = new DefaultListModel<String>();
		lstPatients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstPatients.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (lstPatients.isSelectionEmpty())
					selection = -1;
				else
					selection = lstPatients.getSelectedIndex();
			}
		});
		lstPatients.setBounds(6, 90, 778, 228);
		contentPane.add(lstPatients);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lstPatients.isSelectionEmpty())
				{
					JOptionPane.showMessageDialog(null,"Please select a patient");
					return;
				}					
				else {
					
					String [] extractedString = lstPatients.getSelectedValue().toString().split("\\s+");
					
					Payment.setValues(extractedString[0], extractedString[2] + " " + extractedString[1]);
					dispose();
					}		
			}
		});
		btnSelect.setBounds(671, 318, 117, 29);
		contentPane.add(btnSelect);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtID.getText().length() > 0)
					baseSQLQuery += " and patientid like '%" + txtID.getText() + "%'";
				if (txtLastName.getText().length() > 0)
					baseSQLQuery += " and lastname like '%" + txtLastName.getText() + "%'";
				if (txtFirstName.getText().length() > 0)
					baseSQLQuery += " and firstname like '%" + txtFirstName.getText() + "%'";
				if (txtPhoneNum.getText().length() > 0)
					baseSQLQuery += " and phonenumber like '%" + txtPhoneNum.getText() + "%'";
				if (txtDOB.getText().length() > 0)
					baseSQLQuery += " and dateofbirth like '%" + txtDOB.getText() + "%'";
				baseSQLQuery += ";";
				System.out.println(baseSQLQuery);
				
				try {
					listModel.clear();
					Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
					//PreparedStatement pstmt = conn.prepareStatement(baseSQLQuery);
					Statement stmt = conn.createStatement();
					ResultSet loadPatients;
								
					loadPatients = stmt.executeQuery(baseSQLQuery);
					
					while (loadPatients.next()) {
						int id = loadPatients.getInt("patientid");
						String firstName = loadPatients.getString("firstname");
						String lastName = loadPatients.getString("lastname");
						Timestamp dob = loadPatients.getTimestamp("dateofbirth");
						String phoneNum = loadPatients.getString("phonenumber");
						
						java.text.SimpleDateFormat sdf = 
								new java.text.SimpleDateFormat("MM-dd-yyyy");
						
						String formattedDOB = sdf.format(dob);
						
						String test = String.format("%1d %41s %36s %45s %33s",id,lastName,firstName,formattedDOB,phoneNum);
						listModel.addElement(test);
					}
					stmt.closeOnCompletion();
					
					
					lstPatients.setModel(listModel);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				baseSQLQuery = "Select patientid, lastname, firstname, dateofbirth, phonenumber From medprogram.patientheader where isactive = 1";
			}
		});
		btnSearch.setBounds(6, 318, 117, 29);
		contentPane.add(btnSearch);
		
		if (name.length() > 0)
		{
			String [] splitName = name.split("//s+");
			if(splitName.length > 1) {
				txtFirstName.setText(splitName[0]);
				txtLastName.setText(splitName[1]);
				btnSearch.doClick();
			}
			else
			{
				txtFirstName.setText(splitName[0]);
				btnSearch.doClick();
			}
			
		}
		
		lstHeaders = new JList <String>();
		lstHeaders.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				lstHeaders.setSelectedIndex(-1);
			}
		});
		lstHeaders.setBorder(new LineBorder(new Color(0, 0, 0)));
		lstHeaders.setLayoutOrientation(JList.VERTICAL_WRAP);
		lstHeaders.setForeground(Color.BLACK);
		lstHeaders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstHeaders.setModel(new AbstractListModel<String>() {
			String[] values = new String[] {"ID                                     Last Name                         First Name                          Date of Birth                      Phone Number"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		lstHeaders.setBounds(6, 73, 778, 16);
		contentPane.add(lstHeaders);
		
		this.setLocationRelativeTo(getParent());
		setVisible(true);
	}

}