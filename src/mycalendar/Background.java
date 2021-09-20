package mycalendar;

import javax.swing.*;
import java.awt.*;

/**
 * 设置背景工具类
 *
 * @Date: 2020/3/11 9:58
 * @author: Eric
 */
public class Background {
    //设置背景
    public static void setBackgroundPicture(JFrame jFrame, ImageIcon picture) {
        // 设置背景图片的宽高
        int width = 1024;
        int height = 614;
        // 获取背景图的宽高
        int picWidth = picture.getIconWidth();
        int picHeight = picture.getIconHeight();
        if (picWidth != -1) {
            width = picWidth;
        }
        if (picHeight != -1) {
            height = picHeight;
        }
        // 获得屏幕宽高
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        if (picWidth > screenWidth) {
            width = screenWidth;
        }
        if (picHeight > screenHeight) {
            height = screenHeight;
        }
        picture.setImage(picture.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        // 设置 JLabel 组件
        JLabel lbl_image = new JLabel(picture);
        jFrame.getLayeredPane().add(lbl_image, JLayeredPane.FRAME_CONTENT_LAYER);
        lbl_image.setSize(width, height);
        Container cp = jFrame.getContentPane();
        cp.setLayout(new BorderLayout());
        //设成透明
        ((JPanel) cp).setOpaque(false);
        jFrame.setSize(width, height);
    }
}
