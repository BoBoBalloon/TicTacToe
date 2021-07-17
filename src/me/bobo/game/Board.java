package me.bobo.game;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a game board
 */
public class Board {
    private final Mark[][] slots;
    private boolean gameOver;
    private boolean playerTurn;
    private Mark winner;

    public static final int BOARD_WIDTH = 3;

    /**
     * Constructor that builds a new empty game board
     */
    public Board() {
        this.slots = new Mark[BOARD_WIDTH][BOARD_WIDTH];

        for (int rows = 0; rows < BOARD_WIDTH; rows++) {
            for (int columns = 0; columns < BOARD_WIDTH; columns++) {
                this.slots[rows][columns] = Mark.EMPTY;
            }
        }

        this.gameOver = false;
        this.playerTurn = true;
        this.winner = null;
    }

    /**
     * A method used when the player wants to make a move
     *
     * @param row    the row of the slot you want to mark
     * @param column the slot of the slot you want to mark
     */
    public void makePlayerMove(int row, int column) {
        if (!this.playerTurn) {
            return;
        }

        Mark mark = this.getMark(row, column);

        if (mark != Mark.EMPTY) {
            return;
        }

        this.setMark(row, column, Mark.X);

        this.checkOver();

        this.nextTurn();
    }


    /**
     * A method used when it is time for the AI to make a move
     */
    public void makeAIMove() {
        if (this.playerTurn) {
            return;
        }

        Minimax.minimax(this, 10, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);

        this.checkOver();

        this.nextTurn();
    }

    /**
     * A method that returns a mark on the board
     *
     * @param row    the row that the mark is in
     * @param column the column that the mark is in
     * @return the mark in that current spot
     */
    public Mark getMark(int row, int column) {
        return this.slots[row - 1][column - 1];
    }

    /**
     * A method that will set the mark of a slot on the board
     *
     * @param row    the row of the slot you want to mark
     * @param column the slot of the slot you want to mark
     * @param mark   the mark you want to set the slot to
     */
    public void setMark(int row, int column, Mark mark) {
        this.slots[row - 1][column - 1] = mark;
    }

    /**
     * A method that will return a list of pairs that are the coordinates to an empty slot on the board
     *
     * @return a list of pairs that are the coordinates to an empty slot on the board
     */
    public List<Pair<Integer, Integer>> getPossibleMoves() {
        List<Pair<Integer, Integer>> coordinates = new ArrayList<>();

        for (int row = 1; row <= BOARD_WIDTH; row++) {
            for (int column = 1; column <= BOARD_WIDTH; column++) {
                if (this.getMark(row, column) == Mark.EMPTY) {
                    coordinates.add(new Pair<>(row, column));
                }
            }
        }

        return coordinates;
    }

    /**
     * A method that returns if the board represents a game that is already complete
     *
     * @return a value that is true when the board represents a game that is already complete
     */
    public boolean isGameOver() {
        return this.gameOver;
    }

    /**
     * A method that returns if the game is waiting for a response from the player
     *
     * @return a value that is true when the game is waiting for a response from the player
     */
    public boolean isPlayerTurn() {
        return this.playerTurn;
    }

    /**
     * A method that will set the game into the next turn
     */
    public void nextTurn() {
        this.playerTurn = !this.playerTurn;
    }

    /**
     * A method that will return the winner of the game, can be null
     *
     * @return the winner of the game, can be null
     */
    public Mark getWinner() {
        return this.winner;
    }

    /**
     * Converts this board into a string
     *
     * @return this board as a string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int row = 0; row < BOARD_WIDTH; row++) {
            builder.append("' ").append(this.slots[row][0].getIcon()).append(" '  ' ").append(this.slots[row][1].getIcon()).append(" '  ' ").append(this.slots[row][2].getIcon()).append(" '").append("\n");
        }

        return builder.toString();
    }

    @Override
    public Board clone() {
        Board board = new Board();

        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int column = 0; column < BOARD_WIDTH; column++) {
                board.slots[row][column] = this.slots[row][column];
            }
        }

        board.gameOver = this.gameOver;
        board.playerTurn = this.playerTurn;
        board.winner = this.winner;

        return board;
    }

    /**
     * A method that will check if the gamestate is in a form where the game cannot continue
     */
    public void checkOver() {
        if (this.checkHorizontal() || this.checkVertical() || this.checkDiagonal(true) || this.checkDiagonal(false) || this.checkFull()) {
            this.gameOver = true;
        }
    }

    /**
     * A method that will check if the game is over because a mark has cross horizontally
     *
     * @return a value that is true when the game is over
     */
    private boolean checkHorizontal() {
        for (int row = 0; row < BOARD_WIDTH; row++) {
            if (this.slots[row][0] == this.slots[row][1] && this.slots[row][0] == this.slots[row][2] && this.slots[row][0] != Mark.EMPTY) {
                this.winner = this.slots[row][0];
                return true;
            }
        }

        return false;
    }

    /**
     * A method that will check if the game is over because a mark has cross vertically
     *
     * @return a value that is true when the game is over
     */
    private boolean checkVertical() {
        for (int column = 0; column < BOARD_WIDTH; column++) {
            if (this.slots[0][column] == this.slots[1][column] && this.slots[0][column] == this.slots[2][column] && this.slots[0][column] != Mark.EMPTY) {
                this.winner = this.slots[0][column];
                return true;
            }
        }

        return false;
    }

    /**
     * A method that will check if the game is over because a mark has cross diagonally
     *
     * @param left if the top diagonal is the left or right side of the board
     * @return a value that is true when the game is over
     */
    private boolean checkDiagonal(boolean left) {
        Mark one = (left) ? this.getMark(1, 1) : this.getMark(1, 3);
        Mark two = this.getMark(2, 2);
        Mark three = (left) ? this.getMark(3, 3) : this.getMark(3, 1);

        if (one == two && one == three && one != Mark.EMPTY) {
            this.winner = two;
            return true;
        }

        return false;
    }

    /**
     * A method that will check if the game is over because there are no more possible moves
     *
     * @return a value that is true when the game is over
     */
    private boolean checkFull() {
        return this.getPossibleMoves().isEmpty();
    }
}
