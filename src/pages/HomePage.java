package pages;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import models.Offer;
import models.Skill;
import models.User;
import repositories.ClusterRepository;
import repositories.OfferRepository;
import repositories.UserRepository;
import shared.FileManager;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import clustering.Cluster;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;

public class HomePage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User currentUser;
	private JPanel panel;
	private boolean userLoggedIn = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JTextArea description = new JTextArea(5, 5);

				try {
					// Path path = Paths.get("/home/niggatron69/localStorage");
					// Files.createDirectories(path);

					if (args.length == 0) {
						ArrayList<String> connectedUser = FileManager.listFilesInLocalStorage();
						if (connectedUser.size() != 0) {
							InsertPasswordPage.main(null);
						} else {
							LoginPage.main(null);
						}

					} else {
						if (FileManager.createFileByUsername(args[0])) {
							User connectedUser = UserRepository.getUserByUsername(args[0]);
							HomePage frame = new HomePage(connectedUser);
							frame.setVisible(true);
						} else {
							LoginPage.main(null);
						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void displayPosts(String username) {
		ArrayList<Offer> offers = OfferRepository.getOffers(username, null);
		int counter = 0;
		for (Offer offer : offers) {
			System.out.println(offer.getCompany().toString());
			JPanel panel2 = new JPanel();
			JLabel lblLastName = DefaultComponentFactory.getInstance().createTitle(offer.getPoste() + (
					offer.getCompany().getNom().length() != 0  ? ", from " + offer.getCompany().getNom() : " "));
			lblLastName.setFont(new Font("Dialog", Font.BOLD, 15));
			lblLastName.setBounds(462, 128, 116, 15);
			panel.add(lblLastName);
			JTextArea description = new JTextArea(5, 2);
			description.setText(offer.getDescription());
			description.setWrapStyleWord(true);
			description.setLineWrap(true);
			description.setEditable(false);
			description.setBounds(462, 206, 87, 15);
			panel.add(description);
		}

	}

	public void paginatePosts() {

	}

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public HomePage(User user) throws Exception {
		this.currentUser = user;
		ArrayList<Skill> userSkills = UserRepository.getUserSkills(user.getUsername());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1259, 658);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(169, 169, 169));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel UserLoggedTitle = DefaultComponentFactory.getInstance()
				.createTitle("You are logged in as  : " + this.currentUser.getUsername());
		UserLoggedTitle.setForeground(new Color(255, 255, 255));
		UserLoggedTitle.setBackground(new Color(0, 139, 139));
		UserLoggedTitle.setFont(new Font("Dialog", Font.BOLD, 20));
		UserLoggedTitle.setBounds(12, 20, 430, 32);
		contentPane.add(UserLoggedTitle);

		JButton profileButton = new JButton("My profile");
		profileButton.setForeground(new Color(255, 255, 255));
		profileButton.setBackground(new Color(0, 139, 139));
		profileButton.setBounds(1048, 9, 189, 43);
		contentPane.add(profileButton);

		if (userSkills.size() == 0) {
			JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance()
					.createTitle("You haven't set any skill yet, go to your profile and add some skills...");
			lblNewJgoodiesTitle.setFont(new Font("Dialog", Font.BOLD, 20));
			lblNewJgoodiesTitle.setBounds(198, 107, 845, 60);
			contentPane.add(lblNewJgoodiesTitle);
		}

		JButton disconnectButton = new JButton("Loggout");
		disconnectButton.setForeground(new Color(255, 255, 255));
		disconnectButton.setBackground(new Color(0, 128, 128));
		disconnectButton.setBounds(835, 12, 189, 43);
		contentPane.add(disconnectButton);

		if (userSkills.size() > 0) {
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(0, 167, 1255, 456);
			contentPane.add(scrollPane);

			panel = new JPanel(new GridLayout(0, 1, 0, 0));
			panel.setBackground(new Color(255, 255, 255));

			scrollPane.setViewportView(panel);
			
						JLabel lblYourSuggestedSector = DefaultComponentFactory.getInstance()
								.createTitle("Your suggested sector of work is :");
						lblYourSuggestedSector.setBounds(12, 73, 403, 43);
						contentPane.add(lblYourSuggestedSector);
						lblYourSuggestedSector.setFont(new Font("Dialog", Font.BOLD, 19));
						
									JLabel lblThoseAreSome = DefaultComponentFactory.getInstance()
											.createTitle("Those are some job oportunities suggested for you");
									lblThoseAreSome.setBounds(12, -84, 1252, 453);
									contentPane.add(lblThoseAreSome);

			displayPosts(currentUser.getUsername());
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					scrollPane.getVerticalScrollBar().setValue(0);
				}
			});

		}

//		ArrayList<Cluster> clusters = ClusterRepository.generateOrderedClusters();
//
//		for(int j = 0 ; j < clusters.size(); j++) {
//			System.out.println(clusters.get(j).toString());
//		}
//		
		disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComponent comp = (JComponent) e.getSource();
				Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
				InsertPasswordPage.main(null);
			}
		});

		profileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComponent comp = (JComponent) e.getSource();
				Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
				String[] stringUname = { user.getUsername() };
				ProfilePage.main(stringUname);
			}
		});
	}
}
