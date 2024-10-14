import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {

    private static final String[][] FOE_CARDS = {
            {"F5", "5", "8"},
            {"F10", "10", "7"},
            {"F15", "15", "8"},
            {"F20", "20", "7"},
            {"F25", "25", "7"},
            {"F30", "30", "4"},
            {"F35", "35", "4"},
            {"F40", "40", "2"},
            {"F50", "50", "2"},
            {"F70", "70", "1"}
    };

    private static final String[][] WEAPON_CARDS = {
            {"D5", "5", "6"},
            {"H10", "10", "12"},
            {"S10", "10", "16"},
            {"B15", "15", "8"},
            {"L20", "20", "6"},
            {"E30", "30", "2"}
    };

    private static final String[][] QUEST_CARDS = {
            {"Q2", "2", "3"},
            {"Q3", "3", "4"},
            {"Q4", "4", "3"},
            {"Q5", "5", "2"}
    };

    private static final String[][] EVENT_CARDS = {
            {"Plague", "1"},
            {"Queen's favor", "2"},
            {"Prosperity", "2"}
    };

    private ArrayList<Card> adventureDeck = new ArrayList<>();;
    private ArrayList<Card> eventDeck = new ArrayList<>();;
    private ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;

    public Game() {
        //initialize decks
        //adventure deck
        for (int i = 0; i < 10; i++) {
            createFoeCards(FOE_CARDS[i][0], Integer.parseInt(FOE_CARDS[i][1]), Integer.parseInt(FOE_CARDS[i][2]));
        }

        for (int i = 0; i < 6; i++) {
            createWeaponCards(WEAPON_CARDS[i][0], Integer.parseInt(WEAPON_CARDS[i][1]), Integer.parseInt(WEAPON_CARDS[i][2]));
        }

        //event deck
        for (int i = 0; i < 4; i++) {
            createQuestCards(QUEST_CARDS[i][0], Integer.parseInt(QUEST_CARDS[i][1]), Integer.parseInt(QUEST_CARDS[i][2]));
        }

        for (int i = 0; i < 3; i++) {
            createEventCards(EVENT_CARDS[i][0], Integer.parseInt(EVENT_CARDS[i][1]));
        }

        Collections.shuffle(adventureDeck);
        Collections.shuffle(eventDeck);

        //setup players
        for (int i = 0; i < 4; i++) {
            players.add(new Player("P" + (i+1)));
        }

        currentPlayer = players.get(0);
    }

    public ArrayList<Card> getAdventureDeck() {
        return adventureDeck;
    }

    public ArrayList<Card> getEventDeck() {
        return eventDeck;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void dealCards() {
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 12; k++) {
                players.get(i).addCard(adventureDeck.removeLast());
            }
        }
    }

    public Card drawEventCard() {
        return eventDeck.removeLast();
    }

    public void nextTurn() {
        if (currentPlayer.getName().equals("P4")) {
            currentPlayer = players.get(0);
            return;
        }

        currentPlayer = players.get(Character.getNumericValue(currentPlayer.getName().charAt(1)));
    }

    public ArrayList<Player> checkWinner() {
        ArrayList<Player> winners = new ArrayList<>();

        for (Player p : players) {
            if (p.getShields() >= 7) {
                winners.add(p);
            }
        }

        return winners;
    }

    public void performEventAction(Card c) {

        if (c.getCard().equals("Plague")) {
            currentPlayer.removeShields();
        } else if (c.getCard().equals("Queen’s favor")) {
            drawAdventureCards(currentPlayer, 2);
        } else if (c.getCard().equals("Prosperity")) {
            for (int i = 0; i < 4; i++) {
                drawAdventureCards(players.get(i), 2);
            }
        }
    }

    public void drawAdventureCards(Player p, int amount) {
        for (int i = 0; i < amount; i++) {
            p.addCard(adventureDeck.removeLast());
        }
    }

    public void trimHand() {
        UI ui = new UI();
        Scanner scanner = new Scanner(System.in);
        String index = "";

        for (int i = 0; i < 4; i++) {
            if (players.get(i).getCards().size() <= 12) {
                continue;
            }

            while (players.get(i).getCards().size() > 12) {
                index = ui.trimPrompt(players.get(i), scanner);
                players.get(i).removeCard(Integer.parseInt(index));
            }
        }
    }

    private void createFoeCards(String face, int value, int amount) {
        for (int k = 0; k < amount; k++) {
            adventureDeck.add(new Foe(face, value));
        }
    }

    private void createWeaponCards(String face, int value, int amount) {
        for (int k = 0; k < amount; k++) {
            adventureDeck.add(new Weapon(face, value));
        }
    }

    private void createQuestCards(String face, int value, int amount) {
        for (int k = 0; k < amount; k++) {
            eventDeck.add(new Quest(face, value));
        }
    }

    private void createEventCards(String face, int amount) {
        for (int k = 0; k < amount; k++) {
            eventDeck.add(new Event(face));
        }
    }
}
