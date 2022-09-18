package tictactoe;

class Player {
    static final int n = 3;

    static boolean findWinner(String[][] matrix, String player) {
        boolean wins = false;
        int[] rowCounter = new int[n];
        int[] columnCounter = new int[n];
        int mainCounter = 0;
        int sideCounter = 0;

        for (int i = 0 ; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (player.equals(matrix[i][j])) {
                    rowCounter[i]++;
                    columnCounter[j]++;
                    if (i == j) {
                        mainCounter++;
                    }
                    if (i + j ==  n - 1) {
                        sideCounter++;
                    }
                }
                if (columnCounter[j] == n) {
                    wins = true;
                    break;
                }
            }
            if (rowCounter[i] == n) {
                wins = true;
                break;
            }
        }
        if (mainCounter == n || sideCounter == n) {
            wins = true;
        }
        return wins;
    }

    static int countTokens(String[][] matrix) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ("X".equals(matrix[i][j]) || "O".equals(matrix[i][j])) {
                    count++;
                }
            }
        }
        return count;
    }
}
