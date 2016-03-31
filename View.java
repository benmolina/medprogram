package medProgram;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;


public class View extends JPanel {
	
	public View(){
		super(new GridLayout(1,0));
		
		JTable table = new JTable(new AppointmentViewer());
		table.setPreferredScrollableViewportSize(new Dimension(1300,800));
		
		table.setRowHeight(50);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		add(scrollPane);
		
		
	}

}