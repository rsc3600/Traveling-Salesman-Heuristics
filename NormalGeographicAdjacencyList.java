import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NormalGeographicAdjacencyList implements NormalGeographicGraph {

    Set<String> cities;
    Map<String, Map<String, Double>> edges;
    
    public NormalGeographicAdjacencyList() {
        cities = new HashSet<String>();
        edges = new HashMap<String, Map<String, Double>>();
    }
    
    public void addCity(String city) {
        if(city != null && !hasCity(city)) {
            cities.add(city);
            edges.put(city, new HashMap<String, Double>());
        }
    }
    
    public void addConnection(String city1, String city2, double distance) {
        if(hasCity(city1) && hasCity(city2) && !hasConnection(city1, city2)) {
            edges.get(city1).put(city2, distance);
            edges.get(city2).put(city1, distance);
        }
    }
    
    @Override
    public Set<String> getNeighbors(String city) {
        Map<String, Double> cityEdges = edges.get(city);
        return cityEdges.keySet();
    }

    @Override
    public boolean hasConnection(String city1, String city2) {
        return edges.get(city1).containsKey(city2);
    }
    
    @Override
    public Set<String> getCities() {
        return cities;
    }

    @Override
    public int size() {
        return cities.size();
    }

    @Override
    public double getDistance(String city1, String city2) {
        return edges.get(city1).get(city2);
    }

	@Override
	public NormalGeographicGraph removeEdge(String city1, String city2) {
		throw new UnsupportedOperationException("cannot remove edge from a NormalGeographicGraph");
	}
    
    
}
