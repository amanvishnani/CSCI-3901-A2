/**
 * Class ListHierarchy
 *
 * @author Aman Vishnani (aman.vishnani@dal.ca) [CSID: vishnani]
 */
public class ListHierarchy {
    private Node[] hierarchy;
    private Coin coin;
    private int levels = 0;

    /**
     * Add a string to the structure.
     * @param key the item to be inserted.
     * @return true if success
     */
    public boolean add( String key ) {
        if(key==null) return false;
        key = key.toLowerCase();
        return add(key, 0, null, null);
    }

    /**
     * A utility method to add key to the structure at a particular level.
     * @param key Item to be added.
     * @param level the level where it should be inserted.
     * @param startFrom acts as head of the list node to add element from.
     * @param linkFrom acts as the node from the bottom hierarchy.
     * @return true if insertion was successful.
     */
    private boolean add(String key, int level, Node startFrom, Node linkFrom) {
        handleOverflow(); // Handle Overflow
        Node item = new Node(key); // Create item to be added.

        //*********************** Initialize head and last up pointer for traversal ****************
        Node head = startFrom == null? hierarchy[level] : startFrom;
        Node lastUp = startFrom == null? (hierarchy[level] == null ? null : hierarchy[level].getUp()) : startFrom.getUp();
        //*********************** Setup UP and DOWN References. ************************************

        if(linkFrom != null) {
            item.setDown(linkFrom);
            linkFrom.setUp(item);
        }
        try {
            if(head == null) {
                // Insert at the head of the hierarchy.
                hierarchy[level] = item;
                levels++;
                // Flip the coin and add to higher hierarchy
                addToHierarchy(key,level+1, lastUp, item);
                return true;
            } else {
                Node pointer = head;
                lastUp = head.getUp();
                do {
                    if(
                            item.isLessThan(pointer) &&
                            pointer.hasPrevious() &&
                            item.isGreaterThan(pointer.getPrevious())
                    ) {
                        addBefore(pointer, item); // Add in between the linked list
                        addToHierarchy(key,level+1, lastUp, item); // Flip the coin and add to higher hierarchy
                        return true;
                    } else if( item.isGreaterThan(pointer) && pointer.hasNext() && item.isLessThan(pointer.getNext())) {
                        addAfter(pointer, item); // Add at the end of the linked list.
                        addToHierarchy(key,level+1, lastUp, item); // Flip the coin and add to higher hierarchy
                        return true;
                    } else if(item.isGreaterThan(pointer) && !pointer.hasNext()) {
                        addAfter(pointer, item);
                        addToHierarchy(key,level+1, lastUp, item); // Flip the coin and add to higher hierarchy
                        return true;
                    } else if( pointer.getPrevious() == null && item.isLessThan(pointer) ) {
                        addBefore(pointer,item); // Add at the head of the linked list
                        hierarchy[level] = item; // Flip the coin and add to higher hierarchy
                        addToHierarchy(key,level+1, lastUp, item);
                        return true;
                    } else if(pointer.getData().equals(item.getData())) {
                        // Skip if Entry Exists.
                        // Do not add it to higher hierarchy, it's fate was already decided while insertion.
                        return true;
                    }
                    pointer = pointer.getNext();

                    if(pointer != null  && pointer.hasUp()) {
                        lastUp = pointer.getUp(); // Keep tracking lastUp for jumping.
                    }

                } while(pointer!=null);
            }
        } catch (Exception e) {
//            System.out.println(e); // Ideally we should not print it.
            return false;
        }
        return true;
    }

    /**
     * Adds a node before a before an element.
     * @param node The reference node.
     * @param newNode The node item to be added.
     * @return true on success.
     */
    private boolean addBefore(Node node, Node newNode) {
        Node previous = node.getPrevious();

        if(previous!=null) {
            previous.setNext(newNode);
        }
        newNode.setNext(node);
        newNode.setPrevious(previous);
        node.setPrevious(newNode);
        return true;
    }

