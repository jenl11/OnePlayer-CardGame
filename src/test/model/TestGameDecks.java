package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestGameDecks {

    private GameDecks decks;
    private GameDecks gameDecks;
    private Deck hand;
    private Deck deck;
    private Deck red;
    private Deck black;

    @BeforeEach
    public void setup() {
        hand = new Deck();
        deck = new Deck();
        red = new Deck();
        black = new Deck();
        decks = new GameDecks();
        gameDecks = new GameDecks(deck, hand, red, black);
    }

    @Test
    public void testEmptyConstructor() {
        assertEquals(0, decks.getSize());
    }

    @Test
    public void testConstructorWithDecks() {
        assertEquals(4, gameDecks.getSize());
        assertEquals(deck, gameDecks.getDeck(0));
        assertEquals(hand, gameDecks.getDeck(1));
        assertEquals(red, gameDecks.getDeck(2));
        assertEquals(black, gameDecks.getDeck(3));

    }




}
