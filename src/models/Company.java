package models;

public class Company {
	private static int id =0 ;
	private String nom,secteur_activite;
	private String site;
	private String description;
	static void increment() {
		id++;
	}
	public Company(String nom, String secteur_activite, String description) {
		increment();
		this.nom = nom;
		this.secteur_activite = secteur_activite;
		this.description = description;
	}
	public Company() {
		increment();
		this.nom = null;
		this.secteur_activite = null;
		this.site = null;
		this.description = null;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getSecteur_activite() {
		return secteur_activite;
	}
	public void setSecteur_activite(String secteur_activite) {
		this.secteur_activite = secteur_activite;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Company [nom=" + nom + ", secteur_activite=" + secteur_activite + ", site=" + site + ", description="
				+ description + "]";
	}
	public static int getId() {
		return id;
	}
	
	
}
