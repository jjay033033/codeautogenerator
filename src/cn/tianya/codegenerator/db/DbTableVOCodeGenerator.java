/**
 * 
 */
package cn.tianya.codegenerator.db;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import cn.tianya.codegenerator.BeanInfo;
import cn.tianya.codegenerator.Field;
import cn.tianya.codegenerator.util.DataTypeUtils;
import cn.tianya.codegenerator.util.NameUtils;
import cn.tianya.codegenerator.util.StringUtil;
import cn.tianya.codegenerator.util.VelocityTemplateUtils;

/**
 * @author PTY
 * 
 */
public class DbTableVOCodeGenerator {

	private Connection connection;

	public DbTableVOCodeGenerator(Connection connection) {
		this.connection = connection;
	}

	/**
	 * 
	 */
	public BeanInfo[] generate(String pkgName, String... tableNames) {

		BeanInfo[] beanInfos = new BeanInfo[tableNames.length*3];
		String xmlPkgName = pkgName+".xml";
		String mapperPkgName = pkgName+".dao";
		String entityPkgName = pkgName+".entity";
		int indexCount = 0;
		for (String tableName : tableNames) {
//			beanInfos[indexCount++] = generateOneBean(entityPkgName, tableName);
			beanInfos[indexCount++] = generateOneMapperBean(entityPkgName,mapperPkgName,xmlPkgName, tableName);
			beanInfos[indexCount++] = generateOneVoForMbp(entityPkgName, tableName);
			beanInfos[indexCount++] = generateOneMapperClassForMbp(entityPkgName,mapperPkgName, tableName);
		}

		return beanInfos;
	}

	/**
	 * @param pkgName
	 * @return
	 */
	private BeanInfo generateOneBean(String pkgName, String tableName) {
		BeanInfo beanInfo = new BeanInfo();
		beanInfo.setPkg(pkgName);
		beanInfo.setClassName(NameUtils.capitalize(NameUtils.getJavaStyleName(tableName.toLowerCase())) + "Entity");
		beanInfo.setFileExt("java");

		Field[] fields = getList(tableName);
		
		beanInfo.setFields(fields);

		String codeString = VelocityTemplateUtils.createBeanCode(beanInfo);
		beanInfo.setCodeString(codeString);
		return beanInfo;
	}
	
	private BeanInfo generateOneVoForMbp(String pkgName, String tableName) {
		BeanInfo beanInfo = new BeanInfo();
		beanInfo.setPkg(pkgName);
		beanInfo.setClassName(NameUtils.capitalize(NameUtils.getJavaStyleName(tableName.toLowerCase())) + "Entity");
		beanInfo.setFileExt("java");
		
		beanInfo.setTbName(tableName.toUpperCase());

		Field[] fields = getList(tableName);
		
		beanInfo.setFields(fields);

		String codeString = VelocityTemplateUtils.createVoForMbpCode(beanInfo);
		beanInfo.setCodeString(codeString);
		return beanInfo;
	}
	
	private BeanInfo generateOneMapperClassForMbp(String entityPkgName,String mapperPkgName, String tableName) {
		BeanInfo beanInfo = new BeanInfo();
		beanInfo.setPkg(mapperPkgName);
		String capitalize = NameUtils.capitalize(NameUtils.getJavaStyleName(tableName.toLowerCase()));
		
		beanInfo.setTableName(capitalize);
		beanInfo.setTbName(tableName.toUpperCase());
		beanInfo.setClassName(capitalize + "Mapper");
		beanInfo.setFileExt("java");
		beanInfo.setEntityPkg(entityPkgName);

		Field[] fields = getList(tableName);
		
		beanInfo.setFields(fields);

		String codeString = VelocityTemplateUtils.createMapperForMbpCode(beanInfo);
		beanInfo.setCodeString(codeString);

		return beanInfo;
	}

	
	
