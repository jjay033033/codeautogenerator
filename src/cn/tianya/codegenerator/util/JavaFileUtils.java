/**
 * 
 */
package cn.tianya.codegenerator.util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.tianya.codegenerator.BeanInfo;

/**
 * @author PTY
 *
 */
public class JavaFileUtils {
	
	private static final Log log = LogFactory.getLog(JavaFileUtils.class);
	
	private static final String VO_FILE_PATH = "/conf/vocode";

	public static void createJavaFile(String basePath, BeanInfo... beanInfos){
		
		if(basePath == null){
			basePath = getDefaultBasePath();
		}
		
		for(BeanInfo beanInfo : beanInfos){
			createOneFile(basePath, beanInfo);
		}
	}

	/**
	 * @param basePath
	 * @param beanInfo
	 */
	private static void createOneFile(String basePath, BeanInfo beanInfo) {
		//创建java包的文件夹
		File fileDir = createPkgFolder(basePath, beanInfo.getPkg());
		
		BufferedOutputStream bos = null;
		BufferedWriter bufferedWriter = null;
		
		try {
			OutputStream os = new FileOutputStream(fileDir + "/"
					+ beanInfo.getClassName() + ".java");
			bos = new BufferedOutputStream(os);
			
			Writer writer = new OutputStreamWriter(bos);
			bufferedWriter = new BufferedWriter(writer);
			
			bufferedWriter.write(beanInfo.getCodeString());
			bufferedWriter.flush();
			
		} catch (Exception e) {
			log.error(e);
		} finally {
			try {
				bufferedWriter.close();
			} catch (Exception e) {
			}
			try {
				bos.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @return String
	 */
	private static String getDefaultBasePath() {

		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("./");

		return new File(url.getFile()).getParent() + VO_FILE_PATH;
	}
	
	/**
	 * @param basePath
	 * @param pkg
	 * @return File
	 */
	public static File createPkgFolder(String basePath, String pkg){
		
		String dirName = basePath + "/" + pkg.replaceAll("\\.", "/");
		
		File folder = new File(dirName);
		if(!folder.exists()){
			folder.mkdirs();
		}
		
		return folder;
	}
}
