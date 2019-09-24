package golfteesolver;

import java.util.*;

public class GolfTeeSolverImpl implements GolfTeeSolver {
    private static int cnt = 0;
    private List<GolfTeeSolver.Move> moves = new ArrayList<GolfTeeSolver.Move>();

    class Grid {
        private int level;
        private int remainingPieces;
        private List<GolfTeeSolver.Move> moves = new ArrayList<GolfTeeSolver.Move>();
        private GolfTeeSolver.Board.Piece[][] grid = new GolfTeeSolverImpl.Board.Piece[5][5];
        private int[][] cords = {{0, 0},
                {1, 0}, {1, 1},
                {2, 0}, {2, 1}, {2, 2},
                {3, 0}, {3, 1}, {3, 2}, {3, 3},
                {4, 0}, {4, 1}, {4, 2}, {4, 3}, {4, 4}
        };
        private int[][] cordsMap = new int[5][5];
        private Map<String, Integer> failures = new HashMap<String, Integer>();

        private Grid(Grid grid) {
            this();
            this.level = grid.level + 1;
            this.remainingPieces = grid.remainingPieces;
            this.moves.addAll(grid.moves);
            this.failures.putAll(grid.failures);
            for (int r = 0; r < grid.grid.length; r++) {
                for (int c = 0; c < grid.grid[r].length; c++) {
                    this.grid[r][c] = grid.grid[r][c];
                }
            }
        }

        public Grid() {
            level = 0;

            cordsMap[0][0] = 0;
            cordsMap[1][0] = 1;
            cordsMap[1][1] = 2;
            cordsMap[2][0] = 3;
            cordsMap[2][1] = 4;
            cordsMap[2][2] = 5;
            cordsMap[3][0] = 6;
            cordsMap[3][1] = 7;
            cordsMap[3][2] = 8;
            cordsMap[3][3] = 9;
            cordsMap[4][0] = 10;
            cordsMap[4][1] = 11;
            cordsMap[4][2] = 12;
            cordsMap[4][3] = 13;
            cordsMap[4][4] = 14;

            remainingPieces = 0;
        }

        public Grid(GolfTeeSolver.Board board) {
            this();
            fillGrid(board);
        }

        public int getRemainingPieces() {
            return remainingPieces;
        }

        public int getCordsMap(int r, int c) {return this.cordsMap[r][c];}

        private void fillGrid(GolfTeeSolver.Board startingBoard) {
            for (int i = 0; i < 15; i++) {
                int r = cords[i][0];
                int c = cords[i][1];
                GolfTeeSolver.Board.Piece piece = startingBoard.get(i);
                this.grid[r][c] = piece;
                if (piece == GolfTeeSolver.Board.Piece.TEE) remainingPieces++;
            }
        }

        public void updateGrid(GolfTeeSolver.Move move) {
            int from = move.getFrom();
            int to = move.getTo();
            int fromR = cords[from][0];
            int fromC = cords[from][1];
            int toR = cords[to][0];
            int toC = cords[to][1];
            grid[fromR][fromC] = GolfTeeSolver.Board.Piece.EMPTY;
            grid[toR][toC] = GolfTeeSolver.Board.Piece.TEE;

            int diffR = toR - fromR;
            int diffC = toC - fromC;
            int jumpedR, jumpedC;
            if (diffR == 0) {
                jumpedR = fromR;
            } else if (diffR > 0) {
                jumpedR = fromR + 1;
            } else {
                jumpedR = fromR - 1;
            }
            if (diffC == 0) {
                jumpedC = fromC;
            } else if (diffC > 0) {
                jumpedC = fromC + 1;
            } else {
                jumpedC = fromC - 1;
            }

            grid[jumpedR][jumpedC] = GolfTeeSolver.Board.Piece.EMPTY;
            remainingPieces--;
        }

        public Grid findMove() {
            Grid newGrid = null;
            int startPos = 0;

            for (int i = startPos; i < 15; i++) {
                newGrid = new Grid(this);
                Move move = newGrid.checkLocation(i);
                if (move != null) {
                    newGrid.moves.add(move);
                    if (!this.failures.containsKey(newGrid.toKey())) {
                        newGrid.updateGrid(move);
                        newGrid = newGrid.findMove();
                        if (newGrid.remainingPieces == 1) return newGrid;
                        this.failures.putAll(newGrid.failures);
                        this.failures.put(newGrid.toKey(), newGrid.remainingPieces);
                    }
                }
            }
            return this;
        }

        private Move checkLocation2(int from, int directionR, int directionC) {
            GolfTeeSolverImpl.Move move = null;
            int r = cords[from][0];
            int c = cords[from][1];
            int r1 = r + directionR;
            int c1 = c + directionC;
            int r2 = r + directionR * 2;
            int c2 = c + directionC * 2;

            if (r2 < 0 || r2 > 4 || c2 < 0 || c2 > 4) return move; //cannot move off board
            if (grid[r1][c1] == null || grid[r1][c1] == GolfTeeSolver.Board.Piece.EMPTY)
                return move; // cannot jump empty/null piece
            if (grid[r2][c2] == null || grid[r2][c2] == GolfTeeSolver.Board.Piece.TEE)
                return move; // cannot jump to occupied/null space
            int to = cordsMap[r2][c2];
            move = new Move(from, to);
            return move;
        }

        private Move checkLocation(int location) {
            GolfTeeSolverImpl.Move move = null;
            int r = cords[location][0];
            int c = cords[location][1];
            final int DOWN = 1;
            final int UP = -1;
            final int RIGHT = 1;
            final int LEFT = -1;
            final int STAY = 0;
            //check for empty or null piece
            if (grid[r][c] == null || grid[r][c] == GolfTeeSolver.Board.Piece.EMPTY) return move;
            move = checkLocation2(location, UP, STAY); //check up
            if (move == null) move = checkLocation2(location, DOWN, STAY); //check down
            if (move == null) move = checkLocation2(location, STAY, RIGHT); //check right
            if (move == null) move = checkLocation2(location, STAY, LEFT); //check left
            if (move == null) move = checkLocation2(location, DOWN, RIGHT); //check top left to bottom right diaginal
            if (move == null) move = checkLocation2(location, DOWN, LEFT); //check top right to bottom left diaginal
            if (move == null) move = checkLocation2(location, UP, RIGHT); //check bottom left to top right diaginal
            if (move == null) move = checkLocation2(location, UP, LEFT); //check bottom right to top left diaginal
            return move;
        }

        public String toKey() {
            String str = "";
            for (GolfTeeSolver.Move m : moves) {
                str += m.getFrom() + "," + m.getTo() + "; ";
            }
            return str;
        }
    }

    public class Move implements GolfTeeSolver.Move {
        private int moveFrom;
        private int moveTo;

        public Move(int moveFrom, int moveTo) {
            this.moveFrom = moveFrom;
            this.moveTo = moveTo;
        }

        @Override
        public int getFrom() {
            return this.moveFrom;
        }

        @Override
        public int getTo() {
            return this.moveTo;
        }
    }

    public class Board implements GolfTeeSolver.Board {
        private Piece[] board = new Piece[15];

        public Board() {
            int empty = (int)Math.floor(Math.random() * 15);
            Arrays.fill(this.board, Piece.TEE);
            this.board[empty] = Piece.EMPTY;
        }

        @Override
        public Piece get(int location) {
            return this.board[location];
        }
    }

    @Override
    public List<GolfTeeSolver.Move> solve(GolfTeeSolver.Board startingBoard) {
        Grid grid = new Grid(startingBoard);

        grid = grid.findMove();
        return grid.moves;
    }

}
