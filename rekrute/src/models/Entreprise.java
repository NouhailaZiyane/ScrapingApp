package models;

public class Entreprise {
	private static int id =300 ;
	String nom;//name
	private String secteur_activite;//desc
	private String site;//desc
	private String description;//desc
	static void increment() {
		id++;
	}
	
	public java.lang.String toString() {
		return "Entreprise [nom=" + nom + ", secteur_activite=" + secteur_activite + ", site=" + site + ", description="
				+ description + "]";
	}
	public Entreprise(String nom, String secteur_activite, String site, String description) {
		increment();
		this.nom = nom;
		this.secteur_activite = secteur_activite;
		this.site = site;
		this.description = description;
	}
	public Entreprise() {
		increment();
		this.nom = null;
		this.secteur_activite = null;
		this.site = null;
		this.description = null;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String company_name) {
		this.nom = company_name;
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
	public void setDescription(String descrip) {
		this.description = descrip;
	}
	
	
	public  int getId() {
		return id;
	}
	public  void setId(int id) {
		this.id=id;
	}
	
	
}

