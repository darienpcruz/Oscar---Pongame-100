package prube001;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Window.Type;
import java.awt.Toolkit;
import javax.swing.DropMode;
import javax.swing.SwingConstants;

public class Inicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField UserFiel;
	private JTextField HoFiel;
	private JTextField PoFiel;
	private JPasswordField ConFiel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio frame1 = new Inicio();
					frame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Inicio() {
		setTitle("Inicia secion en el sistema");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Theda\\Downloads\\Imagen1aaaa.png"));
		setType(Type.POPUP);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 763, 477);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 153, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		UserFiel = new JTextField();
		UserFiel.setHorizontalAlignment(SwingConstants.LEFT);
		UserFiel.setBounds(482, 63, 244, 40);
		contentPane.add(UserFiel);
		UserFiel.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("USUARIO");
		lblNewLabel.setBackground(new Color(0, 0, 0));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Fuzzy Bubbles", Font.BOLD, 13));
		lblNewLabel.setBounds(374, 65, 63, 27);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("CONTRASEÑA");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Fuzzy Bubbles", Font.BOLD, 13));
		lblNewLabel_1.setBounds(374, 129, 98, 27);
		contentPane.add(lblNewLabel_1);
		
		HoFiel = new JTextField();
		HoFiel.setBounds(482, 199, 244, 40);
		contentPane.add(HoFiel);
		HoFiel.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("HOST");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Fuzzy Bubbles", Font.BOLD, 13));
		lblNewLabel_2.setBounds(374, 207, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		PoFiel = new JTextField();
		PoFiel.setBounds(482, 270, 244, 40);
		contentPane.add(PoFiel);
		PoFiel.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("PUERTO");
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setFont(new Font("Fuzzy Bubbles", Font.BOLD, 13));
		lblNewLabel_3.setBounds(374, 278, 63, 14);
		contentPane.add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\Theda\\Downloads\\Hacer conexion.png"));
		btnNewButton.setFont(new Font("Fuzzy Bubbles", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = UserFiel.getText();
                String contrasena = new String(ConFiel.getPassword());
                String host = HoFiel.getText();
                String puerto = PoFiel.getText();
                // URL de conexión a MySQL
                String jdbcUrl = "jdbc:mysql://" + host + ":" + puerto;
                
                
				int respuesta = JOptionPane.showConfirmDialog(btnNewButton,
	                    "¿Seguro que quieres iniciar sesión?",
	                    "Confirmación",
	                    JOptionPane.YES_NO_OPTION);

	            if (respuesta == JOptionPane.YES_OPTION) {
	            	try {
	                    // Establecer la conexión a la base de datos
	                    Connection connection = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
	                    JOptionPane.showMessageDialog(btnNewButton, "Conexion Exitosa");
	                    BD frame = new BD();
	                    
						frame.setVisible(true);
					
	                    connection.close();
	                    dispose();

	                } catch (SQLException ex) {
	                    System.out.println("Error al conectar a MySQL: " + ex.getMessage());
	                    ex.printStackTrace();
	                }
	            } else {
	                // Código a ejecutar si el usuario elige "No" o cierra la ventana
	                System.out.println("Inicio de sesión cancelado");
	            }  
			}
		});
		btnNewButton.setBounds(384, 353, 137, 40);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\Theda\\Downloads\\Hacer conexion (1).png"));
		btnNewButton_1.setFont(new Font("Fuzzy Bubbles", Font.PLAIN, 11));
		btnNewButton_1.setBounds(581, 353, 137, 40);
		contentPane.add(btnNewButton_1);
		
		ConFiel = new JPasswordField();
		ConFiel.setBounds(482, 127, 244, 40);
		contentPane.add(ConFiel);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(Inicio.class.getResource("/Imagenes/Us2.png")));
		lblNewLabel_4.setBounds(10, 38, 347, 354);
		contentPane.add(lblNewLabel_4);
	}
}
