package pages;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import controllers.UserController;
import models.Skill;
import models.User;
import repositories.SkillRepository;
import repositories.UserRepository;
import shared.MyException;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JTextArea;
import java.awt.TextArea;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Map;
import java.awt.SystemColor;
import java.awt.Scrollbar;
import javax.swing.JScrollPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import java.awt.Component;
import java.awt.ScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class ProfilePage {

	private JFrame frame;
	private JTextField FirstName;
	private JTextField LastName;
	private JTextField Email;
	private JTextField Age;
	private User currentUser;
	private JTextField SearchedSkill;
	private ArrayList<Skill> Skills;
	private JCheckBox[] cb;
	private JPanel panel;
	private JTextArea MySkills;
	private ArrayList<Skill> userSkills;
	private JPasswordField OldPassword;
	private JPasswordField NewPassword;
	private JPasswordField Confirm;
	private JTextField degreeSchool;
	private boolean isStudent;
	private boolean isGraduate;

	private String status;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if (args.length == 0) {
						InsertPasswordPage.main(null);
					} else {
						ProfilePage window = new ProfilePage(args[0]);
						window.frame.setVisible(true);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private boolean isInUserSkill(String skill) {
		for (int j = 0; j < currentUser.getSkills().size(); j++) {
			if (currentUser.getSkills().get(j).getName().equals(skill)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Create the application.
	 */
	public ProfilePage(String username) {
		try {
			User user = UserController.getUserByUsername(username);
			initialize(user);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, e.getMessage());
		}

	}

	// This function will serve us to generate check-boxes when the user want to
	// search a skill and add it.
	private void createAndListenForCheckBoxClick(ArrayList<Skill> Skills) {
		Component[] componentList = panel.getComponents();
		for (Component c : componentList) {
			// Empty the panel first.
			if (c instanceof JCheckBox || c instanceof JTextField) {
				panel.remove(c);
			}
		}

		if (Skills.size() > 0) {
			cb = new JCheckBox[Skills.size()];
			for (int i = 0; i < Skills.size(); i++) {
				String skill = Skills.get(i).getName();
				cb[i] = new JCheckBox(skill);

				if (isInUserSkill(skill)) {
					cb[i].setSelected(true);
				} else {

				}
				panel.add(cb[i]);
				panel.revalidate();
				panel.repaint();
				cb[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						boolean contains = false;
						String skill = e.getActionCommand();
						Skill skillInstance = SkillRepository.getSkillByName(skill);

						System.out.println(skillInstance.getName() + " " + skillInstance.getSkillID());
						
						
						ArrayList<Skill> currentUserSkills = currentUser.getSkills();
						
						System.out.println("---------------------------\n");
						for(int j = 0; j< currentUserSkills.size(); j++ ) {
							if(currentUserSkills.get(j).getSkillID() == skillInstance.getSkillID()) {
								currentUserSkills.remove(j);
								currentUser.setSkills(currentUserSkills);
								contains = true;
							}
						}
						
						if(!contains) {
							currentUser.addSkill(skillInstance);
						}
						updateMySkillsField();
					}
				});
			}

		} else {
			JTextField tempTextField = new JTextField("There is no skill by this name");
			tempTextField.setEditable(false);
			tempTextField.setBounds(633, 152, 269, 32);
			panel.add(tempTextField);
			panel.revalidate();
			panel.repaint();
		}
	}

	private void updateMySkillsField() {
		ArrayList<Skill> currentUserSkills = currentUser.getSkills();
		if (currentUserSkills.size() > 0) {
			MySkills.setText(SkillRepository.displaySkills(currentUserSkills));
		} else {
			MySkills.setText("You havent selected any skills");
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(User user) {
		this.currentUser = user;
		frame = new JFrame();
		frame.setBounds(100, 100, 1563, 637);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblWelcomeToYour = DefaultComponentFactory.getInstance().createTitle("Welcome to your profile page. ");
		lblWelcomeToYour.setFont(new Font("Dialog", Font.BOLD, 23));
		lblWelcomeToYour.setBounds(238, 34, 427, 25);
		frame.getContentPane().add(lblWelcomeToYour);

		JLabel lblFirstName = DefaultComponentFactory.getInstance().createTitle("First name");
		lblFirstName.setFont(new Font("Dialog", Font.BOLD, 15));
		lblFirstName.setBounds(249, 114, 106, 15);
		frame.getContentPane().add(lblFirstName);

		JLabel lblLastName = DefaultComponentFactory.getInstance().createTitle("Last name");
		lblLastName.setFont(new Font("Dialog", Font.BOLD, 15));
		lblLastName.setBounds(249, 160, 130, 15);
		frame.getContentPane().add(lblLastName);

		JLabel lblEmail = DefaultComponentFactory.getInstance().createTitle("Email");
		lblEmail.setFont(new Font("Dialog", Font.BOLD, 15));
		lblEmail.setBounds(249, 213, 130, 15);
		frame.getContentPane().add(lblEmail);

		JLabel lblAge = DefaultComponentFactory.getInstance().createTitle("Age");
		lblAge.setFont(new Font("Dialog", Font.BOLD, 15));
		lblAge.setBounds(249, 264, 130, 15);
		frame.getContentPane().add(lblAge);

		FirstName = new JTextField();
		FirstName.setText("f i r s t n a m e");
		FirstName.setBounds(396, 106, 198, 32);
		frame.getContentPane().add(FirstName);
		FirstName.setColumns(10);

		LastName = new JTextField();
		LastName.setText("l a s t n a m e");
		LastName.setColumns(10);
		LastName.setBounds(397, 152, 198, 32);
		frame.getContentPane().add(LastName);

		Email = new JTextField();
		Email.setText("e m a i l");
		Email.setColumns(10);
		Email.setBounds(396, 205, 198, 32);
		frame.getContentPane().add(Email);

		Age = new JTextField();
		Age.setText("a g e");
		Age.setColumns(10);
		Age.setBounds(396, 281, 198, 32);
		frame.getContentPane().add(Age);

		JLabel skilsTit = DefaultComponentFactory.getInstance().createTitle("Skills");
		skilsTit.setBounds(249, 412, 91, 15);
		frame.getContentPane().add(skilsTit);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(396, 387, 500, 106);
		frame.getContentPane().add(scrollPane);

		MySkills = new JTextArea();
		scrollPane.setViewportView(MySkills);
		MySkills.setEditable(false);
		MySkills.setLineWrap(true);
		MySkills.setRows(5);
		MySkills.setText("You have no added skills at the moment");

		JLabel lblAddSkills = DefaultComponentFactory.getInstance().createTitle("Manage skills");
		lblAddSkills.setBounds(633, 106, 156, 15);
		frame.getContentPane().add(lblAddSkills); 

		JButton btnSaveChanges = new JButton("Save changes");
		btnSaveChanges.setBounds(394, 542, 304, 48);
		frame.getContentPane().add(btnSaveChanges);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(633, 208, 268, 150);

		frame.getContentPane().add(scrollPane_1);

		panel = new JPanel(new GridLayout(0, 2, 10, 10));
		scrollPane_1.setViewportView(panel);

		SearchedSkill = new JTextField();
		SearchedSkill.setBounds(633, 152, 269, 32);
		frame.getContentPane().add(SearchedSkill);
		SearchedSkill.setColumns(10);

		JLabel lblSearch = DefaultComponentFactory.getInstance().createTitle("Search");
		lblSearch.setBounds(633, 129, 130, 15);
		frame.getContentPane().add(lblSearch);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(922, 106, 16, 400);
		frame.getContentPane().add(separator);

		OldPassword = new JPasswordField();
		OldPassword.setBounds(965, 158, 222, 32);
		frame.getContentPane().add(OldPassword);

		JLabel lblChangePassword = DefaultComponentFactory.getInstance().createTitle("Change password");
		lblChangePassword.setBounds(975, 213, 169, 15);
		frame.getContentPane().add(lblChangePassword);

		JLabel lblNewPassword = DefaultComponentFactory.getInstance()
				.createLabel("Enter your password to confirm changes");
		lblNewPassword.setBounds(965, 132, 315, 25);
		frame.getContentPane().add(lblNewPassword);

		NewPassword = new JPasswordField();
		NewPassword.setBounds(965, 264, 222, 32);
		frame.getContentPane().add(NewPassword);

		JLabel lblNewPassword_1 = DefaultComponentFactory.getInstance().createTitle("New password");
		lblNewPassword_1.setBounds(965, 242, 130, 15);
		frame.getContentPane().add(lblNewPassword_1);

		Confirm = new JPasswordField();
		Confirm.setBounds(965, 386, 222, 32);
		frame.getContentPane().add(Confirm);

		JLabel lblConfirmNewPassword = DefaultComponentFactory.getInstance().createTitle("Confirm new password");
		lblConfirmNewPassword.setBounds(965, 359, 210, 15);
		frame.getContentPane().add(lblConfirmNewPassword);

		JButton ReturnHomeButton = new JButton("Home");
		ReturnHomeButton.setBounds(1391, 30, 156, 39);
		frame.getContentPane().add(ReturnHomeButton);

		degreeSchool = new JTextField();
		degreeSchool.setColumns(10);
		degreeSchool.setBounds(396, 325, 198, 32);
		frame.getContentPane().add(degreeSchool);

		JLabel lblDegree = DefaultComponentFactory.getInstance().createTitle("Degree");
		lblDegree.setBounds(249, 333, 130, 15);
		frame.getContentPane().add(lblDegree);

		// ==================================================================================
		// ======================== Management ========================================
		// ==================================================================================
		try {
			if (UserRepository.isStudent(currentUser.getUsername())) {
				lblDegree.setText("Current school :");
				status = "Student";
				degreeSchool.setText(UserRepository.getSchool(currentUser.getUsername()));

			} else {
				if (UserRepository.isGraduate(currentUser.getUsername())) {
					status = "Graduate";
					lblDegree.setText("Degree :");
					degreeSchool.setText(UserRepository.getDegree(currentUser.getUsername()));
				}
			}

			FirstName.setText(currentUser.getFirstName());
			LastName.setText(currentUser.getLastName());
			Age.setText(String.valueOf(currentUser.getAge()));
			Email.setText(currentUser.getEmail());

			Skills = SkillRepository.getAllSkills();
			currentUser.setSkills(UserController.getUserSkills(currentUser.getUsername()));

			userSkills = currentUser.getSkills(); // Get user skills from
													// database.

			SearchedSkill.getDocument().addDocumentListener(new DocumentListener() {

				public void insertUpdate(DocumentEvent e) {

					Skills = SkillRepository.searchSkills(SearchedSkill.getText());
					createAndListenForCheckBoxClick(Skills);

				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					if (SearchedSkill.getText().length() == 0) {
						Skills = SkillRepository.getAllSkills();
						createAndListenForCheckBoxClick(Skills);
					} else {
						Skills = SkillRepository.searchSkills(SearchedSkill.getText());
						createAndListenForCheckBoxClick(Skills);
					}

				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					System.out.println(SearchedSkill.getText());
				}
			});

			if (userSkills.size() != 0) {
				MySkills.setText(SkillRepository.displaySkills(userSkills));
			}

			createAndListenForCheckBoxClick(Skills);

			btnSaveChanges.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println();
					try {
						String username = currentUser.getUsername();
						String firstName = FirstName.getText();
						String lastName = LastName.getText();
						String age = Age.getText();
						String oldPassword = String.valueOf(OldPassword.getPassword());
						String newPassword = String.valueOf(NewPassword.getPassword());
						String confirm = String.valueOf(Confirm.getPassword());
						String email = Email.getText();
						String degSchool = degreeSchool.getText();
						ArrayList<Skill> updatedSkills = currentUser.getSkills();

						if (UserController.updateUser(username, firstName, lastName, oldPassword, newPassword, confirm,
								age, email, updatedSkills, status, degSchool)) {
							JOptionPane.showMessageDialog(btnSaveChanges,
									"You have successfully updated your profile.");
						}
					} catch (Exception myException) {
						JOptionPane.showMessageDialog(btnSaveChanges, myException.getMessage());
					}
				}
			});

			ReturnHomeButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JComponent comp = (JComponent) e.getSource();
					Window win = SwingUtilities.getWindowAncestor(comp);
					win.dispose();
					String[] userInfo = { currentUser.getUsername() };
					HomePage.main(userInfo);

				}

			});
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(frame, exception.getMessage());
		}

	}
}
