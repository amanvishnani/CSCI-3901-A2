public class Main {

    public static void main(String[] args) {
        boolean[] debugSequence = {false, true, true, false, true, false, false, true, true, false, false, false, true, false};

//        Coin coin = new UnfairCoin(debugSequence);
        Coin coin = new FairCoin();

        ListHierarchy lh = new ListHierarchy(coin);
        lh.add("apple");
        lh.add("date");
        lh.add("grape");
        lh.add("lettuce");
        lh.add("orange");
        lh.add("pepper");
        lh.add("salt");
        lh.add("tea");
        lh.print();
    }
}
