package medProgram;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class OrganDonorSystem extends JFrame {

	private JPanel contentPane;
	
	private static DefaultListModel<String> donor, needDonate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new OrganDonorSystem();
	}

	/**
	 * Create the frame.
	 */
	public OrganDonorSystem() {
		
		donor = new DefaultListModel<String>();
		needDonate = new DefaultListModel<String>();
		
		setTitle("Organ Donor System");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblListOfOrgan = new JLabel("List of Organ Donors");
		lblListOfOrgan.setBounds(13, 6, 135, 16);
		contentPane.add(lblListOfOrgan);
		
		JScrollPane scpOrganDonors = new JScrollPane();
		scpOrganDonors.setBounds(6, 22, 150, 250);
		contentPane.add(scpOrganDonors);
		
		JList<String> lstOrganDonors = new JList<String>();
		lstOrganDonors.setModel(donor);
		scpOrganDonors.setViewportView(lstOrganDonors);
		
		JLabel lblListOfPatients = new JLabel("List of Patients Needing Donation");
		lblListOfPatients.setBounds(220, 6, 215, 16);
		contentPane.add(lblListOfPatients);
		
		JScrollPane scpNeedDonate = new JScrollPane();
		scpNeedDonate.setBounds(250, 22, 150, 250);
		contentPane.add(scpNeedDonate);
		
		JList<String> lstNeedDonate = new JList<String>();
		lstNeedDonate.setModel(needDonate);
		scpNeedDonate.setViewportView(lstNeedDonate);
		
		try {
			
			Connection myConn = DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
			
			String donorQuery = "select concat(ph.firstname, ' ', ph.lastname) as fullname from medprogram.patientheader ph "
					+ "join medprogram.patientdetails pd on ph.patientid = pd.patientid "
					+ "where ph.isactive = 1 and pd.isorgandonor = 1 "
					+ "order by ph.lastname;";
			
			PreparedStatement donorList = myConn.prepareStatement(donorQuery);
			
			ResultSet getDonorList = donorList.executeQuery();
			
			while (getDonorList.next()) {
				String fullName = getDonorList.getString("fullname");
				
				donor.addElement(fullName);
			}
			
			lstOrganDonors.setModel(donor);
			
			donorList.closeOnCompletion();
			
			String needDonateQuery = "select concat(ph.firstname, ' ', ph.lastname) as fullname from medprogram.patientheader ph "
					+ "join medprogram.patientdetails pd on ph.patientid = pd.patientid "
					+ "where ph.isactive = 1 and pd.needsorgdonation = 1";
			
			PreparedStatement needDonateList = myConn.prepareStatement(needDonateQuery);
			
			ResultSet getNeedsDonateList = needDonateList.executeQuery();
			
			while (getNeedsDonateList.next()) {
				String fullName = getNeedsDonateList.getString("fullname");
				
				needDonate.addElement(fullName);
			}
			
			lstNeedDonate.setModel(needDonate);
			
			needDonateList.closeOnCompletion();
			
			myConn.close();
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Unable to load any data. Please make sure that the server is up and try again. If the issue persists, please contact System Admin.");
			ex.printStackTrace();
			return;
		}
		
		setLocationRelativeTo(this);
		setVisible(true);
	}

}
