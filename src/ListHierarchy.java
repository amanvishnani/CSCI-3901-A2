public class ListHierarchy {
    private Node[] hierarchy;
    private ICoin coin;
    private int levels = 0;

    /**
     * Add a string to the structure.
     * @param key the item to be inserted.
     * @return true if success
     */
    public boolean add( String key ) {
        return add(key, 0, null, null);
    }

    /**
     * A utility method to add key to the structure at a particular level.
     * @param key Item to be added.
     * @param level the level where it should be inserted.
     * @param startFrom acts as head of the list node to add element from.
     * @param linkFrom acts as the node from the bottom hierarchy.
     * @return
     */
    private boolean add(String key, int level, Node startFrom, Node linkFrom) {
        handleOverflow();
        Node item = new Node(key);
        Node head = startFrom == null? hierarchy[level] : startFrom;
        Node lastUp = startFrom == null? (hierarchy[level] == null ? null : hierarchy[level].getUp()) : startFrom.getUp();
        if(linkFrom != null) {
            item.setDown(linkFrom);
            linkFrom.setUp(item);
        }
        try {
            if(head == null) {
                hierarchy[level] = item;
                levels++;
                addToHierarchy(key,level+1, lastUp, item);
                return true;
            } else {
                Node pointer = head;
                lastUp = head.getUp();
                do {
                    if( pointer.getPrevious()!=null && item.isLessThan(pointer) && item.isGreaterThan(pointer.getPrevious()) ) {
                        addBefore(pointer, item); // Add in between the linked list
                        addToHierarchy(key,level+1, lastUp, item);
                        return true;
                    } else if( pointer.getNext()==null && item.isGreaterThan(pointer) ) {
                        addAfter(pointer, item); // Add at the end of the linked list.
                        addToHierarchy(key,level+1, lastUp, item);
                        return true;
                    } else if( pointer.getPrevious() == null && item.isLessThan(pointer) ) {
                        addBefore(pointer,item); // Add at the head of the linked list
                        hierarchy[level] = item;
                        addToHierarchy(key,level+1, lastUp, item);
                        return true;
                    } else if(pointer.getData().equals(item)) {
                        return true;
                    }
                    pointer = pointer.getNext();

                    if(pointer != null  && pointer.getUp() != null) {
                        lastUp = pointer.getUp();
                    }

                } while(pointer!=null);
            }
        } catch (Exception e) {
            System.out.println(e);
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

    private void addToHierarchy(String key, int level, Node startFrom, Node linkFrom) {
        boolean flag = this.coin.flip();
        if(flag) add(key, level, startFrom, linkFrom);
    }

    public boolean find( String key ) {
        Node searchItem = new Node(key);
        Node pointer = hierarchy[levels-1];
        do {
            if(
                    pointer.getData().equals(key) ||
                    (pointer.hasNext() && pointer.getNext().equals(key)) ||
                    (pointer.hasPrevious() && pointer.getPrevious().equals(key))
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
                pointer = pointer.getNext();
            } else if(
                    !pointer.hasNext() &&
                    !pointer.hasPrevious()
            ) {
                pointer = pointer.getDown();
            } else if(
                    searchItem.isLessThan(pointer) &&
                    pointer.hasPrevious()
            ) {
                pointer = pointer.getPrevious();
            } else if(
                    searchItem.isLessThan(pointer) &&
                    !pointer.hasPrevious()
            ) {
                pointer = pointer.getDown();
            } else if(searchItem.isGreaterThan(pointer) && !pointer.hasNext()) {
                pointer = pointer.getDown();
            }

        } while (pointer!=null);

        return false;
    }

    public ListHierarchy() {
        this.hierarchy = new Node[2];
    }

    public ListHierarchy(ICoin coin) {
        this();
        this.coin = coin;
    }

    public void print() {
        System.out.printf("Total Number of Levels: %d\n", levels);
        System.out.printf("Max Levels (Increments Dynamically): %d\n", hierarchy.length);
        for (int i = hierarchy.length-1; i >= 0; i--) {
            Node pointer = hierarchy[i];
            if(pointer == null) continue;
            System.out.print("LEVEL "+i+":\t");
            while (pointer != null) {
                System.out.printf(pointer.toString());
                if(pointer.getNext() != null) {
                    System.out.print("<---->");
                }
                pointer = pointer.getNext();
            }
            System.out.println();
        }
    }

    private void handleOverflow() {
        if(levels == hierarchy.length) { // If Overflow

            Node[] newHierarchy = new Node[levels * 2]; // Double the size.

            for (int i = 0; i < hierarchy.length; i++) {
                newHierarchy[i] = hierarchy[i];
            }
            hierarchy = newHierarchy;
        }
    }
}
