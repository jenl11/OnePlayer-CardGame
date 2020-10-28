package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCard {

    @Test
    public void testConstructor() {
        Card card1 = new Card("r", 1);

        assertEquals("r", card1.getColour());
        assertEquals(1, card1.getRank());
    }

    @Test
    public void testSameCard() {
        Card card1 = new Card("r", 1);
        Card card2 = new Card("r", 1);

        assertTrue(card1.sameCard(card2));
    }

    @Test
    public void testNotEqualsDifferentClass() {
        Card card1 = new Card("r", 1);
        Deck deck = new Deck();
        assertFalse(card1.equals(deck));
    }

    @Test
    public void testNotSameCard() {
        Card card1 = new Card("r", 1);
        Card card2 = new Card("b", 3);

        assertFalse(card1.sameCard(card2));
    }

    @Test
    public void testToString() {
        Card card1 = new Card("r", 1);
        String string = "<r,1>";

        assertEquals(string, card1.toString());
    }

}
