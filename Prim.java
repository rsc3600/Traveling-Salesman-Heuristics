import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class that uses Prim's algorithm to get
 * a minimum spanning tree of a GeographicGraph
 * @author maxgoldman
 *
 */
public class Prim {

    private static class DistanceTuple {
        String city;
        double distance;
        
        DistanceTuple(String city, double distance) {
            this.city = city;
            this.distance = distance;
        }
    }
    
    /**
     * Gets a MST of a given graph
     * @param g the initial graph
     * @return an MST
     */
    public static NormalGeographicGraph getMST(GeographicGraph g) {
        Map<String, DistanceTuple> distanceFromTree = new HashMap<String, DistanceTuple>();
        NormalGeographicAdjacencyList MST = new NormalGeographicAdjacencyList();
        String currentCity = "";
        for(String city: g.getCities()) {
            distanceFromTree.put(city, new DistanceTuple(null, Double.MAX_VALUE));
            currentCity = city;
        }
        
        distanceFromTree.put(currentCity, new DistanceTuple(null, 0.0));
        while(MST.size() < g.size()) {
            Entry<String, DistanceTuple> minEntry = getMin(distanceFromTree);
            String min = minEntry.getKey();
            String sourceCity = minEntry.getValue().city;
            MST.addCity(min);
            if(sourceCity != null) {
                MST.addConnection(sourceCity, min, minEntry.getValue().distance);
            }
            distanceFromTree.remove(min);
            for(String neighbor: distanceFromTree.keySet()) {
                double distance = g.getDistance(min, neighbor);
                if(distance < distanceFromTree.get(neighbor).distance) {
                    distanceFromTree.put(neighbor, new DistanceTuple(min, distance));
                }
            }
        }
        return MST;
    }
    
    private static Entry<String, DistanceTuple> getMin(Map<String, DistanceTuple> map) {
        Entry<String, DistanceTuple> currentMin = null;
        for(Entry<String, DistanceTuple> entry: map.entrySet()) {
            if(currentMin == null || currentMin.getValue().distance > entry.getValue().distance) {
                currentMin = entry;
            }
        }
        return currentMin;
    }
    
}
