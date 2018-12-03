import java.awt.*;

public class City {
	/**
	 * matrix of all cities and the distance between them
	 */
	public static double[][] distanceMatrix = null;

	/**
	 * where to find this city in the matrix
	 */
	private Point matrixIndex;

	/**
	 * the next city to appear (after this instance) on the TSP tour
	 */
	private City nextInTour;

	/**
	 * name/ID of city represented as a double
	 */
	private double cityID;

	/**
	 * @param name
	 *            of city
	 * @param idx
	 *            where in the matrix this city is
	 */
	public City(double name, Point idx) {
		this.cityID = name;
		this.nextInTour = null;
		this.matrixIndex = idx;
	}

	/**
	 * @param name
	 *            of city
	 * @param next
	 *            city in the tour
	 * @param idx
	 *            where in the matrix this city is
	 */
	public City(double name, City next, Point idx) {
		this.cityID = name;
		this.nextInTour = next;
		this.matrixIndex = idx;
	}

	/**
	 * @return String of city's ID
	 */
	public String stringRep() {
		String rtrn = "CityID: " + this.cityID;
		return rtrn;
	}

	/**
	 * Function: changes the next city in the tour after this city instance
	 */
	public void changeNext(City next) {
		this.nextInTour = next;
	}

	/**
	 * @return City next in the tour
	 */
	public City getNext() {
		return this.nextInTour;
	}

	/**
	 * Function: returns the distance between the city calling the method and the
	 * argued city
	 * 
	 * @param travels
	 *            city you want to find distance to
	 * @return distance (as double) between this city and the argued city
	 */
	public double distanceTo(City travels) {
		return distanceMatrix[(int) this.matrixIndex.getY()][(int) travels.matrixIndex.getY()];
	}

	public int getIndex() {
		return (int) this.matrixIndex.getY();
	}
}