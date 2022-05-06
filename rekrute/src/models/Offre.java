package models;


public class Offre {
	private static int id=300;
	private String poste;// Title
	private String description;//desc
	private String skills;//skills
	private String type_contrat;//desc
	private String niveauExprience;//desc
	private int nbrPoste;//desc
	private Entreprise entreprise;//id => entreprise
	
	static void increment() {
		id++;
	}

	public int getId() {
		return id;
	}
	

	public Offre() {
	    increment();

		this.poste = null;
		this.description = null;
		this.skills = null;
		this.type_contrat = null;
		this.niveauExprience = null;
		this.nbrPoste = 0;
		this.entreprise = null;
	}

	public Offre(String poste,String description , String skills, String type_contrat, String niveauExprience,
			int nbrPoste, Entreprise entreprise) {
	    increment();
		this.poste = poste;
		this.description = description;
		this.skills = skills;
		this.type_contrat = type_contrat;
		this.niveauExprience = niveauExprience;
		this.nbrPoste = nbrPoste;
		this.entreprise = entreprise;
	}
	public Offre(String poste,String description , String skills, String type_contrat, String niveauExprience,
			int nbrPoste) {
	    increment();
		this.poste = poste;
		this.description = description;
		this.skills = skills;
		this.type_contrat = type_contrat;
		this.niveauExprience = niveauExprience;
		this.nbrPoste = nbrPoste;
		this.entreprise = null;
	}


	public String getPoste() {
		return poste;
	}

	public void setPoste(String filtered_poste) {
		this.poste = filtered_poste;
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

	public void setType_contrat(String contrat) {
		this.type_contrat = contrat;
	}

	public String getNiveauExprience() {
		return niveauExprience;
	}

	public void setNiveauExprience(String niveauExprience) {
		this.niveauExprience = niveauExprience;
	}

	public int getNbrPoste() {
		return nbrPoste;
	}

	public void setNbrPoste(int poste2) {
		this.nbrPoste = poste2;
	}

	public Entreprise getEntreprise() {
		return entreprise;
	}
	public int getEntreprise1() {
		return entreprise.getId();
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}


	public String toString() {
		return "Offre [poste=" + poste + ", description=" + description + ", skills=" + skills
				+ ", type_contrat=" + type_contrat + ", niveauExprience=" + niveauExprience + ", nbrPoste=" + nbrPoste
				+ ", entreprise=" + entreprise + "]";
	}

	public void affiche() {
		System.out.print(toString()+"\n");
		
	}
	
	

}
