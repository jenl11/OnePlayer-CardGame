package model;

import org.json.JSONObject;
import persistence.Writable;

import java.nio.file.Watchable;
import java.util.Objects;

/**
 * Represents a playing card with a colour of the card and the rank
 */
public class Card implements Writable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return rank == card.rank && Objects.equals(colour, card.colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colour, rank);
    }

    //Code reference: https://www.javatpoint.com/understanding-toString()-method
    //EFFECT: returns the string representation of the card
    public String toString() {
        return "<" + getColour() + "," + getRank() + ">";
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("colour", colour);
        json.put("rank", rank);
        return json;
    }
}
