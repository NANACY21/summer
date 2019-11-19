package schoolcardms.vo;

/**
 * 等差数列
 */
public class SequenceOfNumber {

    private int a1;//首项
    private int d;//公差
    private int numberOfItems;//项数
    private int[] data;//值

    public SequenceOfNumber(int a1, int d, int numberOfItems) {
        this.a1 = a1;
        this.d = d;
        this.numberOfItems = numberOfItems;
        this.data = new int[this.numberOfItems];
        int temp = this.a1;
        for (int i = 0; i < this.numberOfItems; i++) {
            this.data[i] = temp;
            temp = temp + this.d;
        }
    }

    public int[] getData() {
        return data;
    }
}
