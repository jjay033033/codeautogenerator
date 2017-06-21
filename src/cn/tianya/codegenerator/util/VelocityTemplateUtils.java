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
		// ����ģ�����·�����������õ���class��
		ve.setProperty(Velocity.RESOURCE_LOADER, "class");
		ve.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		// ���г�ʼ������
		ve.init();
		// ����ģ�壬�趨ģ�����
		template = ve.getTemplate("VOTemplate.vm", "gbk");
	}

	/**
	 * @param codeString
	 * @return
	 */
	public static String createBeanCode(BeanInfo beanInfo) {

		try {
			// ���ó�ʼ������
			VelocityContext context = new VelocityContext();
			context.put("pkgName", beanInfo.getPkg());
			context.put("className", beanInfo.getClassName());
			context.put("fields", beanInfo.getFields());

			// �������
			StringWriter writer = new StringWriter();
			// ����������ת�����
			template.merge(context, writer);
			// �򻯲���
			// ve.mergeTemplate("test/velocity/simple1.vm", "gbk", context,
			// writer );
			
			return writer.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
