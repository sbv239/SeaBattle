package BattleShips;

public class Game {
    private Player[] players;

    public Game() {
        this.players = new Player[2];
    }

    public void start() {
        players = GameSetup.createPlayers();
        GameSetup.createShips(players[0]);
        GameSetup.createShips(players[1]);
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
        playGame(playerFirst, playerSecond);
    }

    public Player playGame(Player player, Player opponent) {
        char[][] field = opponent.getOpponentField();
        if (player.getShips().size() == 0) {
            return printResult(player, opponent);
        } else if (opponent.getShips().size() == 0) {
            return printResult(opponent, player);
        }
        System.out.println(player.getName() + ", делайте ход");
        opponent.printField(field);
        Point currentShot = nextShot();
        if (field[currentShot.getY()][currentShot.getX()] == 'o') {
            System.out.println(player.getName() + ", Вы уже сюда стреляли. Переход хода.");
            opponent.printField(field);
            return playGame(opponent, player);
        }
        if (field[currentShot.getY()][currentShot.getX()] == 'X') {
            System.out.println(player.getName() + ", Вы уже сюда уже попадали. Будьте внимательнее. Переход хода.");
            opponent.printField(field);
            return playGame(opponent, player);
        }
        if (!opponent.getShips().containsKey(currentShot)) {
            System.out.println("Мимо. Ход переходит к другому игроку");
            field[currentShot.getY()][currentShot.getX()] = 'o';
            opponent.setOpponentField(field);
            opponent.printField(opponent.getOpponentField());
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
        opponent.printField(opponent.getOpponentField());
        return playGame(player, opponent);
    }

    private Player printResult(Player player1, Player player2) {
        player1.printField(player1.getOpponentField());
        System.out.println("Ура, победил игрок по имени " + player2.getName());
        return player2;
    }
    private Point nextShot() {
        int x = GameSetup.setCoordinate("X");
        int y = GameSetup.setCoordinate("Y");
        return new Point(x, y);
    }
}