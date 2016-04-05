import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.table.TableModel;
import javax.swing.event.*;

public class ViewSupply extends JPanel implements TableModelListener{
	
	public ViewSupply(){
		super(new GridLayout(1,0));
		
		JTable table = new JTable(new SuppliesViewer());
		table.setPreferredScrollableViewportSize(new Dimension(1300,800));
		
		table.setRowHeight(50);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		table.getModel().addTableModelListener(this);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		add(scrollPane);
		
	}
	
	public void tableChanged(TableModelEvent e) {
		if (e.getType() == TableModelEvent.UPDATE){	
        int row = e.getFirstRow();  
        int column = e.getColumn();
       
        
        TableModel model = (TableModel)e.getSource();  
        Object data = model.getValueAt(row, column);
        System.out.println(data);
		}
		  
    }
    

}