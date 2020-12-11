/**
 * Item represents each package in the current warehouse.
 */
public class Item {

    // attributes
    private int id;
    private int deadline;
    private int destination;
    private int location;

    // basic constructor
    public Item(int id, int deadline, int destination, int location) {
        this.id = id;
        this.deadline = deadline;
        this.destination = destination;
        this.location = location;
    }

    // simple getter methods
    public int getId() {
        return id;
    }

    public int getDeadline() {
        return deadline;
    }

    public int getDestination() {
        return destination;
    }

    // for better print out
    @Override
    public String toString() {
        return "Item(ID:" + id + ") deadline:" + deadline + " destination: " + destination + " location: " + location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                deadline == item.deadline &&
                destination == item.destination &&
                location == item.location;
    }

    public boolean more(Item i) {
        return this.getId() > i.getId();
    }

    public boolean less(Item i) {
        return this.getId() < i.getId();
    }
}
