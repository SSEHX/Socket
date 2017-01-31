import javax.swing.*;

/**
 * 使传入的JFrame窗口居中
 * Created by xuqi on 2017/1/31.
 */
public class SetWindowCenter {
    public SetWindowCenter(JFrame jFrame) {
        int width = jFrame.getWidth();
        int height = jFrame.getHeight();

        int screenWidth = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int screenHeight = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        jFrame.setLocation((screenWidth-width)/2,(screenHeight-height)/2);
    }
}
