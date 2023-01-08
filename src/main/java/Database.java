import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Database {
    public static Connection c;
    public static Statement stmt;
    public static ResultSet resSet;
    public static void Conn() throws ClassNotFoundException, SQLException {
        c = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:earthquakesdb.s3db");
        stmt = c.createStatement();
    }

    public static void CreateDB() throws SQLException {
        stmt = c.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS 'EARTHQUAKES' " +
                "(ID STRING PRIMARY KEY NOT NULL," +
                " DEPTH INT NOT NULL," +
                " TYPE STRING NOT NULL, " +
                " MAGNITUDE REAL NOT NULL, " +
                " STATE STRING NOT NULL, " +
                " DATE INT NOT NULL)";
        stmt.execute(sql);
    }

    public static void WriteDB() throws SQLException {
        ArrayList<Earthquake> data = ParseCSV.ParseToObjects();
        for (int i = 0; i < data.size(); i++) {
            Earthquake object = data.get(i);
            String values = "('" + object.id + "', '" + object.depth + "', '" + object.type + "', '" +
                    object.magnitude + "', '" + object.state + "', '" + object.date + "')";
            stmt.execute("INSERT INTO 'EARTHQUAKES' ('ID', 'DEPTH', 'TYPE', 'MAGNITUDE', 'STATE', 'DATE') VALUES " + values);
        }
    }

    public static void ReadDB() throws SQLException {
        resSet = stmt.executeQuery("SELECT * FROM EARTHQUAKES");
        while (resSet.next()) {
            String id = resSet.getString("ID");
            int depth = resSet.getInt("DEPTH");
            String type = resSet.getString("TYPE");
            float magnitude = resSet.getFloat("MAGNITUDE");
            String state = resSet.getString("STATE");
            String date = resSet.getString("DATE");
            System.out.println("ID: " + id + ", " + "Depth: " + depth + ", " + "Type: " + type + ", " +
                    "Magnitude: " + magnitude + ", " + "State: " + state + ", " + "Date: " + date);
        }
    }

    public static void PrintAvgVWMagnitude() throws SQLException {
        resSet = stmt.executeQuery("SELECT * from EARTHQUAKES WHERE STATE = 'WESTVIRGINIA'");
        ArrayList<Float> magnitudes = new ArrayList<Float>();
        while (resSet.next()) {
            float magnitude = resSet.getFloat("MAGNITUDE");
            magnitudes.add(magnitude);
        }
        float sum = 0;
        for (int i = 0; i < magnitudes.size(); i++) sum += magnitudes.get(i);
        float average = sum / magnitudes.size();
        System.out.println("Средняя магнитуда для штата West Virginia: " + average);
    }

    public static void PrintDeepest2013Earthquake() throws SQLException {
        int maxDepth = 0;
        String state = "";
        resSet = stmt.executeQuery("SELECT * from EARTHQUAKES WHERE DATE = 2013");
        while (resSet.next()) {
            int depth = resSet.getInt("DEPTH");
            if (depth > maxDepth) {
                maxDepth = depth;
                state = resSet.getString("STATE");
            }
        }
        System.out.println("Штат, в котором произошло самое глубокое землетрясение в 2013 году: " + state);
    }

    public static void PrintChart() throws SQLException {
        HashMap<Integer, Integer> years = new HashMap<Integer, Integer>();
        resSet = stmt.executeQuery("SELECT * from EARTHQUAKES");
        while (resSet.next()) {
            int year = resSet.getInt("DATE");
            if (years.containsKey(year)) {
                years.put(year, years.get(year) + 1);
            } else years.put(year, 1);
        }
        Map<Integer, Integer> treeMap = new TreeMap<Integer, Integer>(years);
        new TimeSeriesChartDrawer("График", treeMap).setVisible(true);
    }
    public static  void CloseDB() throws SQLException {
        c.close();
        stmt.close();
        resSet.close();
        System.out.println("DB connections closed");
    }
}

