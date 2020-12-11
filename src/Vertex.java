import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class Vertex represents every vertex in the current graph
 */
public class Vertex implements Comparable<Vertex> {

    // id
    private final int id;
    // all connected edges of the current vertex
    private ArrayList<Edge> edges;
    // visited vertices list
    private LinkedList<Vertex> path;
    // minimum distance between vertex to a start vertex
    private double minDistance = Double.MAX_VALUE;

    // Constructor
    public Vertex(int id) {
        this.id = id;
        this.edges = new ArrayList<>();
        this.path = new LinkedList<>();
    }

    /**
     * Compare the current vertex's minDistance with another vertex
     *
     * @param o the other vertex
     * @return -1 or 1
     */
    @Override
    public int compareTo(Vertex o) {
        return Double.compare(this.minDistance, o.minDistance);
    }

    /**
     * Helper function for printing the path out
     *
     * @return vertex's id
     */
    @Override
    public String toString() {
        return String.valueOf(this.id);
    }

    // Basic setters and getters
    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public LinkedList<Vertex> getPath() {
        return path;
    }

    public void setPath(LinkedList<Vertex> path) {
        this.path = path;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public int getId() {
        return id;
    }
}