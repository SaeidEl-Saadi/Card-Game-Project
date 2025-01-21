package com.game;

import java.util.ArrayList;

public class Player {

    private ArrayList<Card> cards = new ArrayList<>();
    private String name;
    private int shields = 0;
    private boolean eligible = true;

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

    public boolean getEligible() {
        return eligible;
    }

    public void addCard(Card e) {
        cards.add(e);
        cards.sort(new CardComparator());
    }

    public void giveShields(int amount) {
        shields += amount;
    }

    public void removeShields() {
        if (shields < 2) {
            shields = 0;
            return;
        }

        shields -= 2;
    }

    public Card removeCard(int index) {
        return cards.remove(index - 1);
    }

    public int foeNum() {
        int total = 0;

        for (Card c : cards) {
            if (c instanceof Foe) {
                total += 1;
            }
        }

        return total;
    }

    public int weaponNum() {
        int total = 0;

        for (Card c : cards) {
            if (c instanceof Weapon) {
                total += 1;
            }
        }

        return total;
    }

    public void setEligible(boolean value) {
        eligible = value;
    }

    @Override
    public String toString() {
        return name;
    }
}
