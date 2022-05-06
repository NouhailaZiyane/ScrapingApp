package clustering;

import java.util.HashMap;
import java.util.Map;

public class Point {
	private Map<Integer, Double> coordinates = new HashMap<>();
	private int key;

	private Double value;

	public Point(int key, Double value) {
		this.coordinates.put(key, value);
		this.key = key;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Map<Integer, Double> getCoordinates() {
		return coordinates;
	}

}
