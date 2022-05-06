package pages;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.UserController;
import shared.MysqlCon;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JRadioButton;

public class RegisterPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField Passwd;
	private JTextField Age;
	private JTextField LastName;
	private JTextField Username;
	private JPasswordField ConfirmPasswd;
	private JLabel lblLastName;
	private JLabel lblEmail;
	private JLabel lblAge;
	private JLabel UsernameLabel;
	private JLabel lblSex;
	private JLabel lblPassword;
	private JLabel lblConfirm;
	private JTable table;
	private JTextField Degree;
	private JTextField School;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					RegisterPage frame = new RegisterPage();
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
	public RegisterPage() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1132, 542);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlLtHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton loginButton = new JButton("Login");
		loginButton.setBounds(573, 414, 171, 35);
		contentPane.add(loginButton);

		Passwd = new JPasswordField();
		Passwd.setBounds(367, 372, 174, 30);
		contentPane.add(Passwd);

		JTextField Email = new JTextField();
		Email.setBounds(367, 166, 171, 30);
		contentPane.add(Email);

		Age = new JTextField();
		Age.setBounds(573, 166, 171, 30);
		contentPane.add(Age);

		LastName = new JTextField();
		LastName.setBounds(574, 102, 171, 30);
		contentPane.add(LastName);

		String sexValues[] = { "M", "F" };
		JComboBox Sexe = new JComboBox(sexValues);
		Sexe.setBounds(571, 229, 174, 28);
		contentPane.add(Sexe);

		Username = new JTextField();
		Username.setBounds(367, 229, 171, 30);
		contentPane.add(Username);

		ConfirmPasswd = new JPasswordField();
		ConfirmPasswd.setBounds(573, 372, 171, 30);
		contentPane.add(ConfirmPasswd);

		lblLastName = new JLabel("Last name");
		lblLastName.setForeground(SystemColor.text);
		lblLastName.setBackground(Color.WHITE);
		lblLastName.setBounds(576, 75, 89, 15);
		contentPane.add(lblLastName);

		lblEmail = new JLabel("Email");
		lblEmail.setForeground(SystemColor.text);
		lblEmail.setBounds(367, 144, 55, 15);
		contentPane.add(lblEmail);

		lblAge = new JLabel("Age");
		lblAge.setForeground(SystemColor.text);
		lblAge.setBounds(574, 144, 55, 15);
		contentPane.add(lblAge);

		UsernameLabel = new JLabel("User name");
		UsernameLabel.setForeground(SystemColor.text);
		UsernameLabel.setBounds(367, 208, 94, 15);
		contentPane.add(UsernameLabel);

		lblSex = new JLabel("sexe");
		lblSex.setForeground(SystemColor.text);
		lblSex.setBounds(574, 208, 70, 15);
		contentPane.add(lblSex);

		lblPassword = new JLabel("Password");
		lblPassword.setForeground(SystemColor.text);
		lblPassword.setBounds(367, 345, 91, 15);
		contentPane.add(lblPassword);

		lblConfirm = new JLabel("Confirm");
		lblConfirm.setForeground(SystemColor.text);
		lblConfirm.setBounds(573, 344, 91, 15);
		contentPane.add(lblConfirm);

		JLabel lblFirstName = new JLabel("First name");
		lblFirstName.setForeground(Color.WHITE);
		lblFirstName.setBackground(Color.WHITE);
		lblFirstName.setBounds(367, 75, 91, 15);
		contentPane.add(lblFirstName);

		JLabel lblNewLabel = new JLabel("Already have one?");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 11));
		lblNewLabel.setForeground(SystemColor.text);
		lblNewLabel.setBounds(574, 453, 147, 15);
		contentPane.add(lblNewLabel);

		JTextField FirstName = new JTextField();
		FirstName.setBounds(367, 102, 173, 30);
		contentPane.add(FirstName);

		JButton submitButton = new JButton("Submit");
		submitButton.setBounds(368, 414, 173, 35);
		contentPane.add(submitButton);

		Degree = new JTextField();
		Degree.setBounds(370, 289, 171, 30);
		Degree.setEditable(false);
		contentPane.add(Degree);

		School = new JTextField();
		School.setBounds(573, 289, 171, 30);
		School.setEditable(false);
		contentPane.add(School);

		JRadioButton rdbtnYoureAStudent = new JRadioButton("you're a student");
		rdbtnYoureAStudent.setForeground(new Color(230, 230, 250));
		rdbtnYoureAStudent.setBackground(new Color(46, 139, 87));
		rdbtnYoureAStudent.setBounds(571, 265, 149, 23);
		contentPane.add(rdbtnYoureAStudent);

		JRadioButton degreeRadio = new JRadioButton("You're a graduate");
		degreeRadio.setBackground(new Color(46, 139, 87));
		degreeRadio.setForeground(new Color(230, 230, 250));
		degreeRadio.setBounds(367, 267, 174, 23);
		contentPane.add(degreeRadio);

		table = new JTable();
		table.setBackground(new Color(46, 139, 87));
		table.setBorder(new EmptyBorder(10, 10, 10, 10));
		table.setBounds(261, 43, 585, 443);
		contentPane.add(table);

		degreeRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (degreeRadio.isSelected()) {
					rdbtnYoureAStudent.setSelected(false);
					School.setText("");
					School.setEditable(false);
					Degree.setEditable(true);
				} else {
					Degree.setText("");
					Degree.setEditable(false);
				}

			}
		});

		rdbtnYoureAStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnYoureAStudent.isSelected()) {
					degreeRadio.setSelected(false);
					Degree.setEditable(false);
					Degree.setText("");
					School.setEditable(true);
				} else {
					School.setText("");
					School.setEditable(false);
				}

			}
		});

		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstName = FirstName.getText();
				String lastName = LastName.getText();
				String username = Username.getText();
				System.out.println("USERNAME :" + username);
				String password = String.valueOf(Passwd.getPassword());
				String confirm = String.valueOf(ConfirmPasswd.getPassword());
				String degree = Degree.getText();
				String school = School.getText();
				String email = Email.getText();
				String age = Age.getText();
				String sexe = String.valueOf(Sexe.getSelectedItem());
				boolean isStudent = rdbtnYoureAStudent.isSelected();
				boolean isGraduate = degreeRadio.isSelected();

				try {
					if (UserController.register(username, firstName, lastName, password, confirm, sexe, age, degree,
							email, isStudent, isGraduate, school)) {
						JOptionPane.showMessageDialog(submitButton, "You are registred successfully");
					} else {
						JOptionPane.showMessageDialog(submitButton, "Something is wrong, please try again.");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(submitButton, ex.getMessage());
				}

			}
		});

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComponent comp = (JComponent) e.getSource();
				Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
				LoginPage.main(null);
			}
		});

	}
}
