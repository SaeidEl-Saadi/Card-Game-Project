import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("Player trims their hand")
    void RESP_7_test_1() {
        Game game = new Game();
        game.dealCards();

        game.drawAdventureCards(game.getPlayers().get(0), 2);
        game.drawAdventureCards(game.getPlayers().get(1), 1);

        String input = "\n3\n\n4\n\n1\n";
        InputStream previousIn = System.in;
        ByteArrayInputStream userInput = new ByteArrayInputStream(input.getBytes());
        System.setIn(userInput);

        game.trimHand();

        for (int i = 0; i < 4; i++) {
            assertEquals(12, game.getPlayers().get(i).getCards().size());
        }

        System.setIn(previousIn);
    }

    @Test
    @DisplayName("Trim outputs")
    void RESP_7_test_2() {
        Game game = new Game();
        UI ui = new UI();
        game.dealCards();

        PrintStream previous = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        InputStream previousIn = System.in;
        String input = "\n2\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        game.drawAdventureCards(game.getPlayers().get(0), 1);
        String expected = game.getPlayers().get(0).getName() + " needs to trim their hand (press enter to show hand):\n" +
                game.getPlayers().get(0).getName() + "'s hand:\n";
        for (int i = 0; i < 13; i++) {
            expected += (i+1) + ". " + game.getPlayers().get(0).getCards().get(i) + "\n";
        }

        game.trimHand();
        String output = outputStream.toString();

        assertEquals(expected, output);
        System.setOut(previous);
        System.setIn(previousIn);
    }

    @Test
    @DisplayName("Find sponsor (UI outputs)")
    void RESP_8_test_1() {
        Game game = new Game();
        UI ui = new UI();

        PrintStream previous = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        InputStream previousIn = System.in;
        String input = "1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ui.sponsorPrompt(game.getPlayers().get(0));

        String output = outputStream.toString();

        String expected = game.getPlayers().get(0).getName() + " do you want to sponsor this quest?\n" +
                "1. Yes\n" +
                "2. No\n";


        assertEquals(expected, output);
        System.setOut(previous);
        System.setIn(previousIn);
    }

    @Test
    @DisplayName("Find sponsor")
    void RESP_8_test_2() {
        Game game = new Game();

        InputStream previousIn = System.in;
        String input = "1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        game.setQuest(new Quest("Q2", 2));
        game.getCurrentPlayer().getCards().add(new Foe("F15", 15));
        game.getCurrentPlayer().getCards().add(new Foe("F15", 20));
        game.setSponsor(game.findSponsor());

        assertEquals(game.getPlayers().get(0), game.getSponsor());
        System.setIn(previousIn);
        Game.QuestLine.resetQuest();
    }

    @Test
    @DisplayName("Quest setup UI outputs")
    void RESP_9_test_1() {
        Game game = new Game();
        UI ui = new UI();
        game.drawAdventureCards(game.getCurrentPlayer(), 1);
        game.setQuest(new Quest("Q3", 3));

        PrintStream previous = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        InputStream previousIn = System.in;
        String input = "1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        game.setSponsor(game.getCurrentPlayer());
        String answer = ui.promptStage(game, 1);

        String output = outputStream.toString();

        String expected = "Choose Card to add to stage 1 of 3:\n" +
                "P1's hand:\n" +
                "1. " + game.getCurrentPlayer().getCards().get(0) +
                "\nCurrent cards in stage:\n";

        assertEquals(expected, output);
        assertEquals("1", answer);
        System.setOut(previous);
        System.setIn(previousIn);
        Game.QuestLine.resetQuest();
    }

    @Test
    @DisplayName("Valid quest setup")
    void RESP_9_test_2() {
        Game game = new Game();
        UI ui = new UI();
        Card c1 = new Foe("F20", 20);
        Card c2 = new Foe("F70", 70);
        game.getCurrentPlayer().addCard(c1);
        game.getCurrentPlayer().addCard(c2);
        game.setSponsor(game.getCurrentPlayer());
        game.setQuest(new Quest("Q2", 2));

        InputStream previousIn = System.in;
        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setUpQuest(1);

        input = "quit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setUpQuest(1);

        input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setUpQuest(2);

        input = "quit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setUpQuest(2);

        assertEquals(c1, game.getCurrentQuest().getStage(1).get(0));
        assertEquals(c2, game.getCurrentQuest().getStage(2).get(0));
        System.setIn(previousIn);
        Game.QuestLine.resetQuest();
    }

    @Test
    @DisplayName("Check quest setup conditions")
    void RESP_9_test_3() {
        Game game = new Game();
        UI ui = new UI();

        game.setSponsor(game.getCurrentPlayer());
        game.setQuest(new Quest("Q2", 2));

        //more than 1 foe per stage
        game.getCurrentQuest().getStage(1).add(new Foe("F10", 10));
        game.getCurrentQuest().getStage(1).add(new Foe("F15", 15));
        assertFalse(game.checkValidity(1));

        game.getCurrentQuest().getStage(1).clear();

        //duplicate weapon cards
        game.getCurrentQuest().getStage(1).add(new Foe("F15", 15));
        game.getCurrentQuest().getStage(1).add(new Weapon("D5", 5));
        game.getCurrentQuest().getStage(1).add(new Weapon("D5", 5));
        assertFalse(game.checkValidity(1));

        //empty stage
        game.getCurrentQuest().getStage(1).clear();
        assertFalse(game.checkValidity(1));

        //stage is less value than previous stage
        game.getCurrentQuest().getStage(1).add(new Foe("F15", 15));
        game.getCurrentQuest().getStage(2).add(new Foe("F10", 10));
        assertFalse(game.checkValidity(2));

        game.getCurrentQuest().getStage(1).clear();
        game.getCurrentQuest().getStage(2).clear();

        //stage has no foe
        game.getCurrentQuest().getStage(1).add(new Weapon("D5", 5));
        assertFalse(game.checkValidity(1));

        game.getCurrentQuest().getStage(1).clear();

        //valid stages
        game.getCurrentQuest().getStage(1).add(new Foe("F10", 10));
        game.getCurrentQuest().getStage(1).add(new Weapon("D5", 5));
        game.getCurrentQuest().getStage(2).add(new Foe("F15", 10));
        game.getCurrentQuest().getStage(2).add(new Weapon("E30", 30));

        assertTrue(game.checkValidity(1));
        assertTrue(game.checkValidity(2));
        Game.QuestLine.resetQuest();
    }

    @Test
    @DisplayName("Choose participants")
    void RESP_10_test_1() {
        Game game = new Game();
        UI ui = new UI();
        game.dealCards();

        Game.QuestLine.setSponsor(game.getPlayers().get(1));
        Game.QuestLine.setQuest(new Quest("Q2", 2));

        for (Player p : game.getPlayers()) {
            p.getCards().removeLast();
        }

        InputStream previousIn = System.in;
        String input = "1\n1\n2\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        game.chooseParticipants();

        assertEquals(12,game.getPlayers().get(0).getCards().size());
        assertEquals(12,game.getPlayers().get(2).getCards().size());
        assertEquals(11,game.getPlayers().get(3).getCards().size());
        assertEquals(2, Game.QuestLine.getParticipents().size());
        assertEquals(Game.QuestLine.getParticipents().get(0), game.getPlayers().get(0));
        assertEquals(Game.QuestLine.getParticipents().get(1), game.getPlayers().get(2));
        System.setIn(previousIn);
        Game.QuestLine.resetQuest();
    }

    @Test
    @DisplayName("Set up valid attack")
    void RESP_11_test_1() {
        Game game = new Game();
        UI ui = new UI();

        //setup quest
        game.setSponsor(game.getCurrentPlayer());
        game.setQuest(new Quest("Q2", 2));
        Game.QuestLine.addParticipant(game.getPlayers().get(1));
        Game.QuestLine.addParticipant(game.getPlayers().get(2));
        Game.QuestLine.getCurrentQuest().getStage(1).add(new Foe("F10", 10));
        Game.QuestLine.getCurrentQuest().getStage(2).add(new Foe("F15", 15));

        //setup cards in hands
        game.getPlayers().get(1).addCard(new Weapon("L20", 20));
        game.getPlayers().get(1).addCard(new Weapon("E30", 30));
        game.getPlayers().get(2).addCard(new Weapon("E30", 30));
        game.getPlayers().get(2).addCard(new Weapon("E30", 30));

        InputStream previousIn = System.in;
        String input = "1\n1\nquit\n1\n1\nquit\n1\nquit\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        game.setUpAttacks();

        assertEquals(20, Game.QuestLine.getSpecificAttack(0).get(0).getValue());
        assertEquals(30, Game.QuestLine.getSpecificAttack(0).get(1).getValue());
        assertEquals(30, Game.QuestLine.getSpecificAttack(1).get(0).getValue());
        System.setIn(previousIn);
        Game.QuestLine.resetQuest();
    }

    @Test
    @DisplayName("JP Scenario")
    void A_TEST_JP_Scenario() {
        Game game = new Game();
        UI ui = new UI();
        game.dealCards();

        //rig P1
        game.getPlayers().get(0).getCards().clear();
        game.getPlayers().get(0).addCard(new Foe("F5", 5));
        game.getPlayers().get(0).addCard(new Foe("F5", 5));
        game.getPlayers().get(0).addCard(new Foe("F15", 15));
        game.getPlayers().get(0).addCard(new Foe("F15", 15));
        game.getPlayers().get(0).addCard(new Weapon("D5", 5));
        game.getPlayers().get(0).addCard(new Weapon("S10", 10));
        game.getPlayers().get(0).addCard(new Weapon("S10", 10));
        game.getPlayers().get(0).addCard(new Weapon("H10", 10));
        game.getPlayers().get(0).addCard(new Weapon("H10", 10));
        game.getPlayers().get(0).addCard(new Weapon("B15", 15));
        game.getPlayers().get(0).addCard(new Weapon("B15", 15));
        game.getPlayers().get(0).addCard(new Weapon("L20", 20));

        //rig P2
        game.getPlayers().get(1).getCards().clear();
        game.getPlayers().get(1).addCard(new Foe("F5", 5));
        game.getPlayers().get(1).addCard(new Foe("F5", 5));
        game.getPlayers().get(1).addCard(new Foe("F15", 15));
        game.getPlayers().get(1).addCard(new Foe("F15", 15));
        game.getPlayers().get(1).addCard(new Foe("F40", 40));
        game.getPlayers().get(1).addCard(new Weapon("D5", 5));
        game.getPlayers().get(1).addCard(new Weapon("S10", 10));
        game.getPlayers().get(1).addCard(new Weapon("H10", 10));
        game.getPlayers().get(1).addCard(new Weapon("H10", 10));
        game.getPlayers().get(1).addCard(new Weapon("B15", 15));
        game.getPlayers().get(1).addCard(new Weapon("B15", 15));
        game.getPlayers().get(1).addCard(new Weapon("E30", 30));

        //rig P3
        game.getPlayers().get(2).getCards().clear();
        game.getPlayers().get(2).addCard(new Foe("F5", 5));
        game.getPlayers().get(2).addCard(new Foe("F5", 5));
        game.getPlayers().get(2).addCard(new Foe("F5", 5));
        game.getPlayers().get(2).addCard(new Foe("F15", 15));
        game.getPlayers().get(2).addCard(new Weapon("D5", 5));
        game.getPlayers().get(2).addCard(new Weapon("S10", 10));
        game.getPlayers().get(2).addCard(new Weapon("S10", 10));
        game.getPlayers().get(2).addCard(new Weapon("S10", 10));
        game.getPlayers().get(2).addCard(new Weapon("H10", 10));
        game.getPlayers().get(2).addCard(new Weapon("H10", 10));
        game.getPlayers().get(2).addCard(new Weapon("B15", 15));
        game.getPlayers().get(2).addCard(new Weapon("L20", 20));

        //rig P4
        game.getPlayers().get(3).getCards().clear();
        game.getPlayers().get(3).addCard(new Foe("F5", 5));
        game.getPlayers().get(3).addCard(new Foe("F15", 5));
        game.getPlayers().get(3).addCard(new Foe("F15", 5));
        game.getPlayers().get(3).addCard(new Foe("F40", 40));
        game.getPlayers().get(3).addCard(new Weapon("D5", 5));
        game.getPlayers().get(3).addCard(new Weapon("D5", 5));
        game.getPlayers().get(3).addCard(new Weapon("S10", 10));
        game.getPlayers().get(3).addCard(new Weapon("H10", 10));
        game.getPlayers().get(3).addCard(new Weapon("H10", 10));
        game.getPlayers().get(3).addCard(new Weapon("B15", 15));
        game.getPlayers().get(3).addCard(new Weapon("L20", 20));
        game.getPlayers().get(3).addCard(new Weapon("E30", 30));

        //P1 draws Q4
        game.drawEventCard();
        Card eventCardDrawn = new Quest("Q4", 4);
        game.setQuest((Quest) eventCardDrawn);

        //setup rigged input
        InputStream previousIn = System.in;
        String input = "2\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //find sponsor (P2)
        game.setSponsor(game.findSponsor());


        input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        //build stage 1
        game.setUpQuest(1);
        input = "7\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setUpQuest(1);
        input = "quit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setUpQuest(1);

        input = "2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        //build stage 2
        game.setUpQuest(2);
        input = "5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setUpQuest(2);
        input = "quit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setUpQuest(2);

        input = "2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        //build stage 3
        game.setUpQuest(3);
        input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        //build stage 3
        game.setUpQuest(3);
        input = "4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        //build stage 3
        game.setUpQuest(3);
        input = "quit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        //build stage 3
        game.setUpQuest(3);

        input = "2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        //build stage 4
        game.setUpQuest(4);
        input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        //build stage 4
        game.setUpQuest(4);
        input = "quit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        //build stage 4
        game.setUpQuest(4);


        //rig draws
        game.getAdventureDeck().addLast(new Weapon("B15", 15));
        game.getAdventureDeck().addLast(new Weapon("S10", 10));
        game.getAdventureDeck().addLast(new Foe("F30", 30));

        //stage 1
        input = "1\n1\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        //choose participants
        game.chooseParticipants();

        input = "\n1\n\n1\n\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.trimHand();

        input = "5\n5\nquit\n5\n4\nquit\n4\n7\nquit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setUpAttacks();

        game.resolveAttacks();

        //rig draws
        game.getAdventureDeck().addLast(new Weapon("L20", 20));
        game.getAdventureDeck().addLast(new Weapon("L20", 20));
        game.getAdventureDeck().addLast(new Foe("F10", 10));

        //stage 2
        input = "1\n1\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        //choose participants
        game.chooseParticipants();

        input = "7\n6\nquit\n9\n4\nquit\n6\n6\nquit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setUpAttacks();

        game.resolveAttacks();

        ui.displayHand(game.getPlayers().get(0));
        assertEquals(0, game.getPlayers().get(0).getShields());


        //rig draws
        game.getAdventureDeck().addLast(new Weapon("S10", 10));
        game.getAdventureDeck().addLast(new Weapon("B15", 15));


        //stage 3
        input = "1\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        //choose participants
        game.chooseParticipants();

        input = "9\n6\n4\nquit\n7\n5\n6\nquit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setUpAttacks();

        game.resolveAttacks();

        //rig draws
        game.getAdventureDeck().addLast(new Weapon("L20", 20));
        game.getAdventureDeck().addLast(new Foe("F30", 30));

        //stage 4
        input = "1\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        //choose participants
        game.chooseParticipants();

        input = "7\n6\n6\nquit\n4\n4\n4\n5\nquit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setUpAttacks();

        game.resolveAttacks();

        ui.displayHand(game.getPlayers().get(2));
        assertEquals(0, game.getPlayers().get(2).getShields());
        assertEquals(4, game.getPlayers().get(3).getShields());

        input = "\n1\n\n1\n\n1\n\n1\n\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.trimHand();

        assertEquals(12, game.getPlayers().get(1).getCards().size());

        System.setIn(previousIn);
        Game.QuestLine.resetQuest();
    }

}
