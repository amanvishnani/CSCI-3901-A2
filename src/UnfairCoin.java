public class UnfairCoin implements Coin{

    private boolean[] sequence;
    private int i = -1;

    public UnfairCoin(boolean[] sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean flip() {
        int length = sequence.length;
        if(length == 0) {
            return false;
        }
        return sequence[(i+1)%length];
    }
}
