package com.game;

public class Event implements Card {

    private String face;

    public Event(String face) {
        this.face = face;
    }

    @Override
    public String getCard() {
        return face;
    }
}
