package tictactoe;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Game {
    static Random random = new Random();
    static final int n = 3;
    static int[] playUser (String[][] matrix) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the coordinates: ");
            String input = sc.nextLine();
            String[] coordinates = input.split(" ");
            if (coordinates.length == 2 && coordinates[0].matches("\\d+") && coordinates[1].matches("\\d+")) {
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);
                if (x < 1 || y < 1 || x > n || y > n) {
                    System.out.printf("Coordinates should be from 1 to %d!\n", n);
                } else if (matrix[x - 1][y - 1] != null) {
                    System.out.println("This cell is occupied! Choose another one!");
                }
                else {
                    return new int[] {x - 1, y - 1};
                }
            } else {
                System.out.println("You should enter numbers!");
            }
        }
    }

    static int[] playEasyLevel(String[][] matrix) {
        int x;
        int y;
        while (true) {
            x = random.nextInt(n);
            y = random.nextInt(n);
            if (matrix[x][y] == null) {
                return new int[] {x, y};
            }
        }
    }

    static int[] playMediumLevel(String[][] matrix, String player) {
        //player is always AI, the question is whether it's X or O
        String user = "X".equals(player) ? "O" : "X";
        int[] canWin = canWin(matrix, player);
        int[] canBlock = canWin(matrix, user);
        if (canWin[0] == 1) {
            return new int[] {canWin[1], canWin[2]};
        } else if (canBlock[0] == 1) {
            return new int[] {canBlock[1], canBlock[2]};
        } else {
            return playEasyLevel(matrix);
        }
    }

    static int[] playHardLevel(String[][] matrix, String player) {
        String user = "X".equals(player) ? "O" : "X";
        Move bestMove = minimax(matrix, player, user, 1);
        return new int[] {bestMove.x, bestMove.y};
    }

    static int[] canWin(String[][] matrix, String player) {
        int x = 0;
        int y = 0;
        int canWin = 0;
        //String[][] newBoard = Arrays.stream(matrix).map(String[]::clone).toArray(String[][]::new);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == null) {
                    matrix[i][j] = player;
                    x = i;
                    y = j;
                    if (Player.findWinner(matrix, player)) {
                        canWin = 1;
                        matrix[i][j] = null;
                        break;
                    } else {
                        matrix[i][j] = null;
                    }
                }
            }
            if (canWin == 1) {
                break;
            }
        }
        return new int[] {canWin, x, y};
    }

    static ArrayList<Move> emptyIndexes(String[][] newBoard) {
        ArrayList<Move> availSpots = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (newBoard[i][j] == null) {
                    availSpots.add(new Move(i, j, 0));
                }
            }
        }
        return availSpots;
    }

    static Move minimax(String[][] newBoard, String player, String user, int turn) {
        ArrayList<Move> availSpots = emptyIndexes(newBoard);
        if (Player.findWinner(newBoard, player)) {
            return new Move(0, 0, 10);
        } else if (Player.findWinner(newBoard, user)) {
            return new Move(0, 0, -10);
        } else if (Player.countTokens(newBoard) == n * n) {
            return new Move(0, 0, 0);
        }
        ArrayList<Move> moves = new ArrayList<>();
        for (Move availSpot : availSpots) {
            Move move = new Move(0, 0, 0);
            move.x = availSpot.x;
            move.y = availSpot.y;
            if (turn % 2 == 1) {
                newBoard[move.x][move.y] = player;
            } else {
                newBoard[move.x][move.y] = user;
            }
            Move result = minimax(newBoard, player, user, ++turn);
            move.score = result.score;
            newBoard[move.x][move.y] = null;
            turn--;
            moves.add(move);
        }
        Move bestMove = null;
        if (turn % 2 == 1) {
            int bestScore = -100;
            for (Move move : moves) {
                if (move.score > bestScore) {
                    bestScore = move.score;
                    bestMove = move;
                }
            }
        } else {
            int bestScore = 100;
            for (Move move : moves) {
                if (move.score < bestScore) {
                    bestScore = move.score;
                    bestMove = move;
                }
            }
        }
        return bestMove;
    }
}