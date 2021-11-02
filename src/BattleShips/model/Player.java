package BattleShips.model;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private String name;
    private char[][] personalField = new char[12][12];
    private char[][] opponentField = new char[12][12];
    private Map<Point, Ship> ships = new HashMap<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[][] getPersonalField() {
        return personalField;
    }

    public void setPersonalField(char[][] personalField) {
        this.personalField = personalField;
    }

    public void printField(char[][] field) {

        for (int i = 1; i < field.length - 1; i++) {
            for (int j = 1; j < field.length - 1; j++) {
                if (field[i][j] == 0) {
                    System.out.print(" |");
                } else {
                    System.out.print(field[i][j] + "|");
                }
            }
            System.out.println();
        }
    }

    public Map<Point, Ship> getShips() {
        return ships;
    }

    public void setShips(Map<Point, Ship> ships) {
        this.ships = ships;
    }

    public char[][] getOpponentField() {
        return opponentField;
    }

    public void setOpponentField(char[][] opponentField) {
        this.opponentField = opponentField;
    }
}