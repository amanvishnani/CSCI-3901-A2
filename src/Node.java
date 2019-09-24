/**
 * Node class representing a single node in the ListHierarchy.
 * Mimics Doubly linked list node.
 * It has five properties of which one is data of type string and four links to other nodes.
 * Up and Down links are references to top and bottom nodes in the hierarchy and are nullable values.
 * Previous and next links are references to rear and front nodes in the linked list.
 *
 * @author Aman Vishnani (aman.vishnani@dal.ca) [CSID: vishnani]
 */
public class Node {
    private String data;
    private Node next;
    private Node previous;
    private Node up;
    private Node down;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    /**
     * Compares current node string with provide node string.
     * @param n the node to be compared with
     * @return true if current node is smaller than the provided node.
     */
    public boolean isLessThan(Node n) {
        if(n != null) {
            return this.getData().compareTo(n.getData()) < 0;
        }
        return false;
    }

    /**
     * Compares current node string with provide node string.
     * @param n the node to be compared with
     * @return true if current node is greater than the provided node.
     */
    public boolean isGreaterThan(Node n) {
        if(n != null) {
            return  this.getData().compareTo(n.getData()) > 0;
        }
        return false;
    }

    public  Node (String key) {
        setData(key);
    }

    public Node getUp() {
        return up;
    }

    public void setUp(Node up) {
        this.up = up;
    }

    public Node getDown() {
        return down;
    }

    public void setDown(Node down) {
        this.down = down;
    }

    /**
     * Utility method to check if node has a reference to next element.
     * @return true if next element exists.
     */
    public boolean hasNext() {
        return this.getNext() != null;
    }

    /**
     * Utility method to check if node has a reference to previous element.
     * @return true if previous element exists.
     */
    public boolean hasPrevious() {
        return this.getPrevious() != null;
    }

    /**
     * Utility method to check if node has a reference to 'up' element.
     * @return true if 'up' exists.
     */
    public boolean hasUp() {
        return this.getUp() != null;
    }
    /**
     * toString Override for pretty printing.
     * Format: data[↑↓]
     * ↑ - If node is linked with a top node.
     * ↓ - If node is linked with a bottom node.
     * @return the pretty string.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(this.data);
        builder.append("[");
        if(getUp()!=null) {
            builder.append("↑");
        }
        if(getDown()!=null){
            builder.append("↓");
        }
        builder.append("]");
        return builder.toString();
    }
}
