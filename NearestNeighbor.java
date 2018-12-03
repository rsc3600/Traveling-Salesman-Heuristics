import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Uses the nearest neighbor heuristic to approximate
 * the traveling salesman problem
 * @author maxgoldman
 *
 */
public class NearestNeighbor implements TSPSolution {
    
    @Override
    public List<String> getPath(GeographicGraph g, String city) {
        Set<String> remaining = g.getCities();
        List<String> path = new LinkedList<String>();
        
        path.add(city);
        while(remaining.size() > 0) {
            String smallestCity = "";
            double smallestDistance = Double.MAX_VALUE;
            String lastCity = path.get(path.size() - 1);
            
            for(String currentCity: remaining) {
                double distanceToCity = g.getDistance(lastCity, currentCity);
                if(distanceToCity < smallestDistance) {
                    smallestCity = currentCity;
                    smallestDistance = distanceToCity;
                }
            }
            path.add(smallestCity);
            remaining.remove(smallestCity);
        }
        path.add(city);
        return path;
    }

}
