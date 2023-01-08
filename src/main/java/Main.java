import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Database.Conn();
        //Database.CreateDB();
        //Database.WriteDB();
        Database.ReadDB();
        Database.PrintAvgVWMagnitude();
        Database.PrintDeepest2013Earthquake();
        Database.PrintChart();
        Database.CloseDB();
    }
}
