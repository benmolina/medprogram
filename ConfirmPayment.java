package medProgram;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import com.toedter.calendar.JDateChooser;

public class ConfirmPayment extends JFrame {

	private JPanel contentPane;
	private JTextField txtRemBalance;
	private JTextField txtPaidAmt;
	private JTextField txtPmtMethod;
	private JTextField txtConfirm;
	private int patientID;
	private List<String> invoiceDetails;
	private float totalBalance = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new ConfirmPayment(-1, null, 0);
	}

	/**
	 * Create the frame.
	 */
	public ConfirmPayment(int id, List<String> items, float total) {
		patientID = id;
		invoiceDetails = items;
		totalBalance += total;
		setTitle("Confirm Payment");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 525, 230);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
		btnCancel.setBounds(6, 173, 117, 29);
		contentPane.add(btnCancel);
		
		JButton btnSubmitPayment = new JButton("Submit Payment");
		btnSubmitPayment.setBounds(393, 173, 126, 29);
		contentPane.add(btnSubmitPayment);
		
		JPanel pnlConfirm = new JPanel();
		pnlConfirm.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlConfirm.setBounds(6, 6, 513, 155);
		contentPane.add(pnlConfirm);
		pnlConfirm.setLayout(null);
		
		JLabel lblRemainingBalance = new JLabel("Remaining Balance");
		lblRemainingBalance.setBounds(283, 125, 122, 16);
		pnlConfirm.add(lblRemainingBalance);
		
		txtRemBalance = new JTextField();
		txtRemBalance.setEditable(false);
		txtRemBalance.setBounds(403, 119, 105, 28);
		pnlConfirm.add(txtRemBalance);
		txtRemBalance.setColumns(10);
		
		try {
			

			Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
			//Statement stmt = conn.createStatement();
			
			ResultSet getBalance;
			
			//getBalance = stmt.executeQuery("select max(pmtbalance) as 'balance' from medprogram.paymentheader where"
					//+ " patientid = " + id + " and pmtdate in (select max(pmtdate) from medprogram.paymentheader);");
			String query = "select pmtbalance from medprogram.paymentheader where patientid = ? and "
					+ "pmtheaderid in (select max(pmtheaderid) from medprogram.paymentheader);";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			getBalance = pstmt.executeQuery();
			while(getBalance.next())
			{
				totalBalance += getBalance.getFloat("pmtbalance");
				System.out.println(totalBalance);
			}
			pstmt.closeOnCompletion();
			//stmt.closeOnCompletion();
			
			conn.close();
			
			
			String testing = String.format("%.2f", totalBalance);
			System.out.println(testing);
			txtRemBalance.setText(testing);
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		JLabel lblAmountPaid = new JLabel("Amount Paid");
		lblAmountPaid.setBounds(6, 6, 80, 16);
		pnlConfirm.add(lblAmountPaid);
		
		txtPaidAmt = new JTextField();
		txtPaidAmt.setBounds(2, 23, 89, 28);
		pnlConfirm.add(txtPaidAmt);
		txtPaidAmt.setColumns(10);
		
		JLabel lblPaymentMethod = new JLabel("Payment Method");
		lblPaymentMethod.setBounds(120, 6, 105, 16);
		pnlConfirm.add(lblPaymentMethod);
		
		txtPmtMethod = new JTextField();
		txtPmtMethod.setBounds(117, 23, 112, 28);
		pnlConfirm.add(txtPmtMethod);
		txtPmtMethod.setColumns(10);
		
		JLabel lblConfirmation = new JLabel("Confirmation");
		lblConfirmation.setBounds(255, 6, 89, 16);
		pnlConfirm.add(lblConfirmation);
		
		txtConfirm = new JTextField();
		txtConfirm.setEnabled(true);
		txtConfirm.setEditable(true);
		txtConfirm.setToolTipText("Enter information that reflects payment method, such as last four numbers of a credit card, policy number, etc.");
		txtConfirm.setText("");
		txtConfirm.setBounds(253, 23, 89, 28);
		pnlConfirm.add(txtConfirm);
		txtConfirm.setColumns(10);
		
		JLabel lblDate = new JLabel("Date of Payment");
		lblDate.setBounds(365, 6, 112, 16);
		pnlConfirm.add(lblDate);
		
		JLabel lblComments = new JLabel("Comments");
		lblComments.setBounds(6, 65, 68, 16);
		pnlConfirm.add(lblComments);
		
		JTextArea txtComments = new JTextArea();
		txtComments.setLineWrap(true);
		txtComments.setBounds(6, 85, 265, 62);
		pnlConfirm.add(txtComments);
		
		JDateChooser dtcDateOfPayment = new JDateChooser();
		dtcDateOfPayment.setBounds(364, 23, 123, 28);
		dtcDateOfPayment.setDate(new java.util.Date());
		dtcDateOfPayment.setEnabled(false);
		pnlConfirm.add(dtcDateOfPayment);
		
		btnSubmitPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					
					float amountPaid = Float.parseFloat(txtPaidAmt.getText());
					String method = txtPmtMethod.getText();
					String confirmation = txtConfirm.getText();
					
					java.util.Date chosenDate = dtcDateOfPayment.getDate();
					Calendar convertDate = Calendar.getInstance();
					convertDate.setTime(chosenDate);
					java.util.Date revertedDate = convertDate.getTime();
					
					java.text.SimpleDateFormat sdf = 
						     new java.text.SimpleDateFormat("yyyy-MM-dd");

					String finalDate = sdf.format(revertedDate);
					
					String comments = txtComments.getText();
					
					if(amountPaid == 0 || method.length() < 1 || confirmation.length() < 1)
					{
						JOptionPane.showMessageDialog(null, "Please insure all entered amounts are correct (make sure that amount is in decimal form, a date is entered and correct, etc.");
						return;
					}
					else
					{
						int dialogBtn = JOptionPane.YES_NO_OPTION;
						int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to submit the payment?", "Warning", dialogBtn);
						if (dialogResult == 1) 
						{
							return;
						}
						try {
							
							Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
							Statement stmt = conn.createStatement();
							
							//stmt.executeUpdate("Insert into paymentheader(patientid, pmttotal, pmtdate, pmtmethod, methodconfirm, comments, pmtbalance)"
									//+ "Values(" + patientID + ", " + amountPaid + ", '" + finalDate + "', '" + method + "', '" + confirmation 
									//+ "', '" + comments + "', " + (totalBalance - amountPaid) + ");");
							String query = "Insert into paymentheader(patientid, pmttotal, pmtdate, pmtmethod, methodconfirm, comments, amtpaid, pmtbalance)"
									+ "Values(?,?,?,?,?,?,?,?);";
							PreparedStatement pstmt = conn.prepareStatement(query);
							pstmt.setInt(1, patientID);
							pstmt.setFloat(2, totalBalance);
							pstmt.setString(3, finalDate);
							pstmt.setString(4, method);
							pstmt.setString(5, confirmation);
							pstmt.setString(6, comments);
							pstmt.setFloat(7, amountPaid);
							pstmt.setFloat(8, (totalBalance - amountPaid));
							pstmt.executeUpdate();
							//stmt.closeOnCompletion();
							pstmt.closeOnCompletion();
							ResultSet maxId;
							
							maxId = stmt.executeQuery("Select max(pmtheaderid) as 'headerid' from medprogram.paymentheader");
							int currentHeaderId = -1;
							
							while (maxId.next())
							{
								currentHeaderId = maxId.getInt("headerid");
							}
							
							if (currentHeaderId == -1)
							{
								JOptionPane.showMessageDialog(null, "Unable to get Header Id.");
								return;
							}
							
							try
							{
								if (invoiceDetails.size() > 0)
								{									
									for(int i = 0; i < invoiceDetails.size(); i++) {
										//String updateQuery = "Insert into paymentdetails(pmtheaderid, supplyid, supplydesc, supplyamt, supplytotal, discountamt)"
												//+ "Values(" + currentHeaderId + ",";
										query = "Insert into paymentdetails(pmtheaderid, supplyid, supplydesc, supplyamt, supplytotal, discountamt)"
												+ "Values(?,?,?,?,?,?);";
										String [] temp = invoiceDetails.get(i).split("\\s+");
										//updateQuery += temp[0] + ", '" + temp[1] + "', " + temp[3] + ", " + temp[5] + ", " + temp[7] + ");";
										
										pstmt = conn.prepareStatement(query);
										pstmt.setInt(1, currentHeaderId);
										pstmt.setInt(2, Integer.parseInt(temp[0]));
										pstmt.setString(3, temp[1]);
										pstmt.setInt(4, Integer.parseInt(temp[3]));
										pstmt.setFloat(5, Float.parseFloat(temp[5]));
										pstmt.setFloat(6, Float.parseFloat(temp[7]));
										pstmt.executeUpdate();
										//stmt.executeUpdate(updateQuery);
										pstmt.closeOnCompletion();
										//stmt.closeOnCompletion();
										
										ResultSet supplyEdit;
										
										supplyEdit = stmt.executeQuery("Select supplytype, qtyonhand, reorderqty, reorderamt from supplies where supplyid = " + temp[0] + ";");
										
										int supplyType, qtyOnHand, reorderQty, reorderAmt;
										
										supplyType = 0;
										qtyOnHand = 0;
										reorderQty = 0;
										reorderAmt = 0;
										
										while (supplyEdit.next())
										{
											supplyType = supplyEdit.getInt("supplytype");
											qtyOnHand = supplyEdit.getInt("qtyonhand");
											reorderQty = supplyEdit.getInt("reorderqty");
											reorderAmt = supplyEdit.getInt("reorderamt");
										}
										
										if (supplyType != 1 && supplyType != 7) 
										{
											qtyOnHand = qtyOnHand - Integer.parseInt(temp[3]);
											
											if (qtyOnHand <= reorderQty)
											{
												JOptionPane.showMessageDialog(null, temp[1] + " is running low. Please reorder " + reorderAmt + " of " + temp[1] + " to insure no shortages occur.");
											}
											
											stmt.executeUpdate("Update medprogram.supplies set qtyonhand = " + qtyOnHand + " where supplyid = " + temp[0]);
										}
										
									}
									
									JOptionPane.showMessageDialog(null, "Payment was successful!");
									
									dispose();
									}
									else
									{
										stmt.executeUpdate("Insert into paymentdetails(pmtheaderid, supplyid, supplydesc)"
												+ "Values(" + currentHeaderId + ", " + 0 + ", 'Payment');");
										
										JOptionPane.showMessageDialog(null, "Payment was successful!");
										dispose();
									}
							}
							
							catch (Exception ex)
							{
								stmt.executeUpdate("Insert into paymentdetails(pmtheaderid, supplyid, supplydesc)"
										+ "Values(" + currentHeaderId + ", " + 0 + ", 'Payment');");
								
								JOptionPane.showMessageDialog(null, "Payment was successful!");
								dispose();
							}
														
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								JOptionPane.showMessageDialog(null, "Please insure all entered amounts are correct (make sure that amount is in decimal form, a date is entered and correct, etc.");
							}
					}
					
				}
				catch (Exception dobEx) {
					// TODO Auto-generated catch block
					dobEx.printStackTrace();
					JOptionPane.showMessageDialog(null, "Please insure all entered amounts are correct (make sure that amount is in decimal form, a date is entered and correct, etc.");
					return;
				}
			}
		});
		
		setLocationRelativeTo(this);
		setVisible(true);		
	}
}
