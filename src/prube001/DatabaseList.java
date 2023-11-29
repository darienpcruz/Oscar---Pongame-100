package prube001;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class DatabaseList {
    private static Connection conn;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bases de Datos");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JTree tree = new JTree();
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            fillTree(root);

            frame.add(new JScrollPane(tree), BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                            System.out.println("Conexión cerrada");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        });
    }

    private static void fillTree(DefaultMutableTreeNode root) {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "12345";

        try {
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                ResultSet res = meta.getCatalogs();

                while (res.next()) {
                    String db = res.getString("TABLE_CAT");

                    // Ignorar las bases de datos del sistema
                    if (db.equals("information_schema") || db.equals("mysql") || db.equals("performance_schema") || db.equals("sys")) {
                        continue;
                    }

                    DefaultMutableTreeNode dbNode = new DefaultMutableTreeNode(db);
                    root.add(dbNode);

                    Statement stmt1 = conn.createStatement();
                    ResultSet tables = stmt1.executeQuery("SHOW TABLES FROM " + db);
                    while (tables.next()) {
                        String table = tables.getString(1);
                        DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(table);
                        dbNode.add(tableNode);

                        Statement stmt2 = conn.createStatement();
                        ResultSet columns = stmt2.executeQuery("SHOW COLUMNS FROM " + db + "." + table);
                        while (columns.next()) {
                            String column = columns.getString("Field");
                            DefaultMutableTreeNode columnNode = new DefaultMutableTreeNode(column);
                            tableNode.add(columnNode);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al conectar con la base de datos");
            ex.printStackTrace();
        }
    }
}


