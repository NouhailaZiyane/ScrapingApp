package pages;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.UserController;

import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import java.awt.Color;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Window;

public class LoginPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JPasswordField Password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
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
	public LoginPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1185, 583);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JFormattedTextField Username = new JFormattedTextField();
		Username.setBounds(462, 155, 273, 35);
		contentPane.add(Username);

		JButton registerButton = new JButton("Register");
		registerButton.setBounds(607, 302, 128, 44);
		contentPane.add(registerButton);

		JButton submitButton = new JButton("Login");
		submitButton.setBounds(462, 302, 128, 44);
		contentPane.add(submitButton);

		JLabel lblNotRegisteredYet = new JLabel("Not registered yet?");
		lblNotRegisteredYet.setFont(new Font("Dialog", Font.BOLD, 11));
		lblNotRegisteredYet.setForeground(Color.WHITE);
		lblNotRegisteredYet.setBounds(607, 350, 184, 15);
		contentPane.add(lblNotRegisteredYet);

		JLabel lblUserName = new JLabel("User name");
		lblUserName.setForeground(new Color(230, 230, 250));
		lblUserName.setBounds(462, 128, 116, 15);
		contentPane.add(lblUserName);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(462, 206, 87, 15);
		contentPane.add(lblPassword);

		Password = new JPasswordField();
		Password.setBounds(462, 236, 271, 35);
		contentPane.add(Password);

		table = new JTable();
		table.setBackground(new Color(0, 128, 128));
		table.setBounds(384, 89, 423, 339);
		contentPane.add(table);

		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String username = Username.getText(); 
				String password = String.valueOf(Password.getPassword());

				try {
					if (UserController.login(username, password)) {
						JOptionPane.showMessageDialog(submitButton, "Successfully logged in");
						JComponent comp = (JComponent) e.getSource();
						Window win = SwingUtilities.getWindowAncestor(comp);
						win.dispose();
						String[] userInfo = {username};
						HomePage.main(userInfo);
					} else {
						JOptionPane.showMessageDialog(submitButton, "Something is wrong, please try again.");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(submitButton, ex.getMessage());
				}

			}
		});
		
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComponent comp = (JComponent) e.getSource();
				Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
				RegisterPage.main(null);
			}
		});
	

	}
}
