package shared;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import shared.Bd;
import models.*;

public class InsertIndeedOffers {

	public static void main(String[] args) throws IOException {
			Offre o=new Offre();
			Entreprise e = new Entreprise();

			Connection con= Bd.connexion();
			InputStream ips = new FileInputStream("C:\\Users\\salma\\OneDrive\\Bureau\\CSV\\combined-csv-files.csv"); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			
			int counter = 0;
			while ((line = br.readLine()) != null) {
				String[] s = line.split(";");
					/**System.out.println(s[0] + "\n");
					System.out.println(s[1] + "\n");*/
					o.setDescription(s[1]);
					o.setPoste(s[0]);
					
					//System.out.println(o.toString());
					int check =Bd.insertionOffreIndeed(con, o);
					System.out.println(check);
					}

			System.out.println("\n\n\n" + counter);
			br.close();
		

	}
}
