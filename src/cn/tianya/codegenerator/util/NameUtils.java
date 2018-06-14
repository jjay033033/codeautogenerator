/**
 * 
 */
package cn.tianya.codegenerator.util;

import java.beans.Introspector;

/**
 * @author PTY
 *
 */
public class NameUtils {
	
	public static String getJavaStyleName(String name){
		
		name = name.toLowerCase();
		
		if(name == null || name.trim().length() == 0){
			return name;
		}
		
		String[] nameItems = name.split("_");
		
		StringBuffer sb = new StringBuffer(nameItems[0]);
		for(int i = 1; i < nameItems.length; i++){
			sb.append(capitalize(nameItems[i]));
		}
		return sb.toString();
	}
	
	public static String capitalize(String name) {
		if (name == null || name.length() == 0) {
			return name;
		}
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	public static String decapitalize(String name) {
		return Introspector.decapitalize(name);
	}
	
	public static void main(String[] args) {
		System.out.println(getJavaStyleName("tb_collect_article"));
	}
}
