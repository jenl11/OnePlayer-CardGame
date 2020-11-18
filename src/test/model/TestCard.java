package model;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestCard {

    @Test
    public void testConstructor() {
        Card card1 = new Card("r", 1);

        assertEquals("r", card1.getColour());
        assertEquals(1, card1.getRank());
        assertEquals("AD.png",card1.getImage().getDescription());
    }

    @Test
    public void testSameCard() {
        Card card1 = new Card("r", 1);
        Card card2 = new Card("r", 1);

        assertTrue(card1.sameCard(card2));
    }

    @Test
    public void testNotSameCard() {
        Card card1 = new Card("r", 1);
        Card card2 = new Card("b", 3);

        assertFalse(card1.sameCard(card2));
    }

    @Test
    public void testNotEqualNull() {
        Card card1 = new Card("r", 1);
        Card card2 = null;
        assertFalse(card1.equals(card2));
    }

    @Test
    public void testNotEqualsDifferentClass() {
        Card card1 = new Card("r", 1);
        Deck deck = new Deck();
        assertFalse(card1.equals(deck));
    }

    @Test
    public void testSameHashCode() {
        Card card1 = new Card("r", 1);
        Card card2 = new Card("r", 1);
        assertEquals(card2, card1);
        assertEquals(card2.hashCode(), card1.hashCode());
    }

    @Test
    public void testDifferentHashCode() {
        Card card1 = new Card("r", 1);
        Card card2 = new Card("b", 2);
        assertNotEquals(card1, card2);
        assertTrue(card1.hashCode() != card2.hashCode());
    }

    @Test
    public void testToString() {
        Card card1 = new Card("r", 1);
        String string = "<r,1>";

        assertEquals(string, card1.toString());
    }

    @Test
    public void testImageIcon() {
        Card card1 = new Card("r", 1);
        Card card2 = new Card("r", 1);
        assertEquals(card1.getImage().getDescription(), card2.getImage().getDescription());
    }


}
