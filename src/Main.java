public class Main {

    public static void main(String[] args) {
        boolean[] debugSequence = {false, true, true, false, true, false, false, true, true, false, false, false, true, false};
        String[] testStrings = {"apple", "date", "grape", "lettuce", "orange", "pepper", "salt", "tea"};
//        ICoin coin = new UnfairCoin(debugSequence);
        ICoin coin = new FairCoin();

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
