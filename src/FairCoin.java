import java.util.Random;

public class FairCoin implements ICoin {
    Random random;
    public FairCoin() {
        random = new Random();
    }

    public boolean flip() {
        return random.nextBoolean();
    }
}
