import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    @Test
    @DisplayName("Initialize adventure and event decks")
    void RESP_1_test_1() {
        Game game = new Game();

        //check if empty and total count
        assertEquals(100, game.getAdventureDeck().size());
        assertEquals(17, game.getEventDeck().size());

        Map<String, Integer> cards = new HashMap<>();
        cards.put("F5", 0);
        cards.put("F10", 0);
        cards.put("F15", 0);
        cards.put("F20", 0);
        cards.put("F25", 0);
        cards.put("F30", 0);
        cards.put("F35", 0);
        cards.put("F40", 0);
        cards.put("F50", 0);
        cards.put("F70", 0);
        cards.put("D5", 0);
        cards.put("H10", 0);
        cards.put("S10", 0);
        cards.put("B15", 0);
        cards.put("L20", 0);
        cards.put("E30", 0);
        cards.put("Q2", 0);
        cards.put("Q3", 0);
        cards.put("Q4", 0);
        cards.put("Q5", 0);
        cards.put("Plague", 0);
        cards.put("Queen's favor", 0);
        cards.put("Prosperity", 0);
        //check specific cards (adventure deck)
        for (int i = 0; i < 100; i++) {
            String card = game.getAdventureDeck().get(i).getCard();
            cards.put(card, cards.get(card) + 1);
        }

        //event deck
        for (int i = 0; i < 17; i++) {
            String card = game.getEventDeck().get(i).getCard();
            cards.put(card, cards.get(card) + 1);
        }

        //check right amounts
        //foes
        assertEquals(8, cards.get("F5"));
        assertEquals(7, cards.get("F10"));
        assertEquals(8, cards.get("F15"));
        assertEquals(7, cards.get("F20"));
        assertEquals(7, cards.get("F25"));
        assertEquals(4, cards.get("F30"));
        assertEquals(4, cards.get("F35"));
        assertEquals(2, cards.get("F40"));
        assertEquals(2, cards.get("F50"));
        assertEquals(1, cards.get("F70"));

        //weapons
        assertEquals(6, cards.get("D5"));
        assertEquals(12, cards.get("H10"));
        assertEquals(16, cards.get("S10"));
        assertEquals(8, cards.get("B15"));
        assertEquals(6, cards.get("L20"));
        assertEquals(2, cards.get("E30"));

        //event deck
        assertEquals(3, cards.get("Q2"));
        assertEquals(4, cards.get("Q3"));
        assertEquals(3, cards.get("Q4"));
        assertEquals(2, cards.get("Q5"));
        assertEquals(1, cards.get("Plague"));
        assertEquals(2, cards.get("Queen's favor"));
        assertEquals(2, cards.get("Prosperity"));
    }

    @Test
    @DisplayName("Deal 12 cards to each player")
    void RESP_2_test_1() {
        Game game = new Game();
        game.dealCards();

        assertEquals(100 - (game.getPlayers().size() * 12), game.getAdventureDeck().size());
        for (int i = 0; i < game.getPlayers().size(); i++) {
            assertEquals(12, game.getPlayers().get(i).getCards().size());
        }
    }

    @Test
    @DisplayName("All players take a turn")
    void RESP_3_test_1() {
        Game game = new Game();
        game.dealCards();
        int eventDeckSize = game.getEventDeck().size();

        for (int i = 0; i <= 3; i++) {
            assertEquals(game.getPlayers().get(i).getName(), game.getCurrentPlayer().getName());
            Card c = game.drawEventCard();
            assertEquals(eventDeckSize - (i + 1), game.getEventDeck().size());
            assertTrue(c instanceof Event || c instanceof Quest);
            game.nextTurn();
        }

        //should be back to first player
        assertEquals(game.getPlayers().get(0).getName(), game.getCurrentPlayer().getName());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    @DisplayName("Determine winner(s)")
    void RESP_3_test_2(int winnerAmount) {
        Game game = new Game();
        game.dealCards();

        //give winning amount of shields to {winnerAmount} players
        for (int i = 0; i < winnerAmount; i++) {
            game.getPlayers().get(i).giveShields(7);
        }

        assertEquals(winnerAmount, game.checkWinner().size());
    }

    @Test
    @DisplayName("Display winners")
    void RESP_4_test_1() {
        Game game = new Game();
        game.dealCards();
        game.getPlayers().get(0).giveShields(7);
        game.getPlayers().get(2).giveShields(7);

        UI ui = new UI();

        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(outputstream);
        ui.displayWinners(pw, game);
        pw.flush();
        String output = outputstream.toString().trim();

        String expectedOutput = "Game over. Winner(s):\n";
        for (Player p : game.checkWinner()) {
            expectedOutput += p.getName() + " with " + p.getShields() + " shields\n";
        }

        assertEquals(expectedOutput.trim(), output);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Plague", "Queen's favor", "Prosperity"})
    @DisplayName("Display drawn event card")
    void RESP_5_test_1(String eventName) {
        Game game = new Game();
        UI ui = new UI();
        game.getEventDeck().addLast(new Event(eventName));

        PrintStream previous = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Card drawnCard = game.drawEventCard();
        ui.displayDrawnCard(drawnCard);

        String output = outputStream.toString().trim();
        String expected = "Drawn Card: " + drawnCard.getCard() + "\n";
        assertEquals(expected.trim(), output);

        System.setOut(previous);
    }

    @Test
    @DisplayName("Perform event actions")
    void RESP_5_test_2() {
        Game game = new Game();

        //test Plague
        game.getEventDeck().addLast(new Event("Plague"));
        game.getPlayers().get(0).giveShields(3);
        game.performEventAction(game.drawEventCard());
        assertEquals(1, game.getCurrentPlayer().getShields());

        game.getEventDeck().addLast(new Event("Plague"));
        game.performEventAction(game.drawEventCard());
        assertEquals(0, game.getCurrentPlayer().getShields());

        //test Queen’s favor
        game.getEventDeck().addLast(new Event("Queen’s favor"));
        game.performEventAction(game.drawEventCard());
        assertEquals(2, game.getCurrentPlayer().getCards().size());
        game.getCurrentPlayer().getCards().clear();

        //test Prosperity
        game.getEventDeck().addLast(new Event("Prosperity"));
        game.performEventAction(game.drawEventCard());
        for (int i = 0; i < 4; i++) {
            assertEquals(2, game.getPlayers().get(i).getCards().size());
        }
    }

    @Test
    @DisplayName("Order hand")
    void RESP_6_test_1() {
        Game game = new Game();

        Weapon first = new Weapon("H10", 10);
        Weapon second = new Weapon("D5", 5);
        Weapon third = new Weapon("S10", 10);
        Foe fourth = new Foe("F15", 15);
        Foe fifth = new Foe("F50", 50);

        game.getCurrentPlayer().addCard(first);
        game.getCurrentPlayer().addCard(second);
        game.getCurrentPlayer().addCard(third);
        game.getCurrentPlayer().addCard(fourth);
        game.getCurrentPlayer().addCard(fifth);

        assertEquals("F15", game.getCurrentPlayer().getCards().get(0).getCard());
        assertEquals("F50", game.getCurrentPlayer().getCards().get(1).getCard());
        assertEquals("D5", game.getCurrentPlayer().getCards().get(2).getCard());
        assertEquals("S10", game.getCurrentPlayer().getCards().get(3).getCard());
        assertEquals("H10", game.getCurrentPlayer().getCards().get(4).getCard());
    }

    @Test
    @DisplayName("Display player's hand")
    void RESP_6_test_2() {
        Game game = new Game();
        UI ui = new UI();
        game.dealCards();

        PrintStream previous = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        ui.displayHand(game.getCurrentPlayer());
        String output = outputStream.toString().trim();

        String expected = game.getCurrentPlayer().getName() + "'s hand:";
        for (int i = 0; i < 12; i++) {
            expected += "\n" + (i + 1) + ". " + game.getCurrentPlayer().getCards().get(i);
        }

        System.setOut(previous);
        assertEquals(expected.trim(), output);
    }

    @Test
    @DisplayName("Players swapping turns")
    void RESP_6_test_3() {
        Game game = new Game();
        UI ui = new UI();
        game.dealCards();

        String input = "\n";
        ByteArrayInputStream userInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(userInput);

        PrintStream previous = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        ui.hotSeatPrompt(game);
        Player p = game.getCurrentPlayer();
        game.nextTurn();
        ui.displayHand(game.getCurrentPlayer());
        String output = outputStream.toString();

        String expected = "";
        for (int i = 0; i < 20; i++) {
            expected += "\n";
        }
        expected += p.getName() + "'s turn has ended. Press enter for next turn...\n";
        expected += game.getCurrentPlayer().getName() + "'s hand:";
        for (int i = 0; i < 12; i++) {
            expected += "\n" + (i + 1) + ". " + game.getCurrentPlayer().getCards().get(i);
        }
        expected += "\n";

        System.setOut(previous);
        assertEquals(expected, output);
    }

}
