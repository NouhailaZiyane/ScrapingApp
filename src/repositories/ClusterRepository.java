package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import clustering.Centroid;
import clustering.Cluster;
import shared.MysqlCon;

public class ClusterRepository {
	public static Centroid getNewMean(Cluster cluster, int ID) {
		Map<Integer, Double> points = cluster.getPoints();
		if (points.size() > 0) {
			Double total = 0.0;
			for (int p : points.keySet()) {
				total += points.get(p);
			}

			return new Centroid(ID, total / points.size());
		}
		return new Centroid(ID, 0.0);

	}

	public static Map<Integer, Double> getMap() {
		Map<Integer, Double> result = new HashMap<>();
		try {
			Connection conn = (Connection) DriverManager.getConnection(MysqlCon.DBUrl, MysqlCon.DBUserName,
					MysqlCon.DBPassword);

			PreparedStatement st = (PreparedStatement) conn.prepareStatement(
					"select skill_id, count(offer_id) as repeated from offer_skills group by skill_id");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				result.put(rs.getInt("skill_id"), rs.getDouble("repeated"));
			}
			conn.close();

		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}

		return result;
	}

	public static ArrayList<Cluster> generateOrderedClusters() {
		ArrayList<Centroid> centroids = new ArrayList<Centroid>();
		ArrayList<Centroid> centroids_ = new ArrayList<Centroid>(); // this will serve us to compare old values of means
																	// with new ones.
		Map<Centroid, Cluster> clusters = null;
		int maxIteration = 0;
		// We first add random means.
		// We can add as much centroids as we judge necessary.
		Centroid c1 = new Centroid(1, 84.0);
		Centroid c2 = new Centroid(2, 64.0);
		Centroid c3 = new Centroid(3, 61.0);
		Centroid c4 = new Centroid(4, 55.0);
		centroids.add(c1);
		centroids.add(c2);
		centroids.add(c3);
		centroids.add(c4);

		ArrayList<Cluster> result = new ArrayList<Cluster>();

		Map<Integer, Double> points = getMap();

		// Set the other centroids list.
		for (Centroid ce : centroids) {
			centroids_.add(ce);
		}
		int counter = 1;
		while (true) {
//			System.out.println(
//					"\n\n--------------------------ITERATION NUMBER = " + counter + "----------------------\n\n");
			 clusters = Cluster.generateClusters(centroids, points);

			// The number of clusters = numbers of centroids
			for (Centroid cen : clusters.keySet()) {
				for (int i = 0; i < centroids.size(); i++) {
					if (cen.getKey() == centroids.get(i).getKey()) {
//						System.out.println(clusters.get(cen).toString());
						centroids.set(i, getNewMean(clusters.get(cen), i + 1));
//						System.out.println("MEAN CHANGED FOR CLUSER n* " + cen.getKey() + " " + cen.toString() + " => "
//								+ centroids.get(i).toString() + "\n\n");
					}
				}

			}

			if (centroids.equals(centroids_)) {
				System.out.println("STOPPED NORMALLY\n");
				break;
			} else {
				centroids_ = new ArrayList<Centroid>();
				for (Centroid ce : centroids) {
					centroids_.add(ce);
				}
			}

			counter++;
			// If you want to limit the number of iterations, uncomment the next section.
//			if(COUNTER == maxIteration) {
//				System.out.println("REACHED MAX ITERATION NUMBER.");
//				break;
//			}

		}
		for (Centroid c : clusters.keySet()) {
			result.add(clusters.get(c));
		}
		
		return orderClusters(result, new ArrayList<Cluster>());
	}

	public static ArrayList<Cluster> orderClusters(ArrayList<Cluster> clusters, ArrayList<Cluster> res) {
		ArrayList<Cluster> result = res;
		// ArrayList is an ordered collection, so for us, index 1 means the cluster that
		// has the most wanted skills and so on.

		Cluster maxCluster = null;

		// A greater value of centroid means a a cluster containing the most wanted
		// skills.
		// Since the centroid, in this case, is the mean of the number of posts in wich
		// the
		if (clusters.size() == 0) {
			for (int i = 0; i < clusters.size(); i++) {
				System.out.println(clusters.get(i).toString());
			}
			return result;
		}
		Double max = 0.0;
		// we should order clusters
		for (Cluster c : clusters) {
			if (c.getCentroid().getValue() > max) {
				max = c.getCentroid().getValue();
				maxCluster = c;
			}
		}

		result.add(maxCluster);
		clusters.remove(maxCluster);
		return orderClusters(clusters, result);
	}

}
