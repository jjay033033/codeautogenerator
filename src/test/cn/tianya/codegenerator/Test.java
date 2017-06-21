/**
 * 
 */
package test.cn.tianya.codegenerator;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import cn.tianya.codegenerator.BeanInfo;
import cn.tianya.codegenerator.db.DbTableVOCodeGenerator;
import cn.tianya.codegenerator.util.JavaFileUtils;

/**
 * @author PTY
 * 
 */
public class Test {

	public static void main(String[] args) {

		DbTableVOCodeGenerator codeGenerator = new DbTableVOCodeGenerator(
				getLocalConnection());

		// String[] tabs = new String[] { "follow_count", "follower",
		// "following", "group_info", "group_invite",
		// "group_meida_items", "group_reply", "group_reply_deleted",
		// "group_topic_count", "group_tweet", "group_tweet_deleted",
		// "group_user", "message_count", "msg_rcv", "msg_rcv_del",
		// "msg_snd", "msg_snd_del", "msg_sys", "msg_sys_del", "tweet",
		// "tweet_atme", "tweet_count", "tweet_deleted",
		// "tweet_lasted_activetime", "tweet_media_items",
		// "tweet_topic_count","group_reply","group_topic","tweet_oper_log","tweet_reply","tweet_reply_deleted","tweet_topic","user_grade"
		// };

		String[] tabs = new String[] {"test"};

		BeanInfo[] beanInfos = codeGenerator.generate(
				"cn.tianya.game.race.component.vo1", tabs);

		JavaFileUtils.createJavaFile(null, beanInfos);
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
		InputStream resourceAsStream = Thread.currentThread()
				.getContextClassLoader()
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
