import java.sql.*;

import javax.swing.JOptionPane;
public class Connect_SQL {

	public static void main(String[] args) throws SQLException{
		Connect_SQL con = new Connect_SQL();
		Connection connect = con.dbconnect();
		
		
		try {
			PreparedStatement stmt;
			stmt = connect.prepareStatement("Select * from Professors");
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rmd = rs.getMetaData();
			printResultSet(rs,rmd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void printResultSet(ResultSet rs, ResultSetMetaData rmd){
		try{
			int columns = rmd.getColumnCount();
			while(rs.next()){
				for(int i=1; i<= columns; i++){
					System.out.println(rs.getString(i)+" ");
				}
				System.out.println();
			}
		}
		catch(SQLException e){
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
