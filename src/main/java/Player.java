import java.util.ArrayList;

public class Player {

    private ArrayList<Card> cards = new ArrayList<>();
    private String name;
    private int shields = 0;

    public Player(String name) {
        this.name = name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public String getName() {
        return this.name;
    }

    public int getShields() {
        return this.shields;
    }

    public void addCard(Card e) {
        cards.add(e);
    }

    public void giveShields(int amount) {
        shields += amount;
    }
}
