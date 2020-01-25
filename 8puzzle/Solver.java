/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class Solver {

    private MinPQ<BoardWrapper> priorityQueue;
    private boolean foundSolution = false;
    private BoardWrapper boardWrapperSolved;
    private Board initial;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        this.initial = initial;

        if (initial.isGoal()) {
            foundSolution = true;
            boardWrapperSolved = new BoardWrapper(initial, null, 0);
            return;
        }

        priorityQueue = new MinPQ<>(boardComparator());
        priorityQueue.insert(new BoardWrapper(initial, null, 0));

        while (!priorityQueue.isEmpty()) {
            BoardWrapper min = priorityQueue.delMin();

            if (min.board.isGoal()) {
                foundSolution = true;
                boardWrapperSolved = min;
                break;
            }

            for (Board neighbor : min.board.neighbors()) {
                boolean equalsToPrevious = false;
                if (min.previous != null) {
                    equalsToPrevious = neighbor.equals(min.previous.board);
                }

                if (!equalsToPrevious) {
                    priorityQueue.insert(new BoardWrapper(
                                    neighbor, min, min.moves+1));
                }
            }
        }
    }

    private static class BoardWrapper {
        public final int moves;
        public final Board board;
        public final BoardWrapper previous;

        public BoardWrapper(Board board, BoardWrapper previous, int moves) {
            this.previous = previous;
            this.moves = moves;
            this.board = board;
        }
    }

    private static Comparator<BoardWrapper> boardComparator() {
        return (boardWrapper, t1) -> {
            int p1 = boardWrapper.moves + boardWrapper.board.manhattan();
            int p2 = t1.moves + t1.board.manhattan();
            if (p1 == p2) return 0;
            return p1 < p2 ? -1 : 1;
        };
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return foundSolution;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (boardWrapperSolved == null) return -1;
        return boardWrapperSolved.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!isSolvable()) throw new NoSuchElementException();
        if (boardWrapperSolved == null) throw new NoSuchElementException();
        LinkedStack<Board> stack = new LinkedStack<>();

        BoardWrapper ref = this.boardWrapperSolved;
        while (ref.previous != null) {
            stack.push(ref.board);
            ref = ref.previous;
        }
        stack.push(initial);
        return stack;
    }

    // test client (see below)

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
