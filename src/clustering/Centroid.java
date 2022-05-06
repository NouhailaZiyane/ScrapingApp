package clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Centroid {
	private static int ID;
	private int key;
	public int getKey() {
		return key;
	}

	public Double getValue() {
		return value;
	}

	private Double value;
	public int getID() {
		return ID;
	}

	private Map<Integer, Double> coordinates = new HashMap<>();

	public Centroid(int key, Double value) {
		this.key = key;
		this.value = value;
		this.coordinates.put(key, value);
	}
	
	public Map<Integer, Double> getCoordinates() {
		return coordinates;
	}

	public static Centroid getNearestCentroid(ArrayList<Centroid> centroids, Point point) {
		boolean firstIteration = true;
		Double min = 0.0;
		Centroid result = new Centroid(-1, min);
		for (Centroid c : centroids) {

			Double euclideanDistance = Distance.calculate(c.getValue(), point.getValue());
			if (firstIteration) {
				min = euclideanDistance;
				result = c;
				firstIteration = false;
			} else {
				if (euclideanDistance < min) {
					// Current nearest Neighbor.
					min = euclideanDistance;
					result = c;
					
				}
			}

		}

		return result;
	}

	@Override
	public String toString() {
		return "coordinates=" + coordinates + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(coordinates);
	}


	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Centroid other = (Centroid) obj;
		return Objects.equals(coordinates, other.coordinates);
	}
}
