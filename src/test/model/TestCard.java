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
    public void testConstructorRedCardIcon() {
        Card card1 = new Card("r", 1);
        Card card2 = new Card("r", 2);
        Card card3 = new Card("r", 3);
        Card card4 = new Card("r", 4);
        Card card5 = new Card("r", 5);
        Card card6 = new Card("r", 6);
        Card card7 = new Card("r", 7);
        Card card8 = new Card("r", 8);
        Card card9 = new Card("r", 9);
        Card card10 = new Card("r", 10);
        Card card11 = new Card("r", 11);
        Card card12 = new Card("r", 12);
        Card card13 = new Card("r", 13);

        assertEquals("AD.png",card1.getImage().getDescription());
        assertEquals("2D.png",card2.getImage().getDescription());
        assertEquals("3D.png",card3.getImage().getDescription());
        assertEquals("4D.png",card4.getImage().getDescription());
        assertEquals("5D.png",card5.getImage().getDescription());
        assertEquals("6D.png",card6.getImage().getDescription());
        assertEquals("7D.png",card7.getImage().getDescription());
        assertEquals("8D.png",card8.getImage().getDescription());
        assertEquals("9D.png",card9.getImage().getDescription());
        assertEquals("10D.png",card10.getImage().getDescription());
        assertEquals("JD.png",card11.getImage().getDescription());
        assertEquals("QD.png",card12.getImage().getDescription());
        assertEquals("KD.png",card13.getImage().getDescription());

        try {
            Card card14 = new Card("r", 14);
            card14.getImage();
            fail();
        } catch (NullPointerException e) {
            //expected
        }

        try {
            Card cardNeg = new Card("r", -1);
            cardNeg.getImage();
            fail();
        } catch (NullPointerException e) {
            //expected
        }

        try {
            Card cardZ = new Card("Z", 1);
            cardZ.getImage();
            fail();
        } catch (NullPointerException e) {
            //expected
        }
    }

    @Test
    public void testConstructorBlackCardIcon() {
        Card card1 = new Card("b", 1);
        Card card2 = new Card("b", 2);
        Card card3 = new Card("b", 3);
        Card card4 = new Card("b", 4);
        Card card5 = new Card("b", 5);
        Card card6 = new Card("b", 6);
        Card card7 = new Card("b", 7);
        Card card8 = new Card("b", 8);
        Card card9 = new Card("b", 9);
        Card card10 = new Card("b", 10);
        Card card11 = new Card("b", 11);
        Card card12 = new Card("b", 12);
        Card card13 = new Card("b", 13);

        assertEquals("AC.png",card1.getImage().getDescription());
        assertEquals("2C.png",card2.getImage().getDescription());
        assertEquals("3C.png",card3.getImage().getDescription());
        assertEquals("4C.png",card4.getImage().getDescription());
        assertEquals("5C.png",card5.getImage().getDescription());
        assertEquals("6C.png",card6.getImage().getDescription());
        assertEquals("7C.png",card7.getImage().getDescription());
        assertEquals("8C.png",card8.getImage().getDescription());
        assertEquals("9C.png",card9.getImage().getDescription());
        assertEquals("10C.png",card10.getImage().getDescription());
        assertEquals("JC.png",card11.getImage().getDescription());
        assertEquals("QC.png",card12.getImage().getDescription());
        assertEquals("KC.png",card13.getImage().getDescription());

        try {
            Card card14 = new Card("b", 14);
            card14.getImage();
            fail();
        } catch (NullPointerException e) {
            //expected
        }

        try {
            Card cardNeg = new Card("b", -1);
            cardNeg.getImage();
            fail();
        } catch (NullPointerException e) {
            //expected
        }
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
