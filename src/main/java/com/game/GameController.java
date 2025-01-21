package com.game;

import org.openqa.selenium.By;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
public class GameController {
    Game game = null;
    UI ui = new UI();
    PrintStream previousOut = System.out;
    InputStream previousIn = System.in;
    int sponsorIndex = -1;
    int participateIndex = 0;
    int stageBuildNum = 1;
    ArrayList<Boolean> sponsorAsked = new ArrayList<>();
    ArrayList<Boolean> participating = new ArrayList<>();

    ByteArrayOutputStream tempOutput = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(tempOutput);

    PipedInputStream pipedInputStream;
    PipedOutputStream pipedOutputStream;

    String gameStage = "drawCard";

    public GameController() throws IOException {
        for (int i = 0; i < 4; i++) {
            sponsorAsked.add(false);
            participating.add(false);
        }

        pipedInputStream = new PipedInputStream();
        pipedOutputStream = new PipedOutputStream();

        pipedInputStream.connect(pipedOutputStream);
    }

    @GetMapping("/start")
    public String start() {
        game = new Game();
        game.dealCards();
        game.getEventDeck().remove(game.getEventDeck().size() - 1);
        game.getEventDeck().remove(game.getEventDeck().size() - 1);
        game.getEventDeck().add(new Event("Plague"));
        game.getEventDeck().add(new Quest("Q2", 2));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream);

        System.setOut(out);
        System.out.println("It is P1's turn");

