package shared;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

public class InsertToDB {

	public static void main(String[] args) {
		try {
			Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
					MysqlCon.DBPassword);
			
			
			InputStream ips = new FileInputStream("ClassificationData/devops2 (1).csv"); // change it to your path
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			
			// TODO : INSERT IN COMPANIES A COMPANY NAMED Unknown
			// INSERT INTO companies(name) values('Unknown');
			
			// GET THE COPANY ID EXECUTING:
			// SELECT id FROM companies where name='Unknown'.
			int counter = 0;
			while ((line = br.readLine()) != null) {
				String[] s = line.split(";");
				
					// Hadou printithoum bach tchofi kolla s[?] chnou kat 3ni
					System.out.println(s[0] + "\n");
					System.out.println(s[1] + "\n");
					System.out.println(s[2] + "\n");
					System.out.println(s[3] + "\n");
					
					// chofi les attributs li kaynin fla table dial offers ou 3mrihoum bdakchi li jay mn excel
					// Then:
					// INSERT INTO offers(company_id, title....) values(1,?,?...).
					
				
			}

			System.out.println("\n\n\n" + counter);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
