import java.util.ArrayList;
import java.util.List;

public class DijkstrasSoln implements TSPSolution {

	public DijkstrasSoln() {		
	}
	
	@Override
	public List<String> getPath(GeographicGraph g, String city) {
		
		//initialize variables
		List<String> result = new ArrayList<>();
		int numCities = 0;
		int origSize = g.size();
		Dijkstras dij;
		
		//find the farthest city from the original city
		String farthestCity = "";
		double farthestDist = 0.0;
		for (String potentialFarthest : g.getCities()) {
			if (g.getDistance(city, potentialFarthest) > farthestDist) {
				farthestDist = g.getDistance(city, potentialFarthest);
				farthestCity = potentialFarthest;
			}
		}
		
		//find the shortest path to the farthest city that uses at least 1/2 - 1 nodes
		while ((double) numCities < origSize / 2) {
			dij = new Dijkstras();
			result = dij.shortestPath(g, city, farthestCity);
			numCities = result.size();
			for (String cityy : result) {
				int idx = result.indexOf(cityy);
				if (idx != result.size() - 1) {
					String nextCity = result.get(idx + 1);
					g = g.removeEdge(cityy, nextCity);
				}
			}
		}
		
		//find the shortest path back using the remaining nodes
		List<String> secondHalf = new ArrayList<>();
		while ((double) (numCities + secondHalf.size()) < origSize) {
			dij = new Dijkstras();
			secondHalf = dij.shortestPath(g, farthestCity, city);
			for (String cityy : secondHalf) {
				int idx = secondHalf.indexOf(cityy);
				if (idx != secondHalf.size() - 1) {
					String nextCity = secondHalf.get(idx + 1);
					g = g.removeEdge(cityy, nextCity);
				}
			}
		}
		
		result.addAll(secondHalf);
		return result;
	}

}
