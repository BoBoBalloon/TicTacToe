package me.bobo;

import me.bobo.game.Board;
import me.bobo.game.Mark;

import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] ignore) {
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);

        while (!board.isGameOver()) {
            if (!board.isPlayerTurn()) {
                board.makeAIMove();
                continue;
            }

            System.out.println(board.toString());

            String line = scanner.nextLine();

            if (!line.matches("\\d,\\d")) { //regex = ^\d,\d$
                continue;
            }

            String[] args = line.split(",");

            int row;

            try {
                row = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                continue;
            }

            if (row > Board.BOARD_WIDTH || row < 0) {
                continue;
            }

            int column;

            try {
                column = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                continue;
            }

            if (column > Board.BOARD_WIDTH || column < 0) {
                continue;
            }

            board.makePlayerMove(row, column);
        }

        System.out.println(board.toString());

        Mark winner = board.getWinner();
        if (winner != null) {
            System.out.println(winner.getIcon() + " has won the game!");
        } else {
            System.out.println("The game ended in a tie!");
        }
    }
}