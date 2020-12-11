/**
 * Class Edge represents every edge in the current graph
 */
public class Edge {

    private Vertex start;
    private Vertex end;
    private double weight; // distance

    // Constructor
    public Edge(Vertex start, Vertex end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    // Basic getters
    public Vertex getEnd() {
        return end;
    }

    public double getWeight() {
        return weight;
    }
}
