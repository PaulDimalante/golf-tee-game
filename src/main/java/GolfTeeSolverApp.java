import java.util.List;

public class GolfTeeSolverApp implements GolfTeeSolver {
    private class Move implements GolfTeeSolver.Move {
        @Override
        public int getFrom() {
            return 0;
        }
        @Override
        public int getTo() {
            return 0;
        }
    }

    public class Board implements GolfTeeSolver.Board {
        @Override
        public Piece get(int location) {
            return null;
        }
        public int getRemainingPieces() {
            return 13;
        }
    }

    @Override
    public List<GolfTeeSolver.Move> solve(GolfTeeSolver.Board startingBoard) {
        return null;
    }

    public Board getBoard() {
        return new Board();
    }
}
