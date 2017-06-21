/**
 * 
 */
package cn.tianya.codegenerator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import cn.tianya.codegenerator.BeanInfo;
import cn.tianya.codegenerator.db.DbTableVOCodeGenerator;
import cn.tianya.codegenerator.util.DbUtil;
import cn.tianya.codegenerator.util.JavaFileUtils;

/**
 * @author PTY
 * 
 */
public class TableWindow {

	private JComboBox typeComboBox;
	private JTextField serverField;
	private JTextField dbNameField;
	private JTextField portField;
	private JTextField userNameField;
	private JTextField passwordField;
	private JTextArea textArea;
	private JFrame frame;

	private JTable jTable;
	private DefaultTableModel tableModel;

	public TableWindow() {

		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		initWindow();
	}

	private void initWindow() {

		frame = new JFrame("���ݿ��");

		JMenuBar menuBar = menuBar();
		frame.setJMenuBar(menuBar);

		initCenterPanel();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		initFrame(frame);
	}
	
	private void initCenterPanel(){
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		JPanel centerPanel = centerPanel();
		contentPanel.add(centerPanel, BorderLayout.CENTER);

		JPanel southPanel = southPanel();
		contentPanel.add(southPanel, BorderLayout.SOUTH);

		frame.setContentPane(contentPanel);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * @return
	 */
	private JMenuBar menuBar() {

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createEmptyBorder(3, 2, 5, 2));

		JMenu dsMenu = new JMenu("����Դ");
		JMenuItem createMenu = new JMenuItem("����Դ");
		createMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				KeyEvent.CTRL_MASK));
		createMenu.addActionListener(new createMenuAction());
		dsMenu.add(createMenu);
		dsMenu.add(new JSeparator(SwingConstants.HORIZONTAL));

		JMenuItem menuItem = new JMenuItem("���ļ�����Դ");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				KeyEvent.CTRL_MASK));
		menuItem.addActionListener(new openPropsAction());
		dsMenu.add(menuItem);

