package models;

public class Skill {
	private int skillID;
	private String name;

	public Skill(int skillID, String name) {
		super();
		this.skillID = skillID;
		this.name = name;
	}
	
	public Skill() {
		this.skillID = -1;
		this.name = null;
	}
	
	public void setSkillIdFromName(String name) {
		
	}

	public int getSkillID() {
			return skillID;
	}

	public void setSkillID(int skillID) {
		this.skillID = skillID;
	}

	public String getName() { 
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public boolean isSkillsID(String name) {
		// This will serve us to link the check boxes text with the id of the skill in order for us to put it in back-end
		return this.getName().equals(name);
	}
	
}
