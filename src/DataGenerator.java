import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;


/**
 * Generate test data sets for the warehouse and graph
 * If you would like to generate some random data, just modify the following final constants.
 */
public class DataGenerator {

    private static final int DATA_SIZE = 26;
    private static final int GRAPH_SIZE = 16;
    private static final int DEADLINE_RANGE = 100;
    private static final int DESTINATION_RANGE = 30;
    private static final int WEIGHT_MIN = 2;
    private static final int WEIGHT_MAX = 50;
    private static final int DEFAULT = 100;

    public static void warehouseData(int size) {
        try {
            File testData = new File("warehouseData" + size + ".txt");

            if (testData.createNewFile()) {
                System.out.println("File created: " + testData.getName());
            }

            FileWriter fileWriter = new FileWriter("warehouseData" + size + ".txt");

            Random random = new Random();
            LocalDate baseDate = LocalDate.now();
            int randomDays;
            LocalDate randomDate;
            String dateAsString;

            int tempDeadline;
            int tempDestination;

            int upper = (int) Math.pow(2, size);
            for (int i = 0; i < upper; i++) {
                randomDays = (int) (DEADLINE_RANGE * Math.random());
                randomDate = baseDate.plusDays(randomDays);
                dateAsString = DateTimeFormatter.ofPattern("yyMMdd").format(randomDate);
                tempDeadline = Integer.parseInt(dateAsString);
                tempDestination = random.nextInt(DESTINATION_RANGE) + 1;
                fileWriter.write(i + "," + tempDeadline + "," + tempDestination + "," + i + "\n");
            }

            fileWriter.close();
            System.out.println("Successfully wrote " + testData.getName());

        } catch (IOException e) {
            System.out.println("Failed to create warehouse data file.");
            e.printStackTrace();
        }
    }

    public static void graphData(int size) {
        try {
            File testData = new File("graphData" + size + ".txt");

            if (testData.createNewFile()) {
                System.out.println("File created: " + testData.getName());
            }

            FileWriter fileWriter = new FileWriter("graphData" + size + ".txt");

            Random random = new Random();

            int vertexNumber = DEFAULT * size;
            int start;
            int end;
            int weight;

            // generate vertexNumber edges in the graph
            for (int i = 0; i < vertexNumber; i++) {
                start = i;
                end = random.nextInt(vertexNumber);
                if (start == end) {
                    end = vertexNumber - 1;
                }
                weight = random.nextInt(WEIGHT_MAX) + WEIGHT_MIN;
                fileWriter.write(start + "," + end + "," + weight + "\n");
                fileWriter.write(end + "," + start + "," + weight + "\n");
            }

            for (int i = 0; i < 2 * vertexNumber; i++) {
                start = random.nextInt(vertexNumber);
                end = random.nextInt(vertexNumber);
                if (start == end) {
                    end = vertexNumber - 1;
                }
                weight = random.nextInt(WEIGHT_MAX) + WEIGHT_MIN;
                fileWriter.write(start + "," + end + "," + weight + "\n");
                fileWriter.write(end + "," + start + "," + weight + "\n");
            }

            fileWriter.close();
            System.out.println("Successfully wrote " + testData.getName());

        } catch (IOException e) {
            System.out.println("Failed to create graph data file.");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//        for (int i = 6; i <= DATA_SIZE; i++) {
//            warehouseData(i);
//        }
//        for (int i = 3; i <= 500; i++) {
//            graphData(i);
//        }
        warehouseData(23);
    }
}