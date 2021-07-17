package me.bobo.game;

import javafx.util.Pair;

/**
 * A util class using the minimax algorithm to control the AIs moves
 */
public class Minimax {

    /**
     * A util method to return the value of the board
     *
     * @param board the board object
     * @param maxDepth the max depth the algorithm should look into
     * @param alpha the alpha value
     * @param beta the beta value
     * @param currentDepth the current depth of the algorithm
     * @return the score of the board
     */
    public static int minimax(Board board, int maxDepth, int alpha, int beta, int currentDepth) {
        board.checkOver();
        if (currentDepth >= maxDepth || board.isGameOver()) {
            return score(board, currentDepth);
        }

        if (board.isPlayerTurn()) {
            return getMax(board, maxDepth, alpha, beta, currentDepth);
        } else {
            return getMin(board, maxDepth, alpha, beta, currentDepth);
        }
    }

    private static int getMax(Board board, int maxDepth, int alpha, int beta, int currentDepth) {
        Pair<Integer, Integer> bestMove = null;

        for (Pair<Integer, Integer> pair : board.getPossibleMoves()) {
            Board copy = board.clone();
            Mark mark;

            if (copy.isPlayerTurn()) {
                mark = Mark.X;
            } else {
                mark = Mark.O;
            }

            copy.setMark(pair.getKey(), pair.getValue(), mark);

            copy.nextTurn();

            int score = minimax(copy, maxDepth, alpha, beta, currentDepth + 1);

            if (score > alpha) {
                alpha = score;
                bestMove = pair;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (bestMove == null) {
            return alpha;
        }

        Mark mark;

        if (board.isPlayerTurn()) {
            mark = Mark.X;
        } else {
            mark = Mark.O;
        }

        board.setMark(bestMove.getKey(), bestMove.getValue(), mark);

        return alpha;
    }

    private static int getMin(Board board, int maxDepth, int alpha, int beta, int currentDepth) {
        Pair<Integer, Integer> bestMove = null;

        for (Pair<Integer, Integer> pair : board.getPossibleMoves()) {
            Board copy = board.clone();
            Mark mark;

            if (copy.isPlayerTurn()) {
                mark = Mark.X;
            } else {
                mark = Mark.O;
            }

            copy.setMark(pair.getKey(), pair.getValue(), mark);

            copy.nextTurn();

            int score = minimax(copy, maxDepth, alpha, beta, currentDepth + 1);

            if (score < beta) {
                beta = score;
                bestMove = pair;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (bestMove == null) {
            return beta;
        }

        Mark mark;

        if (board.isPlayerTurn()) {
            mark = Mark.X;
        } else {
            mark = Mark.O;
        }

        board.setMark(bestMove.getKey(), bestMove.getValue(), mark);

        return beta;
    }

    /**
     * A method to return the value of a gamestate
     *
     * @param board the board
     * @return value of the gamestate
     */
    private static int score(Board board, int currentDepth) {
        if (!board.isGameOver() || board.getWinner() == null) {
            return 0;
        }

        if (board.getWinner() == Mark.X) {
            return 10 - currentDepth;
        } else {
            return -10 + currentDepth;
        }
    }
}
