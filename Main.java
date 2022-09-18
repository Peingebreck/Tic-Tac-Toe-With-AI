package tictactoe;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static final int n = 3;
    static String[] mode = {"user", "easy", "medium", "hard"};

    public static void main(String[] args) {
        getCommand();
    }

    static void getCommand() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Input command: ");
            String input = sc.nextLine();
            String[] params = input.split(" ");
            if ("exit".equals(input)) {
                break;
            } else if ("start".equals(params[0]) && params.length == 3
                    && Arrays.asList(mode).contains(params[1])
                    && Arrays.asList(mode).contains(params[2])) {
                startGame(params[1], params[2]);
            } else {
                System.out.println("Bad parameters!");
            }
        }
    }

    static void startGame(String mode1, String mode2) {
        String[][] matrix = new String[n][n];
        String player = "X";
        String[] modes = {mode1, mode2};
        String state;
        boolean finished = false;
        int[] coor = new int[2];

        printField(matrix);
        while (!finished) {
            for (String mode : modes) {
                if("user".equals(mode)) {
                    coor = Game.playUser(matrix);
                } else if ("easy".equals(mode)) {
                    coor = Game.playEasyLevel(matrix);
                    System.out.println("Making move level \"easy\"");
                } else if ("medium".equals(mode)) {
                    coor = Game.playMediumLevel(matrix, player);
                    System.out.println("Making move level \"medium\"");
                } else if("hard".equals(mode)) {
                    coor = Game.playHardLevel(matrix, player);
                    System.out.println("Making move level \"hard\"");
                }
                matrix[coor[0]][coor[1]] = player;
                printField(matrix);
                state = findState(matrix);
                if (!"Game not finished".equals(state)) {
                    System.out.println(state + '\n');
                    finished = true;
                    break;
                }
                player = "X".equals(player) ? "O" : "X";
            }
        }
    }

    static void printField(String[][] matrix) {
        System.out.println("---------");
        for (int i = 0; i < n; i++) {
            System.out.print("| ");
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] == null ? "  " : matrix[i][j] + " ");
            }
            System.out.print("|" + '\r' + '\n');
        }
        System.out.println("---------");
    }

    static String findState(String[][] matrix) {
        boolean XWins = Player.findWinner(matrix, "X");
        boolean OWins = Player.findWinner(matrix, "O");
        if (!XWins && !OWins) {
            int countXAndO = Player.countTokens(matrix);
            if (countXAndO < n * n) {
                return "Game not finished";
            } else {
                return "Draw";
            }
        } else if (XWins) {
            return "X wins";
        } else {
            return "O wins";
        }
    }
}
