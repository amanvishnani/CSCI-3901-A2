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

    public boolean isLessThan(Node n) {
        if(n != null) {
            return this.getData().compareTo(n.getData()) < 0;
        }
        return false;
    }

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
