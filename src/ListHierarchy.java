public class ListHierarchy {
    private Node[] hierarchy;
    private Coin coin;

    public boolean add( String key ) {
        return add(key, 0, null, null);
    }
    private boolean add(String key, int level, Node startFrom, Node linkFrom) {
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
                addToHierarchy(key,level+1, lastUp, item);
                return true;
            } else {
                Node pointer = head;
                lastUp = head.getUp();
                do {
                    if( pointer.getPrevious()!=null && item.isLessThan(pointer) && item.isGreaterThan(pointer.getPrevious()) ) {
                        addBefore(pointer, item); // Add in between the hierarchy
                        addToHierarchy(key,level+1, lastUp, item);
                        return true;
                    } else if( pointer.getNext()==null && item.isGreaterThan(pointer) ) {
                        addAfter(pointer, item); // Add at the end of the hierarchy.
                        addToHierarchy(key,level+1, lastUp, item);
                        return true;
                    } else if( pointer.getPrevious() == null && item.isLessThan(pointer) ) {
                        addBefore(pointer,item); // Add at the head of the hierarchy
                        hierarchy[0] = item;
                        addToHierarchy(key,level+1, lastUp, item);
                        return true;
                    } else if(pointer.getData().equals(item)) {
                        return true;
                    }
                    pointer = pointer.getNext();

                    if(pointer.getUp() != null) {
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
        return false;
    }

    public ListHierarchy() {
        this.hierarchy = new Node[100];
    }

    public ListHierarchy(Coin coin) {
        this();
        this.coin = coin;
    }

    public void print() {
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
}
