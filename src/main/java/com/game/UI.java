package com.game;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    public UI() {}

    public void displayWinners(PrintWriter writer, Game g) {
        writer.print("Game over. Winner(s):\n");
        for (Player p : g.checkWinner()) {
            writer.print(p.getName() + " with " + p.getShields() + " shields\n");
        }
    }

    public void displayDrawnCard(Card c) {
        System.out.print("Drawn Card: " + c.getCard() + "\n");
    }

    public void displayHand(Player p) {
        if (p.getCards().size() == 0) {
            System.out.print(p.getName() + "'s hand is empty\n");
            return;
        }

        System.out.print(p.getName() + "'s hand:\n");
        for (int i = 0; i < p.getCards().size(); i++) {
            System.out.print((i + 1) + ". " + p.getCards().get(i) + "\n");
        }
    }

    public void hotSeatPrompt(Game g) {
        for (int i = 0; i < 20; i++) {
            System.out.print("\n");
        }
        System.out.print(g.getCurrentPlayer().getName() + "'s turn has ended. Press enter for next turn...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.print("\n");
    }

    public String trimPrompt(Player p, Scanner s) {
        System.out.print(p.getName() + " needs to trim their hand (press enter to show hand):\n");
        s.nextLine();

        displayHand(p);

        return s.nextLine();
    }

    public void sponsorPrompt(Player p) {
        System.out.print(p.getName() + " do you want to sponsor this quest?\n1. Yes\n2. No\n");
    }

    public String promptStage(Game g, int stage) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose Card to add to stage " + stage + " of " + g.getCurrentQuest().getStageNum() + ":\n");
        displayHand(g.getSponsor());

        System.out.print("Current cards in stage:\n");
        for (int i = 0; i < g.getCurrentQuest().getStage(stage).size(); i++) {
            System.out.print((i + 1) + ". " + g.getCurrentQuest().getStage(stage).get(i) + "\n");
        }

        return scanner.nextLine();
    }

    public void emptyError() {
        System.out.print("A stage cannot be empty\n");
    }

    public void insufficientValue() {
        System.out.print("Insufficient value for this stage\n");
    }

    public void tooManyFoes() {
        System.out.print("No more than 1 foe per stage\n");
    }

    public void needFoe() {
        System.out.print("The stage needs a foe\n");
    }

    public void containsDuplicates() {
        System.out.print("No repeated weapons per stage\n");
    }

    public void promptParticipant(Player p) {
        System.out.print(p.getName() + " would you like to participate in the Quest with " + Game.QuestLine.getCurrentQuest().getStageNum() + " stages:\n");
        System.out.print("1. Yes\n2. No\n");
    }

    public void attackPrompt(Player p, ArrayList<Card> lineUp) {
        System.out.print(p.getName() + " choose weapon cards to attack with:\n");
        displayHand(p);

        System.out.print("\nCurrent cards in line up:\n");
        for (int i = 0; i < lineUp.size(); i++) {
            System.out.print((i+1) + ". " + lineUp.get(i) + "\n");
        }
    }

    public void noFoesAllowed() {
        System.out.print("Only weapon cards are allowed in an attack.\n");
    }

    public void emptyAttack() {
        System.out.print("There must be at least 1 weapon card.\n");
    }

    public void repeatedWeapons() {
        System.out.print("No repeated weapons in lineup.\n");
    }
}
