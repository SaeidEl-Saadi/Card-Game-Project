Feature: Quests game

  Scenario: A1_scenario
    Given a new game of quests starts
    And hands are rigged for A1_scenario
    When "P1" draws event card "Q4"
    And "P2" is a sponsor
    And "P2" builds stage 1 with "F5,H10"
    And "P2" builds stage 2 with "F15,S10"
    And "P2" builds stage 3 with "F15,D5,B15"
    And "P2" builds stage 4 with "F40,B15"
    And "P1,P3,P4" participate for stage 1
    And "P1" discards "F5"
    And "P3" discards "F5"
    And "P4" discards "F5"
    And "P1" builds attack with "D5,S10"
    And "P3" builds attack with "S10,D5"
    And "P4" builds attack with "D5,H10"
    And "P1,P3,P4" win stage 1
    And "P1,P3,P4" participate for stage 2
    And "P1" builds attack with "H10,S10"
    And "P3" builds attack with "B15,S10"
    And "P4" builds attack with "H10,B15"
    And "P3,P4" win stage 2
    And "P1" has no shields and their hand is "F5,F10,F15,F15,F30,H10,B15,B15,L20" in that order
    And "P3,P4" participate for stage 3
    And "P3" builds attack with "L20,H10,S10"
    And "P4" builds attack with "B15,S10,L20"
    And "P3,P4" win stage 3
    And "P3,P4" participate for stage 4
    And "P3" builds attack with "B15,H10,L20"
    And "P4" builds attack with "D5,S10,L20,E30"
    And "P4" win stage 4
    And "P3" has no shields and their hand is "F5,F5,F15,F30,S10" in that order
    And "P4" has 4 shields and their hand is "F15,F15,F40,L20" in that order
    And "P2" discards 4 random cards
    Then "P4" wins the quest with 4 shields and 4 cards
    And "P1" has 9 cards in hand and 0 shields
    And "P2" has 12 cards in hand and 0 shields
    And "P3" has 5 cards in hand and 0 shields

  Scenario: 2winner_game_2winner_quest
    Given a new game of quests starts
    And hands are rigged for 2winner_game_2winner_quest scenario
    When "P1" draws event card "Q4"
    And "P1" is a sponsor
    And "P1" builds stage 1 with "F10"
    And "P1" builds stage 2 with "F15"
    And "P1" builds stage 3 with "F15,D5"
    And "P1" builds stage 4 with "F20,D5"
    And "P2,P3,P4" participate for stage 1
    And "P2" discards "F5"
    And "P3" discards "B15"
    And "P4" discards "F5"
    And "P2" builds attack with "S10"
    And "P3" builds attack with "D5"
    And "P4" builds attack with "D5,H10"
    And "P2,P4" win stage 1
    And "P2,P4" participate for stage 2
    And "P2" builds attack with "B15"
    And "P4" builds attack with "B15"
    And "P2,P4" win stage 2
    And "P2,P4" participate for stage 3
    And "P2" builds attack with "B15,D5"
    And "P4" builds attack with "B15,D5"
    And "P2,P4" win stage 3
    And "P2,P4" participate for stage 4
    And "P2" builds attack with "S10,H10,B15"
    And "P4" builds attack with "L20,S10"
    And "P2,P4" win stage 4
    And "P4" wins the quest with 4 shields and 8 cards
    And "P2" wins the quest with 4 shields and 8 cards
    And "P1" discards 4 random cards
    And It is "P2" turn
    And "P2" draws event card "Q3"
    And "P3" is a sponsor
    And "P3" builds stage 1 with "F5"
    And "P3" builds stage 2 with "F10"
    And "P3" builds stage 3 with "F15"
    And "P2,P4" participate for stage 1
    And "P2" builds attack with "S10"
    And "P4" builds attack with "S10"
    And "P2,P4" win stage 1
    And "P2,P4" participate for stage 2
    And "P2" builds attack with "H10"
    And "P4" builds attack with "H10"
    And "P2,P4" win stage 2
    And "P2,P4" participate for stage 3
    And "P2" builds attack with "E30"
    And "P4" builds attack with "E30"
    And "P2,P4" win stage 3
    And "P4" wins the quest with 7 shields and 8 cards
    And "P2" wins the quest with 7 shields and 8 cards
    Then "P2,P4" are winners of the game with "7,7" shields respectively



