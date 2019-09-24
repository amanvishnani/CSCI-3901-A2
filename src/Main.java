public class Main {

    public static void main(String[] args) {
        int[] debugSequence = {0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0};
        String[] testStrings = {"apple", "date", "grape", "lettuce", "orange", "pepper", "salt", "tea"};
//        Coin coin = new ArrayCoin();
        Coin coin = new RandomCoin();

        ListHierarchy lh = new ListHierarchy(coin);
        for (String str :
                testStrings) {
            lh.add(str);
        }
        lh.print();

        for (String str :
                testStrings) {
            System.out.printf("%s -> %s\n",str, lh.find(str));
        }
        System.out.printf("%s -> %s\n","Aman", lh.find("Aman"));
    }
}
