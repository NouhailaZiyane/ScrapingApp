package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import clustering.Cluster;
import models.Company;
import models.Offer;
import models.Skill;
import shared.MysqlCon;

public class OfferRepository {

	// We should get offers related to user's skills.
	public static ArrayList<Offer> getOffers(String username, Offer lastViewsOffer) {
		// The lastViewsOffer will help us to paginate posts since the offers are going
		// to be ordered.

		ArrayList<Offer> offers = new ArrayList<Offer>();

		try {
			ArrayList<Skill> userSkills = UserRepository.getUserSkills(username);
			int userSkillsSize = userSkills.size();
			Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
					MysqlCon.DBPassword);

			String query = "select company_id, title,description, type_contrat, niveauExprience, nbrPoste , count(skill_id) as count_skills from offer_skills left join"
					+ " skills on skills.id=offer_skills.skill_id "
					+ "left join offers on offers.id=offer_skills.offer_id "
					+ " where skill_id in (";
			for (int i = 0; i < userSkillsSize; i++) {
				if (i == userSkillsSize - 1) {
					query += userSkills.get(i).getSkillID() + ")";
				} else {
					query += userSkills.get(i).getSkillID() + ", ";
				}

			}
			
			query += "group by offer_id order by count_skills desc ";
			System.out.println(query);
			PreparedStatement st = (PreparedStatement) conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				int companyID = rs.getInt("company_id");
				String poste = rs.getString("title");
				String description = rs.getString("description");
				String skills = null;
				String type_contrat = rs.getString("type_contrat");
				String niveauExprience = rs.getString("niveauExprience");
				String nbrPoste = rs.getString("nbrPoste");
				Company company = CompanyRepository.getCompanyById(companyID);

				Offer offer = new Offer(poste, description, skills, type_contrat, niveauExprience, nbrPoste, company);
				offers.add(offer);
			}
			conn.close();

			return offers;

		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}

		return null;
	}
}
