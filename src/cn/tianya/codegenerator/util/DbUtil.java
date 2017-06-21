/**
 * @copyright 2017 tianya.cn
 */
package cn.tianya.codegenerator.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import cn.tianya.codegenerator.view.ConnectionUtils;

/**
 * @author guozy
 * @date 2017-6-16
 * 
 */
public class DbUtil {

	public static List<String> getTables(Connection conn, String user,
			String database) {
		System.out.println(user);
		System.out.println(database);
		List<String> list = new ArrayList<String>();
		if (conn == null || StringUtil.isNullOrBlank(user)
				|| StringUtil.isNullOrBlank(database))
			return list;
		try {
			DatabaseMetaData dm = conn.getMetaData();
			ResultSet rs = dm.getTables(database, null, null,
					new String[] { "TABLE" });
			while (rs.next()) {
//				 ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
//				for(int i=0;i<rsmd.getColumnCount();i++){
//					System.out.println(rs.getMetaData().getColumnLabel(i+1));
//					System.out.println(rs.getString(i+1));
//				}
				
//				System.out.println(rs.getString("TABLE_SCHEM"));
//				if (user.equalsIgnoreCase(rs.getString("TABLE_SCHEM"))) {
					list.add(rs.getString("TABLE_NAME"));
//				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ConnectionUtils.close(conn);
		}
		return list;
	}

}
