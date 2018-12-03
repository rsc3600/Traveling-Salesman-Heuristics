import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GoldmanAlgorithm implements TSPSolution {

    @Override
    public List<String> getPath(GeographicGraph g, String city) {
        NormalGeographicGraph t = Prim.getMST(g);
        List<String> path = new LinkedList<String>();
        Set<String> notInPath = t.getCities();
        Set<String> inPath = new HashSet<>();
        
        path.add(city);
        inPath.add(city);
        notInPath.remove(city);
        while(notInPath.size() > 0) {
            String smallestTarget = "";
            double smallestDistance = Double.MAX_VALUE;
            for(String source: inPath){
                for(String target: notInPath) {
                    if(t.hasConnection(source, target)) {
                        double distance = t.getDistance(source, target);
                        if(distance < smallestDistance) {
                            smallestTarget = target;
                            smallestDistance = distance;
                        }
                    }
                }
            }
            inPath.add(smallestTarget);
            notInPath.remove(smallestTarget);
            path.add(smallestTarget);
        }
        path.add(city);
        return path;
    }

}
