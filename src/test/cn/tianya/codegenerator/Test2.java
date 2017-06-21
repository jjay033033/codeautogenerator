package test.cn.tianya.codegenerator;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class Test2 {
	
	public static void main(String[] args) {
		
		try {
			Connection conn = getLocalConnection();
			PreparedStatement ps = conn
					.prepareStatement("UPDATE TestUpdate SET age=age-1 WHERE id=2");
			/*for(int i = 1; i < 5; i++){
				ps.setInt(1, i);
				ps.setString(2, "name");
				ps.addBatch();
				
				if(i % 2 == 0){
					ps.executeBatch();
					ps.clearBatch();
				}
				ps.executeBatch();
				ps.clearBatch();
			}*/
			System.out.println(ps.executeUpdate());
			
			ps.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private static Connection getLocalConnection() {
		
		DataSource dataSource = getLocalDataSource();
		
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	private static DataSource getLocalDataSource() {
		Properties properties = new Properties();
		InputStream resourceAsStream = Thread.currentThread()
				.getContextClassLoader().getResourceAsStream("./dbcp_98.properties");
		try {
			properties.load(new BufferedInputStream(resourceAsStream));
			return BasicDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
