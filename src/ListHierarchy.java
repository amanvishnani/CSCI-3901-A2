public class ListHierarchy {
    private Node[] list;
    private Coin coin;

    boolean add( String key ) {
        Node item = new Node(key);
        Node head = list[0];
        try {
            if(head == null) {
                list[0] = item;
                return true;
            } else {
                Node pointer = head;
                do {
                    if( pointer.getPrevious()!=null && item.isLessThan(pointer) && item.isGreaterThan(pointer.getPrevious()) ) {
                        addBefore(pointer, item); // Add in between the list
                        return true;
                    } else if( pointer.getNext()==null && item.isGreaterThan(pointer) ) {
                        addAfter(pointer, item); // Add at the end of the list.
                        return true;
                    } else if( pointer.getPrevious() == null && item.isLessThan(pointer) ) {
                        addBefore(pointer,item); // Add at the head of the list
                        list[0] = item;
                        return true;
                    }
                    pointer = pointer.getNext();
                } while(pointer!=null);
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return  false;
    }

    boolean addBefore(Node node, Node newNode) {
        Node previous = node.getPrevious();

        if(previous!=null) {
            previous.setNext(newNode);
        }
        newNode.setNext(node);
        newNode.setPrevious(previous);
        node.setPrevious(newNode);
        return true;
    }

    boolean addAfter(Node node, Node newNode) {
        Node next = node.getNext();
        newNode.setPrevious(node);
        newNode.setNext(next);
        node.setNext(newNode);
        if(next != null) {
            next.setPrevious(newNode);
        }
        return true;
    }

    boolean find( String key ) {
        return false;
    }

    public ListHierarchy() {
        this.list = new Node[100];
    }

    public ListHierarchy(Coin coin) {
        this();
    }

    public void print() {
        Node pointer = list[0];
        while (pointer != null) {
            System.out.print(pointer.getData());
            if(pointer.getNext() != null) {
                System.out.print("---->");
            }
            pointer = pointer.getNext();
        }
        System.out.println();
    }
}
