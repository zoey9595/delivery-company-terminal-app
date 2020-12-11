import java.io.IOException;
import java.util.Random;

public class RedBlackTree {

    // tree root node
    private Node root;

    // Enum color class
    public enum Color {
        RED, BLACK
    }

    /**
     * Tree node class
     */
    public class Node {
        Color color;
        Item item;
        Node parent;
        Node left, right;

        // Constructors
        public Node() {
            this.item = null;
            this.color = Color.BLACK;
        }

        public Node(Item item) {
            this.color = Color.RED;
            this.item = item;
            this.parent = null;
            this.left = new Node();
            this.right = new Node();
            this.left.parent = this;
            this.right.parent = this;
        }

        /**
         * If node color is red
         *
         * @return true: red
         */
        private boolean isRed() {
            return this.color == Color.RED;
        }
    }

    // Constructors
    public RedBlackTree(Item[] items) {
        this.root = null;
        for (Item i : items) {
            this.add(i);
        }
    }

    /**
     * Basic insert a node into a tree, without recolor
     *
     * @param root pivot node
     * @param node given node
     */
    private void recursiveInsert(Node root, Node node) {
        if (root.item.more(node.item)) {
            if (root.left.item == null) {
                root.left = node;
                node.parent = root;
            } else {
                recursiveInsert(root.left, node);
            }
        } else if (root.item.less(node.item)) {
            if (root.right.item == null) {
                root.right = node;
                node.parent = root;
            } else {
                recursiveInsert(root.right, node);
            }
        }
    }

    /**
     * Insert a node into the red black tree and adjust the color
     *
     * @param node pivot node
     */
    private void insert(Node node) {
        if (this.root == null) {
            this.root = node;
        } else {
            recursiveInsert(root, node);
        }
        while (!node.item.equals(root.item) && node.parent.isRed()) {
            boolean isLeft = node.parent == node.parent.parent.left;
            Node uncle = isLeft ? node.parent.parent.right : node.parent.parent.left;
            if (uncle.isRed()) {
                // uncle node is red, no rotate, just recolor it
                node.parent.color = Color.BLACK;
                uncle.color = Color.BLACK;
                node.parent.parent.color = Color.RED;
                node = node.parent.parent;
            } else {
                // uncle node is black
                if (node.item.equals(isLeft ? node.parent.right.item : node.parent.left.item)) {
                    node = node.parent;
                    if (isLeft) {
                        // uncle is the left node of its parent
                        // node is the right node of its parent
                        if (node.item.equals(root.item)) root = node.right;
                        leftRotate(node);
                    } else {
                        // uncle is the right node of its parent
                        // node is the left node of its parent
                        if (node.item.equals(root.item)) root = node.left;
                        rightRotate(node);
                    }
                }
                node.parent.color = Color.BLACK;
                node.parent.parent.color = Color.RED;

                if (isLeft) {
                    if (node.parent.parent.item.equals(root.item)) root = node.parent.parent.left;
                    rightRotate(node.parent.parent);
                } else {
                    if (node.parent.parent.item.equals(root.item)) root = node.parent.parent.right;
                    leftRotate(node.parent.parent);
                }
            }
        }
        this.root.color = Color.BLACK;
    }

    /**
     * Add an item to the tree
     *
     * @param item given item
     */
    public void add(Item item) {
        insert(new Node(item));
    }

    /**
     * Left rotation
     *
     * @param node pivot node
     */
    private void leftRotate(Node node) {
        if (node.parent != null) {
            if (node.parent.left == node) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
            node.parent = node.right;
            node.right = node.parent.left;
            node.right.parent = node;
            node.parent.left = node;
        }
    }

    /**
     * Right rotation
     *
     * @param node pivot node
     */
    private void rightRotate(Node node) {
        if (node.parent != null) {
            if (node.parent.right == node) {
                node.parent.right = node.left;
            } else {
                node.parent.left = node.left;
            }
            node.left.parent = node.parent;
            node.parent = node.left;
            node.left = node.parent.right;
            node.left.parent = node;
            node.parent.right = node;
        }
    }

    /**
     * Find a node with given itemID in the tree
     *
     * @param node   start node
     * @param itemID given item ID
     * @return the result node
     */
    private Node find(Node node, int itemID) {
        if (node.item == null) return null;
        if (itemID == node.item.getId()) {
            return node;
        } else if (itemID < node.item.getId()) {
            return find(node.left, itemID);
        } else {
            return find(node.right, itemID);
        }
    }

    /**
     * Search for a given ID item
     *
     * @param itemID given item ID
     * @return item information
     */
    public Item search(int itemID) {
        Node node = find(root, itemID);
        if (node != null) {
            return node.item;
        }
        return null;
    }


    public static void main(String[] args) throws IOException {
        // for empirical analysis
        Random random = new Random();
        int dataSize = 26;
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 6; i < dataSize; i++) {
            long start, end, duration = 0;
            int size = (int) Math.pow(2, i);
            int itemID;
            Item[] warehouseItems = GUI.generateItems("warehouseData" + i + ".txt");
            RedBlackTree redBlackTree = new RedBlackTree(warehouseItems);

            for (int j = 0; j < 10000; j++) {
                itemID = random.nextInt(size);
                start = System.nanoTime();
                redBlackTree.search(itemID);
                end = System.nanoTime();
                duration = duration + end - start;
            }
            stringBuilder.append(duration / 10000).append(", ");
            System.out.println(i);
        }
        System.out.println(stringBuilder.toString());
    }
}
