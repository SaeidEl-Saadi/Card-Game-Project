import java.util.ArrayList;

public class Game {

    private ArrayList<Card> adventureDeck;

    public Game() {

    }

    public boolean isAdventureDeckEmpty() {
        return false;
    }

    public ArrayList<Card> getAdventureDeck() {
        return new ArrayList<Card>();
    }

    public ArrayList<Card> getEventDeck() {
        return new ArrayList<Card>();
    }
}
