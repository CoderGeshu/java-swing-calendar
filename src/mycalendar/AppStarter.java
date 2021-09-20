package mycalendar;

/**
 * @Date: 2021/9/20 21:51
 * @author: Eric
 */
public class AppStarter {
    public static void main(String[] args) {
        new MyCalendarUI("CoderGeshu-Calendar");
        MyCalendarUI.updateBeijingTime();
    }
}
