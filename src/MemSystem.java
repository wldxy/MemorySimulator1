import java.util.LinkedList;

/**
 * Created by ocean on 16-5-18.
 */
public class MemSystem {
    private Integer length;
    private Integer type;
    //type=True use "first fit", type=False use "best fit"

    MemSystem(int length) {
        this.length = length;
        memory.add(new MemBlock(1, length, true));
        this.type = 0;
    }

    private LinkedList<MemBlock> memory = new LinkedList<>();
    
    public void setType(int x) {
        this.type = x;
        setEmpty();
    }

    public LinkedList<MemBlock> getMemInfo() {
        return memory;
    }

    public void setEmpty() {
        memory.clear();
        memory.add(new MemBlock(1, length, true));
    }

    public Boolean allocateMem(int id, int size) {
        //print();
        if (type == 1) {
            return firstFit(id, size);
        }
        else {
            return bestFit(id, size);
        }
    }

    public Boolean releaseMem(int id) {
        for (int i = 0;i < memory.size();i++) {
            MemBlock item = memory.get(i);
            if (!item.isEmpty && item.ID == id) {
                mergeBlock(i);
                item.isEmpty = true;
                item.ID = -1;
                return true;
            }
        }
        return false;
    }

    private Boolean firstFit(int id, int size) {
        for (int i = 0; i < memory.size(); i++) {
            MemBlock item = memory.get(i);
        	if (item.isEmpty && item.getLength() >= size) {
                if (item.x+size <= item.y) {
                    memory.add(i+1, new MemBlock(item.x+size, item.y, true));
                }
        		item.y = item.x+size-1;
                item.isEmpty = false;
                item.ID = id;
                return true;
        	}
        }
        return false;
    }

    private Boolean bestFit(int id, int size) {
        int best = -1, bestlen = length+1;
        //print();
        for (int i = 0;i < memory.size();i++) {
            MemBlock item = memory.get(i);
            if (item.isEmpty && item.getLength() >= size && item.getLength() < bestlen) {
                best = i;
                bestlen = item.getLength();
            }
        }
        //System.out.printf("%d  %d  === \n", best, bestlen);
        if (best != -1) {
            if (bestlen > size) {
                memory.add(best+1, new MemBlock(memory.get(best).x+size, memory.get(best).y, true));
            }
            memory.get(best).isEmpty = false;
            memory.get(best).ID = id;
            memory.get(best).y = memory.get(best).x+size-1;
            //print();
            return true;
        }
        else {
            return false;
        }
    }

    private void mergeBlock(int p) {
        if (p < memory.size()-1) {
        	if (memory.get(p+1).isEmpty) {
        		memory.get(p).y = memory.get(p+1).y;
        		memory.remove(p+1);
        	}
        }
        if (p > 0) {
            if (memory.get(p-1).isEmpty) {
                memory.get(p).x = memory.get(p-1).x;
                memory.remove(p-1);
            }
        }
    }

    public void print() {
        for (int i = 0;i < memory.size();i++) {
            MemBlock t = memory.get(i);
            //System.out.printf("%d  %d  %d  ", t.x, t.y, t.ID, t.isEmpty);
            //System.out.println(t.isEmpty);
        }
        //System.out.println("====================");
    }
//
//    public static void main(String[] args) {
//        MemSystem memSystem = new MemSystem(100);
//        memSystem.allocateMem(20, 1);
//        memSystem.allocateMem(30, 2);
//        memSystem.print();
//        memSystem.releaseMem(1);
//        memSystem.print();
//        memSystem.allocateMem(10, 3);
//        memSystem.print();
//        memSystem.releaseMem(2);
//        memSystem.print();
//    }
}
