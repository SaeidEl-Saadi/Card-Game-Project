import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
}
