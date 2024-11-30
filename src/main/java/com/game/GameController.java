package com.game;

import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8081")
public class GameController {
    Game game = new Game();
    UI ui = new UI();
    PrintStream previousOut = System.out;
    InputStream previousIn = System.in;
    int sponsorIndex = -1;
    ArrayList<Boolean> sponsorAsked = new ArrayList<>();

    String gameStage = "drawCard";

    public GameController() {
        for (int i = 0; i < 4; i++) {
            sponsorAsked.add(false);
        }
    }

    @GetMapping("/start")
    public String start() {
        game.dealCards();
        game.getEventDeck().remove(game.getEventDeck().size() - 1);
        game.getEventDeck().add(new Quest("Q2", 2));

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
        }

        return "";
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
            return displayNextTurn(byteArrayOutputStream);
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

    @PostMapping("/getSponsorInput")
    public String getSponsorInput(@RequestBody String answer) {
        if (Integer.parseInt(answer) == 2 || answer.toUpperCase().equals("NO")) {
            sponsorAsked.set(sponsorIndex, true);
            sponsorIndex++;
            return findSponsor();
        }

        String input = "";
        for (int i = game.getPlayers().indexOf(game.getCurrentPlayer()); i < 4; i++) {
            if (game.getPlayers().get(i).foeNum() < Game.QuestLine.getCurrentQuest().getStageNum()) {
                if (i == 3) {
                    i = -1;
                }
                continue;
            }


            if (sponsorAsked.get(game.getPlayers().indexOf(game.getCurrentPlayer()))) {
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
        gameStage = "questBuild1";
        return "";
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
}
