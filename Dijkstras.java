import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstras {
	
	public Dijkstras() {}

	class Entry<A extends Comparable<A>, B> implements Comparable<Entry<A, B>> {

		private A key;
		private B value;

		public Entry(A key, B value) {
			this.key = key;
			this.value = value;
		}

		public A getKey() {
			return key;
		}

		public B getValue() {
			return value;
		}

		@Override
		public int compareTo(Entry<A, B> other) {
			return this.getKey().compareTo(other.getKey());
		}

	}

	public List<String> shortestPath(GeographicGraph g, String city1, String city2) {
		
	    if(city1 == null || city2 == null) {
	    	    throw new IllegalArgumentException("source or target city is null");
	    }
    	    if (city1.equals(city2)) {
    	    	    ArrayList<String> result = new ArrayList<>();
    	    	    result.add(city1);
    	    	    return result;
    	    }    	    
    	    
    	    
    		LinkedList<String> result = new LinkedList<>();
    		PriorityQueue<Entry<Double, String>> minHeap = new PriorityQueue<>();
    		
    	    //stores nodes that have been discovered as Keys, parents as values
    	    Map<String, String> parentMap = new HashMap<>();
    	    
    	    //stores nodes that have been discovered as Keys, current longest path to it as values
    	    Map<String, Double> weightMap = new HashMap<>();
    	    String currCity;
    	    
    	    for (String city : g.getCities()) {
    	      	Entry<Double, String> cityEntry = new Entry<Double, String>(Double.POSITIVE_INFINITY, city);
    	    	    minHeap.add(cityEntry);
    	    	    weightMap.put(city, Double.POSITIVE_INFINITY);
    	    }
    	    
    	    Entry<Double, String> city1Entry = new Entry<Double, String>(0.0, city1);
    	    minHeap.add(city1Entry);
    	    weightMap.put(city1, 0.0);
    	    parentMap.put(city1, null);
    	    int initSize = minHeap.size();
    	    int counter = 0;
    	    while(counter < initSize) {
    	    	    currCity = minHeap.poll().getValue();
    	    	    for (String neighbor : g.getCities()) {
    	    	    	   if (!currCity.equals(neighbor)){
        	    	    	   Double weight = weightMap.get(neighbor);
        	    	    	   Double edgeWeight = g.getDistance(currCity, neighbor);
        	    	    	   if (weight > weightMap.get(currCity) + edgeWeight) {
        	    	    		   Double newWeight = weightMap.get(currCity) + edgeWeight;
        	    	    		   minHeap.remove(new Entry<Double, String>(weightMap.get(neighbor), neighbor));
        	    	    		   weightMap.put(neighbor, newWeight);
        	    	    		   parentMap.put(neighbor, currCity);
        	    	    		   minHeap.add(new Entry<Double, String>(weightMap.get(neighbor), neighbor));
        	    	    	   }
    	    	    	   }
    	    	    }
    	    	    counter++;
    	    }
    	    String city = city2;
    	    result.add(city2);
    	    while (!city.equals(city1)) {
  	    	  String parent = parentMap.get(city);
    	    	  if (parent == null) {
    	    		  return new ArrayList<String>();
    	    	  }
    	    	  result.add(parent);
    	    	  city = parent;
    	    }
    	    Collections.reverse(result);
		return result;
	}
}
