import java.util.List;

/**
 * Interface for a class that provides a potential 
 * solution to the traveling salesman problem
 * @author maxgoldman
 *
 */
public interface TSPSolution {
    /**
     * Returns a potential solution to the traveling
     * salesman problem
     * @param g the graph to evaluate
     * @param city the starting and ending city
     * @return a path that visits all vertices once and
     * starts and ends at the given city (includes the given city)
     */
    public List<String> getPath(GeographicGraph g, String city);
}