//		JMenuItem menuItem2 = new JMenuItem("Դ�ļ�����");
//		menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
//				KeyEvent.CTRL_MASK));
//		dsMenu.add(menuItem2);
		menuBar.add(dsMenu);

		JMenu voMenu = new JMenu("VO����");
		JMenuItem tablesMenu = new JMenuItem("ѡ�����ݿ��");
		tablesMenu.addActionListener(new ViewTablesAction());
		voMenu.add(tablesMenu);
		menuBar.add(voMenu);

		// menuBar.setHelpMenu(new JMenu("����"));

		return menuBar;
	}

	/**
	 * @param frame
	 */
	private void initFrame(JFrame frame) {
		frame.pack();
		frame.setVisible(true);
		GraphicsEnvironment environment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice device = environment.getDefaultScreenDevice();
		GraphicsConfiguration configuration = device.getDefaultConfiguration();
		Rectangle bounds = configuration.getBounds();
		frame.setLocation(bounds.x + 350, bounds.y + 200);
		frame.setSize(new Dimension(700, 500));
		BorderLayout manager = new BorderLayout(5, 5);
		frame.setLayout(manager);
		// frame.setFont(new Font("SansSerif", Font.PLAIN, 2));
	}

	/**
	 * @return
	 */
	private JPanel southPanel() {
		JPanel southPanel = new JPanel(new GridLayout(1, 1));
		southPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		textArea = new JTextArea(10, 60);
		textArea.setAutoscrolls(true);
		textArea.setEditable(false);

		southPanel.setAutoscrolls(true);
		southPanel.add(textArea);

		return southPanel;
	}

	/**
	 * @return
	 */
	private JPanel centerPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		GridLayout mgr = new GridLayout(3, 4);
		mgr.setHgap(8);
		topPanel.setLayout(mgr);

		JLabel typeLabel = new JLabel("���ݿ�����");
		typeComboBox = new JComboBox(new String[] { "Mysql���ݿ�" });
		JLabel serverLabel = new JLabel("����������");
		serverField = new JTextField("192.169.100.250");
		topPanel.add(typeLabel);
		topPanel.add(typeComboBox);
		topPanel.add(serverLabel);
		topPanel.add(serverField);

		JLabel dbNameLabel = new JLabel("���ݿ�����");
		dbNameField = new JTextField("tianya_");
		JLabel portLabel = new JLabel("�������˿�");
		portField = new JTextField("3306");
		topPanel.add(dbNameLabel);
		topPanel.add(dbNameField);
		topPanel.add(portLabel);
		topPanel.add(portField);

		JLabel userNameLabel = new JLabel("�û���");
		userNameField = new JTextField();
		JLabel passwordLabel = new JLabel("����");
		passwordField = new JTextField();
		topPanel.add(userNameLabel);
		topPanel.add(userNameField);
		topPanel.add(passwordLabel);
		topPanel.add(passwordField);

		JPanel footPanel = new JPanel();
		GridLayout mgr2 = new GridLayout(1, 2);
		mgr2.setHgap(8);
		footPanel.setLayout(mgr2);
		JButton testButton = new JButton("��������");
		testButton.addActionListener(new TestConnectionAction());

		JButton saveButton = new JButton("�������Ե��ļ�");
		saveButton.addActionListener(new SavePropsAction());
		footPanel.setSize(300, 50);
		footPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		footPanel.add(testButton);
		footPanel.add(saveButton);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout(5, 5));
		centerPanel.add(topPanel, BorderLayout.CENTER);
		centerPanel.add(footPanel, BorderLayout.SOUTH);

		return centerPanel;
	}

	private class TestConnectionAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {
				ConnectionUtils.getConnection(getProperties());

				textArea.append("�������ӳɹ�!\n");

			} catch (Exception e1) {
				textArea.append("��������ʧ��!\n");
				textArea.append(e1.getMessage());
				textArea.append("\n");
			}
		}
	}

	private Properties getProperties() {
		Properties dbProps = new Properties();
		dbProps.put("dbType", typeComboBox.getSelectedItem().toString());
		dbProps.put("host", serverField.getText());
		dbProps.put("port", portField.getText());
		dbProps.put("dbName", dbNameField.getText());
		dbProps.put("user", userNameField.getText());
		dbProps.put("password", passwordField.getText());
		return dbProps;
	}

	private void setFieldFromProperties(Properties dbProps) {
		typeComboBox.setSelectedItem(dbProps.get("dbType"));
		serverField.setText(dbProps.get("host").toString());
		portField.setText(dbProps.get("port").toString());
		dbNameField.setText(dbProps.get("dbName").toString());
		userNameField.setText(dbProps.get("user").toString());
		passwordField.setText(dbProps.get("password").toString());
	}

	private class ViewTablesAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				JPanel contentPanel = new JPanel();
				contentPanel.setLayout(new BorderLayout());

				JPanel centerPanel = new JPanel();
				centerPanel.setLayout(new BorderLayout());
				Properties props = getProperties();
				Connection conn = ConnectionUtils.getConnection(props);
				List<String> tables = DbUtil.getTables(conn,
						props.getProperty("user"), props.getProperty("dbName"));
				Object[][] data = new Object[tables.size()][2];
				System.out.println(tables);
				for (int i = 0; i < tables.size(); i++) {
					data[i][0] = i+1;
					data[i][1] = tables.get(i);
				}
				tableModel = new DefaultTableModel(data, new String[] { "ѡ��",
						"����" });
				jTable = new JTable(tableModel);
				// jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				JScrollPane scrollpane = new JScrollPane(jTable);
				centerPanel.add(scrollpane, BorderLayout.CENTER);

				JButton testButton = new JButton("����VO");
				testButton.addActionListener(new createVOAction());
				JPanel buttonPanel = new JPanel();
				buttonPanel.setLayout(new GridLayout(1, 1));
				buttonPanel.add(testButton);
				centerPanel.add(buttonPanel, BorderLayout.SOUTH);

				buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20,
						10, 20));

				contentPanel.add(centerPanel, BorderLayout.CENTER);

				JPanel southPanel = southPanel();
				contentPanel.add(southPanel, BorderLayout.SOUTH);

				frame.setContentPane(contentPanel);
				frame.setVisible(true);
			} catch (Exception e1) {
				textArea.append("����ʧ��!\n");
				textArea.append(e1.getMessage());
				textArea.append("\n");
			}
		}
	}
	
	private class createMenuAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			initCenterPanel();
		}

	}

	private class createVOAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				// TODO Auto-generated method stub
				int[] selectedRows = jTable.getSelectedRows();
				if(selectedRows.length==0){
					textArea.append("û��ѡ���κα�!\n");
					return;
				}
				List<String> list = new ArrayList<String>();
				for (int i : selectedRows) {
					list.add(tableModel.getValueAt(i, 1).toString());
				}
				System.out.println(list);
				Connection conn = ConnectionUtils
						.getConnection(getProperties());
				DbTableVOCodeGenerator codeGenerator = new DbTableVOCodeGenerator(
						conn);
				String[] a = new String[]{};
				BeanInfo[] beanInfos = codeGenerator.generate(
						"vo1", list.toArray(a));
				JavaFileUtils.createJavaFile(null, beanInfos);
				textArea.append("����VO"+list+"���!\n");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	private class openPropsAction implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			// �½��ļ��Ի��򣬲�����Ϊ�򿪵ķ�ʽ
			FileDialog filedlg = new FileDialog(frame, "��");
			// �����ļ��Ի���ı���
			filedlg.setTitle("�ļ�ѡ��");
			filedlg.setFilenameFilter(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					return name.endsWith(".properties");
				}
			});
			// ���ó�ʼ·��
			filedlg.setDirectory("./conf/common/");
			filedlg.setVisible(true);
			// filedlg.setFilterPath("SystemRoot");
			// ���ļ��Ի��򣬷���ѡ���ļ��ľ���·��
			String file = filedlg.getFile();
			if(file==null)return;
			String selected = filedlg.getDirectory() + file;
			System.out.println("��ѡ�е��ļ�·��Ϊ��" + selected);
			initCenterPanel();
			Properties props = new Properties();
			try {
				props.load(new FileInputStream(new File(selected)));
				setFieldFromProperties(props);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	private class SavePropsAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			URL url = Thread.currentThread().getContextClassLoader()
					.getResource("./");
			String basePath = new File(url.getFile()).getParent()
					+ "/conf/common/";
			File dir = new File(basePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String fileName = basePath + "db_" + dbNameField.getText()
					+ ".properties";

			OutputStream outputStream = null;
			BufferedOutputStream bos = null;
			try {
				outputStream = new FileOutputStream(fileName);
				bos = new BufferedOutputStream(outputStream);
				getProperties().store(bos, "");

				textArea.append("����ɹ�! �ļ���Ϊ" + fileName + "\n");

			} catch (Exception e1) {
				textArea.append("����ʧ��!\n");
				textArea.append(e1.getMessage());
				textArea.append("\n");
			}finally{
				try {
					bos.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					outputStream.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		new TableWindow();
	}

}
