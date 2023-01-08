import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static java.lang.Float.parseFloat;

public class ParseCSV {
    public static ArrayList<Earthquake> ParseToObjects() {
        ArrayList<Earthquake> dataList = new ArrayList<Earthquake>();
        ArrayList<String[]> data = Parser.Parse("D:/jaba/projs/untitledb/src/Землетрясения.csv");
        for (int i = 1; i < data.size(); i++) {
            String[] dataLine  = data.get(i);
            if (!dataLine[5].contains(" ")) {
                dataLine[5] = String.valueOf(OffsetDateTime.parse(dataLine[5]).getYear());
                //dataLine[5] = OffsetDateTime.parse(dataLine[5]).
                //        format(DateTimeFormatter.ofPattern("yyyy-M-dd hh:mm:ss"));
            } else dataLine[5] = dataLine[5].substring(0, 4);
            System.out.println(dataLine[5]);
            Earthquake earthquake = new Earthquake(dataLine[0], Integer.parseInt(dataLine[1]),
                    dataLine[2], parseFloat(dataLine[3]), dataLine[4].replaceAll("\\s", ""), dataLine[5]);
            dataList.add(earthquake);
        }
        return dataList;
    }
}
