package bd;

import java.sql.Connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import models.Entreprise;
import models.Offre;

public class data {
	 
	public void scanItemLinks() {
		try {
	//Document doc = Jsoup.connect("https://www.rekrute.com/offres.html?st=d&keywordNew=1&keyword=ing%C3%A9nieure+informatique&filterLogo=&filterLogo=&sectorId%5B%5D=24&filterLogo=&filterLogo=").get();
	//getElementbyclass permet de retourner un tableau  de tous les
	//éléments enfants qui ont tous le meme nom de classe
	String linkat[] = new String[14];
	//HashMap<Element, String> link0 = new HashMap<Element,String>();
	//Element a;
	
	Document docu = Jsoup.connect("https://www.rekrute.com/offres.html?s=1&p=1&o=1").get();
	String nbr = docu.select("div.pagination:nth-of-type(1) .section .jobs").text() ;
	String[] page = nbr.split("sur ");
		//System.out.println(page[1]);
	int pg =Integer.parseInt(page[1]);
	

	int i;
	for(i=1;i<=pg;i++) {
		Document doc = Jsoup.connect("https://www.rekrute.com/offres.html?s=1&p="+i+"&o=1&sectorId%5B0%5D=24").get();

		Elements items = (Elements) doc.getElementsByClass("section");
		//String[] links = new String[100];
		
		for (Element item : doc.select("div.col-sm-10.col-xs-12")) {
			Entreprise e= new Entreprise();
			Element link_annonce = item.select(".section h2 > a").first();
			String linnk = link_annonce.attr("href");
			
			//String linnk = item.getElementsByTag("a").attr("href");
			linnk="https://www.rekrute.com"+linnk;
			
			Offre o =new Offre();
			//poste
			Document document = Jsoup.connect(linnk).get();
			Elements titles = document.getElementsByClass("featureInfo");
			//String titlesa = titles.getElementsByTag("a").attr("href");
			String poste = titles.select("ul.featureInfo b").text();
			int poste1;
			if(poste.equals("")){
				poste1=1;
			}else {poste1= Integer.parseInt(poste);}
			
			final String secteur=document.select("h2.h2italic").text();
			final String desc= document.select("div#recruiterDescription").text();
			e.setDescription(desc);
			o.setNbrPoste(poste1);
			e.setSecteur_activite(secteur);
			e.setNom("");
			e.setSite("");
			//contrat
			//Document document = Jsoup.connect(link).get();
			//Elements titles = document.getElementsByClass("featureInfo");
			//String titlesa = titles.getElementsByTag("a").attr("href");
			final String contrat = titles.select(".tagContrat").text();
			o.setType_contrat(contrat);
			//experience
			 String experience = titles.select("ul.featureInfo li:nth-of-type(1)").text();
			experience= experience.replace("CDI", "");
			experience= experience.replace("CDD", "");
			
			o.setNiveauExprience(experience);
			//System.out.println(experience);
			//bac==33
			//final String bac = titles.select("li:nth-of-type(1)").text();
			//title
			Elements title = document.getElementsByClass("col-sm-12");
			//String titlesa = titles.getElementsByTag("a").attr("href");
			final String tit = title.select("h1").text();
			o.setPoste(tit);
			
			//Elements titlel = document.getElementsByClass("col-md-12 blc");
			String comp1 = document.select("div.blc.col-md-12:nth-of-type(4)").text();
			
			String comp2 = document.select("div.blc.col-md-12:nth-of-type(5)").text();
			
			//System.out.println(comp2);
			o.setSkills(comp2);
			o.setDescription(comp1);
			o.affiche();
			Bd b= new Bd();
			Connection con= b.connexion();
			o.setEntreprise(e);
			b.insertionEntreprise(con, e);
			b.insertionOffre(con, o,e);
			b.insertionSkills(con, o);
		
		}
	}
	

		}
	catch(Exception e){
		e.printStackTrace();

		}
		
		
	}
	
	
	
}



