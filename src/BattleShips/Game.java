package BattleShips;

import BattleShips.model.Player;
import BattleShips.model.Point;
import BattleShips.util.GameUtil;

public class Game {
    private Player[] players;

    public Game() {
        this.players = new Player[2];
    }

    public void start() {
        players = GameUtil.createPlayers();
        GameUtil.createShips(players[0]);
        GameUtil.createShips(players[1]);
    }

    public void play() {
        Player playerFirst, playerSecond;
        System.out.println("Давайте начнем игру");
        if ((int) Math.round(Math.random()) == 1) {
            playerFirst = players[0];
            playerSecond = players[1];
        } else {
            playerFirst = players[1];
            playerSecond = players[0];
        }
        System.out.println("Случайным образом мы определили, что первым ходить будет " + playerFirst.getName());
        Player winner = playGame(playerFirst, playerSecond);
        System.out.println(winner.getName() + ", c победой. Ты красавчик!");
        System.out.println("\nИгра окончена. До новых встреч.");
    }

    public Player playGame(Player player, Player opponent) {
        char[][] field = opponent.getOpponentField();
        if (player.getShips().size() == 0) {
            return opponent;
        } else if (opponent.getShips().size() == 0) {
            return player;
        }
        System.out.println(player.getName() + ", делайте ход");
        GameUtil.printField(field);
        Point currentShot = nextShot();
        if (field[currentShot.getY()][currentShot.getX()] == 'o') {
            System.out.println(player.getName() + ", Вы уже сюда стреляли. Переход хода.");
            GameUtil.printField(field);
            return playGame(opponent, player);
        }
        if (field[currentShot.getY()][currentShot.getX()] == 'X') {
            System.out.println(player.getName() + ", Вы уже сюда уже попадали. Будьте внимательнее. Переход хода.");
            GameUtil.printField(field);
            return playGame(opponent, player);
        }
        if (!opponent.getShips().containsKey(currentShot)) {
            System.out.println("Мимо. Ход переходит к другому игроку");
            field[currentShot.getY()][currentShot.getX()] = 'o';
            opponent.setOpponentField(field);
            GameUtil.printField(opponent.getOpponentField());
            return playGame(opponent, player);
        }
        int shipLives = opponent.getShips().get(currentShot).getDeckCount() - 1;
        opponent.getShips().get(currentShot).setDeckCount(shipLives);
        if (shipLives == 0) {
            System.out.println("Убил");
        } else {
            System.out.println("Попал");
        }
        opponent.getShips().remove(currentShot);
        field[currentShot.getY()][currentShot.getX()] = 'X';
        opponent.setOpponentField(field);
        GameUtil.printField(opponent.getOpponentField());
        return playGame(player, opponent);
    }
    private Point nextShot() {
        int x = GameUtil.setCoordinate("X");
        int y = GameUtil.setCoordinate("Y");
        return new Point(x, y);
    }
}