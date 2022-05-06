package clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cluster {

	// A cluster belongs to Centroid
	private Centroid centroid;
	private Map<Integer, Double> points = new HashMap<>();

	public Cluster(Map<Integer, Double> points, Centroid centroid) {
		this.points = points;
		this.centroid = centroid;
	}

	// =======================================//
	// =======================================//

	public Centroid getCentroid() {
		return centroid;
	}

	public Map<Integer, Double> getPoints() {
		return points;
	}

	public void setPoints(Map<Integer, Double> points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "Cluster n*" + centroid.getKey() + " ================================\n\nCENTROID   :" + centroid
				+ "\nPOINTS     :" + points.toString() + "\n\n=============================================\n\n";
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

	public void addPoint(Point point) {
		this.points.put(point.getKey(), point.getValue());
	}

	public static Map<Centroid, Cluster> generateClusters(ArrayList<Centroid> centroids, Map<Integer, Double> points) {

		int centroidsSize = centroids.size();
		Map<Centroid, Cluster> clusters = new HashMap<>();
		// we're going to initialize a number of clusters equals to the number of
		// centroids given as args.
		for (int i = 0; i < centroidsSize; i++) {
			Map<Integer, Double> newSetOfPoints = new HashMap<>();
			clusters.put(centroids.get(i), new Cluster(newSetOfPoints, centroids.get(i)));
		}

		for (int key : points.keySet()) {
			Point p = new Point(key, points.get(key));
			Centroid closestCentroid = Centroid.getNearestCentroid(centroids, p);
			clusters.get(closestCentroid).addPoint(p);
		}
		
		
		return clusters;
	}
}
