/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {

    private final int[][] tiles;
    private int hamming;
    private int manhattan;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();
        if (tiles.length < 1) throw new IllegalArgumentException();

        this.tiles = tiles;
        calculateManhattanAndHamming();
    }

    // string representation of this board
    public String toString() {
        int n = tiles.length;
        StringBuilder sb = new StringBuilder();

        sb.append(n);

        // rows
        for (int i = 0; i < n; i++) {
            sb.append("\n");
            // columns
            for (int j = 0; j < n; j++) {
                sb.append(" ");
                sb.append(tiles[i][j]);
            }
        }


        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return this.tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    private void calculateManhattanAndHamming() {
        int sum = 0;
        int n = dimension();
        int wrongTiles = 0;
        int current = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = tiles[i][j];

                // hamming
                if (value > 0 && current != value) {
                    wrongTiles++;
                }
                current++;

                // manhattan
                if (value == 0) {
                    continue;
                }

                int mod = value % n;
                int x = value / n;
                int y = mod - 1;
                if (mod == 0) {
                    x--;
                    y = n-1;
                }
                sum += Math.abs(i - x) + Math.abs(j - y);
            }
        }

        hamming = wrongTiles;
        manhattan = sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (!y.getClass().equals(this.getClass()))
            return false;

        Board another = (Board) y;
        int size = dimension();
        if (size != another.tiles.length)
            return false;

        for (int i = 0; i < size; i++) {
            if (tiles[i].length != another.tiles.length) {
                return false;
            }

            for (int j = 0; j < size; j++) {
                int x1 = tiles[i][j];
                int x2 = another.tiles[i][j];

                if (x1 != x2) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        BoardIterator boardIterator = new BoardIterator(this.tiles);
        return new Iterable<Board>() {

            public Iterator<Board> iterator() {
                return boardIterator;
            }
        };
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] copy = cloneArray(tiles);
        int size = dimension();
        for (int i = 1; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x1 = copy[i][j];
                int x0 = copy[i-1][j];

                if (x1 != 0 && x0 != 0) {
                    changePosition(copy, i, j, i-1, j);
                    return new Board(copy);
                }
            }
        }
        return null;
    }

    private static class BoardIterator implements Iterator<Board> {

        private static final int MOVE_UP = 0;
        private static final int MOVE_RIGHT = 1;
        private static final int MOVE_DOWN = 2;
        private static final int MOVE_LEFT = 3;

        private int[][] iteratorTiles;
        private boolean[] moves = new boolean[4];
        private int currentMove = -1;
        private int blankX = 0;
        private int blankY = 0;

        public BoardIterator(int[][] iteratorTiles) {
            this.iteratorTiles = cloneArray(iteratorTiles);
            this.findTileBlank();
            this.findMoves();
            this.setCursorToTheNextMove();
        }

        public boolean hasNext() {
            return currentMove < this.moves.length;
        }

        public Board next() {
            if (currentMove >= this.moves.length) {
                throw new NoSuchElementException();
            }
            int[][] copyTiles = cloneArray(iteratorTiles);

            if (currentMove == MOVE_UP) {
                changePosition(copyTiles,
                               blankX, blankY, blankX - 1, blankY);
            }
            if (currentMove == MOVE_RIGHT) {
                changePosition(copyTiles,
                               blankX, blankY, blankX, blankY + 1);
            }
            if (currentMove == MOVE_DOWN) {
                changePosition(copyTiles,
                               blankX, blankY, blankX + 1, blankY);
            }
            if (currentMove == MOVE_LEFT) {
                changePosition(copyTiles,
                               blankX, blankY, blankX, blankY - 1);
            }

            setCursorToTheNextMove();
            return new Board(copyTiles);
        }

        private void setCursorToTheNextMove() {
            int n = this.moves.length;
            currentMove++;
            while (currentMove < n) {
                boolean valid = this.moves[currentMove];
                if (valid) {
                    break;
                }
                currentMove++;
            }
        }

        private void findMoves() {
            int size = iteratorTiles.length;
            if (blankX > 0) {
                moves[MOVE_UP] = true;
            }
            if (blankY > 0) {
                moves[MOVE_LEFT] = true;
            }
            if (blankX < size - 1) {
                moves[MOVE_DOWN] = true;
            }
            if (blankY < size - 1) {
                moves[MOVE_RIGHT] = true;
            }
        }

        private void findTileBlank() {
            int size = iteratorTiles.length;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int v = iteratorTiles[i][j];
                    if (v == 0) {
                        blankX = i;
                        blankY = j;
                        return;
                    }
                }
            }
        }
    }

    private static void changePosition(int[][] src,
                                       int x1, int y1, int x2, int y2) {
        int aux = src[x1][y1];
        src[x1][y1] = src[x2][y2];
        src[x2][y2] = aux;
    }

    /**
     * Clones the provided array
     *
     * @param src
     * @return a new clone of the provided array
     */
    private static int[][] cloneArray(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            target[i] = Arrays.copyOf(src[i], src[i].length);
        }
        return target;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        Board board = buildBoardFromStdInput();

        StdOut.println(board);
        StdOut.println();

        StdOut.println("Dimension: " + board.dimension());
        StdOut.println("Hamming: " + board.hamming());
        StdOut.println("Manhattan: " + board.manhattan());
        StdOut.println("Solved: " + board.isGoal());


        StdOut.println();
        StdOut.println("Twin");
        StdOut.println(board.twin());

        StdOut.println();
        StdOut.println("Neighbors");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println("------" + board.equals(neighbor));
        }
    }

    private static Board buildBoardFromStdInput() {
        int n = StdIn.readInt();
        int[][] arr = new int[n][n];
        int row = 0;
        int col = 0;
        while (!StdIn.isEmpty()) {
            int value = StdIn.readInt();
            arr[row][col++] = value;

            if (col >= n) {
                col = 0;
                row++;
            }
        }
        return new Board(arr);
    }
}
