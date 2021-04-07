import javax.swing.*;

public class CalendarException extends Exception{
    public CalendarException(String s)
    {
        JOptionPane.showMessageDialog(null,s);
    }
}
