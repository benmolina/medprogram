package medProgram;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class List {

	private JFrame frmSearchResultsFor;
	private static DefaultListModel listModel;
	public static Patient database_public;

	public static String Model;
	public static ArrayList<Patient> Patients;
	
	public int index = -1;
	/**
	 * Launch the application.
	 */
	public static void NewScreen(DefaultListModel model, ArrayList<Patient> patients) {
		listModel = model;
		Patients = patients;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					List window = new List();
					window.frmSearchResultsFor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public List() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSearchResultsFor = new JFrame();
		frmSearchResultsFor.setTitle("Search results for " + Search.getFirst_Name() + " " + Search.getLast_Name());
		frmSearchResultsFor.setBounds(100, 100, 715, 370);
		frmSearchResultsFor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSearchResultsFor.getContentPane().setLayout(null);
		frmSearchResultsFor.setLocationRelativeTo(null);
		
		final JList<Object> list = new JList<Object>();
		list.setModel(listModel);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(list.isSelectionEmpty()){
					index = -1;
				}
				else
				{
					index = list.getSelectedIndex();
				}
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(10, 27, 679, 235);
		frmSearchResultsFor.getContentPane().add(list);
		
		JButton btnCheckIn = new JButton("Check-In");
		btnCheckIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if(index != -1)
			{
				Patient selection = Patients.get(index);
				int ID = selection.getID();
				int apptId = selection.getAppointmentId();
				try{
					Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
					Statement stmt = conn.createStatement();
					stmt.executeUpdate("UPDATE appointments SET checkinstatus = 1 WHERE patientid = " + ID + " AND apptid = " + apptId + ";");
					System.out.println("Database updated");
					ResultSet rs;
					rs = stmt.executeQuery("SELECT ap.apptid, ph.patientid,ph.firstname,ph.lastname,ap.appttime,ap.visitreason,st.statusdesc FROM medprogram.patientheader ph JOIN medprogram.appointments ap on ap.patientid = ph.patientid JOIN medprogram.status st on st.statusid = ap.checkinstatus "
							+ "WHERE ap.isdeleted = 0 and ph.firstname like '%" + Search.getFirst_Name() + "%' and ph.lastname like '%" + Search.getLast_Name() + "%'");
					listModel.clear();
					Patients.clear();
					while(rs.next()){
						apptId = rs.getInt("apptid");
						ID = rs.getInt("patientid");
						String first = rs.getString("firstname");
						String last = rs.getString("lastname");
						Timestamp apt_time = rs.getTimestamp("appttime");
						String visit_reason = rs.getString("visitreason");
						String status = rs.getString("statusdesc");
						if(status.equals("Not Checked In"))
						{
							status = "";
						}
						//java.util.Date apptDateTime = new java.util.Date(apt_time.getTime());
						
						java.text.SimpleDateFormat sdf = 
								new java.text.SimpleDateFormat("MM-dd-yyyy HH:mm");
						
						String appt = sdf.format(apt_time);
						
						String test = String.format("%1d %21s %1s %45s %20s %35s",ID,first,last,appt,visit_reason,status);
						listModel.addElement(test);
						Patients.add(new Patient(apptId,ID,first,last,apt_time,visit_reason,status));
					}
					list.setModel(listModel);
					conn.close();
					
				}
				catch (Exception e1)
				{
					System.err.println(e1.getMessage());
				}
			}
			else
			{
				System.out.println("error");
			}
			}
		});
		btnCheckIn.setBounds(10, 273, 154, 47);
		frmSearchResultsFor.getContentPane().add(btnCheckIn);
		
		JButton btnCheckOut = new JButton("Check-Out");
		btnCheckOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(index != -1)
				{
					Patient selection = Patients.get(index);
					int ID = selection.getID();
					int apptId = selection.getAppointmentId();
					try{
						Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
						Statement stmt = conn.createStatement();
						
						stmt.executeUpdate("UPDATE appointments SET checkinstatus = 2 WHERE patientid = " + ID + " AND apptid = " + apptId + ";");
						//Display updated list
						System.out.println("Database updated");
						ResultSet rs;
						rs = stmt.executeQuery("SELECT ap.apptid, ph.patientid,ph.firstname,ph.lastname,ap.appttime,ap.visitreason,st.statusdesc FROM medprogram.patientheader ph JOIN medprogram.appointments ap on ap.patientid = ph.patientid JOIN medprogram.status st on st.statusid = ap.checkinstatus "
								+ "WHERE ap.isdeleted = 0 and ph.firstname like '%" + Search.getFirst_Name() + "%' and ph.lastname like '%" + Search.getLast_Name() + "%'");
						listModel.clear();
						Patients.clear();
						while(rs.next()){
							apptId = rs.getInt("apptid");
							ID = rs.getInt("patientid");
							String first = rs.getString("firstname");
							String last = rs.getString("lastname");
							Timestamp apt_time = rs.getTimestamp("appttime");
							String visit_reason = rs.getString("visitreason");
							String status = rs.getString("statusdesc");
							if(status.equals("Not Checked In"))
							{
								status = "";
							}
							//java.util.Date apptDateTime = new java.util.Date(apt_time.getTime());
							java.text.SimpleDateFormat sdf = 
									new java.text.SimpleDateFormat("MM-dd-yyyy HH:mm");
							
							String appt = sdf.format(apt_time);
							
							String test = String.format("%1d %21s %1s %45s %20s %35s",ID,first,last,appt,visit_reason,status);
							listModel.addElement(test);
							Patients.add(new Patient(apptId,ID,first,last,apt_time,visit_reason,status));
						}
						list.setModel(listModel);
						conn.close();
						
					}
					catch (Exception e1)
					{
						System.err.println(e1.getMessage());
					}
				}
				else
				{
					System.out.println("error");
				}
			}
		});
		btnCheckOut.setBounds(182, 274, 154, 47);
		frmSearchResultsFor.getContentPane().add(btnCheckOut);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSearchResultsFor.dispose();
			}
		});
		btnClose.setBounds(535, 273, 154, 47);
		frmSearchResultsFor.getContentPane().add(btnClose);
		
		JLabel lblIdNumber = new JLabel("ID");
		lblIdNumber.setBounds(10, 11, 58, 14);
		frmSearchResultsFor.getContentPane().add(lblIdNumber);
		
		JLabel lblFullName = new JLabel("Full Name");
		lblFullName.setHorizontalAlignment(SwingConstants.CENTER);
		lblFullName.setBounds(64, 11, 86, 14);
		frmSearchResultsFor.getContentPane().add(lblFullName);
		
		JLabel lblAppointmentTime = new JLabel("Appointment Time");
		lblAppointmentTime.setBounds(249, 11, 160, 14);
		frmSearchResultsFor.getContentPane().add(lblAppointmentTime);
		
		JLabel lblVisitReason = new JLabel("Visit Reason");
		lblVisitReason.setBounds(463, 11, 101, 14);
		frmSearchResultsFor.getContentPane().add(lblVisitReason);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(615, 11, 94, 14);
		frmSearchResultsFor.getContentPane().add(lblStatus);
		
		JButton btnDelete = new JButton("Delete Appointment");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(index != -1)
				{
					Patient selection = Patients.get(index);
					int ID = selection.getID();
					int apptId = selection.getAppointmentId();
					try{
						Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
						Statement stmt = conn.createStatement();
						int dialogBtn = JOptionPane.YES_NO_OPTION;
						int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this entry?", "Warning", dialogBtn);
						if (dialogResult == 1)
							return;
						stmt.executeUpdate("UPDATE appointments set isdeleted=1 where patientid = " + ID + " AND apptid = " + apptId + ";");
						System.out.println("Database updated");
						ResultSet rs;
						rs = stmt.executeQuery("SELECT ap.apptid, ph.patientid,ph.firstname,ph.lastname,ap.appttime,ap.visitreason,st.statusdesc FROM medprogram.patientheader ph JOIN medprogram.appointments ap on ap.patientid = ph.patientid JOIN medprogram.status st on st.statusid = ap.checkinstatus "
								+ "WHERE ap.isdeleted = 0 and ph.firstname like '%" + Search.getFirst_Name() + "%' and ph.lastname like '%" + Search.getLast_Name() + "%'");
						listModel.clear();
						Patients.clear();
						while(rs.next()){
							apptId = rs.getInt("apptid");
							ID = rs.getInt("patientid");
							String first = rs.getString("firstname");
							String last = rs.getString("lastname");
							Timestamp apt_time = rs.getTimestamp("appttime");
							String visit_reason = rs.getString("visitreason");
							String status = rs.getString("statusdesc");
							if(status.equals("Not Checked In"))
							{
								status = "";
							}
							//java.util.Date apptDateTime = new java.util.Date(apt_time.getTime());
							
							java.text.SimpleDateFormat sdf = 
								     new java.text.SimpleDateFormat("MM-dd-yyyy HH:mm");
							
							String appt = sdf.format(apt_time);
							
							String test = String.format("%1d %21s %1s %45s %20s %35s",ID,first,last,appt,visit_reason,status);
							listModel.addElement(test);
							Patients.add(new Patient(apptId,ID,first,last,apt_time,visit_reason,status));
						}
						list.setModel(listModel);
						conn.close();
						
					}
					catch (Exception e1)
					{
						System.err.println(e1.getMessage());
					}
				}
				else
				{
					System.out.println("error");
				}
				}
		});
		btnDelete.setBounds(357, 273, 154, 47);
		frmSearchResultsFor.getContentPane().add(btnDelete);
	}
}
