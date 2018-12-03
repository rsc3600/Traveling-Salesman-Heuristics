import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class InsertShortest implements TSPSolution {

	public InsertShortest() {
	}
	
	@Override
	public List<String> getPath(GeographicGraph g, String city) {
		ArrayList<String> path = new ArrayList<>();
		String currCity = city;
		Set<String> remaining = g.getCities();
		path.add(city);
		remaining.remove(currCity);
		while (remaining.size() > 0) {
			String minInsertionStart = city;
			double minDistanceIncr = Double.POSITIVE_INFINITY;
			Iterator<String> iter = remaining.iterator();
			while (iter.hasNext()) {
				String remainingCity = iter.next();
				if (path.size() == 1) {
                    path.add(1, remainingCity);
				} else {
					for (String pathCity : path) {
						int idx = path.indexOf(pathCity);
						if (idx != path.size() - 1) {
							String nextCity = path.get(idx + 1);
							double currDistance = g.getDistance(pathCity, nextCity);
							double newDistance = g.getDistance(pathCity, remainingCity) +
									g.getDistance(remainingCity, nextCity);
							if (newDistance - currDistance < minDistanceIncr) {
								minDistanceIncr = g.getDistance(pathCity, remainingCity) +
										g.getDistance(remainingCity, nextCity);
								minInsertionStart = pathCity;
							}
						}
					}
					path.add(path.indexOf(minInsertionStart) + 1, remainingCity);
				}
				iter.remove();
			}	
		}
		path.add(city);
		return path;
	}

}
