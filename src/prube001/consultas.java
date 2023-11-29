package prube001;

import java.awt.EventQueue;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class consultas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					consultas con = new consultas();
					con.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public consultas() {
		setType(Type.POPUP);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 487);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		/////////////////////////////////
		JPanel CHB = new JPanel();
		CHB.setBounds(10, 112, 367, 229);
		contentPane.add(CHB);
		CHB.setLayout(null);

		////////////////////////////////
		JComboBox TBs = new JComboBox();
		JComboBox BDs = new JComboBox();
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "12345";

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW DATABASES");

            while (rs.next()) {
                String dbName = rs.getString("Database");
                BDs.addItem(dbName);
            }

            BDs.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedDB = (String) BDs.getSelectedItem();
                    TBs.removeAllItems();

                    try {
                        Statement stmt2 = con.createStatement();
                        ResultSet rs2 = stmt2.executeQuery("SHOW TABLES FROM " + selectedDB);

                        while (rs2.next()) {
                            String tableName = rs2.getString("Tables_in_" + selectedDB);
                            TBs.addItem(tableName);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
		

        TBs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTable = (String) TBs.getSelectedItem();
                if (selectedTable != null) {
                    CHB.removeAll();

                    try {
                        String selectedDB = (String) BDs.getSelectedItem();
                        Connection con2 = DriverManager.getConnection(url + selectedDB, user, password);
                        Statement stmt3 = con2.createStatement();
                        ResultSet rs3 = stmt3.executeQuery("SHOW COLUMNS FROM " + selectedTable);

                        int x = 33;
                        int y = 5;
                        while (rs3.next()) {
                            String columnName = rs3.getString("Field");
                            JCheckBox checkBox = new JCheckBox(columnName);
                            checkBox.setBounds(x, y, 97, 23);
                            CHB.add(checkBox);
                            x += 102;
                            if (x > 237) {
                                x = 33;
                                y += 28;
                            }
                        }
                        CHB.revalidate();
                        CHB.repaint();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


		TBs.setBounds(240, 36, 137, 22);
		contentPane.add(TBs);
		
		
		
		
		//////////////////////////////////////
		
		BDs.setBounds(10, 36, 137, 22);
		contentPane.add(BDs);
		//////////////////////////////////////
		

		JLabel lblNewLabel = new JLabel("Bases de datos");
		lblNewLabel.setBounds(42, 11, 78, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Tablas");
		lblNewLabel_1.setBounds(285, 11, 46, 14);
		contentPane.add(lblNewLabel_1);

		table = new JTable();
		table.setBounds(399, 11, 435, 391);
		contentPane.add(table);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(125, 352, 89, 23);
		contentPane.add(btnNewButton);
		
		
		JLabel lblNewLabel_2 = new JLabel("Columnas");
		lblNewLabel_2.setBounds(168, 86, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		
		
	}
}