        System.setOut(previousOut);
        return byteArrayOutputStream.toString();
    }

    @GetMapping("/startA1")
    public String startA1() {
        game = new Game();
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

        //event deck rig
        game.getEventDeck().add(new Quest("Q4", 4));

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

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream);

        System.setOut(out);
        System.out.println("It is P1's turn");

        System.setOut(previousOut);
        return byteArrayOutputStream.toString();
    }

    @GetMapping("/second_Scenario")
    public String second_Scenario() {
        game = new Game();
        game.dealCards();

        //rig P1
        game.getPlayers().get(0).getCards().clear();
        game.getPlayers().get(0).addCard(new Foe("F5", 5));
        game.getPlayers().get(0).addCard(new Foe("F5", 5));
        game.getPlayers().get(0).addCard(new Foe("F10", 10));
        game.getPlayers().get(0).addCard(new Foe("F10", 10));
        game.getPlayers().get(0).addCard(new Foe("F15", 15));
        game.getPlayers().get(0).addCard(new Foe("F15", 15));
        game.getPlayers().get(0).addCard(new Weapon("D5", 5));
        game.getPlayers().get(0).addCard(new Weapon("H10", 10));
        game.getPlayers().get(0).addCard(new Weapon("H10", 10));
        game.getPlayers().get(0).addCard(new Weapon("B15", 15));
        game.getPlayers().get(0).addCard(new Weapon("B15", 15));
        game.getPlayers().get(0).addCard(new Weapon("L20", 20));

        //rig P2
        game.getPlayers().get(1).getCards().clear();
        game.getPlayers().get(1).addCard(new Foe("F40", 40));
        game.getPlayers().get(1).addCard(new Foe("F50", 50));
        game.getPlayers().get(1).addCard(new Weapon("H10", 10));
        game.getPlayers().get(1).addCard(new Weapon("H10", 10));
        game.getPlayers().get(1).addCard(new Weapon("S10", 10));
        game.getPlayers().get(1).addCard(new Weapon("S10", 10));
        game.getPlayers().get(1).addCard(new Weapon("S10", 10));
        game.getPlayers().get(1).addCard(new Weapon("B15", 15));
        game.getPlayers().get(1).addCard(new Weapon("B15", 15));
        game.getPlayers().get(1).addCard(new Weapon("L20", 20));
        game.getPlayers().get(1).addCard(new Weapon("L20", 20));
        game.getPlayers().get(1).addCard(new Weapon("E30", 30));

        //rig P3
        game.getPlayers().get(2).getCards().clear();
        game.getPlayers().get(2).addCard(new Foe("F5", 5));
        game.getPlayers().get(2).addCard(new Foe("F5", 5));
        game.getPlayers().get(2).addCard(new Foe("F5", 5));
        game.getPlayers().get(2).addCard(new Foe("F5", 5));
        game.getPlayers().get(2).addCard(new Weapon("D5", 5));
        game.getPlayers().get(2).addCard(new Weapon("D5", 5));
        game.getPlayers().get(2).addCard(new Weapon("D5", 5));
        game.getPlayers().get(2).addCard(new Weapon("H10", 10));
        game.getPlayers().get(2).addCard(new Weapon("H10", 10));
        game.getPlayers().get(2).addCard(new Weapon("H10", 10));
        game.getPlayers().get(2).addCard(new Weapon("H10", 10));
        game.getPlayers().get(2).addCard(new Weapon("H10", 10));

        //rig P4
        game.getPlayers().get(3).getCards().clear();
        game.getPlayers().get(3).addCard(new Foe("F50", 50));
        game.getPlayers().get(3).addCard(new Foe("F70", 70));
        game.getPlayers().get(3).addCard(new Weapon("H10", 10));
        game.getPlayers().get(3).addCard(new Weapon("H10", 10));
        game.getPlayers().get(3).addCard(new Weapon("S10", 10));
        game.getPlayers().get(3).addCard(new Weapon("S10", 10));
        game.getPlayers().get(3).addCard(new Weapon("S10", 10));
        game.getPlayers().get(3).addCard(new Weapon("B15", 15));
        game.getPlayers().get(3).addCard(new Weapon("B15", 15));
        game.getPlayers().get(3).addCard(new Weapon("L20", 20));
        game.getPlayers().get(3).addCard(new Weapon("L20", 20));
        game.getPlayers().get(3).addCard(new Weapon("E30", 30));

        //event deck rig
        game.getEventDeck().add(new Quest("Q3", 3));
        game.getEventDeck().add(new Quest("Q4", 4));

        //rig draws
        game.getAdventureDeck().add(new Weapon("L20", 20));
        game.getAdventureDeck().add(new Weapon("B15", 15));
        game.getAdventureDeck().add(new Weapon("B15", 15));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Foe("F30", 30));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F20", 20));
        game.getAdventureDeck().add(new Foe("F20", 20));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F15", 15));
        game.getAdventureDeck().add(new Foe("F15", 15));
        game.getAdventureDeck().add(new Weapon("D5", 5));
        game.getAdventureDeck().add(new Weapon("D5", 5));
        game.getAdventureDeck().add(new Foe("F30", 30));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F20", 20));
        game.getAdventureDeck().add(new Foe("F20", 20));
        game.getAdventureDeck().add(new Foe("F20", 20));
        game.getAdventureDeck().add(new Foe("F20", 20));
        game.getAdventureDeck().add(new Foe("F15", 15));
        game.getAdventureDeck().add(new Foe("F15", 15));
        game.getAdventureDeck().add(new Foe("F10", 10));
        game.getAdventureDeck().add(new Foe("F5", 5));
        game.getAdventureDeck().add(new Foe("F20", 20));
        game.getAdventureDeck().add(new Foe("F15", 15));
        game.getAdventureDeck().add(new Foe("F15", 15));
        game.getAdventureDeck().add(new Foe("F30", 30));
        game.getAdventureDeck().add(new Foe("F30", 30));
        game.getAdventureDeck().add(new Foe("F10", 10));
        game.getAdventureDeck().add(new Foe("F10", 10));
        game.getAdventureDeck().add(new Foe("F40", 40));
        game.getAdventureDeck().add(new Foe("F5", 5));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream);

        System.setOut(out);
        System.out.println("It is P1's turn");

        System.setOut(previousOut);
        return byteArrayOutputStream.toString();
    }

    @GetMapping("/third_Scenario")
    public String third_Scenario() {
        game = new Game();
        game.dealCards();

        //rig P1
        game.getPlayers().get(0).getCards().clear();
        game.getPlayers().get(0).addCard(new Foe("F5", 5));
        game.getPlayers().get(0).addCard(new Foe("F5", 5));
        game.getPlayers().get(0).addCard(new Foe("F10", 10));
        game.getPlayers().get(0).addCard(new Foe("F10", 10));
        game.getPlayers().get(0).addCard(new Foe("F15", 15));
        game.getPlayers().get(0).addCard(new Foe("F15", 15));
        game.getPlayers().get(0).addCard(new Foe("F20", 20));
        game.getPlayers().get(0).addCard(new Foe("F20", 20));
        game.getPlayers().get(0).addCard(new Weapon("D5", 5));
        game.getPlayers().get(0).addCard(new Weapon("D5", 5));
        game.getPlayers().get(0).addCard(new Weapon("D5", 5));
        game.getPlayers().get(0).addCard(new Weapon("D5", 5));

        //rig P2
        game.getPlayers().get(1).getCards().clear();
        game.getPlayers().get(1).addCard(new Foe("F25", 25));
        game.getPlayers().get(1).addCard(new Foe("F30", 30));
        game.getPlayers().get(1).addCard(new Weapon("H10", 10));
        game.getPlayers().get(1).addCard(new Weapon("H10", 10));
        game.getPlayers().get(1).addCard(new Weapon("S10", 10));
        game.getPlayers().get(1).addCard(new Weapon("S10", 10));
        game.getPlayers().get(1).addCard(new Weapon("S10", 10));
        game.getPlayers().get(1).addCard(new Weapon("B15", 15));
        game.getPlayers().get(1).addCard(new Weapon("B15", 15));
        game.getPlayers().get(1).addCard(new Weapon("L20", 20));
        game.getPlayers().get(1).addCard(new Weapon("L20", 20));
        game.getPlayers().get(1).addCard(new Weapon("E30", 30));

        //rig P3
        game.getPlayers().get(2).getCards().clear();
        game.getPlayers().get(2).addCard(new Foe("F25", 25));
        game.getPlayers().get(2).addCard(new Foe("F30", 30));
        game.getPlayers().get(2).addCard(new Weapon("H10", 10));
        game.getPlayers().get(2).addCard(new Weapon("H10", 10));
        game.getPlayers().get(2).addCard(new Weapon("S10", 10));
        game.getPlayers().get(2).addCard(new Weapon("S10", 10));
        game.getPlayers().get(2).addCard(new Weapon("S10", 10));
        game.getPlayers().get(2).addCard(new Weapon("B15", 15));
        game.getPlayers().get(2).addCard(new Weapon("B15", 15));
        game.getPlayers().get(2).addCard(new Weapon("L20", 20));
        game.getPlayers().get(2).addCard(new Weapon("L20", 20));
        game.getPlayers().get(2).addCard(new Weapon("E30", 30));

        //rig P4
        game.getPlayers().get(3).getCards().clear();
        game.getPlayers().get(3).addCard(new Foe("F25", 25));
        game.getPlayers().get(3).addCard(new Foe("F30", 30));
        game.getPlayers().get(3).addCard(new Foe("F70", 70));
        game.getPlayers().get(3).addCard(new Weapon("H10", 10));
        game.getPlayers().get(3).addCard(new Weapon("H10", 10));
        game.getPlayers().get(3).addCard(new Weapon("S10", 10));
        game.getPlayers().get(3).addCard(new Weapon("S10", 10));
        game.getPlayers().get(3).addCard(new Weapon("S10", 10));
        game.getPlayers().get(3).addCard(new Weapon("B15", 15));
        game.getPlayers().get(3).addCard(new Weapon("B15", 15));
        game.getPlayers().get(3).addCard(new Weapon("L20", 20));
        game.getPlayers().get(3).addCard(new Weapon("L20", 20));

        //event deck rig
        game.getEventDeck().add(new Quest("Q3", 3));
        game.getEventDeck().add(new Event("Queen's favor"));
        game.getEventDeck().add(new Event("Prosperity"));
        game.getEventDeck().add(new Event("Plague"));
        game.getEventDeck().add(new Quest("Q4", 4));

        //rig draws
        game.getAdventureDeck().add(new Foe("F35", 35));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Weapon("H10", 10));
        game.getAdventureDeck().add(new Weapon("H10", 10));
        game.getAdventureDeck().add(new Weapon("H10", 10));
        game.getAdventureDeck().add(new Foe("F50", 50));
        game.getAdventureDeck().add(new Foe("F40", 40));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Foe("F50", 50));
        game.getAdventureDeck().add(new Weapon("H10", 10));
        game.getAdventureDeck().add(new Weapon("B15", 15));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F30", 30));
        game.getAdventureDeck().add(new Weapon("D5", 5));
        game.getAdventureDeck().add(new Weapon("D5", 5));
        game.getAdventureDeck().add(new Foe("F40", 40));
        game.getAdventureDeck().add(new Weapon("B15", 15));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Weapon("H10", 10));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F15", 15));
        game.getAdventureDeck().add(new Foe("F15", 15));
        game.getAdventureDeck().add(new Foe("F15", 15));
        game.getAdventureDeck().add(new Foe("F15", 15));
        game.getAdventureDeck().add(new Foe("F10", 10));
        game.getAdventureDeck().add(new Foe("F10", 10));
        game.getAdventureDeck().add(new Foe("F5", 5));
        game.getAdventureDeck().add(new Foe("F5", 5));
        game.getAdventureDeck().add(new Foe("F20", 20));
        game.getAdventureDeck().add(new Foe("F10", 10));
        game.getAdventureDeck().add(new Foe("F5", 5));
        game.getAdventureDeck().add(new Foe("F20", 20));
        game.getAdventureDeck().add(new Foe("F10", 10));
        game.getAdventureDeck().add(new Foe("F5", 5));
        game.getAdventureDeck().add(new Foe("F25", 25));
        game.getAdventureDeck().add(new Foe("F5", 5));
        game.getAdventureDeck().add(new Foe("F15", 15));
        game.getAdventureDeck().add(new Foe("F20", 20));
        game.getAdventureDeck().add(new Foe("F10", 10));
        game.getAdventureDeck().add(new Foe("F5", 5));


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream);

        System.setOut(out);
        System.out.println("It is P1's turn");

        System.setOut(previousOut);
        return byteArrayOutputStream.toString();
    }

    @GetMapping("/fourth_Scenario")
    public String fourth_Scenario() {
        game = new Game();
        game.dealCards();

        //rig P1
        game.getPlayers().get(0).getCards().clear();
        game.getPlayers().get(0).addCard(new Foe("F50", 50));
        game.getPlayers().get(0).addCard(new Foe("F70", 70));
        game.getPlayers().get(0).addCard(new Weapon("D5", 5));
        game.getPlayers().get(0).addCard(new Weapon("D5", 5));
        game.getPlayers().get(0).addCard(new Weapon("H10", 10));
        game.getPlayers().get(0).addCard(new Weapon("H10", 10));
        game.getPlayers().get(0).addCard(new Weapon("S10", 10));
        game.getPlayers().get(0).addCard(new Weapon("S10", 10));
        game.getPlayers().get(0).addCard(new Weapon("B15", 15));
        game.getPlayers().get(0).addCard(new Weapon("B15", 15));
        game.getPlayers().get(0).addCard(new Weapon("L20", 20));
        game.getPlayers().get(0).addCard(new Weapon("L20", 20));

        //rig P2
        game.getPlayers().get(1).getCards().clear();
        game.getPlayers().get(1).addCard(new Foe("F5", 5));
        game.getPlayers().get(1).addCard(new Foe("F5", 5));
        game.getPlayers().get(1).addCard(new Foe("F10", 10));
        game.getPlayers().get(1).addCard(new Foe("F15", 15));
        game.getPlayers().get(1).addCard(new Foe("F15", 15));
        game.getPlayers().get(1).addCard(new Foe("F20", 20));
        game.getPlayers().get(1).addCard(new Foe("F20", 20));
        game.getPlayers().get(1).addCard(new Foe("F25", 25));
        game.getPlayers().get(1).addCard(new Foe("F30", 30));
        game.getPlayers().get(1).addCard(new Foe("F30", 30));
        game.getPlayers().get(1).addCard(new Foe("F40", 40));
        game.getPlayers().get(1).addCard(new Weapon("E30", 30));

        //rig P3
        game.getPlayers().get(2).getCards().clear();
        game.getPlayers().get(2).addCard(new Foe("F5", 5));
        game.getPlayers().get(2).addCard(new Foe("F5", 5));
        game.getPlayers().get(2).addCard(new Foe("F10", 10));
        game.getPlayers().get(2).addCard(new Foe("F15", 15));
        game.getPlayers().get(2).addCard(new Foe("F15", 15));
        game.getPlayers().get(2).addCard(new Foe("F20", 20));
        game.getPlayers().get(2).addCard(new Foe("F20", 20));
        game.getPlayers().get(2).addCard(new Foe("F25", 25));
        game.getPlayers().get(2).addCard(new Foe("F25", 25));
        game.getPlayers().get(2).addCard(new Foe("F30", 30));
        game.getPlayers().get(2).addCard(new Foe("F40", 40));
        game.getPlayers().get(2).addCard(new Weapon("L20", 20));

        //rig P4
        game.getPlayers().get(3).getCards().clear();
        game.getPlayers().get(3).addCard(new Foe("F5", 5));
        game.getPlayers().get(3).addCard(new Foe("F5", 5));
        game.getPlayers().get(3).addCard(new Foe("F10", 10));
        game.getPlayers().get(3).addCard(new Foe("F15", 15));
        game.getPlayers().get(3).addCard(new Foe("F15", 15));
        game.getPlayers().get(3).addCard(new Foe("F20", 20));
        game.getPlayers().get(3).addCard(new Foe("F20", 20));
        game.getPlayers().get(3).addCard(new Foe("F25", 25));
        game.getPlayers().get(3).addCard(new Foe("F25", 25));
        game.getPlayers().get(3).addCard(new Foe("F30", 30));
        game.getPlayers().get(3).addCard(new Foe("F50", 50));
        game.getPlayers().get(3).addCard(new Weapon("E30", 30));

        //event deck rig
        game.getEventDeck().add(new Quest("Q2", 2));

        //rig draws
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Weapon("S10", 10));
        game.getAdventureDeck().add(new Weapon("H10", 10));
        game.getAdventureDeck().add(new Weapon("H10", 10));
        game.getAdventureDeck().add(new Weapon("H10", 10));
        game.getAdventureDeck().add(new Weapon("H10", 10));
        game.getAdventureDeck().add(new Weapon("D5", 5));
        game.getAdventureDeck().add(new Weapon("D5", 5));
        game.getAdventureDeck().add(new Weapon("D5", 5));
        game.getAdventureDeck().add(new Weapon("D5", 5));
        game.getAdventureDeck().add(new Foe("F15", 15));
        game.getAdventureDeck().add(new Foe("F10", 10));
        game.getAdventureDeck().add(new Foe("F5", 5));
        game.getAdventureDeck().add(new Foe("F10", 10));
        game.getAdventureDeck().add(new Foe("F15", 15));
        game.getAdventureDeck().add(new Foe("F5", 5));


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream);

        System.setOut(out);
        System.out.println("It is P1's turn");

        System.setOut(previousOut);
        return byteArrayOutputStream.toString();
    }

    @GetMapping("/continue")
    public String continueButton() {
        if (gameStage.equals("drawCard")) {
            return drawCard();
        } else if (gameStage.equals("findSponsor")) {
            return findSponsor();
        } else if (gameStage.equals("questBuildPrompt")) {
            return questBuildPrompt();
        }

        return " ";
    }

    public String drawCard() {

        Card c = game.drawEventCard();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream);

        System.setOut(out);
        ui.displayDrawnCard(c);

        if (c instanceof Quest) {
            gameStage = "findSponsor";
            game.setQuest((Quest) c);
        } else if (c instanceof Event) {
            game.performEventAction(c);
            gameStage = "trimHand";
            System.out.println("Press continue button...");
            return byteArrayOutputStream.toString();
        }

        return byteArrayOutputStream.toString();
    }

    public String findSponsor() {
        if (sponsorIndex == -1) {
            sponsorIndex = game.getPlayers().indexOf(game.getCurrentPlayer());
        } else if (sponsorIndex == 4) {
            sponsorIndex = 0;
        }

        while (true) {
            if (game.getPlayers().get(sponsorIndex).foeNum() < Game.QuestLine.getCurrentQuest().getStageNum()) {
                sponsorAsked.set(sponsorIndex, true);

                if (sponsorAsked.get(sponsorIndex)) {
                    for (int i = 0; i < 4; i++) {
                        sponsorAsked.set(i, false);
                    }

                    Game.QuestLine.resetQuest();
                    sponsorIndex = -1;
                    gameStage = "drawCard";
                    return displayNextTurn();
                }

                sponsorIndex++;
                continue;
            }

            break;
        }

        if (sponsorAsked.get(sponsorIndex)) {
            for (int i = 0; i < 4; i++) {
                sponsorAsked.set(i, false);
            }

            Game.QuestLine.resetQuest();
            sponsorIndex = -1;
            gameStage = "drawCard";
            return displayNextTurn();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream);
        System.setOut(out);
        ui.sponsorPrompt(game.getPlayers().get(sponsorIndex));

        System.setOut(previousOut);
        sponsorAsked.set(sponsorIndex, true);
        gameStage = "getSponsor";
        return byteArrayOutputStream.toString();
    }

    public String questBuildPrompt() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream);
        System.setOut(out);

        String input = "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ui.promptStage(game, stageBuildNum);

        System.setOut(previousOut);
        System.setIn(previousIn);
        gameStage = "questBuild";
        return byteArrayOutputStream.toString();
    }

    @PostMapping("/questBuild")
    public String questBuild(@RequestBody String answer) {
        System.setOut(previousOut);
        String input = answer + "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        if (game.getCurrentQuest().getStageNum() == stageBuildNum && answer.equals("quit")) {
            game.setUpQuest(stageBuildNum);
            gameStage = "findParticipants";
            return participantPrompt(); //PARTICIPANT PROMPT
        } else if (game.setUpQuest(stageBuildNum)) {
            stageBuildNum++;
        }

        return questBuildPrompt();
    }

    public String participantPrompt() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream);
        System.setOut(out);

        while (true) {
            if (game.getPlayers().get(participateIndex).getName().equals(game.getSponsor().getName()) || !game.getPlayers().get(participateIndex).getEligible()) {
                if (participateIndex == 3) {
                    for (Boolean b : participating) {
                        if (b) {
                            return chooseParticipant("2");
                        }
                    }
                    for (int i = 0; i < 4; i++) {
                        sponsorAsked.set(i, false);
                        participating.set(i, false);
                    }

                    Game.QuestLine.resetQuest();
                    sponsorIndex = -1;
                    participateIndex = 0;
                    gameStage = "drawCard";
                    return displayNextTurn();
                } else {
                    participateIndex++;
                }
                continue;
            }

            break;
        }

        gameStage = "chooseParticipant";
        ui.promptParticipant(game.getPlayers().get(participateIndex));
        System.setOut(previousOut);
        return byteArrayOutputStream.toString();
    }

    @PostMapping("/chooseParticipant")
    public String chooseParticipant(@RequestBody String answer) {
        String input = "";

        if (answer.equals("1")) {
            participating.set(participateIndex, true);
        }

        if (participateIndex < 3) {
            participateIndex++;
            return participantPrompt();
        }

        for (int i = 0; i < 4; i++) {
            if (game.getPlayers().get(i).getName().equals(game.getSponsor().getName()) || !game.getPlayers().get(i).getEligible()) {
                continue;
            }

            if (participating.get(i)) {
                input += "1\n";
            } else {
                input += "2\n";
            }
        }

        participateIndex = 0;
        System.setOut(previousOut);
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.chooseParticipants();
        gameStage = "trimHand";
        return "Press the continue button twice....\n";
    }

    @PostMapping("/trimHand")
    public void trimHand() {
        System.setOut(printStream);
        System.setIn(pipedInputStream);
        gameStage = "getInput";
        game.trimHand();


        if (Game.QuestLine.getCurrentQuest() != null) {
            attackSetup();
        } else {
            for (Player p : game.getPlayers()) {
                if (p.getShields() >= 7) {
                    System.out.println("WINNERS:");
                    for (Player pl : game.checkWinner()) {
                        System.out.println(pl.getName());
                    }
                    gameStage = "gameOver";
                    return;
                }
            }
            gameStage = "drawCard";
            sponsorIndex = -1;
            participateIndex = 0;
            stageBuildNum = 1;
            game.nextTurn();
            System.out.println("It is " + game.getCurrentPlayer().getName() + "'s turn");
            System.out.println("Press continue button...");
        }
    }

    public void attackSetup() {
        System.setIn(pipedInputStream);
        gameStage = "getInput";
        game.setUpAttacks();

        System.out.println("Press continue button...");

        gameStage = "resolveAttack";
    }

    @PostMapping("/resolveAttack")
    public void resolveAttack() {
        System.setOut(printStream);

        game.resolveAttacks();

        if (Game.QuestLine.getCurrentQuest() == null) {
            System.out.println("Press continue button...");

            for (Player p : game.getPlayers()) {
                if (p.getCards().size() > 12) {
                    gameStage = "trimHand";
                    return;
                }
            }
            gameStage = "drawCard";
            sponsorIndex = -1;
            participateIndex = 0;
            stageBuildNum = 1;
            return;
        } else {
            participateIndex = 0;
            gameStage = "findParticipants";
            System.out.println(participantPrompt());
        }
    }

    @PostMapping("/getSponsorInput")
    public String getSponsorInput(@RequestBody String answer) {
        if (Integer.parseInt(answer) == 2 || answer.toUpperCase().equals("NO")) {
            sponsorAsked.set(sponsorIndex, true);
            sponsorIndex++;
            return findSponsor();
        }
        sponsorAsked.set(sponsorIndex, false);

        String input = "";
        for (int i = game.getPlayers().indexOf(game.getCurrentPlayer()); i < 4; i++) {
            if (game.getPlayers().get(i).foeNum() < Game.QuestLine.getCurrentQuest().getStageNum()) {
                if (i == 3) {
                    i = -1;
                }
                continue;
            }


            if (sponsorAsked.get(i)) {
                input += "2\n";
            } else if (i == sponsorIndex) {
                input += "1\n";
                break;
            }

            if (i == 3) {
                i = -1;
            }
        }

        System.setOut(previousOut);
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setSponsor(game.findSponsor());
        sponsorIndex = -1;
        for (int i = 0; i < 4; i++) {
            sponsorAsked.set(i, false);
        }
        gameStage = "questBuild";
        return questBuildPrompt();
    }

    @GetMapping("/getGameStage")
    public String getGameStage() {
        return gameStage;
    }

    public String displayNextTurn() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream);

        System.setOut(out);
        String input = "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ui.hotSeatPrompt(game);
        game.nextTurn();

        System.out.println("It is " + game.getCurrentPlayer().getName() + "'s turn");

        System.setOut(previousOut);
        System.setIn(previousIn);
        return byteArrayOutputStream.toString();
    }

    public String displayNextTurn(ByteArrayOutputStream byteArrayOutputStream) {
        String input = "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ui.hotSeatPrompt(game);
        game.nextTurn();

        System.out.println("It is " + game.getCurrentPlayer().getName() + "'s turn");

        System.setOut(previousOut);
        System.setIn(previousIn);
        return byteArrayOutputStream.toString();
    }

    @GetMapping("/getOutput")
    public String getOutput() {
        return tempOutput.toString();
    }

    @GetMapping("/getModel")
    public Game getModel() {
        return game;
    }

    @PostMapping("/setInput")
    public void setInput(@RequestBody String answer) throws IOException {
        String answerNewline = answer + "\n";
        pipedOutputStream.write(answerNewline.getBytes());
        pipedOutputStream.flush();
    }

    @PostMapping("/reset")
    public void reset() {
        Game.QuestLine.resetQuest();
        game = null;
        sponsorIndex = -1;
        participateIndex = 0;
        stageBuildNum = 1;

        for (int i = 0; i < 4; i++) {
            sponsorAsked.set(i, false);
            participating.set(i, false);
        }

        gameStage = "drawCard";
        tempOutput.reset();
    }

}
