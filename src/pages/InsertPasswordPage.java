package pages;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import controllers.UserController;
import shared.FileManager;
import shared.Helpers;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JButton;

public class InsertPasswordPage {

	private JFrame frame;
	private JPasswordField Password;
	private ArrayList<String> connectedUsers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArrayList<String> connectedUser = FileManager.listFilesInLocalStorage();
					if(connectedUser.size() == 0) {
						LoginPage.main(null);
					}else {
						InsertPasswordPage window = new InsertPasswordPage(connectedUser);
						window.frame.setVisible(true);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InsertPasswordPage(ArrayList<String> connectedUsers) {
		initialize(connectedUsers);
	}



	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ArrayList<String> connectedUsers) {
		this.connectedUsers = connectedUsers;
		frame = new JFrame();
		frame.getContentPane().setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
		frame.setBounds(100, 100, 1094, 582);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Password = new JPasswordField();
		Password.setBounds(368, 276, 371, 33);
		frame.getContentPane().add(Password);

		JLabel lblYouAreLogged = DefaultComponentFactory.getInstance().createTitle("Are you one of these users ?  ");
		lblYouAreLogged.setForeground(SystemColor.text);
		lblYouAreLogged.setFont(new Font("Dialog", Font.BOLD, 20));
		lblYouAreLogged.setBounds(368, 110, 382, 56);
		frame.getContentPane().add(lblYouAreLogged);

		JLabel lblInsertYourPassword = DefaultComponentFactory.getInstance().createTitle("Insert your password:");
		lblInsertYourPassword.setForeground(SystemColor.text);
		lblInsertYourPassword.setBounds(368, 249, 226, 15);
		frame.getContentPane().add(lblInsertYourPassword);
		
		
		
		String[] stringArr = Helpers.arrayListToStringArray(connectedUsers);
		JComboBox selectedUser = new JComboBox(stringArr);
		selectedUser.setBounds(368, 184, 371, 33);
		frame.getContentPane().add(selectedUser);
		
		JButton loginButton = new JButton("Submit");
		loginButton.setBounds(368, 334, 371, 48);
		frame.getContentPane().add(loginButton);
		
		JButton returnButton = new JButton("Return");
		returnButton.setBounds(368, 421, 371, 48);
		frame.getContentPane().add(returnButton);
		
		JLabel lblReturnToLogin = new JLabel("Return to login page.");
		lblReturnToLogin.setForeground(SystemColor.text);
		lblReturnToLogin.setBounds(368, 394, 361, 15);
		frame.getContentPane().add(lblReturnToLogin);
		

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String username = String.valueOf(selectedUser.getSelectedItem()); 
				String password = String.valueOf(Password.getPassword());

				try {
					if (UserController.login(username, password)) {
						JOptionPane.showMessageDialog(loginButton, "Successfully logged in");
						JComponent comp = (JComponent) e.getSource();
						Window win = SwingUtilities.getWindowAncestor(comp);
						win.dispose();
						String[] userInfo = {username};
						HomePage.main(userInfo);
					} else {
						JOptionPane.showMessageDialog(loginButton, "The password is incorrect");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(loginButton, ex.getMessage());
				}

			}
		});
		
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComponent comp = (JComponent) e.getSource();
				Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
//				FileManager.clearLocalStorage();
				LoginPage.main(null);
			}
		});

	}
}
