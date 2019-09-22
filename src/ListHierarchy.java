public class ListHierarchy {
    private Node[] hierarchy;
    private ICoin coin;
    private int levels = 0;

    public boolean add( String key ) {
        return add(key, 0, null, null);
    }
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

    boolean find( String key ) {
        Node dummy = new Node(key);
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
                    dummy.isGreaterThan(pointer) &&
                    pointer.hasNext() &&
                    dummy.isLessThan(pointer.getNext())
            ) {
                // Search Down.
                pointer = pointer.getDown();
            } else if(
                    dummy.isGreaterThan(pointer) &&
                    pointer.hasNext() &&
                    dummy.isGreaterThan(pointer)
            ) {
                pointer = pointer.getNext();
            } else if(
                    !pointer.hasNext() &&
                    !pointer.hasPrevious()
            ) {
                pointer = pointer.getDown();
            } else if(
                    dummy.isLessThan(pointer) &&
                    pointer.hasPrevious()
            ) {
                pointer = pointer.getPrevious();
            } else if(
                    dummy.isLessThan(pointer) &&
                    !pointer.hasPrevious()
            ) {
                pointer = pointer.getDown();
            } else if(dummy.isGreaterThan(pointer) && !pointer.hasNext()) {
                pointer = pointer.getDown();
            }

        } while (pointer!=null);

        return false;
    }

    public ListHierarchy() {
        this.hierarchy = new Node[1];
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
                    System.out.print("---->");
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
