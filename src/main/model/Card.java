package model;

/**
 * Represents a playing card with a colour of the card and the rank
 */
public class Card {

    private String colour;
    private int rank;

    //REQUIRES: c has to be "r" or "b" and r had to be within the range of [1,13]
    //EFFECT: creates a card with the given colour and rank
    public Card(String c, int r) {
        colour = c;
        rank = r;
    }

    public String getColour() {
        return colour;
    }

    public int getRank() {
        return rank;
    }

    //EFFECT: returns true if the otherCard is the same as this card
    public boolean sameCard(Card otherCard) {
        return colour.equals(otherCard.getColour()) && rank == otherCard.getRank();
    }

    //Code reference: https://www.javatpoint.com/understanding-toString()-method
    //EFFECT: returns the string representation of the card
    public String toString() {
        return "<" + getColour() + "," + getRank() + ">";
    }
}
