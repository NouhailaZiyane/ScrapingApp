package bd;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.Entreprise;
import models.Offre;

public class Bd {
	
	public Connection connexion(){
		try {
		     Class c= Class.forName("com.mysql.cj.jdbc.Driver");//First loading the MySQL driver, retrieving an object of type Class
		     //JDBC driver that provides database connectivity
		     Driver pilote=(Driver)c.newInstance();// return an instance of the jdbc driver
		     DriverManager.registerDriver(pilote);// register the driver by the administrator jdbc
		     String protocole="jdbc:mysql:";
		     String ip="localhost";
		     String port="3306";
		     String nomBase="app2";
		     String conString=protocole+"//"+ip+":"+port+"/"+nomBase;
		     String nomConnexion="root";
		     String motDePasse="";

		     Connection con = DriverManager.getConnection(conString, nomConnexion, motDePasse);// establish the connection
		     
		         return con;
		    } catch (Exception e) {
		      e.printStackTrace();
		      return null;
		    }    }
	public ResultSet selectionOffre(Connection con) {
		 try {
			//create string to stock the query
			 String sql="select * from offers";
	    	 /**statment an interface that represents a SQL statement*/
	    	  Statement smt= con.createStatement();//send the query to the database
	    	  
		      ResultSet rs=smt.executeQuery(sql);//a table of data representing a database result set
		      
		      return rs;
		      
		 }catch(Exception e) {
			 e.printStackTrace();
			 return null;
		 }
		 
    }
	public ResultSet selectionEntreprise(Connection con) {
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
	
	 public int insertionOffre(Connection con,Offre o, Entreprise e ) {
		 String Description =o.getDescription();
		 String Description1=Description.replaceAll("\'", " ");// we replace the simple quote because there is a problem with the database
		 String poste=o.getPoste().replaceAll("\'", " ");//get the job from the offer
		 try {
			 String sql="INSERT INTO `offers`(`title`,`type_contrat`,`niveauExprience`,`nbrPoste`,`description`,`company_id`) VALUES ('"+poste+"','"+o.getType_contrat()+"','"+o.getNiveauExprience()+"',"+o.getNbrPoste()+",'"+Description1+"',"+e.getId()+")";
			 Statement smt=con.createStatement();
			 int rs= smt.executeUpdate(sql);// to execute a statment that change the data as insert update or delete
			// insertionSkills(con, o);
			 return rs;
		 }catch(Exception e1) {
			 e1.printStackTrace();
			 return 0;
	 }} 
	public int insertionSkills(Connection con,Offre o) {
		
		 String skills =o.getSkills();
	     skills=skills.toLowerCase();
	     skills=skills.replaceAll(",", "");
	     
	   
  String m1;
  int id;int rs=0;
      ResultSet rs1= selectionSkills(con);
	 try {
		while(rs1.next()) {// fetch the data
			m1=" "+rs1.getString("name")+" ";
			id =rs1.getInt("id");
			if(skills.contains(m1)) {//verify if the skills of the offer exist in our table 
				try {//if it is we insert the  id of the skill and the id of the offer in the table offer_skill
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
	 
	 public ResultSet selectionSkills(Connection con) {
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
	 public int insertionEntreprise(Connection con,Entreprise e ) {
		 try {
			 String Newligne=System.getProperty("line.separator");
			 String Description ="le site internet: "+e.getSite()+"Description de l\'entreprise"+e.getDescription();
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
	 
	
		 
		 
	 
	 
	
	 
	 
	 
}

	