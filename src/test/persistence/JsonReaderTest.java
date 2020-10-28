package persistence;

import model.Deck;
import model.GameDecks;
import model.Card;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {


    @Test
    void testReaderNoExistingFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameDecks decks = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyDecks() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDecks.json");
        try {
            GameDecks decks = reader.read();
            assertEquals(0, decks.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGameDecks() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGameDecks.json");
        Deck red = new Deck();
        Deck black = new Deck();
        red.addCard(new Card("r",13));
        black.addCard(new Card("b",13));
        try {
            GameDecks decks = reader.read();
            List<Deck> deck = decks.getDecksToList();
            assertEquals(2, deck.size());
            assertEquals(deck.get(0), red);
            assertEquals(deck.get(1), black);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
