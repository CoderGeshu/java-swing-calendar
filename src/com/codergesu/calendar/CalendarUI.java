package com.codergesu.calendar;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;

/**
 * @Date: 2020/3/10 15:31
 * @author: Eric
 */
public class CalendarUI extends JFrame implements ActionListener {
    private static JLabel lbl_BeijingTime;
    private JComboBox<Integer> cmb_year;
    private JComboBox<Integer> cmb_month;
    private JButton btn_confirm, btn_return;
    private JButton btn_lastMonth, btn_nextMonth;
    private JLabel[] labels;


    private Calendar calendar = Calendar.getInstance();

    public CalendarUI(String title) {
        super(title);
        ImageIcon picture = new ImageIcon("picture/background.png");
        Background.setBackgroundPicture(this, picture);
        //调用初始化面板方法
        initPane();
        //设置当前的日历详细信息
        setTodayCalendar();
        //设置窗口属性
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int year, month;
        if (e.getSource() == btn_confirm) {
            year = (int) cmb_year.getSelectedItem();
            month = (int) cmb_month.getSelectedItem() - 1;
            showCalendar(year, month);
            return;
        }
        if (e.getSource() == btn_return) {
            setTodayCalendar();
            return;
        }
        if (e.getSource() == btn_lastMonth) {
            year = (int) cmb_year.getSelectedItem();
            //month第一次减一是因为月份是从零开始的
            //第二次减一是计算上一个月
            month = (int) cmb_month.getSelectedItem() - 1 - 1;
            if (month < 0) {
                month = 11; //12月
                --year;
            }
            showCalendar(year, month);
            return;
        }
        if (e.getSource() == btn_nextMonth) {
            year = (int) cmb_year.getSelectedItem();
            //month第一次减一是因为月份是从零开始的
            //然后加一是因为计算下一月份
            month = (int) cmb_month.getSelectedItem() - 1 + 1;
            if (month > 11) { // 11就已经代表12月了
                month = 0; //1月
                ++year;
            }
            showCalendar(year, month);
        }
    }

