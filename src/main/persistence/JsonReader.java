package persistence;

import model.Card;
import model.Deck;
import model.GameDecks;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Resource: JsonSerializationDemo
// Represents a reader that reads GameDecks from JSON data stored in file
public class JsonReader {
    private String file;

    //EFFECTS: constructs reader to read the file
    public JsonReader(String file) {
        this.file = file;
    }

    //EFFECTS: reads the decks of GameDecks from file and returns it as a GameDeck
    // throws IOException if any errors come up when reading the file
    public GameDecks read() throws IOException {
        String jsonData = readFile(file);
        JSONObject json = new JSONObject(jsonData);
        return parseGameDecks(json);
    }

    //EFFECTS: reads the source file and returns it as a string
    private String readFile(String file) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(file), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    //EFFECTS: parses the json to type GameDecks and returns it
    private GameDecks parseGameDecks(JSONObject json) {
        GameDecks decks = new GameDecks();
        JSONArray jsonArray = json.getJSONArray("Decks");
        for (Object o : jsonArray) {
            JSONObject nextDeck = (JSONObject) o;
            decks.addDeck(parseDeck(nextDeck));
        }
        return decks;
    }

    //EFFECTS: helper method to parse the deck in GameDecks from JSON object and returns it
    private Deck parseDeck(JSONObject json) {
        Deck deck = new Deck();
        addCards(deck, json);
        return deck;
    }

    //MODIFIES: deck
    //EFFECTS: helper method to parse card from JSON object and adds it to deck
    private void addCards(Deck deck, JSONObject json) {
        JSONArray jsonArray = json.getJSONArray("cards");
        for (Object o : jsonArray) {
            JSONObject nextCard = (JSONObject) o;
            String colour = nextCard.getString("colour");
            int rank = nextCard.getInt("rank");
            Card card = new Card(colour,rank);
            deck.addCard(card);
        }
    }

}
