import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    public static ArrayList<String[]> Parse(String path) {
        //String path = "D:/jaba/projs/untitledb/src/Землетрясения.csv";
        String line = "";

        ArrayList<String[]> values = new ArrayList<String[]>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                String[] array = line.split(",");
                if (array.length == 7) {
                    String[] newArray = new String[6];
                    for (int i = 0, j = 0; i < array.length; i++) {
                        if (i != 4) {
                            newArray[j++] = array[i];
                        }
                    }
                    newArray[4] = newArray[4].replace("\"", "").toUpperCase();
                    values.add(newArray);
                    continue;
                }
                array[4] = array[4].toUpperCase();
                values.add(array);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }
}
