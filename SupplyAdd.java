package medProgram;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class SupplyAdd extends JFrame {

	private JPanel contentPane;

	private JTextField txtAvailQty;
	private JTextField txtPrice;
	private JTextField txtSupplyDesc;
	private JTextField txtReorderQty;
	private JTextField txtReorderAmt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new SupplyAdd();
	}

	/**
	 * Create the frame.
	 */
	public SupplyAdd() {
		setTitle("Add Supply");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 406, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSupplyDesc = new JLabel("Supply Description");
		lblSupplyDesc.setBounds(45, 28, 119, 16);
		contentPane.add(lblSupplyDesc);
		
		JLabel lblSupplyType = new JLabel("Supply Type");
		lblSupplyType.setBounds(45, 66, 76, 16);
		contentPane.add(lblSupplyType);
		
		JLabel lblCtrlSub = new JLabel("Controlled Substance");
		lblCtrlSub.setBounds(45, 105, 134, 16);
		contentPane.add(lblCtrlSub);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(45, 148, 30, 16);
		contentPane.add(lblPrice);
		
		JLabel lblAvailQty = new JLabel("Available Quantity");
		lblAvailQty.setBounds(45, 194, 115, 16);
		contentPane.add(lblAvailQty);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(300, 343, 100, 29);
		contentPane.add(btnAdd);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(6, 343, 94, 29);
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
		contentPane.add(btnCancel);
		
		txtAvailQty = new JTextField();
		txtAvailQty.setColumns(10);
		txtAvailQty.setBounds(185, 188, 85, 28);
		contentPane.add(txtAvailQty);
		
		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(185, 142, 85, 28);
		contentPane.add(txtPrice);
		
		JRadioButton rdoNo = new JRadioButton("No");
		rdoNo.setBounds(260, 101, 50, 23);
		contentPane.add(rdoNo);
		
		JRadioButton rdoYes = new JRadioButton("Yes");
		rdoYes.setBounds(185, 101, 63, 23);
		contentPane.add(rdoYes);

		ButtonGroup controlsub = new ButtonGroup( );
		controlsub.add(rdoYes);
		controlsub.add(rdoNo);
		
		JComboBox<String> cbxSupplyType = new JComboBox<String>();
		cbxSupplyType.setBounds(185, 62, 175, 27);
		contentPane.add(cbxSupplyType);
		try {
			Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
			Statement stmt = conn.createStatement();
			ResultSet loadSupplyTypes;
			loadSupplyTypes = stmt.executeQuery("Select supplytypedesc from medprogram.supplytypes;");
			while (loadSupplyTypes.next()) {
				String tmp = loadSupplyTypes.getString("supplytypedesc");
				cbxSupplyType.addItem(tmp);
			}
			stmt.closeOnCompletion();			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "There was an error. Please try again. If the issue persists, please contact System Administrator");
			e1.printStackTrace();
		}
		
		txtSupplyDesc = new JTextField();
		txtSupplyDesc.setColumns(10);
		txtSupplyDesc.setBounds(185, 22, 175, 28);
		contentPane.add(txtSupplyDesc);
		
		JLabel lblReorderQty = new JLabel("Reorder Quantity");
		lblReorderQty.setBounds(45, 240, 115, 16);
		contentPane.add(lblReorderQty);
		
		txtReorderQty = new JTextField();
		txtReorderQty.setColumns(10);
		txtReorderQty.setBounds(185, 234, 85, 28);
		contentPane.add(txtReorderQty);
		
		JLabel lblReorderAmt = new JLabel("Reorder Amount");
		lblReorderAmt.setBounds(45, 286, 115, 16);
		contentPane.add(lblReorderAmt);
		
		txtReorderAmt = new JTextField();
		txtReorderAmt.setColumns(10);
		txtReorderAmt.setBounds(185, 280, 85, 28);
		contentPane.add(txtReorderAmt);
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
					
					String supplyDesc = "";
					int ctrlSub = -1;
					int supplyType = -1;
					float price = -1;
					int available= -1;
					int reorderQty = -1;
					int reorderAmt = -1;
					
					//Check and get values
					if(txtSupplyDesc.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"Supply description must be filled");
						return;
					}else {
						supplyDesc = txtSupplyDesc.getText();
						}
					
					supplyType = cbxSupplyType.getSelectedIndex();
					if (supplyType == -1) {
						JOptionPane.showMessageDialog(null,"A Supply Type must be selected");
						return;
					}
				
					 if(rdoYes.isSelected()){
				    	ctrlSub = 1;
				    }else if(rdoNo.isSelected()){
				    	ctrlSub = 0;
					}else{
						JOptionPane.showMessageDialog(null,"Controlled Substance must be selected");
					}
				    
					if(txtPrice.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"Price must be filled");
						return;
					}else {
						price = Float.parseFloat(txtPrice.getText());
						}
					
					if(txtAvailQty.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"Available quantity must be filled");
						return;
						}else {
					available = Integer.parseInt(txtAvailQty.getText());
						}
					
					if (txtReorderQty.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"Reorder quantity must be filled");
						return;
					} else {
						reorderQty = Integer.parseInt(txtReorderQty.getText());
					}
					
					if (txtReorderAmt.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"Reorder Amount must be filled");
						return;
					} else {
						reorderAmt = Integer.parseInt(txtReorderAmt.getText());
					}
	
					//end for checking and getting values
				    		  
					String query = "Insert into supplies (supplyDesc, supplyType, isCtrlSub, qtyOnHand, reorderQty, reorderAmt, supplyPrice)" 
							+ "Values (?, ?, ?, ?, ?, ?, ?);";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1, supplyDesc);
					pstmt.setInt(2, supplyType);
					pstmt.setInt(3, ctrlSub);
					pstmt.setInt(4, available);
					pstmt.setInt(5, reorderQty);
					pstmt.setInt(6, reorderAmt);
					pstmt.setFloat(7, price);
					pstmt.executeUpdate();
					
		
					conn.close();
					
					JOptionPane.showMessageDialog(null, "Added " + supplyDesc + " to the supply list");
					dispose();
				
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,"There was an error adding this supply. Please try again later. If issue persists, please contact System Administrator");
					e1.printStackTrace();
				}
				
			}
		});
		
		setLocationRelativeTo(this);
		setVisible(true);
	}
}
