package medProgram;

import java.awt.event.*; 
import javax.swing.*; 
import java.awt.*; 
import java.util.*; 
import java.sql.*; 
class Login extends JFrame  implements  ActionListener {   
	Container cp=null;
	JFrame f=null;
	JPanel jp;	        
	JButton login,exit;    
	JTextField name;    
	JPasswordField pwd;   
	JLabel jlable1,jlable2;   
		
	Login() { 
		jp=new JPanel(); 
		jp.setLayout(new GridLayout(3,2));   
		addComponents(); 
		this.setLayout(new BorderLayout(50,50)); 
		this.add(BorderLayout.NORTH,new JLabel(""));   
		this.add(BorderLayout.SOUTH,new JLabel("")); 
		this.add(BorderLayout.EAST,new JLabel(""));  
		this.add(BorderLayout.WEST,new JLabel("")); 
		this.add(jp); 
		this.setTitle("Login");   
		this.setSize(350,220);   
		this.setVisible(true); 
		this.setLocationRelativeTo(getParent());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	void addComponents() { 
		JLabel jlbu=new JLabel();  
		jlbu.setText("Username");  
		name=new JTextField();  
		
		JLabel jlbp=new JLabel(); 
		jlbp.setText("Password"); 
		pwd=new JPasswordField(); 
		
		login=new JButton("login");   
		exit=new JButton("exit"); 
		login.addActionListener(this);    
		exit.addActionListener(this);  
		
		jp.add(jlbu); 
		jp.add(name);
		jp.add(jlbp);
		jp.add(pwd);
		jp.add(login);
		jp.add(exit);
	}

	public void confirm() { 
        try{ 
        	 System.out.println("start"); 
        	 Class.forName("com.mysql.jdbc.Driver");   
        } 
        catch(ClassNotFoundException e){
        	System.out.println("driver failed!");     
        } 
        try{ 
        	System.out.println("Connecting database...");
        	Connection con=DriverManager.getConnection("jdbc:mysql://99.98.84.144:3306/medprogram","root", "medProgram");
        	System.out.println("Database connected1!");   
        	Statement sql=con.createStatement();    
        	String uname=name.getText().trim();    
        	String Mima=pwd.getText().trim();    
        	String queryMima="select * from user where username='"+uname+"' and password='"+Mima+"'"; 	
        	System.out.println("Database connected2!");
        	ResultSet rs=sql.executeQuery(queryMima); 
        	if(rs.next()){ 
        		new MainInterface(uname);
        		con.close();     
        		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        	} 
        	else{ 
        		JOptionPane.showMessageDialog(null,"Username or password is not correct, please try again.","Attention！",  JOptionPane.YES_NO_OPTION);        
        	} 
        	name.setText("");  
        	pwd.setText("");    
        }  
        catch(SQLException g){ 
        	System.out.println(g.getErrorCode()); 
        	System.out.println(g.getMessage());     
        }    
	}

	public void actionPerformed(ActionEvent e)  { 
		String cmd=e.getActionCommand();  
		if(cmd.equals("login")){   
			System.out.println("start"); 
			confirm();     
		} 
		else if(cmd.equals("exit")){     
			System.exit(0); 
		}     
  } 
    
	public static void main(String []arg){  
		System.out.println("start1");
    	Login a=new Login(); 
    	System.out.println("end");
	} 
	
}
 