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
        JLabel lbl_image = new JLabel(picture);
        jFrame.getLayeredPane().add(lbl_image, JLayeredPane.FRAME_CONTENT_LAYER);
        lbl_image.setSize(picture.getIconWidth(), picture.getIconHeight());
        Container cp = jFrame.getContentPane();
        cp.setLayout(new BorderLayout());
        //设成透明
        ((JPanel) cp).setOpaque(false);
        jFrame.setSize(picture.getIconWidth(), picture.getIconHeight());
    }
}
