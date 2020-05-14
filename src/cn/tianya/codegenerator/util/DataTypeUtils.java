/**
 * 
 */
package cn.tianya.codegenerator.util;

import java.sql.Types;

/**
 * @author PTY
 *
 */
public class DataTypeUtils {
	
	public static String getDataType(int sqlType){
		
		switch(sqlType){
		case Types.ARRAY :
			return "Object[]";
		case Types.BIGINT :
			return "Long";
		case Types.BINARY :
			return "java.sql.Blob";
		case Types.BIT :
			return "Integer";
		case Types.BLOB :
			return "java.sql.Blob";
		case Types.BOOLEAN :
			return "Boolean";
		case Types.CHAR :
			return "String";
		case Types.CLOB :
			return "String";
		case Types.DATALINK :
			return "String";
		case Types.DATE :
			return "String";
		case Types.DECIMAL :
			return "Long";
		case Types.DOUBLE :
			return "Double";
		case Types.FLOAT :
			return "Float";
		case Types.INTEGER :
			return "Integer";
		case Types.JAVA_OBJECT :
			return "java.sql.Blob";
		case Types.LONGVARBINARY :
			return "java.sql.Blob";
		case Types.LONGVARCHAR :
			return "String";
		case Types.NULL :
			return "Object";
		case Types.NUMERIC :
			return "Long";
		case Types.SMALLINT :
			return "Integer";
		case Types.TIME :
			return "String";
		case Types.TIMESTAMP :
			return "java.sql.Timestamp";
		case Types.TINYINT :
			return "Integer";
		case Types.VARBINARY :
			return "java.sql.Blob";
		case Types.VARCHAR :
			return "String";
		default:
			return "String";
		}
		
	}

}
