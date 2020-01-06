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
			return "long";
		case Types.BINARY :
			return "java.sql.Blob";
		case Types.BIT :
			return "int";
		case Types.BLOB :
			return "java.sql.Blob";
		case Types.BOOLEAN :
			return "boolean";
		case Types.CHAR :
			return "String";
		case Types.CLOB :
			return "String";
		case Types.DATALINK :
			return "String";
		case Types.DATE :
			return "String";
		case Types.DECIMAL :
			return "long";
		case Types.DOUBLE :
			return "double";
		case Types.FLOAT :
			return "float";
		case Types.INTEGER :
			return "int";
		case Types.JAVA_OBJECT :
			return "java.sql.Blob";
		case Types.LONGVARBINARY :
			return "java.sql.Blob";
		case Types.LONGVARCHAR :
			return "String";
		case Types.NULL :
			return "Object";
		case Types.NUMERIC :
			return "long";
		case Types.SMALLINT :
			return "int";
		case Types.TIME :
			return "String";
		case Types.TIMESTAMP :
			return "java.sql.Timestamp";
		case Types.TINYINT :
			return "int";
		case Types.VARBINARY :
			return "java.sql.Blob";
		case Types.VARCHAR :
			return "String";
		default:
			return "String";
		}
		
	}

}
