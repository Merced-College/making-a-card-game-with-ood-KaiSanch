// Edited by Kai Sanchez on 9/16/2024
public class card {
    private int value;
    private String suit;
    private String rank;

    // Constructor
    public card(int value, String suit, String rank) {
        this.value = value;
        this.suit = suit;
        this.rank = rank;
    }

    // Accessor 
    public int getValue() {
        return value;
    }
    // Accessor
    public String getSuit() {
        return suit;
    }
    //Accessor
    public String getRank() {
        return rank;
    }

    // Mutator 
    public void setValue(int value) {
        this.value = value;
    }
     // Mutator
    public void setSuit(String suit) {
        this.suit = suit;
    }
     //Mutator
    public void setRank(String rank) {
        this.rank = rank;
    }

    // toString method
    public String toString() {
        return rank + " of " + suit;
    }
}

