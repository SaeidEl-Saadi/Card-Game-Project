public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        UI ui = new UI();
        game.dealCards();

        game.getEventDeck().addLast(new Quest("Q2", 2));

        while (true) {
            Card c = game.drawEventCard();
            if (c instanceof Quest) {
                game.setQuest((Quest) c);
                ui.displayDrawnCard(c);
                game.setSponsor(game.findSponsor());

                for (int i = 0; i < ((Quest) c).getStageNum(); i++) {
                    if(!game.setUpQuest(i + 1)) {
                        i--;
                    } else {
                        if (game.checkValidity(i + 1)) {
                            continue;
                        }
                        i--;
                    }
                }

                boolean flag = true;
                while (flag) {
                    game.chooseParticipants();
                    game.setUpAttacks();
                    if (Game.QuestLine.getCurrentStage() == Game.QuestLine.getCurrentQuest().getStageNum()) {
                        flag = false;
                    }
                    game.resolveAttacks();
                }

                Game.QuestLine.resetQuest();
            } else if (c instanceof Event) {
                game.performEventAction(c);
                ui.displayDrawnCard(c);
                game.performEventAction(c);
            }

            game.checkWinner();
            ui.hotSeatPrompt(game);
            game.nextTurn();
        }
    }
}