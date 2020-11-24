package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TestCard {
    Card card1r;
    Card card2r;
    Card card3r;
    Card card4r;
    Card card5r;
    Card card6r;
    Card card7r;
    Card card8r;
    Card card9r;
    Card card10r;
    Card card11r;
    Card card12r;
    Card card13r;
    Card card1b;
    Card card2b;
    Card card3b;
    Card card4b;
    Card card5b;
    Card card6b;
    Card card7b;
    Card card8b;
    Card card9b;
    Card card10b;
    Card card11b;
    Card card12b;
    Card card13b;

    @BeforeEach
    public void setup() {
        card1r = new Card("r", 1);
        card2r = new Card("r", 2);
        card3r = new Card("r", 3);
        card4r = new Card("r", 4);
        card5r = new Card("r", 5);
        card6r = new Card("r", 6);
        card7r = new Card("r", 7);
        card8r = new Card("r", 8);
        card9r = new Card("r", 9);
        card10r = new Card("r", 10);
        card11r = new Card("r", 11);
        card12r = new Card("r", 12);
        card13r = new Card("r", 13);

        card1b = new Card("b", 1);
        card2b = new Card("b", 2);
        card3b = new Card("b", 3);
        card4b = new Card("b", 4);
        card5b = new Card("b", 5);
        card6b = new Card("b", 6);
        card7b = new Card("b", 7);
        card8b = new Card("b", 8);
        card9b = new Card("b", 9);
        card10b = new Card("b", 10);
        card11b = new Card("b", 11);
        card12b = new Card("b", 12);
        card13b = new Card("b", 13);

    }

    @Test
    public void testCardConstructor() {
        assertEquals("r", card1r.getColour());
        assertEquals(1, card1r.getRank());

        try {
            Card card14 = new Card("r", 14);
            fail();
        } catch (IllegalArgumentException e) {
            fail("wrong exception");
        } catch (IndexOutOfBoundsException e) {
            //expected
        }

        try {
            Card cardNeg = new Card("r", -1);
            fail();
        } catch (IllegalArgumentException e) {
            fail("wrong exception");
        } catch (IndexOutOfBoundsException e) {
            //expected
        }

        try {
            Card cardZ = new Card("Z", 1);
            fail();
        } catch (IllegalArgumentException e) {
            //expected
        } catch (IndexOutOfBoundsException e) {
            fail("wrong exception");
        }
    }

    @Test
    public void testConstructorRedCardIcon() {
        assertEquals("AD.png",card1r.getImage().getDescription());
        assertEquals("2D.png",card2r.getImage().getDescription());
        assertEquals("3D.png",card3r.getImage().getDescription());
        assertEquals("4D.png",card4r.getImage().getDescription());
        assertEquals("5D.png",card5r.getImage().getDescription());
        assertEquals("6D.png",card6r.getImage().getDescription());
        assertEquals("7D.png",card7r.getImage().getDescription());
        assertEquals("8D.png",card8r.getImage().getDescription());
        assertEquals("9D.png",card9r.getImage().getDescription());
        assertEquals("10D.png",card10r.getImage().getDescription());
        assertEquals("JD.png",card11r.getImage().getDescription());
        assertEquals("QD.png",card12r.getImage().getDescription());
        assertEquals("KD.png",card13r.getImage().getDescription());
    }

    @Test
    public void testConstructorBlackCardIcon() {
        assertEquals("AC.png",card1b.getImage().getDescription());
        assertEquals("2C.png",card2b.getImage().getDescription());
        assertEquals("3C.png",card3b.getImage().getDescription());
        assertEquals("4C.png",card4b.getImage().getDescription());
        assertEquals("5C.png",card5b.getImage().getDescription());
        assertEquals("6C.png",card6b.getImage().getDescription());
        assertEquals("7C.png",card7b.getImage().getDescription());
        assertEquals("8C.png",card8b.getImage().getDescription());
        assertEquals("9C.png",card9b.getImage().getDescription());
        assertEquals("10C.png",card10b.getImage().getDescription());
        assertEquals("JC.png",card11b.getImage().getDescription());
        assertEquals("QC.png",card12b.getImage().getDescription());
        assertEquals("KC.png",card13b.getImage().getDescription());
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


}
