package medProgram;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.util.List;

public class Payment extends JFrame {

	private JPanel contentPane;
	private static JTextField txtPatientID;
	private static JTextField txtPatientName;
	private JTextField txtDiscAmt;
	private List<String> invoiceList;

	/**
	 * Launch the application.
	 */
	public static void main() {
		new Payment();
	}

	/**
	 * Create the frame.
	 */
	public Payment() {
		invoiceList = new ArrayList<String>();
		setTitle("Payment");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615, 571);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Button for reseting window
		JButton reset = new JButton("Reset All");
		reset.setBounds(0, 493, 88, 29);
		contentPane.add(reset);
		
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {

				int dialogBtn = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset everything?", "Warning", dialogBtn);
				if (dialogResult == 0) {
					dispose();
					new Payment();
				}
				
			}
		});
		
		//Button for submitting
		JButton submit = new JButton("Submit");
		submit.setBounds(502, 493, 88, 29);
		contentPane.add(submit);
		
		JPanel pnlPatientInfo = new JPanel();
		pnlPatientInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlPatientInfo.setBounds(6, 6, 420, 91);
		contentPane.add(pnlPatientInfo);
		pnlPatientInfo.setLayout(null);
		
		JLabel lblPatient = new JLabel("Patient ID:");
		lblPatient.setBounds(6, 12, 75, 16);
		pnlPatientInfo.add(lblPatient);
		
		txtPatientID = new JTextField();
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
						
						Statement myStmt = myConn.createStatement();
						String query = "select firstname, lastname from medprogram.patientheader where patientid = ?";
						PreparedStatement pstmt = myConn.prepareStatement(query);
						pstmt.setInt(1, id);
						ResultSet myRes;
						
						myRes = pstmt.executeQuery();						
						
						while(myRes.next()){
							firstName = myRes.getString("firstname");
							lastName = myRes.getString("lastname");
							}
						
						myStmt.closeOnCompletion();
						
						if (firstName.equals("") || lastName.equals(""))
						{
							JOptionPane.showMessageDialog(null, "There was an error with patient lookup. Please try again later. If the issue persists, please contact Customer Service.");
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
		txtPatientID.setBounds(75, 6, 55, 28);
		pnlPatientInfo.add(txtPatientID);
		txtPatientID.setColumns(10);
		
		JLabel lblPatientName = new JLabel("Patient Name:");
		lblPatientName.setBounds(175, 12, 88, 16);
		pnlPatientInfo.add(lblPatientName);
		
		txtPatientName = new JTextField();
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
								PmtPatientSearch pmt = new PmtPatientSearch(fullName[0]);
							}
							else 
							{
								PmtPatientSearch pmt = new PmtPatientSearch(fullName[0] + " " + fullName[1]);
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
		txtPatientName.setBounds(265, 6, 150, 28);
		pnlPatientInfo.add(txtPatientName);
		txtPatientName.setColumns(10);
				
		JButton btnSelectPatient = new JButton("Select Patient");
		btnSelectPatient.setBounds(298, 56, 117, 29);
		pnlPatientInfo.add(btnSelectPatient);
		
		JButton btnQuickPayment = new JButton("Quick Payment");
		btnQuickPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtPatientID.getText().length() < 1 || txtPatientName.getText().length() < 1)
				{
					JOptionPane.showMessageDialog(null, "Both fields must be filled out in order to make a quick payment");
					return;
				}
				else 
				{
					ConfirmPayment cp = new ConfirmPayment(Integer.parseInt(txtPatientID.getText()), null, 0);
				}
			}
		});
		btnQuickPayment.setBounds(6, 56, 126, 29);
		pnlPatientInfo.add(btnQuickPayment);
		
		JPanel pnlItemInfo = new JPanel();
		pnlItemInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlItemInfo.setBounds(6, 109, 578, 195);
		contentPane.add(pnlItemInfo);
		pnlItemInfo.setLayout(null);
		
		JTextField txtItemId = new JTextField();
		
		txtItemId.setBounds(2, 30, 65, 28);
		pnlItemInfo.add(txtItemId);
		txtItemId.setColumns(10);
		
		//Supply and Service Label
		JLabel lblSupplyID = new JLabel("ID");
		lblSupplyID.setBounds(28, 15, 18, 16);
		pnlItemInfo.add(lblSupplyID);
		JTextField supplyServiceText = new JTextField(20);
		supplyServiceText.setBounds(66, 30, 254, 28);
		pnlItemInfo.add(supplyServiceText);
		
		JLabel lblSupplyserviceDescription = new JLabel("Supply/Service Description");
		lblSupplyserviceDescription.setBounds(108, 15, 176, 16);
		pnlItemInfo.add(lblSupplyserviceDescription);
		
		//Amount of supplies Label
		JLabel lblAmt = new JLabel("Amount");
		lblAmt.setBounds(340, 15, 50, 16);
		pnlItemInfo.add(lblAmt);
		JTextField NumberText = new JTextField(18);
		NumberText.setBounds(320, 30, 91, 28);
		pnlItemInfo.add(NumberText);
		NumberText.setText("1");
		JTextField PriceText = new JTextField(25);
		PriceText.setBounds(412, 30, 75, 28);
		pnlItemInfo.add(PriceText);
		
		//Price Label
		JLabel Price_1 = new JLabel("Price");
		Price_1.setBounds(432, 15, 34, 16);
		pnlItemInfo.add(Price_1);
		
		JLabel lblDiscount = new JLabel("Discount");
		lblDiscount.setBounds(499, 15, 61, 16);
		pnlItemInfo.add(lblDiscount);
		
		txtDiscAmt = new JTextField();
		txtDiscAmt.setText("0");
		txtDiscAmt.setBounds(487, 30, 85, 28);
		pnlItemInfo.add(txtDiscAmt);
		txtDiscAmt.setColumns(10);
		
		//Button for adding supply/service
		JButton add = new JButton("Add");
		add.setBounds(499, 69, 75, 29);
		pnlItemInfo.add(add);
		
		JButton btnResetItem = new JButton("Reset");
		btnResetItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtItemId.setText("");
				supplyServiceText.setText("");
				NumberText.setText("1");
				PriceText.setText("");
				txtDiscAmt.setText("0");
			}
		});
		btnResetItem.setBounds(484, 160, 88, 29);
		pnlItemInfo.add(btnResetItem);
		
		JPanel pnlSavedItems = new JPanel();
		pnlSavedItems.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlSavedItems.setBounds(6, 328, 578, 163);
		contentPane.add(pnlSavedItems);
		
		//Total Amount Label
		JLabel TotalAmount_1 = new JLabel("Total Amount");
		TotalAmount_1.setBounds(193, 498, 90, 16);
		contentPane.add(TotalAmount_1);
		JTextField amountText = new JTextField(20);
		amountText.setBounds(284, 492, 80, 28);
		contentPane.add(amountText);
		amountText.setEditable(false);
		amountText.setText("0.00");
		
		JLabel lblItemsOnInvoice = new JLabel("Items On Invoice");
		lblItemsOnInvoice.setBounds(250, 310, 106, 16);
		contentPane.add(lblItemsOnInvoice);
		
		add.addActionListener(new ActionListener() {
			@Override
	         public void actionPerformed(ActionEvent evt) {
				String idText = txtItemId.getText();
				if (idText.length() < 1)
					idText = "0";
				
				String SuSeText = supplyServiceText.getText();
				if (SuSeText.length() < 1)
				{
					JOptionPane.showMessageDialog(null, "An item must have a description");
					return;
				}
				
				String amount = amountText.getText();

				String NumText = NumberText.getText();
				if (NumText.length() < 1)
				{
					JOptionPane.showMessageDialog(null, "A quantity must be entered");
					return;
				}
				
				String PText = PriceText.getText();
				if (PText.length() < 1)
				{
					JOptionPane.showMessageDialog(null, "A price must be entered");
					return;
				}
				
				String discText = txtDiscAmt.getText();
				if (discText.length() < 1)
					discText = "0";
				try {
					float NumberOfSupplies = Float.parseFloat(NumText);
				    float InitialAmount = Float.parseFloat(amount);
				    float Price = Float.parseFloat(PText);
				    float discount = Float.parseFloat(discText);
					
				    float SubAmount = NumberOfSupplies * Price;
				    
					float TotalAmount = (InitialAmount + SubAmount) - discount;
					
					int id = Integer.parseInt(idText);
					
					String textTotal = String.format("%.2f", TotalAmount);
					
		            amountText.setText(textTotal);
		            txtItemId.setText(null);
		            supplyServiceText.setText(null);
		            NumberText.setText("1");
		            PriceText.setText(null);
		            txtDiscAmt.setText(null);
		           
		            String addedItem = id + "         " + SuSeText.replace(" ", "") + "         Amount: " + NumText + "         Price: " + String.format("%.2f", Price * Float.parseFloat(NumText))
		            + "         Disc: " + String.format("%.2f", discount);
		            JTextField NewSupplyService = new JTextField();
		            NewSupplyService.setText(addedItem);
		            NewSupplyService.setEditable(false);
		            
		            invoiceList.add(addedItem);
		            
		            pnlSavedItems.add(NewSupplyService, BorderLayout.SOUTH);
		            
		            pnlSavedItems.validate();
		            pnlSavedItems.repaint();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Unable to add item. Please make sure all quantities and prices have only numbers.");
					return;
				}
			    
	           
	         }
	         
		});
		btnSelectPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				PmtPatientSearch pmt = new PmtPatientSearch("");

				
			}
		});
		
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (txtPatientID.getText().length() > 0 && invoiceList.size() > 0) 
				{			
					int finalId = Integer.parseInt(txtPatientID.getText());
					float total = Float.parseFloat(amountText.getText());
					ConfirmPayment cp = new ConfirmPayment(finalId, invoiceList, total);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please make sure a patient is selected and that there is at least one item for the invoice.");
					return;
				}
				
			}
		});

		setLocationRelativeTo(this);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtPatientID, txtPatientName, btnSelectPatient, txtItemId, supplyServiceText, NumberText, PriceText, txtDiscAmt, add, reset, submit}));
		setVisible(true);
		
		txtItemId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (txtItemId.getText().length() < 1) 
				{
					btnResetItem.doClick();
				}
					
				else
				{
					int id = -1;
					String itemDescription = "";
					String amount = "";
					String price = "";
					String discount = "";
					try {
						id = Integer.parseInt(txtItemId.getText());
					} catch (Exception idExcept) {
						JOptionPane.showMessageDialog(null, "Please enter only numbers for the ID");
						return;
					}
					
					try {
						Connection myConn = DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
						
						//Statement myStmt = myConn.createStatement();
						String query = "select supplydesc, supplyprice from medprogram.supplies where "
								+ "supplyid = ?";
						PreparedStatement pstmt = myConn.prepareStatement(query);
						pstmt.setInt(1, id);
						ResultSet myRes;
						
						myRes = pstmt.executeQuery();						
						
						while(myRes.next()){
							itemDescription = myRes.getString("supplydesc");
							price = myRes.getString("supplyprice");
							}
						
						pstmt.closeOnCompletion();
						
						if (itemDescription.equals("") || price.equals("") || id == -1)
						{
							JOptionPane.showMessageDialog(null, "There was an error with item lookup. Please try again later. If the issue persists, please contact Customer Service.");
							return;	
						}
						else
						{
							supplyServiceText.setText(itemDescription);
							if (id != 0)
								supplyServiceText.setEnabled(false);
							else
								supplyServiceText.setEnabled(true);
							PriceText.setText(price);
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
	}
	
	public static void setValues(String id, String name) {
		txtPatientID.setText(id);
		txtPatientName.setText(name);
	}
}