package BattleShips.model;

public class Ship {
    private int deckCount;

    public Ship(int deckCount) {
        this.deckCount = deckCount;
    }

    public int getDeckCount() {
        return deckCount;
    }

    public void setDeckCount(int deckCount) {
        this.deckCount = deckCount;
    }
}