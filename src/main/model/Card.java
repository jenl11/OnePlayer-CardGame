package model;

import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Represents a playing card with a colour of the card and the rank
 */
public class Card implements Writable {

    private ImageIcon image;
    private String colour;
    private int rank;

    //EFFECT: creates a card with the given colour and rank and assigns an image to that card
    public Card(String c, int r) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (c.equals("r") || c.equals("b")) {
            if (r <= 13 && r >= 1) {
                colour = c;
                rank = r;
                String sep = System.getProperty("file.separator");
                if (colour.equals("r")) {
                    redCardSetUp(sep);
                } else {
                    blackCardSetUp(sep);
                }
            } else {
                throw new IndexOutOfBoundsException("card rank is out of bounds");
            }
        } else {
            throw new IllegalArgumentException("card colour is neither red or black");
        }
    }

    //EFFECT: helper method for assigning an image to this card if this card is a black card
    private void blackCardSetUp(String sep) {
        if (rank == 1) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + "AC.png", "AC.png");
        } else if (rank >= 2 && rank <= 10) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + rank + "C.png",rank + "C.png");
        } else if (rank == 11) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + "JC.png","JC.png");
        } else if (rank == 12) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + "QC.png","QC.png");
        } else if (rank == 13) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + "KC.png","KC.png");
        }
    }

    //EFFECT: helper method for assigning an image to this card if this card is a red card
    private void redCardSetUp(String sep) {
        if (rank == 1) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + "AD.png","AD.png");
        } else if (rank >= 2 && rank <= 10) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + rank + "D.png",rank + "D.png");
        } else if (rank == 11) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + "JD.png","JD.png");
        } else if (rank == 12) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + "QD.png","QD.png");
        } else if (rank == 13) {
            image = new ImageIcon(System.getProperty("user.dir") + sep
                    + "image" + sep + "KD.png","KD.png");
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

    //Reference to resizing the image: https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel/32885963#32885963
    //EFFECT: returns an ImageIcon of the this card
    public ImageIcon getImage() {
        Image imageScaled = image.getImage().getScaledInstance(65, 100,Image.SCALE_DEFAULT);
        return new ImageIcon(imageScaled, image.getDescription());
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
