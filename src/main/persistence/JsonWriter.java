package persistence;

import model.GameDecks;
import org.json.JSONObject;

import java.io.*;

// Resource: JsonSerializationDemo
// Represents a writer that writes GameDecks into JSON data and stores it in a file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String location;

    //EFFECTS: constructs a writer to write to the location specified
    public JsonWriter(String location) {
        this.location = location;
    }

    //MODIFIES: this
    //EFFECTS: opens the writer and throws FileNotFoundException if
    // the file in the location cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(location));
    }

    //MODIFIES: this
    //EFFECTS: writes the JSON representation of the GameDecks
    // and saves it to the file
    public void write(GameDecks decks) {
        JSONObject json = decks.toJson();
        saveToFile(json.toString(TAB));
    }

    //MODIFIES: this
    //EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    //EFFECT: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
