package medProgram;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class RemoveUser extends JFrame {

	private JPanel contentPane;
	private int selection = -1;
	private static DefaultListModel<String> listModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new RemoveUser();
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					RemoveUser frame = new RemoveUser();
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
	public RemoveUser() {
		setTitle("Remove User");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 288, 347);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList<String> lstUsers = new JList<String>();
		listModel = new DefaultListModel<String>();
		lstUsers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (lstUsers.isSelectionEmpty())
					selection = -1;
				else
					selection = lstUsers.getSelectedIndex();
			}
		});
		lstUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstUsers.setBounds(6, 45, 256, 201);
		contentPane.add(lstUsers);
		
		try {
			Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
			Statement stmt = conn.createStatement();
			ResultSet loadActiveUsers;
						
			loadActiveUsers = stmt.executeQuery("Select username from medprogram.user where isactive = 1;");
			
			while (loadActiveUsers.next()) {
				String tmp = loadActiveUsers.getString("username");
				listModel.addElement(tmp);
			}
			stmt.closeOnCompletion();
			
			lstUsers.setModel(listModel);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialogBtn = JOptionPane.YES_NO_OPTION;
				if (lstUsers.isSelectionEmpty())
				{
					JOptionPane.showMessageDialog(null,"Please select a user to remove");
					return;
				}
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove the following user?", "Warning", dialogBtn);
				if (dialogResult == 0)
					{
					try{
						int id = -1;
						Connection conn=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram", "root", "medProgram");
						Statement stmt = conn.createStatement();
						ResultSet removeSet;
						removeSet = stmt.executeQuery("Select userid from medprogram.user where username = '" + lstUsers.getSelectedValue() + "';");
						if(removeSet.next()) {
							id = removeSet.getInt("userid");
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Please select a user to remove");
							return;
						}
						
						stmt.executeUpdate("Update medprogram.user Set isactive = 0 where userid = " + id);
						
						stmt.closeOnCompletion();
						
						ResultSet updateList;
						
						listModel.clear();
						updateList = stmt.executeQuery("Select username from medprogram.user where isactive = 1;");
						while(updateList.next())
						{
							String tmp = updateList.getString("username");
							listModel.addElement(tmp);
						}
						
						stmt.closeOnCompletion();
						
						lstUsers.setModel(listModel);

						conn.close();
						
					}
					catch (Exception e1)
					{
						JOptionPane.showMessageDialog(null,"Please select a user to remove");
						e1.printStackTrace();
					}
					}
			}
		});
		btnRemove.setBounds(6, 273, 102, 29);
		contentPane.add(btnRemove);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(160, 273, 102, 29);
		contentPane.add(btnCancel);
		
		JLabel lblSelect = new JLabel("Select users to be removed");
		lblSelect.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelect.setBounds(36, 18, 208, 16);
		contentPane.add(lblSelect);
		
		this.setLocationRelativeTo(this);
		setVisible(true);
		
		
	}
}