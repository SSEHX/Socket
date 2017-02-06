import com.paobe.FileOption.SetWindowCenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by xuqi on 2017/1/30.
 */
public class ClientWindow {
    private JTextField ipText;
    private JTextField portText;
    private JButton TESTButton;
    private JButton CONNECTButton;
    private JTextField userText;
    private JPasswordField passwordText;
    private JPanel jPanel;
    private JList list1;
    private JLabel state;
    private JCheckBox addTheHistoryCheckBox;
    private JCheckBox rememberThePasswordCheckBox;
    private JCheckBox automaticLoginCheckBox;
    private JFrame jFrame = null;

    public ClientWindow() {
        init();
    }

    public static void main(String[] args) {
         new ClientWindow();
    }

    //初始化窗口
    public void init() {
        addLis();
        jFrame = new JFrame("Client");
        jFrame.setContentPane(jPanel);
        jFrame.pack();
        new SetWindowCenter(jFrame);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //添加监听
    public void addLis() {
        TESTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        CONNECTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startClient();
            }
        });
        rememberThePasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void startClient() {
        System.out.println("启动连接");
        new Client(ipText.getText(),Integer.parseInt(portText.getText()),this);
    }

    //关闭窗口
    public void closeWindow() {
        jFrame.dispose();
    }

    //显示正在连接状态
    public void showConnecting(){
        state.setText("connecting");
    }

    //显示连接失败状态
    public void showConnectError() {
        state.setText("Connection failed, please try again");
    }


}
