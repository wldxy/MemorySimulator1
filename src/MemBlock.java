/**
 * Created by ocean on 16-5-19.
 */
public class MemBlock {
    int x, y, ID;
    Boolean isEmpty;
    MemBlock() { }

    MemBlock(int x, int y, Boolean isEmpty) {
        this.x = x;
        this.y = y;
        this.isEmpty = isEmpty;
    }

    public Integer getLength() {
        return y-x+1;
    }
}
