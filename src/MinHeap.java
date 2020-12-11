import java.io.IOException;

/**
 * MinHeap class implement data structure min heap
 * <p>
 * Part of code is developed based on the book Algorithms written by Robert Sedgewick
 */
public class MinHeap {

    public int size;
    private Item[] items;

    // Constructor
    public MinHeap(int maxN) {
        this.size = 0;
        this.items = new Item[maxN + 1];
    }

    /**
     * Insert a new item into the current min heap
     *
     * @param newItem a new item created by users
     */
    public void insert(Item newItem) {
        items[++size] = newItem;
        swim(size);
    }

    /**
     * Shift the kth item up
     *
     * @param k index
     */
    private void swim(int k) {
        while (k > 1 && more(k / 2, k)) {
            swap(k, k / 2);
            k = k / 2;
        }
    }

    /**
     * minHeapify at index k
     *
     * @param k index
     */
    private void sink(int k) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && more(j, j + 1)) j++;
            if (!more(k, j)) break;
            swap(k, j);
            k = j;
        }
    }

    /**
     * Extract the minimum element in heap
     *
     * @return the item with soonest deadline
     */
    public Item extractMin() {
        if (size == 0) throw new IllegalStateException("Heap is empty.");
        Item min = items[1];
        swap(1, size--);
        items[size + 1] = null;
        sink(1);
        return min;
    }

    /**
     * Return the minimum item
     *
     * @return the item with soonest deadline
     */
    public Item peek() {
        if (size == 0) throw new IllegalStateException("Heap is empty.");
        return items[1];
    }


    /**
     * Compare whether the item at index i has a longer deadline than item at index j
     *
     * @param i item index
     * @param j item index
     * @return whether i's deadline is sooner than j
     */
    private boolean more(int i, int j) {
        return items[i].getDeadline() > items[j].getDeadline();
    }


    /**
     * Swap two items in the array
     *
     * @param i index
     * @param j index
     */
    private void swap(int i, int j) {
        Item temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    /**
     * Build the min-heap
     */
    public void build() {
        for (int i = size / 2; i >= 1; i--) {
            sink(i);
        }
    }


    public static void main(String[] args) throws IOException {
        // for empirical analysis
        int dataSize = 26;
        StringBuilder stringBuilder = new StringBuilder();
        Item[] warehouseItems;
        MinHeap minHeap;
        long start, end, duration;
        Item temp;
        for (int i = 6; i <= dataSize; i++) {
            duration = 0;
            warehouseItems = GUI.generateItems("warehouseData" + i + ".txt");
            minHeap = new MinHeap(warehouseItems.length);
            for (Item item : warehouseItems) {
                minHeap.insert(item);
            }

            for (int j = 0; j < 10000; j++) {
                temp = minHeap.extractMin();
                start = System.nanoTime();
                minHeap.insert(temp);
                end = System.nanoTime();
                duration = duration + end - start;
            }
            stringBuilder.append(duration / 10000).append(", ");
        }
        System.out.println(stringBuilder.toString().trim());
    }
}