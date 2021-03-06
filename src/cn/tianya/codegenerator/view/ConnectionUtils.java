/**
 * 
 */
package cn.tianya.codegenerator.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author PTY
 *
 */
public class ConnectionUtils {
	
	public static Connection getConnection(Properties dbProps) throws Exception {
		
		Connection conn = null;

		boolean isMysql = dbProps.getProperty("dbType").equalsIgnoreCase("Mysql数据库");
		
		boolean isOracle = dbProps.getProperty("dbType").equalsIgnoreCase("oracle数据库");
		
		String driverClass = null;
		if(isMysql){
			driverClass = "com.mysql.jdbc.Driver";
		}else if(isOracle){
			driverClass = "oracle.jdbc.driver.OracleDriver";
		}
		
		Properties info = new Properties();
		StringBuffer url = new StringBuffer();
		if(isMysql){
			url.append("jdbc:mysql://").append(dbProps.get("host")).append(":").append(dbProps.get("port"))
				.append("/").append(dbProps.get("dbName")).append("?useUnicode=true&amp;characterEncoding=utf-8");
		}else if(isOracle){
			url.append("jdbc:oracle:thin:@").append(dbProps.get("host")).append(":").append(dbProps.get("port")).append(":").append(dbProps.get("dbName"));
			info.setProperty("remarksReporting","true");
		}
		
		try {
			Class.forName(driverClass);
//			conn = DriverManager.getConnection(url.toString(),
//					dbProps.getProperty("user"), dbProps.getProperty("password"));
			
			
			info.setProperty("user", dbProps.getProperty("user"));
			info.setProperty("password", dbProps.getProperty("password"));
			
			conn = DriverManager.getConnection(url.toString(), info);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return conn;
	}
	
	public static void close(Connection conn){
		try {
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
