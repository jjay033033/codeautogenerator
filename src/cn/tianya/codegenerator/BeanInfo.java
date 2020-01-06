/**
 * 
 */
package cn.tianya.codegenerator;

import java.util.Arrays;

/**
 * @author PTY
 *
 */
public class BeanInfo {
	
	private String pkg;
	
	private String mapperPkg;
	
	private String entityPkg;
	
	private String tableName;
	
	private String tbName;
	
	private String className;
	
	private Field[] fields;
	
	private String codeString;
	
	private String fileExt;

	public String getCodeString() {
		return codeString;
	}

	public void setCodeString(String codeString) {
		this.codeString = codeString;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Field[] getFields() {
		return fields;
	}

	public void setFields(Field[] fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "BeanInfo [pkg=" + pkg + ", mapperPkg=" + mapperPkg + ", entityPkg=" + entityPkg + ", tableName="
				+ tableName + ", tbName=" + tbName + ", className=" + className + ", fields=" + Arrays.toString(fields)
				+ ", codeString=" + codeString + ", fileExt=" + fileExt + "]";
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getMapperPkg() {
		return mapperPkg;
	}

	public void setMapperPkg(String mapperPkg) {
		this.mapperPkg = mapperPkg;
	}

	public String getEntityPkg() {
		return entityPkg;
	}

	public void setEntityPkg(String entityPkg) {
		this.entityPkg = entityPkg;
	}

}
