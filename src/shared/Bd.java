package shared;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import models.Company;
import models.Offer;

public class Bd {
	final static int id_CompanyUknown=758;

	public static Connection connexion(){
		try {
		     Class c= Class.forName("com.mysql.cj.jdbc.Driver");
		     Driver pilote=(Driver)c.newInstance();
		     DriverManager.registerDriver(pilote);
		     String protocole="jdbc:mysql:";
		     String ip="localhost";
		     String port="3306";
		     String nomBase="our_app2";
		     String conString=protocole+"//"+ip+":"+port+"/"+nomBase;
		     String nomConnexion="root";
		     String motDePasse="";

		     Connection con = DriverManager.getConnection(conString, nomConnexion, motDePasse);
		     
		         return con;
		    } catch (Exception e) {
		      e.printStackTrace();
		      return null;
		    }    }
	public static ResultSet selectionOffer(Connection con) {
		 try {
			 String sql="select * from offers";
	    	 Statement smt= con.createStatement();
		      ResultSet rs=smt.executeQuery(sql);
		      
		      return rs;
		      
		 }catch(Exception e) {
			 e.printStackTrace();
			 return null;
		 }
		 
    }
	public static ResultSet selectionCompany(Connection con) {
		 try {
			 String sql="select * from companies";
	    	 Statement smt= con.createStatement();
		      ResultSet rs=smt.executeQuery(sql);
		      
		      return rs;
		      
		 }catch(Exception e) {
			 e.printStackTrace();
			 return null;
		 }
		 
   }
	
	 public static int insertionOffer(Connection con,Offer o, Company e ) {
		 String Description =o.getDescription();
		 String Description1=Description.replaceAll("\'", " ");
		 String poste=o.getPoste().replaceAll("\'", " ");
		 try {
			 String sql="INSERT INTO `offers`(`title`,`type_contrat`,`niveauExprience`,`nbrPoste`,`description`,`company_id`) VALUES ('"+poste+"','"+o.getType_contrat()+"','"+o.getNiveauExprience()+"',"+o.getNbrPoste()+",'"+Description1+"',"+e.getId()+")";
			 Statement smt=con.createStatement();
			 int rs= smt.executeUpdate(sql);
			// insertionSkills(con, o);
			 return rs;
		 }catch(Exception e1) {
			 e1.printStackTrace();
			 return 0;
	 }} 
	public static int insertionSkills(Connection con,Offer o) {
		
		 String skills =o.getSkills();
	     skills=skills.toLowerCase();
	     skills=skills.replaceAll(",", "");
	     
	   
  String m1;
  int id;int rs=0;
      ResultSet rs1= selectionSkills(con);
	 try {
		while(rs1.next()) {
			m1=" "+rs1.getString("name")+" ";
			id =rs1.getInt("id");
			if(skills.contains(m1)) {
				try {
					 String sql="INSERT INTO `offer_skills`(`offer_id`,`skill_id`) VALUES ("+o.getId()+","+id+")";
					 Statement smt=con.createStatement();
					   rs= smt.executeUpdate(sql);	 
				 }catch(Exception e1) {
					 e1.printStackTrace();
					
				 }
			}
			
		            	 
		            }
		 
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	        
	  return rs;      
	        }
	 
	 public static ResultSet selectionSkills(Connection con) {
		 try {
			 String sql="select * from skills";
	    	 Statement smt= con.createStatement();
		      ResultSet rs=smt.executeQuery(sql);
		      
		      return rs;
		      
		 }catch(Exception e) {
			 e.printStackTrace();
			 return null;
		 }
		 
   }
	 public static int insertionCompany(Connection con,Company e ) {
		 try {
			 String Newligne=System.getProperty("line.separator");
			 String Description ="le site internet: "+e.getSite()+"Description de l\'company"+e.getDescription();
			 String Description1=Description.replaceAll("\'", " ");	 
			 String nom=e.getNom().replaceAll("\'", " ");
			 String secteur_activite=e.getSecteur_activite().replaceAll("\'", " ");
			 String sql="INSERT INTO `companies`(`name`,`secteur_activite`,`description`) VALUES ('"+nom+"','"+secteur_activite+"','"+Description1+"')";
				 Statement smt=con.createStatement();
				 int rs= smt.executeUpdate(sql);
				 return rs;
			 }catch(Exception e2) {
				 e2.printStackTrace();
				 return 0;
			 }
		 }
	public static int insertionOfferIndeed(Connection con, Offer o) {
		String Description =o.getDescription();
		 String Description1=Description.replaceAll("\'", " ");
		 String poste=o.getPoste().replaceAll("\'", " ");
		 try {
			 String sql="INSERT INTO `offers`(`title`,`type_contrat`,`niveauExprience`,`nbrPoste`,`description`,`company_id`) VALUES ('"+poste+"','"+o.getType_contrat()+"','"+o.getNiveauExprience()+"',"+o.getNbrPoste()+",'"+Description1+"',"+null+")";	
			 Statement smt=con.createStatement();
			 int rs= smt.executeUpdate(sql);
			// insertionSkills(con, o);
			 return rs;
		 }catch(Exception e1) {
			 e1.printStackTrace();
			 return 0;
	}
	 
	}
		 
		 
	 
	 
	
	 
	 
	 
}