	private BeanInfo generateOneMapperBean(String entityPkgName,String mapperPkgName,String xmlPkgName, String tableName) {
		BeanInfo beanInfo = new BeanInfo();
		beanInfo.setPkg(xmlPkgName);
		String capitalize = NameUtils.capitalize(NameUtils.getJavaStyleName(tableName.toLowerCase()));
		
		beanInfo.setTableName(capitalize);
		beanInfo.setTbName(tableName.toUpperCase());
		beanInfo.setClassName(capitalize + "Mapper");
		beanInfo.setFileExt("xml");
		beanInfo.setEntityPkg(entityPkgName);
		beanInfo.setMapperPkg(mapperPkgName);

//		ResultSet rs = null;
//
//		try {
//
//			DatabaseMetaData metaData = connection.getMetaData();
//			rs = metaData.getColumns(connection.getCatalog(), null, tableName, null);
//
//			List<Field> fieldList = new ArrayList<Field>();
//			while (rs.next()) {
//
//				String colName = rs.getString("COLUMN_NAME");
//
//				String remarks = rs.getString("REMARKS");
//
//
//				Field field = new Field();
//				field.setName(NameUtils.getJavaStyleName(colName));
//
//				field.setDesc(StringUtil.isNullOrBlank(remarks) ? "" : remarks);
//
//				field.setUpperName(NameUtils.capitalize(field.getName()));
//				int dataType = rs.getInt("DATA_TYPE");
//				field.setType(DataTypeUtils.getDataType(dataType));
//				field.setJdbcType(JDBCType.valueOf(dataType).getName());
//				field.setColumName(colName);
//				fieldList.add(field);
//			}
//
//			Field[] fields = new Field[fieldList.size()];
//			beanInfo.setFields(fieldList.toArray(fields));
//
//			String codeString = VelocityTemplateUtils.createMapperBeanCode(beanInfo);
//			beanInfo.setCodeString(codeString);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				rs.close();
//			} catch (Exception e2) {
//			}
//		}
		
		Field[] fields = getList(tableName);
		
		beanInfo.setFields(fields);

		String codeString = VelocityTemplateUtils.createMapperBeanCode(beanInfo);
		beanInfo.setCodeString(codeString);

		return beanInfo;
	}
	
	private Field[] getList(String tableName) {
		List<Field> fieldList = new ArrayList<Field>();
		ResultSet rs = null;
		try {

			DatabaseMetaData metaData = connection.getMetaData();
			rs = metaData.getColumns(connection.getCatalog(), null, tableName, null);

			
			while (rs.next()) {

				String colName = rs.getString("COLUMN_NAME");

				String remarks = rs.getString("REMARKS");
				
//				ResultSetMetaData metaData2 = rs.getMetaData();
//				int columnCount = metaData2.getColumnCount();
//				for(int i=1;i<=columnCount;i++){
//					String columnLabel = metaData2.getColumnLabel(i);
//					System.out.println(columnLabel);
//					System.out.println(rs.getObject(columnLabel));
//				}

				Field field = new Field();
//				field.setName(NameUtils.getJavaStyleName(colName));
//				field.setDesc(StringUtil.isNullOrBlank(remarks) ? "" : remarks);
//				field.setUpperName(NameUtils.capitalize(field.getName()));
//				field.setType(DataTypeUtils.getDataType(rs.getInt("DATA_TYPE")));

					
				field.setName(NameUtils.getJavaStyleName(colName));

				field.setDesc(StringUtil.isNullOrBlank(remarks) ? "" : remarks);

				field.setUpperName(NameUtils.capitalize(field.getName()));
				int dataType = rs.getInt("DATA_TYPE");
				field.setType(DataTypeUtils.getDataType(dataType));
				field.setJdbcType(JDBCType.valueOf(dataType).getName());
				field.setColumName(colName);

				fieldList.add(field);
			}

			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception e2) {
			}
		}
		
		Field[] fields = fieldList.toArray(new Field[fieldList.size()]);
		return fields;
	}

	public static void main(String[] args) {

		DbTableVOCodeGenerator codeGenerator = new DbTableVOCodeGenerator(getLocalConnection());

		String[] tabs = new String[] { "mobile_block_invite" };

		System.out.println(codeGenerator.generate("com.huawei.tw.component.weibo.bean", tabs)[0].getCodeString());
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
		InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("./dbcp_98.properties");
		try {
			properties.load(new BufferedInputStream(resourceAsStream));
			return BasicDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
