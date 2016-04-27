package medProgram;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;



public class Records {

	private JFrame frmMedicalRecord;
	private static Patient thispatient;
	private static ArrayList<String> Appointments_Array;

	/**
	 * Launch the application.
	 */
	public static void newScreen(Patient patient, ArrayList<String> appointments) {
		thispatient = patient;
		Appointments_Array = appointments;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Records window = new Records();
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
	public Records() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMedicalRecord = new JFrame();
		frmMedicalRecord.setTitle("Medical Record");
		frmMedicalRecord.setBounds(100, 100, 564, 400);
		frmMedicalRecord.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmMedicalRecord.getContentPane().setLayout(null);
		
		JTextPane Name = new JTextPane();
		Name.setEditable(false);
		Name.setBounds(10, 27, 292, 20);
		Name.setText(thispatient.getFirst() + " " + thispatient.getLast());
		frmMedicalRecord.getContentPane().add(Name);
		
		String test = "";
		Object array[] = Appointments_Array.toArray();
		for(int i = 0; i < array.length; i++)
		{
			test = test + array[i] + "\n";
		}
		
		JTextPane Allergies = new JTextPane();
		Allergies.setEditable(false);
		Allergies.setBounds(312, 181, 224, 53);
		Allergies.setText(thispatient.getAllergy());
		frmMedicalRecord.getContentPane().add(Allergies);
		
		JTextPane Notes = new JTextPane();
		Notes.setEditable(false);
		Notes.setBounds(312, 260, 224, 53);
		Notes.setText(thispatient.getNotes());
		frmMedicalRecord.getContentPane().add(Notes);
		
		JTextPane Info = new JTextPane();
		Info.setEditable(false);
		Info.setBounds(10, 77, 292, 236);
		java.text.SimpleDateFormat sdf = 
				new java.text.SimpleDateFormat("MM-dd-yyyy");
		String birth = sdf.format(thispatient.getBirth());
		String info = "Birthdate: " + birth + "\n\n" +
					  "Address:   " + thispatient.getStreet() + " " + thispatient.getAptNumber()+ " "+ thispatient.getState() + "," + thispatient.getZip() + "\n\n" +
					  "Phone:     " + thispatient.getPhone() + "\n\n" +
					  "Insurance: " + thispatient.getInsurance() + "\n\n" + thispatient.getInsuranceInfo();
		String birth_out = "Birthdate: " + birth;
		String address_out = "Address:   " + thispatient.getStreet() + " " + thispatient.getAptNumber()+ " "+ thispatient.getState() + "," + thispatient.getZip();
		String phone_out = "Phone:     " + thispatient.getPhone();
		String insurance_out =  "Insurance: " + thispatient.getInsurance();
		String insurance_info_out = thispatient.getInsuranceInfo();
		Info.setText(info);
		frmMedicalRecord.getContentPane().add(Info);
		
		JLabel lblPatientInformation = new JLabel("Patient Name:");
		lblPatientInformation.setBounds(10, 11, 180, 14);
		frmMedicalRecord.getContentPane().add(lblPatientInformation);
		
		JLabel lblPatientInformation_1 = new JLabel("Patient Information:");
		lblPatientInformation_1.setBounds(10, 64, 180, 14);
		frmMedicalRecord.getContentPane().add(lblPatientInformation_1);
		
		JLabel lblAppointments = new JLabel("Appointments:");
		lblAppointments.setBounds(312, 11, 218, 14);
		frmMedicalRecord.getContentPane().add(lblAppointments);
		
		JLabel lblAllergies = new JLabel("Allergies:");
		lblAllergies.setBounds(312, 165, 224, 14);
		frmMedicalRecord.getContentPane().add(lblAllergies);
		
		JLabel lblDoctorNotes = new JLabel("Doctor Notes:");
		lblDoctorNotes.setBounds(312, 245, 224, 14);
		frmMedicalRecord.getContentPane().add(lblDoctorNotes);
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setBounds(312, 27, 46, 20);
		frmMedicalRecord.getContentPane().add(lblTime);
		
		JLabel lblReason = new JLabel("Reason:");
		lblReason.setBounds(484, 27, 62, 20);
		frmMedicalRecord.getContentPane().add(lblReason);
		JTextPane Apt = new JTextPane();
		Apt.setEditable(false);
		Apt.setBounds(312, 56, 226, 98);
		frmMedicalRecord.getContentPane().add(Apt);
		Apt.setText(test);
		JScrollPane scrollPane = new JScrollPane(Apt);
		scrollPane.setBounds(312, 54, 228, 100);
		frmMedicalRecord.getContentPane().add(scrollPane);
		//Add a button for exporting a patient's medical record
		JButton  txtExport = new JButton("Export");
		txtExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userHomeFolder = System.getProperty("user.home")+"/Desktop";
				String filename = String.format("%s%s_Medical_Record.txt",thispatient.getFirst(),thispatient.getLast());
				File textFile = new File(userHomeFolder, filename);
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		        Date today = Calendar.getInstance().getTime();
		        String reportDate = df.format(today);
				  BufferedWriter out;
				try {
					out = new BufferedWriter(new FileWriter(textFile));
					out.write("Patient Name: "+thispatient.getFirst() + " " + thispatient.getLast()+"\n\n");
					out.newLine();
					out.write(birth_out);
					out.newLine();
					out.write(address_out);
					out.newLine();
					out.write(phone_out);
					out.newLine();
					out.write(insurance_out);
					out.newLine();
					out.write(insurance_info_out);
					out.newLine();
					out.write("Allergies: "+thispatient.getAllergy()+"\n\n");
					out.newLine();
					out.write("Doctor Notes: "+thispatient.getNotes()+"\n\n");
					out.newLine();
					out.write("Exporting time: "+reportDate+"\n");
					out.close();
					JOptionPane.showMessageDialog(null, "The medical record has been exported to Desktop.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		txtExport.setBounds(447, 325 , 89, 23);
		frmMedicalRecord.getContentPane().add(txtExport);
		
	}
}
