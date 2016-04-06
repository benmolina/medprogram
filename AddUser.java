package medProgram;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class AddUser extends JFrame {

	private JPanel contentPane;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtUsername;
	private JPasswordField pwfPassword;
	private JPasswordField pwfConfirm;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new AddUser("");
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AddUser frame = new AddUser("");
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * Create the frame.
	 */
	public AddUser(String username) {
		
		setTitle("Add a User");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(6, 17, 73, 16);
		contentPane.add(lblFirstName);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(91, 11, 134, 28);
		contentPane.add(txtFirstName);
		txtFirstName.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(6, 57, 73, 16);
		contentPane.add(lblLastName);
		
		txtLastName = new JTextField();
		txtLastName.setBounds(91, 51, 134, 28);
		contentPane.add(txtLastName);
		txtLastName.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(6, 97, 73, 16);
		contentPane.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(91, 91, 134, 28);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblRole = new JLabel("Role");
		lblRole.setBounds(6, 137, 61, 16);
		contentPane.add(lblRole);
		
		JComboBox cbxRole = new JComboBox();
		cbxRole.setBounds(91, 133, 134, 27);
		contentPane.add(cbxRole);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(6, 177, 61, 16);
		contentPane.add(lblPassword);
		
		pwfPassword = new JPasswordField();
		pwfPassword.setBounds(91, 172, 134, 28);
		contentPane.add(pwfPassword);
		
		JLabel lblConfirm = new JLabel("Confirm");
		lblConfirm.setBounds(6, 217, 61, 16);
		contentPane.add(lblConfirm);
		
		pwfConfirm = new JPasswordField();
		pwfConfirm.setBounds(91, 212, 134, 28);
		contentPane.add(pwfConfirm);
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.setBounds(6, 280, 117, 29);
		contentPane.add(btnAddUser);
		
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
		btnCancel.setBounds(161, 280, 117, 29);
		contentPane.add(btnCancel);
		
		this.setLocationRelativeTo(this);
		setVisible(true);
		
		try {
			Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
			Statement stmt = conn.createStatement();
			ResultSet loadRoles;
			loadRoles = stmt.executeQuery("Select roledesc from medprogram.userroles;");
			while (loadRoles.next()) {
				String tmp = loadRoles.getString("roledesc");
				cbxRole.addItem(tmp);
			}
			stmt.closeOnCompletion();
		} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
					Statement stmt = conn.createStatement();
					
					int getId = -1;
					int numOfUsernames = 0;
					String firstName, lastName, username, password, confirm;
					
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
					
					if(txtUsername.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"Username cannot be empty");
						return;
					}
					else {
						username = txtUsername.getText();
					}
					
					password = new String(pwfPassword.getPassword());					
					if(password.equals("") || password.length() <= 5) {
						JOptionPane.showMessageDialog(null,"Password must be at least six characters");
						return;
					}
					
					confirm = new String(pwfConfirm.getPassword());
					if(!(password.equals(confirm))) {
						JOptionPane.showMessageDialog(null,"The confirm password must match the desired password");
						return;
					}
					
					ResultSet id = stmt.executeQuery("Select roleid from medprogram.userroles where roledesc = '" +
					cbxRole.getItemAt(cbxRole.getSelectedIndex()) + "';");
					
					while(id.next()) {
						getId = id.getInt("roleid");
					}
					
					stmt.closeOnCompletion();
					
					if (getId == -1) {
						JOptionPane.showMessageDialog(null,"This role does not exist. Contact your system administrators " + 
					"for assistance");
						return;
					}
					
					ResultSet getUsername = stmt.executeQuery("Select username from medprogram.user where username = '" + username 
							+ "';");
					
					while(getUsername.next()) {
						numOfUsernames += 1;
					}
					
					stmt.closeOnCompletion();
					
					if (numOfUsernames > 0) {
						JOptionPane.showMessageDialog(null,"This username is already in use. Please select another username");
						return;
					}
					
					stmt.executeUpdate("Insert into user (username, password, firstname, lastname, userrole)" 
							+ "Values ('" + username + "', '" + password + "', '" + firstName + "', '" + lastName + "', " + 
							getId + ");");
					
					stmt.closeOnCompletion();
					
					conn.close();
					
					JOptionPane.showMessageDialog(null,"User " + username + " has been added as a " + cbxRole.getItemAt(cbxRole.getSelectedIndex()));
					
					dispose();
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"There was an error adding this user. Please try again later");
					e1.printStackTrace();
				}
				
			}
		});
		
	}
}