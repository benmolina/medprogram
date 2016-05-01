package medProgram;

import java.sql.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.util.*; 
import javax.swing.*; 
import javax.swing.border.*;

public class MainInterface extends JFrame implements ActionListener { 
	
	JDesktopPane gui = new JDesktopPane();
	
	JMenuBar mb = new JMenuBar();  
	JMenu  ManaSystem,appoViewer,checkIn,medRecord,usersetting,GetHelp,Supplies; 
	JMenuItem  outsystem,patientinfo,patientinfo1,addHospital,dropHospital,addUser,aboutauthor,aboutsystem,usehelp,background,color1,manspace_1, 
 color2,color3, openApptViewer, openCheckIn,mntmOpenMedicalRecord,mntmRemoveUser,openSupplies, mntmTimeManagement,
 mnPayment, mntmOpenPaymentWindow, mntmAddPatient;  
	JMenu backcolor;  
	JPanel jp=new JPanel(); 
	Container cp=getContentPane();
	String username; 
	boolean isLoggedIn = false;
	private JMenuItem mntmAddASupply;
	private JMenuItem mntmViewOrganDonors;
	
	MainInterface(String username) { 
		
		this.username=username; 
		cp.add(mb,"North"); 
		/*
		 * Menu 
		 */
		//System management
		ManaSystem=new JMenu("System Setting");  
		//ManaSystem.setMnemonic('S'); 
		//Check in
		checkIn=new JMenu("Check In"); 
		//Appointment viewer
		appoViewer=new JMenu("Appointment Viewer");   
		//Medical record
		medRecord=new JMenu("Medical Record");
		//Supplies
		Supplies=new JMenu("Supplies");
		//User setting
		usersetting=new JMenu("User Setting");       
		//Help
		GetHelp=new JMenu("Help");     
		/*
		 * Menu items 
		 */
		//Background color in 'System Setting'
		backcolor=new JMenu("Background Color");   
		background=new JMenuItem("Backgrond Color");      
		color1=new JMenuItem("White");      
		color2=new JMenuItem("Gray");      
  		color3=new JMenuItem("Black");
  		openApptViewer=new JMenuItem("Open Appointment Viewer");
  		openCheckIn=new JMenuItem("Open Check In");
  		openSupplies=new JMenuItem("Open Supplies Viewer");
		//background.add(backcolor);     
		backcolor.add(color1);         
		backcolor.add(color2);         
		backcolor.add(color3);   
		
		//Log out in 'System Setting'
		outsystem=new JMenuItem("Log Out");
		
		//Add user
		//adduser=new JMenuItem("AddUser");   
		
		//Space leaved for later function in 'User Setting'
		addUser=new JMenuItem("Add User");
		//Info about author and system
		
		aboutauthor=new JMenuItem("About Author");      
		aboutsystem=new JMenuItem("About System"); 
		
		//User guide
		usehelp=new JMenuItem("User Guide"); 
		
		//Assemble properly
		mb.add(ManaSystem);  
		mb.add(checkIn);   
		mb.add(appoViewer);   
		mb.add(medRecord);
		mb.add(Supplies);
		
		mntmAddPatient = new JMenuItem("Add Patient");
		medRecord.add(mntmAddPatient);
		
		mntmOpenMedicalRecord = new JMenuItem("Open Medical Record");
		medRecord.add(mntmOpenMedicalRecord);
		
		mntmViewOrganDonors = new JMenuItem("View Organ Donors and Patients Needing Donations");
		medRecord.add(mntmViewOrganDonors);
		
		mnPayment = new JMenu("Payment");
		mb.add(mnPayment);
		
		mntmOpenPaymentWindow = new JMenuItem("Open Payment Window");
		mnPayment.add(mntmOpenPaymentWindow);
		mb.add(usersetting);  
		mb.add(GetHelp);
		ManaSystem.add(backcolor);   
		ManaSystem.add(outsystem);
		checkIn.add(openCheckIn);
		appoViewer.add(openApptViewer);
		
		mntmAddASupply = new JMenuItem("Add A Supply");
		Supplies.add(mntmAddASupply);
		Supplies.add(openSupplies);
		
		mntmTimeManagement = new JMenuItem("Open Time Management");
		appoViewer.add(mntmTimeManagement);
		usersetting.add(addUser);    
		
		mntmRemoveUser = new JMenuItem("Remove User");
		usersetting.add(mntmRemoveUser);
		GetHelp.add(aboutauthor);    
		GetHelp.add(aboutsystem);       
		GetHelp.add(usehelp); 
		color1.addActionListener(this);        
		color2.addActionListener(this);     
		color3.addActionListener(this);  
		
		//Add action listener
		outsystem.addActionListener(this);
		addUser.addActionListener(this);
		mntmRemoveUser.addActionListener(this);
		mntmOpenMedicalRecord.addActionListener(this);
		openSupplies.addActionListener(this);
		//addUser.addActionListener(this);       
		aboutauthor.addActionListener(this);      
		aboutsystem.addActionListener(this);   
		usehelp.addActionListener(this);
		openApptViewer.addActionListener(this);
		openCheckIn.addActionListener(this);
		mntmTimeManagement.addActionListener(this);
		mntmOpenPaymentWindow.addActionListener(this);
		mntmAddPatient.addActionListener(this);
		mntmAddASupply.addActionListener(this);
		mntmViewOrganDonors.addActionListener(this);

		
		
		jp.setLayout(new BorderLayout());       
		JLabel label1 = new JLabel();   
		jp.add(label1); 
		JLabel  JL=new JLabel("<html><font color=#4F4F4F size='14'><i>Group 6  Medical Program<br><hr>"  + 
          "</i></font>",SwingConstants.CENTER);  
		jp.add(JL); 
		JScrollPane scrollpane=new JScrollPane(jp);  
		cp.add(scrollpane); 
		setTitle("Group 6  Medical Program");  
		jp.setBackground(Color.lightGray);    
		setSize(1100,600);       
		setVisible(true); 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
		
		this.setLocationRelativeTo(getParent());
	}
	public void actionPerformed(ActionEvent e)  { 
		if (e.getSource()==color3){ 
			jp.setBackground(Color.BLACK);   
		} 
		if (e.getSource()==color2){ 
			jp.setBackground(Color.lightGray);   
		} 
		if (e.getSource()==color1){ 
			jp.setBackground(Color.white);   
		} 
		if (e.getSource()==outsystem){ 
			int dialogBtn = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Warning", dialogBtn);
			if (dialogResult == 0)
				{
					new Login();
					this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
				}
						
		}
		if (e.getSource()==addUser){ 
			new AddUser(username);   
		} 
		if (e.getSource()==aboutauthor){ 
			JOptionPane.showMessageDialog(this,"Created by Yirong Chen, Preston Evans, Robby Fry, Zach Kovach, and Benedit Molina. Copyright 2016");   
		}
		if (e.getSource()==aboutsystem){ 
			JOptionPane.showMessageDialog(this,"Medical Management System. Created by Group Six. Copyright 2016"); 
		} 
		if (e.getSource()==usehelp){ 
			JOptionPane.showMessageDialog(this,"This feature has not been implemented yet.");   
		}

		//CHECK IN
		if (e.getSource()==openCheckIn){ 
			
			Search patient = new Search();
			patient.main();
		} 
   
		//APPONTMENT VIEW
		if (e.getSource()==openApptViewer){ 
			AppointmentViewer av = new AppointmentViewer();
			av.main();
		} 
   
		//MEDICAL RECORD
		if (e.getSource()==mntmOpenMedicalRecord){
			new EditMedRecords();
		} 
		//SUPPLIES
		if (e.getSource()==openSupplies){
			SuppliesViewer supplies = new SuppliesViewer();
			supplies.main();
		}
		if (e.getSource() == mntmRemoveUser) {
			new RemoveUser();
		}
		
		if (e.getSource() == mntmTimeManagement) {
			new TimeManagement();
		}
		
		if (e.getSource() == mntmAddPatient) {
			new AddPatient();
		}
		
		if (e.getSource() == mntmOpenPaymentWindow) {
			new Payment();
		}
		
		if (e.getSource() == mntmAddASupply) {
			new SupplyAdd();
		}
		
		if (e.getSource() == mntmViewOrganDonors) {
			new OrganDonorSystem();
		}
	}

	public static void main(String[]args) { 
		new MainInterface("");   
	} 
	
}