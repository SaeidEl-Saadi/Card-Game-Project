package com.game;

import io.cucumber.java.en.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameSteps {

    private Game game;
    private UI ui = new UI();
    InputStream previousIn = System.in;
    ArrayList<String> attacks = new ArrayList<>();

    @Given("a new game of quests starts")
    public void a_new_game_of_quests_starts() {
        game = new Game();
    }

    @Given("hands are rigged for A1_scenario")
    public void first_scenario_rig() {
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

        //rig draws
        game.getAdventureDeck().add(new Weapon("L20", 20));
        game.getAdventureDeck().add(new Foe("F30", 30));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Weapon("B15", 15));
        game.getAdventureDeck().add(new Weapon("L20", 20));
        game.getAdventureDeck().add(new Weapon("L20", 20));
        game.getAdventureDeck().add(new Foe("F10", 10));
        game.getAdventureDeck().add(new Weapon("B15", 15));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Foe("F30", 30));
    }

    @Given("hands are rigged for 2winner_game_2winner_quest scenario")
    public void secondScenarioRig() {
        game.dealCards();

        //rig P1
        game.getPlayers().get(0).getCards().clear();
        game.getPlayers().get(0).addCard(new Foe("F10", 10));
        game.getPlayers().get(0).addCard(new Foe("F15", 15));
        game.getPlayers().get(0).addCard(new Foe("F15", 15));
        game.getPlayers().get(0).addCard(new Foe("F20", 20));
        game.getPlayers().get(0).addCard(new Weapon("D5", 5));
        game.getPlayers().get(0).addCard(new Weapon("D5", 5));
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
        game.getPlayers().get(1).addCard(new Weapon("D5", 5));
        game.getPlayers().get(3).addCard(new Weapon("S10", 10));
        game.getPlayers().get(1).addCard(new Weapon("S10", 10));
        game.getPlayers().get(1).addCard(new Weapon("S10", 10));
        game.getPlayers().get(1).addCard(new Weapon("H10", 10));
        game.getPlayers().get(1).addCard(new Weapon("H10", 10));
        game.getPlayers().get(1).addCard(new Weapon("B15", 15));
        game.getPlayers().get(1).addCard(new Weapon("B15", 15));
        game.getPlayers().get(1).addCard(new Weapon("E30", 30));

        //rig P3
        game.getPlayers().get(2).getCards().clear();
        game.getPlayers().get(2).addCard(new Foe("F5", 5));
        game.getPlayers().get(2).addCard(new Foe("F10", 10));
        game.getPlayers().get(2).addCard(new Foe("F15", 15));
        game.getPlayers().get(2).addCard(new Foe("F20", 20));
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
        game.getPlayers().get(3).addCard(new Weapon("D5", 5));
        game.getPlayers().get(3).addCard(new Weapon("D5", 5));
        game.getPlayers().get(3).addCard(new Weapon("S10", 10));
        game.getPlayers().get(3).addCard(new Weapon("S10", 10));
        game.getPlayers().get(3).addCard(new Weapon("H10", 10));
        game.getPlayers().get(3).addCard(new Weapon("H10", 10));
        game.getPlayers().get(3).addCard(new Weapon("B15", 15));
        game.getPlayers().get(3).addCard(new Weapon("L20", 20));
        game.getPlayers().get(3).addCard(new Weapon("E30", 30));

        //rig draws
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Weapon("B15", 15));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Weapon("B15", 15));
    }

    @Given("hands are rigged for 1winner_game_with_events scenario")
    public void thirdScenarioRig(){
        game.dealCards();

        //rig P1
        game.getPlayers().get(0).getCards().clear();
        game.getPlayers().get(0).addCard(new Foe("F5", 5));
        game.getPlayers().get(0).addCard(new Foe("F5", 5));
        game.getPlayers().get(0).addCard(new Foe("F5", 5));
        game.getPlayers().get(0).addCard(new Foe("F10", 10));
        game.getPlayers().get(0).addCard(new Foe("F10", 10));
        game.getPlayers().get(0).addCard(new Foe("F15", 15));
        game.getPlayers().get(0).addCard(new Foe("F15", 15));
        game.getPlayers().get(0).addCard(new Foe("F20", 20));
        game.getPlayers().get(0).addCard(new Weapon("S10", 10));
        game.getPlayers().get(0).addCard(new Weapon("H10", 10));
        game.getPlayers().get(0).addCard(new Weapon("H10", 10));
        game.getPlayers().get(0).addCard(new Weapon("B15", 15));

        //rig P2
        game.getPlayers().get(1).getCards().clear();
        game.getPlayers().get(1).addCard(new Foe("F5", 5));
        game.getPlayers().get(1).addCard(new Foe("F5", 5));
        game.getPlayers().get(1).addCard(new Foe("F15", 15));
        game.getPlayers().get(1).addCard(new Weapon("D5", 5));
        game.getPlayers().get(3).addCard(new Weapon("S10", 10));
        game.getPlayers().get(1).addCard(new Weapon("S10", 10));
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
        game.getPlayers().get(2).addCard(new Foe("F15", 15));
        game.getPlayers().get(2).addCard(new Foe("F20", 20));
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
        game.getPlayers().get(3).addCard(new Weapon("D5", 5));
        game.getPlayers().get(3).addCard(new Weapon("D5", 5));
        game.getPlayers().get(3).addCard(new Weapon("S10", 10));
        game.getPlayers().get(3).addCard(new Weapon("S10", 10));
        game.getPlayers().get(3).addCard(new Weapon("H10", 10));
        game.getPlayers().get(3).addCard(new Weapon("H10", 10));
        game.getPlayers().get(3).addCard(new Weapon("B15", 15));
        game.getPlayers().get(3).addCard(new Weapon("L20", 20));
        game.getPlayers().get(3).addCard(new Weapon("E30", 30));

        //rig draws
        game.getAdventureDeck().add(new Foe("F30", 30));
        game.getAdventureDeck().add(new Foe("F20", 20));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Weapon("B15", 15));
        game.getAdventureDeck().add(new Weapon("H10", 10));
        game.getAdventureDeck().add(new Weapon("H10", 10));
        game.getAdventureDeck().add(new Weapon("L20", 20));
        game.getAdventureDeck().add(new Weapon("B15", 15));
        game.getAdventureDeck().add(new Weapon("B15", 15));
        game.getAdventureDeck().add(new Weapon("L20", 20));
        game.getAdventureDeck().add(new Weapon("L20", 20));
        game.getAdventureDeck().add(new Weapon("L20", 20));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Weapon("S10", 10));


        //event card rig
        game.getEventDeck().add(new Event("Queen's Favor"));
        game.getEventDeck().add(new Event("Prosperity"));
        game.getEventDeck().add(new Event("Plague"));
        game.getEventDeck().add(new Quest("Q4", 4));
    }

    @Given("hands are rigged for 0_winner_quest")
    public void fourthScenarioRig() {
        game.dealCards();

        //rig P1
        game.getPlayers().get(0).getCards().clear();
        game.getPlayers().get(0).addCard(new Foe("F5", 5));
        game.getPlayers().get(0).addCard(new Foe("F5", 5));
        game.getPlayers().get(0).addCard(new Foe("F5", 5));
        game.getPlayers().get(0).addCard(new Foe("F10", 10));
        game.getPlayers().get(0).addCard(new Foe("F10", 10));
        game.getPlayers().get(0).addCard(new Foe("F15", 15));
        game.getPlayers().get(0).addCard(new Foe("F15", 15));
        game.getPlayers().get(0).addCard(new Foe("F20", 20));
        game.getPlayers().get(0).addCard(new Weapon("S10", 10));
        game.getPlayers().get(0).addCard(new Weapon("H10", 10));
        game.getPlayers().get(0).addCard(new Weapon("H10", 10));
        game.getPlayers().get(0).addCard(new Weapon("B15", 15));
    }

    @When("{string} draws quest card {string}")
    public void drawQuestCard(String player, String card) {
        game.drawEventCard();
        game.getEventDeck().add(new Quest(card, Character.getNumericValue(card.charAt(1))));
        Card eventCardDrawn = game.drawEventCard();
        game.setQuest((Quest) eventCardDrawn);

        assertEquals(card, eventCardDrawn.getCard());
    }

    @When("{string} draws event card {string}")
    public void drawEventCard(String player, String event) {
        if (event.equals("Plague")) {
            int previous = game.getCurrentPlayer().getShields();
            game.performEventAction(game.drawEventCard());
            if (previous < 2) {
                assertEquals(0, game.getCurrentPlayer().getShields());
            } else {
                assertEquals(previous - 2, game.getCurrentPlayer().getShields());
            }
            return;
        }
        Card drawnCard = game.drawEventCard();
        game.performEventAction(drawnCard);
        assertEquals(event, drawnCard.getCard());
    }

    @When("{string} is a sponsor")
    public void questSponsor(String player) {
        String buffer = "";
        Player previous = game.getCurrentPlayer();

        while (true) {
            if (game.getCurrentPlayer().getName().equals(player)) {
                while (true) {
                    if (previous.getName().equals(game.getCurrentPlayer().getName())) {
                        break;
                    }

                    game.nextTurn();
                }
                break;
            }

            if (game.getCurrentPlayer().getEligible()) {
                buffer += "2\n";
            }
            game.nextTurn();
        }

        String input = buffer + "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        game.setSponsor(game.findSponsor());

        assertEquals(player, game.getSponsor().getName());
        assertEquals(previous, game.getCurrentPlayer());
        System.setIn(previousIn);
    }

    @When("{string} builds stage {int} with {string}")
    public void buildQuest(String player, int stage, String cards) {
        String[] arr = cards.split(",");
        String buffer = "";
        Player sponsor = Game.QuestLine.getSponsor();

        for (String s : arr) {
            buffer = getIndex(s, sponsor);

            System.setIn(new ByteArrayInputStream(buffer.getBytes()));
            game.setUpQuest(stage);
            buffer = "";
        }

        buffer = "quit\n";
        System.setIn(new ByteArrayInputStream(buffer.getBytes()));
        game.setUpQuest(stage);

        System.setIn(previousIn);
    }

    @When("{string} participate for stage {int}")
    public void chooseParticipants(String players, int stage) {
        String[] arr = players.split(",");
        String buffer = "";

        for (Player p : game.getPlayers()) {
            if (p.getName().equals(game.getSponsor().getName()) || !p.getEligible()) continue;

            boolean flag = false;
            for (String s : arr) {
                if (p.getName().equals(s)) {
                    buffer += "1\n";
                    flag = true;
                    break;
                }
            }

            if (!flag) buffer += "2\n";
        }

        System.setIn(new ByteArrayInputStream(buffer.getBytes()));
        game.chooseParticipants();

        for (int i = 0; i < Game.QuestLine.getParticipents().size(); i++) {
            assertEquals(arr[i], Game.QuestLine.getParticipents().get(i).getName());
        }

        System.setIn(previousIn);
    }

    @When("Nobody participates")
    public void noParticipants() {
        String buffer = "";

        for (Player p : game.getPlayers()) {
            if (p.getName().equals(game.getSponsor().getName()) || !p.getEligible()) continue;

            buffer += "2\n";
        }

        System.setIn(new ByteArrayInputStream(buffer.getBytes()));
        game.chooseParticipants();

        assertTrue(Game.QuestLine.getParticipents().isEmpty());

        System.setIn(previousIn);
    }

    @When("{string} discards {string}")
    public void trimCards(String player, String cards) {
        String[] arr = cards.split(",");
        Player playerDiscard = getPlayer(player);

        String buffer = "";
        for (String s : arr) {
            buffer += "\n" + getIndex(s, playerDiscard);

        }

        System.setIn(new ByteArrayInputStream(buffer.getBytes()));
        game.trimHand(playerDiscard);
        assertEquals(12, playerDiscard.getCards().size());
        System.setIn(previousIn);
    }

    @When("{string} builds attack with {string}")
    public void buildAttack(String player, String cards) {
        String[] arr = cards.split(",");
        Player attacker = getPlayer(player);
        ArrayList<Card> mockCards = new ArrayList<>();

        for (Card c : attacker.getCards()) {
            if (c instanceof Foe) {
                mockCards.add(new Foe(c.getCard(),((Foe) c).getValue()));
                continue;
            }
            mockCards.add(new Weapon(c.getCard(), ((Weapon) c).getValue()));
        }
        mockCards.sort(new CardComparator());

        String input = "";
        for (String s : arr) {
            input += getIndex(s, mockCards);
            mockCards.remove(Integer.parseInt(getIndex(s, mockCards).trim()) - 1);
        }

        input += "quit\n";
        attacks.add(input);
    }

    @When("{string} win stage {int}")
    public void resolveAttacks(String players, int stage) {
        String[] arr = players.split(",");

        String buffer = "";
        for (String s : attacks) {
            buffer += s;
        }

        System.setIn(new ByteArrayInputStream(buffer.getBytes()));
        game.setUpAttacks();
        game.resolveAttacks();
        for (String s : arr) {
            assertTrue(getPlayer(s).getEligible());
        }
        attacks.clear();

        System.setIn(previousIn);
    }

    @When("{string} has no shields and their hand is {string} in that order")
    public void playerShieldAndHand(String player, String hand) {
        String[] arr = hand.split(",");

        assertEquals(0, getPlayer(player).getShields());
        for (int i = 0; i < arr.length; i++) {
            assertEquals(arr[i], getPlayer(player).getCards().get(i).getCard());
        }
    }

    @When("{string} has {int} shields and their hand is {string} in that order")
    public void playerShieldAndHand(String player, int shields, String hand) {
        String[] arr = hand.split(",");

        assertEquals(shields, getPlayer(player).getShields());
        for (int i = 0; i < arr.length; i++) {
            assertEquals(arr[i], getPlayer(player).getCards().get(i).getCard());
        }
    }

    @When("{string} discards {int} random cards")
    public void trimRandom(String player, int amount) {
        Player playerDiscard = getPlayer(player);

        String buffer = "";
        for (int i = 0; i < amount; i++) {
            buffer += "\n1\n";
        }

        System.setIn(new ByteArrayInputStream(buffer.getBytes()));
        game.trimHand(playerDiscard);
        assertEquals(12, playerDiscard.getCards().size());
        System.setIn(previousIn);
    }

    @When("It is {string} turn")
    public void nextTurn(String player) {
        game.nextTurn();

        assertEquals(player, game.getCurrentPlayer().getName());
    }

    @Then("{string} wins the quest with {int} shields and {int} cards")
    public void questWinner(String player, int shields, int cardAmount) {
        assertEquals(shields, getPlayer(player).getShields());
        assertEquals(cardAmount, getPlayer(player).getCards().size());
    }

    @Then("{string} has {int} cards in hand and {int} shields")
    public void cardAmount(String player, int amount, int shieldAmount) {
        assertEquals(amount, getPlayer(player).getCards().size());
        assertEquals(shieldAmount, getPlayer(player).getShields());
    }

    @Then("{string} are winners of the game with {string} shields respectively")
    public void checkWinners(String players, String shields){
        String[] playerList = players.split(",");
        String[] shieldList = shields.split(",");

        for (int i = 0; i < game.checkWinner().size(); i++) {
            assertEquals(playerList[i], game.checkWinner().get(i).getName());
            assertEquals(Integer.parseInt(shieldList[i]), game.checkWinner().get(i).getShields());
        }
    }

    @Then("Quest is over")
    public void noQuest() {
        assertNull(game.getCurrentQuest());
    }

    private String getIndex(String card, Player p) {
        String buffer = "";

        int index = 1;
        for (Card c : p.getCards()) {
            if (card.equals(c.getCard())) {
                buffer += index + "\n";
                break;
            }
            index++;
        }

        return buffer;
    }

    private String getIndex(String card, ArrayList<Card> cards) {
        String buffer = "";

        int index = 1;
        for (Card c : cards) {
            if (card.equals(c.getCard())) {
                buffer += index + "\n";
                break;
            }
            index++;
        }

        return buffer;
    }

    private Player getPlayer(String player) {
        Player playerDiscard = null;

        for (Player p : game.getPlayers()) {
            if (p.getName().equals(player)) {
                playerDiscard = p;
                break;
            }
        }

        return playerDiscard;
    }

}
