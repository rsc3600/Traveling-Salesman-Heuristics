import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;
import java.awt.*;

public class Graph {
    
    /**
     * Set of all cities that must appear in Tour
     */
    private static Set<City> allCities = new HashSet<City>();
    
    //private static LinkedList<LinkedList<City>> allRoutes = new LinkedList<LinkedList<City>>();
    
    /**
     * First (therefore last) city in the tour -- always set to first city read in
     */
    private static City firstCity = null;
    
    /**
     * Adjacency Matrix representing city graph
     */
    static double[][] cityMatrix;
    
    public static void main(String[] args) {
  
        //Prompts the user to choose the tour size
        System.out.println("Would you like to find TSP on a (7), (15), or (128) city graph?");
        

        try {
            //read in user response
            BufferedReader cmdLine = new BufferedReader(new InputStreamReader(System.in));
            String citySize = cmdLine.readLine();
            
            //call function that builds appropriate sized tour and builds matrix
            readInPoints(citySize);

            //ask how they would like to calculate path
            System.out.println("Please press the number of how you would like to go about TSP");
            System.out.println("(1) Nearest Neighbor");
            System.out.println("(2) Insert Smallest");
            System.out.println("(3) Goldman's Algorithm");
            System.out.println("(4) All");
            System.out.println("(5) Brute Force Sol (only for 7 cities!)");
            
            int type = Integer.parseInt(cmdLine.readLine());
            
            //run appropriate 
            if (type == 1) {
                nearestNeighbor();
            } else if (type == 2) {
                insertSmallest();
            } else if (type == 3) {
                goldman();
            } else if (type == 4) {
                nearestNeighbor();
                insertSmallest();
                goldman();
            } else if (type == 5) {
                bruteForce();
            }
            
            
        } catch (IOException e) {
            System.out.println(e + " \n Please exit program and try again");
        }
            
    }
    
    /**
     * Function: Takes in the user inputted size and scrapes the web for the
     *           correct data set. It then constructs the cities and matrix 
     * <p>
     * @param size size of tour
     */
    public static void readInPoints(String size) {
        
        //creates connection to data website and store's contents as string
        URLGetter site = new URLGetter("https://people.sc.fsu.edu/~jburkardt/datasets/cities/cities.html");
        String sourceCode = getString(site.getContents());
        int sizeStr = 0;
        Matcher m = null;
        
        //if statements to build different matrices for diff user inputs
        if (size.contains("7")) {
            //pattern matches data-site to find the correct data set to build with
            Pattern p = Pattern.compile("<a href = \"([\\_\\.a-zA-Z07]*)\">[\\w0-7]*_dist.txt</a>");
            m = p.matcher(sourceCode);
            sizeStr = 7;
        } else if (size.contains("15")) {
            //pattern matches data-site to find the correct data set to build with
            Pattern p = Pattern.compile("<a href = \"([\\_\\.a-zA-Z15]*)\">[\\w]*15_dist.txt</a>");
            m = p.matcher(sourceCode);
            sizeStr = 15;
        } else if (size.contains("128")) {
            //pattern matches data-site to find the correct data set to build with
            Pattern p = Pattern.compile("<a href = \"([\\_\\.a-zA-Z128]*)\">[\\w]*128_dist.txt</a>");
            m = p.matcher(sourceCode);
            sizeStr = 128;
        }
        
        
        String fileLink = "https://people.sc.fsu.edu/~jburkardt/datasets/cities/";
        
        
        while (m.find()) {
            fileLink += m.group(1);
        }
        
        //collects matrix data set and shortens as needed
        String pointMatrixText = getString(new URLGetter(fileLink).getContents());
               
        //create double array to be filled
        cityMatrix = new double[sizeStr][sizeStr];
        
        //REGEX pattern to match pointMatrixText and extract cities and dist.
        Pattern pat = null;
        Matcher mat = null;
        if (sizeStr == 7) {
            pat = Pattern.compile("([0-9]{1,2}.[0-9])");
            mat = pat.matcher(pointMatrixText);
        } else {
            pat = Pattern.compile("([0-9]{1,})[\\s\\n\\r\\t]+");
            mat = pat.matcher(pointMatrixText);
        }
        
        int count = 0;
        
        //Use REGEX to find each city and distance in the text-map and fill our
        //array accordingly
        for (int i = 0; i < sizeStr; i++) {
            while (count < sizeStr) {
                if (mat.find()) {
                    cityMatrix[i][count] = Double.parseDouble(mat.group(1));
                    count++;
                }
            }
            count = 0;
        }
        
        //Make city objects for each city, and update the static adjacency matrix
        //Update global city list
        boolean test = true;
        for (int i = 0; i < sizeStr; i++) {
            City add = new City(cityMatrix[0][i], new Point(0,i));
            if (test) {
                firstCity = add;
                test = false;
            }
            allCities.add(add);
            add.distanceMatrix = cityMatrix;
        }
    }
    
