package BattleShips.util;

import BattleShips.model.Player;
import BattleShips.model.Point;
import BattleShips.model.Ship;

import java.util.Map;
import java.util.Scanner;

public final class GameUtil {
    private GameUtil() {
    }

    public static Player[] createPlayers() {
        Scanner scanner = new Scanner(System.in);
        Player[] players = new Player[2];
        System.out.println("Игрок №1, введите имя: ");
        String name = scanner.nextLine();
        players[0] = new Player(name);
        System.out.println("Игрок №2, введите имя: ");
        name = scanner.nextLine();
        players[1] = new Player(name);
        return players;
    }

    public static void createShips(Player player) {
        System.out.println(player.getName() + ", давайте расставим корабли");
        for (int i = 4; i > 0; i--) {
            for (int j = 5 - i; j > 0; j--) {
                fillPlayerField(player, i);
            }
        }
    }

    private static void fillPlayerField(Player player, int deck) {
        char[][] playerField = player.getPersonalField();
        boolean isPossible = false;
        int x = 0;
        int y = 0;
        int vector = 0;

        while (!isPossible) {
            System.out.println("Расположите корректно корабль, число палуб в котором = " + deck);
            x = setCoordinate("X");
            y = setCoordinate("Y");
            if (deck != 1) {
                vector = setVector();
            }
            isPossible = isCoordinatesValid(playerField, x, y, vector, deck);
        }

        createShip(player, playerField, deck, x, y, vector);
    }

    private static boolean isCoordinatesValid(char[][] field, int x, int y, int vector, int deck) {

        if (deck == 1 && isImposible(1, field, x, y)) {
            return false;
        } else if (vector == 1) {
            for (int i = 0; i < deck; i++) {
                if (isImposible(deck, field, x + i, y) || (x + deck > 11)) {
                    return false;
                }
            }
        } else if (vector == 2) {
            for (int i = 0; i < deck - 1; i++) {
                if (isImposible(deck, field, x, y + i) || (y + deck > 11)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int setCoordinate(String axis) {
        Scanner scanner = new Scanner(System.in);
        boolean isInteger;
        String inputText;
        do {
            System.out.println("Введите координату " + axis + " (от 1 до 10): ");
            inputText = scanner.nextLine();
            isInteger = isInteger(inputText);
        }
        while (!isInteger);

        int x = Integer.parseInt(inputText);

        if (x > 0 && x <= 10) {
            return x;
        }
        System.out.println("Некорретная кордината. Попробуйте еще.");
        return setCoordinate(axis);
    }

    public static int setVector() {
        Scanner scanner = new Scanner(System.in);
        boolean isInteger;
        String inputText;
        do {
            System.out.println("Как расположить корабль (горизонтально - 1, вертикальное - 2): ");
            inputText = scanner.nextLine();
            isInteger = isInteger(inputText);
        }
        while (!isInteger);

        int v = Integer.parseInt(inputText);

        if (v == 1 || v == 2) {
            return v;
        }
        System.out.println("Неверное значение. Попробуйте снова.");
        return setVector();
    }

    private static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели не число!");
            return false;
        }
        return true;
    }

    private static boolean isImposible(int deck, char[][] field, int x, int y) {
        if (deck == 1 && '◯' == field[y][x]
                || '◯' == field[y][x - 1]
                || '◯' == field[y + 1][x - 1]
                || '◯' == field[y + 1][x]
                || '◯' == field[y + 1][x + 1]
                || '◯' == field[y][x + 1]
                || '◯' == field[y - 1][x + 1]
                || '◯' == field[y - 1][x]
                || '◯' == field[y - 1][x - 1]) {
            System.out.println("Тут располагать корабль нельзя, давай еще раз попробуем.");
            return true;
        }
        return false;
    }

    private static void createShip(Player player, char[][] field, int deck, int x, int y, int vector) {
        Map<Point, Ship> ships = player.getShips();
        Ship ship = new Ship(deck);
        if (vector == 1) {
            for (int i = 0; i < deck; i++) {
                field[y][x + i] = '◯';
                Point point = new Point(x + i, y);
                ships.put(point, ship);
            }
        }
        if (vector == 2) {
            for (int i = 0; i < deck; i++) {
                field[y + i][x] = '◯';
                Point point = new Point(x + i, y);
                ships.put(point, ship);
            }
        }
        if (deck == 1) {
            Point point = new Point(x, y);
            ships.put(point, ship);
            field[y][x] = '◯';
        }
        player.setShips(ships);
        player.setPersonalField(field);
        System.out.println("Корабль поставлен");
        player.printField(field);
    }
}

