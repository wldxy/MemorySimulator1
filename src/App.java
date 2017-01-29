/**
 * Created by ocean on 16-5-19.
 */
public class App {
    int ID;
    int memSize;
    boolean isGetMem;
    boolean isDone;
    //isGetMen=true App get the memory to run
    App(int ID, int memSize) {
        this.ID = ID;
        this.memSize = memSize;
        isGetMem = false;
        isDone = false;
    }
}
