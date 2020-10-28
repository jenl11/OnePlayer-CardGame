package persistence;

import model.Card;
import model.Deck;
import model.GameDecks;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;


public class JsonWriterTest {

    @Test
    void testWriterIllegalFile() {
        try {
            GameDecks decks = new GameDecks();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyGameDecks() {
        try {
            GameDecks decks = new GameDecks();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGameDecks.json");
            writer.open();
            writer.write(decks);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterEmptyGameDecks.json");
            decks = reader.read();
            assertEquals(0, decks.getSize());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    void testWriterGeneralGameDecks() {
        try {
            GameDecks decks = new GameDecks();
            Deck red = new Deck();
            Deck black = new Deck();
            red.addCard(new Card("r",13));
            black.addCard(new Card("b",13));
            decks.addDeck(red);
            decks.addDeck(black);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGameDecks.json");
            writer.open();
            writer.write(decks);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralGameDecks.json");
            decks = reader.read();
            List<Deck> deck = decks.getDecksToList();
            assertEquals(2, deck.size());
            assertTrue(red.equals(deck.get(0)));
            assertTrue(black.equals(deck.get(1)));
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

}
