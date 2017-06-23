/**
 * 
 */
package cn.tianya.codegenerator.util;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import cn.tianya.codegenerator.BeanInfo;

/**
 * @author PTY
 * 
 */
public class VelocityTemplateUtils {

	private static VelocityEngine ve = new VelocityEngine();
	private static Template template;
	
	static {
		// 设置模板加载路径，这里设置的是class下
		ve.setProperty(Velocity.RESOURCE_LOADER, "class");
		ve.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		// 进行初始化操作
		ve.init();
		// 加载模板，设定模板编码
		template = ve.getTemplate("VOTemplate.vm", "gbk");
	}

	/**
	 * @param codeString
	 * @return
	 */
	public static String createBeanCode(BeanInfo beanInfo) {

		try {
			// 设置初始化数据
			VelocityContext context = new VelocityContext();
			context.put("pkgName", beanInfo.getPkg());
			context.put("className", beanInfo.getClassName());
			context.put("fields", beanInfo.getFields());

			// 设置输出
			StringWriter writer = new StringWriter();
			// 将环境数据转化输出
			template.merge(context, writer);
			// 简化操作
			// ve.mergeTemplate("test/velocity/simple1.vm", "gbk", context,
			// writer );
			
			return writer.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
