import java.util.ArrayList;
import java.util.List;

public class GolfTeeSolverImpl implements GolfTeeSolver {
    private List<GolfTeeSolver.Move> moves = new ArrayList<GolfTeeSolver.Move>();

    private class Grid {
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

        public Grid(GolfTeeSolver.Board board) {
            cordsMap[0][0]=0;
            cordsMap[1][0]=1;
            cordsMap[1][1]=2;
            cordsMap[2][0]=3;
            cordsMap[2][1]=4;
            cordsMap[2][2]=5;
            cordsMap[3][0]=6;
            cordsMap[3][1]=7;
            cordsMap[3][2]=8;
            cordsMap[3][3]=9;
            cordsMap[4][0]=10;
            cordsMap[4][1]=11;
            cordsMap[4][2]=12;
            cordsMap[4][3]=13;
            cordsMap[4][4]=14;

            remainingPieces = 0;
            fillGrid(board);
        }

        public int getRemainingPieces() {
            return remainingPieces;
        }

        private void fillGrid(GolfTeeSolver.Board startingBoard) {
            for (int i = 0; i < 15; i++) {
                int r = cords[i][0];
                int c = cords[i][1];
                GolfTeeSolver.Board.Piece piece = startingBoard.get(i);
                this.grid[r][c] = piece;
                if(piece == GolfTeeSolver.Board.Piece.TEE) remainingPieces++;
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
            if(diffR == 0) {
                jumpedR = fromR;
            } else if(diffR > 0) {
                jumpedR = fromR + 1;
            } else {
                jumpedR = fromR - 1;
            }
            if(diffC == 0) {
                jumpedC = fromC;
            } else if(diffC > 0) {
                jumpedC = fromC + 1;
            } else {
                jumpedC = fromC - 1;
            }

            grid[jumpedR][jumpedC] = GolfTeeSolver.Board.Piece.EMPTY;
            remainingPieces--;
        }

        private List<GolfTeeSolver.Move> findMove() {
            Move move = null;
            for (int i = 0; i < 15; i++) {
                move = checkLocation(i);
                if(move != null) {
                    moves.add(move);
                    updateGrid(move);
                    if(remainingPieces == 1) return moves;
                    findMove();
                }
            }

            return moves;
        }

        private Move checkLocation(int location) {
            GolfTeeSolverImpl.Move move = null;
            int r = cords[location][0];
            int c = cords[location][1];
            //check for empty or null piece
            if (grid[r][c] == null || grid[r][c] == GolfTeeSolver.Board.Piece.EMPTY) return move;
            //check up
            if (r > 1) {
                if (GolfTeeSolver.Board.Piece.TEE == grid[r - 1][c]) {
                    if (GolfTeeSolver.Board.Piece.EMPTY == grid[r - 2][c]) {
                        return new Move(location, cordsMap[r - 2][c]);
                    }
                }
            }
            //check down
            if (r < 3) {
                if (GolfTeeSolver.Board.Piece.TEE == grid[r + 1][c]) {
                    if (GolfTeeSolver.Board.Piece.EMPTY == grid[r + 2][c]) {
                        return new Move(location, cordsMap[r + 2][c]);
                    }
                }
            }
            //check left
            if (c > 1) {
                if (GolfTeeSolver.Board.Piece.TEE == grid[r][c - 1]) {
                    if (GolfTeeSolver.Board.Piece.EMPTY == grid[r][c - 2]) {
                        return new Move(location, cordsMap[r][c - 2]);
                    }
                }
            }
            //check right
            if (c < 3) {
                if (GolfTeeSolver.Board.Piece.TEE == grid[r][c + 1]) {
                    if (GolfTeeSolver.Board.Piece.EMPTY == grid[r][c + 2]) {
                        return new Move(location, cordsMap[r][c + 2]);
                    }
                }
            }
            //check top left to bottom right diaginal (left to right)
            if (r < 3 && c < 3) {
                if (GolfTeeSolver.Board.Piece.TEE == grid[r + 1][c + 1]) {
                    if (GolfTeeSolver.Board.Piece.EMPTY == grid[r + 2][c + 2]) {
                        return new Move(location, cordsMap[r + 2][c + 2]);
                    }
                }
            }
            //check top left to bottom right diaginal (right to left)
            if (r > 1 && c > 1) {
                if (GolfTeeSolver.Board.Piece.TEE == grid[r - 1][c - 1]) {
                    if (GolfTeeSolver.Board.Piece.EMPTY == grid[r - 2][c - 2]) {
                        return new Move(location, cordsMap[r - 2][c - 2]);
                    }
                }
            }
            //check top right to bottom left diaginal (left to right)
            if (r > 1 && c < 3) {
                if (GolfTeeSolver.Board.Piece.TEE == grid[r - 1][c + 1]) {
                    if (GolfTeeSolver.Board.Piece.EMPTY == grid[r - 2][c + 2]) {
                        return new Move(location, cordsMap[r - 2][c + 2]);
                    }
                }
            }
            //check top right to bottom left diaginal (right to left)
            if (r < 3 && c > 1) {
                if (GolfTeeSolver.Board.Piece.TEE == grid[r + 1][c - 1]) {
                    if (GolfTeeSolver.Board.Piece.EMPTY == grid[r + 2][c - 2]) {
                        return new Move(location, cordsMap[r + 2][c - 2]);
                    }
                }
            }
            return move;
        }
    }

    public class Move implements GolfTeeSolver.Move {
        private int moveFrom;
        private int moveTo;

        public Move() {
        }

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
        private Piece[] board = {Piece.EMPTY,
                Piece.TEE,
                Piece.TEE,
                Piece.TEE,
                Piece.TEE,
                Piece.TEE,
                Piece.TEE,
                Piece.TEE,
                Piece.TEE,
                Piece.TEE,
                Piece.TEE,
                Piece.TEE,
                Piece.TEE,
                Piece.TEE,
                Piece.TEE};

        @Override
        public Piece get(int location) {
            return this.board[location];
        }
    }

    @Override
    public List<GolfTeeSolver.Move> solve(GolfTeeSolver.Board startingBoard) {
        Grid grid = new Grid(startingBoard);
        this.moves = grid.findMove();
        return this.moves;
    }

}
