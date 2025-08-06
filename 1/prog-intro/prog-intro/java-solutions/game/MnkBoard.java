package game;

import java.util.Arrays;
import java.util.Map;


public class MnkBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(Cell.X, 'X', Cell.O, 'O', Cell.E, '.');
    private final Cell[][] cells;
    protected int m, n, k;
    private Cell turn;

    public MnkBoard(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.cells = new Cell[m][n];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public int getM() {
        return this.m;
    }

    @Override
    public int getN() {
        return this.n;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        cells[move.row()][move.column()] = move.value();
        int i = move.row();
        int j = move.column();
        int empty = -1;
        int inRow = -1;
        int inCol = -1;
        int inDiag1 = -1;
        int inDiag2 = -1;
        for (int p = 0; p < n; p++) {
            if (j - p >= 0 && cells[i][j - p] == turn) {
                inRow++;
            } else if (j - p >= 0 && cells[i][j - p] == Cell.E) {
                empty++;
            } else {
                break;
            }
        }
        for (int p = 0; p < n; p++) {
            if (j + p < n && cells[i][j + p] == turn) {
                inRow++;
            } else if (j + p < n && cells[i][j + p] == Cell.E) {
                empty++;
            } else {
                break;
            }
        }
        if (inRow == k) {
            return Result.WIN;
        }

        for (int p = 0; p < n; p++) {
            if (i - p >= 0 && cells[i - p][j] == turn) {
                inCol++;
            } else if (i - p >= 0 && cells[i - p][j] == Cell.E) {
                empty++;
            } else {
                break;
            }
        }
        for (int p = 0; p < n; p++) {
            if (i + p < m && cells[i + p][j] == turn) {
                inCol++;
            } else if (i + p < m && cells[i + p][j] == Cell.E) {
                empty++;
            } else {
                break;
            }
        }
        if (inCol == k) {
            return Result.WIN;
        }

        for (int p = 0; p < n; p++) {
            if (i - p >= 0 && j - p >= 0 && cells[i - p][j - p] == turn) {
                inDiag1++;
            } else if (i - p >= 0 && j - p >= 0 && cells[i - p][j - p] == Cell.E) {
                empty++;
            } else {
                break;
            }
        }
        for (int p = 0; p < n; p++) {
            if (i + p < m && j + p < n && cells[i + p][j + p] == turn) {
                inDiag1++;
            } else if (i + p < m && j + p < n && cells[i + p][j + p] == Cell.E) {
                empty++;
            } else {
                break;
            }
        }
        if (inDiag1 == k) {
            return Result.WIN;
        }

        for (int p = 0; p < n; p++) {
            if (i - p >= 0 && j + p < n && cells[i - p][j + p] == turn) {
                inDiag2++;
            } else if (i - p >= 0 && j + p < n && cells[i - p][j + p] == Cell.E) {
                empty++;
            } else {
                break;
            }
        }
        for (int p = 0; p < n; p++) {
            if (i + p < m && j - p >= 0 && cells[i + p][j - p] == turn) {
                inDiag2++;
            } else if (i + p < m && j - p >= 0 && cells[i + p][j - p] == Cell.E) {
                empty++;
            } else {
                break;
            }
        }
        if (inDiag2 == k) {
            return Result.WIN;
        }

        if (empty == 0) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }


    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.row() && move.row() < m && 0 <= move.column() && move.column() < n && cells[move.row()][move.column()] == Cell.E && turn == getCell();
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" ");
        for (int p = 0; p < n; p++) {
            sb.append(p);
        }
        for (int r = 0; r < m; r++) {
            sb.append(System.lineSeparator());
            sb.append(r);
            for (int c = 0; c < n; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
}
