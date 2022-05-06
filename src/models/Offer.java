package models;

import models.Company;

public class Offer {
	private static int id=0;
	private String poste;
	private String description;
	private String skills;
	private String type_contrat;
	private String niveauExprience;
	private String nbrPoste;
	private Company company;
	
	static void increment() {
		id++;
	}

	public static int getId() {
		return id;
	}

	public Offer() {
	    increment();
		this.poste = null;
		this.description = null;
		this.skills = null;
		this.type_contrat = null;
		this.niveauExprience = null;
		this.nbrPoste = nbrPoste;
		this.company = null;
	}

	public Offer(String poste,String description , String skills, String type_contrat, String niveauExprience,
			String nbrPoste, Company company) {
	    increment();
		this.poste = poste;
		this.description = description;
		this.skills = skills;
		this.type_contrat = type_contrat;
		this.niveauExprience = niveauExprience;
		this.nbrPoste = nbrPoste;
		this.company = company;
	}



	public String getPoste() {
		return poste;
	}

	public void setPoste(String poste) {
		this.poste = poste;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getType_contrat() {
		return type_contrat;
	}

	public void setType_contrat(String type_contrat) {
		this.type_contrat = type_contrat;
	}

	public String getNiveauExprience() {
		return niveauExprience;
	}

	public void setNiveauExprience(String niveauExprience) {
		this.niveauExprience = niveauExprience;
	}

	public String getNbrPoste() {
		return nbrPoste;
	}

	public void setNbrPoste(String nbrPoste) {
		this.nbrPoste = nbrPoste;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Offre [poste=" + poste + ", description=" + description + ", skills=" + skills
				+ ", type_contrat=" + type_contrat + ", niveauExprience=" + niveauExprience + ", nbrPoste=" + nbrPoste
				+ ", company=" + company + "]";
	}
	public void afficheSoftSkills() {
		String skills = this.getSkills();
		 
		      System.out.println(skills);
		    
	}
	

}
