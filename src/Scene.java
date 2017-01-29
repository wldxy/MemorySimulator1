import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

/**
 * Created by ocean on 16-5-26.
 */
public class Scene implements ActionListener {
    JFrame frame;
    JPanel control, codeControl;
    JTextField memSize, appSize, inputMen;
    JComboBox algo, codeChoose, appChoose;
    AppSystem system;
    JRadioButton jrb1, jrb2;

    Scene() {
        frame = new JFrame("内存动态分配");

        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container cont = frame.getContentPane();

        JButton initButton = new JButton("初始化");
        JButton nextButton = new JButton("下一条指令");
        initButton.setActionCommand("init");
        initButton.addActionListener(this);
        nextButton.setActionCommand("next");
        nextButton.addActionListener(this);
        frame.add(initButton);
        frame.add(nextButton);

        JTextArea jta1 = new JTextArea();
        JScrollPane scr1 = new JScrollPane(jta1);
        JTextArea jta2 = new JTextArea();
        JScrollPane scr2 = new JScrollPane(jta2);
        JTextArea jta3 = new JTextArea();
        JScrollPane scr3 = new JScrollPane(jta3);
        jta1.setLineWrap(true);
        jta2.setLineWrap(true);
        jta3.setLineWrap(true);
        scr1.setBounds(350, 150, 200, 300);
        scr2.setBounds(580, 150, 200, 300);

        JLabel jl1 = new JLabel("内存分配指令");
        JLabel jl2 = new JLabel("执行指令日志");
        frame.add(jl1);
        frame.add(jl2);
        jl1.setBounds(350, 130, 100, 20);
        jl2.setBounds(580, 130, 100, 20);

        frame.add(scr1);
        frame.add(scr2);

        system = new AppSystem(jta2, jta1);
        frame.add(system);
        system.setBorder(BorderFactory.createTitledBorder("内存空间"));
        system.repaint();

        jrb1 = new JRadioButton("随机指令");
        jrb2 = new JRadioButton("输入指令");
        jrb1.setActionCommand("random");
        jrb2.setActionCommand("input");
        jrb1.addActionListener(this);
        jrb2.addActionListener(this);
        JPanel pan = new JPanel();
        pan.setBorder(BorderFactory.createTitledBorder("指令")); // 设置一个边框的显示条
        pan.setLayout(new GridLayout(3, 1));
        jrb1.setSelected(true);
        pan.add(jrb1);
        pan.add(jrb2);
        //pan.add(jrb3);
        cont.add(pan);

        initControl();
        cont.add(control);

        initCodeControl();
        cont.add(codeControl);
        codeControl.setEnabled(false);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent obj) {
                System.exit(1);
            }
        });

        frame.setLayout(null);
        initButton.setBounds(350, 50, 100, 40);
        nextButton.setBounds(500, 50, 100, 40);
        //jta1.setBounds(15, 80, 150, 105);
        //jta2.setBounds(200,80,170,87) ;

        control.setBounds(50, 50, 250, 200);
        pan.setBounds(50, 300, 250, 150);
        codeControl.setBounds(50, 500, 250, 200);

        system.setBounds(350, 500, 500, 200);
        system.init(15, 720, 0);

        frame.setVisible(true);
    }

    private void initControl() {
        control = new JPanel();

        JLabel label1 = new JLabel("内存大小");
        memSize = new JTextField("720", 10);
        memSize.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int key = e.getKeyChar();
                if (key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) {

                }
                else {
                    e.consume();
                }
            }
        });

        JLabel label3 = new JLabel("随机模拟作业数");
        appSize = new JTextField("15", 10);
        memSize.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int key = e.getKeyChar();
                if (key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) {

                }
                else {
                    e.consume();
                }
            }
        });

        JLabel label2 = new JLabel("算法选择");
        algo = new JComboBox();
        algo.addItem("最佳适应算法");
        algo.addItem("首先适应算法");
        System.out.println(algo.getSelectedIndex());

        control.setBorder(BorderFactory.createTitledBorder("参数设置"));
        control.setLayout(new GridLayout(3, 2, 50, 50));
        control.add(label1);
        control.add(memSize);
        control.add(label2);
        control.add(algo);
        control.add(label3);
        control.add(appSize);

    }

    public void initCodeControl() {
        codeControl = new JPanel();

        JLabel label4 = new JLabel("指令类型");

        codeChoose = new JComboBox();
        codeChoose.addItem("申请内存");
        codeChoose.addItem("释放内存");
        codeControl.add(codeChoose);

        JLabel label5 = new JLabel("释放作业选择");
        appChoose = new JComboBox();

        JLabel label6 = new JLabel("申请内存大小");
        inputMen = new JTextField(20);
        inputMen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int key = e.getKeyChar();
                if (key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) {

                }
                else {
                    e.consume();
                }
            }
        });


        codeControl.setBorder(BorderFactory.createTitledBorder("指令输入"));
        codeControl.setLayout(new GridLayout(3, 2, 50, 50));
        codeControl.add(label4);
        codeControl.add(codeChoose);
        codeControl.add(label6);
        codeControl.add(inputMen);
        codeControl.add(label5);
        codeControl.add(appChoose);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("init")) {
            int mem_len = Integer.parseInt(memSize.getText());
            int app_len = Integer.parseInt(appSize.getText());
            int use = algo.getSelectedIndex();
            system.init(app_len, mem_len, use);
        }
        if (e.getActionCommand().equals("next")) {
            if (jrb1.isSelected()) {
                system.next();
            }
            if (jrb2.isSelected()) {
                int flag = codeChoose.getSelectedIndex();
                if (flag == 0) {
                    int size = Integer.parseInt(inputMen.getText());
                    system.next(new Code(0, size));
                }
                else {
                    int id = (int)codeChoose.getSelectedItem();
                    system.next(new Code(id));
                }
            }
            control.repaint();
            appChoose.removeAllItems();
            LinkedList<App> appList = system.getAppList();
            for (int i = 0;i < appList.size();i++) {
                if (appList.get(i).isGetMem && !appList.get(i).isDone) {
                    appChoose.addItem(appList.get(i).ID);
                }
            }
        }
        if (e.getActionCommand().equals("random")) {
            jrb2.setSelected(false);
            codeControl.setEnabled(false);
        }
        if (e.getActionCommand().equals("input")) {
            jrb1.setSelected(false);
            codeControl.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        new Scene();
    }
}
