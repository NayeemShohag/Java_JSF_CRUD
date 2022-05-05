package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBConnection {

	private String dbURL = "jdbc:mysql://localhost:3306/demo";
	private String username = "root";
	private String password = "My1234";
	
	Logger log = Logger.getLogger("DBConnection");

	private static Connection conn = null;

	private DBConnection() {

		if (conn == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				this.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "shohag", "shohag227");
				if(conn!=null)
				{
					log.info("=================================================================================>connected");
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Connection getInstance() {
		if(conn==null)
		{
			DBConnection db = new DBConnection();
		}
		
		return conn;
	}

}
