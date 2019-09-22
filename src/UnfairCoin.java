public class UnfairCoin implements Coin{

    private boolean[] sequence;
    private int i;

    public UnfairCoin(boolean[] sequence) {
        this.i = -1;
        this.sequence = sequence;
    }

    @Override
    public boolean flip() {
        int length = sequence.length;
        if(length == 0) {
            return false;
        }
        return sequence[(++i)%length];
    }
}
