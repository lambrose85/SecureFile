import java.awt.BorderLayout;
import java.awt.EventQueue;

import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JDialog;

import com.jgoodies.forms.factories.DefaultComponentFactory;

public class Login extends JFrame {

	private JPanel contentPane;
	private String User;
	private String Pass;
	private String getUser() {
		return User;
	}

	private void setUser(String user) {
		User = user;
	}

	private String getPass() {
		return Pass;
	}

	private void setPass(String pass) {
		Pass = pass;
	}



	private JTextField UsernameField;
	private JPasswordField PasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)throws SQLException{
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		Connect_SQL con = new Connect_SQL();
		Connection connect = con.dbconnect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel UsernameLabel = new JLabel("Username");
		UsernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		UsernameLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		UsernameLabel.setBounds(151, 49, 124, 30);
		contentPane.add(UsernameLabel);
		
		JLabel PasswordLabel = new JLabel("Password");
		PasswordLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		PasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PasswordLabel.setBounds(151, 114, 118, 30);
		contentPane.add(PasswordLabel);
		
		JButton LoginButton = new JButton("Login");
		LoginButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		LoginButton.setBounds(151, 185, 124, 33);
		contentPane.add(LoginButton);
		
		UsernameField = new JTextField();
		UsernameField.setColumns(10);
		UsernameField.setBounds(127, 79, 172, 30);
		contentPane.add(UsernameField);
		
		PasswordField = new JPasswordField();
		PasswordField.setBounds(127, 144, 172, 30);
		contentPane.add(PasswordField);
		
		LoginButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0){
				setUser(UsernameField.getText());
				System.out.println(getUser());
				Hash sha = new Hash();
				String pass = String.valueOf(PasswordField.getPassword());
				String hash = sha.hashPassword(pass);
				
				System.out.println(hash);
				try{
				
				//send data in query 
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@131.230.133.11:1521:cs","lambrose","4uhNcRMq");
					PreparedStatement ps = conn.prepareStatement("Select * from SecureUsers where username = '"+getUser()+"' and password='"+hash+"'");
					ResultSet rs = ps.executeQuery();
					System.out.println(rs);
					if(rs.next()){
						JOptionPane.showMessageDialog(null, "login successful");
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									FileSystem window = new FileSystem();
									window.frame.setVisible(true);
									window.setUserName(getUser());
									window.setPassword(pass);
									setVisible(false);
									dispose();
									
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

						
					
					}else{
						JOptionPane.showMessageDialog(null, "Invalid Credentials!!!!");
					}		
					
				}
				catch(SQLException e){
					e.printStackTrace();
				}
				
		}
		});
	

	}


	
	public static Connection dbconnect(){
		try{
			Class.forName("oracle.jdbc.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@131.230.133.11:1521:cs","lambrose","4uhNcRMq");
			
			return conn;
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
