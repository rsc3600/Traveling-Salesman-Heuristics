import java.util.Set;

/**
 * Interface for a geographic map in which not all cities are
 * necessarily adjacent
 * @author maxgoldman
 *
 */
public interface NormalGeographicGraph extends GeographicGraph {

    /**
     * Gets the distance between two cities
     * @param city1 the first city
     * @param city2 the second city
     * @return the distance between them
     * @throws IllegalArgumentException if either city
     * is not in graph
     * @throws IllegalArgumentException if the two cities 
     * are not adjacent
     */
    @Override
    public double getDistance(String city1, String city2);
    
    /**
     * Gets the neighbors of the given city
     * @param city the city
     * @return the neighbors of the city
     */
    public Set<String> getNeighbors(String city);
    
    /**
     * Determines whether there is a connection between the
     * two cities
     * @param city1 the first city
     * @param city2 the second city
     * @return true if there is a connection between the cities,
     * false otherwise
     */
    public boolean hasConnection(String city1, String city2);
    
    /**
     * Removes an edge between two cities
     * @param city1 the first city
     * @param city2 the second city
     */
    public NormalGeographicGraph removeEdge(String city1, String city2);
}
