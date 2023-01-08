import java.time.LocalDate;

public class Earthquake {
    String id;
    int depth;
    String type;
    float magnitude;
    String state;
    String date;

    Earthquake(String id, int depth, String type, float magnitude, String state, String date) {
        this.id = id;
        this.depth = depth;
        this.type = type;
        this.magnitude = magnitude;
        this.state = state;
        this.date = date;
    }
}