    /**
     * Adds a node before a After an element.
     * @param node The reference node.
     * @param newNode The node item to be added.
     * @return true on success.
     */
    private boolean addAfter(Node node, Node newNode) {
        Node next = node.getNext();
        newNode.setPrevious(node);
        newNode.setNext(next);
        node.setNext(newNode);
        if(next != null) {
            next.setPrevious(newNode);
        }
        return true;
    }

    /**
     * Utility method that adds a node to higher hierarchy if flip method returns 1.
     * @param key The data Item to be added.
     * @param level Next level of hierarchy.
     * @param startFrom Use this node as starting point instead of traversing all the elements.
     * @param linkFrom Node from which branch to next hierarchy is created.
     */
    private void addToHierarchy(String key, int level, Node startFrom, Node linkFrom) {
        boolean flag = this.coin.flip() == 1;
        if(flag) add(key, level, startFrom, linkFrom);
    }

    /**
     * Searches the string from top most hierarchy in the data structure.
     * @param key the input to search for.
     * @return true if found.
     */
    public boolean find( String key ) {
        if(key==null) return false;
        key = key.toLowerCase();
        Node searchItem = new Node(key);
        Node pointer = hierarchy[levels-1];
        do {
            if(
                    pointer.getData().equals(key) ||
                    (pointer.hasNext() && pointer.getNext().getData().equals(key)) ||
                    (pointer.hasPrevious() && pointer.getPrevious().getData().equals(key))
            ) {
                // Found
                return true;
            } else if(
                    searchItem.isGreaterThan(pointer) &&
                    pointer.hasNext() &&
                    searchItem.isLessThan(pointer.getNext())
            ) {
                // Search Down.
                pointer = pointer.getDown();
            } else if(
                    searchItem.isGreaterThan(pointer) &&
                    pointer.hasNext() &&
                    searchItem.isGreaterThan(pointer)
            ) {
                // Go to Next
                pointer = pointer.getNext();
            } else if(
                    !pointer.hasNext() &&
                    !pointer.hasPrevious()
            ) {
                // Search Down
                pointer = pointer.getDown();
            } else if(
                    searchItem.isLessThan(pointer) &&
                    pointer.hasPrevious()
            ) {
                // Go to Previous.
                pointer = pointer.getPrevious();
            } else if(
                    searchItem.isLessThan(pointer) &&
                    !pointer.hasPrevious()
            ) {
                // Search Down.
                pointer = pointer.getDown();
            } else if(searchItem.isGreaterThan(pointer) && !pointer.hasNext()) {
                // Search down.
                pointer = pointer.getDown();
            }

        } while (pointer!=null);

        return false;
    }

    /**
     * Constructor with structure of initial size of 2 Nodes to demonstrate dynamic scaling.
     */
    private ListHierarchy() {
        this.coin = new ArrayCoin(); // Default coin initialization.
        this.hierarchy = new Node[2];
    }

    public ListHierarchy(Coin coin) {
        this();
        this.coin = coin;
    }

    /**
     * A utility function to print the list in a pretty fashion.
     * It uses toString method of Node. Also provides some meaningful insights of the structure.
     */
    public void print() {
        System.out.printf("Total Number of Levels: %d\n", levels);
        System.out.printf("Max Levels (Increments Dynamically): %d\n", hierarchy.length);
        for (int i = hierarchy.length-1; i >= 0; i--) {
            Node pointer = hierarchy[i];
            if(pointer == null) continue;
            System.out.print("LEVEL "+i+":\t");
            while (pointer != null) {
                System.out.print(pointer.toString());
                if(pointer.hasNext()) {
                    System.out.print(" <----> ");
                }
                pointer = pointer.getNext();
            }
            System.out.println();
        }
    }

    /**
     * A utility function used to handle overflow of hierarchy array. It checks if the array is full, if so, doubles
     * the size of hierarchy array and copy contents of previous array.
     */
    private void handleOverflow() {
        if(levels == hierarchy.length) { // If Overflow

            Node[] newHierarchy = new Node[levels * 2]; // Double the size.
            System.arraycopy(hierarchy, 0, newHierarchy, 0, levels);
            hierarchy = newHierarchy; // Update reference with new array.
        }
    }
}
