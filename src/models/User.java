package models;

import java.util.ArrayList;

public class User {
	private String firstName, lastName, password, sexe, username, email;
	private ArrayList<Skill> skills;
	private int age;

	public User() {
		this.username = "";
		this.firstName = "";
		this.lastName = "";
		this.password = "";
		this.sexe = "";
		this.age = -1;
	}
	
	public User(String username, String firstName, String lastName, String password, String sexe, String email ,int age) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.sexe = sexe;
		this.age = age;
		this.email = email;
	}
	
	public void addSkill(Skill skill) {
		this.skills.add(skill);
	}
	
	public void removeSkill(Skill skill) {
		this.skills.remove(skill);
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	} 
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "User [firstName:" + firstName + "\nlastName:" + lastName + "\npassword:" + password + "\nsex:" + sexe
				+ "\nusername=" + username + "\nage:" + age + "]";
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sexe;
	}
	public void setSex(String sexe) {
		this.sexe = sexe;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public ArrayList<Skill> getSkills() {
		return skills;
	}

	public void setSkills(ArrayList<Skill> skills) {
		this.skills = skills;
	}
	
}
