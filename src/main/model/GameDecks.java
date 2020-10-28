package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a list that contains all the four decks involved in the game
 * which are the decks: deck, hand, startWithRed, startWithBlack
 */
public class GameDecks implements Writable {
    List<Deck> decks;

    //EFFECTS: constructs an empty GameDecks
    public GameDecks() {
        decks = new ArrayList<>();
    }

    //EFFECTS: constructs GameDeck with the decks that are used in the game at that current state
    public GameDecks(Deck d, Deck h, Deck r, Deck b) {
        decks = new ArrayList<>();
        decks.add(d);
        decks.add(h);
        decks.add(r);
        decks.add(b);
    }

    public void addDeck(Deck deck) {
        decks.add(deck);
    }

    public Deck getDeck(int index) {
        return decks.get(index);
    }

    public int getSize() {
        return decks.size();
    }

    //Reference: JsonSerializationDemo -- WorkRoom.java
    // EFFECTS: returns an unmodifiable list of decks in GameDecks
    public List<Deck> getDecksToList() {
        return Collections.unmodifiableList(decks);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Decks", decksToJson());
        return json;
    }

    //EFFECTS: returns the decks in GameDecks as a JSON array
    private JSONArray decksToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Deck deck : decks) {
            jsonArray.put(deck.toJson());
        }
        return jsonArray;
    }
}
