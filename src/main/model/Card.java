package model;

import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Watchable;
import java.util.Objects;

/**
 * Represents a playing card with a colour of the card and the rank
 */
public class Card implements Writable {

    private ImageIcon image;
    private String colour;
    private int rank;

    //REQUIRES: c has to be "r" or "b" and r had to be within the range of [1,13]
    //EFFECT: creates a card with the given colour and rank and assigns a imagin to that card
    public Card(String c, int r) {
        colour = c;
        rank = r;
        String sep = System.getProperty("file.separator");
        if (colour.equals("r")) {
            redCardSetUp(sep, "AD.png", "D.png", "JD.png", "QD.png", "KD.png");
        } else if (colour.equals("b")) {
            blackCardSetUp(sep, "AC.png", "C.png", "JC.png", "QC.png", "KC.png");
        }
    }

    private void blackCardSetUp(String sep, String s, String s2, String s3, String s4, String s5) {
        if (rank == 1) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + s);
        } else if (rank >= 2 && rank <= 10) {
            for (int i = 2; i < 11; i++) {
                image = new ImageIcon(System.getProperty("user.dir") + sep
                        + "image" + sep + rank + s2);
            }
        } else if (rank == 11) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + s3);
        } else if (rank == 12) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + s4);
        } else if (rank == 13) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + s5);
        }
    }

    private void redCardSetUp(String sep, String s, String s2, String s3, String s4, String s5) {
        if (rank == 1) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + s);
        } else if (rank >= 2 && rank <= 10) {
            for (int i = 2; i < 11; i++) {
                image = new ImageIcon(System.getProperty("user.dir") + sep
                        + "image" + sep + rank + s2);
            }
        } else if (rank == 11) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + s3);
        } else if (rank == 12) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + s4);
        } else if (rank == 13) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + s5);
        }
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

    public ImageIcon getImage() {
        Image imageScaled = image.getImage().getScaledInstance(65, 100,Image.SCALE_DEFAULT);
        ImageIcon newImage = new ImageIcon(imageScaled);
        return newImage;
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
