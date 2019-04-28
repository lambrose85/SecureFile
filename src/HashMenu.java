import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Label;
import java.awt.Font;

public class HashMenu extends JFrame {
	
	public String userName;
	public String password;
	
	public void setuserName(String x){
		userName = x;
		
	}
	public String getuserName(){
		return userName;
	}
	public void setpassword(String x){
		password= x;
	}
	public String getPassword(){
		return password;
	}
	private JPanel contentPane;
	public static void printResultSet(ResultSet rs, ResultSetMetaData rmd, TextArea TA){
		try{
			int columns = rmd.getColumnCount();
			while(rs.next()){
				for(int i=1; i<= columns; i++){
					System.out.println(rs.getString(i)+" ");
					TA.append(rs.getString(i)+" ");
				}
				System.out.println();
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	public static void printMyFiles(ResultSet rs, ResultSetMetaData rmd, TextArea TA){
		try{
			int columns = rmd.getColumnCount();
			while(rs.next()){
				for(int i=1; i<= columns; i++){
					TA.append(rs.getString(i)+" ");
				}
				System.out.println();
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HashMenu frame = new HashMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HashMenu() {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 546, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton myFiles = new JButton("My Files");
		myFiles.setBounds(414, 78, 103, 23);
		contentPane.add(myFiles);
		
		JButton Authenticate = new JButton("Authenticate");
		Authenticate.setBounds(414, 112, 103, 23);
		contentPane.add(Authenticate);
		
		JButton Remove = new JButton("Remove");
		Remove.setBounds(414, 146, 103, 23);
		contentPane.add(Remove);
		
		JButton Add = new JButton("Add");
		Add.setBounds(414, 180, 103, 23);
		contentPane.add(Add);
		
		JButton Retrieve = new JButton("New button");
		Retrieve.setBounds(414, 215, 103, 23);
		contentPane.add(Retrieve);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(30, 93, 350, 191);
		contentPane.add(textArea);
		
		Label label = new Label("Hash Menu");
		label.setFont(new Font("Dialog", Font.PLAIN, 20));
		label.setAlignment(Label.CENTER);
		label.setBounds(297, 0, 122, 39);
		contentPane.add(label);
		
		Remove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){
				try{
				String filename = "TEST";
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@131.230.133.11:1521:cs","lambrose","4uhNcRMq");
				//Prepared statement to remove entered fil
				PreparedStatement ps = conn.prepareStatement("delete from SecureFiles where filename = '"+filename+"'");
				ResultSet rs = ps.executeQuery();
				ResultSetMetaData rmd = rs.getMetaData();
				}
				catch(SQLException arg0){
					arg0.printStackTrace();
				}
			}
			
		});
		
		
		myFiles.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){
				String uname = "lambrose";
				try{
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@131.230.133.11:1521:cs","lambrose","4uhNcRMq");
					//Need to replace with a file query
					PreparedStatement ps = conn.prepareStatement("Select * from SecureFiles");
					ResultSet rs = ps.executeQuery();
					ResultSetMetaData rmd = rs.getMetaData();
					printResultSet(rs,rmd,textArea);
				}catch(SQLException c){
					c.printStackTrace();
				}
				
			}
		});
	
	}
}
