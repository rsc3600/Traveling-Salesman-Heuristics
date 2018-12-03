import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GeographicGraphImpl implements GeographicGraph {

	double[][] adjMatrix;
	Map<String, City> nameMap;
	Set<City> citySet;
	
	public GeographicGraphImpl(double[][] am, Set<City> cSet) {
		adjMatrix = am;
		nameMap = new HashMap<>();
		citySet = cSet;
		for (City city : cSet) {
			nameMap.put(city.stringRep(), city);
		}
	}
	
	@Override
	public double getDistance(String city1, String city2) {
		City firstCity = nameMap.get(city1);
		City secondCity = nameMap.get(city2);
		return 	adjMatrix[firstCity.getIndex()][secondCity.getIndex()];
	}

	@Override
	public Set<String> getCities() {
		Set<String> result = new HashSet<>();
		for (String city : nameMap.keySet()) {
			result.add(city);
		}
		return result;
	}

	@Override
	public int size() {
		return nameMap.size();
	}
	
	public GeographicGraphImpl removeEdge(String city1, String city2) {
		City firstCity = nameMap.get(city1);
		City secondCity = nameMap.get(city2);
		double[][] adjMatrix2 = new double[adjMatrix.length][adjMatrix[0].length];
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix[0].length; j++) {
				adjMatrix2[i][j] = adjMatrix[i][j];
			}
		}
		adjMatrix2[firstCity.getIndex()][secondCity.getIndex()] = Double.POSITIVE_INFINITY;
		adjMatrix2[secondCity.getIndex()][firstCity.getIndex()] = Double.POSITIVE_INFINITY;
		return new GeographicGraphImpl(adjMatrix2, citySet);
	}

	
	
}