    /**
     * Function: Returns the solution to TSP in a linkedlist
     * <p>
     * @return LinkedList list ordered according to TSP Solution
     * 
     */
    public static LinkedList<City> solutionToList() {
        LinkedList<City> roundTrip = new LinkedList<City>();
        roundTrip.add(firstCity);
        City currentCity = firstCity.getNext();
        while (currentCity != firstCity) {
            roundTrip.add(currentCity);
            currentCity = currentCity.getNext();
        }
        return roundTrip;
    }
    
    /**
     * Function: Helper function for turning website into a single string
     * <p>
     * @param list contains line-by-line website content
     * @return a single String of the website
     */
    public static String getString(ArrayList<String> list){
        String rtrn = "";
        for (String words : list) {
            rtrn += words + "\n";
        }
        return rtrn;
    }
    
    public static void nearestNeighbor() {
        List<String> results = new ArrayList<>();
	    NearestNeighbor nn = new NearestNeighbor();
	    GeographicGraphImpl geog = new GeographicGraphImpl(cityMatrix, allCities);
	    results = nn.getPath(geog, firstCity.stringRep());
	    System.out.println("---------NEAREST NEIGHBOR ALGORITHM----------");
	    for (String city : results) {
	    	    System.out.print(city + ", ");
	    }
	    System.out.println();
    }
    
    public static void insertSmallest() {
        List<String> results = new ArrayList<>();
	    InsertShortest is = new InsertShortest();
	    GeographicGraphImpl geog = new GeographicGraphImpl(cityMatrix, allCities);
	    results = is.getPath(geog, firstCity.stringRep());
	    System.out.println("---------INSERT SHORTEST ALGORITHM----------");
	    for (String city : results) {
	    	    System.out.print(city + ", ");
	    }
	    System.out.println();
    }
    
    public static void goldman() {
        List<String> results = new ArrayList<>();
    	    GoldmanAlgorithm gold = new GoldmanAlgorithm();
    	    GeographicGraphImpl geog = new GeographicGraphImpl(cityMatrix, allCities);
    	    results = gold.getPath(geog, firstCity.stringRep());
    	    System.out.println("---------GOLDMAN'S ALGORITHM----------");
    	    for (String city : results) {
    	    	    System.out.print(city + ", ");
    	    }
    	    System.out.println();

    }
    
    public static LinkedList<City> bruteForce() {
        LinkedList<City[]> allRoutes = permute(allCities.toArray(new City[allCities.size()]));
        TreeMap<Double, City[]> totalDistances = new TreeMap<Double, City[]>();
        Double sum = 0.0;
        
        //make list of routes that dont start with the first city
        LinkedList<City[]> toRemove = new LinkedList<City[]>();
        for (City[] city: allRoutes) {
            if (city[0] != firstCity) {
                toRemove.add(city);
            }
        }
        
        //remove those cities from our list
        for (City city[] : toRemove) {
            allRoutes.remove(city);
        }
        
        //
        for (City[] city : allRoutes) {
            for (int i = 0; i < city.length; i++) {
                if (i < city.length - 1) {
                    sum += city[i].distanceTo(city[i+1]);
                }
            }
            totalDistances.put(sum + city[city.length-1].distanceTo(city[0]), city);
        }
        
        City[] answer = totalDistances.get(totalDistances.firstKey());
        firstCity.changeNext(answer[1]);
        answer[answer.length-1].changeNext(firstCity);
        System.out.print(answer[0].stringRep() + " ");
        
        for (int i = 1; i < answer.length; i++) {
            System.out.print(answer[i].stringRep() + " ");
            if (i < answer.length - 1) {
                answer[i].changeNext(answer[i+1]);
            }
        }
        
        return solutionToList();
    }
    
    public static LinkedList<City[]> permute(City[] arr) {
        LinkedList<City[]> allRoutes = new LinkedList<City[]>();
       
        //do not even try to perform on large data sets
        if (allCities.size() > 7) {
            System.out.println("cannot brute force for a a route with more than 7 cities");
        } else {
            
            //base case returns the two permutations of that array
            if (arr.length == 2) {
                LinkedList<City[]> add = new LinkedList<City[]>();
                City[] arr1 = {arr[0], arr[1]};
                City[] arr2 = {arr[1], arr[0]};
                add.add(arr1);
                add.add(arr2);
                return add;
            }
            
            
            for (int i = 0; i < arr.length; i++) {
                
                //create an array of length elements - 1 and fill with every
                //value except i
                City[] temp = new City[arr.length - 1];
                int counter = 0;
                for (int j = 0; j < arr.length; j++) {
                    if (j != i) {
                        temp[counter] = arr[j];
                        counter++;
                    }
                }
                
                //make a linkedlist of arrays and add to them all of the permutations
                //of the elements after the 0th (through recursion)
                LinkedList<City[]> toAdd = new LinkedList<City[]>();
                toAdd = permute(temp);
                
                //for every array of cities, add the first element back to the front
                //and add to the return value
                for (City[] city : toAdd) {
                    City[] fin = new City[arr.length];
                    fin[0] = arr[i];
                    for (int j = 1; j < arr.length; j++) {
                        fin[j] = city[j-1];
                    }
                    allRoutes.add(fin);
                } 
            }
        }
        return allRoutes;
    } 
}








