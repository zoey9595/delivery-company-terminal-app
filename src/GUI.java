import java.io.*;
import java.util.*;

/**
 * Main class for user interface
 */
public class GUI {

    private static final int QUIT_OPTION = 6;

    /**
     * Generate an item array to contain all items based on data generated by WarehouseDataGenerator
     *
     * @param fileName the input file name
     * @return the min heap
     * @throws IOException file not exists
     */
    public static Item[] generateItems(String fileName) throws IOException {
        File file = new File(fileName);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        ArrayList<Item> itemArrayList = new ArrayList<>();
        int id, deadline, destination, location;
        while ((line = bufferedReader.readLine()) != null) {
            String[] temp = line.split(",");
            id = Integer.parseInt(temp[0]);
            deadline = Integer.parseInt(temp[1]);
            destination = Integer.parseInt(temp[2]);
            location = Integer.parseInt(temp[3]);
            itemArrayList.add(new Item(id, deadline, destination, location));
        }
        Item[] items = new Item[itemArrayList.size()];
        for (int i = 0; i < itemArrayList.size(); i++) {
            items[i] = itemArrayList.get(i);
        }
        return items;
    }


    /**
     * Read in a txt file (generated by graphDataGenerator) and create a graph
     *
     * @param fileName    input filename
     * @param verticesNum number of vertices
     * @return corresponding graph
     * @throws IOException file not exists
     */
    public static MyGraph initializeMyGraph(String fileName, int verticesNum) throws IOException {
        File file = new File(fileName);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;

        MyGraph graph = new MyGraph(verticesNum);
        int start, end, weight;
        while ((line = bufferedReader.readLine()) != null) {
            String[] temp = line.split(",");
            start = Integer.parseInt(temp[0]);
            end = Integer.parseInt(temp[1]);
            weight = Integer.parseInt(temp[2]);
            graph.addEdge(start, end, weight);
        }
        return graph;
    }

