import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    Scanner scanner = new Scanner(System.in);
    int totalRows, totalColumns, userColumn, userRow;
    String[][] userMinefield;
    String[][] adminMinefield;

    public int getTotalRows() {
        int totalRows;
        while (true) {
            System.out.println("Enter the number of rows:");
            totalRows = scanner.nextInt();
            if (totalRows >= 2) {
                break;
            }
            System.out.println("Invalid input");
        }
        return totalRows;
    }

    public int getTotalColumns() {
        Scanner scanner = new Scanner(System.in);
        int totalColumns;
        while (true) {
            System.out.println("Enter the number of columns:");
            totalColumns = scanner.nextInt();
            if (totalColumns >= 2) {
                break;
            }
            System.out.println("Invalid input");
        }
        return totalColumns;
    }

    public void createMinefield() {
        userMinefield = new String[totalRows][totalColumns];
        adminMinefield = new String[totalRows][totalColumns];
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                userMinefield[i][j] = "-";
                adminMinefield[i][j] = "-";
            }
        }
    }

    public void printField(String[][] field) {
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }

    public void placeMines() {
        int totalMineCount = (totalRows * totalColumns) / 4;
        int currentMineCount = 0;
        Random rand = new Random();
        while (true) {
            int mineRow = rand.nextInt(totalRows);
            int mineColumn = rand.nextInt(totalColumns);

            if (adminMinefield[mineRow][mineColumn].equals("*")) {
                continue;
            }
            adminMinefield[mineRow][mineColumn] = "*";
            currentMineCount++;
            if (totalMineCount == currentMineCount) {
                break;
            }
        }
    }

    public void startGame() {
        int winCondition = (totalRows * totalColumns) - (totalRows * totalColumns) / 4;
        int currentInput = 0;
        while (currentInput < winCondition) {
            getRowColumn();
            if (adminMinefield[userRow][userColumn].equals("*")) {
                System.out.println("You lost!");
                break;
            }
            int[][] distanceMatrix = calculateDistance();
            int mineCount = countMines(distanceMatrix);

            userMinefield[userRow][userColumn] = Integer.toString(mineCount);
            printField(userMinefield);

            currentInput++;
        }
        if (currentInput == winCondition) {
            System.out.println("Congratulations! You won!");
        }
    }

    public void getRowColumn() {
        while (true) {
            System.out.print("Enter the row: ");
            userRow = scanner.nextInt();
            if (userRow >= totalRows || userRow < 0) {
                System.out.println("Invalid input");
                continue;
            }
            System.out.print("Enter the column: ");
            userColumn = scanner.nextInt();
            if (userColumn >= totalColumns || userColumn < 0) {
                System.out.println("Invalid input");
                continue;
            }
            break;
        }
    }

    public int[][] calculateDistance() {
        int[][] distanceMatrix = new int[totalRows][totalColumns];

        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                int rowDifference = userRow - i, columnDifference = userColumn - j;
                int squaredDifferenceSum = (int) (Math.pow(rowDifference, 2) + Math.pow(columnDifference, 2));
                int distance = (int) Math.sqrt(squaredDifferenceSum);
                distanceMatrix[i][j] = distance;
            }
        }
        return distanceMatrix;
    }

    public int countMines(int[][] distanceMatrix) {
        int mineCount = 0;

        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                if (distanceMatrix[i][j] == 1 && adminMinefield[i][j].equals("*")) {
                    mineCount++;
                }
            }
        }
        return mineCount;
    }

    public MineSweeper() {
        totalRows = getTotalRows();
        totalColumns = getTotalColumns();
        System.out.println(totalRows);
        System.out.println(totalColumns);
        createMinefield();
        printField(userMinefield);
        System.out.println("///////////////// Game started! Good luck! ///////////////// ");
        placeMines();
        printField(adminMinefield);
        startGame();
    }
}