    /**
     * 初始化窗口面板
     */
    private void initPane() {
        //初始化顶部今日信息面板
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        lbl_BeijingTime = new JLabel(df.format(calendar.getTime()), JLabel.CENTER);
        lbl_BeijingTime.setFont(new Font("楷体", Font.PLAIN, 30));

        //初始化顶部查询日期面板
        cmb_year = new JComboBox<>();
        for (int i = 1900; i <= 2100; i++)
            cmb_year.addItem(i);
        JLabel lbl_year = new JLabel("年");
        lbl_year.setFont(new Font("楷体", Font.BOLD, 25));

        cmb_month = new JComboBox<>();
        for (int i = 1; i <= 12; i++)
            cmb_month.addItem(i);
        JLabel lbl_month = new JLabel("月");
        lbl_month.setFont(new Font("楷体", Font.BOLD, 25));

        btn_confirm = new JButton("确定查询");
        btn_confirm.setOpaque(false);
        btn_confirm.setBackground(Color.darkGray);
        btn_confirm.setFont(new Font("楷体", Font.BOLD, 20));
        btn_confirm.addActionListener(this);
        btn_return = new JButton("返回当前日期");
        btn_return.setOpaque(false);
        btn_return.setBackground(Color.darkGray);
        btn_return.setFont(new Font("楷体", Font.BOLD, 20));
        btn_return.addActionListener(this);

        JPanel jp_search = new JPanel();
        jp_search.setOpaque(false);
        jp_search.setBorder(new TitledBorder("查询日期"));
        jp_search.add(cmb_year);
        jp_search.add(lbl_year);
        jp_search.add(cmb_month);
        jp_search.add(lbl_month);
        jp_search.add(new JLabel("         "));
        jp_search.add(btn_confirm);
        jp_search.add(new JLabel("    "));
        jp_search.add(btn_return);

        //添加至顶部面板
        JPanel jp_top = new JPanel(new BorderLayout());
        jp_top.setOpaque(false);
        jp_top.add(lbl_BeijingTime, BorderLayout.NORTH);
        jp_top.add(jp_search, BorderLayout.CENTER);

        //初始化中间详细日期面板
        JPanel jp_display = new JPanel(new GridLayout(7, 7));
        jp_display.setOpaque(false);
        labels = new JLabel[49];
        for (int i = 0; i < 49; i++) {
            labels[i] = new JLabel(" ", JLabel.CENTER);
            labels[i].setFont(new Font("楷体", Font.PLAIN, 35));
            jp_display.add(labels[i]);
        }
        labels[0].setText("日");
        labels[1].setText("一");
        labels[2].setText("二");
        labels[3].setText("三");
        labels[4].setText("四");
        labels[5].setText("五");
        labels[6].setText("六");
        for (int i = 0; i < 7; i++) {
            labels[i].setForeground(Color.WHITE);
            labels[i].setBackground(Color.darkGray);
            labels[i].setOpaque(true);
        }

        //设置上一月或下一月查询按钮
        btn_lastMonth = new JButton("<<");
        btn_lastMonth.setBorder(null);
        btn_lastMonth.setOpaque(false);
        btn_lastMonth.setBackground(Color.darkGray);
        btn_lastMonth.setForeground(Color.WHITE);
        btn_lastMonth.setFont(new Font("楷体", Font.PLAIN, 30));
        btn_lastMonth.addActionListener(this);
        btn_lastMonth.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_lastMonth.setForeground(Color.green);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_lastMonth.setForeground(Color.white);
            }
        });

        btn_nextMonth = new JButton(">>");
        btn_nextMonth.setBorder(null);
        btn_nextMonth.setOpaque(false);
        btn_nextMonth.setBackground(Color.darkGray);
        btn_nextMonth.setForeground(Color.WHITE);
        btn_nextMonth.setFont(new Font("楷体", Font.PLAIN, 30));
        btn_nextMonth.addActionListener(this);
        btn_nextMonth.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_nextMonth.setForeground(Color.green);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_nextMonth.setForeground(Color.white);
            }
        });

        //添加至主窗口
        this.setBackground(new Color(202, 199, 198));
        this.add(jp_top, BorderLayout.NORTH);
        this.add(jp_display, BorderLayout.CENTER);
        this.add(btn_lastMonth, BorderLayout.WEST);
        this.add(btn_nextMonth, BorderLayout.EAST);
        this.validate();
    }

    /**
     * 根据年份和月份设置日历详细信息，并返回当前月第一天是周几
     * 注意：月份下标从0开始
     *
     * @param year  年份
     * @param month 月份
     * @return 第一天是周几
     */
    private int showCalendar(int year, int month) {
        //设置当前的下拉框为选择的日期
        cmb_year.setSelectedItem(year);
        cmb_month.setSelectedItem(month + 1);
        //把以前的日历信息清空
        for (int i = 7; i < 49; ++i) {
            labels[i].setText("");
            labels[i].setForeground(Color.black);
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
//        System.out.println("当前选择的是：" + year + "年" + (month + 1) + "月");
        //获得这一月有多少天
        int daysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//        System.out.println("这月一共有" + daysOfMonth + "天");
        //得到每月第一天是一周中的第几天，从周日开始计算
        //如：firstDay=0，表示周日；firstDay=1则表示周一，以此类推
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstWeekOfMonth = cal.get(Calendar.DAY_OF_WEEK) - 1;
//        System.out.println("这月第一天为周 " + firstWeekOfMonth);
        //为这月的每一天的标签赋值
        for (int i = 0; i < daysOfMonth; ++i) {
            labels[7 + firstWeekOfMonth + i].setText(i + 1 + "");
        }
        return firstWeekOfMonth;
    }

    /**
     * 设置当前年月的日历，并标出当前日期
     */
    private void setTodayCalendar() {
        cmb_year.setSelectedItem(calendar.get(Calendar.YEAR));
        cmb_month.setSelectedItem(calendar.get(Calendar.MONTH) + 1);
        int firstWeekOfMonth = showCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
        int today = calendar.get(Calendar.DAY_OF_MONTH);
//        System.out.println("今天是" + today + "号");
        labels[7 + firstWeekOfMonth + today - 1].setForeground(Color.red);
    }

    public static void updateBeijingTime() {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                lbl_BeijingTime.setText(df.format(new Date().getTime()));
            }
        }, 0, 1000);

    }

}
