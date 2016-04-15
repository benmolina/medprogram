package medProgram;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.ScrollPane;


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
		frmMedicalRecord.setBounds(100, 100, 564, 365);
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
		//Add a button for exporting medical record
		JButton  txtExport = new JButton("Export");
		txtExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 String fileName="output.txt";
				  BufferedWriter out;
				try {
					out = new BufferedWriter(new FileWriter(fileName));
					out.write("Patient Name: "+thispatient.getFirst() + " " + thispatient.getLast()+"\n\n");
					out.write(info+"\n\n");
					out.write("Allergies: "+thispatient.getAllergy()+"\n\n");
					out.write("Doctor Notes: "+thispatient.getNotes()+"\n");
										out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
				
			}
		});
		txtExport.setBounds(447, 325 , 89, 23);
		frmMedicalRecord.getContentPane().add(txtExport);	
	}
}
