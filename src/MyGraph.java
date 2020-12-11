import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Graph class is used for generate a graph using the given graph data
 */
public class MyGraph {

    // All vertices in a graph
    private ArrayList<Vertex> vertices;

    // Constructor
    public MyGraph(int verticesNum) {
        this.vertices = new ArrayList<>();
        // Add verticesNum vertices into a graph
        // Vertices ID: from 0 to verticesNum - 1
        for (int i = 0; i < verticesNum; i++) {
            this.vertices.add(new Vertex(i));
        }
    }

    /**
     * Add a weighted edge to a graph
     *
     * @param start  the start point in a line
     * @param end    the end point of a line
     * @param weight the distance between the start point and the end point
     */
    public void addEdge(int start, int end, double weight) {
        Edge newEdge = new Edge(this.vertices.get(start), this.vertices.get(end), weight);
        vertices.get(start).getEdges().add(newEdge);
    }

    /**
     * Get the vertex with a given index
     *
     * @param index the index of the vertices
     * @return vertex with the given index
     */
    public Vertex getVertex(int index) {
        return vertices.get(index);
    }

    // get the number of vertices in the current graph
    public int getVertexNumber() {
        return this.vertices.size();
    }

    /**
     * Compute the shortest distance between the start vertex and all other vertices in the graph
     *
     * @param start the start vertex
     */
    public void dijkstra(Vertex start) {

        start.setMinDistance(0);
        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Vertex next = queue.poll();
            for (Edge e : next.getEdges()) {
                Vertex end = e.getEnd();
                double newDistance = next.getMinDistance() + e.getWeight();
                if (end.getMinDistance() > newDistance) {
                    // remove it to update
                    queue.remove(end);
                    end.setMinDistance(newDistance);
                    end.setPath(new LinkedList<>(next.getPath()));
                    // add the new next vertex
                    end.getPath().add(next);
                    // add it back
                    queue.add(end);
                }
            }
        }
    }

    /**
     * Print out the path information
     *
     * @param destination the destination place corresponding vertex
     */
    public void printShortestPath(Vertex destination) {
        boolean found = false;
        StringBuilder stringBuilder = new StringBuilder("Shortest Path (distance:");
        stringBuilder.append(destination.getMinDistance()).append(") from warehouse to the place ");
        stringBuilder.append(destination.getId()).append(" is: ");
        for (Vertex v : destination.getPath()) {
            stringBuilder.append(v).append(" -> ");
            found = true;
        }
        if (found) {
            stringBuilder.append(destination.getId());
        } else {
            stringBuilder.append("no path");
        }
        System.out.println(stringBuilder.toString());
    }

    public static void main(String[] args) throws IOException {
        // for empirical analysis
        int graphSize = 500;
        StringBuilder stringBuilder = new StringBuilder();
        MyGraph myGraph;
        long start, end, duration;
        int vertexNumber;

        for (int i = 3; i < graphSize; i++) {
            duration = 0;
            vertexNumber = 100 * i;

            myGraph = GUI.initializeMyGraph("graphData" + i + ".txt", vertexNumber);

            for (int j = 0; j < 10; j++) {
                start = System.nanoTime();
                myGraph.dijkstra(myGraph.getVertex(0));
                end = System.nanoTime();
                duration = duration + end - start;
            }
            stringBuilder.append(duration / 10).append(", ");
        }
        System.out.println(stringBuilder.toString().trim());
    }
}
