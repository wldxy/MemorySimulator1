import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by ocean on 16-5-19.
 */
public class AppSystem extends JPanel {
    private boolean isRandom;
    private int cur_app_len;
    private int mem_len, app_len;
    private int min_mem, rand_mem;
    private LinkedList<Code> codeList = new LinkedList<>();
    private LinkedList<App> appList = new LinkedList<>();
    private LinkedList<Code> wait = new LinkedList<>();
    private Boolean isRun;
    private int numRunApp;
    MemSystem memSystem;

    JTextArea codeText;
    JTextArea returnText;

    AppSystem(JTextArea codeText, JTextArea returnText) {
        super();
        isRun = false;
        this.codeText = codeText;
        this.returnText = returnText;
        isRandom = true;
    }

    public int getCur_app_len() {
        return cur_app_len;
    }

    public LinkedList<App> getAppList() {
        return appList;
    }

    public void init(int app_len, int mem_len, int type) {
        isRandom = true;
        cur_app_len = 1;
        this.mem_len = mem_len;
        this.app_len = app_len;
        isRun = true;
        numRunApp = 0;
        min_mem = mem_len / app_len * 2;
        rand_mem = (mem_len - min_mem) / 2;
        memSystem = new MemSystem(mem_len);
        memSystem.setType(type);
        codeText.setText(null);
        returnText.setText(null);
        this.repaint();
    }

    public void init(int app_len, int mem_len, int type, String file) {
        init(app_len, mem_len, type);
        isRandom = false;
    }

    public void next() {
        randNextOrder();
    }

    public void next(Code code) {
        if (code.type) {
            codeText.append("作业"+code.ID+"申请"+code.size+"K\n");
            App tApp = new App(cur_app_len, code.size);
            appList.add(tApp);
            numRunApp++;
            if (memSystem.allocateMem(tApp.ID, tApp.memSize)) {
                tApp.isGetMem = true;
                returnText.append("作业"+cur_app_len+"成功申请"+tApp.memSize+"K\n");
            }
            else {
                returnText.append("作业"+cur_app_len+"申请失败\n");
            }
            cur_app_len++;
        }
        else {
            for (int i = 0;i < appList.size();i++) {
                if (appList.get(i).ID == code.ID) {
                    if (appList.get(i).isDone)
                        returnText.append("作业"+appList.get(i)+"已完成\n");
                    else if (!appList.get(i).isGetMem) {
                        returnText.append("作业"+appList.get(i)+"尚未申请到内存\n");
                    }
                    else {
                        memSystem.releaseMem(appList.get(i).ID);
                        returnText.append("作业"+appList.get(i)+"释放内存\n");
                        numRunApp--;
                    }
                    break;
                }
            }
        }
        this.repaint();
    }

    private void randNextOrder() {
        if (!isRun) {
            return;
        }

        Random r = new Random();

        if ((r.nextBoolean() && cur_app_len <= app_len) || (numRunApp == 0 && cur_app_len < app_len)) {
            int size = min_mem+r.nextInt(rand_mem);
            codeText.append("作业"+cur_app_len+"申请"+size+"K\n");
            codeList.add(new Code(cur_app_len, size));
            App tApp = new App(cur_app_len, size);
            appList.addLast(tApp);
            numRunApp++;
            if (memSystem.allocateMem(tApp.ID, tApp.memSize)) {
                tApp.isGetMem = true;
                codeList.getLast().isFinish = true;
                returnText.append("作业"+cur_app_len+"成功申请"+size+"K\n");
            }
            else {
                returnText.append("作业"+cur_app_len+"申请失败\n");
            }
            cur_app_len++;
        }
        else {
            int rand = r.nextInt(numRunApp) + 1, id;
            //System.out.printf("%d  %d\n", numRunApp, rand);
            for (int i = 0; i < appList.size(); i++) {
                if (appList.get(i).isGetMem && !appList.get(i).isDone) {
                    rand--;
                }
                if (rand == 0) {
                    codeText.append("作业" + appList.get(i).ID + "释放内存\n");
                    memSystem.releaseMem(appList.get(i).ID);
                    appList.get(i).isDone = true;
                    returnText.append("作业" + appList.get(i).ID + "释放内存\n");
                    numRunApp--;
                    break;
                }
            }
            for (int i = 0; i < appList.size(); i++) {
                if (!appList.get(i).isGetMem) {
                    if (memSystem.allocateMem(appList.get(i).ID, appList.get(i).memSize)) {
                        returnText.append("等待队列中作业" + appList.get(i).ID + "获得" + appList.get(i).memSize + "K\n");
                        appList.get(i).isGetMem = true;
                    }
                }
            }
        }
        this.repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (isRun){
            LinkedList<MemBlock> mem = memSystem.getMemInfo();
            int length = 400, x = 20, y = 60;
            for (int i = 0;i < mem.size();i++) {
                if (mem.get(i).isEmpty) {
                    g.setColor(Color.blue);
                    int len = mem.get(i).getLength() * length / mem_len ;
                    //System.out.println(len);
                    g.fillRect(x, y, len, 40);
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(mem.get(i).getLength())+"K", x+len/2, y-20);
                    x = x + len + 5;
                }
                else {
                    g.setColor(Color.green);
                    int len = mem.get(i).getLength() * length / mem_len ;
                    //System.out.println(len);
                    g.fillRect(x, y, len, 50);
                    g.setColor(Color.BLACK);
                    g.drawString("作业"+String.valueOf(mem.get(i).ID), x+len/2, y+80);
                    x = x + len + 5;
                }
            }
        }
    }
}
