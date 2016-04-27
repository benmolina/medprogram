package medProgram;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TimeManagement extends JFrame {

	private JPanel contentPane;
	private int selection = -1;
	private static DefaultListModel<String> listModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new TimeManagement();
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TimeManagement frame = new TimeManagement();
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
	public TimeManagement() {
		setTitle("Time Management");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 260, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList<String> lstDocs = new JList<String>();
		lstDocs.setBounds(6, 57, 233, 166);
		contentPane.add(lstDocs);
		listModel = new DefaultListModel<String>();
		lstDocs.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (lstDocs.isSelectionEmpty())
					selection = -1;
				else
					selection = lstDocs.getSelectedIndex();
			}
		});
		lstDocs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		try {
			Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
			Statement stmt = conn.createStatement();
			ResultSet loadActiveUsers;
						
			loadActiveUsers = stmt.executeQuery("Select CONCAT(u.firstname, ' ', u.lastname) as 'doctor', s.statusdesc " 
					+ "from medprogram.user u "
					+ "join medprogram.status s on u.userstatus = s.statusid where u.isactive = 1" 
					+ " and u.userrole = 2;");
			
			while (loadActiveUsers.next()) {
				String doctor = loadActiveUsers.getString("doctor");
				String status = loadActiveUsers.getString("statusdesc");
				String concatDocStatus = String.format("%-20s %s",doctor,status);
				listModel.addElement(concatDocStatus);
			}
			stmt.closeOnCompletion();
			
			lstDocs.setModel(listModel);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		JLabel lblDoctor = new JLabel("Doctor");
		lblDoctor.setBounds(6, 29, 61, 16);
		contentPane.add(lblDoctor);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(121, 29, 61, 16);
		contentPane.add(lblStatus);
		
		JButton btnInAppt = new JButton("In Appt.");
		btnInAppt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
					Statement stmt = conn.createStatement();
					
					int id = -1;
					ResultSet changeStatus;
					String [] splitDocName = lstDocs.getSelectedValue().split(" ");
					String query = "Select u.userid from medprogram.user u "
							+ "where u.firstname = ? and u.lastname = ? and u.userrole = 2;";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1,splitDocName[0]);
					pstmt.setString(2,splitDocName[1]);
					changeStatus = pstmt.executeQuery();
					
					while(changeStatus.next()) {
						id = changeStatus.getInt("userid");
					}
					
					if (id == -1) {
						return;
					}
					
					stmt.executeUpdate("UPDATE user SET userstatus = 5 where userid = " + id + ";");
					listModel.clear();
					
					ResultSet loadActiveUsers;
								
					loadActiveUsers = stmt.executeQuery("Select CONCAT(u.firstname, ' ', u.lastname) as 'doctor', s.statusdesc " 
							+ "from medprogram.user u "
							+ "join medprogram.status s on u.userstatus = s.statusid where u.isactive = 1 " 
							+ "and u.userrole = 2;");
					
					while (loadActiveUsers.next()) {
						String doctor = loadActiveUsers.getString("doctor");
						String status = loadActiveUsers.getString("statusdesc");
						String concatDocStatus = String.format("%-20s %s",doctor,status);
						listModel.addElement(concatDocStatus);
					}
					stmt.closeOnCompletion();
					
					lstDocs.setModel(listModel);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnInAppt.setBounds(6, 235, 117, 29);
		contentPane.add(btnInAppt);
		
		JButton btnFinishingUp = new JButton("Finishing Up");
		btnFinishingUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
					Statement stmt = conn.createStatement();
					
					int id = -1;
					ResultSet changeStatus;
					String [] splitDocName = lstDocs.getSelectedValue().split(" ");
					String query = "Select u.userid from medprogram.user u "
							+ "where u.firstname = ? and u.lastname = ? and u.userrole = 2;";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1,splitDocName[0]);
					pstmt.setString(2,splitDocName[1]);
					changeStatus = pstmt.executeQuery();
					
					while(changeStatus.next()) {
						id = changeStatus.getInt("userid");
					}
					
					if (id == -1) {
						return;
					}
					
					stmt.executeUpdate("UPDATE user SET userstatus = 6 where userid = " + id + ";");
					listModel.clear();
					
					ResultSet loadActiveUsers;
								
					loadActiveUsers = stmt.executeQuery("Select CONCAT(u.firstname, ' ', u.lastname) as 'doctor', s.statusdesc " 
							+ "from medprogram.user u "
							+ "join medprogram.status s on u.userstatus = s.statusid where u.isactive = 1" 
							+ " and u.userrole = 2;");
					
					while (loadActiveUsers.next()) {
						String doctor = loadActiveUsers.getString("doctor");
						String status = loadActiveUsers.getString("statusdesc");
						String concatDocStatus = String.format("%-20s %s",doctor,status);
						listModel.addElement(concatDocStatus);
					}
					stmt.closeOnCompletion();
					
					lstDocs.setModel(listModel);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnFinishingUp.setBounds(122, 235, 117, 29);
		contentPane.add(btnFinishingUp);
		
		JButton btnAvailable = new JButton("Available");
		btnAvailable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
					Statement stmt = conn.createStatement();
					
					int id = -1;
					ResultSet changeStatus;
					String [] splitDocName = lstDocs.getSelectedValue().split(" ");
					String query = "Select u.userid from medprogram.user u "
							+ "where u.firstname = ? and u.lastname = ? and u.userrole = 2;";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1,splitDocName[0]);
					pstmt.setString(2,splitDocName[1]);
					changeStatus = pstmt.executeQuery();
					while(changeStatus.next()) {
						id = changeStatus.getInt("userid");
					}
					
					if (id == -1) {
						return;
					}
					
					stmt.executeUpdate("UPDATE user SET userstatus = 4 where userid = " + id + ";");
					listModel.clear();
					
					ResultSet loadActiveUsers;
								
					loadActiveUsers = stmt.executeQuery("Select CONCAT(u.firstname, ' ', u.lastname) as 'doctor', s.statusdesc " 
							+ "from medprogram.user u "
							+ "join medprogram.status s on u.userstatus = s.statusid where u.isactive = 1" 
							+ " and u.userrole = 2;");
					
					while (loadActiveUsers.next()) {
						String doctor = loadActiveUsers.getString("doctor");
						String status = loadActiveUsers.getString("statusdesc");
						String concatDocStatus = String.format("%-20s %s",doctor,status);
						listModel.addElement(concatDocStatus);
					}
					stmt.closeOnCompletion();
					
					lstDocs.setModel(listModel);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAvailable.setBounds(63, 276, 117, 29);
		contentPane.add(btnAvailable);
		
		this.setLocationRelativeTo(this);
		setVisible(true);
	}
}