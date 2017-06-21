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
	
	private String className;
	
	private Field[] fields;
	
	private String codeString;

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
		return "BeanInfo [pkg=" + pkg + ", className=" + className
				+ ", fields=" + Arrays.toString(fields) + ", codeString="
				+ codeString + "]";
	}

}
