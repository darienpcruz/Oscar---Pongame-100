package prube001;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Window.Type;
import java.awt.Toolkit;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

public class BD extends JFrame {

	public static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public static void main (String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BD frame = new BD();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BD() {
		setType(Type.POPUP);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Theda\\Downloads\\Imagen1aaaa.png"));
		setBackground(new Color(102, 153, 153));
		setTitle("Gestion de bases de datos");
		setResizable(false);


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 965, 478);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 153, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBackground(new Color(102, 153, 153));
		menuBar.setForeground(new Color(102, 153, 204));
		menuBar.setBounds(0, 0, 949, 23);
		contentPane.add(menuBar);

		JMenu mnNewMenu = new JMenu("Opciones");
		mnNewMenu.setBackground(new Color(102, 153, 153));
		menuBar.add(mnNewMenu);

		// Crea el JTree una vez y mantenlo como una variable de instancia

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Bases de datos");
		DefaultTreeModel model = new DefaultTreeModel(root);
		JTree tree = new JTree(model);
		tree.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tree.setBackground(new Color(255, 255, 255));
		tree.setVisibleRowCount(5);
		tree.setBounds(10, 33, 384, 297);
		contentPane.add(tree);

		// Crear un JScrollPane para la tabla
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane.setBounds(404, 34, 535, 297); // Ajusta estos valores para cambiar el tamaño del JScrollPane
		contentPane.add(jScrollPane);
		
		
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 153, 255));
		panel.setBounds(404, 342, 535, 39);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Codigo MySQL");
		lblNewLabel.setBackground(new Color(102, 153, 255));
		lblNewLabel.setBounds(10, 11, 133, 14);
		panel.add(lblNewLabel);
		
		JTextPane Querymysql = new JTextPane();
		Querymysql.setEditable(false);
		Querymysql.setBounds(171, 11, 354, 20);
		panel.add(Querymysql);
		/////////
		
		// CRET
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setIcon(new ImageIcon("C:\\Users\\Theda\\Downloads\\Crear Tabla (3).png"));
		btnNewButton_3.setEnabled(false);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (node == null || node.getParent() != root)
					return;

				String dbName = node.getUserObject().toString();

				JTextField tableNameField = new JTextField();
				JTextField columnCountField = new JTextField();

				Object[] message = { "Nombre de la tabla:", tableNameField, "Número de columnas:", columnCountField };

				int option = JOptionPane.showConfirmDialog(null, message, "Crear tabla", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					String tableName = tableNameField.getText();
					int columnCount = Integer.parseInt(columnCountField.getText());

					try {
						// Asegúrate de tener el driver de MySQL en tu classpath
						Class.forName("com.mysql.cj.jdbc.Driver");

						// Establece la conexión con tu servidor MySQL
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, "root",
								"12345");
						Statement stmt = con.createStatement();

						// Crea la nueva tabla sin columnas
						stmt.executeUpdate("CREATE TABLE " + tableName + " (dummy_column INT)");

						for (int i = 0; i < columnCount; i++) {
							JTextField columnNameField = new JTextField();
							JComboBox<String> columnTypeField = new JComboBox<>(
									new String[] { "INT", "VARCHAR", "TEXT", "DATE" });
							JTextField columnLengthField = new JTextField();
							JCheckBox primaryKeyCheckbox = new JCheckBox("Primary Key");
							JCheckBox autoIncrementCheckbox = new JCheckBox("Auto Increment");
							JCheckBox nullableCheckbox = new JCheckBox("Nullable");

							Object[] columnMessage = { "Nombre de la columna:", columnNameField,
									"Tipo de dato de la columna:", columnTypeField, "Longitud del dato:",
									columnLengthField, "Opciones de la columna:", primaryKeyCheckbox,
									autoIncrementCheckbox, nullableCheckbox };

							option = JOptionPane.showConfirmDialog(null, columnMessage, "Crear columna",
									JOptionPane.OK_CANCEL_OPTION);
							if (option != JOptionPane.OK_OPTION)
								break;

							String columnName = columnNameField.getText();
							String columnType = columnTypeField.getSelectedItem().toString();
							String columnLength = columnLengthField.getText();
							String columnOptions = "";
							if (primaryKeyCheckbox.isSelected())
								columnOptions += " PRIMARY KEY";
							if (autoIncrementCheckbox.isSelected())
								columnOptions += " AUTO_INCREMENT";
							if (!nullableCheckbox.isSelected())
								columnOptions += " NOT NULL";

							// Agrega la columna a la tabla
							stmt.executeUpdate("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " "
									+ columnType + "(" + columnLength + ")" + columnOptions);
						}

						// Elimina la columna dummy
						stmt.executeUpdate("ALTER TABLE " + tableName + " DROP COLUMN dummy_column");

						// Agrega la nueva tabla al JTree
						DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(tableName);
						model.insertNodeInto(tableNode, node, node.getChildCount());

						stmt.close();
						con.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					} catch (ClassNotFoundException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		btnNewButton_3.setBounds(257, 341, 125, 40);
		contentPane.add(btnNewButton_3); // Fin

		// Eliminar
		// ELM
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\Theda\\Downloads\\Crear Tabla (2).png"));
		btnNewButton_2.setEnabled(false);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (node == null || node.getParent().getParent() != root)
					return;

				String dbName = ((DefaultMutableTreeNode) node.getParent()).getUserObject().toString();
				String tableName = node.getUserObject().toString();

				int dialogResult = JOptionPane.showConfirmDialog(null,
						"¿Estás seguro de que quieres eliminar la tabla " + tableName + "?", "Advertencia",
						JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					try {
						// Asegúrate de tener el driver de MySQL en tu classpath
						Class.forName("com.mysql.cj.jdbc.Driver");

						// Establece la conexión con tu servidor MySQL
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, "root",
								"12345");
						Statement stmt = con.createStatement();

						// Elimina la tabla
						stmt.executeUpdate("DROP TABLE " + tableName);

						// Elimina la tabla del JTree
						model.removeNodeFromParent(node);

						stmt.close();
						con.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					} catch (ClassNotFoundException ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		btnNewButton_2.setBounds(257, 389, 125, 40);
		contentPane.add(btnNewButton_2);// Fin eliminar
		///
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.setIcon(new ImageIcon("C:\\Users\\Theda\\Downloads\\Añadir un título.png"));
		btnNewButton_4.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent event) {
		        // Asumiendo que 'tree' es tu JTree
		        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		        if (selectedNode != null) {
		            // Aquí asumimos que el nodo padre del nodo seleccionado es el nombre de la base de datos
		            String dbName = selectedNode.getParent().toString();
		            String tableName = selectedNode.getUserObject().toString();

		            // Crear una nueva ventana JFrame
		            JFrame frame = new JFrame("Agregar registro");
		            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		            frame.getContentPane().setLayout(new GridLayout(0, 2, 15, 15));  // Aumenta el espacio entre los componentes
		            frame.setSize(384, 350);  // Ajusta el tamaño del JFrame a 384x350
		            frame.setLocationRelativeTo(null);  // Esto hace que la ventana aparezca en el centro de la pantalla
		            frame.getContentPane().setBackground(Color.decode("#669999"));  // Cambia el color de fondo
		            ((JComponent) frame.getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));  // Agrega un margen a la ventana

		            try {
		                // Carga el driver de MySQL
		                Class.forName("com.mysql.cj.jdbc.Driver");

		                // Establece la conexión con tu servidor MySQL
		                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, "root", "12345");

		                Statement stmt = conn.createStatement();
		                ResultSet rs = stmt.executeQuery("DESCRIBE " + tableName);
		                List<JTextField> textFields = new ArrayList<>();
		                while (rs.next()) {
		                    String columnName = rs.getString("Field");
		                    JLabel label = new JLabel(columnName);
		                    JTextField textField = new JTextField(20);  // Aumenta el tamaño de las cajas de texto
		                    label.setBorder(new EmptyBorder(10, 10, 10, 10));  // Agrega un margen a la etiqueta
		                    textField.setBorder(new EmptyBorder(10, 10, 10, 10));  // Agrega un margen al campo de texto
		                    frame.getContentPane().add(label);
		                    frame.getContentPane().add(textField);
		                    textFields.add(textField);
		                }

		                JButton submitButton = new JButton("Submit");
		                submitButton.setBorder(new EmptyBorder(10, 10, 10, 10));  // Agrega un margen al botón
		                submitButton.addActionListener(new ActionListener() {
		                    public void actionPerformed(ActionEvent e) {
		                        String id = textFields.get(0).getText();
		                        if (!id.matches("[0-9]+")) {
		                            JOptionPane.showMessageDialog(frame, "El ID no puede contener letras, símbolos o números negativos.");
		                            return;
		                        }

		                        int confirm = JOptionPane.showConfirmDialog(frame, "¿Estás seguro de que quieres agregar este registro?", "Confirmar inserción", JOptionPane.YES_NO_OPTION);
		                        if (confirm == JOptionPane.YES_OPTION) {
		                            try {
		                                String query = "INSERT INTO " + tableName + " VALUES(";
		                                for (JTextField textField : textFields) {
		                                    String text = textField.getText();
		                                    query += "'" + text + "', ";
		                                }
		                                query = query.substring(0, query.length() - 2) + ")";
		                                stmt.executeUpdate(query);

		                                // Muestra el comando SQL en el JTextPane del JFrame principal
		                                // Asumiendo que 'Querymysql' es tu JTextPane en el JFrame principal
		                                Querymysql.setText(query);

		                                // Mensaje de éxito
		                                JOptionPane.showMessageDialog(frame, "Los datos se han añadido correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		                                
		                                // Cierra la ventana
		                                frame.dispose();
		                                
		                                // Actualiza el JScrollPane
		                                // Asumiendo que 'scrollPane' es tu JScrollPane
		                                // Asumiendo que 'table' es tu JTable
		                             
		                            } catch (SQLException ex) {
		                                // Muestra un mensaje de error con la información de la excepción
		                                JOptionPane.showMessageDialog(frame, "Error al agregar los datos: " + ex.getMessage());
		                            }
		                        }
		                    }
		                });

		                frame.getContentPane().add(submitButton);

		                frame.pack();
		                frame.setVisible(true);
		            } catch (ClassNotFoundException | SQLException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		});


		btnNewButton_4.setEnabled(false);
		btnNewButton_4.setBounds(415, 389, 155, 40);
		contentPane.add(btnNewButton_4);

		JButton btnNewButton_4_1 = new JButton("");
		btnNewButton_4_1.setIcon(new ImageIcon("C:\\Users\\Theda\\Downloads\\Añadir un título (1).png"));
		btnNewButton_4_1.setEnabled(false);
		btnNewButton_4_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent event) {
		        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		        if (selectedNode != null) {
		            String dbName = selectedNode.getParent().toString();
		            String tableName = selectedNode.getUserObject().toString();

		            while (true) {
		                String inputValue = JOptionPane.showInputDialog("Por favor, introduce el valor de la primera columna del registro que deseas eliminar");
		                if (inputValue == null) {
		                    break;
		                } else if (!inputValue.isEmpty()) {
		                    try {
		                        int id = Integer.parseInt(inputValue);
		                        if (id < 0) {
		                            throw new NumberFormatException();
		                        }

		                        Class.forName("com.mysql.cj.jdbc.Driver");
		                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, "root", "12345");

		                        Statement stmt = conn.createStatement();
		                        String query = "SELECT * FROM " + tableName + " WHERE " + tableName + ".id = " + id;
		                        ResultSet rs = stmt.executeQuery(query);
		                        if (rs.next()) {
		                            int dialogResult = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres eliminar este registro?", "Advertencia", JOptionPane.YES_NO_OPTION);
		                            if (dialogResult == JOptionPane.YES_OPTION) {
		                                String deleteQuery = "DELETE FROM " + tableName + " WHERE " + tableName + ".id = " + id;
		                                stmt.executeUpdate(deleteQuery);
		                                JOptionPane.showMessageDialog(null, "Registro eliminado con éxito");
		                                Querymysql.setText(deleteQuery); // Agrega esta línea para mostrar la consulta SQL en tu JTextPane
		                            }
		                            break;
		                        } else {
		                            JOptionPane.showMessageDialog(null, "No se encontró ningún registro con ese valor. Por favor, inténtalo de nuevo.");
		                        }
		                    } catch (NumberFormatException e) {
		                        JOptionPane.showMessageDialog(null, "El valor introducido no es válido. Por favor, introduce un número positivo.");
		                    } catch (ClassNotFoundException | SQLException e) {
		                        e.printStackTrace();
		                    }
		                } else {
		                    JOptionPane.showMessageDialog(null, "El valor introducido no es válido. Por favor, inténtalo de nuevo.");
		                }
		            }
		        }
		    }
		});


		
		
		
		
		btnNewButton_4_1.setBounds(600, 389, 155, 40);
		contentPane.add(btnNewButton_4_1);

		JButton btnNewButton_4_1_1 = new JButton("");
		btnNewButton_4_1_1.setIcon(new ImageIcon("C:\\Users\\Theda\\Downloads\\Añadir un título (2).png"));
		btnNewButton_4_1_1.setEnabled(false);
		btnNewButton_4_1_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent event) {
		        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		        if (selectedNode != null) {
		            String dbName = selectedNode.getParent().toString();
		            String tableName = selectedNode.getUserObject().toString();

		            JFrame frame = new JFrame("Actualizar registro");
		            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		            frame.getContentPane().setLayout(new GridLayout(0, 2, 15, 15));
		            frame.setSize(800, 600);
		            frame.setLocationRelativeTo(null);
		            frame.getContentPane().setBackground(Color.decode("#669999"));
		            frame.setResizable(false);
		            ((JComponent) frame.getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));

		            try {
		                Class.forName("com.mysql.cj.jdbc.Driver");
		                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, "root", "12345");
		                Statement stmt = conn.createStatement();
		                ResultSet rs = stmt.executeQuery("DESCRIBE " + tableName);
		                List<JTextField> textFields = new ArrayList<>();
		                while (rs.next()) {
		                    String columnName = rs.getString("Field");
		                    JLabel label = new JLabel(columnName);
		                    JTextField textField = new JTextField(20);
		                    textField.setName(columnName);  // Asegúrate de que cada campo de texto tenga un nombre que corresponda al nombre de la columna en la base de datos
		                    label.setBorder(new EmptyBorder(10, 10, 10, 10));
		                    textField.setBorder(new EmptyBorder(10, 10, 10, 10));
		                    frame.getContentPane().add(label);
		                    frame.getContentPane().add(textField);
		                    textFields.add(textField);
		                }

		                JButton submitButton = new JButton("Actualizar");
		                submitButton.setBorder(new EmptyBorder(10, 10, 10, 10));
		                submitButton.addActionListener(new ActionListener() {
		                    public void actionPerformed(ActionEvent e) {
		                        String id = textFields.get(0).getText();
		                        String query = "UPDATE " + tableName + " SET ";
		                        for (int i = 1; i < textFields.size(); i++) {
		                            String text = textFields.get(i).getText();
		                            if (text.isEmpty()) {
		                                JOptionPane.showMessageDialog(frame, "Por favor, rellena todos los campos.");
		                                return;
		                            }
		                            query += textFields.get(i).getName() + " = '" + text + "', ";
		                        }
		                        query = query.substring(0, query.length() - 2) + " WHERE " + textFields.get(0).getName() + " = '" + id + "'";

		                        Querymysql.setText(query);  // Muestra la consulta SQL en Querymysql

		                        int dialogResult = JOptionPane.showConfirmDialog(frame, "¿Estás seguro de que quieres actualizar los datos?", "Confirmar actualización", JOptionPane.YES_NO_OPTION);
		                        if (dialogResult == JOptionPane.YES_OPTION) {
		                            try {
		                                ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE " + textFields.get(0).getName() + " = '" + id + "'");
		                                if (!rs.next()) {
		                                    JOptionPane.showMessageDialog(frame, "El registro no existe. Por favor, ingresa un registro existente.");
		                                } else {
		                                    stmt.executeUpdate(query);
		                                    JOptionPane.showMessageDialog(frame, "Los datos se han actualizado correctamente");
		                                    frame.dispose();  // Cierra la ventana después de actualizar los datos
		                                }
		                            } catch (SQLException ex) {
		                                JOptionPane.showMessageDialog(frame, "Error al actualizar los datos: " + ex.getMessage());
		                            }
		                        }
		                    }
		                });
		                frame.getContentPane().add(submitButton);

		                frame.pack();
		                frame.setVisible(true);
		            } catch (ClassNotFoundException | SQLException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		});










		btnNewButton_4_1_1.setBounds(784, 389, 155, 40);
		contentPane.add(btnNewButton_4_1_1);
		// Tree funcion
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				btnNewButton_4.setEnabled(true);
				btnNewButton_4_1.setEnabled(true);
				btnNewButton_4_1_1.setEnabled(true);
				btnNewButton_3.setEnabled(true);
				btnNewButton_2.setEnabled(true);
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (node == null || node.isRoot() || !node.isLeaf())
					return;

				// Obtener la base de datos del nodo padre
				DefaultMutableTreeNode databaseNode = (DefaultMutableTreeNode) node.getParent();
				String database = databaseNode.getUserObject().toString();

				// Obtener la tabla del nodo seleccionado
				String table = node.getUserObject().toString();

				String url = "jdbc:mysql://localhost:3306/" + database;
				String usuario = "root";
				String contrasena = "12345";
				Connection conexion;

				try {
					conexion = DriverManager.getConnection(url, usuario, contrasena);
					Statement declaracion = conexion.createStatement();
					ResultSet resultado = declaracion.executeQuery("SELECT * FROM " + table);

					// Inicializar nombres de columnas y datos de la tabla
					ResultSetMetaData metaData = resultado.getMetaData();
					int numeroDeColumnas = metaData.getColumnCount();
					Vector<String> nombresColumnas = new Vector<>();
					Vector<Vector<Object>> datosTabla = new Vector<>();

					for (int columna = 1; columna <= numeroDeColumnas; columna++) {
						nombresColumnas.add(metaData.getColumnName(columna));
					}

					while (resultado.next()) {
						Vector<Object> vector = new Vector<>();
						for (int columnIndex = 1; columnIndex <= numeroDeColumnas; columnIndex++) {
							vector.add(resultado.getObject(columnIndex));
						}
						datosTabla.add(vector);
					}

					JTable jTable = new JTable(datosTabla, nombresColumnas);

					// Limpiar el jScrollPane antes de agregar la nueva tabla
					jScrollPane.setViewportView(jTable);
					jScrollPane.revalidate();
					jScrollPane.repaint();

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});

		// Boton Crear
		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(new Color(102, 153, 153));
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\Theda\\Downloads\\Agregar Base de datos (5).png"));
		btnNewButton.setSelectedIcon(new ImageIcon("C:\\Users\\Theda\\Downloads\\BT001 (1).png"));
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dbName = JOptionPane.showInputDialog("Ingresa el nombre de la nueva base de datos:");
				if (dbName != null) {
					try {
						// Asegúrate de tener el driver de MySQL en tu classpath
						Class.forName("com.mysql.cj.jdbc.Driver");

						// Establece la conexión con tu servidor MySQL
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "12345");
						Statement stmt = con.createStatement();

						// Crea la nueva base de datos
						stmt.executeUpdate("CREATE DATABASE " + dbName);

						// Agrega la nueva base de datos al JTree
						root.removeAllChildren();
						model.reload();
						DatabaseMetaData meta = con.getMetaData();
						ResultSet res = meta.getCatalogs();
						while (res.next()) {
							String db = res.getString("TABLE_CAT");
							DefaultMutableTreeNode databaseNode = new DefaultMutableTreeNode(db);

							// Obtiene las tablas de la base de datos
							ResultSet tables = meta.getTables(db, null, null, new String[] { "TABLE" });
							while (tables.next()) {
								String tableName = tables.getString("TABLE_NAME");
								DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(tableName);
								model.insertNodeInto(tableNode, databaseNode, databaseNode.getChildCount());
							}
							tables.close();

							model.insertNodeInto(databaseNode, root, root.getChildCount());
						}

						stmt.close();
						con.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					} catch (ClassNotFoundException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(28, 341, 106, 40);
		contentPane.add(btnNewButton); // Fin boton Crear

		// Innicio boton eliminar
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setSelectedIcon(new ImageIcon("C:\\Users\\Theda\\Downloads\\BT02.png"));
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\Theda\\Downloads\\Agregar Base de datos (6).png"));
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dbName = JOptionPane.showInputDialog("Ingresa el nombre de la base de datos a eliminar:");
				if (dbName != null) {
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"¿Estás seguro de que quieres eliminar la base de datos " + dbName + "?", "Advertencia",
							JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						try {
							// Asegúrate de tener el driver de MySQL en tu classpath
							Class.forName("com.mysql.cj.jdbc.Driver");

							// Establece la conexión con tu servidor MySQL
							Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root",
									"12345");
							Statement stmt = con.createStatement();

							// Elimina la base de datos
							stmt.executeUpdate("DROP DATABASE " + dbName);

							// Elimina la base de datos del JTree
							for (int i = 0; i < root.getChildCount(); i++) {
								DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);
								if (node.toString().equals(dbName)) {
									model.removeNodeFromParent(node);
									break;
								}
							}

							root.removeAllChildren();
							model.reload();
							DatabaseMetaData meta = con.getMetaData();
							ResultSet res = meta.getCatalogs();
							while (res.next()) {
								String db = res.getString("TABLE_CAT");
								DefaultMutableTreeNode databaseNode = new DefaultMutableTreeNode(db);

								// Obtiene las tablas de la base de datos
								ResultSet tables = meta.getTables(db, null, null, new String[] { "TABLE" });
								while (tables.next()) {
									String tableName = tables.getString("TABLE_NAME");
									DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(tableName);
									model.insertNodeInto(tableNode, databaseNode, databaseNode.getChildCount());
								}
								tables.close();

								model.insertNodeInto(databaseNode, root, root.getChildCount());
							}

							stmt.close();
							con.close();
						} catch (SQLException ex) {
							ex.printStackTrace();
						} catch (ClassNotFoundException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});
		btnNewButton_1.setBounds(28, 389, 106, 40);
		contentPane.add(btnNewButton_1); // Fin boton eliminar
		
		
		// Boton y logica
		JMenuItem BDA = new JMenuItem("Bases de datos");
		BDA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Limpia el nodo raíz antes de agregar nuevos nodos
				root.removeAllChildren();
				model.reload();
				
				btnNewButton.setEnabled(true);
				btnNewButton_1.setEnabled(true);

				try {
					// Asegúrate de tener el driver de MySQL en tu classpath
					Class.forName("com.mysql.cj.jdbc.Driver");

					// Establece la conexión con tu servidor MySQL
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "12345");
					DatabaseMetaData meta = con.getMetaData();
					ResultSet res = meta.getCatalogs();
					while (res.next()) {
						String db = res.getString("TABLE_CAT");
						DefaultMutableTreeNode databaseNode = new DefaultMutableTreeNode(db);

						// Obtiene las tablas de la base de datos
						ResultSet tables = meta.getTables(db, null, null, new String[] { "TABLE" });
						while (tables.next()) {
							String tableName = tables.getString("TABLE_NAME");
							DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(tableName);
							model.insertNodeInto(tableNode, databaseNode, databaseNode.getChildCount());
						}
						tables.close();

						model.insertNodeInto(databaseNode, root, root.getChildCount());
					}
					res.close();
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace();
				}
			}
		});
		mnNewMenu.add(BDA);// Fin menu log

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Secreto");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String konamiCode = "Arriba, Arriba, Abajo, Abajo, Izquierda, Derecha, Izquierda, Derecha, B, A";
		        String input = JOptionPane.showInputDialog("Ingresa el código Konami:");
		        if (konamiCode.equals(input)) {
		        	secreto secreto = new secreto();
					secreto.setVisible(true);
		            dispose();
		        }
		    }
		});

		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Cerrar Conexion");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)  {
		    	Inicio frame1 = new Inicio();
				frame1.setVisible(true);
				dispose();
		    }
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		
		JMenu mnNewMenu_1 = new JMenu("Herramientas");
		mnNewMenu_1.setBackground(new Color(102, 153, 153));
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Consultas");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultas con = new consultas();
				con.setVisible(true);
				dispose();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Modelo relacional");
		mnNewMenu_1.add(mntmNewMenuItem_3);
		

		// Boton CBD

		
	}
}
