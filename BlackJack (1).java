//Edited by Kai Sanchez on 9/16/2024
import java.util.ArrayList; // added ArrayList for dynamic card storage
import java.util.Collections; // added Collections for shuffling the deck
import java.util.Scanner; // added Scanner for user input

public class BlackJack {

  private static ArrayList<Card> deck = new ArrayList<>(); // changed from array to ArrayList for deck

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean turn = true;
    String playerDecision = "";

    while (turn) {
      initializeDeck(); // initialize deck using ArrayList to store cards
      shuffleDeck(); // shuffle deck using Collections.shuffle for randomization
     

      int playerTotal = dealInitialPlayerCards(); // deal two cards to the player
      int dealerTotal = dealInitialDealerCards(); // deal one card to the dealer
      
      // Player's turn to hit or stand
      playerTotal = playerTurn(scanner, playerTotal);
      if (playerTotal > 21) { // if player busts, the game continues to the next round
        System.out.println("You busted! Dealer wins.");
        continue; // continue instead of returning, allowing more games
      }
      
      // Dealer's turn once the player stands
      dealerTotal = dealerTurn(dealerTotal);
      determineWinner(playerTotal, dealerTotal); // determine the winner based on player and dealer totals

      // Ask player if they want to play another hand
      System.out.println("Would you like to play another hand? (yes/no)");
      playerDecision = scanner.nextLine().toLowerCase(); // capturing player's decision

      // Validate the player's input for yes or no
      while (!(playerDecision.equals("no") || playerDecision.equals("yes"))) {
        System.out.println("Invalid action. Please type 'yes' or 'no'.");
        playerDecision = scanner.nextLine().toLowerCase();
      }
      
      // If player says 'no', exit the loop and end the game
      if (playerDecision.equals("no"))
          turn = false;
    }
    System.out.println("Thanks for playing!");
  }

  // Method to initialize the deck, adding cards for each suit and rank
  private static void initializeDeck() {
    String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
    String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };
    deck.clear(); // clear the deck before initializing

    // Loop through suits and ranks to create a full deck of cards
    for (String suit : SUITS) {
      for (String rank : RANKS) {
        int value;
        if (rank.equals("Ace")) {
          value = 11; // Ace is initially valued as 11
        } else if (rank.equals("King") || rank.equals("Queen") || rank.equals("Jack")) {
          value = 10; // Face cards are valued at 10
        } else {
          value = Integer.parseInt(rank); // Number cards take their face value
        }
        deck.add(new Card(value, suit, rank)); // add each card to the deck
      }
    }
  }

  // Shuffle the deck using Collections.shuffle for randomness
  private static void shuffleDeck() {
    Collections.shuffle(deck);
  }

  // Deal two cards to the player at the start
  private static int dealInitialPlayerCards() {
    Card card1 = dealCard(); // deal the first card
    Card card2 = dealCard(); // deal the second card
    
    // Show the player's initial cards
    System.out.println("Your cards: " + card1.getRank() + " of " + card1.getSuit() + " and " + card2.getRank() + " of " + card2.getSuit());
    return calculateTotalWithAce(card1.getValue(), card2.getValue()); // calculate the player's total considering Aces
  }

  // Deal the first card to the dealer and show it
  private static int dealInitialDealerCards() {
    Card card1 = dealCard();
    System.out.println("Dealer's card: " + card1.getRank() + " of " + card1.getSuit());
    return card1.getValue();
  }

  // Handle player's turn, allowing them to hit or stand
  private static int playerTurn(Scanner scanner, int playerTotal) {
    while (true) {
      System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
      String action = scanner.nextLine().toLowerCase();

      if (action.equals("hit")) { // If the player hits, deal another card
        Card newCard = dealCard();
        playerTotal += newCard.getValue();
        System.out.println("You drew a " + newCard.getRank() + " of " + newCard.getSuit());
        playerTotal = calculateTotalWithAce(playerTotal); // recalculate total, considering Aces

        if (playerTotal > 21) { // If the player busts, return immediately
          System.out.println("You busted! Dealer wins.");
          return playerTotal;
        }
      } else if (action.equals("stand")) { // If the player stands, end the turn
        break;
      } else {
        System.out.println("Invalid action. Please type 'hit' or 'stand'.");
      }
    }
    return playerTotal;
  }

  // Dealer's turn, dealer must hit until reaching 17 or higher
  private static int dealerTurn(int dealerTotal) {
    while (dealerTotal < 17) { // dealer hits until total is at least 17
      Card newCard = dealCard();
      dealerTotal += newCard.getValue();
      dealerTotal = calculateTotalWithAce(dealerTotal); // recalculate dealer's total, considering Aces
    }
    System.out.println("Dealer's total is " + dealerTotal);
    return dealerTotal;
  }

  // Determine the winner based on player and dealer totals
  private static void determineWinner(int playerTotal, int dealerTotal) {
    if (dealerTotal > 21 || playerTotal > dealerTotal) {
      System.out.println("You win!");
    } else if (dealerTotal == playerTotal) {
      System.out.println("It's a tie!");
    } else {
      System.out.println("Dealer wins!");
    }
  }

  // Deal the top card from the deck and return it
  private static Card dealCard() {
    return deck.remove(0); // Remove the top card from the deck
  }

  // Calculate the total value of the hand, handling Aces which can be 1 or 11
  private static int calculateTotalWithAce(int... values) {
    int total = 0;
    int aceCount = 0; // Track the number of Aces in the hand

    // Sum the card values, counting Aces
    for (int value : values) {
      total += value;
      if (value == 11) {
        aceCount++;
      }
    }

    // If total exceeds 21, and there are Aces, reduce their value from 11 to 1
    while (total > 21 && aceCount > 0) {
      total -= 10; // reduce total by 10 to convert an Ace from 11 to 1
      aceCount--;
    }

    return total; // return the final calculated total
  }
}

// Card class representing a single playing card
class Card {
  private int value;
  private String suit;
  private String rank;

  public Card(int value, String suit, String rank) {
    this.value = value;
    this.suit = suit;
    this.rank = rank;
  }

  public int getValue() {
    return value;
  }

  public String getSuit() {
    return suit;
  }

  public String getRank() {
    return rank;
  }

  @Override
  public String toString() {
    return rank + " of " + suit;
  }
}
