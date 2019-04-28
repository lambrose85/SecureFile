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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton myFiles = new JButton("My Files");
		myFiles.setBounds(321, 78, 103, 23);
		contentPane.add(myFiles);
		
		JButton Authenticate = new JButton("Authenticate");
		Authenticate.setBounds(321, 112, 103, 23);
		contentPane.add(Authenticate);
		
		JButton Remove = new JButton("Remove");
		Remove.setBounds(321, 146, 103, 23);
		contentPane.add(Remove);
		
		JButton Add = new JButton("Add");
		Add.setBounds(321, 180, 103, 23);
		contentPane.add(Add);
		
		JButton Retrieve = new JButton("New button");
		Retrieve.setBounds(321, 215, 103, 23);
		contentPane.add(Retrieve);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(36, 78, 266, 160);
		contentPane.add(textArea);
		
		Label label = new Label("Hash Menu");
		label.setFont(new Font("Dialog", Font.PLAIN, 20));
		label.setAlignment(Label.CENTER);
		label.setBounds(156, 10, 122, 39);
		contentPane.add(label);
		
		
		
		
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
