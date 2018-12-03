import java.util.Set;

/**
 * Interface for a geographic map in which there
 * is a distance between any two cities
 * @author maxgoldman
 *
 */
public interface GeographicGraph {
    /**
     * Gets the distance between two cities
     * @param city1 the first city
     * @param city2 the second city
     * @return the distance between them
     * @throws IllegalArgumentException if either city
     * is not in graph
     */
    public double getDistance(String city1, String city2);
    
    /**
     * Gets all the cities in the graph
     * @return the set of all cities in the graph
     */
    public Set<String> getCities();
    
    /**
     * Gets whether or not a city is in the graph
     * @param city the city to check for
     * @return true if city is in graph. false 
     * otherwise
     */
    default boolean hasCity(String city) {
        return getCities().contains(city);
    }
    
    /**
     * Gets the number of cities in the graph
     * @return the number of cities in the graph
     */
    public int size();
    
    /**
     * 
     * @param city1 the first city to remove the edge from
     * @param city2 the second city to remove an edge from
     * @return
     */
    public GeographicGraph removeEdge(String city1, String city2);
}
