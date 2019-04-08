import java.sql.*;

import javax.swing.JOptionPane;
public class Connect_SQL {

	public static void main(String[] args){
		Connect_SQL con = new Connect_SQL();
		Connection connect = con.dbconnect();
		
		
		try {
			PreparedStatement stmt;
			stmt = connect.prepareStatement("Select * from Professors");
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rmd = rs.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