    /**
     * Read in a txt file (generated by graphDataGenerator) and create a graph developed by GitHub users
     *
     * @param fileName    input filename
     * @param verticesNum number of vertices
     * @return the reference graph
     * @throws IOException file not exists
     */
    public static DijkstrasShortestPathAdjacencyList initializeGeekGraph(String fileName, int verticesNum) throws IOException {
        File file = new File(fileName);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        DijkstrasShortestPathAdjacencyList reference = new DijkstrasShortestPathAdjacencyList(verticesNum);

        int start, end, weight;
        while ((line = bufferedReader.readLine()) != null) {
            String[] temp = line.split(",");
            start = Integer.parseInt(temp[0]);
            end = Integer.parseInt(temp[1]);
            weight = Integer.parseInt(temp[2]);
            reference.addEdge(start, end, weight);
        }
        return reference;
    }


    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);

        // User guide
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("|                   Welcome to the Delivery System                      |");
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("|                 Please select the test item set first                 |");
        System.out.println("|              1. 2^6  items (test-f1-1.txt/test-f2-1.txt)              |");
        System.out.println("|              2. 2^11 items (test-f1-2.txt/test-f2-2.txt)              |");
        System.out.println("|              3. 2^16 items (test-f1-3.txt/test-f2-3.txt)              |");
        System.out.println("|              4. 2^21 items (test-f1-4.txt/test-f2-4.txt)              |");
        System.out.println("|              5. 2^23 items (test-f1-5.txt/test-f2-5.txt)              |");
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("Please enter the test file number:");

        // Initialise an array to hole the items
        Item[] warehouseItems = new Item[0];

        // Read in different test files
        int testNum = in.nextInt();
        while (testNum != 0) {
            switch (testNum) {
                case 1:
                    warehouseItems = generateItems("test-f1-1.txt");
                    testNum = 0;
                    break;
                case 2:
                    warehouseItems = generateItems("test-f1-2.txt");
                    testNum = 0;
                    break;
                case 3:
                    warehouseItems = generateItems("test-f1-3.txt");
                    testNum = 0;
                    break;
                case 4:
                    warehouseItems = generateItems("test-f1-4.txt");
                    testNum = 0;
                    break;
                case 5:
                    warehouseItems = generateItems("test-f1-5.txt");
                    testNum = 0;
                    break;
                default:
                    System.out.println("There is no test file with id " + testNum + ", please try again :)");
                    testNum = in.nextInt();
            }
        }

        // Generate my minheap and red-black tree using the test data
        MinHeap minHeap = null;
        RedBlackTree redBlackTree = new RedBlackTree(warehouseItems);

        // Java libraries (used to prove the correctness of the data structure/algorithm implemented by myself)
        // 1. Min-heap reference
        PriorityQueue<Item> standardMinHeap = new PriorityQueue<>(Comparator.comparingInt(Item::getDeadline));
        standardMinHeap.addAll(Arrays.asList(warehouseItems));

        // 2. Red-Black tree reference
        TreeMap<Integer, Item> treeMap = new TreeMap<>();
        for (Item i : warehouseItems) {
            treeMap.put(i.getId(), i);
        }

        // Read different graph data
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("|                         Please select a test graph                    |");
        System.out.println("|                     1. 300  vertices  (test-f3-1.txt)                 |");
        System.out.println("|                     2. 1000  vertices (test-f3-2.txt)                 |");
        System.out.println("|                     3. 5000  vertices (test-f3-3.txt)                 |");
        System.out.println("|                     4. 25000 vertices (test-f3-4.txt)                 |");
        System.out.println("|                     5. 50000 vertices (test-f3-5.txt)                 |");
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("Please enter the testing graph number:");

        // Generate my graph using the test data
        MyGraph myGraph = null;
        // 3. Dijkstra's algorithm reference
        DijkstrasShortestPathAdjacencyList reference = null;

        // the graph number
        int testGraphNum = in.nextInt();
        while (testGraphNum != 0) {
            switch (testGraphNum) {
                case 1:
                    myGraph = initializeMyGraph("test-f3-1.txt", 300);
                    reference = initializeGeekGraph("test-f3-1.txt", 300);
                    testGraphNum = 0;
                    break;
                case 2:
                    myGraph = initializeMyGraph("test-f3-2.txt", 1000);
                    reference = initializeGeekGraph("test-f3-2.txt", 1000);
                    testGraphNum = 0;
                    break;
                case 3:
                    myGraph = initializeMyGraph("test-f3-3.txt", 5000);
                    reference = initializeGeekGraph("test-f3-3.txt", 5000);
                    testGraphNum = 0;
                    break;
                case 4:
                    myGraph = initializeMyGraph("test-f3-4.txt", 25000);
                    reference = initializeGeekGraph("test-f3-4.txt", 25000);
                    testGraphNum = 0;
                    break;
                case 5:
                    myGraph = initializeMyGraph("test-f3-5.txt", 50000);
                    reference = initializeGeekGraph("test-f3-5.txt", 50000);
                    testGraphNum = 0;
                    break;
                default:
                    System.out.println("There is no test file with number " + testGraphNum + ", please try again :)");
                    testGraphNum = in.nextInt();
            }
        }

        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("|       Please enter the function number you would like to play         |");
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("|         1. Return the item with the soonest deadline                  |");
        System.out.println("|         2. Find an item in the warehouse                              |");
        System.out.println("|         3. Find the shortest path to the destination place            |");
        System.out.println("|         4. Add a new item to the warehouse                            |");
        System.out.println("|         5. Add an edge to current myGraph                             |");
        System.out.println("|         6. Quit                                                       |");
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("| Notes:                                                                |");
        System.out.println("| Function 1, 2, and 3 are the main functionalities of this project.    |");
        System.out.println("| Function 4 and 5 are helper functions if you'd like to add some data  |");
        System.out.println("| to check if this program works correctly.                             |");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Please enter the function number:");

        // the function number
        int funcNum = in.nextInt();

        // empirical analyse fields
        long start;
        long end;
        long duration;

        while (funcNum != QUIT_OPTION) {
            switch (funcNum) {
                case 1: // Return the order with the soonest deadline
                    System.out.println("------------------------------ My Output --------------------------------");

                    start = System.nanoTime();
                    if (minHeap == null) {
                        minHeap = new MinHeap(warehouseItems.length);
                        for (Item item : warehouseItems) {
                            minHeap.insert(item);
                        }
                    }
                    end = System.nanoTime();
                    duration = end - start;
                    System.out.println("Min-heap Build Time: " + duration);

                    start = System.nanoTime();
                    Item myItem = minHeap.peek();
                    end = System.nanoTime();
                    duration = end - start;
                    System.out.println("Min-heap Peek Time: " + duration);
                    System.out.println("Result: " + myItem.toString());

                    System.out.println("------------------------- Java Library Output ----------------------------");
                    Item refItem = standardMinHeap.peek();
                    assert refItem != null;
                    System.out.println("Result: " + refItem.toString());

                    // reset the current state to the beginning
                    System.out.println("Please enter the next function number you would like to play with: (6 for quit)");
                    funcNum = in.nextInt();
                    break;
                case 2: // Find an item in the warehouse
                    System.out.println("Please enter the item ID in range [1," + warehouseItems.length + "):");
                    int itemID = in.nextInt();
                    System.out.println("------------------------------ My Output --------------------------------");

                    start = System.nanoTime();
                    Item result = redBlackTree.search(itemID);
                    end = System.nanoTime();
                    duration = end - start;
                    System.out.println("RBT Search Time: " + duration);
                    if (result == null) {
                        System.out.println("This item is not stored in our warehouse");
                    } else {
                        System.out.println("Result: " + result.toString());
                    }

                    System.out.println("------------------------- Java Library Output ----------------------------");
                    Item referenceItem = treeMap.get(itemID);
                    if (referenceItem == null) {
                        System.out.println("This item is not stored in our warehouse");
                    } else {
                        System.out.println("Result: " + referenceItem.toString());
                    }

                    // reset the current state to the beginning
                    System.out.println("Please enter the next function number you would like to play with: (6 for quit)");
                    funcNum = in.nextInt();
                    break;
                case 3: // Find the shortest path to the destination place
                    if (minHeap != null) {
                        System.out.println("Please enter the item ID in range [1," + minHeap.size + "):");
                    } else {
                        System.out.println("Please enter the item ID in range [1," + warehouseItems.length + "]:");
                    }
                    int findItemID = in.nextInt();

                    int destinationID = 0;
                    Item target = redBlackTree.search(findItemID);
                    if (target == null) {
                        System.out.println("This item is not stored in our warehouse");
                    } else {
                        System.out.println("------------------------------ My Output --------------------------------");
                        destinationID = target.getDestination();
                        System.out.println("Item Information" + target.toString());
                        start = System.nanoTime();
                        myGraph.dijkstra(myGraph.getVertex(0));
                        end = System.nanoTime();
                        duration = end - start;
                        System.out.println("Dijkstra's Algorithm Build Time: " + duration);
                        myGraph.printShortestPath(myGraph.getVertex(destinationID));
                    }

                    try {
                        System.out.println("------------------------ Online Library Output --------------------------");
                        reference.dijkstra(0, destinationID);
                        System.out.println(reference.reconstructPath(0, destinationID));
                    } catch (Exception e) {
                        System.out.println("There is no path to the destination place.");
                    }

                    // reset the current state to the beginning
                    System.out.println("Please enter the next function number you would like to play with: (6 for quit)");
                    funcNum = in.nextInt();
                    break;
                case 4: // Add a new item to the warehouse
                    System.out.println("Please enter a new ID: (larger than " + warehouseItems.length + ")");
                    int newItemID = in.nextInt();
                    System.out.println("Please enter its deadline: (format: yymmdd, e.g. 201123)");
                    int newItemDeadline = in.nextInt();
                    System.out.println("Please enter its destination place code: (an integer in [1,30])");
                    int newItemDestination = in.nextInt();
                    Item newItem = new Item(newItemID, newItemDeadline, newItemDestination, newItemID);
                    // regenerate the min heap for insertion
                    minHeap = new MinHeap(warehouseItems.length + 1);
                    for (Item item : warehouseItems) {
                        minHeap.insert(item);
                    }
                    minHeap.insert(newItem);
                    standardMinHeap.add(newItem);
                    // insert into the red black tree
                    redBlackTree.add(newItem);
                    treeMap.put(newItemID, newItem);
                    // add this new item to the current warehouse
                    System.out.println("Congrats! A new item(ID:" + newItemID
                            + ") will be stored in the location " + newItemID
                            + " in our warehouse, and will be sent to the destination place " + newItemDestination
                            + " before the deadline " + newItemDeadline);
                    // reset the current state to the beginning
                    System.out.println("Please enter the next function number you would like to play with: (6 for quit)");
                    funcNum = in.nextInt();
                    break;
                case 5: // Add an edge to current myGraph
                    System.out.println("Please enter the start vertex id:(range[0," + (myGraph.getVertexNumber() - 1) + "])");
                    int startVertex = in.nextInt();
                    System.out.println("Please enter the end vertex id:(range[0," + (myGraph.getVertexNumber() - 1) + "])");
                    int endVertex = in.nextInt();
                    System.out.println("Please enter the weight(positive integer) of this vertex:");
                    int newWeight = in.nextInt();
                    myGraph.addEdge(startVertex, endVertex, newWeight);
                    myGraph.addEdge(endVertex, startVertex, newWeight);
                    assert reference != null;
                    reference.addEdge(startVertex, endVertex, newWeight);
                    System.out.println("Congrats! A new edge start at " + startVertex
                            + ", end at " + endVertex
                            + " with a weight of " + newWeight
                            + " has been created successfully!");
                    // reset the current state to the beginning
                    System.out.println("Please enter the next function number you would like to play with: (6 for quit)");
                    funcNum = in.nextInt();
                    break;
                default: // Invalid input
                    System.out.println("Oops, this is an error message :( Could you check the function number and enter it again?");
                    funcNum = in.nextInt();
                    break;
            }
        }
        // end
        System.out.println("See you :)");
    }
}