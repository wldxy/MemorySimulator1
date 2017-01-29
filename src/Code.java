/**
 * Created by ocean on 16-5-19.
 */
public class Code {
    Boolean type;
    //type=true Code do the allocate, type=false Code do the release
    int size;
    //if Code do the allocate, size means the size of memory
    int ID;
    boolean isFinish;

    Code(int id, int size) {
        type = true;
        isFinish = false;
        this.ID = id;
        this.size = size;
    }

    Code(int id) {
        type = false;
        this.ID = id;
        isFinish = false;
    }
}
